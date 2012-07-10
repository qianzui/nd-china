package com.hiapk.firewall;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;

import android.content.pm.PackageInfo;
import android.util.Log;

public class MyCompName implements  Comparator {
	 HashMap<Integer,String> appnamemap;
	 
	public void init( HashMap<Integer,String> appnamemap){
		this.appnamemap = appnamemap;
	}
	
	@Override
	public int compare(Object one, Object two) {
		// TODO Auto-generated method stub
		Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
		Integer uid1 = (Integer)one;
		Integer uid2 = (Integer)two;
		String name1;
		String name2;
		if(appnamemap.containsKey(uid1)){
	     name1 = appnamemap.get(uid1).toString().replaceAll(" ","").replaceAll(" ","");
		}else{
	     name1 = "null";
		}
		if(appnamemap.containsKey(uid2)){
	     name2 = appnamemap.get(uid2).toString().replaceAll(" ","").replaceAll(" ","");
		}else{
	     name2 = "null";
		}
		if(myCollator.compare(name1, name2) < 0){
			return -1;
		}else if(myCollator.compare(name1, name2) > 0){
			return 1;
		}else{
			return 0;
		}
	}
}
