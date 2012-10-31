package com.hiapk.control.bootandclose;

import com.hiapk.firewall.Block;
import com.hiapk.logs.Logs;
import com.hiapk.logs.SaveRule;
import com.hiapk.util.SharedPrefrenceData;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class OnUninstallother {
	Context context;
	String packagename;

	public OnUninstallother(Context context, String packagename) {
		this.context = context;
		this.packagename = packagename;
	}

	public void operetor() {
		SharedPrefrenceData sharedDate = new SharedPrefrenceData(context);
		final SharedPreferences prefs = context.getSharedPreferences(
				Block.PREFS_NAME, 0);
		String savedPkgname_wifi = prefs.getString(Block.PREF_WIFI_PKGNAME,
				"");
		String savedPkgname_3g = prefs.getString(Block.PREF_3G_PKGNAME, "");
		String newWifi = savedPkgname_wifi;
		String new3g = savedPkgname_3g;
		if (sharedDate.isAutoSaveFireWallRule()) {
			SaveRule sr = new SaveRule(context);
			if (savedPkgname_wifi.contains(packagename)) {
				sr.saveUninstalledPkgnameWifi(packagename, true);
			}
			if (savedPkgname_3g.contains(packagename)) {
				sr.saveUninstalledPkgname3g(packagename, true);
			}
		}
		if(savedPkgname_wifi.contains(packagename)){
			newWifi = savedPkgname_wifi.replace(packagename, "");
		}
		if(savedPkgname_3g.contains(packagename)){
			new3g = savedPkgname_3g.replace(packagename, "");
		}
		final Editor edit = prefs.edit();
		edit.putString(Block.PREF_WIFI_PKGNAME, newWifi);
		edit.putString(Block.PREF_3G_PKGNAME, new3g);
		edit.commit();

	}
}
