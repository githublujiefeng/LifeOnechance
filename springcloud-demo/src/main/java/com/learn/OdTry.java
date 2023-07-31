package com.learn;

import java.util.*;

public class OdTry {
    // 行列矩阵
    static int n;
    static int m;
    static String[][] matrix;

    // 可移动选项
    static int[][] offset = {{0,-1},{0,1},{-1,0},{1,0}};

    static HashSet<String> checked = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        matrix = new String[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.next();
            }
        }
        System.out.println(getResult(n,m));
    }
    public static String getResult( int n, int m){
        ArrayList<Integer[]> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 进行遍历 并 判断之前是否遍历过
                if (Objects.equals("O",matrix[i][j]) && !checked.contains(i+"_"+j)){
                    ArrayList<Integer[]> enter = new ArrayList<>();
                    int count = dfs(i,j,0,enter);
                    if (enter.size() == 1){
                        Integer[] pos = enter.get(0);
                        Integer[] an = {pos[0],pos[1],count};
                        ans.add(an);
                    }
                }
            }
        }

        if (ans.size() == 0 ){
            return "NULL";
        }
        ans.sort((a,b) -> b[2]-a[2]);

        if (ans.size() ==1 || ans.get(0)[2] > ans.get(1)[2]){
            StringJoiner sj = new StringJoiner(" ","","");
            for (Integer ele:ans.get(0)){
                sj.add(ele+"");
            }
            return sj.toString();
        }else {
            return ans.get(0)[2] + "";
        }
    }

    public static int dfs(int i, int j, int count , ArrayList<Integer[]> enter){
        // 当前位置
        String pos = i+"_"+j;

        if (i<0 || i>=n || j<0 || j>=m || Objects.equals("X",matrix[i][j]) || checked.contains(pos)){
            return count;
        }

        checked.add(pos);

        if (i==0 || i==n-1 || j==0 || j==m-1 ){
            enter.add(new Integer[]{i,j});
        }

        count++;

        for (int k = 0; k < offset.length; k++) {
            int offsetX = offset[k][0];
            int offsetY = offset[k][1];

            int newI = i + offsetX;
            int newJ = j + offsetY;
            count = dfs(newI,newJ,count,enter);
        }

        return count;
    }
}
