class AMQPProducerInterface:
    def __init__(self):
        self.connection = None

    def startConnection(self, connectionUrl) -> None:
        raise NotImplementedError

    def publishMessage(self, binary):
        raise NotImplementedError

    def closeConnection(self) -> None:
        raise NotImplementedError