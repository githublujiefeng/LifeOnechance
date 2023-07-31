package com.learn.算法;

public class leetCode795 {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int sum = 0;
        int max=0;
        int end =0;
        int lowNum=0;
        for(int i=0;i<nums.length;i++){
            max = Math.max(max,nums[i]);
            if(max>=left && max<=right){
                end++;
                if(nums[i]<left){
                    if(i+1<nums.length&&nums[i+1]<left){
                        lowNum++;
                    }else if(i==nums.length-1){
                        sum--;
                    }else{
                        lowNum++;
                        sum-=(1+lowNum)*lowNum/2;
                        lowNum=0;
                    }
                }
            }else{
                sum += (1+end)*end/2;
                end = 0;
                max = 0;
            }
        }
        if(end!=0 && max == nums[nums.length-1]){
            sum += (1+end)*end/2;
        }
        return sum;
    }

    public static void main(String[] args) {
        leetCode795 o = new leetCode795();
        o.numSubarrayBoundedMax(new int[]{2,1,4,3},2,3);
    }
}
