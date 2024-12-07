#!/bin/bash

#PATH_TO_LOCUST="locust"
#PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulate_sessions.py"
#PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"

PATH_TO_RESULTS="csv_results/Q_JIT"
PATH_TO_LOCUST_FILE="simulate_sessions.py"
HOST="http://localhost:9966"

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
echo "--------------------------------------------------------------------"
echo "--------------------------------------------------------------------"
echo "Running the script with: minutes=${time}, users=${users}, db=${db}"
echo "--------------------------------------------------------------------"
echo "--------------------------------------------------------------------"

# Run the app and the test
for i in $(seq 1 3); do
  echo "Iteration number: ${i}"

  echo "Running quarkus server with databse ${db}"

  cd ./quarkus-petclinic/ && ./mvnw clean compile package quarkus:run -Ddb=${db} >/dev/null 2>&1 &
  MAVEN_PID=$!

  sleep 20 # Give enough time for the app to set up

  # Get the current date and time in a format suitable for filenames
  current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')
  current_date=$(date '+%Y_%m_%d')

  # Define the directory where the results will be saved
  iteration_dir="locust/${PATH_TO_RESULTS}/u${users}_db${db}_t${time}${current_date}/${i}"

  echo "Creating directory: ${iteration_dir}"
  mkdir -p "${iteration_dir}"

  echo "Current directory: $(pwd)"
  echo "activating perf"
  # perf analysis
  perf stat -e cycles,instructions,cache-references,cache-misses,branch-instructions,branch-misses -a -o ${iteration_dir}/perf.txt &

  sleep 1
  if [ -f "${iteration_dir}/perf.txt" ]; then
    echo "Perf data file created."
  else
    echo "Perf data file not created."
  fi

  PERF_PID=$! # Save the perf process PID

  # Run the locust command with the date in the CSV filename
  echo "--------------------------------------------------------------------"
  echo "Running locust load test ..."
  cd ./locust/
  locust --headless -u "${users}" -t "${time}s" --host "${HOST}" --csv "${PATH_TO_RESULTS}/u${users}_db${db}_t${time}${current_date}/${i}/${current_time}" -f "${PATH_TO_LOCUST_FILE}" >/dev/null 2>&1
  cd ..

  echo "--------------------------------------------------------------------"
  echo "Load Test Finished"

  PORT=9966

  # Trova il PID del processo che usa la porta
  PID=$(lsof -t -i :"$PORT")

  if [ -z "$PID" ]; then
    echo "Nessun processo trovato sulla porta $PORT."
    exit 0
  fi

  # Termina il processo
  echo "Trovato processo sulla porta $PORT con PID: $PID. Terminazione in corso..."
  kill -9 "$PID"

  kill -INT $PERF_PID
  wait $PERF_PID # Ensure that perf has terminated

  echo "Iteration ${i} Finished"
  sleep 10
done
