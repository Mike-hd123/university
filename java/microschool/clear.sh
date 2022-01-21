#!/bin/bash
docker-compose down
docker rmi -f $(docker images -qa)
rm -rf */target/*.jar