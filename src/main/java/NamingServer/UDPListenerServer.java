package NamingServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class UDPListenerServer implements Runnable {

    private int port;
    private boolean isRunning = true;
    private NamingServer namingserver;

    private Thread runningThread = null;
    private int connectionAmount = 0;
    public int runningThreads = 0;

    private DatagramSocket s = null;

    public UDPListenerServer(int port, NamingServer namingserver) {
        this.port = port;
        this.namingserver = namingserver;
    }

    @Override
    public void run() {

        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        System.out.println("Unicast Listener running");

        openSocket();
        while (this.isRunning()) {

            System.out.println("New connection, total: " + ++connectionAmount);
            runningThreads++;

            //Wait for new connection
            while (!s.isConnected());

            new Thread(
                    new WorkerRunnableUDP(
                            s, connectionAmount, this, namingserver)
            ).start();

        }
        System.out.println("UDP thread stoppped");
    }

    private synchronized boolean isRunning() {
        return this.isRunning;
    }

    public synchronized void stop() {
        this.isRunning = false;
        this.s.close();
    }

    private void openSocket() {
        try {
            this.s = new DatagramSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.port, e);
        }
    }
}
