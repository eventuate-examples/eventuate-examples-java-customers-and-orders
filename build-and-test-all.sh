#! /bin/bash

set -e

docker-compose stop
docker-compose rm -v --force

docker-compose up -d mongodb

./gradlew $* build

docker-compose stop
docker-compose rm -v --force



