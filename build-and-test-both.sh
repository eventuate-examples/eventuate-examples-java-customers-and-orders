#! /bin/bash

set -e

./gradlew clean

./build-and-test-all.sh

./gradlew clean

./build-and-test-all-eventuate-local.sh
