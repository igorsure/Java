package EMBDTREE;

/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
	class Edge{
		int node1,node2,weight;
		Edge(int n1,int n2, int w){
			node1=n1;
			node2=n2;
			weight=w;
		}
		int another(int node) {
			return node1==node?node2:node1;
		}
		public boolean equals(Object o){
			Edge edge = (Edge)o;
			if(node1==edge.node1 && node2==edge.node2){
				return true;
			}
			return false;
			}
			 
		@Override
		public int hashCode() {
			int hash = 97 * node1 + node2;
			return hash;
		}
		public String toString() {
			return node1+"->"+node2;
		}
	}
class Codechef
{
	HashMap<Integer,HashSet<Edge>> graph;
	int root=-1;
	int[] levels,dists,xCoord,yCoord;
	int[][] coords;
	int n;
    Codechef() throws IOException{
        Reader in=new Reader();
        n=in.nextInt();
        levels=new int[n+1];
        dists=new int[n+1];
        yCoord=new int[n+1];
        coords=new int[n+1][2];
        graph=new HashMap<Integer,HashSet<Edge>>();
        for(int i=0;i<n-1;i++) {
        	int node1=in.nextInt();
        	int node2=in.nextInt();
        	int weight=in.nextInt();
        	if (root==-1) root=node1;
        	addEdge(node1,node2,weight);
        }
        BFSLevel(root);
        BFSDist(root);
        calcXCoord();
        BFSCoord(root);
        //System.out.println(graph);
        //dumpArr(levels);
        //dumpArr(dists);
        //dumpArr(xCoord);
        dumpCoords();
        
    }
    void dumpArr(int[] ar) {
    	System.out.print("[");
    	for(int i=0;i<ar.length;i++) {
    		System.out.print(ar[i]+" ");
    	}
    	System.out.println("]");
    }
    void addNode(int node) {
    	if (graph.containsKey(node)) return;
    	graph.put(node, new HashSet<Edge>());
    }
    void addEdge(int node1,int node2,int weight) {
    	addNode(node1);
    	addNode(node2);
    	graph.get(node1).add(new Edge(node1,node2,weight));
    	graph.get(node2).add(new Edge(node2,node1,weight));
    	
    }
    void calcXCoord() {
    	xCoord=new int[n+1];
    	for(int i=1;i<xCoord.length;i++) {
    		xCoord[i]=dists[i]+xCoord[i-1];
    	}
    }
    void dumpGraph() {
    	for(int node : graph.keySet()) {
    		System.out.print(node+": ");
    		//for(Edge e)
    	}
    }
    void BFSLevel(int node) {
    	Queue<Integer> queue=new LinkedList<Integer>();
    	boolean[] visited= new boolean[n+1];
    	visited[node]=true;
    	queue.add(node);
    	while(!queue.isEmpty()) {
    		int next=queue.remove();
    		for(Edge edge:graph.get(next)) {
    			int nd=edge.another(next);
    			if (visited[nd]) continue;
    			visited[nd]=true;
    			queue.add(nd);
    			levels[nd]=levels[next]+1;
    		}
    	}
    	
    }
    void BFSCoord(int node) {
    	Queue<Integer> queue=new LinkedList<Integer>();
    	boolean[] visited= new boolean[n+1];
    	visited[node]=true;
    	queue.add(node);
    	while(!queue.isEmpty()) {
    		int next=queue.remove();
    		for(Edge edge:graph.get(next)) {
    			int nd=edge.another(next);
    			if (visited[nd]) continue;
    			visited[nd]=true;
    			queue.add(nd);
    			int level=levels[nd];
    			coords[nd][0]=xCoord[level];
    			coords[nd][1]=yCoord[level];
    			yCoord[level]+=1;
    		}
    	}
    	
    }
    void BFSDist(int node) {
    	Queue<Integer> queue=new LinkedList<Integer>();
    	boolean[] visited= new boolean[n+1];
    	visited[node]=true;
    	queue.add(node);
    	while(!queue.isEmpty()) {
    		int next=queue.remove();
    		for(Edge edge:graph.get(next)) {
    			int nd=edge.another(next);
    			if (visited[nd]) continue;
    			visited[nd]=true;
    			queue.add(nd);
    			int level=levels[nd];
    			dists[level]=Math.max(dists[level],edge.weight);
    		}
    	}
    	
    }
    void dumpCoords(){
    	for(int i=1;i<=n;i++) {
    		System.out.println(coords[i][0]+" "+coords[i][1]);
    	}
    }
	class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }
 
        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
 
        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
 
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
 
            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }
 
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }    	

	public static void main (String[] args) throws java.lang.Exception
	{
		Codechef chef=new Codechef();
			
	}
}
