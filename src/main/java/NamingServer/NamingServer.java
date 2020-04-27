package NamingServer;

import Node.Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class NamingServer {

    private TreeMap<Integer, String> NodeMap;
    private String groupAddress;
    private int port;
    private String ip;
    private String name;
    private int hashLast;

    public NamingServer(String address, int port) {
        this.groupAddress = address;
        this.port = port;
        this.setHost();
    }

    public void setHost() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            ip = host.getHostAddress();
            name = host.getHostName();
            System.out.println("ip: "+ ip +"\nName: "+ name);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addNode(String name, String ip) throws Exception {
        int hashVal = hash(name);
        NodeMap.put(hashVal, ip);
        udpNumberOfNodes();
    }

    public void udpNumberOfNodes() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        String str = Integer.toString(NodeMap.size());
        InetAddress ipDest = InetAddress.getByName(NodeMap.get(hashLast));
        DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ipDest, port);
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
