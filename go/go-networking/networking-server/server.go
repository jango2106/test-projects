package main

import (
	"io"
	"log"
	"net"
)

func main() {
	go startTcp(":9000")
	go startUdp(":9001")
	for {
		continue
	}
}

func startTcp(address string) {
	networkType := "tcp"
	listener, err := net.Listen(networkType, address)
	handleFatalError(err)
	log.Printf("Started a %v connection on %v\n", networkType, listener.Addr().String())
	for {
		connection, err := listener.Accept()
		if err != nil {
			continue
		}
		go handleTcpClient(connection)
	}
}

func startUdp(address string) {
	networkType := "udp"
	listener, err := net.ListenPacket(networkType, address)
	handleFatalError(err)
	log.Printf("Started a %v connection on %v\n", networkType, listener.LocalAddr())
	for {
		buffer := make([]byte, 1024)
		bufSize, clientAdd, err := listener.ReadFrom(buffer)
		if err != nil && err != io.EOF {
			log.Println("Error occured:", err)
			break
		}
		log.Println("UDP packet recieved from:", clientAdd)
		log.Println(bufSize, string(buffer[:bufSize]))
	}
}

func handleTcpClient(conn net.Conn) {
	defer conn.Close()
	log.Println("Handling a connection from:", conn.RemoteAddr().String())
	buffer := make([]byte, 1024)
	for {
		bufSize, err := conn.Read(buffer[0:])
		if err != nil {
			if err != io.EOF {
				log.Print(err)
			}
			return
		}
		log.Println(bufSize, string(buffer[:bufSize]))
		_, err = conn.Write(buffer[0:bufSize])
		if err != nil {
			log.Println("Error:", err)
			return
		}
	}
}

func handleFatalError(err error) {
	if err != nil {
		log.Fatalf("Fatal error: %s", err.Error())
	}
}
