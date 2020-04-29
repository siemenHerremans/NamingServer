package NamingServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListenerServer implements Runnable {

    private int port;
    private boolean isRunning = true;
    private NamingServer namingserver;

    public UDPListenerServer(int port, NamingServer namingserver) {
        this.port = port;
        this.namingserver = namingserver;
    }

    @Override
    public void run() {
        System.out.println("Unicast Listener running");
        try {
            DatagramSocket s = new DatagramSocket(port);

            while (isRunning) {
                byte[] buf = new byte[32768];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);

                String input = new String(recv.getData());
                System.out.println("Unicast: " + input);
                namingserver.udpProcess(input, "");
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("UDP thread stoppped");
    }

    public void halt() {
        System.out.println("UDP thread stoppping");
        isRunning = false;
    }
}
