package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    private ArrayList<Integer> I,J,C,H,L,IJ,numComp;
    int[]Bucket,Fw,Bk;
    public Graph(ArrayList<Integer> I, ArrayList<Integer> J,ArrayList<Integer> C){
        this.I=I;
        this.J=J;
        this.C=C;

        this.L = new ArrayList<Integer>();
        this.H = new ArrayList<Integer>();
        int top = -1;
        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(I);
        I.addAll(J);
        J.addAll(temp);
        C.addAll(C);
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
        {
            int max = Math.max(I.get(i),J.get(i));
            if (max>top)
                top = max;
            }
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
        System.out.println(H+"\n"+L+"\n"+IJ+"\n");
    }
    public void add(int i,int j,int c){
        /*if (i>j){
            int temp;
            temp=j;
            j=i;
            i=temp;
        }*/
        I.add(I.size()/2,i);
        J.add(J.size()/2,j);
        C.add(C.size()/2,c);

        int size = H.size();
        int max=Math.max(i,j);
        for (int z=0;z<=max-size;z++)
            H.add(-1);
        L.add(-1);
        L.set(I.size()/2-1,H.get(i));
        H.set(i,I.size()/2-1);
        I.add(j);
        J.add(i);
        C.add(c);
        L.add(H.get(j));
        H.set(j,I.size()-1);
        IJ.addAll(I.size()-1,new ArrayList<Integer>(Arrays.asList(i,j,j,i)));
        System.out.println(H+"\n"+L+"\n");

    }
    public void print() {
        try (FileWriter writer = new FileWriter("/Users/ewigkeit/Desktop/graph2.txt", false)) {
            String graph = "";
            graph += ("{");
            for (int i = 0; i < H.size(); i++) {
                for (int k = H.get(i); k != -1; k = L.get(k)) {
                    graph += ("{"+I.get(k) + "->" + J.get(k) + "," +  "\"" +C.get(k)+"\""+ "}"+",");;
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
    public void DFS(int vertex,int currComp,ArrayList<Integer> Hn,ArrayList<Integer> S,int w)
    {
        int k=0;
        int j=0;


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
        ArrayList<Integer> Hn=new ArrayList<>();
        for (int k=0;k<H.size();k++)
        {
            Hn.add(-1);
            Hn.set(k,H.get(k));
        }
        int w=0;
        int x=-1;
        for (int i0=0;i0<H.size();i0++)
        {
            if (numComp.get(i0)!=-1) continue;
            x++;
            i=i0;
            DFS(i,x,Hn,S,w);
        }
        System.out.print(numComp);
    }
    public ArrayList<Integer> BFS(int vertex)
    {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i=0;i<I.size();i++)
        {
            graph.add(new ArrayList<Integer>());
            for (int j=0;j<I.size();j++)
                graph.get(i).add(-1);
        }
        for (int k=0;k<I.size();k++)
        {
            int i=I.get(k);
            int j=J.get(k);
            graph.get(i).add(j);
            graph.get(j).add(i);
        }
        ArrayList<Integer> rang=new ArrayList<Integer>();
        ArrayList<Integer> P=new ArrayList<Integer>();
        for (int i=0;i<I.size()/2;i++) {
            rang.add(-1);
            P.add(-1);
        }
        ArrayDeque<Integer> q=new ArrayDeque<Integer>();
        q.push(vertex);
        rang.set(vertex,0);
        while(!q.isEmpty()) {
            int from = q.peekFirst();
            q.pop();
            for(int i=0;i<graph.get(from).size();i++)
            {
                int to = graph.get(from).get(i);
                if (to>=0)
                    if((rang.get(to))==-1)
                    {
                        q.push(to);
                        rang.set(to,rang.get(from)+1);
                        P.set(to,from);
                    }
            }
        }
        return P;
    }

    public ArrayList<Integer> BFS2(int vertex){
        ArrayList<Integer> R= new ArrayList<>();
        ArrayList<Integer> P= new ArrayList<>();
        ArrayList<Integer> Q= new ArrayList<>();
        int r;
        int w;
        for (int i=0;i<H.size();i++)
        {
            R.add(Integer.MAX_VALUE);
            P.add(-2);
            Q.add(0);
        }
        R.set(vertex,0);
        P.set(vertex,-1);

        Q.set(0,vertex);
        r=0;
        w=1;
        while(r<w){
            int i=Q.get(r);
            r++;
            for(int k=H.get(i);k!=-1;k=L.get(k))
            {
                int j=J.get(k);
                if(R.get(j)==H.size()){
                    R.set(j,R.get(i)+1);
                    P.set(j,k);
                    Q.set(w,j);
                    w++;
                }
            }
        }
        return P;
    }

    public void Ford(int vertex) {
        int[] R = new int[I.size()];
        int[] P = new int[I.size()];
        for (int i = 0; i < R.length; i++) {
            R[i] = Integer.MAX_VALUE;
        }
        ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
        R[vertex] = 0;
        Q.add(vertex);
        for (int i = 0; i < I.size(); ++i) {
            P[i] = -1;
        }
        while (!Q.isEmpty()) {
            int from = Q.poll();
            for (int i = 0; i < I.size(); i++) {
                if (I.get(i) == from) {
                    int to = J.get(i);
                    if (R[from] + C.get(i) < R[to]) {
                        R[to] = R[from] + C.get(i);
                        Q.add(to);
                        P[to] = i;
                    }
                }

            }
        }
        try (FileWriter writer = new FileWriter("/Users/ewigkeit/Desktop/graphFord.txt", false)) {
            String graph = "";
            graph += ("{");
            for (int i = 0; i < P.length; i++)
            {
                if (P[i]==-1) continue;
                graph += ("{"+I.get(P[i]) + "->" + J.get(P[i]) + "," +  "\"" +C.get(P[i])+"\""+ "}"+",");
            }
            graph = graph.substring(0, graph.length() - 1);
            graph += ("}");
            writer.write(graph);
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public void Deikstra(int vertex)
    {
        int i;
        int[] R = new int[H.size()];
        int[] P = new int[H.size()];
        for (i=0;i<H.size();i++)
        {
            R[i]=Integer.MAX_VALUE;
            P[i]=-2;
        }
        R[vertex]=0;
        P[vertex]=-1;
        int Cm=Integer.MIN_VALUE;
        for (i=0;i<C.size();i++)
        {
            if (Cm<C.get(i)) Cm=C.get(i);
        }
            int M=H.size()*Cm;
        Bucket=new int[M+1];
        for (i=0;i<=M;i++)
        {
            Bucket[i]=-1;
        }
        Fw=new int[H.size()];
        Bk=new int[H.size()];
        for (i=0;i<H.size();i++)
        {
            Bk[i]=-1;
            Fw[i]=-1;
        }
        INSERT(vertex,0);
        for (int b=0;b<=M;b++)
            while ((i=GET(b))!=-1)
                for (int k=H.get(i);k!=-1;k=L.get(k))
                {
                    int j=J.get(k);
                    int rj=R[j];
                    if (R[i]+C.get(k)<rj)
                    {
                        R[j]=R[i]+C.get(k);
                        P[j]=k;
                        if(rj!=Integer.MAX_VALUE)
                            REMOVE(j,rj);
                        INSERT(j,R[j]);
                    }
                }
        try (FileWriter writer = new FileWriter("/Users/ewigkeit/Desktop/Deikstra.txt", false)) {
            String graph = "";
            graph += ("{");
            for (i = 0; i < P.length; i++)
            {
                System.out.print(P[i]+" ");
                if (P[i]==-1) continue;
                graph += ("{"+I.get(P[i]) + "->" + J.get(P[i]) + "," +  "\"" +C.get(P[i])+"\""+ "}"+",");
            }
            graph = graph.substring(0, graph.length() - 1);
            graph += ("}");
            writer.write(graph);
            writer.flush();
            System.out.println();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    private int GET(int k) {
        int i;
        i=Bucket[k];
        if(i!=-1)Bucket[k]=Fw[i];
        return i;
    }

    private void INSERT(int i,int k) {
        int j;
        j=Bucket[k];
        Fw[i]=j;
        if(j!=-1)Bk[j]=i;
        Bucket[k]=i;
    }

    private void REMOVE(int i,int k)
    {
        int fi=Fw[i];
        int bi=Bk[i];
        if(i==Bucket[k])
            Bucket[k]=fi;
        else
        {
            Fw[bi]=fi;
            if(fi!=-1)
                Bk[fi]=bi;
        }
    }

}
