package NamingServer;

public class NamingServerHandler extends Thread {

    private String input;
    private NamingServer namingServer;

    public NamingServerHandler(String input, NamingServer namingServer) {
        this.input = input;
        this.namingServer = namingServer;
    }

    public void run() {

        try {
            String[] data = input.split("%");
            namingServer.addNode(data[0].trim(), data[1].trim());
            System.out.println("ip: " + data[0] + " Name: " + data[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
