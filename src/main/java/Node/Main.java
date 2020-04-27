package Node;

public class Main {
    public static void main(String[] args) {
        System.out.println("------Running node------");

        String address = "228.5.6.7";
        int port = 6789;
        Node node = new Node(address, port);

        MulticastListener listener = new MulticastListener(address, port, node);
        new Thread(listener).start();

        node.bootstrap();

        listener.stop();

    }
}
