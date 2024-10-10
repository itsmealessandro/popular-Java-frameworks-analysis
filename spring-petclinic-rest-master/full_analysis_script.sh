#!/bin/bash
#
time="" # in minutes
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
        locust --headless -u ${users} -t ${time}s --host http://localhost:9966 --csv ./locust/csv_results/${i}/csv_results_${current_time} -f ./locust/allTests.py
        # Quando hai finito, termina il processo Maven
        kill $MAVEN_PID

        # Opzionale: aspetta che il processo venga terminato
        wait $MAVEN_PID

        echo "Iteration ${i} Finished"
        sleep 10
done
