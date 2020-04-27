package Node;

public class Main {
    public static void main(String[] args) {
        System.out.println("------Running node------");

        String address = "228.5.6.7";
        int socket = 6789;
        Thread networkmanager = new Thread(new NetworkManager(address, socket));
        Node node = new Node(address, socket);
        
        networkmanager.start();
        node.bootstrap();
    }
}
