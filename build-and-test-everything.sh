#! /bin/bash

set -e

./gradlew clean

#./build-and-test-all.sh

./gradlew clean

./build-and-test-all-eventuate-local-mysql.sh

./gradlew clean

./build-and-test-all-eventuate-local-postgres-polling.sh

./gradlew clean

./build-and-test-all-eventuate-local-postgres-wal.sh
