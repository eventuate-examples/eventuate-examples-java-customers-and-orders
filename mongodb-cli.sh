#! /bin/bash

docker run --network eventuateexamplesjavacustomersandorders_default --link eventuateexamplesjavacustomersandorders_mongodb_1:mongodb -i -t mongo:3.0.4 /usr/bin/mongo --host mongodb
