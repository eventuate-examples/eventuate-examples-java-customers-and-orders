#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=mysqlbinlogcdc

. ./set-env-eventuate-local-mysql.sh

export database=mysql
export mode=binlog

./_build-and-test-all.sh $* -P eventuateDriver=local
