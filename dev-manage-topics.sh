#!/bin/bash

set -e

TOOLS_CONTAINER=kafka-fundamentals_tools_1

#min.insync.replicas
function topicsCreate() {
  docker exec -it $TOOLS_CONTAINER kafka-topics --create --bootstrap-server kafka1:9092 --partitions 1 --replication-factor 1 --topic hello

  TRACKS_REPLICATION_FACTOR=1
  docker exec -it $TOOLS_CONTAINER kafka-topics --create --bootstrap-server kafka1:9092 --partitions 1 --replication-factor $TRACKS_REPLICATION_FACTOR --topic car-tracks-raw --config "min.insync.replicas=$TRACKS_REPLICATION_FACTOR"
}

function topicsDelete() {
  docker exec -it $TOOLS_CONTAINER kafka-topics --delete --bootstrap-server kafka1:9092 --topic hello
  docker exec -it $TOOLS_CONTAINER kafka-topics --delete --bootstrap-server kafka1:9092 --topic car-tracks-raw
}

function topicsList() {
  docker exec -it $TOOLS_CONTAINER kafka-topics --bootstrap-server kafka1:9092 --list
}

if [ "$1" = "create" ]; then
  topicsCreate
elif [ "$1" = "delete" ]; then
  topicsDelete
elif [ "$1" = "recreate" ]; then
  topicsDelete
  topicsCreate
elif [ "$1" = "list" ]; then
  topicsList
elif [ "$1" = "show" ]; then
  docker exec -it $TOOLS_CONTAINER kafka-console-consumer --bootstrap-server kafka1:9092 --topic "$2" --property "print.key=true"
elif [ "$1" = "showAll" ]; then
  docker exec -it $TOOLS_CONTAINER kafka-console-consumer --bootstrap-server kafka1:9092 --from-beginning --topic "$2" --property "print.key=true"
elif [ "$1" = "desc" ]; then
  docker exec -it $TOOLS_CONTAINER kafka-topics --bootstrap-server kafka1:9092 --describe --topic "$2"
else
  echo "dev-manage-topics.sh: No command specified"
  echo "   create ==> creates all needed topics"
  echo "   delete ==> deletes topics"
  echo "   recreate ==> delete + create"
  echo "   list ==> lists evry topic"
  echo "   show <topic_name> ==> show topics data only newest"
  echo "   showAll <topic_name> ==> show topics data FROM BEGINNING"
  echo "   desc <topic_name> ==> shows topics details / configuration"
fi


