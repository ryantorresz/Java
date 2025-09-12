public class longestCommonPrefix {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Exemplo 1
        String[] strs1 = {"flower", "flow", "flight"};
        System.out.println("Exemplo 1: " + sol.longestCommonPrefix(strs1));

        // Exemplo 2
        String[] strs2 = {"dog", "racecar", "car"};
        System.out.println("Exemplo 2: " + sol.longestCommonPrefix(strs2));

        // Exemplo extra
        String[] strs3 = {"interspecies","interstellar","interstate"};
        System.out.println("Exemplo 3: " + sol.longestCommonPrefix(strs3));

        // Exemplo com apenas uma palavra
        String[] strs4 = {"alone"};
        System.out.println("Exemplo 4: " + sol.longestCommonPrefix(strs4));

        // Exemplo com string vazia
        String[] strs5 = {"","b","c"};
        System.out.println("Exemplo 5: " + sol.longestCommonPrefix(strs5));
    }
}

// A classe Solution com o método
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }
}