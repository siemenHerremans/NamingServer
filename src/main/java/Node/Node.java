package Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Node {

    private String groupAddress;
    private int socket;
    private String IP;
    private String name;

    private int currentID, nextID = 0, previousID = 0;

    public Node(String groupAddress, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
        this.setHost();
        this.currentID = hash(name);
//        this.nextID = currentID;
//        this.previousID = currentID;
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

    public void process(String ip, String name){
        int nodeHash = hash(name);

        if(currentID < nodeHash && nodeHash < nextID){
            nextID = nodeHash;
            sendUni(this.name, ip);
        }else if(currentID > nodeHash && nodeHash > previousID){
            previousID = nodeHash;
            sendUni(this.name, ip);
        }
    }

    private void sendUni(String msg, String ip){
//        try{
//            return true;
//        } catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }
    }

    private boolean sendMsg(String msg) {
        try {
            // join multicast group
            InetAddress group = InetAddress.getByName(groupAddress);
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

    private int hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            int temp = 32768;
            return no.mod(BigInteger.valueOf(temp)).intValue();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
