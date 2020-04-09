#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=postgreswalcdc

export database=postgres
export mode=wal

export SPRING_PROFILES_ACTIVE=postgres

./_build-and-test-all.sh
