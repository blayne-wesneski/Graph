import java.util.LinkedList;

public class Graph {
    private LinkedList<Node> nodes;

    public Graph() {
        nodes = new LinkedList<>();
    }

    /**
     * Add a vertext to the graph
     * @param v
     * @return
     */
    public boolean addNode(Node v) {
        return nodes.add(v);
    }

    /**
     * Add an edge to the graph
     * @param from
     * @param to
     * @param weight
     * @return
     */
    public boolean addEdge(Node from, Node to, int weight){
        return from.edges.add(new Edge(from, to, weight));
    }

    public void print(){
        for(Node n : nodes){
            System.out.print(n + " -> ");
            for(Edge e : n.edges){
                System.out.println(e);
            }
            System.out.println("");
        }
    }
}
