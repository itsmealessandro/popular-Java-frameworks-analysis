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
# Verifica che i parametri siano stati definiti
if [[ -z "$time" || -z "$users" || -z "$db"]]; then
  echo "Error: --t (time) e --u (users) --db (database) are mandatory."
        exit 1
fi

echo time:${time}, users:${users} ,db:${db}
