from interfaces.AMQPProducerInterface import AMQPProducerInterface
import pika


class RabbitMqProducer(AMQPProducerInterface):
    def __init__(self):
        super().__init__()

    def __del__(self):
        if self.connection is not None:
            self.closeConnection()

    def startConnection(self, connection_url) -> None:
        connection_params = pika.ConnectionParameters(host=connection_url,
                                                      connection_attempts=5, retry_delay=1)
        self.connection = pika.BlockingConnection(connection_params).channel()
        print("Connection created on channel: " + str(self.connection.channel_number))

    def publishMessage(self, exchange, b_body: bytes, routing_key="", properties=None):
        self.connection.basic_publish(exchange, routing_key, b_body, properties, )

    def closeConnection(self) -> None:
        self.connection.close()
        print("Connection closed for channel: " + str(self.connection.channel_number))
        self.connection = None
