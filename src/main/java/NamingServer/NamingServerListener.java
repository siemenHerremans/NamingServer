package NamingServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NamingServerListener implements Runnable {

    private String groupAddress;
    private int port;
    private boolean isRunning = true;
    private NamingServer namingServer;

    public NamingServerListener(String groupAddress, int port, NamingServer namingServer) {
        this.groupAddress = groupAddress;
        this.port = port;
        this.namingServer = namingServer;
    }

    @Override
    public void run() {
        try {
            InetAddress group = InetAddress.getByName(groupAddress);
            MulticastSocket s = new MulticastSocket(port);
            s.joinGroup(group);

            while(isRunning) {
                byte[] buf = new byte[1024];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);

                s.receive(recv);

                String input = new String(recv.getData());
                NamingServerHandler handler = new NamingServerHandler(input, namingServer);
                handler.start();
            }
            s.leaveGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
            isRunning = false;
        }
    }
}
