package XXOR;

/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	int n,q;
	int[] a;
	int[][] bits,bitsSum;
    Codechef() throws IOException{
        Reader in=new Reader();
        n=in.nextInt();
        q=in.nextInt();
        a=new int[n];
        bits=new int[n][32];
        for(int i=0;i<n;i++) {
        	a[i]=in.nextInt();
        }
        calcBits();
        //dumpArr(a);
        //dumpArr2(bits);
        calcBitsSum();
        //dumpArr2(bits);
        for(int i=0;i<q;i++) {
        	int left=in.nextInt();
        	int right=in.nextInt();
        	//System.out.println(left+" "+right);
        	System.out.println(doit(left-1,right-1));
        }
    }
    private int doit(int left, int right) {
    	int[] x=new int[32];
    	for(int i=0;i<32;i++) {
    		x[i]=bits[right][i];
    	}
    	if (left>0) {
	    	for(int i=0;i<32;i++) {
	    		x[i]-=bits[left-1][i];
	    	
	    	}
    	}
    	int res=0;
    	int amount=right-left+1;
    	for(int i=0;i<31;i++) {
    		if (x[i]*2<=amount) {
    			res+=(1<<i);
    			x[i]=1;
    			}
    		else {
    			x[i]=0;
    		}
    		
    	}
    	//dumpArr(x);
    	//System.out.printn(1<<30);
		return res;
	}
	void calcBits() {
    	for(int i=0;i<a.length;i++) {
    		calcBit(i);
    	}
    }
    void calcBitsSum() {
    	for(int i=1;i<a.length;i++) {
    		for(int j=0;j<32;j++) {
    			bits[i][j]+=bits[i-1][j];
    		}
    	}
    }
    void calcBit(int bit) {
    	for (int i=0;i<32;i++) {
    		if (((1 << i) & (a[bit])) != 0) {
    			bits[bit][i]=1;
    		}
    	}
    }
    void dumpArr(int[] ar) {
    	System.out.println("");
    	System.out.print("[");
    	for(int i=0;i<ar.length;i++) {
    		System.out.print(ar[i]+" ");
    	}
    	System.out.println("]");
    }
    void dumpArr2(int[][] ar) {
    	System.out.println("");
    	for(int i=0;i<ar.length;i++) {
        	System.out.print("[");
        	for(int j=0;j<ar[0].length;j++) {
        		System.out.print(ar[i][j]+" ");
        	}
        	System.out.println("]");
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
