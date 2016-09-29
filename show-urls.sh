#! /bin/bash -e

echo The services are running
echo You can visit these URLS

echo http://${DOCKER_HOST_IP?}:8081/swagger-ui.html - Customers command side
echo http://${DOCKER_HOST_IP?}:8083/swagger-ui.html - Orders command side
echo http://${DOCKER_HOST_IP?}:8082/swagger-ui.html - Order History view
