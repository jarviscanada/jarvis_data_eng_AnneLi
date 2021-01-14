#!/bin/bash

#define the variables for the CLI arguments
command=$1
db_username=$2
db_password=$3

#start docker if docker server is not running
sudo systemctl status docker || systemctl start docker

#validate arguments
case "$command" in
  "create" )
    #check if the container is already created
    if [ $(docker container ls -a -f name=jrvs-psql | wc -1) -eq 2 ]; then
      echo "Error: The docker container jrvs-psql has already been created. Check it's usage below:"
      docker container ls -a -f name=jrvs-psql
      exit 1
    fi
    #check if the `db_username` or `db_password` is passed through CLI arguments
    if [ "$#" -ne 3 ]; then
      echo "Illegal number of parameters. Might be missing db_username and/or db_password"
      exit 1
    fi
    #create `pgdata` volume if it does not exist
    docker volume create pgdata
    #create a psql docker container, name it "jrvs-psql"
    docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    if [ "$?" != 0 ]; then
      echo "Error: Failed to create psql container. Correct usage: ./scripts/psql_docker.sh create [db_username][db_password]"
      exit 1
    fi
    exit 0
  ;;

  "start" )
    if [ $(docker container ls -a -f name=jrvs-psql | wc -1) -ne 2]; then
      echo "Error: Docker container does not exist yet. Correct usage: ./scripts/psql_docker.sh create [db_username][db_password]"
      exit 1
    fi
    docker container start jrvs-psql
    exit $?
  ;;

  "stop" )
    if [ $(docker container ls -a -f name=jrvs-psql | wc -1) -ne 2]; then
      echo "Error: Docker container does not exist yet. Correct usage: ./scripts/psql_docker.sh create [db_username][db_password]"
      exit 1
    fi
    docker container start jrvs-psql
    exit $?
  ;;
  *)
    echo "Error: Invalid input. Correct usage: ./scripts/psql_docker.sh start|stop|create [db_username][db_password]"
    exit 1
  ;;
esac