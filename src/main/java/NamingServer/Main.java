package NamingServer;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("----Namingserver----");

        String address = "228.5.6.7";
        int socket = 6789;

        NamingServer namingserver = new NamingServer(address, socket);
    }
}
