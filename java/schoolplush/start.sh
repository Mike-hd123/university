#!/bin/bash
./mvnw clean package -Ppro
echo '--------END------'
docker-compose up