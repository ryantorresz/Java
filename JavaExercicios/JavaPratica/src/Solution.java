public class Solution {
    public int makeTheIntegerZero(int num1, int num2) {
        for (int k = 1; k <= 60; k++) {
            long target = (long) num1 - (long) num2 * k;
            if (target >= k) {
                long setBits = Long.bitCount(target);
                if (setBits <= k) {
                    return k;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Casos de teste
        int[][] testCases = {
                {5, 3},        // Esperado: 2
                {3, -2},       // Esperado: 3
                {10, 1},       // Esperado: 4
                {100, 5},      // Esperado: 5
                {0, 0},        // Esperado: 0? (caso especial)
                {-5, 2},       // Esperado: -1?
                {15, -3}       // Esperado: ?
        };

        System.out.println("Testando makeTheIntegerZero:");
        System.out.println("num1\tnum2\tResultado");
        System.out.println("----------------------------");

        for (int[] testCase : testCases) {
            int num1 = testCase[0];
            int num2 = testCase[1];
            int result = solution.makeTheIntegerZero(num1, num2);
            System.out.printf("%d\t%d\t%d%n", num1, num2, result);
        }

        // Teste adicional com valores específicos
        System.out.println("\nTestes adicionais:");
        System.out.println("makeTheIntegerZero(5, 3) = " + solution.makeTheIntegerZero(5, 3));
        System.out.println("makeTheIntegerZero(3, -2) = " + solution.makeTheIntegerZero(3, -2));
        System.out.println("makeTheIntegerZero(10, 1) = " + solution.makeTheIntegerZero(10, 1));
    }
}