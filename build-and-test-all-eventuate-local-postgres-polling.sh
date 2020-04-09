#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=postgrespollingcdc

export database=postgres
export mode=polling

export SPRING_PROFILES_ACTIVE=postgres

./_build-and-test-all.sh
