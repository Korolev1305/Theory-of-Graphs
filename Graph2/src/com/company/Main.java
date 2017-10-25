package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph(new ArrayList<Integer>(Arrays.asList(0,1,2,1,0,3,3,4)),
                new ArrayList<Integer>(Arrays.asList(1,2,3,3,2,4,5,5)),
                new ArrayList<Integer>(Arrays.asList(2,4,3,1,5,2,10,3)));
        graph.print();
        //graph.add(3,4,2);
        //graph.add(3,5,10);
        //graph.add(4,5,3);
        graph.print();
        graph.connectedComponent();
        ArrayList<Integer> P = graph.BFS(1);
        System.out.println();
        System.out.println(P+" ");
        ArrayList<Integer> G = graph.BFS2(1);
        System.out.println();
        graph.Ford(0);
        graph.Deikstra(0);
    }
}
