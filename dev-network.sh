#!/bin/bash

set -e

function networkDevUp() {
  docker-compose up -d

  echo "====== Creating topics ====================================================="
  sleep 7
  source dev-manage-topics.sh
  topicsCreate
  echo "============================================================================"
}

function networkDevDown() {
  docker-compose down
  rm -rf car-tracks-producer-db
}


if [ "$1" = "up" ]; then
  networkDevUp
elif [ "$1" = "down" ]; then
  networkDevDown
elif [ "$1" = "reset" ]; then
  networkDevDown
  networkDevUp
else
  echo "No command specified"
  echo "Basic commands are: up, down, reset"
fi
