#!/bin/sh

BASE_DIR=`realpath $0 | xargs dirname`
VERSION=${1:-5.15.9-alpine}

CONF=${2:-no}

mkdir -p $BASE_DIR/volumes/activemq
mkdir -p $BASE_DIR/activemq-conf

#
#    -v $BASE_DIR/activemq-conf:/opt/activemq/conf \
#

if [ "$CONF" == "no" ]; then
  docker run -d --rm --name api-tester-amq \
    -p 61616:61616 \
    -p 5672:5672 \
    -p 8161:8161 \
    -v $BASE_DIR/volumes/activemq:/opt/activemq/data \
    rmohr/activemq:$VERSION
else
  docker run --rm --name api-tester-amq \
    -it \
    -v $BASE_DIR/activemq-conf:/mnt/conf \
    -v $BASE_DIR/volumes/activemq:/mnt/data \
    rmohr/activemq:$VERSION \
    /bin/sh
fi
