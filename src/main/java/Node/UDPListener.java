package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
            byte[] buf = new byte[32768];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            while (isRunning) {
                s.receive(recv);
                String input = new String(recv.getData());
                System.out.println("Unicast: " + input);
                buf = new byte[32768];
                recv = new DatagramPacket(buf, buf.length);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("stopped");
    }

    public void halt() {
        System.out.println("UDP thread stopped");
        isRunning = false;
    }
}
