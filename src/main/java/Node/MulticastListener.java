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
    private int port;
    private int hashVal = 0;
    private boolean isRunning = true;
    private Node currentNode;

    public MulticastListener(String groupAddress, int port, Node node) {
        this.groupAddress = groupAddress;
        this.port = port;
        currentNode = node;
    }

    @Override
    public void run() {

        while(isRunning) {

            try {
                InetAddress group = InetAddress.getByName(groupAddress);
                MulticastSocket s = new MulticastSocket(port);
                s.joinGroup(group);

                byte[] buf = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);

                s.receive(recv);
                s.leaveGroup(group);

                byte b;
                byte[] bytes = recv.getData();
                String[] data = new String[2];
                int j = 0;
                for (int i = 0; (b = bytes[i]) != 0 && i < buf.length; i++) {
                    if (b == '%') {
                        j++;
                    } else {
                        if (data[j] == null)
                            data[j] = "";
                        data[j] += (char) b;
                    }
                }

                hashVal = hash(data[1]);

                System.out.println("ip: " + data[0] + " name: " + data[1]+" hash: " + hashVal);

            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
    }

    public void stop() {
        System.out.println("thread stopped");
        isRunning = false;
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
