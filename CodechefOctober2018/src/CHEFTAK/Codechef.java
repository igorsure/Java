package CHEFTAK;

/* package codechef; // don't place package name! */
//CHEFTAK
import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{
	double coins,tugr;
	double cutoffBuy,cutOffSell,fee;
    Codechef() throws IOException{
        Reader in=new Reader();
        int q=in.nextInt();
        //double amount
        coins=10;
        tugr=0;
        cutoffBuy=0.125;
        cutOffSell=0.1;
        fee=0.05;
        for(int i=0;i<q;i++){
            int t=in.nextInt();
            int y=in.nextInt();
            double z=in.nextDouble();
            if (i<000) { 
            	System.out.println(0);
            }
            else if (i<8000) {
            	if (t==1)
            		System.out.println(sell(y,z));
            	else 
            		System.out.println(buy(y,z));
            } else {
            	if (t==1)
             		System.out.println(sell(y,z));
            	else 
                	System.out.println(0);
            		
            }
        }
    }
	private double buy(int y, double z) {
		//coins->tugr;
		if (z<cutoffBuy) return 0;
		double res=coins;
		double amount=Math.min(y, coins);
		double result=amount*z*0.9995;
		tugr+=result;
		coins-=amount;
		// TODO Auto-generated method stub
		return amount;
	}
	private double sell(int y, double z) {
		//tugr->coins
		if (z>cutOffSell) return 0;
		double amount=Math.min(y, tugr);
		double result=amount*0.9995/z;
		coins+=result;
		tugr-=amount;
		return amount;
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
