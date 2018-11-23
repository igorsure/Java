package CargoDelivery;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
class Node implements Comparable<Node>{
	int id;
	int dist;
	int parentId;
	Node(int id){
		this.id=id;
		this.dist=0;
		this.parentId=-1;
	}
	@Override
	public int compareTo(Node node) {
		if (dist<node.dist) return -1;
		if (dist>node.dist) return -1;
		return 0;
	}
	public int hashCode(){
        return id;
    }
     
    public boolean equals(Object obj){
        if (obj instanceof Edge) {
            Node node = (Node) obj;
            return (this.id==node.id);
        } else {
            return false;
        }
    }	
	public String toString() {
		return id+" dist:"+dist+" parentEdge:" +parentId;
	}
}
class Edge implements Comparable<Edge>{
	int node1,node2;
	int brokenness;
	int id;
	int parentId;
	Edge(int id,int node1,int node2){
		this.id=id;
		this.parentId=-1;
		this.node1=node1;
		this.node2=node2;
		this.brokenness=0;
	}
	int other(int node) {
		return node==node1?node2:node1;
	}
	void addBrokenness(int value){
		if (brokenness+value<0) return;
		brokenness+=value;
	}
	@Override
	public int compareTo(Edge edge) {
		if (brokenness<edge.brokenness) return -1;
		if (brokenness>edge.brokenness) return 1;
		return 0;
	}
	public String toString() {
		return id+":"+node1+"-"+node2+" ("+brokenness+")"; 
	}
	public int hashCode(){
        return id;
    }
     
    public boolean equals(Object obj){
        if (obj instanceof Edge) {
            Edge edge = (Edge) obj;
            return (this.id==edge.id);
        } else {
            return false;
        }
    }	
}
class Graph{
	HashMap<Integer,HashSet<Edge>> graph;
	Edge[] edges;
	Node[] nodes;
	int m,n;
	Graph(int m,int n){
		graph=new HashMap<Integer,HashSet<Edge>>();
		edges=new Edge[m];
		nodes=new Node[n];
		this.m=m;
		this.n=n;
		for(int i=0;i<n;i++) {
			nodes[i]=new Node(i);
			//graph.put(key, value)
		}
	}
	void clearParentsAndDists() {
		for(int i=0;i<n;i++) {
			nodes[i].parentId=-1;
			nodes[i].dist=Integer.MAX_VALUE;
		}
		nodes[0].dist=0;
	}
	void clearParents() {
		for(int i=0;i<m;i++) {
			edges[i].parentId=-1;
		}
	}
	void clearBrokenness() {
		for(int i=0;i<m;i++) {
			edges[i].brokenness=0;
		}
		
	}
	void addNode(int node) {
		if (graph.containsKey(node)) return;
		graph.put(node, new HashSet<Edge>());
	}
	void addEdge(int id,int node1,int node2) {
		addNode(node1);
		addNode(node2);
		Edge edge=new Edge(id,node1,node2);
		edges[id]=edge;
		graph.get(node1).add(edge);
		graph.get(node2).add(edge);
	}
	void dump() {
		for(int key:graph.keySet()) {
			System.out.println(key+" "+graph.get(key));
		}
	}
	Edge[] Dijecstra(int start,int finish) {
		ArrayList<Node> res=new ArrayList<Node>();
		Edge[] res1;
		
		clearParentsAndDists();
		PriorityQueue<Node> queue=new PriorityQueue<Node>();
		queue.add(nodes[0]);
		while(!queue.isEmpty()) {
			Node next=queue.remove();
			//System.out.println("Dijecstra next node:"+next);
			for(Edge neibEdge:graph.get(next.id)) {
				Node neib=nodes[neibEdge.other(next.id)];
				int curdist=next.dist+neibEdge.brokenness;
				if (neib.dist<=curdist) continue;
				neib.dist=curdist;
				neib.parentId=neibEdge.id;
				queue.add(neib);
			}
		}
		Node cur=nodes[n-1];
		while(cur.id!=0) {
			res.add(cur);
			cur=nodes[edges[cur.parentId].other(cur.id)];
		}
		res1=new Edge[res.size()];
		for(int i=res.size()-1;i>=0;i--) {
			res1[i]=edges[res.get(i).parentId];
		}
		return  res1;
	}
	void addBrokennessToRoute(Edge[] route) {
		for(int i=0;i<route.length;i++) {
			if (route[i]==null) continue;
			//System.out.println("addBrokennessToRoute"+i+" route.length:"+route.length+" "+route[i]);
			route[i].addBrokenness(1);
		}
	}
	int driveOneTruck() {
		int n=graph.size();
		Edge[] route=(Edge[]) Dijecstra(0,n-1);
		addBrokennessToRoute(route);
		return route.length;
	}
	void fullRoadsRepair(int t) {
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>(100, Collections.reverseOrder());
		for(int i=0;i<m;i++) {
			queue.add(edges[i]);
		}
		for(int i=0;i<t;i++) {
			Edge edge=queue.remove();
			edge.addBrokenness(-1);
			queue.add(edge);
		}
	}
	int mainRoadsRepair(int t) {
		int curT=t;
		for(int i=0;i<m;i++) {
			if (curT<2) break;
			int brok=edges[i].brokenness;
			if (brok==1) continue;
			if (brok<curT) {
				edges[i].addBrokenness(-brok);
				curT-=brok;
			} 
			else {
				edges[i].addBrokenness(-curT);
				return 0;
			}
		}
		return curT;
	}
	int routeBrokenness(Edge[] route) {
		int res=0;
		for(int i=0;i<route.length;i++) {
			res+=route[i].brokenness;
		}
		return res;
	}
	int routeFinalRepair(int k,int t) {
		this.clearParentsAndDists();
		this.clearBrokenness();
        for(int i=0;i<k-1;i++) {
        	this.driveOneTruck();
        }
        this.fullRoadsRepair(t);
        Edge[] lastRoute=this.Dijecstra(0, n-1);
        int result=this.routeBrokenness(lastRoute);
        return result/2;
	}
	int routeEachDriveRepair(int k,int t) {
		this.clearParentsAndDists();
		this.clearBrokenness();
		
		int curT=t;
        for(int i=0;i<k-1;i++) {
        	int broken=this.driveOneTruck();
        	if (curT>=broken) {
                this.fullRoadsRepair(broken);
                curT-=broken;
        	} else {
                this.fullRoadsRepair(curT);
        		curT=0;
        	}
        }
        if (curT>0) {
            this.fullRoadsRepair(curT);
        }
        Edge[] lastRoute=this.Dijecstra(0, n-1);
        int result=this.routeBrokenness(lastRoute);
        return result/2;
		
	}
	int routeEachDriveMainRepair(int k,int t) {
		this.clearParentsAndDists();
		this.clearBrokenness();
		int curT=t;
        for(int i=0;i<k-1;i++) {
        	//System.out.println("truck:"+i);
        	int broken=this.driveOneTruck();
        	//this.dump();
        	curT=this.mainRoadsRepair(curT);
        	//this.dump();
        }
        if (curT>0) {
            this.fullRoadsRepair(curT);
        }
        Edge[] lastRoute=this.Dijecstra(0, n-1);
        int result=this.routeBrokenness(lastRoute);
        return result/2;
		
	}
}
public class Solution {
	int n,m,k,t;
	Solution(){

        String[] nmkt = scanner.nextLine().split(" ");

        n = Integer.parseInt(nmkt[0]);

        m = Integer.parseInt(nmkt[1]);

        k = Integer.parseInt(nmkt[2]);

        t = Integer.parseInt(nmkt[3]);
        Graph graph=new Graph(m,n);
        for(int i=0;i<m;i++) {
        	int node1=scanner.nextInt();
        	int node2=scanner.nextInt();
        	graph.addEdge(i, node1, node2);
        	//System.out.println(node1+" "+node2);
        }
        //graph.dump();
        int result=graph.routeFinalRepair(k,t);
        int result1=graph.routeEachDriveRepair(k,t);
        int result2=graph.routeEachDriveMainRepair(k,t);
        //routeEachDriveRepair
        System.out.println(result2);
        scanner.close();
		
	}
    // Complete the minimumBrokenness function below.
 

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
    	Solution sol=new Solution();
    }
}

