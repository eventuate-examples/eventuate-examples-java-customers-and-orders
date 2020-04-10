#! /bin/bash

export EVENTUATE_LOCAL=yes

export database=postgres
export mode=wal

export SPRING_PROFILES_ACTIVE=postgres,PostgresWal

./_build-and-test-all.sh
