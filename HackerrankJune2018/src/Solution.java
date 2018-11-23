import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
class Graph{
	HashMap<Integer,HashSet<Integer>> graph;
	int[] a;
	Graph(int[] a){
		this.a=a;
		graph=new HashMap<Integer,HashSet<Integer>>();
		initNodes();
	}
	private void initNodes() {
		for(int i=1;i<a.length;i++) {
			graph.put(i,new HashSet<Integer>());
		}
	}
	void addEdge(int node1,int node2) {
		graph.get(node1).add(node2);
		graph.get(node2).add(node1);
	}
	void dump(){
		for(int node:graph.keySet()) {
			System.out.println(node+":"+graph.get(node));
		}
	}
	int DFS(int u,int d,int k){
		HashMap<Integer,Integer> levels=new HashMap<Integer,Integer>();
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.add(u);
		levels.put(u,0);
		while(!queue.isEmpty()) {
			int next=queue.remove();
			int level=levels.get(next);
			if (level==d) continue;
			for(int neib:graph.get(next)) {
				if (levels.containsKey(neib)) continue;
				levels.put(neib,level+1);
				queue.add(neib);
			}
		}
		//System.out.println(levels);
		if (levels.size()<k) return -1;
		PriorityQueue<Integer> pq=new PriorityQueue<Integer>();
		for(int key:levels.keySet()) {
			pq.add(a[key]);
		}
		int res=-1;
		for(int i=0;i<k;i++) {
			res=pq.remove();
		}
		return res;	
	}
}
public class Solution {
	int n;
	int[] a;
    // Complete the neighborhoodQueries function below.
	Solution(){

        n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        a = new int[n+1];

        
        for (int i = 1; i <= n; i++) {
            a[i] = scanner.nextInt();
        }
        Graph graph=new Graph(a);
        for(int i=0;i<n-1;i++) {
        	int node1=scanner.nextInt();
        	int node2=scanner.nextInt();
        	graph.addEdge(node1, node2);
        	//System.out.println(node1+" "+node2);
        }
        //graph.dump();
        int q=scanner.nextInt();
        while(q-- > 0) {
        	int u=scanner.nextInt();
        	int d=scanner.nextInt();
        	int k=scanner.nextInt();
        	//System.out.println("u:"+u+" d:"+d+" k:"+k);
        	System.out.println(graph.DFS(u, d, k));
        }
        scanner.close();
		
	}

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
    	Solution sol= new Solution();
    }
}
