package org.findstrings;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by filipejorge on 30/05/16.
 */
public class Solution4 {

    private ExecutorService executor;

    public Solution4() {
        executor = Executors.newFixedThreadPool(10);
    }

    public void startThreads(Set<String> strings, Set<String> allUnique) {

        for (String str : strings) {

            Runnable worker = new WorkerThread(str, allUnique);

            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }


    private void substringsLCP(int[] suffixArray, int[] lcp, String s, Set<String> storeSet) {


        long start = System.nanoTime();
        Set<String> result = storeSet;


        int len = s.length();


        long start2 = System.nanoTime();
        //add first suffix substrings
        for (int j = suffixArray[0] + 1; j <= len; j++) {

            result.add(s.substring(suffixArray[0], j));

        }

        long end2 = System.nanoTime();
        System.out.println("first suffix " + (end2 - start2) / 1e9);

        long start3 = System.nanoTime();
        //add other suffix and use lcp to calculate substrings
        for (int i = 1; i < suffixArray.length; i++) {


            //adding substrings
            for (int j = suffixArray[i] + lcp[i - 1] + 1; j <= len; j++) {

                result.add(s.substring(suffixArray[i], j));

            }
        }

        long end3 = System.nanoTime();
        System.out.println("other suffix " + (end3 - start3) / 1e9);
        //System.out.println(result);


        long end = System.nanoTime();
        System.out.println(result.size() + " lcp  " + (end - start) / 1e9);

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


        long end = System.nanoTime();

        System.out.println(result.size() + " iter " + (end - start) / 1e9);
        return result;
    }


    public class WorkerThread implements Runnable {


        private String str;
        private Set<String> allUnique;

        public WorkerThread(String str, Set<String> allUnique) {

            this.str = str;
            this.allUnique = allUnique;
        }

        @Override
        public void run() {
            //System.out.println(Thread.currentThread().getName() + " Start. Command = " + str);
            processCommand();
            //System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {
            // substringsLCP(str, allUnique);

            //calculateSubStrings(str, allUnique);

        }

        @Override
        public String toString() {
            return this.str;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        long start = System.nanoTime();

        //Solution4 solution4 = new Solution4();

        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader("tests/findstrings/test3/input03.txt"));
        Scanner solution = new Scanner(new FileReader("tests/findstrings/test3/output03.txt"));
        Set<String> strings = new HashSet<>();
        Set<String> allUnique = new TreeSet<>();

        Solution4 solution4 = new Solution4();

        int numberOfStrings = sc.nextInt();
        sc.nextLine();

        //read Strings
        for (int i = 0; i < numberOfStrings; i++) {
            // strings.add(sc.nextLine().trim());
            // calculate substrings, put on global Set
            //allUnique Set will be lexicographically ordered by default
            String str = sc.nextLine();
            // Set<String> strings = calculateSubStrings(s.trim());
            //strings.add(str);


            int[] suffixArray = SuffixArray.suffixArray(str);
            int[] lcp = SuffixArray.lcp(suffixArray, str);

            solution4.substringsLCP(suffixArray, lcp, str, allUnique);
        }

        //solution4.startThreads(strings, allUnique);


        //transform in array to access direct with index
        String[] unique = new String[allUnique.size()];
        unique = allUnique.toArray(unique);

//Collections.sort(allUnique);

        int numberOfQueries = sc.nextInt();
        //the Nth lexicographically item of this set is the item on N-1 index
        for (int i = 0; i < numberOfQueries; i++) {
            int query = sc.nextInt();
            String solutionString = solution.nextLine();

            String myString = (query > unique.length ? "INVALID" : unique[query - 1]);
            System.out.print((solutionString.equals(myString) ? "" : "###### FALSE #####  "));
            System.out.println((query > unique.length ? "INVALID" : unique[query - 1]));
        }

        long end = System.nanoTime();
        System.out.println((end - start) / 1e9);
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
