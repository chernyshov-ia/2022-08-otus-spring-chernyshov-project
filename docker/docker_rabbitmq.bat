docker run -d --name rabbitmq --hostname rabbitmq -e RABBITMQ_DEFAULT_USER=user -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3.9.28-management-alpine