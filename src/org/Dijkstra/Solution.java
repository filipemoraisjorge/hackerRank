package org.Dijkstra;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by filipejorge on 26/05/16.
 */
public class Solution {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static class Node implements Comparable<Node> {

        private int nodeName;
        private int distance;
        private Node parent;
        private Set<Edge> edges = new TreeSet<>();


        public Node(int nodeName) {
            this.nodeName = nodeName;
            this.distance = -1;
        }

        public int getNodeName() {
            return nodeName;
        }

        public void addEdge(Edge edge) {
            this.edges.add(edge);
        }

        public Set<Edge> getEdges() {
            return edges;
        }

        public Node getParent() {
            return parent;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return nodeName + "";
                    /*"Node{" +
                    "nodeName=" + nodeName +
                    ", distance=" + distance +
                    ", parent=" + parent +
                    ", edges=" + edges.size() +
                    '}';*/
        }

        @Override
        public int compareTo(Node o) {
            return this.nodeName - o.nodeName;
        }


    }

    public static class Edge implements Comparable<Edge> {

        private Node startNode;
        private Node endNode;

        private int length;

        public Edge(Node node1, Node node2, int length) {
            this.startNode = node1;
            this.endNode = node2;
            this.length = length;
        }

        @Override
        public int compareTo(Edge o) {
            return this.startNode.getNodeName() + this.endNode.getNodeName() - o.startNode.getNodeName() + o.endNode.getNodeName();
        }

        public Node getEndNode() {
            return endNode;
        }

        public int getLength() {
            return length;
        }

        @Override
        public String toString() {
            return startNode + " to " + endNode + " len(" + length + ")";
        }
    }


    public static class Graph {

        private Map<Integer, Node> nodes = new HashMap<>();


        /**
         * Read graph from input
         *
         * @param in
         */
        public Graph(Scanner in) {

            int n = in.nextInt(); // number of nodes

            //create nodes
            for (int i = 1; i <= n; i++) {
                this.nodes.put(i, new Node(i));
            }

            int m = in.nextInt(); // number of edges

            //set edges
            for (int i = 0; i < m; i++) {
                int n1 = in.nextInt();
                int n2 = in.nextInt();
                int r = in.nextInt();


                nodes.get(n1).addEdge(new Edge(nodes.get(n1), nodes.get(n2), r));
                nodes.get(n2).addEdge(new Edge(nodes.get(n2), nodes.get(n1), r));
                //  System.err.println("n1 " + nodes.get(n1).getEdges() + " n2 " + nodes.get(n2).getEdges() );


            }


        }

        public Map<Integer, Node> getNodes() {
            return nodes;
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "nodes=" + nodes +
                    '}';
        }

    }

    public static void Djikstra(Graph graph, int startIndex) {

        for (Node n :
                graph.getNodes().values()) {
            n.setDistance(-1);
            n.setParent(null);
        }

        Node start = graph.getNodes().get(startIndex);

        Queue<Node> nodeQueue = new LinkedList<>();

        start.setDistance(0);
        nodeQueue.offer(start);

        while (!nodeQueue.isEmpty()) {
            //System.err.println(nodeQueue);
            Node current = nodeQueue.poll();

            for (Edge edge :
                    current.getEdges()) {
                Node node = edge.getEndNode();
                int newDistance = current.getDistance() + edge.getLength();
                int distance = node.getDistance();
                //first time
                if (distance == -1) {
                    //System.out.printf(ANSI_YELLOW + current.getNodeName() + " " + node.getNodeName() + " first old " + distance + " new " + newDistance + "\n" + ANSI_RESET);
                    node.setDistance(newDistance);
                    node.setParent(current);
                    nodeQueue.offer(node);
                    //System.err.println( current.getNodeName() + " to " + node.getNodeName() + " " + distance + " " + newDistance);
                    //if newDistance is shorter, update
                } else if (distance > newDistance) {
                    //System.out.printf(ANSI_CYAN + current.getNodeName() + " " + node.getNodeName() + " updating old " + distance + " new " + newDistance + "\n" + ANSI_RESET);
                    node.setDistance(newDistance);
                    node.setParent(current);
                    nodeQueue.offer(node);
                }


            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);


        //sc = new Scanner(new FileReader("tests/dijkstra/test1/input01.txt"));
        //Scanner solution = new Scanner(new FileReader("tests/dijkstra/test1/output01.txt"));

        int t = sc.nextInt(); //number of test cases
        for (int i = 0; i < t; i++) {
            Graph graph = new Graph(sc);

            int start = sc.nextInt();

            //System.out.println("###### test" + (i + 1) + "##### " + start);

            Djikstra(graph, start);

            Map<Integer, Node> nodes = graph.getNodes();

            if (i > 0) {
                System.out.println();
            }

            for (int j = 1; j <= nodes.size(); j++) {

                int d = nodes.get(j).getDistance();

                if (d != 0) {
                    //int dSolution = solution.nextInt();
                    //System.out.println(nodes.get(j).getNodeName() + " " + d + " " + dSolution + " " + ((d == dSolution) ? "" : "INCORRECT " + nodes.get(j).getParent() + " " + nodes.get(j).getEdges()));
                    System.out.print(d + (j < nodes.size() ? " " : ""));

                }

            }


        }

        sc.close();
    }
}
