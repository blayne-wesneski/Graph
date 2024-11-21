public class GraphPrim {
    public static void main(String[] args) {
        Graph graph = new Graph();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");
        Node g = new Node("G");

        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addNode(e);
        graph.addNode(f);
        graph.addNode(g);

        // edges
        graph.addEdge(a, b, 2);
        graph.addEdge(a, c, 3);
        graph.addEdge(a, d, 3);

        graph.addEdge(b, a, 2);
        graph.addEdge(b, c, 4);
        graph.addEdge(b, e, 3);

        graph.addEdge(c, a, 3);
        graph.addEdge(c, b, 4);
        graph.addEdge(c, e, 1);
        graph.addEdge(c, d, 5);
        graph.addEdge(c, f, 6);

        graph.addEdge(d, a, 3);
        graph.addEdge(d, c, 5);
        graph.addEdge(d, f, 7);

        graph.addEdge(e, b, 3);
        graph.addEdge(e, c, 1);
        graph.addEdge(e, f, 8);

        graph.addEdge(f, d, 7);
        graph.addEdge(f, c, 6);
        graph.addEdge(f, e, 8);
        graph.addEdge(f, g, 9);

        graph.addEdge(g, f, 9);

        // PRIM
        // graph.prim();

        // DIJKSTRA
        graph.dijkstra(a, g);
    }
}
