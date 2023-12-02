package main.project4.RMI.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/**
 * The PaxosServerCreator class is responsible for creating and managing Paxos servers using RMI.
 */

public class PaxosServerCreator {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");

    /**
     * The main method of the PaxosServerCreator program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            int serverNumber = 5;
            try {
                if (args.length != 2) {
                    System.out.println(getCurrentTime() + " - Try: java PaxosServer port remoteObjectName");
                    System.exit(1);
                }

                int portInput = Integer.parseInt(args[0]);
                String remoteObjectName = args[1];

                Server[] servers = new Server[serverNumber];

                // Create and bind servers to the RMI registry
                for (int serverId = 0; serverId < serverNumber; serverId++) {
                    int port = portInput + serverId;

                    LocateRegistry.createRegistry(port);

                    servers[serverId] = new Server(serverId);

                    Registry registry = LocateRegistry.getRegistry(port);
                    registry.rebind(remoteObjectName, servers[serverId]);

                    System.out.println(getCurrentTime() + " - Server " + serverId + " is running at port " + port);
                }

                // Schedule server drops
                scheduler(servers);

                // Connect acceptors and learners to servers
                for (int serverId = 0; serverId < serverNumber; serverId++) {
                    Acceptor[] acceptors = new Acceptor[serverNumber];
                    Learner[] learners = new Learner[serverNumber];
                    for (int i = 0; i < serverNumber; i++) {
                        acceptors[i] = servers[i];
                        learners[i] = servers[i];
                    }
                    servers[serverId].setAcceptors(acceptors);
                    servers[serverId].setLearners(learners);
                }

            } catch (Exception e) {
                System.err.println(getCurrentTime() + " - Server exception: " + e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(getCurrentTime() + " - Exception occurred while processing client with message: " + e.getMessage());
        }
    }

    /**
     * Schedules server drops at regular intervals.
     *
     * @param servers Array of servers.
     */
    private static void scheduler(Server[] servers) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ServerDrop(servers);
            }
        }, 10000, 100000);
    }

    /**
     * Simulates a server going down randomly.
     *
     * @param servers Array of servers.
     */
    private static void ServerDrop(Server[] servers) {
        int id = (int) (Math.random() * servers.length);
        servers[id].setServerDown();
        System.out.println(getCurrentTime() + " - Server " + id + " is going down...!!");
    }

    /**
     * Gets the current time in a formatted string.
     *
     * @return Formatted current time string.
     */
    private static String getCurrentTime() {
        return "<Time: " + dateFormat.format(new Date()) + ">";
    }
}
