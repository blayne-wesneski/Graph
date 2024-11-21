import java.util.LinkedList;

public class Node {
    // BASICS
    public String name;
    public LinkedList<Edge> edges;// Adjancency list

    //PRIMS
    public boolean visited;

    //DIJKSTRA
    public int distance;
    public Node prev;

    public Node(String name) {
        this.name = name;
        edges = new LinkedList<>();
        this.visited = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
