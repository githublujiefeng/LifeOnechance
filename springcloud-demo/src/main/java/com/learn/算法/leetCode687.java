package com.learn.算法;

import com.learn.算法.util.ListToTree;

public class leetCode687 {
    int res =0;
    public int longestUnivaluePath(TreeNode root) {
        return 0;
    }
    int dfs(TreeNode root){
        if (root==null){
            return 0;
        }
        int left = dfs(root.left);
        int right = dfs(root.right);
        int leftNum = 0,rightNum=0;
        if(root.left!=null&&root.left.val==root.val){
            leftNum=left+1;
        }
        if(root.right!=null&&root.right.val==root.val){
            rightNum=right+1;
        }
        res = Math.max(res,leftNum+rightNum);
        return Math.max(leftNum,rightNum);

//        if(root.val == val){
//             res =dfs(root.left,root.val,res)+dfs(root.right,root.val,res)+1;
//        }else{
//            if(root.left!=null)
//                left=dfs(root.left,root.left.val,0);
//            if(root.right!=null){
//                right=dfs(root.right,root.right.val,0);
//            }

//        }
    }

    public static void main(String[] args) {
//        ListToTree util = new ListToTree();
//        TreeNode root=util.listToTree("5,4,5,1,1,null,5");
//        leetCode687 test = new leetCode687();
//        System.out.println(test.dfs(root));
        

    }
}
