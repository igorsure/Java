package SURCHESS;

/* package codechef; // don't place package name! */
//SURCHESS
import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Codechef
{	int n,m,max_size;
	int[][] brd;
	int[] sizes;
	Row[][] brdV,brdH;
	Row[][][] brdAll;
	class Row{
		int even,odd,evenAmount,oddAmount;
		int inv=-1;
		Row(){
			this.even=0;
			this.odd=0;
			this.evenAmount=0;
			this.oddAmount=0;
		}
		Row(int num ,int oddity){
			this.even=0;
			this.odd=0;
			this.evenAmount=0;
			this.oddAmount=0;
			addNum(num, oddity);
		}
		Row(int even,int odd,int evenAmount,int oddAmount){
			this.even=even;
			this.odd=odd;
			this.evenAmount=evenAmount;
			this.oddAmount=oddAmount;
		}
		void addOdd(int num){
			odd+=num;
			oddAmount++;
		}
		void addNum(int num,int oddity) {
			if (oddity%2==0) this.addEven(num);
			else this.addOdd(num);
		}
		void addEven(int num) {
			even+=num;
			evenAmount++;
		}
		Row substractRow(Row row) {
			return new Row(
					even-row.even,
					odd-row.odd,
					evenAmount-row.evenAmount,
					oddAmount-row.oddAmount
					);
		}
		Row addRow(Row row) {
			return new Row(
					even+row.even,
					odd+row.odd,
					evenAmount+row.evenAmount,
					oddAmount+row.oddAmount
					);
		}
		public String toString() {
			return even+","+odd+","+evenAmount+","+oddAmount;
		}
		public void calcInversions(){
		    //console.log("calcInversions;",odd,even,oddAmount,evenAmount);
		    int val1=odd+evenAmount-even;
		    int val2=even+oddAmount-odd;
		    inv=Math.min(val1,val2);
		  }
		
	}
	void dumpArrRow(Row[][] ar) {
		for(int i=0;i<ar.length;i++) {
			System.out.print(i+":[ ");
			for(int j=0;j<ar[0].length;j++) {
				System.out.print(ar[i][j]+" ");
			}
			System.out.println("]");
		}
	}
    Codechef() throws IOException{
        Scanner in=new Scanner(System.in);
        n=in.nextInt();
        m=in.nextInt();
        sizes=new int[n*m+1];
        brd=new int[n][m];
        //System.out.println(n+" "+m);
        for(int i=0;i<n;i++) {
        	String s=in.next();
        	for(int j=0;j<m;j++) {
        		brd[i][j]=s.charAt(j)-'0';
        		//System.out.print(brd[i][j]+" ");
        	}
        	//System.out.println("");
        }
        init();
        int q=in.nextInt();
        while(q-- >0) {
        	int num=in.nextInt();
        	System.out.println(doit(num));
        }
        
    }
    private int doit(int num) {
    	if (num>=sizes.length) return sizes[sizes.length-1];
    	return sizes[num];
		// TODO Auto-generated method stub
	}
	void init() {
    	genBrdV();
    	genBrdH();
    	genBrdAll();
    	calcSizes();
    	//dumpArrRow(brdV);
    	//dumpArrRow(brdH);
    	//for(int i=0;i<n;i++) {
    	//	System.out.println("row:"+i);
    	//	dumpArrRow(brdAll[i]);
    	//}
    	//dumpArrRow(brdV);
    }
	private void calcSizes() {

		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				for(int k=0;k<=max_size;k++) {
					if (brdAll[i][j][k]==null) continue;
					Row row=brdAll[i][j][k];
					row.calcInversions();
					sizes[row.inv]=Math.max(sizes[row.inv],k);
				}
			}
		}
		for(int i=1;i<sizes.length;i++) {
			sizes[i]=Math.max(sizes[i-1],sizes[i]);
		}
	}
	private void genBrdV() {
		brdV=new Row[n][m];
		for(int col=0;col<m;col++) {
			brdV[0][col]=new Row(brd[0][col], col);
			for(int row=1;row<n;row++) {
				brdV[row][col]=brdV[row-1][col].addRow(new Row(brd[row][col],row+col));
			}
		}
		
	}
	Row getRowH(int row,int colfrom,int colto){
		Row res=brdH[row][colto];
		if (colfrom>0) {
			res=res.substractRow(brdH[row][colfrom-1]);
		}
		return res;
	}
	Row getRowV(int col,int rowfrom,int rowto){
		Row res=brdV[rowto][col];
		if (rowfrom>0) {
			res=res.substractRow(brdV[rowfrom-1][col]);
		}
		return res;
	}
	private void genBrdH() {
		brdH=new Row[n][m];
		for(int row=0;row<n;row++) {
			brdH[row][0]=new Row(brd[row][0],row);
			for(int col=1;col<m;col++) {
				brdH[row][col]=brdH[row][col-1].addRow(new Row(brd[row][col],row+col));
			}
		}
	}
	private void genBrdAll() {
		max_size=Math.min(n,m);
		brdAll=new Row[n][m][max_size+1];
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				brdAll[i][j][1]=new Row(brd[i][j],i+j);
				for (int k=2;k<=max_size;k++) {
					int bottom=i+k-1;
					int right=j+k-1;
					if (bottom>=n || right >=m) continue;
					Row prevSquare=brdAll[i][j][k-1];
					//prevSquare.addRow(brdAll[i][j][k-1]);
					//System.out.println(i+" right:"+right+" bottom:"+bottom);
					Row rightRow=getRowV(right,i,bottom);
					Row bottomRow=getRowH(bottom,j,right-1);
					Row newSquare=new Row();
					newSquare=newSquare.addRow(prevSquare);
					newSquare=newSquare.addRow(rightRow);
					newSquare=newSquare.addRow(bottomRow);
					brdAll[i][j][k]=newSquare;
					/*
					if (i==0 & j==0) {
						System.out.println("cell 0-0 size:"+k);
						System.out.println(prevSquare);
						System.out.println(bottomRow);
						System.out.println(rightRow);
						System.out.println(newSquare);
					}
					*/
				}
			}
		}
		
		// TODO Auto-generated method stub
		
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
