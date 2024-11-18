#!/bin/bash
PATH_TO_LOCUST="./locust"
PATH_TO_LOCUST_FILE="${PATH_TO_LOCUST}/simulateSessions.py"
PATH_TO_RESULTS="${PATH_TO_LOCUST}/csv_results"
HOST="http://localhost:9966"

echo $(date '+%Y_%m_%d')
