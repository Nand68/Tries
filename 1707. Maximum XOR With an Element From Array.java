class Solution {
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int n = nums.length;
        int m = queries.length;
        int[] ans = new int[m];
        Trie tries = new Trie();
        Arrays.sort(nums);
        List<int[]> offlinequeries = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            offlinequeries.add(new int[]{queries[i][0], queries[i][1], i});
        }
        int j = 0;
        Collections.sort(offlinequeries, (a,b) -> a[1] - b[1]);
        for(int i = 0; i < m; i++){
            int xi = offlinequeries.get(i)[0];
            int mi = offlinequeries.get(i)[1];
            int index = offlinequeries.get(i)[2];
            while ( j < n && nums[j] <= mi){
                tries.insert(nums[j]);
                j++;
            }
            if(j == 0){
                ans[index] = -1;
            }
            else{
                ans[index] = tries.findMax(xi);
            }
        }
        return ans;
    }
    static class Trie {
        Node root = new Node();

        void insert(int num) {
            Node node = root;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                if (!node.containsKey(bit)) {
                    node.put(bit, new Node());
                }
                node = node.get(bit);
            }
        }

        int findMax(int num) {
            Node node = root;
            int maxNum = 0;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                if (node.containsKey(1 - bit)) {
                    maxNum |= (1 << i);
                    node = node.get(1 - bit);
                } else {
                    node = node.get(bit);
                }
            }
            return maxNum;
        }
    }
    static class Node {
        Node[] links = new Node[2];

        boolean containsKey(int ind) {
            return links[ind] != null;
        }

        Node get(int ind) {
            return links[ind];
        }

        void put(int ind, Node node) {
            links[ind] = node;
        }
    }
}

