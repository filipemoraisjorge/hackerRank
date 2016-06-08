package org.findstrings;


import java.util.*;

/**
 * Created by filipejorge on 30/05/16.
 */
public class Solution3 {

    public static Set<String> calculateSubStrings(String s) {
        Set<String> result = new TreeSet();


        //Set<String> substrings2 = iterateString(s, 0);

        Set<String> substrings = substringsLCP(s);
        result.addAll(substrings);

        // System.out.println(substrings);
        // System.out.println(substrings2);


        return result;

    }

    public static void calculateSubStrings(String s, Set<String> storingSet) {
        storingSet.addAll(calculateSubStrings(s));

    }

    private static Set<String> substringsLCP(String s) {


        long start = System.nanoTime();
        Set<String> result = new TreeSet<>();


        int[] suffixArray = SuffixArray.suffixArray(s);
        int[] lcp = SuffixArray.lcp(suffixArray, s);

        int len = s.length();

       /* for (int k = 0; k < suffixArray.length; k++) {

            System.out.println(s.substring(suffixArray[k]));
        }*/
        //add first suffix substrings
        for (int j = suffixArray[0] + 1; j <= len; j++) {
            //System.out.print("(" + (suffixArray[0]) + "," + (j) + ") " + s.substring(suffixArray[0], j) + " ");
            result.add(s.substring(suffixArray[0], j));

        }
        //System.out.println();

        //add other suffix and use lcp to calculate substrings
        for (int i = 1; i < suffixArray.length; i++) {

            //String suffix = s.substring(suffixArray[i], len);

            //System.out.print("(" + suffixArray[i] + "," + lcp[i - 1] + ") " + suffix + "-> ");

            //adding substrings
            for (int j = suffixArray[i] + lcp[i - 1] + 1; j <= len; j++) {
                //System.out.print("(" + (suffixArray[i]) + "," + (j) + ") " + s.substring(suffixArray[i], j) + " ");
                result.add(s.substring(suffixArray[i], j));

            }
            //System.out.println();
        }

        //System.out.println();
        System.out.println(result.size());
        //System.out.println(result);


        long end = System.nanoTime();
        System.out.println("lcp  " + (end - start) / 1e9);
        return result;
    }

    private static Set<String> iterateString(String s, int startIndex) {
        long start = System.nanoTime();

        Set<String> result = new TreeSet<>();

        for (int i = startIndex; i <= s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                result.add(s.substring(i, j));
            }
        }

        //System.out.println(result);
        System.out.println(result.size());

        long end = System.nanoTime();

        System.out.println("iter " + (end - start) / 1e9);
        return result;
    }


    private static Set<String> subStringsFromChar(String s, char startChar) {

        Set<String> result = new TreeSet<>();
        int indexOf = s.indexOf(startChar);

        while (indexOf != -1) {

            System.out.println("start = " + indexOf);

            for (int j = indexOf + 1; j <= s.length(); j++) {
                result.add(s.substring(indexOf, j));
            }
            System.out.println(result);
            indexOf = s.indexOf(startChar, indexOf + 1);

        }

        return result;
    }


    public static class WorkerThread implements Runnable {

        private String str;
        private Set<String> allUnique;

        public WorkerThread(String s, Set<String> allUnique) {
            this.str = s;
            this.allUnique = allUnique;
        }

        @Override
        public void run() {
            //System.out.println(Thread.currentThread().getName() + " Start. Command = " + str);
            processCommand();
            //System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {

            calculateSubStrings(str, allUnique);

        }

        @Override
        public String toString() {
            return this.str;
        }
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Set<String> strings = new HashSet<>();
        Set<String> allUnique = new TreeSet<>();


        int numberOfStrings = sc.nextInt();
        sc.nextLine();
        //read Strings


        for (int i = 0; i < numberOfStrings; i++) {
            // strings.add(sc.nextLine().trim());
            // calculate substrings, put on global Set
            //allUnique Set will be lexicographically ordered by default
            String s = sc.nextLine();
            strings = calculateSubStrings(s.trim());
            // System.out.println("s "+s +" => " + strings);
            allUnique.addAll(strings);
        }

/*

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (String str : strings) {

            Runnable worker = new WorkerThread(str, allUnique);

            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }*/
        //System.out.println("Finished all threads");
        //System.out.println(allUnique);






      /*  int[] queries = new int[numberOfQueries];
        for (int i = 0; i < numberOfQueries; i++) {
            queries[i] = sc.nextInt();
        }


        Iterator<String> it = allUnique.iterator();
        while (it.hasNext()) {
            for (int j = 0; j < queries.length; j++) {
                for (int i = 0; i < queries[j]; i++) {
                    it.next();

                }
            System.out.println((query > unique.length ? "INVALID" : unique[query - 1]));

            }

        }*/

        //transform in array to access direct with index
        String[] unique = new String[allUnique.size()];
        unique = allUnique.toArray(unique);


        int numberOfQueries = sc.nextInt();
        //the Nth lexicographically item of this set is the item on N-1 index
        for (int i = 0; i < numberOfQueries; i++) {
            int query = sc.nextInt();
            System.out.println((query > unique.length ? "INVALID" : unique[query - 1]));
        }

    }


    /*   public static void main(String[] args) {

           Scanner sc = new Scanner(System.in);

           Set<String> strings = new HashSet<>();
           Set<String> allUnique = new TreeSet<>();


           int numberOfStrings = sc.nextInt();
           sc.nextLine();

           //store strings
           for (int i = 0; i < numberOfStrings; i++) {
               strings.add(sc.nextLine());
           }

           //int maxSubstringsNumber = n * (n + 1) / 2;

           //store queries
           int numberOfQueries = sc.nextInt();
           int[] queries = new int[numberOfQueries];
           for (int i = 0; i < numberOfQueries; i++) {
               queries[i] = sc.nextInt();
           }




           strings = calculateSubStrings(s.trim());
           allUnique.addAll(strings);

           //transform in array to access direct with index
           String[] unique = new String[allUnique.size()];
           unique = allUnique.toArray(unique);

           System.out.println((query > unique.length ? "INVALID" : unique[query - 1]));

           //the Nth lexicographically item of this set is the item on N-1 index

       }*/
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
