#! /bin/bash -e

export EVENTUATE_COMMON_VERSION=0.9.0.RC5
export EVENTUATE_CDC_VERSION=0.6.0.RC3

./gradlew assemble

docker-compose -f docker-compose-eventuate-local-mysql.yml build
docker-compose -f docker-stack-eventuate-local.yml build

docker stack deploy -c docker-stack-eventuate-local.yml customers-and-orders

./wait-for-services.sh localhost 8081 8082 8083

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

docker stack rm customers-and-orders
