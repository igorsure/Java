package MINVOTE;

/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
class SegmentTree 
{
    int st[]; // The array that stores segment tree nodes
 
    /* Constructor to construct segment tree from given array. This
       constructor  allocates memory for segment tree and calls
       constructSTUtil() to  fill the allocated memory */
    SegmentTree(int arr[], int n)
    {
        // Allocate memory for segment tree
        //Height of segment tree
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
 
        //Maximum size of segment tree
        int max_size = 2 * (int) Math.pow(2, x) - 1;
 
        st = new int[max_size]; // Memory allocation
 
        constructSTUtil(arr, 0, n - 1, 0);
    }
 
    // A utility function to get the middle index from corner indexes.
    int getMid(int s, int e) {
        return s + (e - s) / 2;
    }
 
    /*  A recursive function to get the sum of values in given range
        of the array.  The following are parameters for this function.
 
      st    --> Pointer to segment tree
      si    --> Index of current node in the segment tree. Initially
                0 is passed as root is always at index 0
      ss & se  --> Starting and ending indexes of the segment represented
                    by current node, i.e., st[si]
      qs & qe  --> Starting and ending indexes of query range */
    int getSumUtil(int ss, int se, int qs, int qe, int si)
    {
        // If segment of this node is a part of given range, then return
        // the sum of the segment
        if (qs <= ss && qe >= se)
            return st[si];
 
        // If segment of this node is outside the given range
        if (se < qs || ss > qe)
            return 0;
 
        // If a part of this segment overlaps with the given range
        int mid = getMid(ss, se);
        return getSumUtil(ss, mid, qs, qe, 2 * si + 1) +
                getSumUtil(mid + 1, se, qs, qe, 2 * si + 2);
    }
 
    /* A recursive function to update the nodes which have the given 
       index in their range. The following are parameters
        st, si, ss and se are same as getSumUtil()
        i    --> index of the element to be updated. This index is in
                 input array.
       diff --> Value to be added to all nodes which have i in range */
    void updateValueUtil(int ss, int se, int i, int diff, int si)
    {
        // Base Case: If the input index lies outside the range of 
        // this segment
        if (i < ss || i > se)
            return;
 
        // If the input index is in range of this node, then update the
        // value of the node and its children
        st[si] = st[si] + diff;
        if (se != ss) {
            int mid = getMid(ss, se);
            updateValueUtil(ss, mid, i, diff, 2 * si + 1);
            updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
        }
    }
    int getValueUtil(int ss, int se, int i, int diff, int si)
    {
        // Base Case: If the input index lies outside the range of 
        // this segment
        if (i < ss || i > se)
            return 0;
 
        // If the input index is in range of this node, then update the
        // value of the node and its children
        st[si] = st[si] + diff;
        if (se != ss) {
            int mid = getMid(ss, se);
            getupdateValueUtil(ss, mid, i, diff, 2 * si + 1);
            updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
        }
    }
 
    // The function to update a value in input array and segment tree.
   // It uses updateValueUtil() to update the value in segment tree
    void updateValue(int arr[], int n, int i, int new_val)
    {
        // Check for erroneous input index
        if (i < 0 || i > n - 1) {
            System.out.println("Invalid Input");
            return;
        }
 
        // Get the difference between new value and old value
        int diff = new_val - arr[i];
 
        // Update the value in array
        arr[i] = new_val;
 
        // Update the values of nodes in segment tree
        updateValueUtil(0, n - 1, i, diff, 0);
    }
    int getValue(int arr[], int n, int i, int new_val)
    {
        // Check for erroneous input index
        if (i < 0 || i > n - 1) {
            System.out.println("Invalid Input");
            return -1;
        }
 
        // Get the difference between new value and old value
        int diff = new_val - arr[i];
 
        // Update the value in array
        arr[i] = new_val;
 
        // Update the values of nodes in segment tree
        return getValueUtil(0, n - 1, i, diff, 0);
    }    
 
    // Return sum of elements in range from index qs (quey start) to
   // qe (query end).  It mainly uses getSumUtil()
    int getSum(int n, int qs, int qe)
    {
        // Check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return getSumUtil(0, n - 1, qs, qe, 0);
    }
 
    // A recursive function that constructs Segment Tree for array[ss..se].
    // si is index of current node in segment tree st
    int constructSTUtil(int arr[], int ss, int se, int si)
    {
        // If there is one element in array, store it in current node of
        // segment tree and return
        if (ss == se) {
            st[si] = arr[ss];
            return arr[ss];
        }
 
        // If there are more than one elements, then recur for left and
        // right subtrees and store the sum of values in this node
        int mid = getMid(ss, se);
        st[si] = constructSTUtil(arr, ss, mid, si * 2 + 1) +
                 constructSTUtil(arr, mid + 1, se, si * 2 + 2);
        return st[si];
    }
 
 
}	
	class Node{
		String blank;
		Node left,right;
		int weight,from,to,level,middle;
		Node(int From,int To,int Level){
			from=From;
			to=To;
			level=Level;
			blank="";
			//if (level>30) return;
			middle=(from+to)/2;
			for(int i=0;i<level;i++) {
				blank+="  ";
			}
			if (from==to) return;
			//System.out.println(this);
			if (from==middle) {
				left=new Node(from,from,level+1);
				right=new Node(middle+1,to,level+1);
			} else {
				middle=middle-1;
				left=new Node(from,middle,level+1);
				right=new Node(middle+1,to,level+1);
			}
		}
		public String toString() {
			return blank+from+"->"+to+" w:"+weight+" middle:"+middle+" level:"+level;
			//return 
		}
		void dump(){
			System.out.println(this);
			if (left!=null) left.dump();
			if (right!=null) right.dump();
			
		}
		void addWeight(int wFrom,int wTo) {
			if (wFrom<=from && wTo>=to) {
				weight+=1;
				//System.out.println(this);
				return;
			}
			if (left!=null)
				left.addWeight(wFrom,wTo);
			if (right!=null)
			right.addWeight(wFrom,wTo);
		}
		int getWeight(int ind) {
			if (ind<from || ind>to) return 0;
			int lWeight=0;
			int rWeight=0;
			//System.out.println(ind+" "+ middle+" left:"+left+" right:"+right);
			if (ind<=middle && left!=null) lWeight=left.getWeight(ind);
			if (ind>middle && right!=null) rWeight=right.getWeight(ind);
			//System.out.println(ind+" "+this);
			return weight+lWeight+rWeight;
		}
	}
/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	int n;
	long[] s,sSum;
	long[] res;
    Codechef() throws IOException{
    	testAll();
        Reader in=new Reader();
        int q=in.nextInt();
        while(q-- >0) {
        	n=in.nextInt();
        	s=new long[n];
        	sSum=new long[n];
        	res=new long[n];
        	//System.out.println(n);
        	for(int i=0;i<n;i++) {
        		s[i]=in.nextInt();
        		sSum[i]=s[i];
        		//System.out.println(i+" "+s[i]);
        	}
        	calcSSum();
        	for(int i=0;i<n;i++) {
        		//res[i]=doit(i);
        		res[i]=bruteForce(i);
        	}
        	//dumpArr(s);
        	//dumpArr(sSum);
        	printRes(res);
        	
        }
    }
    void testAll() {
    	Node node=new Node(0,100000,0);
    	node.addWeight(20, 30);
    	node.addWeight(20, 50);
    	node.addWeight(27, 29);
    	//System.out.println(node.getWeight(27));
    	//System.out.println(node.getWeight(15));
    	//System.out.println(node.getWeight(30));
    	//node.dump();
    }
    private long bruteForce(int minion) {
    	long ret=0;
		for(int i=0;i<n;i++) {
			if (isVoter(i,minion)) ret++;
		}
		return ret;
	}
	void printRes(long[] ar) {
    	System.out.print("");
    	for(int i=0;i<ar.length;i++) {
    		System.out.print(ar[i]+" ");
    	}
    	System.out.println("");
    }
    int doit(int ind) {
    	int ret=0;
    	int left=minLeftVoter(ind);
    	int right=maxRightVoter(ind);
    	int divLeft=0;
    	int divRight=0;
    	if (left!=-1) divLeft+=ind-left;
    	if (right!=-1) divRight+=right-ind;
    	//System.out.println(ind+" left:"+left+" right:"+right+" "+divLeft+"+"+divRight);
    	return divLeft+divRight;
    }
    void calcSSum() {
    	for(int i=1;i<n;i++) {
    		sSum[i]+=sSum[i-1];
    	}
    }
    void dumpArr(long[] ar) {
    	System.out.print("[");
    	for(int i=0;i<ar.length;i++) {
    		System.out.print(ar[i]+" ");
    	}
    	System.out.println("]");
    }
    boolean isVoter(int voter,int minion) {
    	if (voter<minion) {
    		return s[voter]>=sSum[minion-1]-sSum[voter];
    	} 
    	if (voter>minion) {
    		return s[voter]>=sSum[voter-1]-sSum[minion];
    	}
    	return false;
    }
    int minLeftVoter(int minion) {
    	if (isVoter(0,minion)) return 0;
    	if (minion==0) return -1;
    	int startNotVoter=0;
    	int finishVoter=minion-1;
    	while(startNotVoter<finishVoter-1) {
    		int middle=(startNotVoter+finishVoter)/2;
    		if (isVoter(middle,minion)) {
    			finishVoter=middle;
    		} else {
    			startNotVoter=middle;
    		}
    	}
    	return finishVoter;
    }
    int maxRightVoter(int minion) {
    	if (isVoter(n-1,minion)) return n-1;
    	if (minion==n-1) return -1;
    	int startVoter=minion+1;
    	int finishNotVoter=n-1;
    	while(startVoter<finishNotVoter-1) {
    		int middle=(startVoter+finishNotVoter)/2;
    		if (isVoter(middle,minion)) {
    			startVoter=middle;
    		} else {
    			finishNotVoter=middle;
    		}
    	}
    	return startVoter;
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
