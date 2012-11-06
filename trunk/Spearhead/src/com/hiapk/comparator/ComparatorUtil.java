package com.hiapk.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.hiapk.firewall.Block;
import com.hiapk.util.SQLStatic;

import android.content.Context;
import android.content.pm.PackageInfo;

public class ComparatorUtil {
	
	
	public static ArrayList<Integer> compUidList(Context mContext,HashMap<Integer, PackageInfo> appList){
		ArrayList<Integer> uidList = new ArrayList<Integer>();
		ArrayList keys = new ArrayList(appList.keySet());
		for (int i = 0; i < keys.size(); i++) {
			int uid = (Integer) keys.get(i);
			uidList.add(uid);
		}
		MyCompName mn = new MyCompName();
		mn.init(Block.appnamemap);
		Collections.sort(uidList, mn);

		MyCompTraffic mt = new MyCompTraffic();
		mt.init(mContext, SQLStatic.uiddata);
		Collections.sort(uidList, mt);
		return uidList;
	}
	
	
	public static ArrayList<String[]>  compNotifList(Context mContext,ArrayList<String[]> notificationInfos){
		
		MyCompNotifName comp = new MyCompNotifName();
		comp.init(mContext);
		Collections.sort(notificationInfos, comp);

		MyCompNotifTraffic compTraffic = new MyCompNotifTraffic();
		compTraffic.init(mContext);
		Collections.sort(notificationInfos, compTraffic); 
		return notificationInfos;
	}

}
