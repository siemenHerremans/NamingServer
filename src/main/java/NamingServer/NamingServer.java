package NamingServer;

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
        int hashVal = hash(name);
        NodeMap.put(hashVal, ip);
        System.out.println("ip: " + ip);
        udpNumberOfNodes(ip);
    }

    public void udpNumberOfNodes(String ip) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        String str = Integer.toString(NodeMap.size());
        InetAddress ipDest = InetAddress.getByName(ip);
        DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ipDest, portUDP);
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
