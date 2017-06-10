package com.company;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Igor on 08.06.2017.
 */
public class Graph extends AbstractGraph{
    HashMap<Vertex, LinkedList<Vertex>> incidencyList;
    LinkedBlockingQueue<Vertex> Q = new LinkedBlockingQueue();
    PriorityQueue<Vertex> pQ;
    DisjointSets<Vertex> disjointSets;
    int time;

    public Graph(){
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        incidencyList = new HashMap<>();
    }

    public Graph(Object[] objects){
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        incidencyList = new HashMap<>();
        for (int i = 0; i < objects.length; i++) {
            Vertex v = new Vertex(objects[i]);
            vertices.add(v);
            incidencyList.put(v, new LinkedList<>());
        }
    }

    public void addEdge(Object first, Object second){
        Vertex a = findVertex(first);
        Vertex b = findVertex(second);
        if(a == null || b == null) throw new ObjectNotFoundException();
        else {
            edges.add(new Edge(a, b));
            edges.add(new Edge(b, a));
            LinkedList<Vertex> list = getList(a);
            if(!list.contains(b)) list.add(b);
            list = getList(b);
            if(!list.contains(a)) list.add(a);
        }
    }

    public void addEdge(Object first, Object second, int weight){
        Vertex a = findVertex(first);
        Vertex b = findVertex(second);
        if(a == null || b == null) throw new ObjectNotFoundException();
        else {
            edges.add(new Edge(a, b, weight));
            edges.add(new Edge(b, a, weight));
            LinkedList<Vertex> list = getList(a);
            if(!list.contains(b)) list.add(b);
            list = getList(b);
            if(!list.contains(a)) list.add(a);
        }
    }

    public void addVertex(Object value){
        if(findVertex(value) == null){
            Vertex v = new Vertex(value);
            vertices.add(v);
            incidencyList.put(v, new LinkedList<>());
        }
        else throw new DuplicateException();
    }

    public void printIncidencyList(){
        System.out.println("Incidency list: ");
        Iterator it = incidencyList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    private LinkedList<Vertex> getList(Vertex v){
        LinkedList<Vertex> list = incidencyList.get(v);
        return list;
    }

    public void BFS(Vertex start){
        System.out.println("Breadth first search: ");
        for (Vertex v : vertices) {
            if(!v.equals(start)){
                v.distance = Integer.MAX_VALUE;
                v.color = Colors.White;
                v.predecessor = null;
            }
        }
        start.color = Colors.Grey;
        start.distance = 0;
        start.predecessor = null;
        Q.add(start);
        while(!Q.isEmpty()){
            Vertex u = Q.peek();
            System.out.println(Q);
            for (Vertex v : getList(u)) {
                if(v.color == Colors.White){
                    v.color = Colors.Grey;
                    v.distance = u.distance + 1;
                    v.predecessor = u;
                    Q.add(v);
                }
            }
            Q.poll().color = Colors.Black;
        }
    }

    public void printPath(Vertex s, Vertex v){
        if(s.equals(v)){
            System.out.print("Path: " + s.value + " ");
        }
        else if(v.predecessor == null){
            System.out.println("There's no path from " + s.value + " to " + v.value);
        }
        else{
            printPath(s, v.predecessor);
            System.out.print(v.value + " ");
        }
    }

    public void DFS(){
        for (Vertex u : vertices){
            u.color = Colors.White;
            u.predecessor = null;
        }
        time = 0;
        for (Vertex u : vertices) {
            if(u.color == Colors.White){
                System.out.print(u.value + " ");
                DFSVisit(u);
            }
        }
    }

    private void DFSVisit(Vertex u){
        u.color = Colors.Grey;
        time += 1;
        u.distance = time;
        for (Vertex v : getList(u)){
            if(v.color == Colors.White){
                System.out.print(v.value + " ");
                v.predecessor = u;
                DFSVisit(v);
            }
        }
        u.color = Colors.Black;
        time += 1;
        u.exited = time;
    }

    public Set<Edge> Kruskal(){
        System.out.println("Minimal spanning tree using Kruskal's algorithm: ");
        Set<Edge> edgeSet = new HashSet<>();
        disjointSets = new DisjointSets<>();
        for (Vertex v : vertices) {
            disjointSets.makeSet(v);
        }
        ArrayList<Edge> edgesTemp = (ArrayList<Edge>)edges.clone();
        edgesTemp.sort(new WeightComparator());
        System.out.println(disjointSets.printSets());
        for (Edge e : edgesTemp) {
            System.out.println(e + " " + e.weight);
            if((disjointSets.findSet(e.a)) != (disjointSets.findSet(e.b))){
                edgeSet.add(e);
                disjointSets.union(e.a, e.b);
            }
            System.out.println(disjointSets.printSets());
        }
        return edgeSet;
    }

    public void Dijkstra(Vertex start){
        initializeSingleSource(start);
        Set<Vertex> vertexSet = new HashSet<>();
        pQ = new PriorityQueue<>(new DistanceComparator());
        pQ.addAll(vertices);
        while(!pQ.isEmpty()){
            Vertex u = pQ.poll();
            vertexSet.add(u);
            for (Vertex v : getList(u)) {
                relax(u, v, findWeight(u, v));
            }
        }
    }

    public void dijkstraShortestPath(Object start, Object end){
        Dijkstra(findVertex(start));
        System.out.println("Shortest path from " + start + " to " + end + " using Dijkstra's algorithm: ");
        printPath(findVertex(start), findVertex(end));
    }

    private void initializeSingleSource(Vertex start){
        for (Vertex v : vertices) {
            v.distance = Integer.MAX_VALUE;
            v.predecessor = null;
        }
        start.distance = 0;
    }

    private void relax(Vertex u, Vertex v, int w){
        if(v.distance > u.distance + w){
            v.distance = u.distance + w;
            v.predecessor = u;
        }
    }

    private int findWeight(Vertex x, Vertex y){
        for (Edge e : edges) {
            if ((e.a == x && e.b == y) || (e.a == y && e.b == x)) {
                return e.weight;
            }
        }
        return -1;
    }


    public Vertex findVertex(Object value){
        for (Vertex v : vertices) {
            if (v.value.equals(value)){
                return v;
            }
        }
        return null;
    }
}