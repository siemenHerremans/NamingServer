package NamingServer;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NamingServer {

    private String groupAddress;
    private int socket;
    private String ip;
    private String name;

    public NamingServer(String address, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
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

    private int hash(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            int temp = 32768;
            return no.mod(BigInteger.valueOf(temp)).intValue();
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
