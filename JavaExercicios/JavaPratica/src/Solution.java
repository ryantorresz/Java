import java.util.Arrays;

public class Solution {

    /**
     * O método principal para contar pares justos no intervalo [lower, upper].
     * Ele utiliza o princípio de inclusão-exclusão com uma função auxiliar de dois ponteiros.
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        // Passo 1: Ordene o array. Isso é fundamental para a abordagem de dois ponteiros.
        Arrays.sort(nums);

        // Passo 2: A resposta é (pares com soma <= upper) - (pares com soma < lower).
        // Um par com soma < lower é equivalente a um par com soma <= lower - 1.
        return countPairsHelper(nums, upper) - countPairsHelper(nums, lower - 1);
    }

    /**
     * Método auxiliar para contar o número de pares (i, j) com i < j
     * tal que nums[i] + nums[j] <= target.
     */
    private long countPairsHelper(int[] nums, int target) {
        long count = 0;
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            long sum = (long) nums[left] + nums[right];

            if (sum <= target) {
                // Se a soma atual é válida, todos os pares de (left, left+1)
                // até (left, right) também terão uma soma menor ou igual ao alvo
                // porque o array está ordenado.
                count += (right - left);
                left++; // Mova o ponteiro esquerdo para encontrar novos pares.
            } else {
                // Se a soma for muito grande, diminua-a movendo o ponteiro direito.
                right--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Exemplo 1:
        int[] nums1 = {0, 1, 7, 4, 4, 5};
        int lower1 = 3;
        int upper1 = 6;
        long result1 = solution.countFairPairs(nums1, lower1, upper1);
        System.out.println("Exemplo 1:");
        System.out.println("Input: nums = " + Arrays.toString(nums1) + ", lower = " + lower1 + ", upper = " + upper1);
        System.out.println("Output: " + result1); // Esperado: 6
        System.out.println("--------------------");

        // Exemplo 2:
        int[] nums2 = {1, 7, 9, 2, 5};
        int lower2 = 11;
        int upper2 = 11;
        long result2 = solution.countFairPairs(nums2, lower2, upper2);
        System.out.println("Exemplo 2:");
        System.out.println("Input: nums = " + Arrays.toString(nums2) + ", lower = " + lower2 + ", upper = " + upper2);
        System.out.println("Output: " + result2); // Esperado: 1
        System.out.println("--------------------");

        // Exemplo com array vazio (para testar casos de borda)
        int[] nums3 = {};
        long result3 = solution.countFairPairs(nums3, 0, 10);
        System.out.println("Exemplo 3 (Array Vazio):");
        System.out.println("Input: nums = " + Arrays.toString(nums3) + ", lower = " + 0 + ", upper = " + 10);
        System.out.println("Output: " + result3); // Esperado: 0
    }
}