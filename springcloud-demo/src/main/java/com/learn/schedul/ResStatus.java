package com.learn.schedul;

import java.util.HashMap;

public class ResStatus {

    public static HashMap getSuccess(String mes){
        HashMap<String,String> res = new HashMap<>();
        res.put("Status","success");
        res.put("MSG",mes);
        return res;
    }

    public static HashMap getFail(String mes){
        HashMap<String,String> res = new HashMap<>();
        res.put("Status","failed");
        res.put("MSG",mes);
        return res;
    }
}
