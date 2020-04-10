#! /bin/bash

export EVENTUATE_LOCAL=yes

export database=mysql
export mode=binlog

./_build-and-test-all.sh
