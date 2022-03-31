# go-networking

A project to play around with some networking server/client in golang

Requirements: https://go.dev/ or docker using a golang image

## networking-server

A simple UDP/TCP server to test out the net package.
`go run .` will create a polling server on localhost ports 9000(TCP) and 9001(UDP)

## networking-client

A simple UDP/TCP client that sends a single message on run.
`go run . localhost` will send packets to localhost ports 9000(TCP) and 9001(UDP) and will exit when both goroutines are complete

## Run with docker

1. `docker compose build` Pull and build all of the necessary images and docker artifacts
2. `docker compose up` Start the docker containers and run the test services
3. `docker compose down` Clean up all of the docker artifacts created
