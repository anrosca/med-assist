#! /bin/bash

WORKDIR=$(pwd)

#Backend
rm /usr/local/bin/med-assist/med-assist-backend/med-assist.jar
cp /home/ubuntu/med-assist.jar /usr/local/bin/med-assist/med-assist-backend/med-assist.jar

#Frontend
sudo rm -rf /var/www/html/*
cd /var/www/html; sudo tar -xf /home/ubuntu/fe.tar
sudo cp -R /var/www/html/med-assist-client/* /var/www/html/

cd $WORKDIR
