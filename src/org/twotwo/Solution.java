package org.twotwo;

import org.findstrings.Solution3;
import org.findstrings.Solution4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by filipejorge on 08/06/16.
 */
public class Solution {

    public static void main(String[] args) throws FileNotFoundException {

        // long start = System.nanoTime();


        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader("tests/twotwo/input17.txt"));
        Scanner solution = new Scanner(new FileReader("tests/twotwo/output17.txt"));


        int test = Integer.parseInt(sc.nextLine().trim());

        //read Strings
        for (int t = 0; t < test; t++) {
            String str = sc.nextLine();
            //System.out.println(str);
            int countPowerOfTwo = 0;

            int[] suffixArray = SuffixArray.suffixArray(str);
            int[] lcp = SuffixArray.lcp(suffixArray, str);
            for (int k = 0; k < lcp.length; k++) {

                 System.out.println(suffixArray.length);
                for (int i = 0; i < suffixArray.length; i++) {
                    for (int j = i; j < suffixArray.length; j++) {



                        BigInteger strength = strength(str, i, j);
                        //System.out.print(strength + " ");
                        if (isPowerOfTwo(strength) && !strength.equals(BigInteger.ZERO)) {
                            countPowerOfTwo++;
                        }

                    }
                }
                        System.out.println(t + " " + countPowerOfTwo);
            }
            //System.out.print(solution.nextLine()+" ");
            System.out.println(countPowerOfTwo);

        }

        // long end = System.nanoTime();
        // System.out.println((end - start) / 1e9);

    }

    private static BigInteger strength(String str, int i, int j) {


        if (Integer.parseInt(str.charAt(i) + "") == 0) {
            return BigInteger.ZERO; //If first child has value 0 in the group, strength of group is zero
        }
        BigInteger value = BigInteger.ZERO;
        for (int k = i; k <= j; k++) {
            value.add(value.multiply(BigInteger.TEN).add(BigInteger.valueOf(Integer.parseInt(str.charAt(k) + ""))));
            // value = BigInteger.valueOf(value.longValue() * 10L + Integer.parseInt(str.charAt(k) + ""));

        }
        return value;

    }

    private static boolean isPowerOfTwo(BigInteger number) {
        if (number.longValue() < 0) {
            throw new IllegalArgumentException("number: " + number);
        }
        return (number.longValue() & -number.longValue()) == number.longValue();
    }


    public static class SuffixArray {

        // sort suffixes of S in O(n*log(n))
        public static int[] suffixArray(CharSequence S) {
            int n = S.length();
            Integer[] order = new Integer[n];
            for (int i = 0; i < n; i++)
                order[i] = n - 1 - i;

            // stable sort of characters
            Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));

            int[] sa = new int[n];
            int[] classes = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = order[i];
                classes[i] = S.charAt(i);
            }
            // sa[i] - suffix on i'th position after sorting by first len characters
            // classes[i] - equivalence class of the i'th suffix after sorting by first len characters

            for (int len = 1; len < n; len *= 2) {
                int[] c = classes.clone();
                for (int i = 0; i < n; i++) {
                    // condition sa[i - 1] + len < n simulates 0-symbol at the end of the string
                    // a separate class is created for each suffix followed by simulated 0-symbol
                    classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n && c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
                }
                // Suffixes are already sorted by first len characters
                // Now sort suffixes by first len * 2 characters
                int[] cnt = new int[n];
                for (int i = 0; i < n; i++)
                    cnt[i] = i;
                int[] s = sa.clone();
                for (int i = 0; i < n; i++) {
                    // s[i] - order of suffixes sorted by first len characters
                    // (s[i] - len) - order of suffixes sorted only by second len characters
                    int s1 = s[i] - len;
                    // sort only suffixes of length > len, others are already sorted
                    if (s1 >= 0)
                        sa[cnt[classes[s1]]++] = s1;
                }
            }
            return sa;
        }

        // sort rotations of S in O(n*log(n))
        public static int[] rotationArray(CharSequence S) {
            int n = S.length();
            Integer[] order = new Integer[n];
            for (int i = 0; i < n; i++)
                order[i] = i;
            Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));
            int[] sa = new int[n];
            int[] classes = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = order[i];
                classes[i] = S.charAt(i);
            }
            for (int len = 1; len < n; len *= 2) {
                int[] c = classes.clone();
                for (int i = 0; i < n; i++)
                    classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && c[(sa[i - 1] + len / 2) % n] == c[(sa[i] + len / 2) % n] ? classes[sa[i - 1]] : i;
                int[] cnt = new int[n];
                for (int i = 0; i < n; i++)
                    cnt[i] = i;
                int[] s = sa.clone();
                for (int i = 0; i < n; i++) {
                    int s1 = (s[i] - len + n) % n;
                    sa[cnt[classes[s1]]++] = s1;
                }
            }
            return sa;
        }

        // longest common prefixes array in O(n)
        public static int[] lcp(int[] sa, CharSequence s) {
            int n = sa.length;
            int[] rank = new int[n];

            for (int i = 0; i < n; i++)
                rank[sa[i]] = i;
            int[] lcp = new int[n - 1];
            for (int i = 0, h = 0; i < n; i++) {
                if (rank[i] < n - 1) {
                    for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length() && s.charAt(i + h) == s.charAt(j + h); ++h)
                        ;
                    lcp[rank[i]] = h;
                    if (h > 0)
                        --h;
                }
            }
            return lcp;
        }


    }
}
