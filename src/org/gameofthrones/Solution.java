package org.gameofthrones;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by filipejorge on 08/06/16.
 */
public class Solution {

    public static void main(String[] args) throws FileNotFoundException {

        //  long start = System.nanoTime();

        Scanner sc = new Scanner(System.in);
        //  Scanner sc = new Scanner(new FileReader("tests/twostrings/test5/input05.txt"));
        //  Scanner solution = new Scanner(new FileReader("tests/twostrings/test5/output05.txt"));


        String str = sc.nextLine().trim();

        String answer = isPalindrome(str);
        //      String correctAnswer = solution.nextLine();
        //      System.out.print((answer.equals(correctAnswer) ? "" : "## NOT ## "));
        System.out.println(answer);
    }

    private static String isPalindrome(String str) {

        //count quantity for each Char
        Map<Character, Integer> countMap = new HashMap<>();


        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            countMap.putIfAbsent(c, 0);
            countMap.replace(c, countMap.get(c) + 1);
        }


        Iterator<Integer> totalIt = countMap.values().iterator();
        int totalOdd = 0;
        int totalEven = 0;
        //count even and odd totals
        while (totalIt.hasNext()) {
            if (totalIt.next() % 2 == 0) {
                totalEven++;
            } else {
                totalOdd++;
            }
        }
        //if there is more thant 1 even then it's not a palindrome
        if (totalOdd > 1) {
            return "NO";
        }

        // if all the other char count is even the it is a palindrome
        if (totalEven == countMap.size() - totalOdd) {
            return "YES";
        }

        return null;
    }
}
