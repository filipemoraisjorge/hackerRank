package filipemoraisjorge;

/**
 * Created by filipejorge on 23/05/16.
 */

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String A = sc.next();
        String B = sc.next();
        /* Enter your code here. Print output to STDOUT. */

        System.out.println(sumStringLen(A, B)+"");
        System.out.println((isLexicographicallyBigger(A, B) ? "Yes" : "No"));
        System.out.println(capitalizeFirstLetter(A)+" "+capitalizeFirstLetter(B));


    }

    /**
     * Sum the length of two strings.
     *
     * @param str1
     * @param str2
     * @return
     * @throws NullPointerException
     */
    public static long sumStringLen(String str1, String str2) throws NullPointerException {
        if (str1 == null || str2 == null) {
            throw new NullPointerException();
        }
        return str1.length() + str2.length();
    }

    /**
     * @param str1
     * @param str2
     * @return {@code true} if str1 > str2 lexicographically bigger
     */
    public static boolean isLexicographicallyBigger(String str1, String str2) throws NullPointerException {
        if (str1 == null || str2 == null) {
            throw new NullPointerException();
        }
        return str1.compareTo(str2) > 0;
    }

    /**
     * Capitalize just the first letter.
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) throws NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

}


