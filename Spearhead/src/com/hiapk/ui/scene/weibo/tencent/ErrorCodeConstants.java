package com.hiapk.ui.scene.weibo.tencent;

import java.util.HashMap;

/**
 * 本类针对 QweiboSDK＄1�7 设定了一系列的1�7 errcode 以及对应的1�7 errmsg
 */
public class ErrorCodeConstants{
    
    private static MyErrorCodeHashMap myErrorCodeHashMap =new MyErrorCodeHashMap();
    
    public static String getErrmsg(String errcode){
        return myErrorCodeHashMap.get(errcode);
    }
}

class MyErrorCodeHashMap extends HashMap<String,String>{
    private static final long serialVersionUID = 2427025312680000207L;
    public MyErrorCodeHashMap(){
        //TODO errcode尚未确定
        put("1", "connect out of time");
        
        
        //OAuthClient错误
        put("1001","qHttpClient not specified");
        
        
    }
}

