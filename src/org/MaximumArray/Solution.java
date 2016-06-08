package org.MaximumArray;

import java.util.Scanner;

/**
 * Created by filipejorge on 07/06/16.
 */
public class Solution {


    /*  def max_subarray(A):
        max_ending_here = max_so_far = A[0]
        for x in A[1:]:
        max_ending_here = max(x, max_ending_here + x)
        max_so_far = max(max_so_far, max_ending_here)
        return max_so_far
        */
    public static int[] maxSubArray(int[] arr) {
        int[] result = new int[2];

        int firstElem = arr[0];

        int maxEndingHere = firstElem;
        int maxSoFar = firstElem;
        int maxNonContiguous = firstElem;

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            if (arr[i] >= 0) {
                maxNonContiguous = Math.max(arr[i], maxNonContiguous + arr[i]);
            }
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
            maxNonContiguous = Math.max(maxSoFar, maxNonContiguous);
        }

        result[0] = maxSoFar;
        result[1] = maxNonContiguous;
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // First line of the input has an integer .  cases follow.
        int test = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < test; i++) {
            // Each test case begins with an integer . In the next line,  integers follow representing the elements of array .
            int elements = Integer.parseInt(sc.nextLine().trim());
            int[] array = new int[elements];

            for (int j = 0; j < array.length; j++) {
                array[j] = sc.nextInt();

            }
            //to go to next test
            if (sc.hasNext()) {
                sc.nextLine();
            }

            int[] output = maxSubArray(array);

            System.out.println(output[0] + " " + output[1]);
        }

    }

}
