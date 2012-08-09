package com.hiapk.firewall;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MyCompNotifName implements Comparator {
	
	    Context mContext;
	    public void init(Context mContext){
	    	this.mContext = mContext;
	    }
	
		@Override
		public int compare(Object one, Object two) {
			// TODO Auto-generated method stub
			Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
			String[] pkgname1 = (String[])one;
			String[] pkgname2 = (String[])two;
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pkgInfo1 = null;
			PackageInfo pkgInfo2 = null;
			try {
				pkgInfo1 = pm.getPackageInfo(pkgname1[0],0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pkgInfo2 = pm.getPackageInfo(pkgname2[0],0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name1 = pkgInfo1.applicationInfo.loadLabel(pm).toString().replaceAll(" ","").replaceAll(" ","");
			String name2 = pkgInfo2.applicationInfo.loadLabel(pm).toString().replaceAll(" ","").replaceAll(" ","");
			if(myCollator.compare(name1, name2) < 0){
				return -1;
			}else if(myCollator.compare(name1, name2) > 0){
				return 1;
			}else{
				return 0;
			}
		}

}
