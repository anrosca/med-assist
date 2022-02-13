#! /bin/bash

WORKDIR=$(pwd)

cd /opt/codedeploy-agent/deployment-root/e526a29f-8800-4fbd-8b7d-a1ecc8f77936
export artifactDirName=$(ls -td -- */ | head -n 1)
export artifactDirPath=$(readlink -f $artifactDirName)

#Backend
sudo systemctl stop med-assist
rm /usr/local/bin/med-assist/med-assist-backend/med-assist.jar
cp $artifactDirPath/deployment-archive/med-assist.jar /usr/local/bin/med-assist/med-assist-backend/med-assist.jar

#Frontend
sudo systemctl stop nginx
sudo rm -rf /var/www/html/*
cd /var/www/html; sudo tar -xf $artifactDirPath/deployment-archive/fe.tar
sudo cp -R /var/www/html/med-assist-client/* /var/www/html/

cd $WORKDIR
