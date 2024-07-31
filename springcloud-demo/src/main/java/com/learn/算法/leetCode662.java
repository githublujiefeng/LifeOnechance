package com.learn.算法;


import javafx.util.Pair;

import java.util.*;

public class leetCode662 {

    public int widthOfBinaryTree(TreeNode root) {
        return bfs(root);
    }

    //广度搜索
    int bfs(TreeNode root) {
        int res = 1;
        List<Pair<TreeNode, Integer>> arr = new ArrayList<Pair<TreeNode, Integer>>();
        arr.add(new Pair<>(root, 1));
        while (!arr.isEmpty()) {
            List<Pair<TreeNode, Integer>> tmp = new ArrayList<>();
            for (Pair<TreeNode, Integer> pair : arr) {
                TreeNode node = pair.getKey();
                int index = pair.getValue();
                if (node.left != null) {
                    tmp.add(new Pair<>(node.left, index * 2));
                }
                if (node.right != null) {
                    tmp.add(new Pair<>(node.right, index * 2 + 1));
                }

            }
            res = Math.max(res, arr.get(arr.size() - 1).getValue() - arr.get(0).getValue() + 1);
            arr = tmp;
        }
        return res;
    }

    //深度搜索
    HashMap<Integer, Integer> map = new HashMap<>();
    int res;

    void dfs(TreeNode root, int level, int index) {
        if (root == null) {
            return;
        }
        if (!map.containsKey(level)) {
            map.put(level, index);
        }
            res = Math.max(res, index - map.get(level) + 1);
        //可能出現index超出int最大值,将index进行缩小
        index = index - map.get(level) + 1;
        dfs(root.left, level + 1, index * 2);
        dfs(root.right, level + 1, index * 2 + 1);
    }
    //写法改造
    int dfs2(TreeNode root, int level, int index) {
        if (root == null) {
            return 0;
        }
        if (!map.containsKey(level)) {
            map.put(level, index);
        }
        return Math.max(index-map.get(level)+1,Math.max(dfs2(root.left,level+1,index*2),
                dfs2(root.right,level+1,index*2+1)));
    }

    void buildTree(int[] nums,TreeNode root){
        int length = nums.length;
        if(length<=0){
            return;
        }else if(length==1){
            root.val = nums[0];
            return;
        }
        int index=-1,max = -1;
        for(int i=0;i<length;i++){
            if(max<nums[i]){
                max = nums[i];
                index = i;
            }
        }
        root.val = max;
        if(index==0){
            TreeNode right = new TreeNode();
            int[] rightArr = new int[length-1];
            System.arraycopy(nums,1,rightArr,0,length-1);
            root.right = right;
            buildTree(rightArr,right);
        }else if(index==length-1){
            TreeNode left = new TreeNode();
            int[] leftArr = new int[length-1];
            System.arraycopy(nums,0,leftArr,0,length-1);
            root.left = left;
            buildTree(leftArr,left);
        }else{
            TreeNode left = new TreeNode();
            root.left = left;
            TreeNode right = new TreeNode();
            root.right = right;
            int[] leftArr = new int[index];
            System.arraycopy(nums,0,leftArr,0,index);
            int[] rightArr = new int[length-index-1];
            System.arraycopy(nums,index+1,rightArr,0,length-index-1);
            buildTree(leftArr,left);
            buildTree(rightArr,right);
            Queue<Integer>queue = new ArrayDeque<Integer>();

        }
    }
    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            int n = nums.length;
            Deque<Integer> stack = new ArrayDeque<Integer>();
            int[] left = new int[n];
            int[] right = new int[n];
            Arrays.fill(left, -1);
            Arrays.fill(right, -1);
            TreeNode[] tree = new TreeNode[n];
            for (int i = 0; i < n; ++i) {
                tree[i] = new TreeNode(nums[i]);
                while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                    right[stack.pop()] = i;
                }
                if (!stack.isEmpty()) {
                    left[i] = stack.peek();
                }
                stack.push(i);
            }

            TreeNode root = null;
            for (int i = 0; i < n; ++i) {
                if (left[i] == -1 && right[i] == -1) {
                    root = tree[i];
                } else if (right[i] == -1 || (left[i] != -1 && nums[left[i]] < nums[right[i]])) {
                    tree[left[i]].right = tree[i];
                } else {
                    tree[right[i]].left = tree[i];
                }
            }
            return root;
        }
    }
}
