#! /bin/bash

export EVENTUATE_LOCAL=yes

export database=postgres
export mode=polling

export SPRING_PROFILES_ACTIVE=postgres,EventuatePolling

./_build-and-test-all.sh
