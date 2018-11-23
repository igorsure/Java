package CCIRCLES;

/* package codechef; // don't place package name! */
//CCIRCLES
import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	int n,q,curInd;
	int[] x,y,r; 
	double[][][] dist;
	double[] left,right;
	Circle[] circles; 
	
	class Circle{
		int x,y,r;
		Circle(int x,int y,int r){
			this.x=x;
			this.y=y;
			this.r=r;
		}
		double getDist(Circle circle) {
			return Math.hypot(x-circle.x, y-circle.y);
		}
	}
    Codechef() throws IOException{
        Reader in=new Reader();
        n=in.nextInt();
        q=in.nextInt();
        x=new int[n];
        y=new int[n];
        r=new int[n];
        int[][] xxx=new int[100000][1000];
        circles=new Circle[n];
        for(int i=0;i<n;i++) {
        	x[i]=in.nextInt();
        	y[i]=in.nextInt();
        	r[i]=in.nextInt();
        	//System.out.println(x[i]+" "+y[i]+" "+r[i]);
        	circles[i]=new Circle(x[i],y[i],r[i]);
        }
        //processBruteForce();
        processBinarySearch();
        OutputStream out = new BufferedOutputStream ( System.out );
        while(q-- >0) {
        	int val=in.nextInt();
        	//out.write((doitBruteForce(val)+ "\n").getBytes());
        	out.write((doitBinarySearch(val)+ "\n").getBytes());
        	//System.out.println(doit(val));
        }
        //System.out.println(getDist(10000,9999,9998,-11000));
        out.flush();
}
  void processBruteForce() {
	  dist=new double[n][n][3];
      for(int i=0;i<n;i++) {
      	for(int j=i+1;j<n;j++) {
      		double d=circles[i].getDist(circles[j]);
      		dist[i][j][0]=d;
      		int maxR=Math.max(r[i],r[j]);
      		int minR=Math.min(r[i],r[j]);
      		if (maxR>(d+minR)) {
      			dist[i][j][1]=maxR-minR-d;
      			dist[i][j][2]=maxR+minR+d;
      		}
      		else {
      			dist[i][j][1]=Math.max(0,d-r[i]-r[j]);
      			dist[i][j][2]=d+r[i]+r[j];
      		}
      	}
      }
	  
  }
  void processBinarySearch() {
      curInd=0;
      int length=(n*(n-1))/2;
      left=new double[length];
      right=new double[length];
      for(int i=0;i<n;i++) {
      	for(int j=i+1;j<n;j++) {
      		double d=circles[i].getDist(circles[j]);
      		//Math.max
      		int maxR=Math.max(r[i],r[j]);
      		int minR=Math.min(r[i],r[j]);
      		if (maxR>(d+minR)) {
      			left[curInd]=maxR-minR-d;
      			right[curInd]=maxR+minR+d;
      		}
      		else {
      			left[curInd]=Math.max(0,d-r[i]-r[j]);
      			right[curInd]=d+r[i]+r[j];
      		}
      		//System.out.println(" dist:"+d+" minR:"+minR+" maxR:"+maxR);
      		curInd++;
      			
      	}
      }
      Arrays.sort(left);
      Arrays.sort(right);
	  
  }
   void dumpArr(double[] ar) {
	   System.out.print("[ ");
	   for(int i=0;i<ar.length;i++) {
		   System.out.print(ar[i]+" ");
	   }
	   System.out.println("]");
   }
    int doitBruteForce(int val) {
    	int res=0;
    	for(int i=0;i<n;i++) {
    		for(int j=i+1;j<n;j++) {
    			if (val>=dist[i][j][1] && val<=dist[i][j][2])
    				res++;
    		}
    	}
    	return res;
    }
    private int doitBinarySearch(int val) {
    	int kol1=getLeft(left,val);
    	int kol2=getRight(right,val);
    	//System.out.println("doit:"+val+" "+kol1+" "+kol2+" ");
    	return kol1-kol2;
	}
    int getLeft(double[] ar, int val) {
    	if (ar[curInd-1]<=val) {
    		return curInd;
    	}
    	if (ar[0]>val) return 0;
    	int bottom=0;
    	int top=curInd;
    	while(bottom<top-1) {
    		int middle=(top+bottom)/2;
    		if (ar[middle]<=val) bottom=middle;
    		else top=middle;
    	}
    	return bottom+1;
    }
    int getRight(double[] ar, int val) {
    	if (ar[curInd-1]<val) {
    		return curInd;
    	}
    	if (ar[0]>=val) return 0;
    	int bottom=0;
    	int top=curInd;
    	while(bottom<top-1) {
    		int middle=(top+bottom)/2;
    		if (ar[middle]<val) bottom=middle;
    		else top=middle;
    	}
    	return bottom+1;
    }
	double getDist(long x1,long y1,long x2,long y2) {
		return Math.hypot(x1-x2, y1-y2);
    	//double xx=(double) x1-x2;
    	//double yy=(double) y1-y2;
    	//return Math.sqrt(xx*xx+yy*yy);
    	
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
