#!/usr/bin/env bash

# pull a fresh maven image
docker pull maven

#build the project
docker run -v `pwd`:/transitivesrc maven /bin/bash -c "env && cd /transitivesrc && mvn clean install"

# create a docker image
docker build -t transitive:demo .
