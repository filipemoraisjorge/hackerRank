package org.MakeitAnagram;


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


        String strA = sc.nextLine().trim();
        String strB = sc.nextLine().trim();

        int answer = howMuchToTurnAnagram(strA, strB);
        //      String correctAnswer = solution.nextLine();
        //      System.out.print((answer.equals(correctAnswer) ? "" : "## NOT ## "));
        System.out.println(answer);
    }

    private static int howMuchToTurnAnagram(String strA, String strB) {

        int totalDeletions = 0;

        //count quantity for each Char
        Map<Character, Integer> countMapA = frequencyChar(strA);
        Map<Character, Integer> countMapB = frequencyChar(strB);

       // System.out.println("A " + countMapA + " B " + countMapB);

        Iterator<Character> aIt = countMapA.keySet().iterator();
        Iterator<Character> bIt = countMapB.keySet().iterator();

        //verify is the Char Set is the same
            Character cA = null;
            Character cB = null;
        while (aIt.hasNext() || bIt.hasNext()) {
            if (aIt.hasNext()) {
                cA = aIt.next();
            //for strA
            if (!countMapB.containsKey(cA)) {
                totalDeletions += countMapA.get(cA);
                aIt.remove();
            }
            }
            if (bIt.hasNext()) {
                cB = bIt.next();
            //for strB
            if (!countMapA.containsKey(cB)) {
                totalDeletions += countMapB.get(cB);
                bIt.remove();
            }
            }


        }
        //System.out.println(totalDeletions + " " + countMapA + " " + countMapB);
        //verify if char have the same frequency

        for (Character c : countMapA.keySet()) {

            int aFrequency = countMapA.get(c);
            int bFrequency = countMapB.get(c);

            if (aFrequency != bFrequency) {
                totalDeletions += Math.abs(aFrequency - bFrequency);
            }
        }

        return totalDeletions;
    }

    private static Map<Character, Integer> frequencyChar(String s) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            countMap.putIfAbsent(c, 0);
            countMap.replace(c, countMap.get(c) + 1);
        }
        return countMap;
    }
}



