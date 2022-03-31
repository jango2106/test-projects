class AMQPConsumerInterface:
    def __init__(self):
        self.connection = None

    def startConnection(self, connectionUrl) -> None:
        raise NotImplementedError

    def getMessage(self, queue_name: str):
        raise NotImplementedError

    def getMessages(self, queue_name: str):
        raise NotImplementedError

    def processMessage(self, process_fn):
        raise NotImplementedError

    def closeConnection(self) -> None:
        raise NotImplementedError
