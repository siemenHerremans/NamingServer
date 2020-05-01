package NamingServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class WorkerRunnableUDP implements Runnable{

    DatagramSocket s = null;
    int connectionAmount;
    UDPListenerServer server;
    NamingServer namingServer;

    public WorkerRunnableUDP(DatagramSocket socket, int connectionAmount, UDPListenerServer server, NamingServer namingServer) {
        this.s = socket;
        this.connectionAmount = connectionAmount;
        this.server = server;
        this.namingServer = namingServer;
    }

    @Override
    public void run() {

        byte[] buf = new byte[32768];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        try {
            s.receive(recv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = new String(recv.getData());
        System.out.println("Unicast: " + input);
        try {
            namingServer.udpProcess(input, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        s.close();
        System.out.println("Einde worker runnable");
    }


}
