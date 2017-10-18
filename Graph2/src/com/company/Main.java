package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph(new ArrayList<Integer>(Arrays.asList(0,1,2,1,0)),
                new ArrayList<Integer>(Arrays.asList(1,2,3,3,2)));
        graph.print();
        graph.add(6,5);
        graph.add(4,5);
        //graph.add(7,8);
        graph.print();
        graph.connectedComponent();
        ArrayList<Integer> P = graph.BFS(1,graph.I,graph.J);
        System.out.println();
        System.out.println(P+" ");
    }
}
