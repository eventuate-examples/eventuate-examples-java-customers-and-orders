#! /bin/bash

export EVENTUATE_LOCAL=yes
export READER=MySqlReader

export DATABASE=mysql
export MODE=binlog

./_build-and-test-all.sh
