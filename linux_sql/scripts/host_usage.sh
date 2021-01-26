#!/bin/bash
#This is a bash script for collecting usage information to store in database
#Script usage:
#bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
#Example:
#bash scripts/host_usage.sh localhost 5432 host_agent postgres password

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

#Parse server CPU and memory usage data and assign them to meaningful variables
timestamp=$(date '+%Y-%m-%d %H:%M:%S')
hostname="$(hostname -f)"
memory_free=$(cat /proc/meminfo | awk '/^MemFree/{print $2; exit}')
cpu_idle=$(vmstat | awk '/^ [0-9]/{print $15; exit}')
cpu_kernel=$(vmstat | awk '/^ [0-9]/{print $14; exit}')
disk_io=$(vmstat -d | awk '/^sda/{print $10; exit}')
disk_available=$(df -BM / | awk '/dev/{gsub(/M/, ""); print $4; exit}')

#construct the INSERT statement. (Use a subquery to get id by hostname)
insert_stmt="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES ('$timestamp', (SELECT id FROM host_info WHERE host_info.hostname = '$hostname'), '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available');"

export PGPASSWORD="$psql_password"

#execute the INSERT statement
psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$insert_stmt"
exit $?
