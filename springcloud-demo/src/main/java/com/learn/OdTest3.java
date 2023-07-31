package com.learn;

import java.util.*;
import java.util.stream.Collectors;

public class OdTest3 {
    static Map<Character,String> DIGIT_TO_LETTERS = new HashMap<>();
    static {
        DIGIT_TO_LETTERS.put('0',"abc");
        DIGIT_TO_LETTERS.put('1',"def");
        DIGIT_TO_LETTERS.put('2',"ghi");
        DIGIT_TO_LETTERS.put('3',"jkl");
        DIGIT_TO_LETTERS.put('4',"mno");
        DIGIT_TO_LETTERS.put('5',"pqr");
        DIGIT_TO_LETTERS.put('6',"st");
        DIGIT_TO_LETTERS.put('7',"uv");
        DIGIT_TO_LETTERS.put('8',"wx");
        DIGIT_TO_LETTERS.put('9',"yz");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String digits = sc.nextLine();
        String blocked = sc.nextLine();
        System.out.println(generateCombinations(digits,blocked).stream().collect(Collectors.joining(",","",",")));
    }
    static List<String> generateCombinations(String digits,String blocked){
        List<String> res = new ArrayList<>();
        if(digits ==null||digits.length()==0){
            return res;
        }
        backTrack(res,new StringBuilder(),digits,0,blocked);
        return res;
    }

    private  static void backTrack(List<String> res, StringBuilder current, String digits, int index, String blocked) {
        if(index==digits.length()){
            String combination = current.toString();
            if(!containsBlockedCharacters(combination,blocked)){
                res.add(combination);
            }
            return;
        }
        char digit = digits.charAt(index);
        String letters =DIGIT_TO_LETTERS.get(digit);
        for (int i = 0; i < letters.length(); i++) {
            current.append(letters.charAt(i));
            backTrack(res,current,digits,index+1,blocked);
            current.deleteCharAt(current.length()-1);
        }
    }

    private static boolean containsBlockedCharacters(String combination, String blocked) {
        int count = 0;
        for (char blockChar:
        blocked.toCharArray()){
            if (combination.indexOf(blockChar)!=-1){
                count++;
            }
        }
        return count == blocked.length();
    }
}
