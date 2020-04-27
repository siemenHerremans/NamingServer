package NamingServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NamingServerListener implements Runnable {

    private String groupAddress;
    private int port;
    private int hashVal = 0;
    private boolean isRunning = true;
    private NamingServer namingServer;

    public NamingServerListener(String groupAddress, int port, NamingServer namingServer) {
        this.groupAddress = groupAddress;
        this.port = port;
        this.namingServer = namingServer;
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                InetAddress group = InetAddress.getByName(groupAddress);
                MulticastSocket s = new MulticastSocket(port);
                s.joinGroup(group);

                byte[] buf = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);

                s.receive(recv);
                s.leaveGroup(group);

                byte b;
                byte[] bytes = recv.getData();
                String input = "";
                for (int i = 0; (b = bytes[i]) != 0 && i < buf.length; i++) {
                    input += (char) b;
                }

                String[] data = input.split("%");

                namingServer.addNode(data[1], data[0]);

                System.out.println("ip: " + data[0] + " name: " + data[1]+" hash: " + hashVal);

            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
    }

    public void stop() {
        System.out.println("thread stopped");
        isRunning = false;
    }

}
