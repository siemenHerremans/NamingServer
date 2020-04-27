package Node;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("------Running node------");

        String address = "228.5.6.7";
        int port = 6789;
        Node node = new Node(address, port);

        MulticastListener listener = new MulticastListener(address, port);
        new Thread(listener).start();

        node.bootstrap();

        listener.stop();

    }
}
