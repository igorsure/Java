package test;

import java.util.*;

public class testDFS {
	boolean[] visited;
	HashMap<Integer,HashSet<Integer>> graph;
	testDFS(){
		testArr3();
		testDump();
		createTestGraph1();
		doDFS();
		doBFS();
	}
	void doDFS() {
		visited=new boolean[graph.size()+1];
		System.out.print("DFS: ");
		DFS(1);
		System.out.println("");
	}
	void doBFS() {
		visited=new boolean[graph.size()+1];
		System.out.print("BFS: ");
		BFS(1);
		System.out.println("");
	}
	void DFS(int node) {
		visited[node]=true;
		System.out.print(node+" ");
		for(int neib:graph.get(node)) {
			if (!visited[neib]) DFS(neib);
		}
	}
	void BFS(int node) {
		Queue<Integer> queue=new LinkedList<Integer>();
		visited[node]=true;
		queue.add(node);
		System.out.print(node+" ");
		while(!queue.isEmpty()) {
			int next=queue.remove();
			for(int neib:graph.get(next)) {
				//System.out.print(neib+" "+visited[neib]);
				if (visited[neib]) continue;
				queue.add(neib);
				System.out.print(neib+" ");
				visited[neib]=true;
			}
		}
	}
	void init() {
		graph=new HashMap<Integer,HashSet<Integer>>();
	}
	void addNode(int node) {
		if (graph.containsKey(node)) return;
		graph.put(node, new HashSet<Integer>());
	}
	void addEdge(int node1,int node2) {
		addNode(node1);
		addNode(node2);
		graph.get(node1).add(node2);
		graph.get(node2).add(node1);
	}
	void dumpGraph() {
		for(int node: graph.keySet()) {
			System.out.println(node+" "+graph.get(node));
		}
	}
	void testDump() {
		int[][] arr=new int[10][];
		for(int i=0;i<arr.length;i++) {
			arr[i]=new int[i+1];
			for(int j=0;j<arr[i].length;j++) {
				arr[i][j]=i+j;
			}
		}
		dumpArr2(arr);
	}
	void dumpArr2(int[][] arr) {
		for(int i=0;i<arr.length;i++) {
			dumpArr(arr[i]);
		}
	}
	void dumpArr(int[] arr) {
		System.out.print("[");
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]+" ");
		}
		System.out.println("]");
	}
	void createTestGraph1(){
		init();
		addEdge(1,2);
		addEdge(1,3);
		addEdge(1,3);
		addEdge(2,4);
		addEdge(4,5);
		addEdge(5,6);
		addEdge(4,6);
		dumpGraph();
	}
	void testArr3() {
		int[] h1=new int[10];
		int[] h2=new int[10];
		int[] h3=new int[10];
		for(int i=0;i<10;i++) {
			h1[i]=i;
			h2[i]=2*i;
			h3[i]=3*i;
		}
		//dumpArr(h1);
		//dumpArr(h2);
		//dumpArr(h3);
		dumpArr2(testArrr3(h1,h2,h3));
	}
	int[][] testArrr3(int[] h1,int[] h2,int[] h3) {
		int[][] ar=new int[3][];
		ar[0]=h1;
		ar[1]=h2;
		ar[2]=h3;
		for(int i=0;i<3;i++) {
			ar[i][i]=i*i*i*i;
		}
		return ar;
	}
	public static void main(String[] args) {
		testDFS dfs=new testDFS();
	}	

}
