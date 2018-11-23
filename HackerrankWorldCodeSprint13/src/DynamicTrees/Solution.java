package DynamicTrees;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {
	boolean[] colors;
	Solution(){
        String[] nq = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nq[0]);

        int q = Integer.parseInt(nq[1]);
        colors=new boolean[n];
        for(int i=0;i<n;i++) {
        	
        }
        dynamicTrees(n, q);

        scanner.close();
	}
    // Complete the dynamicTrees function below.
    static void dynamicTrees(int n, int q) {
        // Print the answer for each query of type K. Take the remaining data from the standard input.

    }

    private  final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	Solution sol=new Solution();
    }
}
