# Implementation of Paxos

This project extends the groundwork laid in Project #3 by introducing fault tolerance and consensus mechanisms to a replicated Key-Value
Store Server setup. The primary goal is to enhance system resilience against replica failures and achieve synchronized updates using the 
Paxos algorithm, as elucidated in Lamport's "Paxos made simple" paper. By integrating Paxos roles, including Proposers, Acceptors, and 
Learners, the project delves into the intricacies of implementing the algorithm, understanding the steps required for consensus, and 
addressing nuances related to event ordering. Notably, the architecture accommodates dynamic client requests to replicas while providing 
the option to implement leader election for proposers. Furthermore, a key requirement involves the periodic failure of acceptor threads, 
showcasing Paxos' adeptness at handling replicated server failures.


## Steps to Execute

1. Go to the `/jar` folder and open a Command Line Terminal for both client and server.

2. To start the server, execute the following command in CLI:
   `java -jar Server.jar <port> <remoteObject>`
   Replace `<port>` with the desired port number on which the server will provide remote methods for the client.
   Also, replace `<remoteObject>` with the chosen remote object registry name, through which the client can access the server's methods.
   For example: `java -jar Server.jar 7001 KSS`

3. To initiate the client, execute the following command in CLI:
   `java -jar Client.jar <hostname> <port> <remoteObject>` 
    Replace `<serverAddress>` with the server's IP address or hostname, and `<serverPort>` with the port number on which the server
    exposes remote objects. In this project, the serverPort can be set as `localhost`. 
    For example: `java -jar Client.jar localhost 7001 KSS`

4. The initial step involves pre-populating the key store with values. After executing Client side command, the system will perform 5
   operations including GET, DELETE, and PUT.

5. The client will prompt to input the desired operation along with the Key and Value, as per the chosen operation. If the input is
   incorrect, it will ask to re-enter the data. The server processes the operation and returns a response to the client indicating the
   success of the operation. If "EXIT" is entered as the operation, the client will exit the system.

6. Basic examples for the input:

   `PUT key0 umang` 
   `GET key0` 
   `DELETE key0`

7. Available Client Operation inputs: GET <KEY>, PUT <KEY> <VALUE>, DELETE <KEY>. These inputs are case-sensitive.
   **Output**: The returned value for the given Key is: <VALUE>, Key: <KEY> has been added with Value: <VALUE>, Value for Key: <KEY> has
   been deleted.

