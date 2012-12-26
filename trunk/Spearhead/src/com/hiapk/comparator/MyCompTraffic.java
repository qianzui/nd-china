package com.hiapk.comparator;

import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;
import com.hiapk.bean.DatauidHash;
import com.hiapk.util.SharedPrefrenceData;


public class MyCompTraffic implements  Comparator {
	Context mContext;
	public SharedPrefrenceData sharedpref;
	public HashMap<Integer, DatauidHash> uiddata;
	public long traffic1 = 0,traffic2 = 0;
	public void init(Context mContext,HashMap<Integer, DatauidHash> uiddata) {
		this.mContext = mContext;
		this.uiddata = uiddata;
		sharedpref = new SharedPrefrenceData(mContext);
	}
	@Override
	public int compare(Object one, Object two) {
		// TODO Auto-generated method stub
		Integer uid1 = (Integer)one;
		Integer uid2 = (Integer)two;
		if (uiddata != null) {
			if(uiddata.containsKey(uid1)){
				traffic1 = getTraffic(uid1);
			}
			if(uiddata.containsKey(uid2)){
				traffic2 = getTraffic(uid2);
			}
		}
		if (traffic1 > traffic2) {
			return -1;
		} else if (traffic1 < traffic2) {
			return 1;
		} else {
			return 0;
		}
	}
	public long getTraffic(int uid){
		long traffic = 0;
		DatauidHash data = uiddata.get(uid);
		switch (sharedpref.getFireWallType()) {
		case 0:
			traffic = data.getTotalTraffToday();
			break;
		case 1:
			traffic = data.getTotalTraffWeek();
			break;
		case 2:
			traffic = data.getTotalTraff();
			break;
		case 3:
			traffic = data.getDownloadmobile() + data.getUploadmobile();
			break;
		case 4:
			traffic = data.getDownloadwifi() + data.getUploadwifi();
			break;
		case 5:
			traffic = data.getTotalTraff();
			break;
		default:
			traffic = data.getTotalTraff();
			break;
		}
		return traffic;
	}
}
