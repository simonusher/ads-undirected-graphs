package com.company;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Igor on 08.06.2017.
 */
public class AbstractGraph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    enum Colors{White, Grey, Black}

    public void printVertices(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: {");
        for (Vertex v : vertices) {
            sb.append(v.value);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        System.out.println(sb.toString());
    }

    public void printEdges(){
        StringBuilder sb = new StringBuilder();
        sb.append("Edges: {");
        for (Edge e : edges) {
            sb.append("(");
            sb.append(e.a.value);
            sb.append(", ");
            sb.append(e.b.value);
            sb.append(")");
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        System.out.println(sb.toString());
    }

    protected class Vertex{
        Object value;
        int distance;
        int exited;
        Colors color;
        Vertex predecessor;
        public Vertex(Object value){
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    protected class Edge{
        Vertex a;
        Vertex b;
        int weight;
        public Edge(Vertex a, Vertex b){
            this.a = a;
            this.b = b;
        }
        public Edge(Vertex a, Vertex b, int weight){
            this.a = a;
            this.b = b;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + a.value + ", " + b.value + ")";
        }
    }
    protected class WeightComparator implements Comparator{
        public int compare(Object o1, Object o2){
            int x = 0;
            if(((Edge) o1).weight > ((Edge) o2).weight) x = 1;
            else if(((Edge) o1).weight < ((Edge) o2).weight) x = -1;
            return x;
        }
    }
    protected class DistanceComparator implements Comparator{
        public int compare(Object o1, Object o2){
            int x = 0;
            if(((Vertex) o1).distance > ((Vertex) o2).distance) x = 1;
            else if(((Vertex) o1).distance < ((Vertex) o2).distance) x = -1;
            return x;
        }
    }
}

