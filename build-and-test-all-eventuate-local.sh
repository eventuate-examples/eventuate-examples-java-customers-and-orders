#! /bin/bash

export EXTRA_INFRASTRUCTURE_SERVICES=cdcservice
export EVENTUATE_LOCAL=yes

. ./set-env-eventuate-local.sh
./_build-and-test-all.sh -f docker-compose-eventuate-local.yml $BUILD_AND_TEST_ALL_EVENTUATE_LOCAL_EXTRA_COMPOSE_ARGS $* -P eventuateDriver=local
