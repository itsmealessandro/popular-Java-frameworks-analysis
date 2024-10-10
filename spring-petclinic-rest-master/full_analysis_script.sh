for i in $(seq 1 3); do

        echo "avvio"
        ./mvnw spring-boot:run -Dspring-boot.run.arguments="--db=2" &
        MAVEN_PID=$!

        sleep 20
        # Get the current date and time in a format suitable for filenames
        current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')

        # Run the locust command with the date in the CSV filename
        locust --headless -u 100 -t 10s --host http://localhost:9966 --csv ./locust/csv_results/${i}/csv_results_${current_time} -f ./locust/allTests.py
        # Quando hai finito, termina il processo Maven
        kill $MAVEN_PID

        # Opzionale: aspetta che il processo venga terminato
        wait $MAVEN_PID

        echo "killato il bro"
        sleep 20
done
