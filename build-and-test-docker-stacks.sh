#! /bin/bash -e

. ./set-env.sh

# Eventuate SaaS Version

./gradlew assemble

docker-compose build

docker stack deploy -c docker-stack.yml customers-and-orders

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

docker stack rm customers-and-orders


# Eventuate Local version


./gradlew assemble -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local.yml build

docker stack deploy -c docker-stack-eventuate-local.yml customers-and-orders

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 8083

./gradlew -a $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2e-test:cleanTest :e2e-test:test -P ignoreE2EFailures=false

docker stack rm customers-and-orders
