package com.learn.java2.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class mapForeach {
    public static void main(String[] args) {
        Map<Integer,String> map = new HashMap<>();
        for (Integer key: map.keySet()) {
            String value = map.get(key);
        }
        for (String value : map.values()) {
            System.out.println(value);
        }
        for (Map.Entry<Integer,String> entry:
             map.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
        }
        Iterator<Map.Entry<Integer,String>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,String> entry = iterator.next();
            Integer key = entry.getKey();
            String value = entry.getValue();
        }
        //这种方法无法改变变量的值
        map.forEach((key,value)->{
            System.out.println(key+" "+value);
        });
    }
}
