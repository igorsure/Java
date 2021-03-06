package test;

import java.util.*;

public class test {
	String graphString="1 2,1 4,3 4,1 3,2 3,2 5,2 6";
	HashMap<Integer,HashSet<Integer>> graph;
	HashSet<Integer> visited;
	void initGraph() {
		graph=new HashMap<Integer,HashSet<Integer>>();
		String[] arr=graphString.split(",");
		for(int i=0;i<arr.length;i++) {
			//String
			String[] ar= arr[i].split(" ");
			addEdge(Integer.parseInt(ar[0]),Integer.parseInt(ar[1]));
		}
		System.out.println(graph);
	}
	void BFS(int start) {
		System.out.print("BFS: "+start+" ");
		Queue<Integer> queue=new LinkedList<Integer>();
		visited=new HashSet<Integer>();
		visited.add(start);
		queue.add(start);
		while(!queue.isEmpty()) {
			int next=queue.remove();
			for(int neib :graph.get(next)){
				if (visited.contains(neib)) continue;
				System.out.print(neib+" ");
				visited.add(neib);
				queue.add(neib);
			}
		}
		System.out.println("");
	}
	void DFS(int node) {
		visited=new HashSet<Integer>();
		System.out.print("DFS "+" ");
		dfs1(node);
		System.out.println("");
	}
	private void dfs1(int node) {
		if (visited.contains(node)) return;
		visited.add(node);
		System.out.print(node+" ");
		for(int neib:graph.get(node)) {
			dfs1(neib);
		}
	}
	private void addEdge(int node1, int node2) {
		addNode(node1);
		addNode(node2);
		graph.get(node1).add(node2);
		graph.get(node2).add(node1);
	}

	private void addNode(int node) {
		if (!graph.containsKey(node)){
			graph.put(node, new HashSet<Integer>());
		}
	}

	test(){
		System.out.println("asdffd");
		initGraph();
		BFS(1);
		BFS(5);
		DFS(1);
		DFS(5);
		testThrow();
	}
	private void testThrow() {
		int x=1000000000;
		int y=x+x+x+x;
		throw new ArithmeticException("numeric overflow:"+y);
		
	}
	public static void main(String[] args) {
		test tst=new test();
	}
}
