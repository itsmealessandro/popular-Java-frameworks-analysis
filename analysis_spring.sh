#!/bin/bash

#PATH_TO_LOCUST="locust"
#PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulate_sessions.py"
#PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"

PATH_TO_RESULTS="csv_results/S_JIT"
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

current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')
current_date=$(date '+%Y_%m_%d')
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
  echo " ---------------------- Iteration number: ${i} ---------------------- "

  echo "--------------------------------------------------------------------"
  echo " Starting The application... "
  echo "--------------------------------------------------------------------"

  cd ./spring-petclinic-rest-master/ && ./mvnw spring-boot:start -Dspring-boot.run.arguments="--db=${db}" >/dev/null 2>&1 &
  MAVEN_PID=$!

  echo "--------------------------------------------------------------------"
  echo " sleeping for 20 sec "
  echo "--------------------------------------------------------------------"
  #sleep 20 # Give enough time for the app to set up

  # Get the current date and time in a format suitable for filenames
  current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')
  current_date=$(date '+%Y_%m_%d')

  # Define the directory where the results will be saved
  iteration_dir="${PATH_TO_RESULTS}/u${users}_db${db}_t${time}${current_date}/${i}"

  echo "Creating directory: ${iteration_dir}"
  mkdir -p "${iteration_dir}"

  # Ensure the results directory exists
  mkdir -p "${PATH_TO_RESULTS}/${current_date}/${i}"

  echo "ensure dir perf"
  # Ensure the results directory exists (perf)
  mkdir -p "${PATH_TO_RESULTS}/perf/${current_date}/${i}"
  # perf analysis
  perf stat -e cycles,instructions,cache-references,cache-misses,branch-instructions,branch-misses -a -o locust/${iteration_dir}/perf.txt &

  if [ -f "${PATH_TO_RESULTS}/perf/${current_date}/${i}/perf_data_${current_time}.txt" ]; then
    echo "Perf data file created."
  else
    echo "Perf data file not created."
  fi
  PERF_PID=$! # Save the perf process PID

  # Run the locust command with the date in the CSV filename
  cd ./locust/
  echo path: . $(pwd) . This is the path

  LOCUST_PATH=$(which locust)

  echo "--------------------------------------------------------------------"
  echo "Running locust load test ..."
  $LOCUST_PATH --headless -u "${users}" -t "${time}s" --host "${HOST}" --csv "${PATH_TO_RESULTS}/u${users}_db${db}_t${time}${current_date}/${i}/${current_time}" -f "${PATH_TO_LOCUST_FILE}" >/dev/null 2>&1

  echo "--------------------------------------------------------------------"
  echo "Load Test Finished"
  cd ../spring-petclinic-rest-master

  ./mvnw spring-boot:stop

  kill $PERF_PID
  wait $PERF_PID # Ensure that perf has terminated

  cd ..

  echo " ---------------------- Iteration ${i} Finished ---------------------- "
  sleep 5
done
