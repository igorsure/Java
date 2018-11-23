package CompetitiveTeams;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
class UF {
	HashSet<Integer> sets;
	TreeMap<Integer,Integer> map;
    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int[] weight;
    private int count;     // number of components

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        weight=new int[n];
        sets=new HashSet<Integer>();
        map=new TreeMap<Integer,Integer>();
        map.put(1,n);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            weight[i]=1;
            sets.add(i);
            rank[i] = 0;
        }
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
  
    /**
     * Merges the component containing site {@code p} with the 
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        int prnt=rootP;
        int child=rootQ;
        if  (rank[rootP] < rank[rootQ]) {
        	prnt=rootQ;
        	child=rootP;
        }
        merge(prnt,child);
        /*
        // make root of smaller rank point to root of larger rank
        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        */
        count--;
    }
    void incrementMap(int key) {
    	if (!map.containsKey(key)) {
    		map.put(key,0);
    	}
    	map.put(key,map.get(key)+1);
    }
    void decrementMap(int key) {
    	map.put(key, map.get(key)-1);
    	if (map.get(key)==0) {
    		map.remove(key);
    	}
    }
    private void merge(int prnt,int child) {
    	if (rank[prnt]==rank[child]) {
    		rank[prnt]++;
    	}
    	parent[child]=prnt;
    	decrementMap(weight[child]);
    	decrementMap(weight[prnt]);
    	
    	weight[prnt]+=weight[child];
    	weight[child]=0;
    	incrementMap(weight[prnt]);
    	sets.remove(child);
    }
    long calcCompetitiveTeams1(int c){
    	long res=0;
    	for(int weight1:map.keySet()) {
    		for(int weight2:map.keySet()) {
    			if (weight2<weight1) continue;
    			if (c==0 && weight1==weight2) {
    				long kol=map.get(weight1);
    				res+=(kol*(kol-1))/2;
    				continue;
    			}
    			if (Math.abs(weight1-weight2)>=c)
    				res+=map.get(weight1)*map.get(weight2);
    		}
    	}
    	return res;
    }
    long calcCompetitiveTeams(int c){
    	int res=0;
    	for(int team1:sets) {
    		for(int team2:sets) {
    			if (team2<=team1) continue;
    			if (Math.abs(weight[team1]-weight[team2])>=c)
    				res++;
    		}
    	}
    	return (long) res;
    }
    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    /**
     * Reads in a an integer {@code n} and a sequence of pairs of integers
     * (between {@code 0} and {@code n-1}) from standard input, where each integer
     * in the pair represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
 
}
public class Solution {
	UF uf;
	Solution(){
        String[] nq = scanner.nextLine().split(" ");

        int n= Integer.parseInt(nq[0]);
        uf=new UF(n);
        int q = Integer.parseInt(nq[1]);
        while(q-- >0) {
        	int type=scanner.nextInt();
        	int x=scanner.nextInt();
        	int y=0;
        	if (type==1) {
        		y=scanner.nextInt();
        		uf.union(x-1,y-1);
        	}
        	else {
        		System.out.println(uf.calcCompetitiveTeams1(x));
        	}
        	//System.out.println(type+" "+x+" "+y);
        }

        scanner.close();
	}

    private  final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	Solution sol=new Solution();
    }
}
