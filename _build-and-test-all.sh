#! /bin/bash

set -e

export COMPOSE_HTTP_TIMEOUT=240

docker="./gradlew ${database}${mode}Compose"

if [ -z "$SPRING_DATA_MONGODB_URI" ] ; then
  export SPRING_DATA_MONGODB_URI=mongodb://${DOCKER_HOST_IP:-localhost}/customers_orders
  echo Set SPRING_DATA_MONGODB_URI $SPRING_DATA_MONGODB_URI
fi


if [ "$1" = "--use-existing" ] ; then
  shift;
else
  ${docker}Down
fi

NO_RM=false

if [ "$1" = "--no-rm" ] ; then
  NO_RM=true
  shift
fi

./compile-contracts.sh

./gradlew --stacktrace $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* testClasses
./gradlew --stacktrace $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build -x :e2e-test:test

${docker}Up

#Testing db cli
if [ "${database}" == "mysql" ]; then
  echo 'show databases;' | ./mysql-cli.sh -i
elif [ "${database}" == "postgres" ]; then
  echo '\l' | ./postgres-cli.sh -i
else
  echo "Unknown Database"
  exit 99
fi

#Testing mongo cli
echo 'show dbs' |  ./mongodb-cli.sh -i

./wait-for-services.sh ${DOCKER_HOST_IP:-localhost} 8081 8082 8083

set -e

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

if [ $NO_RM = false ] ; then
  ${docker}Down
fi
