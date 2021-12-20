call ../env.bat

docker stop %APPLICATION_NAME%-kafka
docker stop %APPLICATION_NAME%-zookeper
docker rm %APPLICATION_NAME%-kafka
docker rm %APPLICATION_NAME%-zookeper
docker-compose -f ./docker-compose.yml up -d