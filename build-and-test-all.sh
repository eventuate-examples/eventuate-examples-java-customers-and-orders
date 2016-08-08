#! /bin/bash

set -e

docker-compose stop
docker-compose rm -v --force

docker-compose up -d mongodb

if [ -z "$DOCKER_HOST_IP" ] ; then
  if which docker-machine >/dev/null; then
    export DOCKER_HOST_IP=$(docker-machine ip default)
  else
    export DOCKER_HOST_IP=localhost
 fi
 echo set DOCKER_HOST_IP $DOCKER_HOST_IP
fi

if [ -z "$SPRING_DATA_MONGODB_URI" ] ; then
  export SPRING_DATA_MONGODB_URI=mongodb://${DOCKER_HOST_IP?}/customers_orders
  echo Set SPRING_DATA_MONGODB_URI $SPRING_DATA_MONGODB_URI
fi

./gradlew $* build -x :e2e-test:test

if [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

docker-compose up -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

set -e

./gradlew -a $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false


docker-compose stop
docker-compose rm -v --force



