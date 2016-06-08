package org.findstrings;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Arrays;

/**
 * Created by filipejorge on 30/05/16.
 */
public class Solution {

    public static Set<String> calculateSubStrings(String s) {
        Set<String> result = new TreeSet();


        //Set<String> s1 = subStringsFromChar(s, (char) i);
        Set<String> substrings = iterateString(s, 0);

        result.addAll(substrings);

        //System.out.println(substrings);


        return result;

    }

    public static void calculateSubStrings(String s, Set<String> storingSet) {
        storingSet.addAll(calculateSubStrings(s));

    }

    private static Set<String> iterateString(String s, int startIndex) {
        Set<String> result = new TreeSet<>();
        long start = System.nanoTime();

        for (int i = startIndex; i <= s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                result.add(s.substring(i, j));
            }
        }

        long end = System.nanoTime();

        System.out.println((end - start) / 1e9);
        return result;
    }


    private static Set<String> subStringsFromChar(String s, char startChar) {
        Set<String> result = new TreeSet();

        int indexOf = s.indexOf(startChar);

        while (indexOf != -1) {

            //System.out.println("start = " + indexOf);

            for (int j = indexOf + 1; j <= s.length(); j++) {
                result.add(s.substring(indexOf, j));
            }

            indexOf = s.indexOf(startChar, indexOf + 1);

        }
        return result;
    }


    /*public static class WorkerThread implements Runnable {

        private String str;
        private Set<String> allUnique;
        private char c;

        public WorkerThread(String s, char c, Set<String> allUnique) {
            this.str = s;
            this.c = c;
            this.allUnique = allUnique;
        }

        @Override
        public void run() {
            //System.out.println(Thread.currentThread().getName() + " Start. Command = " + str);
            processCommand();
            //System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {

            subStringsFromChar(str, c);

        }

        @Override
        public String toString() {
            return this.str;
        }
    }
    */


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Set<String> strings = new HashSet<>();
        Set<String> allUnique = new TreeSet<>();

        //read strings
        int numberOfStrings = sc.nextInt();
        sc.nextLine();
        //read Strings
        for (int i = 0; i < numberOfStrings; i++) {
            strings.add(sc.nextLine().trim());
        }

        //read queries
        int numberOfQueries = sc.nextInt();
        int[] queries = new int[numberOfQueries];
        //the Nth lexicographically item of this set is the item on N-1 index
        for (int j = 0; j < numberOfQueries; j++) {
            queries[j] = sc.nextInt();

            ExecutorService executor = Executors.newFixedThreadPool(4);

            //calc only for one letter, and check if queries got answered.

            String[] unique = new String[allUnique.size()];

            for (int i = 97; i <= 122; i++) {

                char c = (char) i;

                for (String str : strings) {

                    allUnique.addAll(subStringsFromChar(str, c));

                }


                //transform in array to access direct with index
                unique = allUnique.toArray(unique);

                List<String> answers = new ArrayList();

                //the Nth lexicographically item of this set is the item on N-1 index
                for (int k = 0; k < numberOfQueries; k++) {
                    int query = queries[k];
                    if (query > unique.length) {
                        continue;
                    } else {
                        answers.add(k, unique[query-1]);
                    }
                }

                //check if all answered.
                if (answers.size() != numberOfQueries) {
                    break;

                }

            }
            for (int query :
                    queries) {

                System.out.println((query > unique.length ? "INVALID" : unique[query]));

            }

        }
    }

    /******************************************************************************
     *  Compilation:  javac SuffixArray.java
     *  Execution:    java SuffixArray < input.txt
     *  Dependencies: StdIn.java StdOut.java
     *
     *  A data type that computes the suffix array of a string.
     *
     *   % java SuffixArray < abra.txt
     *    i ind lcp rnk  select
     *   ---------------------------
     *    0  11   -   0  "!"
     *    1  10   0   1  "A!"
     *    2   7   1   2  "ABRA!"
     *    3   0   4   3  "ABRACADABRA!"
     *    4   3   1   4  "ACADABRA!"
     *    5   5   1   5  "ADABRA!"
     *    6   8   0   6  "BRA!"
     *    7   1   3   7  "BRACADABRA!"
     *    8   4   0   8  "CADABRA!"
     *    9   6   0   9  "DABRA!"
     *   10   9   0  10  "RA!"
     *   11   2   2  11  "RACADABRA!"
     *
     *  See SuffixArrayX.java for an optimized version that uses 3-way
     *  radix quicksort and does not use the nested class Suffix.
     *
     ******************************************************************************/



    /**
     *  The <tt>SuffixArray</tt> class represents a suffix array of a string of
     *  length <em>N</em>.
     *  It supports the <em>selecting</em> the <em>i</em>th smallest suffix,
     *  getting the <em>index</em> of the <em>i</em>th smallest suffix,
     *  computing the length of the <em>longest common prefix</em> between the
     *  <em>i</em>th smallest suffix and the <em>i</em>-1st smallest suffix,
     *  and determining the <em>rank</em> of a query string (which is the number
     *  of suffixes strictly less than the query string).
     *  <p>
     *  This implementation uses a nested class <tt>Suffix</tt> to represent
     *  a suffix of a string (using constant time and space) and
     *  <tt>Arrays.sort()</tt> to sort the array of suffixes.
     *  The <em>index</em> and <em>length</em> operations takes constant time
     *  in the worst case. The <em>lcp</em> operation takes time proportional to the
     *  length of the longest common prefix.
     *  The <em>select</em> operation takes time proportional
     *  to the length of the suffix and should be used primarily for debugging.
     *  <p>
     *  For alternate implementations of the same API, see
     *  {@link SuffixArrayX}, which is faster in practice (uses 3-way radix quicksort)
     *  and uses less memory (does not create <tt>Suffix</tt> objects)
     *  and <a href = "http://algs4.cs.princeton.edu/63suffix/SuffixArrayJava6.java.html">SuffixArrayJava6.java</a>,
     *  which relies on the constant-time substring extraction method that existed
     *  in Java 6.
     *  <p>
     *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/63suffix">Section 6.3</a> of
     *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
     */
    public class SuffixArray {
        private Suffix[] suffixes;

        /**
         * Initializes a suffix array for the given <tt>text</tt> string.
         * @param text the input string
         */
        public SuffixArray(String text) {
            int N = text.length();
            this.suffixes = new Suffix[N];
            for (int i = 0; i < N; i++)
                suffixes[i] = new Suffix(text, i);
            Arrays.sort(suffixes);
        }

        private class Suffix implements Comparable<Suffix> {
            private final String text;
            private final int index;

            private Suffix(String text, int index) {
                this.text = text;
                this.index = index;
            }
            private int length() {
                return text.length() - index;
            }
            private char charAt(int i) {
                return text.charAt(index + i);
            }

            public int compareTo(Suffix that) {
                if (this == that) return 0;  // optimization
                int N = Math.min(this.length(), that.length());
                for (int i = 0; i < N; i++) {
                    if (this.charAt(i) < that.charAt(i)) return -1;
                    if (this.charAt(i) > that.charAt(i)) return +1;
                }
                return this.length() - that.length();
            }

            public String toString() {
                return text.substring(index);
            }
        }

        /**
         * Returns the length of the input string.
         * @return the length of the input string
         */
        public int length() {
            return suffixes.length;
        }


        /**
         * Returns the index into the original string of the <em>i</em>th smallest suffix.
         * That is, <tt>text.substring(sa.index(i))</tt> is the <em>i</em>th smallest suffix.
         * @param i an integer between 0 and <em>N</em>-1
         * @return the index into the original string of the <em>i</em>th smallest suffix
         * @throws java.lang.IndexOutOfBoundsException unless 0 &le; <em>i</em> &lt; <Em>N</em>
         */
        public int index(int i) {
            if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
            return suffixes[i].index;
        }


        /**
         * Returns the length of the longest common prefix of the <em>i</em>th
         * smallest suffix and the <em>i</em>-1st smallest suffix.
         * @param i an integer between 1 and <em>N</em>-1
         * @return the length of the longest common prefix of the <em>i</em>th
         * smallest suffix and the <em>i</em>-1st smallest suffix.
         * @throws java.lang.IndexOutOfBoundsException unless 1 &le; <em>i</em> &lt; <em>N</em>
         */
        public int lcp(int i) {
            if (i < 1 || i >= suffixes.length) throw new IndexOutOfBoundsException();
            return lcp(suffixes[i], suffixes[i-1]);
        }

        // longest common prefix of s and t
        private int lcp(Suffix s, Suffix t) {
            int N = Math.min(s.length(), t.length());
            for (int i = 0; i < N; i++) {
                if (s.charAt(i) != t.charAt(i)) return i;
            }
            return N;
        }

        /**
         * Returns the <em>i</em>th smallest suffix as a string.
         * @param i the index
         * @return the <em>i</em> smallest suffix as a string
         * @throws java.lang.IndexOutOfBoundsException unless 0 &le; <em>i</em> &lt; <Em>N</em>
         */
        public String select(int i) {
            if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
            return suffixes[i].toString();
        }

        /**
         * Returns the number of suffixes strictly less than the <tt>query</tt> string.
         * We note that <tt>rank(select(i))</tt> equals <tt>i</tt> for each <tt>i</tt>
         * between 0 and <em>N</em>-1.
         * @param query the query string
         * @return the number of suffixes strictly less than <tt>query</tt>
         */
        public int rank(String query) {
            int lo = 0, hi = suffixes.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, suffixes[mid]);
                if (cmp < 0) hi = mid - 1;
                else if (cmp > 0) lo = mid + 1;
                else return mid;
            }
            return lo;
        }

        // compare query string to suffix
        private int compare(String query, Suffix suffix) {
            int N = Math.min(query.length(), suffix.length());
            for (int i = 0; i < N; i++) {
                if (query.charAt(i) < suffix.charAt(i)) return -1;
                if (query.charAt(i) > suffix.charAt(i)) return +1;
            }
            return query.length() - suffix.length();
        }

        /**
         * Unit tests the <tt>SuffixArray</tt> data type.
         */
       /* public static void main(String[] args) {
            String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
            SuffixArray suffix = new SuffixArray(s);

            // StdOut.println("rank(" + args[0] + ") = " + suffix.rank(args[0]));

            StdOut.println("  i ind lcp rnk select");
            StdOut.println("---------------------------");

            for (int i = 0; i < s.length(); i++) {
                int index = suffix.index(i);
                String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
                assert s.substring(index).equals(suffix.select(i));
                int rank = suffix.rank(s.substring(index));
                if (i == 0) {
                    StdOut.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
                }
                else {
                    int lcp = suffix.lcp(i);
                    StdOut.printf("%3d %3d %3d %3d %s\n", i, index, lcp, rank, ith);
                }
            }
        }*/

    }
}



