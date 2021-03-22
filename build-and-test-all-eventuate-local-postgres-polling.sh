#! /bin/bash

export EVENTUATE_LOCAL=yes
export READER=PostgresPollingReader

export DATABASE=postgres
export MODE=polling

export SPRING_PROFILES_ACTIVE=postgres,EventuatePolling

./_build-and-test-all.sh
