#! /bin/bash

export EVENTUATE_LOCAL=yes
export EXTRA_INFRASTRUCTURE_SERVICES=mysqlbinlogcdc

export database=mysql
export mode=binlog

./_build-and-test-all.sh
