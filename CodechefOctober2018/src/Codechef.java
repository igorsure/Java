
/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
//BBRICCKS
/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	int mult=1000000007;
	int n,k;
	long[][][] path;
	long[][] snakesZero,snakesOne;
	//int[][] snakes=new int[400000000][1000];
    Codechef() throws IOException{
    	testAll();
        Reader in=new Reader();
        int q=in.nextInt();
        while(q-- >0) {
        	n=in.nextInt();
        	k=in.nextInt();
        	System.out.println(doit1());
        }
    }
    void test(int N,int K) {
    	n=N;
    	k=K;
    	System.out.println(doit1());
    }
    void testAll() {
    	test(3,2);
    	test(10,3);
    	test(20,5);
    }
    long doit1(){
    	long res=0;
    	snakesZero=new long[n+1][k+1];
    	snakesOne=new long[n+1][k+1];
    	//snakesZero=new long[100001][k+1];
    	//snakesOne=new long[100001][k+1];
    	snakesZero[0][k]=1;
    	for(int i=1;i<=n;i++) {
    		/*
    		i=I;
    		if (I==1000000) {
    			i=0;
    	    	snakesZero=new long[100001][k+1];
    	    	snakesOne=new long[100001][k+1];
    		}*/
    		for(int j=0;j<=k;j++){
    			snakesZero[i][j]=(snakesOne[i-1][j]+snakesZero[i-1][j])%mult;
    			if (j>0)
        	    	snakesOne[i][j-1]=(2*snakesZero[i-1][j]+snakesOne[i-1][j])%mult;
    		}
    	}
    	//dumpAr2(snakesZero);
    	dumpAr2(snakesOne);
    	return (snakesZero[n][0]+snakesOne[n][0])%mult;
    }
    long doit() {
    	//path=new long[n+1][k+1][3];
    	path=new long[1000001][k+1][3];
    	int i=0;
    	path[0][k][0]=1;
    	for(int I=1;I<=n;I++) {
    		i=I;
    		if (I==1000000) {
    			i=0;
    	    	path=new long[1000001][k+1][3];
    		}
    		for(int j=k;j>=0;j--) {
    			long empty=path[i-1][j][0];
    			long first=path[i-1][j][1];
    			long second=path[i-1][j][2];
    			//System.out.println(i+" "+j+" "+ empty+" "+first+ " "+second);
	            path[i][j][0]=(path[i][j][0]+empty+first+second)%mult;
	            if (j>0) {
	                path[i][j-1][1]=(path[i][j-1][1]+empty+second)%mult;
	                path[i][j-1][2]=(path[i][j-1][2]+empty+first)%mult;   
	            }
	            
    		}
    	}
    	//dumpAr3(path);
    	return getSum(path[i][0]);
    }
    void dumpAr2(long[][] ar) {
    	for(int i=0;i<ar.length;i++) {
			System.out.print(String.format("%3d",i)+": [");
    		for(int j=0;j<ar[0].length;j++) {
    				System.out.print(String.format("%5d",ar[i][j]));
    		}
    		System.out.println(" ]");
    	}
    }
    void dumpAr3(int[][][] ar) {
    	for(int i=0;i<ar.length;i++) {
    		for(int j=0;j<ar[0].length;j++) {
    			System.out.print("[");
    			for(int jj=0;jj<ar[0][0].length;jj++) {
    				System.out.print(ar[i][j][jj]+" ");
    			}
    			System.out.print("], ");
    		}
    		System.out.println("");
    	}
    }
    void dumparr(int[] ar) {
    	System.out.print("[ ");
    	for(int i=0;i<ar.length;i++) {
    		System.out.print(ar[i]+" ");
    	}
    	System.out.println("]");
    }
	private long getSum(long[] ar) {
		// TODO Auto-generated method stub
		long res=0;
		for(int i=0;i<ar.length;i++) {
			res=(res+ar[i])%mult;
		}
		return res;
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
