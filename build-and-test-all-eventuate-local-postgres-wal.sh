#! /bin/bash

export EVENTUATE_LOCAL=yes
export READER=PostgresWalReader

export DATABASE=postgres
export MODE=wal

export SPRING_PROFILES_ACTIVE=postgres,PostgresWal

./_build-and-test-all.sh
