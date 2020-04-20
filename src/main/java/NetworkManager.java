import java.net.*;
import java.io.IOException;


public class NetworkManager implements Runnable{

    private String groupAddress;
    private int socket;

    public NetworkManager(String groupAddress, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
    }

    @Override
    //Waits for 1 incoming message in separate thread. Then stops.
    public void run() {
        try {

            InetAddress group = null;
            group = InetAddress.getByName(groupAddress);

            MulticastSocket s;
            s = new MulticastSocket(socket);

            s.joinGroup(group);

            // get their responses!
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);

            System.out.println("Test");
            s.receive(recv);

            //Print response
            byte b;
            byte[] bytes = recv.getData();
            for (int i = 0;(b = bytes[i]) != 0 && i <buf.length; i++){
                System.out.println((char)b);
            }
            
            s.leaveGroup(group);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
