#! /bin/bash

set -e

if [ -z "$EVENTUATE_LOCAL" ] && [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

if [ -z "$SPRING_DATA_MONGODB_URI" ] ; then
  export SPRING_DATA_MONGODB_URI=mongodb://${DOCKER_HOST_IP?}/customers_orders
  echo Set SPRING_DATA_MONGODB_URI $SPRING_DATA_MONGODB_URI
fi

DOCKER_COMPOSE="docker-compose"

while [ "$1" = "-f" ] ; do
  shift;
  DOCKER_COMPOSE="$DOCKER_COMPOSE -f ${1?}"
  shift
done

if [ "$1" = "--use-existing" ] ; then
  shift;
else
  ${DOCKER_COMPOSE?} down
fi

NO_RM=false

if [ "$1" = "--no-rm" ] ; then
  NO_RM=true
  shift
fi

./compile-contracts.sh


./gradlew --stacktrace $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* testClasses

if [ ! -z "$EXTRA_INFRASTRUCTURE_SERVICES" ]; then
   ${DOCKER_COMPOSE?} up -d $EXTRA_INFRASTRUCTURE_SERVICES
fi

./gradlew --stacktrace $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build -x :e2e-test:test

${DOCKER_COMPOSE?} build

${DOCKER_COMPOSE?} up -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

set -e

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

if [ $NO_RM = false ] ; then
  ${DOCKER_COMPOSE?} down
fi
