public class GraphDemo {

    public static void main(String[] args) {
        Graph g = new Graph();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        g.addNode(a);
        g.addNode(b);
        g.addNode(c);

        g.addEdge(a, b, 16);
        g.addEdge(b, c, 3);
        g.addEdge(c, a, Integer.MAX_VALUE);
        g.addEdge(c, b, 6);
        g.addEdge(c, a, 32);
        g.addEdge(a, c, Integer.MAX_VALUE - 1);

        g.print();
    }
}