package com.hiapk.firewall;

import java.util.Comparator;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.util.SQLStatic;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class MyCompNotifTraffic implements Comparator {
	
	    private Context  mContext;
	    public void init(Context mContext){
	    	this.mContext = mContext;
	    }
		@Override
		public int compare(Object one, Object two) {
			// TODO Auto-generated method stub
			long traffic1 = 0;
			long traffic2 = 0;
			String[] record1 = (String[])one;
			String[] record2 = (String[])two;
			PackageInfo pkgInfo1 = null;
			PackageInfo pkgInfo2 = null;
			try {
				pkgInfo1 = mContext.getPackageManager().getPackageInfo(record1[0], 0);
				pkgInfo2 = mContext.getPackageManager().getPackageInfo(record2[0], 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pkgInfo1 != null){
				int uid = pkgInfo1.applicationInfo.uid;
				long traffic[] = TrafficManager.getUidtraff(mContext, uid);
				traffic1 = traffic[0];
			}
			if(pkgInfo2 != null){
				int uid = pkgInfo2.applicationInfo.uid;
				long traffic[] = TrafficManager.getUidtraff(mContext, uid);
				traffic2 = traffic[0];
			}
			if(traffic1 < traffic2){
				return -1;
			}else if(traffic1 > traffic2){
				return 1;
			}else{
				return 0;
			}

		}
		

}
