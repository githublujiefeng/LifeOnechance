package com.learn.算法.题目;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class leetCode_1161 {
    class TreeNode{
        int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
    }
    public int maxLevelSum(TreeNode root) {
        List<TreeNode> p0=new ArrayList<TreeNode>();
        int max=root.val;
        int min_level=0;
        p0.add(root);
        for(int level=1;!p0.isEmpty();level++){
            int sum=0;
            List<TreeNode> p1=new ArrayList<TreeNode>();
            for (TreeNode node : p0){
                sum=sum+node.val;
                if(node.left!=null){
                    p1.add(node.left);
                }
                if(node.right!=null){
                    p1.add(node.right);
                }
            }
            if(max<sum){
                min_level = level;
                max = sum;
            }
            p0=p1;
        }
        return min_level;
    }
}
