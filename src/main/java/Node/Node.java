package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Node {

    private String groupAddress;
    private int socket;

    public Node(String groupAddress, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
    }

    private boolean sendMsg(String msg) {
        try {
            //String msg = "testMsg";

            InetAddress group = null;
            group = InetAddress.getByName(groupAddress);

            MulticastSocket s = new MulticastSocket(socket);

            s.joinGroup(group);
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(),
                    group, socket);
            s.send(packet);

            s.leaveGroup(group);

            return true;

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}