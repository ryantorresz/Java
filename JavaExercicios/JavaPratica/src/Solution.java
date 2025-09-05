import java.util.HashSet;
import java.util.Set;

class Solution {
    public int minOperations(int[] nums, int k) {
        // Check if any element is less than k
        for (int num : nums) {
            if (num < k) {
                return -1;
            }
        }

        // Use a set to collect distinct numbers greater than k
        Set<Integer> distinctAboveK = new HashSet<>();
        for (int num : nums) {
            if (num > k) {
                distinctAboveK.add(num);
            }
        }

        return distinctAboveK.size();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1: nums = [5,2,5,4,5], k = 2 -> Output: 2
        int[] nums1 = {5, 2, 5, 4, 5};
        int k1 = 2;
        System.out.println("Test case 1: " + sol.minOperations(nums1, k1)); // Expected: 2

        // Test case 2: nums = [2,1,2], k = 2 -> Output: -1
        int[] nums2 = {2, 1, 2};
        int k2 = 2;
        System.out.println("Test case 2: " + sol.minOperations(nums2, k2)); // Expected: -1

        // Test case 3: nums = [9,7,5,3], k = 1 -> Output: 4
        int[] nums3 = {9, 7, 5, 3};
        int k3 = 1;
        System.out.println("Test case 3: " + sol.minOperations(nums3, k3)); // Expected: 4

        // Additional test case: nums = [10, 10, 10], k = 10 -> Output: 0
        int[] nums4 = {10, 10, 10};
        int k4 = 10;
        System.out.println("Test case 4: " + sol.minOperations(nums4, k4)); // Expected: 0

        // Additional test case: nums = [3, 3, 3], k = 2 -> Output: 1
        int[] nums5 = {3, 3, 3};
        int k5 = 2;
        System.out.println("Test case 5: " + sol.minOperations(nums5, k5)); // Expected: 1
    }
}