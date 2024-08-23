# Multithreaded Key-Value Store with RPC and Paxos

## Overview

The Multithreaded Key-Value Store is a Java-based distributed application designed to provide a reliable and fault-tolerant solution for managing key-value data across multiple nodes. The system uses Remote Procedure Calls (RPC) for communication between clients and servers and employs the Paxos consensus algorithm to ensure consistency in a distributed environment. This project demonstrates the principles of distributed computing, focusing on achieving consistency, fault tolerance, and efficient handling of concurrent requests.

## Technologies Used

- **Java**: The primary programming language used for implementing the application, chosen for its robustness, object-oriented capabilities, and extensive libraries for networking and multithreading.
- **Remote Procedure Call (RPC)**: Enables remote communication between the client and server components, simulating real-world distributed systems.
- **Paxos Consensus Algorithm**: A distributed consensus protocol that ensures agreement on a single value among a group of nodes, providing consistency even in the presence of network failures.
- **Multithreading**: Allows concurrent processing of multiple client requests, enhancing the performance and responsiveness of the server.
- **Java Archive (JAR) Files**: Used for packaging compiled Java classes and associated metadata into a single file, simplifying deployment and execution.

## Features

- **Distributed Key-Value Storage**: Provides a scalable and reliable key-value store that can manage data across multiple distributed nodes.
- **Paxos-Based Consensus**: Ensures that all nodes in the system agree on the order of operations, maintaining consistency even in a distributed environment.
- **Multithreaded Server Operations**: Supports concurrent client connections and operations, improving system throughput and responsiveness.
- **RPC Communication**: Facilitates seamless communication between clients and servers, allowing for dynamic and flexible interactions in a distributed setup.
- **Fault Tolerance and High Availability**: Designed to handle server failures gracefully, ensuring continuous operation and data integrity through the Paxos protocol.
- **Modular and Extensible Design**: The codebase is structured to allow easy extension and modification, supporting future enhancements and scalability.

## Project Structure

The project is organized into several key directories, each containing specific components of the application:

- **Client**: Contains the client-side code responsible for interacting with the server.
- **ProcessRequest**: Manages the processing of client requests on the server side.
- **Server**: Contains the server-side implementation, including classes related to the Paxos protocol and key-value store management.
- **jar**: Contains compiled JAR files for easy execution.
- **out**: Holds compiled classes or outputs generated during the build process.
- **META-INF**: Stores metadata related to the Java archive files (JAR).
- **Documentation and Diagrams**: Provides visual representations and summaries to aid understanding of the system's architecture and operations.

## Steps to Execute

1. **Navigate to the JAR Directory**:
   Go to the `/jar` folder in your terminal. This folder contains the compiled JAR files for both the client and server.

2. **Start the Server**:
   Open a terminal and execute the following command to start the server:

   ```bash
   java -jar Server.jar <port> <remoteObject>
