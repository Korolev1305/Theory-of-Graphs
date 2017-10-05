package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph(new ArrayList<Integer>(Arrays.asList(0,1,1,2,2,3,1,3,0,2)),
                new ArrayList<Integer>(Arrays.asList(1,0,2,1,3,2,3,1,2,0)));
        graph.print();
        graph.add(11,10);
        graph.print();
        graph.deleteEdgeByNumber(10);
        graph.deleteEdgeByNumber(11);
        graph.print();
    }
}
