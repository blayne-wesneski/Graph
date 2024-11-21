public class GraphDemo {

    public static void main(String[] args) {
        Graph g = new Graph();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        g.addNode(a);
        g.addNode(b);
        g.addNode(c);

        g.addEdge(a, b, 2);
        g.addEdge(b, a, 2);
        g.addEdge(b, c, 3);
        g.addEdge(c, b, 3);
        g.addEdge(c, a, 4);
        g.addEdge(a, c, 4);

        g.print();
    }
}