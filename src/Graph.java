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
            while (!path.isEmpty()) {
                Node n = path.pop();
                pl(n + " (" + n.distance + ")");
            }
        }
    }

    /**
     * A* algorithm - find a shortest path through a graph using a heuristic
     * 
     * @return
     */

    public void aStar(Node start, Node end) {
        // INIT
        unvisitAllNodes();
        resetDistanceData();// g
        resetPrev();
        resetAStar();

        // Lists
        LinkedList<Node> open = new LinkedList<>();
        LinkedList<Node> closed = new LinkedList<>();

        Node curr = start;
        curr.h = manhattanDistance(curr, end);// heuristic calc
        curr.f = 0 + curr.h;// f + h

        boolean done = false;
        while (!done) {
            pl("Current node is " + curr);

            // examine all edges
            for (Edge e : curr.edges) {
                pl("\tExamine edge " + e);

                Node dest = e.end;

                if (!open.contains(dest) && !closed.contains(dest)) {
                    pl("\t\t" + dest + " is not in open or closed. Add to open.");
                    open.add(dest);
                }

                // Calc A* data
                int g = curr.distance + e.weight;
                int h = manhattanDistance(dest, end);// Calc heuristic on the fly
                int f = g + h;
                pl("\t\tg= " + g + ", h= " + h + ", f= " + f);

                pl("\t\tComparing " + dest + " f (" + dest.f + ") to new f (" + f + ")");
                if (f < dest.f) {
                    dest.distance = g;
                    dest.h = h;
                    dest.f = f;
                    dest.prev = curr;
                    pl("\t\tSmaller f calculated... updating " + dest + "; g= " + g + ", h= " + h + ", f= " + f);
                }

                if (dest == end) {
                    done = true;
                    break;
                }
            } // close edge loop

            printAStarTable();
            pl("Open: " + open);
            pl("Closed: " + closed);

            closed.add(curr);

            // update curr
            curr = getNodeWithLowestFOpen(open);

        } // closes 'done' loop

        // TODO: PATH- same as dijkstra's...

    }

    private void printAStarTable() {
        pl("");
        System.out.printf("%-15s%-15s%-15s%-15s%-15s\n", "NAME", "DIST(G)", "H", "F", "PREV");
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            System.out.printf("%-15s", node);
            System.out.printf("%-15s", node.distance);
            System.out.printf("%-15s", node.h);
            System.out.printf("%-15s", node.f);
            System.out.printf("%-15s", node.prev);
            pl("");
        }
    }

    private Node getNodeWithLowestFOpen(LinkedList<Node> list) {
        int min = Integer.MAX_VALUE;
        Node candidate = null;

        for (Node n : list) {
            if (n.f < min) {
                min = n.f;
                candidate = n;
            }
        }

        return candidate;
    }

    private int manhattanDistance(Node from, Node goal) {
        int dx = Math.abs(from.x - goal.x);
        int dy = Math.abs(from.y - goal.y);

        return dx + dy;
    }

    private void resetAStar() {
        for (Node n : nodes) {
            n.distance = 0;
            n.h = 0;
            n.f = Integer.MAX_VALUE;
        }
    }

    private void resetPrev() {
        for (Node n : nodes) {
            n.prev = null;
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
