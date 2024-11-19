import java.util.LinkedList;

public class Graph {
    private LinkedList<Node> nodes;
    private final boolean DEBUG = true;

    public Graph() {
        nodes = new LinkedList<>();
    }

    /**
     * Add a vertext to the graph
     * 
     * @param v
     * @return
     */
    public boolean addNode(Node v) {
        return nodes.add(v);
    }

    /**
     * Add an edge to the graph
     * 
     * @param from
     * @param to
     * @param weight
     * @return
     */
    public boolean addEdge(Node from, Node to, int weight) {
        return from.edges.add(new Edge(from, to, weight));
    }

    public void print() {
        for (Node n : nodes) {
            System.out.print(n + " -> ");
            for (Edge e : n.edges) {
                System.out.println(e);
            }
            System.out.println("");
        }
    }

    public void prim() {
        int mst_weight = 0;

        // make sure all nodes are 'unvisited'/'unprocessed'
        unvisitAllNodes();
        if (DEBUG) {
            System.out.println("Unvisit all nodes");
        }
        // start anywhere
        Node tmp_node = nodes.getFirst(); // not a removal
        tmp_node.visited = true;

        while (!allNodesVisited()) {
            // find smollest edge
            Edge tmp_edge = getSmallestEdgeToUnvisitedNode();
            if (DEBUG) {
                System.out.println("Smallest edge selected: " + tmp_edge);
                System.out.println("Cost so far: " + mst_weight);
            }
            // Add up mst cost
            mst_weight += tmp_edge.weight;

            // Mark the node at the end of edge as visited
            tmp_edge.end.visited = true;
        }

        System.out.println("MST total cost: " + mst_weight);
    }

    private Edge getSmallestEdgeToUnvisitedNode() {
        int min = Integer.MAX_VALUE;
        Edge candidate = null;

        for (Node n : nodes) {
            if (n.visited) {
                for (Edge e : n.edges) {
                    if (e.weight < min && !e.end.visited) {
                        min = e.weight;
                        candidate = e;
                    }
                }
            }
        }

        return candidate;
    }

    private boolean allNodesVisited() {
        for (Node n : nodes) {
            if (!n.visited) {
                return false;
            }
        }
        return true;
    }

    private void unvisitAllNodes() {
        for (Node n : nodes) {
            n.visited = false;
        }
    }
}
