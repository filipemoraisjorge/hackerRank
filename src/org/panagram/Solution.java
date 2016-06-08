package org.panagram;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * Created by filipejorge on 26/05/16.
 */


public class Solution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        Set<Character> alphabet = new HashSet<>();

        char[] chars = s.toLowerCase().replaceAll("[^a-z]", "").toCharArray();
        System.out.println(chars);
        for (char c : chars) {
            alphabet.add(c);
        }

        System.out.println((alphabet.size() == 26 ? "panagram" : "not panagram"));

        sc.close();

        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution2. */
    }
}