#!/bin/bash
#'Bash script for collecting hardware specification data and then insert the data to the psql instance.
#You can assume that hardware specifications are static, so the script will be executed only once.
#Usage:
#./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
#Example:
#./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "password" '

#Assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#This usage info will pop up every time the user provides wrong or missing arguments
usage="Usage: scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password"
#Validate arguments
if [ "$#" -ne 5 ]; then
    echo "Error: illegal number of arguments. Correct usage: $usage"
    exit 1
fi

#Set password to an environment variable
export PGPASSWORD=$psql_password

#Parse host hardware specification
hostname=$(hostname -f)
cpu_number=$(lscpu | awk '/CPU\(s\)/{print $2; exit}')
cpu_architecture=$(lscpu | awk '/^Arch/{print $2; exit}')
cpu_model=$(lscpu | awk '/^Model/{print $2; exit}')
cpu_mhz=$(lscpu | awk '/^CPU MHz:/{print $3; exit}')
L2_cache=$(lscpu | awk '/^L2 cache:/{print $3; exit}')
total_mem=$(cat /proc/meminfo | awk '/^MemTotal:/{print $2 " " $3; exit}')
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#Insert data into PSQL from Bash script
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$L2_cache', '$total_mem', '$timestamp');"

#Execute the insert statement
psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$insert_stmt"