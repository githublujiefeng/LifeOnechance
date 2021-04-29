package com.learn.example.springclouddemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;

@SpringBootTest
class SpringcloudDemoApplicationTests {

    @Test
    void contextLoads() {
        int numbers[]={3,32,321};
        //System.out.println(PrintMinNumber(numbers));
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
}
