package com.hiapk.firewall;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall.Data;


public class MyCompTraffic implements  Comparator {
	Context mContext;
	
	public void init(Context mContext) {
		this.mContext = mContext;
	}
	@Override
	public int compare(Object one, Object two) {
		// TODO Auto-generated method stub
		Integer uid1 = (Integer)one;
		Integer uid2 = (Integer)two;
		long traffic1[] = TrafficManager.getUidtraff(mContext, uid1);
		long traffic2[] = TrafficManager.getUidtraff(mContext, uid2);
		
		if(traffic1[0] > traffic2[0]){
			return -1;
		}else if(traffic1[0] < traffic2[0]){
			return 1;
		}else{
			return 0;
		}
	}
}
