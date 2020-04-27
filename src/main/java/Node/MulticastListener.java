package Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MulticastListener implements Runnable {

    private String groupAddress;
    private String name;
    private int port;
    private int hashVal = 0;

    public MulticastListener(String groupAddress, int port) {
        this.groupAddress = groupAddress;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            InetAddress group = InetAddress.getByName(groupAddress);
            MulticastSocket s = new MulticastSocket(port);
            s.joinGroup(group);

            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);

            s.receive(recv);
            System.out.println("Recieving");

            byte b;
            byte[] bytes = recv.getData();
            String[] data = new String[2];
            int j = 0;
            for (int i = 0; (b = bytes[i]) != 0 && i < buf.length; i++) {
                if (b == '%') {
                    j++;
                } else {
                    if(data[j] == null)
                        data[j] = "";
                    data[j] += (char) b;
                }
            }

            System.out.println("ip: " + data[0] + " name: " + data[1]);

            s.leaveGroup(group);

        } catch (IOException e) {
            e.printStackTrace();
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
