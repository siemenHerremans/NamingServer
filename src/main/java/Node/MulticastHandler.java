package Node;

public class MulticastHandler extends Thread{
    private String input;
    private Node currentNode;

    public MulticastHandler(String input, Node node){
        this.input = input;
        this.currentNode = node;
    }

    public void run() {
        String[] data = input.split("%");

        System.out.println("ip: " + data[0] + " name: " + data[1]);
        currentNode.process(data[0], data[1]);
    }
}
