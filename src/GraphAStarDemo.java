import java.util.HashMap;

public class GraphAStarDemo {
    public static void main(String[] args) {
        Graph graph = new Graph();

        final String ALPHA = "ABCDEFGHIJKLMNOP";
        HashMap<String, Node> nodes = new HashMap<>();

        for (int i = 0; i < ALPHA.length(); i++) {
            String name = ALPHA.charAt(i) + "";

            String key = name;
            Node value = new Node(name);

            nodes.put(key, value);
            graph.addNode(value);
        }

        final String[] edges = {
                "AB5",
                "AC5",
                "BC4",
                "BD3",
                "CD7",
                "CH8",
                "CE7",
                "DH11",
                "DK16",
                "DL13",
                "DM14",
                "EH5",
                "EF4",
                "FG9",
                "GN12",
                "HI3",
                "IJ4",
                "JP8",
                "JN3",
                "KN7",
                "KP4",
                "KL5",
                "LO4",
                "LM9",
                "MO5",
                "NP7",
        };

        for (int i = 0; i < edges.length; i++) {
            String origin = edges[i].charAt(0) + "";
            String destination = edges[i].charAt(1) + "";
            int weight = Integer.parseInt(edges[i].substring(2));

            // System.out.println("adding edge- " + origin + " to " + destination + " with
            // weight " + weight);

            graph.addEdge(nodes.get(origin), nodes.get(destination), weight);
            graph.addEdge(nodes.get(destination), nodes.get(origin), weight);
        }

        /*
         * ADD HEURISTIC DATA
         */
        final String[] heuristicData = {
                "A,0,8", // node name, x, y
                "B,2,11",
                "C,3,8",
                "D,4,12",
                "E,4,4",
                "F,1,3",
                "G,6,1",
                "H,7,6",
                "I,9,5",
                "J,12,4",
                "K,12,8",
                "L,12,11",
                "M,12,14",
                "N,14,3",
                "O,15,12",
                "P,16,8",
        };
        for (int i = 0; i < heuristicData.length; i++) {
            String[] tokens = heuristicData[i].split(",");

            Node node = nodes.get(tokens[0]);

            node.x = Integer.parseInt(tokens[1]);
            node.y = Integer.parseInt(tokens[2]);
        }
        graph.aStar(nodes.get("A"), nodes.get("P"));
    }
}
