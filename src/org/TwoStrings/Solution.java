package org.TwoStrings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by filipejorge on 07/06/16.
 */
public class Solution {


    public static void main(String[] args) throws FileNotFoundException {

      //  long start = System.nanoTime();

          Scanner sc = new Scanner(System.in);
      //  Scanner sc = new Scanner(new FileReader("tests/twostrings/test5/input05.txt"));
      //  Scanner solution = new Scanner(new FileReader("tests/twostrings/test5/output05.txt"));

        int test = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < test; i++) {

            String a = sc.nextLine().trim();
            String b = sc.nextLine().trim();
            a = uniqueCharacters(a);
            b = uniqueCharacters(b);

            String answer = twoStrings(a, b);
      //      String correctAnswer = solution.nextLine();
      //      System.out.print((answer.equals(correctAnswer) ? "" : "## NOT ## "));
            System.out.println(answer);
        }

     //   long end = System.nanoTime();
    //    System.out.println((end - start) / 1e9);

    }

    private static String uniqueCharacters(String str) {
        Set<Character> uniqueChar = new HashSet<>();

        for (int i = 0; i < str.length() && uniqueChar.size() <= 26; i++) {
            uniqueChar.add(str.charAt(i));
        }

        String result = uniqueChar.toString().replaceAll("\\W","");

        return result;
    }

    private static String twoStrings(String a, String b) {
        int k = 1;
        //  for (int k = 1; k < a.length(); k++) {
        for (int i = 0; i <= a.length() - k; i++) {
            String subA = a.substring(i, i + k);
            for (int j = 0; j < b.length() - k; j++) {
                String subB = b.substring(j, j + k);
                if (subA.equals(subB)) {
                    return "YES";
                }
            }
        }
        //}
        return "NO";
    }
}