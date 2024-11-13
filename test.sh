#!/bin/bash
PATH_TO_LOCUST="./locust"
PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulateSessions.py"
PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"
HOST="http://localhost:9966"
i=1
echo locust --headless -u ${users} -t ${time}s --host ${HOST} --csv ${PATH_TO_RESULTS}/${i}/csv_results_${current_time} -f ${PATH_TO_LOCUST_FILE}
