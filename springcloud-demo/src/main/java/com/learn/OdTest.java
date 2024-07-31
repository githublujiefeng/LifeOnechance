package com.learn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringJoiner;

public class OdTest {
    static int[][] toDo = {{0,-1},{0,1},{-1,0},{1,0}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        String [][] data = new String[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                data[i][j]= sc.next();
            }
        }
        ArrayList<Integer[]> res = new ArrayList<>();
        HashSet<String> isGo = new HashSet<>();

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if("O".equals(data[i][j]) &&!isGo.contains(i+"-"+j) ){
                    ArrayList<Integer[]> found = new ArrayList<>();
                    int count = dfs(i,j,m,n,0,found,isGo,data);
                    if(found.size()==1){
                        Integer[] temp = found.get(0);
                        Integer[] resTemp = {temp[0],temp[1],count};
                        res.add(resTemp);
                    }
                }
            }
        }
        if(res.size() ==0) {
            System.out.println("null");
        }
        res.sort((a,b)->b[2]-a[2]);
        if(res.size()==1||res.get(0)[2]>res.get(1)[2]){
            StringJoiner sj = new StringJoiner(" ","","");
            for (Integer v :
                    res.get(0)) {
                sj.add(v + "");
            }
            System.out.println(sj.toString());
        }else {
            System.out.println(res.get(0)[2]+"");
        }
    }
    //dfs搜索
    public static int dfs(int i,int j,int m,int n,int count,ArrayList<Integer[]> found,HashSet<String> isGo, String [][] data){
        String line = i+"-"+j;
        if(i<0||j<0||i>=m||j>=n||"X".equals(data[i][j])||isGo.contains(line)){
            return count;
        }
        isGo.add(line);
        if(i==0||i==m-1||j==0|j==n-1){
            found.add(new Integer[]{i,j});
        }
        count++;
        for(int k=0;k<toDo.length;k++){
            int x = toDo[k][0];
            int y = toDo[k][1];
            int nextI = i+x;
            int nextJ = j+y;
            count = dfs(nextI,nextJ,m,n,count,found,isGo,data);
        }
        return count;
    }
}
