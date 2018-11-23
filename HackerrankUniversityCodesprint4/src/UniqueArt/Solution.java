package UniqueArt;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {
    int[] t;
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
    class Node {
    	int start,finish,level;
    	boolean leaf;
    	Node left,right;
    	String blank;
    	HashSet<Integer> good,bad;
    	Node(int Start,int Finish,int Level){
    		start=Start;
    		finish=Finish;
    		leaf=true;
    		level=Level;
    		level=Level;
    		if (start==finish-1) {
    			left=new Node(start,start,level+1);
    			right=new Node(finish,finish,level+1);
    			leaf=false;
    		}
    		if (start<finish-1) {
    			int middle=(start+finish)/2;
    			left=new Node(start,middle,level+1);
    			right=new Node(middle+1,finish,level+1);
    			leaf=false;
    		}
    		StringBuilder blank1=new StringBuilder("");
    		for(int i=0;i<level;i++) {
    			blank1.append("  ");
    		}
    		blank=blank1.toString();
    		good=new HashSet<Integer>();
    	}
    	
    	void dump() {
    		System.out.println(blank+start+"->"+finish);
    		if (!leaf) {
    			left.dump();
    			right.dump();
    		}
    	}
    }
    Solution()  throws IOException  { 
        Node tt=new Node(0,201,0);
        tt.dump();
        Reader scan = new Reader();
       //int n = scan.nextInt();

        int tSize = scan.nextInt();

        t = new int[tSize];


        for (int tItr = 0; tItr < tSize; tItr++) {
            t[tItr] = scan.nextInt();

        }

        initialize(t);
        int q = scan.nextInt();
        //System.out.println(q);    
        for (int q_i = 0; q_i < q; q_i++) {
                //System.out.println(q_i);    
                
                int start=scan.nextInt();
                int finish=scan.nextInt();
                int result = student(start-1,finish-1);

                System.out.println(result);

        }

    }
    void initialize(int[] t) {
        // Write your code here.


    }

    int student(int start, int finish) {
        HashSet<Integer> good=new HashSet<Integer>();
        HashSet<Integer> bad=new HashSet<Integer>();
        for(int i=start;i<=finish;i++){
            if (bad.contains(t[i])){
                continue;
            }
            if (good.contains(t[i])){
                good.remove(t[i]);
                bad.add(t[i]);
            } else {
                good.add(t[i]);
            }
        }
        return good.size();  

    }

    void test(int start,int finish){
    	System.out.println(student(start,finish));
    }

    public static void main(String[] args) throws IOException {
         Solution sol=new Solution();
    }
}
