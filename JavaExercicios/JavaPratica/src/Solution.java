import java.io.IOException;

public class Solution {
    private static final int MOD = 1_000_000_007;

    public static long countGoodNumbers(long n) {
        long evenPositions = (n + 1) / 2;
        long oddPositions = n / 2;

        long evenChoices = power(5, evenPositions);
        long oddChoices = power(4, oddPositions);

        return (evenChoices * oddChoices) % MOD;
    }

    private static long power(long base, long exp) {
        long res = 1;
        base %= MOD;

        while (exp > 0) {
            if ((exp % 2) == 1) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        // Example 1: n = 1
        System.out.println("Input: n = 1");
        System.out.println("Output: " + countGoodNumbers(1)); // Expected: 5

        // Example 2: n = 4
        System.out.println("Input: n = 4");
        System.out.println("Output: " + countGoodNumbers(4)); // Expected: 400

        // Example 3: n = 50
        System.out.println("Input: n = 50");
        System.out.println("Output: " + countGoodNumbers(50)); // Expected: 564908303
    }
}