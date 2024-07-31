package com.learn;

import java.util.*;

public class Test {

    public static void main(String[] args) {
//        String m = "/tencent/tencentInsightGetliveTypedwad.do";
//        System.out.println(m.matches("/tencent/tencentInsight(.*).do"));
//        new Test().groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
//        new Test().moveZeroes(new int[]{0,1,0,3,12});
//        new Test().findAnagrams2("abab","ab");
        System.out.println(new Test().gcd(7,3));
        Set set = new HashSet();

    }


    public int subarraySum(int[] nums, int k) {
        int res=0; int sum =0;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            sum +=nums[i];
            if(map.containsKey(sum-k)){
                res+=map.get(sum-k);
            }
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
        return res;
    }

    public int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        HashMap<Character,Integer> pMap = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char m = p.charAt(i);
            pMap.put(m,pMap.getOrDefault(m,0)+1);
        }
        for(int i=0;i<s.length()-p.length();i++){
            HashMap<Character,Integer> sMap = new HashMap<>();
            int j = 0;
            for (; j < p.length(); j++) {
                char m = s.charAt(i+j);
                if(!pMap.containsKey(m)){
                    i=i+j;
                    break;
                }
                sMap.put(m,sMap.getOrDefault(m,0)+1);
                if(sMap.get(m)>pMap.get(m)){
                    break;
                }
            }
            if(j==p.length())
                res.add(i);
        }
        return res;
    }

    public List<Integer> findAnagrams2(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] pArry = new int[26];
        int[] sArry = new int[26];
        int pLength = p.length();
        for (int i = 0; i < pLength; i++) {
            pArry[p.charAt(i)-'a']++;
            sArry[s.charAt(i)-'a']++;
        }
        for(int i=pLength-1;i<s.length();i++){
            int j = 0;
            for (; j < 26; j++) {
                if(pArry[j]!=sArry[j])
                    break;
            }
            if(j==26){
                res.add(i-pLength+1);
            }
            if(i<s.length()-1) {
                sArry[s.charAt(i + 1) - 'a']++;
            }
            sArry[s.charAt(i-pLength+1)-'a']--;
        }
        return res;
    }
    public void moveZeroes(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==0){
                list.add(i);
            }
        }
        int index=0;
        while (index<list.size()){
            int a = list.get(index);
            if(index==list.size()-1){
                for (int i = a; i <nums.length-1 ; i++) {
                    nums[i-index] = nums[i+1];
                }
                for (int i=0;i<list.size();i++){
                    nums[nums.length-list.size()+i] =0;
                }
                index++;
            }else {
                int b = list.get(++index);
                for (int i = a; i < b - 1; i++) {
                    nums[i - index+1] = nums[i + 1];
                }
            }
        }

    }

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<List<String>> res = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            char[] ans = strs[i].toCharArray();
            Arrays.sort(ans);
            String s = String.valueOf(ans);
            if(map.containsKey(s)){
                map.get(s).add(strs[i]);
            }else{
                ArrayList<String> list =  new ArrayList<String>();
                list.add(strs[i]);
                res.add(list);
                map.put(s,list);
            }
        }
        return res;
    }

    public int[] arrayRankTransform(int[] arr) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        int []m = arr.clone();
        System.arraycopy(arr,0,m,0,arr.length);
        Arrays.sort(arr);
        int []res = new int[arr.length];
        for(int i=0,j=1;i<arr.length;i++){
            if(!map.containsKey(arr[i])){
                map.put(arr[i],j++);
            }
        }
        int j=0;
        for(int i:m){
            res[j++] = map.get(i);
        }
        return res;
    }
}
