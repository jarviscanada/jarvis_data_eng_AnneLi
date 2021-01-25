# Linux Cluster Monitoring Agent 😎
A monitoring agent that helps you keep track of your machines' hardware resource usage and allocation in real-time.
![Architecture Overview](/assets/LCMA_diagram.png)

## Introduction
This project automates the process of monitoring servers/nodes of linux that are connected through a switch and communicates internally through IPv4 addresses. This simplifies the jobs of infrastructure managers, sys admins or even site reliability engineers who wants to monitor hardware specifications of servers/nodes and resource usages (e.g number of CPU, free memory, cache, etc). Allowing for comparison of current asset to future asset needs based on the information gathered to make informed decisions for enterprise planning. 

The linux cluster monitoring agent (LCMA) is powered by [docker](https://docs.docker.com/) containers under the hood to provision a postgreSQL (psql) database instance to collect information from the host machines. The project contains a bash script that automatically configures the creation of the psql container using docker and two other bash scripts that are installed on the servers as monitoring agents to retrieve relative data about host usage and host information. The data retrieved will then populate the psql database table running in the container.

Note: I use clusters/servers/nodes interchangeably. They mean the same thing.
## Quick Start
```
- Start a psql instance using psql_docker.sh
./scripts/psql_docker.sh create db_username db_password

- Create tables using ddl.sql
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql

- Insert hardware specs data into the db using host_info.sh
scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

- Insert hardware usage data into the db using host_usage.sh
scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

- Crontab setup to automate the collection of the usage statistics every minute
- edit cronjobs
crontab -e 

- add this line to crontab, the usage data will be collected every minute (indicated by * * * * *)
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

- list crontab jobs
crontab -l
```

## Implementations
* A [PostgreSQL](https://www.postgresql.org/) instance is used to persist all the data. The server hosting the database needs the following two scripts:

  * [psql_docker.sh](./scripts/psql_docker.sh) acts as a switch to start/stop the psql instance.
  
  * [ddl.sql](./sql/ddl.sql) automates the database initialization.  

* The bash agent gathers server usage data, and then insert into the psql instance. The agent will be installed on every host/server/node. The agent consists of two bash scripts:

  * [host_info.sh](./scripts/host_info.sh) collects the host hardware info and insert it into the database. It will be run only once at the installation time.

  * [host_usage.sh](./scripts/host_usage.sh) collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the crontab job every minute.
  
* [queries.sql](./sql/queries.sql) contains some pre-written queries that help cluster administrator to manage the cluster better and plan for future recourses.  

## Database Modeling
- Describing the schema of each table 
- The `host_info` table contains information of hardware specifications of the cpu/node
Field | Description
------|------------
id | Unique primary key for the host, auto-incremented
hostname | Content in the second column
cpu_number | Number of cpu cores
cpu_architecture | CPU architecture (x86_64)
cpu_model | Model of CPU "Intel(R) Xeon(R) CPU @ 2.30GHz" 
cpu_mhz | Clock speed of the cpu in MHz
l2_cache |  L2 cache in KB   
total_mem | Total RAM in MB
timestamp | The time when the host_info specifications were taken

- The `host_usage` table contains information about individual cpu/node usage
Field | Description
------|------------
timestamp | The time when the host_usage data were taken 
host_id | Host identifier
memory_free | Free RAM in MB
cpu_idle | % of time the CPU is idle
cpu_kernel | % of time the CPU is running kernel code
disk_io | Number of disks undergoing I/O processes
disk_available | Available space in the disk's root directory in MB

## Test
How did you test your bash scripts and SQL queries? What was the result?

## Improvements
Write at least three things you want to improve 
e.g. 
- handle hardware update 
- blah
- blah
