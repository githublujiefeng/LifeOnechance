package com.learn.算法.util;

import com.learn.算法.TreeNode;

public class ListToTree {
    public TreeNode listToTree(String input){
        String[] inputArr = input.split(",");

        return creatTree(inputArr,1);
    }

    TreeNode creatTree(String[] arr,int index){
        if(index>arr.length||arr[index-1].equals("null")){
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(arr[index-1]));
        node.left = creatTree(arr,2*index);
        node.right  = creatTree( arr, 2 * index + 1);
        return node;
    }
}
