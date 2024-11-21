import java.util.LinkedList;
import java.util.Stack;

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
            p(n + " -> ");
            for (Edge e : n.edges) {
                System.out.println(e);
            }
            pl("");
        }
    }

    public void prim() {
        int mst_weight = 0;

        // make sure all nodes are 'unvisited'/'unprocessed'
        unvisitAllNodes();
        if (DEBUG) {
            pl("Unvisit all nodes");
        }
        // start anywhere
        Node tmp_node = nodes.getFirst(); // not a removal
        tmp_node.visited = true;

        while (!allNodesVisited()) {
            // find smollest edge
            Edge tmp_edge = getSmallestEdgeToUnvisitedNode();
            if (DEBUG) {
                pl("Smallest edge selected: " + tmp_edge);
                pl("Cost so far: " + mst_weight);
            }
            // Add up mst cost
            mst_weight += tmp_edge.weight;

            // Mark the node at the end of edge as visited
            tmp_edge.end.visited = true;
        }

        pl("MST total cost: " + mst_weight);
    }

    /**
     * Dijkstra's algorithm - get path with minimal cost from start to end
     */
    public void dijkstra(Node start, Node end) {
        // init
        unvisitAllNodes();
        resetDistanceData();

        Node curr = start;
        curr.distance = 0;

        while (curr != null) {
            pl("Current node is " + curr);

            if (curr == end) {
                break;
            }

            // process all the edges
            for (int i = 0; i < curr.edges.size(); i++) {
                Edge edge = curr.edges.get(i);
                pl("\tEdge " + (i + 1) + " " + edge + " visited? " + edge.end.visited);

                if (edge.end.visited) {
                    continue;
                }

                int newDistance = curr.distance + edge.weight;
                pl("\t\t New distance: " + newDistance);

                pl("\t\t New Distance < Edge Distance: " + newDistance + " < " + edge.end.distance + " ?");
                if (newDistance < edge.end.distance) {
                    pl("\t\t Smaller distance found. Updating.");
                    edge.end.distance = newDistance;
                    edge.end.prev = curr;
                }
            } // done proc edges

            curr.visited = true;

            curr = getSmallestDistanceVisitedNode();
            pl("curr is now " + curr);

            Stack<Node> path = new Stack<>();
            curr = end;
            while (curr != null) {
                path.push(curr);
                curr = curr.prev;
            }
            while(!path.isEmpty()) {
                Node n = path.pop();
                pl(n + " (" + n.distance + ")");
            }
        }
    }

    /**
     * Get the node with the smallest distance that has been visited
     * 
     * @return
     */
    private Node getSmallestDistanceVisitedNode() {
        int min = Integer.MAX_VALUE;
        Node candidate = null;

        for (Node n : nodes) {
            // Short-circuit
            if (!n.visited && n.distance < min) {
                min = n.distance;
                candidate = n;
            }
        }

        return candidate;
    }

    /**
     * A function that acts as an alias to System.out.println
     * 
     * @param s - String to be printed
     */
    private void pl(String s) {
        System.out.println(s);
    }

    /**
     * A function that acts as an alias to System.out.print
     * 
     * @param s - String to be printed
     */
    private void p(String s) {
        System.out.print(s);
    }

    private void resetDistanceData() {
        for (Node n : nodes) {
            n.distance = Integer.MAX_VALUE - 1;
        }
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
