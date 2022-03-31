package main

import (
	"fmt"
	"io"
	"log"
	"net"
	"os"
)

func main() {
	if len(os.Args) != 2 {
		fmt.Fprintf(os.Stderr, "Usage: %s host:port", os.Args[0])
		os.Exit(1)
	}

	tcpDone := make(chan bool, 1)
	go tcp(os.Args[1]+":9000", tcpDone)
	udpDone := make(chan bool, 1)
	go udp(os.Args[1]+":9001", udpDone)
	for {
		if <-tcpDone && <-udpDone {
			break
		}
	}
}

func tcp(address string, exit chan bool) {
	networkType := "tcp"
	connection, err := net.Dial(networkType, address)
	if err != nil {
		log.Fatalf("Failed to establish a %v connection with %v\n", networkType, address)
	}
	defer connection.Close()

	sendMessage(connection, "This is a message to/from a TCP server\n")
	log.Println("Message sent to TCP server")

	message := getMessage(connection)
	log.Println("Response recieved from TCP server")
	log.Println(message)

	exit <- true
}

func udp(address string, exit chan bool) {
	networkType := "udp"
	serverAddr, err := net.ResolveUDPAddr(networkType, address)
	if err != nil {
		log.Fatalf("Failed to create a %v address with %v\n", networkType, address)
	}
	connection, err := net.DialUDP(networkType, nil, serverAddr)
	if err != nil {
		log.Fatalf("Failed to establish a %v connection with %v\n", networkType, address)
	}
	defer connection.Close()

	sendMessage(connection, "This is a message to a UDP server\n")

	exit <- true
}

func sendMessage(conn net.Conn, message string) {
	_, err := conn.Write([]byte(message))
	if err != nil {
		log.Println("Error:", err)
	}
}

func getMessage(conn net.Conn) string {
	buffer := make([]byte, 1024)
	size, err := conn.Read(buffer)
	if err != nil && err != io.EOF {
		log.Println("Error:", err)
	}
	return string(buffer[:size])
}
