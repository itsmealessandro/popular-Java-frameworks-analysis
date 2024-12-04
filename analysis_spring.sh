#!/bin/bash
set -e # Stop the script immediately if any command fails

#PATH_TO_LOCUST="locust"
#PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulate_sessions.py"
#PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"

PATH_TO_RESULTS="csv_results/S_JIT"
PATH_TO_LOCUST_FILE="simulate_sessions.py"
HOST="http://localhost:9966"

# Create a process group for the script
trap "kill 0" SIGINT SIGTERM EXIT

# NOTE: Input is expected in minutes, it will be converted into seconds
time=""
users=""
db=""

# Args processing
while [[ "$#" -gt 0 ]]; do
  case $1 in
  --t)
    time="$2"
    shift
    ;; # Save time value
  --u)
    users="$2"
    shift
    ;; # Save users value
  --db)
    db="$2"
    shift
    ;; # Save db value
  *)
    echo "Unknown argument: $1"
    exit 1
    ;;
  esac
  shift
done

# Check mandatory arguments
if [[ -z "$time" || -z "$users" || -z "$db" ]]; then
  echo "Error: --t (time in minutes), --u (users), and --db (database) are mandatory."
  exit 1
fi

# Explain the script arguments
echo "Running the script with: minutes=${time}, users=${users}, db=${db}"

# Run the app and the test
for i in $(seq 1 3); do
  echo " ---------------------- Iteration number: ${i} ---------------------- "
  cd ./spring-petclinic-rest-master/ && ./mvnw spring-boot:start -Dspring-boot.run.arguments="--db=${db}" &
  MAVEN_PID=$!

  sleep 20 # Give enough time for the app to set up

  # Get the current date and time in a format suitable for filenames
  current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')
  current_date=$(date '+%Y_%m_%d')

  # Ensure the results directory exists
  mkdir -p "${PATH_TO_RESULTS}/${current_date}/${i}"

  # Ensure the results directory exists (perf)
  mkdir -p "${PATH_TO_RESULTS}/perf/${current_date}/${i}"
  # perf analysis
  sudo perf stat -e cycles,instructions,cache-references,cache-misses,branch-instructions,branch-misses -a -o "${PATH_TO_RESULTS}/perf/${current_date}/${i}/perf_data_${current_time}.txt" &

  PERF_PID=$! # Save the perf process PID

  # Run the locust command with the date in the CSV filename
  cd ./locust/
  echo path: . $(pwd) . This is the path

  LOCUST_PATH=$(which locust)

  $LOCUST_PATH --headless -u "${users}" -t "${time}s" --host "${HOST}" --csv "${PATH_TO_RESULTS}/${current_date}/${i}/${current_time}" -f "${PATH_TO_LOCUST_FILE}"

  cd ../spring-petclinic-rest-master

  ./mvnw spring-boot:stop

  kill $PERF_PID
  wait $PERF_PID # Ensure that perf has terminated

  cd ..
  echo path: . $(pwd)

  #
  #  # kill the process via the port recognition
  #  PORT=9966
  #
  #  PID=$(lsof -t -i:$PORT)
  #
  #  if [ -n "$PID" ]; then
  #    echo "La porta $PORT è in uso dal processo con PID: $PID"
  #    echo "Uccido il processo..."
  #    kill $PID
  #    # kill -9 $PID aggressive closure
  #    echo "Processo terminato."
  #  else
  #    echo "La porta $PORT non è in uso."
  #  fi
  #

  echo " ---------------------- Iteration ${i} Finished ---------------------- "
  sleep 5
done
