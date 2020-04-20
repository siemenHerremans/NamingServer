import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Tester {
    private String groupAddress;
    private int socket;

    public Tester(String groupAddress, int socket) {
        this.groupAddress = groupAddress;
        this.socket = socket;
    }

    public boolean test(){
        try {
            // join a Multicast group and send the group salutations
            String msg = "Hello";
            InetAddress group = null;
            group = InetAddress.getByName(groupAddress);

            MulticastSocket s;
            s = new MulticastSocket(socket);


            s.joinGroup(group);
            DatagramPacket hello = new DatagramPacket(msg.getBytes(), msg.length(),
                    group, socket);
            s.send(hello);

            s.leaveGroup(group);

            return true;


        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
