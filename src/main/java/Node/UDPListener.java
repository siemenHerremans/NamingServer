package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPListener implements Runnable {

    private int port;
    private boolean isRunning = true;
    private Node currentNode;

    public UDPListener(int port, Node currentNode) {
        this.port = port;
        this.currentNode = currentNode;
    }

    @Override
    public void run() {
        System.out.println("Unicast Listener running");
        try {
            DatagramSocket s = new DatagramSocket(port);
            while (isRunning) {
                byte[] buf = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);
                String input = new String(recv.getData());
                System.out.println("Unicast: " + input);
            }
            System.out.println("stopped");
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("UDP thread stopped");
        isRunning = false;
    }
}
