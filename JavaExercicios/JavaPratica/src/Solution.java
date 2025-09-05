import java.util.Scanner;

class Solution {
    public int countSymmetricIntegers(int low, int high) {
        int count = 0;
        for (int x = low; x <= high; x++) {
            if (isSymmetric(x)) {
                count++;
            }
        }
        return count;
    }

    private boolean isSymmetric(int x) {
        // Convert number to string to process digits
        String s = String.valueOf(x);
        int n = s.length();

        // Only consider numbers with even number of digits
        if (n % 2 != 0) {
            return false;
        }

        // Calculate sum of first n/2 digits and last n/2 digits
        int firstHalfSum = 0, secondHalfSum = 0;
        for (int i = 0; i < n / 2; i++) {
            firstHalfSum += s.charAt(i) - '0';
            secondHalfSum += s.charAt(n - 1 - i) - '0';
        }

        return firstHalfSum == secondHalfSum;
    }

    // Main method to test the solution
    public static void main(String[] args) {
        Solution solution = new Solution();
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.println("Enter the value of low:");
        int low = scanner.nextInt();
        System.out.println("Enter the value of high:");
        int high = scanner.nextInt();

        // Validate input based on constraints
        if (low < 1 || high > 10000 || low > high) {
            System.out.println("Invalid input: Ensure 1 <= low <= high <= 10^4");
        } else {
            // Call the solution and print result
            int result = solution.countSymmetricIntegers(low, high);
            System.out.println("Number of symmetric integers: " + result);
        }

        // Optional: Test with example cases
        System.out.println("\nRunning example test cases:");
        System.out.println("Example 1: low = 1, high = 100");
        System.out.println("Result: " + solution.countSymmetricIntegers(1, 100)); // Should print 9
        System.out.println("Example 2: low = 1200, high = 1230");
        System.out.println("Result: " + solution.countSymmetricIntegers(1200, 1230)); // Should print 4

        scanner.close();
    }
}