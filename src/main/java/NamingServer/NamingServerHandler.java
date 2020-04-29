package NamingServer;

public class NamingServerHandler extends Thread {

    private String input;
    private NamingServer namingServer;

    public NamingServerHandler(String input, NamingServer namingServer) {
        this.input = input;
        this.namingServer = namingServer;
    }

    public void run() {
        String[] data = input.split("%");

        try {
            namingServer.addNode(data[1], data[0]);
            System.out.println("ip: " + data[0] + " name: " + data[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
