import java.util.LinkedList;

public class Node {
    // BASICS
    public String name;
    public LinkedList<Edge> edges;// Adjancency list

    // TODO: more to come

    public Node(String name) {
        this.name = name;
        edges = new LinkedList<>();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
