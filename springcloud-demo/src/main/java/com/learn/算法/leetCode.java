package com.learn.算法;

import java.util.*;

public class leetCode {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {
        public boolean isValidBST(TreeNode root) {
            if (root == null)
                return false;
//            boolean b=_binarySearch(root, 0,0);
//            return b;
            ArrayList<Integer> list = new ArrayList<>();
            inOrder(root, list);
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) >= list.get(i + 1))
                    return false;
            }
            return true;
        }

        void inOrder(TreeNode root, ArrayList<Integer> list) {
            if (root == null)
                return;
            inOrder(root.left, list);
            list.add(root.val);
            inOrder(root.right, list);
        }

        boolean _binarySearch(TreeNode node, Integer lower, Integer upper) {
            TreeNode left = node.left;
            TreeNode right = node.right;
            if (left != null && left.val >= node.val) {
                return false;
            }
            if (right != null && right.val <= node.val) {
                return false;
            }
            if (left != null) {
                return _binarySearch(left, lower, node.val);
            }
            if (right != null) {
                return _binarySearch(right, node.val, upper);
            }
            return false;
        }
    }

    class Codec  {
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null)
                return null;
            ArrayList list = new ArrayList<String>();
            int high = deep(root);
            //System.out.println(high);
            list.add(root.val + "");
            keep(root, list, --high);
            System.out.print(list);
            return String.valueOf(list);
        }

        void keep(TreeNode root, ArrayList<String> list, int high) {
            if (root == null) {
                //list.add(null);
                return;
            }
            //list.add(root.val+"");
            if (root.left != null)
                list.add(String.valueOf(root.left.val));
            else if (high > 0)
                list.add(null);
            if (root.right != null)
                list.add(root.right.val + "");
            else if (high > 0)
                list.add(null);
            keep(root.left, list, --high);
            keep(root.right, list, --high);
        }

        int deep(TreeNode root) {
            if (root == null)
                return 0;
            return Math.max(deep(root.left), deep(root.right)) + 1;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            return null;
        }

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder.length == 0 || inorder.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(preorder[0]);
            for (int i = 0; i < inorder.length; i++) {
                if (inorder[i] == preorder[0]) {
                    root.left = buildTree(Arrays.copyOfRange(preorder, 1, i + 1), Arrays.copyOfRange(inorder, 0, i + 1));
                    root.right = buildTree(Arrays.copyOfRange(preorder, i + 1, inorder.length), Arrays.copyOfRange(inorder, i + 1, inorder.length));
                    break;
                }
            }
            return root;
        }
    }

    TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode pNode = lowestCommonAncestor(root.left, p, q);
        TreeNode qNode = lowestCommonAncestor(root.right, p, q);
        if (pNode != null && qNode != null)
            return root;
        else if (pNode != null)
            return pNode;
        else
            return qNode;
    }

    static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return;
        int length = nums.length;
        // int m = 0;
        // int turn = nums[0];
        // for(int i=0;i<length;i++){
        //     int first=turn;
        //     if(length%2==0&&m+k>length)
        //         m=(m+1+k)%length;
        //     else
        //         m = (m+k)%length;
        //     System.out.println("first:"+first+"**m:"+m);
        //     turn = nums[m];
        //     nums[m]=first;
        // }
        for (int i = 0; i < length - k; i++) {
            int temp = nums[k + i];
            nums[k + i] = nums[i];
            nums[i] = temp;
        }
    }
    static int i;
    public static void main(String argv[]){
//        System.out.print(100%3);
//        System.out.print(",");
//        System.out.println(100%3.0);
//        int x=20, y=30;
//        boolean b;
//         b=x>50&&y>60||x>50&&y<-60||x<-50&&y>60||x<-50&&y<-60;
//        System.out.println(b);
//        System.out.println(i);
//        ArrayList list = new ArrayList();
//        System.out.println(list.contains(null));
//        method((byte) 1,1.0);
        Solution1 s =new Solution1();
        //int d[][]={{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        int d[][]={{1,2,2},{3,8,2},{5,3,5}};
        //int a[]={1, 7, 3, 6, 5, 6};
        //System.out.println(s.numEquivDominoPairs(d));
        //s.spiralOrder(d);
        //s.pivotIndex(a);
        //s.minimumEffortPath(d);
        s.characterReplacement("ABCCCCDDDDDD" ,3);
    }

    static void  method(byte x, double y){
        System.out.println(((short)x/y*2));
    }
//    public static void main(String[] args) {
//        int a[] = {1, 2, 3, 4, 5, 6, 7};
//        rotate(a, 2);
//        for (int s :
//                a) {
//            System.out.println(s);
//        }
//    }
}

class Solution1 {

    public int numEquivDominoPairs(int[][] dominoes) {
        int num = 0;
//        Arrays.sort(dominoes,new Comparator<int[]>(){
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0]-o2[0];
//            }
//        });
//        for (int i=0;i<dominoes.length;i++){
//            System.out.println(dominoes[i][0]+","+dominoes[i][1]);
//        }
//        HashMap<int[],Integer> map =new HashMap<int[], Integer>();
//        for(int i=0;i<dominoes.length;i++){
//            int a =map.get(dominoes[i]);
//            if(a==0)
//                map.put(dominoes[i])
//        }
        for(int i=0;i<dominoes.length;i++){
            if(dominoes[i][0]>dominoes[i][1]){
                int temp = dominoes[i][0];
                dominoes[i][0]=dominoes[i][1];
                dominoes[i][1]=temp;
            }
        }
        Arrays.sort(dominoes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0]){
                    return o1[1]-o2[1];
                }else {
                    return o1[0]-o2[0];
                }
            }
        });
        int n=0;
        for(int i=0;i<dominoes.length-1;i++){
            if(dominoes[i][0]==dominoes[i+1][0]&&dominoes[i][1]==dominoes[i+1][1]){
                n++;
            }else {
                num=num+n*(n+1)/2;
                n = 0;
            }
        }
        num=num+n*(n-1)/2;
       return num;
    }
    class Arr{
        int a;
        int b;

        @Override
        public int hashCode() {
            return a+b;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Arr))
                return false;
            Arr arr =(Arr)obj;
            if(obj==this)
                return true;
            if((arr.a==a&&arr.b==b)||(arr.a==b&&arr.b==a))
                return true;
            return false;
        }
    }

    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length==0)
            return null;
        int x=matrix.length;
        int y=matrix[0].length;
        int n=1;
        if(x<y) {
            n = x / 2;
            if(x%2==1)
                n=n+1;
        }
        else{
            n = y / 2;
            if(y%2==1)
                n=n+1;
        }
        int a[] = new int[x*y];
        int j=0;
        for(int k=0;k<n;k++){
            for(int i=k;i<y-k;i++){
                a[j++] = matrix[k][i];
            }
            for(int i=k+1;i<x-k;i++){
                a[j++]=matrix[i][y-k-1];
            }
            if(x-k-1>k)
            for (int i=y-k-2;i>k;i--){
                a[j++]=matrix[x-k-1][i];
            }
            if(y-k-1>k)
            for (int i =x-k-1;i>k;i--){
                a[j++]=matrix[i][k];
            }
        }
        return a;
    }

    public int pivotIndex(int[] nums) {
        if(nums.length<3)
            return -1;
        int leftIndex=0;
        int rightIndex=nums.length-1;
        int left=nums[0],right=nums[rightIndex];
        while(leftIndex+1<=rightIndex){
            if(left==right&&leftIndex+2==rightIndex){
                return leftIndex+1;
            }
            if(left < right){
                leftIndex++;
                left=left+nums[leftIndex];
            }else if(left > right){
                rightIndex--;
                right= right+nums[rightIndex];
            }else if(left==right){
                leftIndex++;
                left=left+nums[leftIndex];
                rightIndex--;
                right= right+nums[rightIndex];
            }
        }
        return -1;
    }

    public int minimumEffortPath(int[][] heights) {
        int m=heights.length;
        int n =heights[0].length;
        List<int []> edges = new ArrayList<>();
        for(int i=0;i<m;++i){
            for(int j = 0;j<n; ++j){
                int id =i*n+j;
                if(i>0){
                    edges.add(new int[]{id-n,id,Math.abs(heights[i][j]-heights[i-1][j])});
                }
                if(j>0){
                    edges.add(new int[]{id-1,id,Math.abs(heights[i][j]-heights[i][j-1])});
                }
            }
        }
        Collections.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2]-o2[2];
            }
        });
        UnionFind uf = new UnionFind(m*n);
        int ans=0;
        for(int []edge:edges){
            int x=edge[0],y=edge[1],v=edge[2];
            uf.unite(x,y);
            if(uf.connected(0,m*n-1)){
                ans=v;
                break;
            }
        }
        return ans;
    }

    //并查集模板
    private class UnionFind {
        int []parent;
        int []size;
        int n;
        //当前连通分量数目
        int setCount;
        public UnionFind(int n) {
            this.n=n;
            this.setCount=n;
            this.parent = new int[n];
            this.size=new int[n];
            Arrays.fill(size,1);
            for(int i=0;i<n;++i){
                parent[i]=i;
            }
        }
        public int findset(int x){
            return parent[x]==x?x:(parent[x]=findset(parent[x]));
        }

        public boolean unite(int x, int y){
            x=findset(x);
            y=findset(y);
            if(x==y){
                return false;
            }
            if(size[x]<size[y]){
                int temp =x;
                x=y;
                y=temp;
            }
            parent[y]=x;
            size[x]+=size[y];
            --setCount;
            return true;
        }

        public boolean connected(int x,int y){
            x=findset(x);
            y=findset(y);
            return x==y;
        }

    }

    public int characterReplacement(String s, int k) {
        if(s=="")
            return 0;
        char[] sArry= s.toCharArray();
        int left=0,right=0,num=0,max=0;
        int flg=k;
//        List<Integer> list=new ArrayList();
//        for(int i=0;i<sArry.length-1;i++){
//            if(sArry[i]!=sArry[i+1])
//                list.add(i+1);
//        }
//        int j=0;
        while(left<sArry.length){
            if(right==sArry.length) {
                //left=list.get(j++);
                left++;
                right=left;
                max=Math.max(max,num);
                num=0;
                k=flg;
            } else if(sArry[left]==sArry[right]) {
                num++;
                right++;
            }else if(sArry[left]!=sArry[right]&&k>0){
                num++;
                right++;
                k--;
            }else if(k==0){
                //left=list.get(j++);
                left++;
                right=left;
                max = Math.max(max,num);
                num=0;
                k=flg;
            }
        }
        return max;
    }
}