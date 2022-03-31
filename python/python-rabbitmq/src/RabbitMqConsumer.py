from interfaces.AMQPConsumerInterface import AMQPConsumerInterface
import pika


class RabbitMqConsumer(AMQPConsumerInterface):
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

    def getMessage(self, queue_name: str):
        return self.connection.basic_get(queue_name)

    def getMessages(self, queue_name: str):
        return self.connection.consume(queue_name)

    def processMessage(self, process_fn, message) -> any:
        if message is None:
            return

        result = None
        method, properties, body = message
        try:
            result = process_fn(body)
            self.connection.basic_ack(method.delivery_tag)
            print("Acknowledged message: " + str(method.delivery_tag))
        except Exception as e:
            print(e)
            self.connection.basic_nack(method.delivery_tag)
        return result

    def closeConnection(self) -> None:
        self.connection.close()
        print("Connection closed for channel: " + str(self.connection.channel_number))
        self.connection = None
