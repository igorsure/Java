package TimeSavingAffair;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
	int[][] roads;
	int n,m,k;
	HashMap<Integer,HashSet<Integer>> graph;
	long Dijecstra(){
		PriorityQueue<Long> queue=new  PriorityQueue<Long> ();
		long dists[]=new long[n+1];
		//boolean[] visited= new boolean[n+1];
		for(int i=0;i<=n;i++) {
			dists[i]=Long.MAX_VALUE;
		}
		queue.add((long) 1);
		while(!queue.isEmpty()) {
			long next=queue.remove();
			long dist=next/100000;
			int node=(int) (next%100000);
			dists[node]=Math.min(dist,dists[node]);
			for(int edge:graph.get(node)) {
				long depart=getDepartTime(dist);
				long distNeib=depart+roads[edge][2];
				int neib=getOther(roads[edge],node);
				if (dists[neib]<=distNeib) continue;
				dists[neib]=distNeib;
				queue.add(distNeib*100000+neib);
				//visited[neib]=true;
				
				//if 
			}
		}
		return dists[n];
	}
	private int getOther(int[] is, int node) {
		// TODO Auto-generated method stub
		if (node==is[0]) return is[1];
		else return is[0];
	}
	Solution() throws IOException{

        n = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        k = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        m = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        roads=new int[m][3];
        graph=new HashMap<Integer,HashSet<Integer>>();
        for(int i=0;i<m;i++) {
        	int node1=scanner.nextInt();
        	int node2=scanner.nextInt();
        	int length=scanner.nextInt();
        	roads[i][0]= node1;
        	roads[i][1]= node2;
        	roads[i][2]= length;
        	addEdge(i,node1,node2,length);
        	//System.out.println(i+" "+roads[i][0]+" "+roads[i][1]+" "+roads[i][2]);
        }
        //System.out.println(graph);
        //long result = leastTimeToInterview(n, k, m);
        long result=Dijecstra();
        System.out.println(result);
        //bufferedWriter.write(String.valueOf(result));
        //bufferedWriter.newLine();

        //bufferedWriter.close();

        scanner.close();
	}
	long getDepartTime(long arrive) {
		long odds=arrive/k;
		long rest=arrive%k;
		if (odds%2==0) return arrive;
		if (odds%2==1 && rest==0) return arrive+k;
		
		return arrive+(k-rest);
	}
    private void addEdge(int edge, int node1, int node2, int length) {
    	addNode(node1);
    	addNode(node2);
    	graph.get(node1).add(edge);
    	graph.get(node2).add(edge);
	}
	private void addNode(int node) {
		if (graph.containsKey(node)) return;
		graph.put(node,new HashSet<Integer>());
		
	}
	// Complete the leastTimeToInterview function below.
    long leastTimeToInterview(int n, int k, int m) {
        // Return the least amount of time needed to reach the interview location in seconds.
    	return 0;
    }

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
    	Solution sol=new Solution();
    }
    
}
