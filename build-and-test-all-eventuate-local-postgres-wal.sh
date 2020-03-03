#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=postgreswalcdc

. ./set-env-eventuate-local-postgres-wal.sh

export database=postgres
export mode=wal

./_build-and-test-all.sh $* -P eventuateDriver=local
