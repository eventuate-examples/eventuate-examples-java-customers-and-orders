#! /bin/bash -e

./wait-for-services.sh ${DOCKER_HOST_IP:localhost} 8081 8082 8083

echo The microservices are running
echo You can visit these URLS
echo http://localhost:8081/swagger-ui.html - Customer service
echo http:/localhost:8083/swagger-ui.html - Order Service
echo http://localhost:8082/swagger-ui.html - Order History Service
