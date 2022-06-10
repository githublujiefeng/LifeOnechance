package com.learn.example.springclouddemo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;

//@RunWith(SpringRunner.class)
@SpringBootTest
class SpringcloudDemoApplicationTests {

    @Test(expected = Exception.class)
    public void contextLoads() {
        int numbers[]={3,32,321};
        Double.parseDouble(numbers[0]+"");
        System.out.println(PrintMinNumber(numbers));
    }

    public String PrintMinNumber(int numbers[]) {
        Integer []numArray=new Integer[numbers.length];
        for(int i=0;i<numbers.length;i++){
            numArray[i]=numbers[i];
        }
        Arrays.sort( numArray,new Comparator(){

            @Override
            public int compare(Object a, Object b) {
                return Integer.parseInt(""+a+b)-Integer.parseInt(""+b+a);
            }
        });
        for (int i=0;i<numArray.length;i++)
            System.out.println(numArray[i]);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<numArray.length;i++){
            stringBuffer=stringBuffer.append(numArray[i]);
        }
        return stringBuffer.toString();
    }
    public int eraseOverlapIntervals(int[][] intervals) {
        Integer [][] newArry= new Integer[intervals.length][2];
        for(int i=0;i<intervals.length;i++){
            for(int j=0;j<intervals[i].length;j++)
                newArry[i][j]=intervals[i][j];
        }
        Arrays.sort(intervals,new Comparator(){
            @Override
            public int compare(Object o1,Object o2){
                Integer[] a= (Integer[])o1;
                Integer[] b = (Integer[]) o2;
                return b[1]-b[0]-(a[1]-a[0]);
            }
        });
        for(int i=0;i<intervals.length;i++){
            for(int j=0;j<intervals[i].length;j++)
                System.out.print(intervals[i][j]+"\t");
            System.out.print("\n");
        }
        return 0;
    }

    static class solucation{
        public static void main(String[] args) {
            SpringcloudDemoApplicationTests test = new SpringcloudDemoApplicationTests();
            test.searchRange(new int []{5,7,7,8,8,10},8);
        }
    }

    public int[] searchRange(int[] nums, int target) {
        int l = search(nums,target);
        int r = search(nums,target+1);
        return l==nums.length||l<r?new int[]{l,r-1}:new int[]{-1,-1};
    }
    int search(int []nums, int target){
        int left = 0,right=nums.length;
        while(left<right){
            int mid = (left+right) >>1;
            if(nums[mid]>=target){
                right = mid;
            }else{
                left = mid +1;
            }
        }
        return left;
    }
    class lee1 implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    class lee2 implements Comparable{

        @Override
        public int compareTo(Object o) {
            return this.compareTo(o);
        }
    }

}
