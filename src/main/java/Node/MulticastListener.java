package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastListener implements Runnable {

    private String groupAddress;
    private int port;
    private boolean isRunning = true;
    private Node currentNode;

    public MulticastListener(String groupAddress, int port, Node node) {
        this.groupAddress = groupAddress;
        this.port = port;
        currentNode = node;
    }

    @Override
    public void run() {
        System.out.println("Multicast Listener running");
        while(isRunning) {
            try {
                InetAddress group = InetAddress.getByName(groupAddress);
                MulticastSocket s = new MulticastSocket(port);
                s.joinGroup(group);

                byte[] buf = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);

                s.receive(recv);
                s.leaveGroup(group);

                String input = new String(recv.getData());
                String[] data = input.split("%");



                System.out.println("ip: " + data[0] + " name: " + data[1]);
                currentNode.process(data[0], data[1]);

            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
    }

    public void stop() {
        System.out.println("Multicast thread stopped");
        isRunning = false;
    }
}
