#!/bin/bash
MYPATH=$PWD
cd $MYPATH/schoolapp
npm install
npm run build
cd $MYPATH/schoolui
npm install
npm run build
cd $MYPATH/common
./mvnw install
cd $MYPATH/eureka
./mvnw clean package -Ppro
cd $MYPATH/gateway
./mvnw clean package -Ppro
cd $MYPATH/course
./mvnw clean package -Ppro
cd $MYPATH/student
./mvnw clean package -Ppro
cd $MYPATH/teacher
./mvnw clean package -Ppro
cd $MYPATH/user
./mvnw clean package -Ppro
echo '--------END------'
docker-compose up