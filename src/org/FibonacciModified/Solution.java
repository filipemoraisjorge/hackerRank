package org.FibonacciModified;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by filipejorge on 30/05/16.
 */
public class Solution {


    public static BigInteger fibModified(int a, int b, int n) {
        //  System.out.println("on fib a=" + a + " b=" + b + " n=" + n);
        if (n == 0) {
            return BigInteger.valueOf(a);
        }
        if (n == 1) {
            return BigInteger.valueOf(b);
        }

        /*if (n < 0) {
            //negafibonacci
            System.out.println("NEGA!!!");
            return fibModified(a, b, -n) * (int) Math.pow(-1, n + 1);
        }*/

        BigInteger n1 = fibModified(a, b, n - 1);
        BigInteger n2 = fibModified(a, b, n - 2);


//        System.out.println("n1 " + Math.pow(n1.longValueExact(), 2) + " n2 " + n2);

        //System.out.print("^2 " + ((long)Math.pow(fibModified(a, b, n - 2),2)));
        // System.out.print(" " + "  " + "\n");
        return n1.pow(2).add(n2);


    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();
        int n = sc.nextInt();


            System.out.println((n) + " " + fibModified(a, b, n-1));



    }

}
