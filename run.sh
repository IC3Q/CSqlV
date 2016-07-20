#!/bin/bash

RUN="java -jar build/libs/CSqlV.jar "

if [ $# -eq 0 ];
then
    echo "Type -h to show help or -q \"YOUR QUERY\" to query. "
    exit 0
else

while getopts ":q:h" opt; do
    case $opt in
        h)
          echo "there is h!" >&2
	  $RUN --help
	  exit 0
	  ;;
	q)
	  echo "there is q with $OPTARG" >&2
	  $RUN --query="$OPTARG"
	  exit 0
	  ;;
	\?)
	  echo "there isn't h!" >&2
	  ;;
    esac
done

fi
