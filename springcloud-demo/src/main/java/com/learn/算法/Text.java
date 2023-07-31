package com.learn.算法;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Text {
    public class TreeNode {
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
    //深度
    int height;
    public List<List<String>> printTree(TreeNode root) {
        int m = height+1;
        int n = (int) (Math.pow(2,height+1)-1);
        List<List<String >> res = new ArrayList<>(m);
        //填充字符

        return null;
    }

    void inputData(String[][] res,TreeNode root,int r,int c){
        if(root==null){
            //return;
        }
//        res[r][c]=root.val;
//        inputData(res,root.left,r+1,c-Math.pow(2,height-r-1));
//        inputData(res,root.left,r+1,c+Math.pow(2,height-r-1));
    }

    void findhight(TreeNode root,int level){
        if(root==null){
            return ;
        }
        if(height<level){
            height = level;
        }
        findhight(root.left,level+1);
        findhight(root.right,level+1);
    }

    public static void main1(String[] args) {
        Node nodeA = new Node(2);
        nodeA.next = new Node(4);
        Node nodeB = new Node(1);
        nodeB.next = new Node(3);
        nodeB.next.next = new Node(5);
        //判断两个链表第一个节点值，小的作为最终链表
        Node nodeC;
        if(nodeA.val<nodeB.val){
            nodeC = nodeA;
           nodeA = nodeA.next;
        }else{
            nodeC = nodeB;
            nodeB = nodeB.next;
        }
        Node head =nodeC;
        while(nodeA!=null&&nodeB!=null){
            if(nodeA.val<nodeB.val){
                nodeC.next = nodeA;
                nodeC = nodeC.next;
                nodeA = nodeA.next;
            }else{
                nodeC.next = nodeB;
                nodeC = nodeC.next;
                nodeB = nodeB.next;
            }
        }
        if(nodeB!=null){
            nodeC.next = nodeB;
        }else {
            nodeC.next = nodeA;
        }
        while(head!=null){
            System.out.println(head.val);
            head= head.next;
        }
        String m = "aaa";

    }
    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] words = sentence.split(" ");
        for(int i=0;i<words.length;i++){
            if(words[i].startsWith(searchWord)){
                return i;
            }
        }
        return -1;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int length = arr.length;
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        Integer[] m = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        list = IntStream.of(arr).boxed().collect(Collectors.toList());
        arr = list.stream().mapToInt(Integer::intValue).toArray();
        arr =Arrays.stream(m).mapToInt(Integer::intValue).toArray();
        arr =Arrays.stream(m).mapToInt(Integer::valueOf).toArray();
        Integer[] integers = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        integers = list.toArray(new Integer[0]);
        list = Arrays.asList(integers);
        return null;
    }

    public static void main(String[] args) {
        String a = "abc";
        String b = new String("abc");
        System.out.println(a==b);
    }
}
