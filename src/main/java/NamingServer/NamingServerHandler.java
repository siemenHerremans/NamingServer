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
            System.out.println("ip: "+ data[0] +"\nName: "+ data[1]);
            namingServer.addNode(data[0], data[1]);
            System.out.println("ip: " + data[0] + " name: " + data[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
