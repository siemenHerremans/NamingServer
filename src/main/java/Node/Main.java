package Node;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("------Running node------");

        String address = "228.5.6.7";
        int port = 6789;
        Node node = new Node(address, port);

        MulticastListener listener = new MulticastListener(address, port, node);
        UDPListener udpListener = new UDPListener(7890, node);
        new Thread(listener).start();

        node.bootstrap();
//        new Thread(udpListener).start();

        while(true){
            String scan = new Scanner(System.in).nextLine();

            if(scan.equals("stop"))
                break;
//            else
//                node.testUni(scan);
        }

        udpListener.halt();
        listener.stop();

    }
}
