#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=postgrespollingcdc

. ./set-env-eventuate-local-postgres-polling.sh

export database=postgres
export mode=polling

./_build-and-test-all.sh $* -P eventuateDriver=local
