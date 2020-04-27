package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Node {

    private String groupAddress;
    private int socket;
    private String IP;
    private String name;

    public Node(String groupAddress, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
        this.setHost();
    }

    public void setHost() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            IP = host.getHostAddress();
            name = host.getHostName();

            System.out.println("Ip: " + IP + "\nName: " + name);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getHost() {
        return IP + "%" + name;
    }

    public void bootstrap(){
        sendMsg(getHost());
    }

    private boolean sendMsg(String msg) {
        try {
            // join multicast group
            InetAddress group = null;
            group = InetAddress.getByName(groupAddress);
            MulticastSocket s = new MulticastSocket(socket);
            s.joinGroup(group);

            // build packet and multicast send it
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, socket);
            s.send(packet);

            // leave group
            s.leaveGroup(group);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
