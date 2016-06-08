package org.sortTutorial;

/**
 * Created by filipejorge on 26/05/16.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution2. */
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        int n = sc.nextInt();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++) {
            ar[i] = sc.nextInt();
        }

        System.out.println(V);
        System.out.println(n);
        System.out.println(ar);

    }
}
