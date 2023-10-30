# Java Fake Person RabbitMQ example

1. As written needs a local rabbitmq (username: user, password: password, port: 9191)
2. As written needs a local postgres db (port: 9190)
3. Run the client. Use the POST /fake/person/{number} endpoint to create a few fake people that get added to a message queue
4. Run the worker. On startup it will start polling the reabbitmq messages to process. And will add the names of the fake people to the postgres db
5. Use the GET /fake/person/ endpoint to get a list of all fake people that have been added to the db