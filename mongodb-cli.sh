#! /bin/bash

docker run  $* --network=${PWD##*/}_default --rm  mongo:3.6 sh -c "exec /usr/bin/mongo --host mongodb customers_and_orders"
