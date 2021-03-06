package NamingServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("----Namingserver----");

        String address = "228.5.6.7";
        int port = 6789;

        NamingServer namingserver = new NamingServer(address, port);

        NamingServerListener listener = new NamingServerListener(address, port, namingserver);
        Thread listenerThread = new Thread(listener);
        UDPListenerServer udpListener = new UDPListenerServer(7895, namingserver);
        Thread udpThread = new Thread(udpListener);

        try {
            listenerThread.join();
            udpThread.join();
        }catch (InterruptedException e){e.printStackTrace();}

    }
}
