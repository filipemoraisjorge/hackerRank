package org.BreadthFirstSearch;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by filipejorge on 26/05/16.
 */
public class Solution {

    public static class Node implements Comparable<Node> {

        private int nodeName;
        private int distance;
        private Node parent;
        private Set<Node> neighbours = new TreeSet<>();


        public Node(int nodeName) {
            this.nodeName = nodeName;
            this.distance = -1;
        }

        public int getNodeName() {
            return nodeName;
        }

        public void addNeighbour(Node neighbour) {
            this.neighbours.add(neighbour);
        }

        public Set<Node> getNeighbours() {
            return neighbours;
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
                    ", neighbours=" + neighbours.size() +
                    '}';*/
        }

        @Override
        public int compareTo(Node o) {
            return this.nodeName - o.nodeName;
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

            //set neighbours
            for (int i = 0; i < m; i++) {
                int n1 = in.nextInt();
                int n2 = in.nextInt();
                if (!nodes.get(n1).getNeighbours().contains(nodes.get(n2))) {

                    nodes.get(n1).addNeighbour(nodes.get(n2));
                    nodes.get(n2).addNeighbour(nodes.get(n1));

                }
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

    public static void BFS(Graph graph, int startIndex) {

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

            Node current = nodeQueue.poll();

            for (Node node :
                    current.getNeighbours()) {
                if (node.getDistance() == -1) {
                    node.setDistance(current.getDistance() + 6);
                    node.setParent(current);
                    nodeQueue.offer(node);
                }
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);


        sc = new Scanner(new FileReader("tests/bfs/test1/input01.txt"));

        int t = sc.nextInt(); //number of test cases
        for (int i = 0; i < t; i++) {

            Graph graph = new Graph(sc);

            int start = sc.nextInt();

            BFS(graph, start);

            Map<Integer, Node> nodes = graph.getNodes();

            for (int j = 1; j <= nodes.size(); j++) {


                int d = nodes.get(j).getDistance();


                if (d != 0) {
                    System.out.print(d + (j < nodes.size() ? " " : ""));
                }

            }

            if (i > 0) {
                System.out.println();
            }

        }

        sc.close();
    }
}
