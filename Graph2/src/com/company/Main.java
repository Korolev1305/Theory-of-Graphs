package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph(new ArrayList<Integer>(Arrays.asList(0,1,2,1,0)),
                new ArrayList<Integer>(Arrays.asList(1,2,3,3,2)));
        graph.print();
        graph.add(11,10);
        graph.print();
        graph.deleteEdgeByNumber(5);
        graph.print();
    }
}
