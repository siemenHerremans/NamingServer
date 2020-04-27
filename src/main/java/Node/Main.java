package Node;

public class Main {
    public static void main(String[] args) {
        System.out.println("------Running node------");

        String address = "228.5.6.7";
        int port = 6789;
        Node node = new Node(address, port);

        int size = 4;
        Thread myThreads[] = new Thread[size];
        for (int j = 0; j < size; j++) {
            myThreads[j] = new Thread(new MulticastListener(address, port));
            myThreads[j].start();
        }

//        Thread multi = new Thread(new MulticastListener(address, port));
//        multi.start();

        node.bootstrap();
        node.bootstrap();
    }
}
