#!/bin/bash

#PATH_TO_LOCUST="locust"
#PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulate_sessions.py"
#PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"

PATH_TO_RESULTS="csv_results/Q_JIT"
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
  echo "Iteration number: ${i}"
  cd ./quarkus-petclinic/ && ./mvnw clean compile package quarkus:run -Ddb=${db} &
  MAVEN_PID=$!

  sleep 20 # Give enough time for the app to set up

  # Get the current date and time in a format suitable for filenames
  current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')
  current_date=$(date '+%Y_%m_%d')

  # Ensure the results directory exists
  mkdir -p "${PATH_TO_RESULTS}/${current_date}/${i}"

  # Run the locust command with the date in the CSV filename
  cd ./locust/
  locust --headless -u "${users}" -t "${time}s" --host "${HOST}" --csv "${PATH_TO_RESULTS}/${current_date}/${i}/${current_time}" -f "${PATH_TO_LOCUST_FILE}"
  cd ..

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

  echo "Iteration ${i} Finished"
  sleep 10
done
