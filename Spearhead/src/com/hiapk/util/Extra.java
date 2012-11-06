package com.hiapk.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class Extra {
	
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	private static final String APP_PKG_NAME_22 = "pkg";
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	
	
	
	/*
	 * 调用系统应用管理功能
	 */
	public static void showInstalledAppDetails(Context context,
			String packageName) {
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) {
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts("package", packageName, null);
			intent.setData(uri);
		} else {
			final String appPackageName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPackageName, packageName);
		}
		context.startActivity(intent);
	}

	/*
	 * 返回流量大于0的应用个数
	 */
	public static int getAppNum(SharedPrefrenceData sharedpref , ArrayList<Integer> uidList) {
		int j = 0;
		int m = 0;
		int w = 0;
		if (SQLStatic.uiddata != null) {
			if (sharedpref.getFireWallType() == 3) {
				for (int i = 0; i < uidList.size(); i++) {
					if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
						if ((SQLStatic.uiddata.get(uidList.get(i))
								.getUploadmobile() + SQLStatic.uiddata.get(
								uidList.get(i)).getDownloadmobile()) > 0) {
							m++;
						}
					}
				}
				return m;
			} else if (sharedpref.getFireWallType() == 4) {
				for (int i = 0; i < uidList.size(); i++) {
					if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
						if ((SQLStatic.uiddata.get(uidList.get(i))
								.getUploadwifi() + SQLStatic.uiddata.get(
								uidList.get(i)).getDownloadwifi()) > 0) {
							w++;
						}
					}
				}
				return w;
			} else {
				for (int i = 0; i < uidList.size(); i++) {
					if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
						if (SQLStatic.uiddata.get(uidList.get(i))
								.getTotalTraff() > 0) {
							j++;
						}
					}
				}
				return j;
			}
		} else {
			return 0;
		}
	}
}
