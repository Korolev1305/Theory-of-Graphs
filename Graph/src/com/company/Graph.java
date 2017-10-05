package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {
   private ArrayList<Integer> I,J,H,L;
    public Graph(ArrayList<Integer> I, ArrayList<Integer> J){
        this.I=I;
        this.J=J;
        this.L = new ArrayList<Integer>();
        this.H = new ArrayList<Integer>();
        int top = -1;
        for (int i=0;i<I.size();i++)
            if (I.get(i)>top) top = I.get(i);
        for (int i=0;i<=top;i++){
            H.add(-1);
        }
        for (int i=0;i<I.size();i++){
            L.add(-1);
        }
        for (int k=0;k<I.size();k++){
            int i = I.get(k);
            L.set(k,H.get(i));
            H.set(i,k);
        }
    }
    public void add(int i,int j){
            I.add(i);
            J.add(j);
            I.add(j);
            J.add(i);
            int max = Math.max(i,j);
            int size = H.size();
            for (int z=0;z<=max-size;z++) H.add(-1);
            L.add(H.get(i));
            H.set(i,I.size()-2);
            L.add(H.get(j));
            H.set(j,I.size()-1);


    }
    public void print() {
        try (FileWriter writer = new FileWriter("/Users/ewigkeit/Desktop/graph.txt", false)) {
            String graph = "";
            graph += ("{");
            for (int i = 0; i < H.size(); i++) {
                if (H.get(i) == -1) graph += (i + "->" + i + ",");
                for (int k = H.get(i); k != -1; k = L.get(k)) {
                    graph += (I.get(k) + "->" + J.get(k) + ",");
                }
            }
                graph = graph.substring(0, graph.length() - 1);
                graph += ("}");
                writer.write(graph);
                writer.flush();
        }
        catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
                System.out.println(H + "\n" + L + "\n");

    }
    public void deleteEdge(int vertexB,int vertexE)
    {
        int arcB=0;
        int arcE=0;
        for (int k = H.get(vertexB); k != -1; k = L.get(k)) {
            if ((I.get(k) == vertexB) && (J.get(k) == vertexE)) {
                arcB = k;
                if (arcB != I.size() - 1) {
                    if ((I.get(k + 1) == vertexE) && (J.get(k + 1) == vertexB)) {
                        arcE = k + 1;
                    } else arcE = k - 1;

                } else arcE = k - 1;
                break;
            }
        }
        if(arcB!=0&&arcE!=0)
        {
            deleteArc(vertexB, arcB);
            deleteArc(vertexE,arcE);
        }
    }
    public void deleteEdgeByNumber(int arc) {
        int arc2 = 0;
        int vertexB = I.get(arc);
        int vertexE = J.get(arc);
        if (arc != I.size() - 1) {
            if ((I.get(arc + 1) == vertexE) && (J.get(arc + 1) == vertexB)) {
                arc2 = arc + 1;
            }
            else arc2 = arc - 1;

        } else arc2 = arc - 1;

        if (arc2 != 0) {
            deleteArc(vertexB, arc);
            deleteArc(vertexE, arc2);
        }
    }
    public void deleteArc(int vertex,int arc) {
        if (H.get(vertex) == arc)
        {
            int temp = H.get(vertex);
            H.set(vertex,L.get(H.get(vertex)));
            L.set(temp,-1);
        }
        else
            for (int k = H.get(vertex);k!=-1;k=L.get(k))
            {
                if (L.get(k) == arc)
                {
                    int temp = L.get(k);
                    L.set(k,L.get(L.get(k)));
                    L.set(temp,-1);
                }
            }
    }

}
