package com.company;

import java.util.*;

/**
 * Created by Igor on 08.06.2017.
 */
public class GraphMatrix extends AbstractGraph{
    int[][]incidencyMatrix;

    public GraphMatrix(int numberOfVertices){
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        incidencyMatrix = new int[numberOfVertices][numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                incidencyMatrix[i][j] = 0;
            }
        }
    }

    public GraphMatrix(Object[] objects){
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        for (int i = 0; i < objects.length; i++) {
            vertices.add(new Vertex(objects[i]));
        }
        incidencyMatrix = new int[objects.length][objects.length];
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                incidencyMatrix[i][j] = 0;
            }
        }
    }

    public void addEdge(Object first, Object second){
        Vertex a = findVertex(first);
        Vertex b = findVertex(second);
        if(a == null || b == null) throw new ObjectNotFoundException();
        else {
            edges.add(new Edge(a, b));
            edges.add(new Edge(b, a));
            incidencyMatrix[vertices.indexOf(a)][vertices.indexOf(b)] = 1;
            incidencyMatrix[vertices.indexOf(b)][vertices.indexOf(a)] = 1;
        }
    }

    public void addVertex(Object value){
        Vertex v;
        if(findVertex(value) == null){
            v = new Vertex(value);
            vertices.add(v);
            if(vertices.size() >= incidencyMatrix.length) resize();
            int index = vertices.indexOf(v);
            for (int i = 0; i < incidencyMatrix.length; i++) {
                incidencyMatrix[i][index] = 0;
            }
            for (int i = 0; i < incidencyMatrix.length; i++) {
                incidencyMatrix[index][i] = 0;
            }
        }
        else throw new DuplicateException();
    }

    private void resize(){
        int[][] temp = new int[incidencyMatrix.length + 1][incidencyMatrix.length + 1];
        for (int i = 0; i < incidencyMatrix.length; i++) {
            for (int j = 0; j < incidencyMatrix.length; j++) {
                temp[i][j] = incidencyMatrix[i][j];
            }
        }
        incidencyMatrix = temp;
    }

    public void printIncidencyList(){
        System.out.println("Incidency matrix: ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < incidencyMatrix.length; i++) {
            sb.append("[");
            for (int j = 0; j < incidencyMatrix.length; j++) {
                sb.append(incidencyMatrix[i][j]);
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            sb.append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }

    private Vertex findVertex(Object value){
        for (Vertex v : vertices) {
            if (v.value.equals(value)){
                return v;
            }
        }
        return null;
    }
}