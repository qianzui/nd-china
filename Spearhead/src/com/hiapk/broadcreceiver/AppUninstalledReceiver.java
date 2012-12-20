package com.hiapk.broadcreceiver;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppUninstalledReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {   // uninstall  
	            String packageName = intent.getDataString();  
	            FireWallActivity.isRefreshList = true;
	            Logs.i("test", "app uninstalled:" + packageName);
	        }  
		 if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {     // install  
	            String packageName = intent.getDataString();  
	            FireWallActivity.isRefreshList = true;
	            Logs.i("test", "app installed:" + packageName);
	        } 
	}

}
