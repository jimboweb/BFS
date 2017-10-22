import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class BFS {
    private static Queue<Integer> q;
    private static int[] distances;

    
    public static void main(String[] args) {
        //normalRun();
        unitTest();
     }



    private static int distance(ArrayList<Integer>[] adj, int s, int t, Queue<Integer> q, int[] distances) {
        q.add(s);
        int activeNode;
        while(!q.isEmpty()){
            activeNode = q.poll();
            for(int node:adj[activeNode]){
                if(distances[node]==-1 || distances[node]>distances[activeNode] + 1){
                    q.add(node);
                    distances[node] = distances[activeNode] + 1;
                }
                if(node == t){
                    return distances[node];
                }
            }
        }
        return -1;
    }

    private static int dfsDistance(ArrayList<Integer>[] adj, int s, int t, int depth, boolean[] visited){
        if(s == t)
            return depth;
        visited[s] = true;
        ArrayList<Integer> depths = new ArrayList<>();
        try{
        for(int node:adj[s]){
            if(!visited[node]){
                depths.add(dfsDistance(adj, node, t, depth + 1, visited));
            }
        }
        } catch (StackOverflowError err){
            System.out.println("Stack Overflow on:");
            System.out.println("Size = " + adj.length);
            System.out.println("From = " + s);
            System.out.println("To = " + t);
            System.out.println();
        }
        if(!depths.isEmpty())
            depth = Collections.min(depths);
        else
            depth = Integer.MAX_VALUE;
        if(depth < Integer.MAX_VALUE)
            return depth;
        return Integer.MAX_VALUE;
    }
    
    private static void normalRun(){
               
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        q = new PriorityQueue<>();
        distances = new int[n];
        for(int i=0;i<distances.length;i++)
            distances[i] = -1;
        distances[x]=0;
        System.out.println(distance(adj, x, y, q, distances));
        
        
        
        
        
        
        boolean[] visited = new boolean[adj.length];
        for(boolean v:visited)
            v = false;
        int depth = dfsDistance(adj, x, y, 0, visited);
        if(depth == Integer.MAX_VALUE)
            depth = -1;
        System.out.println(depth);

        printArray(adj);

    }
    
    
    
    private static void unitTest(){
        int numberOfTests = 10000;
        int maxVertices = 9996;
        Random rnd = new Random();
        for(int i=0; i < numberOfTests; i++){
            q = new PriorityQueue<>();
            int v = rnd.nextInt(maxVertices)+3;
            boolean[] visited = new boolean[v];
            for(boolean nodeV:visited)
                nodeV = false;
            distances = new int[v];
            for(int j=0;j<distances.length;j++)
                distances[j] = -1;
            int maxEdges = Math.max(triangular(v)-1, 9999);
            int e = rnd.nextInt(maxEdges);
            ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[v];
            for (int j = 0; j < v; j++) {
                adj[j] = new ArrayList<>();
            }
            for(int j = 0; j < e; j++){
                int startVertex = rnd.nextInt(v);
                int endVertex;
                do{
                    endVertex = rnd.nextInt(v);
                } while(endVertex ==    startVertex || 
                                        adj[startVertex].contains(endVertex) || 
                                        adj[endVertex].contains(startVertex));
                adj[startVertex].add(endVertex);
                adj[endVertex].add(startVertex);
            }
            int from = rnd.nextInt(v);
            int to = rnd.nextInt(from);
            distances[from] = 0;
            /*
            System.out.println("Running test using");
            printArray(adj);

            System.out.printf("%d %d%n", v, e);
            for(int j=0; j<adj.length;j++){
                if(!adj[j].isEmpty()){
                    for(int k = 0; k<adj[j].size(); k++){
                        int start = j + 1;
                        int end = adj[j].get(k) + 1;
                        if(start<end)
                            System.out.printf("%d %d%n", start, end);
                    }
                }
            }
            System.out.printf("%d %d%n", from + 1, to + 1);
            */
            int naive = dfsDistance(adj, from, to, 0, visited);
            if(naive == Integer.MAX_VALUE)
                naive = -1;
            int efficient = distance(adj, from, to, q, distances);
            if(naive!=efficient){
                System.out.println("No Match");
                System.out.printf("naive was %d and efficient was %d%n", naive, efficient);
                System.out.println("Running test using");
                printArray(adj);

                System.out.printf("%d %d%n", v, e);
                for(int j=0; j<adj.length;j++){
                    if(!adj[j].isEmpty()){
                        for(int k = 0; k<adj[j].size(); k++){
                            int start = j + 1;
                            int end = adj[j].get(k) + 1;
                            if(start<end)
                                System.out.printf("%d %d%n", start, end);
                        }
                    }
                }
                System.out.printf("%d %d%n", from + 1, to + 1);
            } /*else {
                System.out.println("Match.");
                System.out.printf("naive was %d and efficient was %d%n", naive, efficient);
            }*/


            try{
                System.in.read();
            } catch(IOException err){
                
            
            }
        }
        
    }
    
    private static int triangular(int n){
        int tri = 0;
        for(int i=1; i<n; i++){
            tri = tri + i;
        }
        return tri;
    }

    private static void printArray(ArrayList<Integer>[] adj){
        for(int i=0; i<adj.length; i++){
            System.out.printf("%d: ", i);
            for(int n:adj[i]){
                System.out.printf("%d ", n);
            }
            System.out.println();
        }
    }
}

