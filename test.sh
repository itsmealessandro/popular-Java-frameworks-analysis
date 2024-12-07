#!/bin/bash
PATH_TO_RESULTS="csv_results/S_JIT"
PATH_TO_LOCUST_FILE="simulate_sessions.py"
HOST="http://localhost:9966"

echo $(date '+%Y_%m_%d')

iteration_dir="test"
mkdir -p "${iteration_dir}"

# Avvia perf in background
perf stat -e cycles,instructions,cache-references,cache-misses,branch-instructions,branch-misses -a -o ${iteration_dir}/perf.txt &

# Salva il PID di perf
PERF_PID=$!

# Attendi qualche secondo per dare il tempo a perf di scrivere il file
sleep 1

# Controlla se il file è stato creato prima di terminare perf
if [ -f "${iteration_dir}/perf.txt" ]; then
  echo "Perf data file created."
else
  echo "Perf data file not created."
fi

# Termina perf
kill -INT ${PERF_PID}
wait ${PERF_PID}

# Aggiungi un ritardo per assicurarti che i dati vengano scritti nel file
sleep 1

# Riprova a controllare dopo che perf è terminato
if [ -f "${iteration_dir}/perf.txt" ]; then
  echo "Perf data file created."
else
  echo "Perf data file not created."
fi

cat "${iteration_dir}/perf.txt"
