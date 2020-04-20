public class Main {

    public static void main(String[] args) {

        NetworkManager networkManager = new NetworkManager("228.5.6.7", 6789);
        Tester tester = new Tester("228.5.6.7", 8081);
        networkManager.run();
        tester.test();

    }
}
