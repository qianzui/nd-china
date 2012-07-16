package com.hiapk.firewall;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;

import android.util.Log;

import com.hiapk.sqlhelper.uid.SQLHelperFireWall.Data;


public class MyCompTraffic implements  Comparator {
	HashMap<Integer, Data> mp;
	
	public void init(HashMap<Integer, Data> mp) {
		this.mp = mp;
	}
	@Override
	public int compare(Object one, Object two) {
		// TODO Auto-generated method stub
		Integer uid1 = (Integer)one;
		Integer uid2 = (Integer)two;
		long traffic1 ;
		long traffic2 ;
		if (mp.containsKey(uid1)) {
			traffic1 = mp.get(uid1).upload + mp.get(uid1).download;
	    }else{
			traffic1 = -1000;
		}
		if (mp.containsKey(uid2)) {
			try{
			traffic2 = mp.get(uid2).upload + mp.get(uid2).download;
			 }catch(Exception e){
			  traffic2 = -1000;
			}
	    }else{
			traffic2 = -1000;
		}
		if(traffic1 > traffic2){
			return -1;
		}else if(traffic1 < traffic2){
			return 1;
		}else{
			return 0;
		}
	}
}
