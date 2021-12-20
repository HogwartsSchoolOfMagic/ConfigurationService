call ../env.bat

docker stop %APPLICATION_NAME%
docker rm %APPLICATION_NAME%
docker-compose -f ./postgres-compose.yml up --build -d