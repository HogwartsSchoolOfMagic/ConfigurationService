call ../env.bat

docker stop %APPLICATION_NAME%-postgres
docker rm %APPLICATION_NAME%-postgres
docker-compose -f ./postgres-compose.yml up --build -d