package com.company;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Igor on 02.06.2017.
 */
public class DisjointSets<T> {
    ArrayList<Node<T>> nodeList;

    public DisjointSets() {
        nodeList = new ArrayList<>();
    }

    public void makeSet(T x){
        for (int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i).value.equals(x)) throw new DuplicateException();
        }
        makeSet(new Node<>(x));
    }

    public void makeSet(Node x){
        x.parent = x;
        x.height = 0;
        nodeList.add(x);
    }

    private Node findNode(T x){
        for (Node n : nodeList) {
            if(n.value.equals(x)){
                return n;
            }
        }
        return null;
    }

    public void union(T x, T y){
        union(findNode(x), findNode(y));
    }

    public void union(Node x, Node y){
        link(findSet(x), findSet(y));
    }

    private void link(Node x, Node y){
        if(x.height > y.height){
            y.parent = x;
        }
        else {
            x.parent = y;
            if (x.height == y.height) {
                y.height = y.height + 1;
            }
        }
    }

    public String printSets(){
        ArrayList<Node> nodes = (ArrayList<Node>)nodeList.clone();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        while (!nodes.isEmpty()){
            sb.append("{");
            Node x = findSet(nodes.get(0));
            sb.append(x.value);
            sb.append(", ");
            nodes.remove(x);
            Iterator<Node> it = nodes.iterator();
            while(it.hasNext()){
                Node y = it.next();
                if(findSet(y).equals(x)){
                    sb.append(y.value);
                    sb.append(", ");
                    it.remove();
                }
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}");
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }

    public Node findSet(Node x){
        if(x.parent != x){
            x.parent = findSet(x.parent);
        }
        return x.parent;
    }

    public Node findSet(T t){
        return findSet(findNode(t));
    }
    /**
     * Created by Igor on 02.06.2017.
     */
    private class Node<T>{
        public Node(T value) {
            this.value = value;
        }

        Node parent;
        T value;
        int height;
    }

}
