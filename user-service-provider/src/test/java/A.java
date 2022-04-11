import java.util.*;

class  A{
    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        List<Integer> list = new ArrayList<Integer>();
        int len = nums.length;
        List<Integer> keys = new ArrayList<Integer>();
        for(int i=0;i<len;i++){
            if(nums[i]==key){
                keys.add(i);
            }
        }
        for(int i=0;i<len;i++){
            for(int j=0;j<keys.size();j++){
                if(Math.abs(i-keys.get(j))<=k){
                    list.add(i);
                    break;
                }
            }
        }
        return list;
    }

    public int maximumTop(int[] nums, int k) {
        int len = nums.length;
        if(nums.length==1&&k%2==1){
            return -1;
        }
        int maxSize=0;
        for(int i =0;i<len&&i<k-1 ;i++){
            if(nums[i]>maxSize){
                maxSize = nums[i];
            }
        }
        if(len-1>=k&&nums[k]>maxSize){
            maxSize = nums[k];
        }
        return maxSize;
    }

//    public int digArtifacts(int n, int[][] artifacts, int[][] dig) {
//        int [][] res = new int[n][4];
//        for (int i = 0; i < dig.length; i++) {
//            for (int j = i; j < dig.length; j++) {
//                res[i][j] = dig[i][j]
//            }
//        }
//    }
public boolean validUtf8(int[] data) {
    int len=0;
    boolean isOk = true;
    for(int i= 0 ;i<data.length;i++){
        int num =data[i];
        System.out.println(num>>4);
        if(len ==0){
            if(num>>7==0){
                continue;
            }else if(num>>5==6){
                len = 1;
            }else if(num>>4 ==14){
                len = 2;
            }else if(num>>3==30){
                len = 3;
            }
        }else if(len>0){
            if(num>>6==2){
                len --;
                continue;
            }else{
                isOk= false;
                break;
            }
        }
    }
    return isOk;
}
    public int singleNumber(int[] nums) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else{
                map.put(nums[i],1);
            }
        }
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(entry.getValue() == 1){
                return entry.getKey();
            }
        }
        return 0;
    }
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> reslist  = new ArrayList<List<Integer>>();
        int n = nums.length;
        if(n<3){
            return reslist;
        }
        for(int i=1 ;i<n;i++){
            for(int j=0;j<n-i;j++){
                if(nums[j]>nums[j+1]){
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }

        for(int i=0;i<n;i++){
            int left=i+1;int right=n-1;
            while(left<right){
                int sum = nums[i]+nums[left]+nums[right];
                if(sum<0){
                    left++;
                }else if(sum>0){
                    right--;
                }else{
                    reslist.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    break;
                }
            }
        }
        return reslist;

    }
    public static void main(String[] args) {
        final A a = new A();
       // List list = a.findKDistantIndices(new int[]{3,4,9,1,3,9,5},9,1);
       // int mun=a.maximumTop(new int[]{100,9,6,8,7},5);
//        boolean isOk = a.validUtf8(new int[]{237});
//        System.out.println(isOk);
//        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
//        map.containsKey(1);
//        System.out.println(a.threeSum(new int[]{-1,0,1,2,-1,-4}));
//        String [] m = new String[]{"a","bc"};
//        Arrays.sort(m,
//                new Comparator<String>(){
//
//                    @Override
//                    public int compare(String o1, String o2) {
//                        return 0;
//                    }
//                }
//                (o1,o2)-> {return 0;}
//        );
//        Arrays.sort(m,(k,l)->{
//            return l.length()-k.length();
//        });
        Test thread1 = new Test();
        Test thread2 = new Test();
        thread1.start();
        thread2.start();
        //thread1.doAdd();
        thread2.doAdd();
    }
}