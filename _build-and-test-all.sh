#! /bin/bash

set -e

export COMPOSE_HTTP_TIMEOUT=240

docker="./gradlew ${DATABASE}${MODE}Compose"

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
if [ "${DATABASE}" == "mysql" ]; then
  echo 'show databases;' | ./mysql-cli.sh -i
elif [ "${DATABASE}" == "postgres" ]; then
  echo '\l' | ./postgres-cli.sh -i
else
  echo "Unknown Database"
  exit 99
fi

#Testing mongo cli
echo 'show dbs' |  ./mongodb-cli.sh -i

set -e

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

./wait-for-services.sh ${DOCKER_HOST_IP:-localhost} readers/${READER}/finished 8099

compose="docker-compose -f docker-compose-eventuate-local-${DATABASE}-${MODE}.yml "

$compose stop cdc-service
curl -s https://raw.githubusercontent.com/eventuate-foundation/eventuate-common/master/migration/db-id/migration.sh &> /dev/stdout | bash
$compose start cdc-service

${docker}Up -P envFile=docker-compose-env-files/db-id-gen.env

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

./gradlew -P verifyDbIdMigration=true :migration-tests:cleanTest migration-tests:test

if [ $NO_RM = false ] ; then
  ${docker}Down
fi
