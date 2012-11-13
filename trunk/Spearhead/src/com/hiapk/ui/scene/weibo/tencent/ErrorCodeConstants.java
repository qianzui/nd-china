package com.hiapk.ui.scene.weibo.tencent;

import java.util.HashMap;

/**
 * 鏈被閽堝 QweiboSDK锛� 璁惧畾浜嗕竴绯诲垪鐨� errcode 浠ュ強瀵瑰簲鐨� errmsg
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
        //TODO errcode灏氭湭纭畾
        put("1", "connect out of time");
        
        
        //OAuthClient閿欒
        put("1001","qHttpClient not specified");
        
        
    }
}

