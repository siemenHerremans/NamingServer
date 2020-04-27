public class Main {

    public static void main(String[] args) {

        Thread networkManager = new Thread(new NetworkManager("228.5.6.7", 6789));
        Tester tester = new Tester("228.5.6.7", 6789);
        networkManager.start();
        //tester.test();

    }
}
