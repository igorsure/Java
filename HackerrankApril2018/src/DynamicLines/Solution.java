package DynamicLines;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    /*
     * Complete the dynamicLineIntersection function below.
     */
    int n;
    final int BOUND=11;
    final int N=100000;
    int[][] xs=new int[BOUND][100001];
    int[][][] xxs=new int[BOUND][BOUND][N+2];
    int[] bigXs=new int[100001];
    int[] bigBs=new int[100001];
    Solution(){
        testBIT();
        Scanner in=new Scanner(System.in);
        n=in.nextInt();
        for(int i=0;i<n;i++){
            String req=in.next();
            int first=in.nextInt();
            int second=0;
            if (req.equals("?")) {
                
            	System.out.println(doitBIT(first));
            	continue;
            }
            second=in.nextInt();
            int value=req.equals("+")?1:-1;
        	//if (first<BOUND && second<BOUND) {
        	//	xs[first][second]+=value;
        	//	continue;
        	//} 
        	if (first<BOUND) {
                int curVal=getBITPoint(second,xxs[first][second%first]);
        	    updateBIT(N+1,second,curVal+value,xxs[first][second%first]);
                dumpBitArr(xxs[first][second%first]);
                dumpArr(xxs[first][second%first]);
                //dumpXXS();
        	} 
       		else {
                addXs(first,second,value);
            }
        }
        
    }
    int getBITResult(int index,int[] BITree) {
    	int res=getBITSum(BITree.length-2,BITree);
    	if (index>0) {
    		res-=getBITSum(index-2,BITree);
    	}
        //System.out.println("   getBITResult length:"+(BITree.length-2)+" up:"+getBITSum(BITree.length-2,BITree));
    	return res;
    }
    int getBITPoint(int index,int[] BITree) {
    	int res=getBITSum(index-1,BITree);
    	if (index>0) {
    		res-=getBITSum(index-2,BITree);
    	}
    	return res;
    }
    int getBITSum(int index,int[] BITree)
    {
        int sum = 0; // Iniialize result
      
        // index in BITree[] is 1 more than
        // the index in arr[]
        index = index + 1;
      
        // Traverse ancestors of BITree[index]
        while(index>0)
        {
            // Add current element of BITree 
            // to sum
            sum += BITree[index];
      
            // Move index to parent node in 
            // getSum View
            index -= index & (-index);
        }
        return sum;
    }
 
    // Updates a node in Binary Index Tree (BITree)
    // at given index in BITree.  The given value 
    // 'val' is added to BITree[i] and all of 
    // its ancestors in tree.
    public void updateBIT(int n, int index, 
                                        int val,int[] BITree)
    {
        // index in BITree[] is 1 more than 
        // the index in arr[]
        index = index + 1;
      
        // Traverse all ancestors and add 'val'
        while(index <= n)
        {
           // Add 'val' to current node of BIT Tree
           BITree[index] += val;
      
           // Update index to that of parent 
           // in update View
           index += index & (-index);
        }
    }    
    void dumpXXS(){
        for(int i=0;i<5;i++){
            System.out.println(i+":");
            for(int j=0;j<5;j++){
                System.out.print(j+" ");
                dumpArr(xxs[i][j]);
            }
        }
    }
    private int doitBruteForce(int q) {
		int countBigXs=bigXs[q];
		int countBigBs=bigBs[q];
		int rest=0;
		for(int i=1;i<BOUND;i++) {
            
			for(int j=0;j<xs[0].length;j++) {
				int q1=q-j;
				if ((q1%i)!=0) continue;
				rest+=xs[i][j];
			}
		}
		return countBigXs+countBigBs+rest;
	}
    private int doitBruteForce1(int q) {
		int countBigXs=bigXs[q];
		int countBigBs=bigBs[q];
		int rest=0;
		for(int k=1;k<BOUND;k++) {
            int reminder=q%k;
            int cur=reminder;
            while(cur<=100000){
                rest+=xs[k][cur];
                cur+=k;
            }
		}
		return countBigXs+countBigBs+rest;
	}
    private int doitBIT(int q) {
        int countBigXs=bigXs[q];
		int res=0;
		for(int k=1;k<BOUND;k++) {
			int reminder=q%k;
			res+=getBITResult(q,xxs[k][reminder]);
            //System.out.println("? q:"+q+" k:"+k+" reminder:"+reminder+" result:"+getBITResult(q,xxs[k][reminder]));
		}
        //System.out.println(res+" "+countBigXs);
		return res+countBigXs;
	}    
	private void addXs(int x, int b, int value) {
		int res=x+b;
		while(res<100001) {
			bigXs[res]+=value;
			res+=x;
		}
		
	}
	private void addBs(int x, int b, int value) {
		int res=x;
		while(res<100001) {
			bigBs[res]+=value;
			res+=b;
		}
		
	}
	static void dynamicLineIntersection(int n) {
        /*
         * Write your code here.
         */

    }
   void testBIT(){
       int n=10;
       int[] ar=new int[n+2];
       for(int i=1;i<=n;i++){
           int val=i*i;
          updateBIT(n+1, i, val,ar);
          int sumIn5=getBITSum(5,ar);
          int resIn5=getBITResult(5,ar);
          System.out.println(i+":"+val+" sum in 5:"+sumIn5+" result in 5:"+resIn5);
       }
       for(int i=1;i<=n;i++){
           int val=0;
          updateBIT(n+1, i, val,ar);
          int sumIn5=getBITSum(5,ar);
          int resIn5=getBITResult(5,ar);
          System.out.println(i+":"+val+" sum in 5:"+sumIn5+" result in 5:"+resIn5);
       }       
       
   }
    void dumpBitArr(int[] arr){
        for(int i=0;i<=10;i++){
            System.out.print(getBITSum(i,arr)+" ");
        }
        System.out.println("");
    }
    void dumpArr(int[] ar){
        for(int i=0;i<Math.min(ar.length,10);i++){
            System.out.print(ar[i]+" ");
        }
        System.out.println("");
    }
    public static void main(String[] args) {
        Solution sol=new Solution();
    }
}
