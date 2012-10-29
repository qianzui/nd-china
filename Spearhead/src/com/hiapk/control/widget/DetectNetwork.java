package com.hiapk.control.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DetectNetwork {
	// 进入无线网络配置界面
	// mActivity.startActivity(new Intent(
	// Settings.ACTION_WIRELESS_SETTINGS));
	// startActivity(new
	// Intent(Settings.ACTION_WIFI_SETTINGS));
	// //进入手机中的wifi网络设置界面

	// 检测网络是否可用
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
