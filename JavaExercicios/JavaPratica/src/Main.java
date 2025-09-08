import java.util.Arrays;

class SegmentTreeWithLazyPropagation {

    private long[] tree;
    private long[] lazy;
    private int n;

    public SegmentTreeWithLazyPropagation(int[] nums) {
        this.n = nums.length;
        this.tree = new long[4 * n];
        this.lazy = new long[4 * n];
        build(nums, 1, 0, n - 1);
    }

    private void build(int[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
            return;
        }
        int mid = (start + end) / 2;
        build(nums, 2 * node, start, mid);
        build(nums, 2 * node + 1, mid + 1, end);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    private void pushDown(int node, int start, int end) {
        if (lazy[node] != 0) {
            tree[node] += (long) (end - start + 1) * lazy[node];
            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }
            lazy[node] = 0;
        }
    }

    public void update(int l, int r, int val) {
        updateRange(1, 0, n - 1, l, r, val);
    }

    private void updateRange(int node, int start, int end, int l, int r, int val) {
        pushDown(node, start, end);

        // Nenhum overlap
        if (start > end || start > r || end < l) {
            return;
        }

        // Total overlap
        if (l <= start && end <= r) {
            tree[node] += (long) (end - start + 1) * val;
            if (start != end) {
                lazy[2 * node] += val;
                lazy[2 * node + 1] += val;
            }
            return;
        }

        // Partial overlap
        int mid = (start + end) / 2;
        updateRange(2 * node, start, mid, l, r, val);
        updateRange(2 * node + 1, mid + 1, end, l, r, val);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    public long query(int l, int r) {
        return queryRange(1, 0, n - 1, l, r);
    }

    private long queryRange(int node, int start, int end, int l, int r) {
        pushDown(node, start, end);

        // Nenhum overlap
        if (start > end || start > r || end < l) {
            return 0;
        }

        // Total overlap
        if (l <= start && end <= r) {
            return tree[node];
        }

        // Partial overlap
        int mid = (start + end) / 2;
        long p1 = queryRange(2 * node, start, mid, l, r);
        long p2 = queryRange(2 * node + 1, mid + 1, end, l, r);
        return p1 + p2;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        SegmentTreeWithLazyPropagation st = new SegmentTreeWithLazyPropagation(nums);

        // Consulta inicial
        System.out.println("Soma do array completo: " + st.query(0, nums.length - 1)); // Expected: 36

        System.out.println("---");

        // Atualizar intervalo [1, 4] com +10
        System.out.println("Atualizando o intervalo [1, 4] com valor 10.");
        st.update(1, 4, 10);
        // O array lógico seria: {1, 12, 13, 14, 15, 6, 7, 8}

        // Consultar soma de um sub-intervalo
        System.out.println("Soma do intervalo [2, 5]: " + st.query(2, 5)); // Expected: 13 + 14 + 15 + 6 = 48

        System.out.println("---");

        // Atualizar outro intervalo [5, 6] com +5
        System.out.println("Atualizando o intervalo [5, 6] com valor 5.");
        st.update(5, 6, 5);
        // Array lógico seria: {1, 12, 13, 14, 15, 11, 12, 8}

        // Consulta final
        System.out.println("Soma do intervalo [0, 7]: " + st.query(0, 7)); // Expected: 1+12+13+14+15+11+12+8 = 86
    }
}