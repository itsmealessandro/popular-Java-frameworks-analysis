for i in $(seq 1 3); do
        # Get the current date and time in a format suitable for filenames
        current_time=$(date '+%Y_%m_%d_%Hh%Mm%Ss')

        # Run the locust command with the date in the CSV filename
        locust --headless -u 100 -t 10s --host http://localhost:9966 --csv ./locust/csv_results/${i}/csv_results_${current_time} -f ./locust/allTests.py

done
