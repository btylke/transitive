#!/usr/bin/env bash

# build the transitive project and container image
./build_container_image.sh

#pull and run postgres
docker pull postgres
docker run -i -d --network="host" -e POSTGRES_USER=transitive -e POSTGRES_PASSWORD=transitive -e POSTGRES_DB=transitive postgres

# run transitive
docker run -it --network="host" transitive:demo


