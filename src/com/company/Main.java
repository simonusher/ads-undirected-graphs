package com.company;

public class Main {

    public static void main(String[] args) {
        Integer tab[] = {10, 20, 30, 40, 3, 15};
        Graph g = new Graph(tab);
        g.addEdge(10, 20, 1);
        g.addEdge(10, 30, 2);
        g.addEdge(10, 15, 5);
        g.addEdge(10, 3, 6);
        g.addEdge(20, 15, 2);
        g.addEdge(20, 30, 4);
        g.addEdge(30, 40, 1);
        g.addEdge(30, 3, 3);
        g.addEdge(15, 40, 3);
        g.addEdge(15, 3, 2);
        g.addEdge(40, 3, 1);

        g.printIncidencyList();
        g.BFS(g.findVertex(10));
        g.printPath(g.findVertex(10), g.findVertex(40));
        System.out.println();
        g.DFS();
        System.out.println();
        System.out.println();
        System.out.println("MST edges: " + g.Kruskal());
        System.out.println();
        g.dijkstraShortestPath(10, 3);
    }
}
