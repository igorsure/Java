package CyclicalQueries;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Path implements Comparable<Path>{
	Stack<Long> path;
	int lastNode;
	long weight;
	Path(){
		path=new Stack<Long>();
		//path.pe
		weight=0;
		lastNode=0;
	}
	Path(int node, int w){
		path=new Stack<Long>();
		//path.pe
		weight=0;
		lastNode=0;
		addNode(node,w);
	}
	void addNode(int node, int w) {
		weight+=w; 
		long key=(long) w * 1000000+node;
		lastNode=node;
		path.push(key);
	}
	int[] removeNode() {
		int[] res=new int[2];
		long key=path.pop();
		res[0]=(int) (key/1000000); // weight
		res[1]=(int) (key%1000000); //node
		lastNode=getLastNode();
		weight-=res[0];
		return res;
	}
	int getLastNode() {
		if (path.size()==0) return -1;
		long key=path.peek();
		return (int) (key/1000000);
	}
	@Override
	public int compareTo(Path pth) {
		if (this.weight<pth.weight) return 1;
		if (this.weight>pth.weight) return -1;
		if (this.lastNode>pth.lastNode) return -1;
		if (this.lastNode<pth.lastNode) return 1;
		// TODO Auto-generated method stub
		return 0;
	}
	public String toString() {
		return "'weight:"+weight+" lastNode:"+lastNode+" size:"+path.size()+"'";
	}
}
class Node{
	PriorityQueue<Path> queue;
	//int weight=0;
	
	Node(){
		queue=new PriorityQueue<Path>();
	}
	void addNewNode(int n,int w){
		Path path=new Path(n,w);
		queue.add(path);
	}
	void addNodeToLongestPath(int n,int w) {
		if (queue.size()==0) {
			addNewNode(n,w);
			return;
		}
		Path path=queue.peek();
		path.addNode(n,w);
	}
	void removeNode() {
		Path path=queue.remove();
		path.removeNode();
		if (path.path.size()>0)
			queue.add(path);
	}
	long getWeight(){
		if (queue.size()==0) return 0;
		return queue.peek().weight;
	}
	int getLastNode(){
		if (queue.size()==0) return 0;
		return queue.peek().lastNode;
	}
	
	public String toString() {
		String res=queue.toString();
		return res;		
	}
}
class Circle {
	final int SEGMENT=350;
	int ind;
	long[] dist;
	long[] distSum;
	Node[] nodes;
	long[] segmentDists;
	int[] segmentNodes;
;	
	int n;
	Circle(long[] dist){
		this.dist=dist;
		n=dist.length;
		nodes=new Node[n];
		distSum=new long[n];
		for(int i=0;i<n;i++) {
			nodes[i]=new Node();
			//System.out.println(nodes[i]);
		}
		calcDistSum();
		ind=n+1;
		segmentDists=new long[SEGMENT*2];
		segmentNodes=new int[SEGMENT*2];
	}
	int getSegment(int node) {
		return node/SEGMENT;
	}
	void recalcSegment(int seg) {
		segmentNodes[seg]=0;
		segmentDists[seg]=0;
		int start=seg*SEGMENT;
		int finish=(seg+1)*SEGMENT;
		for(int i=start;i<finish;i++) {
			long dist=distFromXtoYMax(start,i);
			if (dist>segmentDists[seg]) {
				segmentDists[seg]=dist;
				segmentNodes[seg]=i;
			}
			if (dist==segmentDists[seg]) {
				segmentNodes[seg]=Math.max(nodes[segmentNodes[seg]].getLastNode(),nodes[i].getLastNode());
			}
		}
		segmentNodes[seg+SEGMENT]=segmentNodes[seg];
		segmentDists[seg]=segmentDists[seg];
		
	}
	void calcDistSum() {
		for(int i=1;i<n;i++) {
			distSum[i]=dist[i-1]+distSum[i-1];
		}
	}
	void addNode(int node,int w) {
		nodes[node].addNewNode(ind++, w);
	}
	void removeNode(int node) {
		nodes[node].removeNode();
	}
	long distFromXtoYBackward(int x,int y) {
		if (x==y) return 0;
		long res=0;
		res=distSum[n-1]-distSum[x]+dist[n-1];
		res+=distSum[y];
		return res;		
	}
	long distFromXtoYForward(int x,int y) {
		long res=0;
		res=distSum[y];
		if (x>0) {
			res-=distSum[x];
		}
		return res;
	}	
	long distFromXtoY(int x,int y) {
		if (x==y) return 0;
		if (x<y) return distFromXtoYForward(x,y);
		else return distFromXtoYBackward(x,y);
	}	
	long distFromXtoYMax(int x,int y) {
		//System.out.println("distFromXtoYMax:"+x+" "+y);
		return distFromXtoY(x,y)+nodes[y].getWeight();
	}
	int getFarthestNode(int x) {
		int y=x;
		long dist=0;
		long lastNode=-1;
		for(int i=0;i<n;i++) {
			long curdist=distFromXtoYMax(x,i);
			if (curdist<dist) continue;
			if (curdist>dist) {
				y=i;
				dist=curdist;
				lastNode=nodes[i].getLastNode();
				//System.out.println("getFarthestNode x:" + x+ "y:"+y);
				continue;
			}
			if (nodes[i].getLastNode()>lastNode) {
				lastNode=nodes[i].getLastNode();
			}
		}
		return y;
	}
	long getMaxDist(int x){
		int y=getFarthestNode(x);
		//System.out.println("getMaxDist x:"+x+" y:"+y+" - "+distFromXtoY(x,y));
		return distFromXtoYMax(x,y);
	}
	void addNodeToFarthestPlace(int x,int w) {
		int y=getFarthestNode(x);
		nodes[y].addNodeToLongestPath(ind++, w);
	}
	void removeNodeFromFarthestPlace(int x) {
		int y=getFarthestNode(x);
		nodes[y].removeNode();
	}
	void dump(){
		for(int i=0;i<n;i++) {
			System.out.println(nodes[i]);
		}
	}
}
public class Solution {
	class Reader{
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
	
	Solution() throws IOException{
    	//testNode();
		Reader scanner=new Reader();
        int n = scanner.nextInt();
        //scanner.skip//("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long[] w = new long[n];

        //String[] wItems = scanner.nextLine().split(" ");
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            long wItem = scanner.nextLong();
            w[i] = wItem;
        }
        Circle circle=new Circle(w);
        int m = scanner.nextInt();
        for(int i=0;i<m;i++) {
        	int type=scanner.nextInt();
        	int x=scanner.nextInt();
        	int weight=-1;
        	//System.out.print(type+" "+x);
        	if (type<3) {
        		weight=scanner.nextInt();
            	//System.out.print(" "+weight);
        	}
        	//System.out.println("");
        	if (type==1) {
        		circle.addNodeToFarthestPlace(x-1, weight);
        	}
        	if (type==2) {
        		circle.addNode(x-1, weight);
        	}
        	if (type==3) {
        		circle.removeNodeFromFarthestPlace(x-1);
        	}
        	if (type==4) {
        		long res=circle.getMaxDist(x-1);
        		//circle.dump();
        		System.out.println(res);
        	}
        }

        scanner.close();
		
	}
    private  final Reader scanner = new Reader();

    public static void main(String[] args) throws IOException {
    	Solution sol=new Solution();
    }
    void testNode() {
    	Node node=new Node();
    	node.addNewNode(1, 10);
    	node.addNewNode(2, 5);
    	node.addNewNode(3, 6);
    	node.addNewNode(4, 8);
    	System.out.println(node);   
    	node.addNodeToLongestPath(5, 6);
    	node.addNodeToLongestPath(6, 5);
    	node.addNodeToLongestPath(7, 5);
    	System.out.println(node);   
    	node.removeNode();
    	System.out.println(node);   
    	node.removeNode();
    	System.out.println(node);   
    	node.removeNode();
    	System.out.println(node);   
    	node.removeNode();
       	System.out.println(node);
       	System.out.println(node);   
        
        node.addNodeToLongestPath(8, 5);
    	node.addNodeToLongestPath(9, 5);
    	node.addNodeToLongestPath(10, 5);
    	System.out.println(node);   
    }
}