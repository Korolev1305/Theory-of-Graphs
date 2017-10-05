package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    public ArrayList<Integer> I,J,H,L,IJ,numComp;
    public Graph(ArrayList<Integer> I, ArrayList<Integer> J){
        this.I=I;
        this.J=J;
        this.L = new ArrayList<Integer>();
        this.H = new ArrayList<Integer>();
        int top = -1;
        IJ = new ArrayList<Integer>();
        for (int i=0;i<I.size()*2;i++)
        {
            IJ.add(0);
        }
        for (int i=0;i<I.size();i++)
        {
            IJ.set(i,I.get(i));
            IJ.set(2*I.size()-1-i,J.get(i));
        }
        for (int i=0;i<I.size();i++)
            if (I.get(i)>top) top = I.get(i);
        for (int i=0;i<=top+1;i++){
            H.add(-1);
        }
        for (int i=0;i<=IJ.size();i++){
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
        IJ.addAll(I.size()-1,new ArrayList<Integer>(Arrays.asList(i,j)));
        int size = H.size();
        int max=Math.max(i,j);
        for (int z=0;z<=max-size;z++) H.add(-1);
        System.out.println(H.size());
        L.add(H.get(i));
        H.set(i,I.size()-1);


    }
    public void print() {
        try (FileWriter writer = new FileWriter("/Users/ewigkeit/Desktop/graph2.txt", false)) {
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
        for (int k = H.get(vertexB); k != -1; k = L.get(k)) {
            if (((I.get(k) == vertexB) && (J.get(k) == vertexE)) || ((I.get(k)== vertexE) && (J.get(k)==vertexB))){
                arcB = k;
                break;
            }
        }
        deleteArc(vertexB, arcB);
    }
    public void deleteEdgeByNumber(int arc) {
            deleteArc(I.get(arc), arc);
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
    public void DFS(int vertex,int currComp,ArrayList<Integer> Hn,ArrayList<Integer> S)
    {
        int k=0;
        int j=0;
        int w=0;

        System.out.println(numComp);
        while(true)
        {
            numComp.set(vertex,currComp);
            for (k=Hn.get(vertex);k!=-1;k=L.get(k))
            {
                j=IJ.get(2*I.size()-1-k);
                if (numComp.get(j)==-1) break;
            }
            if (k!=-1)
            {
                Hn.set(vertex,L.get(k));
                S.set(w,vertex);
                w++;
                vertex=j;
            }
            else
            {
                if (w == 0) break;
                else
                {
                    w--;
                    vertex=S.get(w);
                }
            }
        }
    }
    public void connectedComponent()
    {
        int i;
        ArrayList<Integer> S = new ArrayList<Integer>();
        numComp = new ArrayList<Integer>();
        for (int k=0;k<H.size();k++)
        {
            numComp.add(-1);
            S.add(0);
        }
        ArrayList<Integer> Hn;
        Hn=H;
        int x=-1;
        System.out.println(H);
        for (int i0=0;i0<H.size();i0++)
        {
            if (numComp.get(i0)!=-1) continue;
            x++;
            i=i0;
            DFS(i,x,Hn,S);
        }
        System.out.print(numComp);
    }

}
