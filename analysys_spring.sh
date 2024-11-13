#!/bin/bash
PATH_TO_LOCUST="./locust"
PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulateSessions.py"
PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"
HOST="http://localhost:9966"

# NOTE: input is expected in seconds, will be converted in minutes
time=""  
users=""
db=""

# args processing
while [[ "$#" -gt 0 ]]; do
        case $1 in
        --t)
                time="$2"
                shift
                ;; #save time value
        --u)
                users="$2"
                shift
                ;; # save users value
        --db)
                db="$2"
                shift
                ;;                             #save db                        # save users value
        *) echo "Argomento sconosciuto: $1" ;; # handle uknown arguments
        esac
        shift
done

# args processing
while [[ "$#" -gt 0 ]]; do
        case $1 in
        --t)
                time="$2"
                shift
                ;; #save time value
        --u)
                users="$2"
                shift
                ;; # save users value
        --db)
                db="$2"
                shift
                ;;                             #save db                        # save users value
        *) echo "Argomento sconosciuto: $1" ;; # handle uknown arguments
        esac
        shift
done
# Verifica che i parametri siano stati definiti
if [[ -z "$time" || -z "$users" || -z "$db"]]; then
  echo "Error: --t (time) e --u (users) --db (database) are mandatory."
        exit 1
fi


# explain what arguments the script is running with
echo running the script with: minutes=${time}, users=${users},db=${db}

#run the app and the test
for i in $(seq 1 3); do

        echo "Iteration number: ${i}"
        ./mvnw spring-boot:run -Dspring-boot.run.arguments="--db=${db}" &
        MAVEN_PID=$!

        sleep 10
        # Get the current date and time in a format suitable for filenames
        current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')

        # Run the locust command with the date in the CSV filename
        locust --headless -u ${users} -t ${time}s --host ${HOST} --csv ${PATH_TO_RESULTS}/${i}/csv_results_${current_time} -f ${PATH_TO_LOCUST_FILE}
        # Quando hai finito, termina il processo Maven
        kill $MAVEN_PID

        # Opzionale: aspetta che il processo venga terminato
        wait $MAVEN_PID

        echo "Iteration ${i} Finished"
        sleep 10
done
