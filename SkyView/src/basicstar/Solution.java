package basicstar;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    int[] space=new int[2*12*12+1];
    int[] dec=new int[2*12*12+1];
    HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
    TreeMap<Double,Integer> diff=new TreeMap<Double,Integer>();
    double epsilon=0.000000000001;
    double residual;
    double[] Dif=new double[10000000];
    int startNumer;
    boolean startDone=false;
    int startX,startY;
    Solution(){
    	
        //Scanner in = new Scanner(System.in);
        //int x = in.nextInt();
        //int y = in.nextInt();
        test();
        //doit(1,3);
        // your code goes here
        
    }
    void doit(int x,int y){
        init();
        //dump();
        startNumer=x*x+y*y;
        startX=x;
        startY=y;
        removeStone(7,11);
  
        residual=calcDec();
        while (Math.abs(residual)>epsilon) {
            replaceCell();
            //dumpDecision1();
           // System.out.println(residual);
        }
        //System.out.println("calcDec:"+calcDec());
        dumpDecision();
        
    }
    void doitTest(int x,int y){
        space=new int[2*12*12+1];
        dec=new int[2*12*12+1];
        map=new HashMap<Integer,Integer>();
        diff=new TreeMap<Double,Integer>();
        Dif=new double[10000000];
        
        initSpace();
        genDec();
        //dumpDecision1();
        //dump();
        int num=x*x+y*y;
        //if (dec[num]>0) {
         //   dumpDecision(x,y);
        //    return;
        //}
        startNumer=num;
        startX=x;
        startY=y;
        removeStone(7,11);
        //dec[startNumer]--;
        //addStone(1,1);
        //addStone(x,y);
        residual=calcDec();
        //System.out.println("init done, calcDec:"+calcDec());
        int count=0;
        while (Math.abs(residual)>epsilon && count++ <10) {
          
            replaceCell();
            //System.out.println(residual);
        }
        if (Math.abs(residual)<epsilon) {
            System.out.println("x:"+x+" y:"+y+"  calcDec:"+calcDec());
            dumpDecision1();
        }
        
    }    
    void init(){
        initSpace();
        initDecision();
    }
    void replaceCell(){
        residual=calcDec();
        int key=findCloseLowKey(residual);
        int high=findCloseHighKey(residual);
        double key1=key==-1000?-1000:Dif[key];
        double high1=high==-1000?-1000:Dif[high];
        //System.out.println("replaceCell: "+residual+" from:"+key1+" to:"+high1);
        if (key==-1000) key=high;
        else if (high!=-1000)
            if (Math.abs(Dif[high]-residual)<Math.abs(residual-Dif[key])) 
                key=high;
        //System.out.println("choosed key: "+key+"->"+Dif[key]);    
        int from=map.get(key/10000);
        int to=map.get(key%10000);
        boolean ok=removeStone(from/100,from%100);
        if (!ok) System.out.println("fail to remove "+" "+from/100+" "+from%100);
        addStone(to/100,to%100);
        residual=calcDec();
        //System.out.println("finish replaceCell: "+residual+"->"+calcDec()+" from:"+from+" to:"+to);
    }
    boolean isLastStarter(int key){
        return key==startNumer && dec[key]<=1;
    }
    int findCloseLowKey(double residual){
        Double next=diff.floorKey(residual);
        //System.out.println(residual+" "+next);
        if (next==null) return -1000;
        int key=diff.get(next);
        int from=key/10000;
        int to=key%10000;
        if (next==residual) {
            if (dec[from]==0) return findCloseLowKey(next-epsilon);
            if (dec[to]==space[to]) return findCloseLowKey(next-epsilon);            
             return key;
        }

        //System.out.println(residual+"->"+next);
        if (dec[from]==0) return findCloseLowKey(next);
        if (dec[to]==space[to]) return findCloseLowKey(next);
        return key;
    }
    int findCloseHighKey(double residual){
        Double next=diff.higherKey(residual);
        if (next==null) return -1000;
        int key=diff.get(next);
        int from=key/10000;
        int to=key%10000;
        if (dec[from]==0 ) return findCloseHighKey(next);
        if (dec[to]==space[to]) return findCloseHighKey(next);
        return key;
    }
    void initSpace(){
        for(int i=-12;i<=12;i++){
            for(int j=-12;j<=12;j++){
                int min=Math.min(Math.abs(i),Math.abs(j));
                int max=Math.max(Math.abs(i),Math.abs(j));
                //System.out.print
                space[min*min+max*max]+=1;
                map.put(min*min+max*max,min*100+max);
            }
        }
        for(int i=0;i<=12;i++){
            space[i*i]=0;
        }    
        for(int i=0;i<space.length;i++){
            if (space[i]==0) continue;
            for(int j=0;j<space.length;j++){
                if (space[j]!=0) {
                    int key=10000*i+j;
                    double val=Math.sqrt(i)-Math.sqrt(j);
                    val-=(int) val;
                    Dif[key]=val;
                    diff.put(val,key);
                }
            }
        }
    }
    double calcDec(){
        double res=Math.sqrt(startNumer);
        for(int i=0;i<dec.length;i++){
            if (dec[i]==0) continue;
            res+=Math.sqrt(i)*dec[i];
            //System.out.println("calcDec: "+i+"->"+Math.sqrt(i)+ " res:"+res);
        }
        int resInt=(int) res;
        res-=resInt;
        double negRes=res-1;
        if (Math.abs(negRes)<Math.abs(res)) return negRes;
        return res;
        //return Math.min(res,1.0-res);
    }
    void initDecision(){
        addStone(7,11);
        addStone(1,11);
        addStone(12,2);
        addStone(5,4);
        
        addStone(12,3);
        addStone(10,3);
        addStone(9,6);
        addStone(12,7);
        
        addStone(1,11);
        addStone(6,6);
        addStone(12,4);
        addStone(12,4);

        
    }
    void dumpDecision(){
        //int num=x*x+y*y;
        for(int i=0;i<dec.length;i++){
            if (dec[i]==0) continue;
            int amount=dec[i];
            //System.out.println(numamount)
            int Num=map.get(i);
            int X=Num/100;
            int Y=Num%100;
            for(int j=1;j<=amount;j++){
                dumpCell(X,Y,j);
            }
        }
    }
    void dumpDecision1(){
        int count=0;
        for(int i=0;i<dec.length;i++){
            if (dec[i]==0) continue;
            int amount=dec[i];
            //System.out.println(numamount)
            int Num=map.get(i);
            int X=Num/100;
            int Y=Num%100;
            for(int j=1;j<=amount;j++){
                count++;               
                //dumpCell(X,Y,j);
            }
        }
        System.out.println(" amount:"+count);
    }    
    void dump(){
        dumpSpace();
    }
    void dumpSpace(){
        int count=0;
        for(int i=0;i<space.length;i++){
            if (space[i]==0) continue;
            System.out.println((++count)+" "+i+"->"+space[i]);
        }
    }
    void dumpCell(int x, int y,int  ind){
       if ((x==startX && y==startY) || (y==startX && x==startY)){
           if (!startDone) {
               ind=8;
               startDone=true;
           }
       }
       if (ind==1) System.out.println((-x)+" "+(-y));  
       else if (ind==2) System.out.println((-x)+" "+(y));  
       else if (ind==3) System.out.println((x)+" "+(-y));  
       else if (ind==4) System.out.println((x)+" "+(y));  
       else if (ind==5) System.out.println((-y)+" "+(-x));  
       else if (ind==6) System.out.println((-y)+" "+(x));  
       else if (ind==7) System.out.println((y)+" "+(-x));  
       else if (ind==8) System.out.println((y)+" "+(x));  
    }
    boolean addStone(int x,int y){
        int num=x*x+y*y;
        if (space[num]==0) return false;
        if (dec[num]==space[num]) return false;
        dec[num]+=1;
        return true;
    }    
    boolean removeStone(int x,int y){
        int num=x*x+y*y;
        //System.out.println("removeStone:"+x+" "+y);
        if (dec[num]==0) return false;
        dec[num]-=1;
        return true;
    }    
    public static void main(String[] args) {
        Solution sol=new Solution();
    }
    void genDec(){
    	Random rn = new Random();
    	int n = 12;
    	int count=0;
    	while(count<11) {
	    	int x = rn.nextInt() % n;
	    	int y = rn.nextInt() % n;
	    	if (space[x*x+y*y]==0) continue;
	    	//System.out.println(count+" "+x+" "+y);
	    	addStone(x,y);
	    	count++;
    	}
  	}
    void test(){
    	residual=1.0;
    	int	count=0;
    	double curres=1;
    	while(Math.abs(residual)>epsilon) {
    			doitTest(1,3);
    			if (Math.abs(residual)<curres) curres=Math.abs(residual);
    			if (count++ % 500==0 ) 
    				System.out.println(count+" "+curres);	
    	}
    	dumpDecision();
    	System.out.println("residual"+residual);
    	//dumpDecision();
        /*
        for(int i=0;i<=12;i++){
            for(int j=i;j<=12;j++){
                doitTest(i,j);
            }
        }*/
    }
    
}
