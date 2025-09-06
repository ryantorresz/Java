import java.util.Scanner;

public class Solution {
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int count = 0;
        int n = arr.length;

        // Use three nested loops to iterate through all possible triplets (i, j, k)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check if the first condition is met: |arr[i] - arr[j]| <= a
                if (Math.abs(arr[i] - arr[j]) <= a) {
                    for (int k = j + 1; k < n; k++) {
                        // Check the remaining two conditions for a good triplet
                        if (Math.abs(arr[j] - arr[k]) <= b && Math.abs(arr[i] - arr[k]) <= c) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);

        // Example 1
        System.out.println("--- Example 1 ---");
        int[] arr1 = {3, 0, 1, 1, 9, 7};
        int a1 = 7, b1 = 2, c1 = 3;
        int result1 = solution.countGoodTriplets(arr1, a1, b1, c1);
        System.out.println("Input: arr = " + java.util.Arrays.toString(arr1) + ", a = " + a1 + ", b = " + b1 + ", c = " + c1);
        System.out.println("Output: " + result1); // Expected output: 4
        System.out.println("-----------------");

        // Example 2
        System.out.println("--- Example 2 ---");
        int[] arr2 = {1, 1, 2, 2, 3};
        int a2 = 0, b2 = 0, c2 = 1;
        int result2 = solution.countGoodTriplets(arr2, a2, b2, c2);
        System.out.println("Input: arr = " + java.util.Arrays.toString(arr2) + ", a = " + a2 + ", b = " + b2 + ", c = " + c2);
        System.out.println("Output: " + result2); // Expected output: 0
        System.out.println("-----------------");

        scanner.close();
    }
}