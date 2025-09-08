import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Exemplo 1: nums = [1,1,1,1,1], k = 10
        int[] nums1 = {1, 1, 1, 1, 1};
        int k1 = 10;
        long result1 = solution.goodSubarrays(nums1, k1);
        System.out.println("Input: nums = " + Arrays.toString(nums1) + ", k = " + k1);
        System.out.println("Number of good subarrays: " + result1);

        System.out.println("---");

        // Exemplo 2: nums = [3,1,4,3,2,2,4], k = 2
        int[] nums2 = {3, 1, 4, 3, 2, 2, 4};
        int k2 = 2;
        long result2 = solution.goodSubarrays(nums2, k2);
        System.out.println("Input: nums = " + Arrays.toString(nums2) + ", k = " + k2);
        System.out.println("Number of good subarrays: " + result2);

        System.out.println("---");

        // Exemplo adicional para teste
        int[] nums3 = {1, 2, 3};
        int k3 = 3;
        long result3 = solution.goodSubarrays(nums3, k3);
        System.out.println("Input: nums = " + Arrays.toString(nums3) + ", k = " + k3);
        System.out.println("Number of good subarrays: " + result3);
    }
}

class Solution {
    public long goodSubarrays(int[] nums, int k) {
        int n = nums.length;
        long count = 0;

        // Abordagem de força bruta: verificar todos os subarrays possíveis
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }
}