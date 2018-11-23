package TwoEfficientTeams;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
public class Solution {
	long[] f;
	int[][] teams;
	int n,m;
	HashSet<Integer> first;
	HashSet<HashSet<Integer>> sets;
	PriorityQueue<Set> queue,queue1;
	HashSet<Set> queueSets;
	//counter
	final int SETSLEVEL=3;
	final int QUEUESIZE=10;
	final int ITERATIONS=10;
	static int setCounter=1;
	class Set implements Comparable<Set>{
		long value;
		HashSet<Integer> set;
		int id;
		Set(){
			set=new HashSet<Integer>();
			id=setCounter++;
			//value=calcFirst
		}
		Set(HashSet<Integer> st){
			set=new HashSet<Integer>();
			for(Integer num:st) {
				set.add(num);
			}
			id=setCounter++;
		}
		void add(int val) {
			set.add(val);
		}
		void calcValue() {
			value=calcFirstMembersAllTeams(set);
			
		}
		   @Override
		    public boolean equals(Object obj)
		    {
		         
			    // checking if both the object references are 
			    // referring to the same object.
			    if(this == obj)
			            return true;
		         
		        // it checks if the argument is of the 
		        // type Geek by comparing the classes 
		        // of the passed argument and this object.
		        // if(!(obj instanceof Geek)) return false; ---> avoid.
		        if(obj == null || obj.getClass()!= this.getClass())
		            return false;
		         
		        // type casting of the argument. 
		        HashSet<Integer> st=((Set) obj).set;
		        if (set.size()!=st.size()) return false;
		        for(Integer ind:set) {
		        	if (!st.contains(ind)) return false;
		        }
		        return true;
		    }
		    @Override
		    public int hashCode()
		    {
		         
		        return set.hashCode();
		    }			
		@Override
		public int compareTo(Set st) {
			if (value==st.value) return 0;
			return value<st.value?-1:1;
		}
		public String toString() {
			return set.toString()+hashCode();
		}
	}
	Solution()  throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    	//testSets();

        String[] nm = scanner.nextLine().split(" ");

        n = Integer.parseInt(nm[0]);

        m = Integer.parseInt(nm[1]);
        teams=new int[m][];
        f=new long[m];
        long result = maximumEfficiency(n, m);
        for(int i=0;i<m;i++) {
        	int k=scanner.nextInt();
        	f[i]=scanner.nextInt();
        	teams[i]=new int[k];
        	for(int j=0;j<k;j++) {
        		teams[i][j]=scanner.nextInt();
        	}
        }
        //dumpArr2(teams);
        result=doit1();
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
	}
	long doit1() {
		//System.out.println("doit1");
		createSets();
		//System.out.println(sets);
		queue.add(new Set());
		
		for(int i=0;i<ITERATIONS;i++) {
			queue1=new PriorityQueue<Set>();
			//System.out.println(i);
			while(!queue.isEmpty()) {
				Set next=queue.remove();
				queue1.add(next);
				//System.out.println("next:"+next);
				for(HashSet<Integer> set:sets) {
					Set newSet=new Set(next.set);
					newSet.set.addAll(set);
					if (newSet.set.size()==n) continue;
					//System.out.println("  new set:"+set);
					newSet.calcValue();
					addSetToQueue(newSet,queue1);
				}
			}
			queue=queue1;
			//System.out.println(queue);
		}
		//return 0;
		return getResult();
	}
	long doit() {
		long result=0;
		result=Math.max(result, doitOne());
		result=Math.max(result, doitTwo());
		if (n>2)
			result=Math.max(result, doitTwo());
		if (n>3)
			result=Math.max(result, doitTwo());
		return result;
	}
	private long doitOne() {
		long res=0;
		first=new HashSet<Integer>();
		for(int i=1;i<=n;i++) {
			 first.add(i);
			 long resCurrent=calcFirstMembersAllTeams(first);
		     first.remove(i);
		     res=Math.max(res,resCurrent);
		}
		return res;
	}
	private long doitTwo() {
		long res=0;
		first=new HashSet<Integer>();
		for(int i1=1;i1<=n;i1++) {
			first.add(i1);
			for(int i2=i1+1;i2<=n;i2++) {
				 first.add(i2);
				 long resCurrent=calcFirstMembersAllTeams(first);
			     res=Math.max(res,resCurrent);
			     first.remove(i2);
			}
		     first.remove(i1);
		}
		return res;
	}
	private long doitThree() {
		long res=0;
		first=new HashSet<Integer>();
		for(int i1=1;i1<=n;i1++) {
			first.add(i1);
			for(int i2=i1+1;i2<=n;i2++) {
				 first.add(i2);
				 for(int i3=i2+1;i3<=n;i3++) {
					 first.add(i3);
					 long resCurrent=calcFirstMembersAllTeams(first);
				     res=Math.max(res,resCurrent);
				     first.remove(i3);
				 }
			     first.remove(i2);
			}
		     first.remove(i1);
		}
		return res;
	}
	private long doitFour() {
		long res=0;
		first=new HashSet<Integer>();
		for(int i1=1;i1<=n;i1++) {
			first.add(i1);
			for(int i2=i1+1;i2<=n;i2++) {
				 first.add(i2);
				 for(int i3=i2+1;i3<=n;i3++) {
					 first.add(i3);
                     for(int i4=i3+1;i4<=n;i4++) {
                         first.add(i4);
					     long resCurrent=calcFirstMembersAllTeams(first);
				         res=Math.max(res,resCurrent);
                         first.remove(i4);
                     }
				     first.remove(i3);
				 }
			     first.remove(i2);
			}
		     first.remove(i1);
		}
		return res;
	}
	private long calcFirstMembersAllTeams(HashSet<Integer> frst) {
		 long resCurrent=0;
		 for(int j=0;j<m;j++) {
		     int amount=calcFirstMembers(teams[j],frst);
		     if (amount==0 || amount==teams[j].length) {
		    	 resCurrent+=f[j];
		     }
		 }
		 return resCurrent;
	}
	private int calcFirstMembers(int[] team,HashSet<Integer> frst) {
		int res=0;
		for(int i=0;i<team.length;i++) {
			if (frst.contains(team[i])){
				res++;
			}
		}
		return res;
	}	
	void dumpArr(int[] ar) {
		for(int i=0;i<ar.length;i++) {
			System.out.print(ar[i]+" ");
		}
		System.out.println("");
	}
	void dumpArr2(int[][] ar) {
		for(int i=0;i<ar.length;i++) {
			dumpArr(ar[i]);
		}
		System.out.println("");
	}
    // Complete the maximumEfficiency function below.
     long maximumEfficiency(int n, int m) {

    	 return 0;	
    }
    void initSets() {
    	queue=new PriorityQueue<Set>();
    	queueSets=new HashSet<Set>();
    	sets=new HashSet<HashSet<Integer>>();
    	for(int i=1;i<=n;i++) {
    		HashSet<Integer> set=new HashSet<Integer>();
    		set.add(i);
    		sets.add(set);
    		
    	}
    }
    void addSetToQueue(Set set,PriorityQueue<Set> q) {
    	if (queueSets.contains(set)) return;
    	//System.out.println("");
    	//System.out.println(queueSets+" not contains "+set);
    	q.add(set);
    	queueSets.add(set);
    	if (q.size()>QUEUESIZE) {
    		Set removedSet=q.remove();
    		queueSets.remove(removedSet);
    	}
    }
    long getResult() {
    	long res=0;
    	while(!queue.isEmpty()) {
    		res=Math.max(res, queue.peek().value);
    		queue.remove();
    	}
    	return res;
    }
    void createSets() {
    	initSets();
    	for(int i=2;i<=SETSLEVEL;i++) {
    		createSetsStep();
    	}
    }
    private void createSetsStep() {
    	HashSet<HashSet<Integer>> sets1=new HashSet<HashSet<Integer>>();
		for(int i=1;i<=n;i++) {
			for(HashSet<Integer> set:sets) {
				HashSet<Integer> nextSet=new HashSet<Integer>();
				nextSet.add(i);
				for(Integer ind:set) {
					nextSet.add(ind);
				}
				sets1.add(nextSet);
			}
		}
		sets.addAll(sets1);
		
	}
	private final Scanner scanner = new Scanner(System.in);
	void testSets(){
		Set s1=new Set();
		s1.add(3);
		s1.add(5);
		s1.add(7);
		s1.add(3);
		s1.add(9);
		
		Set s2=new Set();
		s2.add(9);
		s2.add(5);
		s2.add(7);
		s2.add(3);
		s2.add(9);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s1.equals(s2));
		
		HashSet<Set> ss=new HashSet<Set>();
		ss.add(s1);
		ss.add(s2);
		System.out.println(ss);
		
	}
    public static void main(String[] args) throws IOException {
    	Solution sol=new Solution();
    	
   }
}