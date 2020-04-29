package NamingServer;

import Node.Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class NamingServer {

    private TreeMap<Integer, String> NodeMap = new TreeMap<Integer, String>();
    private String groupAddress;
    private int port;
    private int portUDP = 7890;
    private String ipHost;
    private String nameHost;

    public NamingServer(String address, int port) {
        this.groupAddress = address;
        this.port = port;
        this.setHost();
    }

    public void setHost() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            ipHost = host.getHostAddress();
            nameHost = host.getHostName();
            System.out.println("ip: "+ ipHost +"\nName: "+ nameHost);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addNode(String ip, String name) throws Exception {
        int hashVal = hash(name.trim());
        System.out.println(hashVal + " " + name);
        NodeMap.put(hashVal, ip);
        String message = "$" + (NodeMap.size() - 1) + "%" + ipHost;
        udpProcess(message, ip);
    }

    public void udpProcess(String message, String ip) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        char firstChar = message.charAt(0);
        String sendMsg = "";
        switch (firstChar){
            case '$':
                //String[] data = message.split("#");
                //ip = data[1].trim();
                //sendMsg = data[0].trim();
                sendMsg = message;
                break;
            case '~':
                String msg = message.substring(1);
                String[] data2 = msg.split("%");
                String previousIp = NodeMap.get(Integer.parseInt(data2[0].trim()));
                String nextIp = NodeMap.get(Integer.parseInt(data2[1].trim()));
                int hashVal = hash(data2[2].trim());
                ip = NodeMap.get(hashVal);
                NodeMap.remove(hashVal);
                sendMsg = "~" + previousIp + "%" + nextIp;
                break;
        }
        InetAddress ipDest = InetAddress.getByName(ip);
        DatagramPacket dp = new DatagramPacket(sendMsg.getBytes(), sendMsg.length(), ipDest, portUDP);
        ds.send(dp);
        ds.close();
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
