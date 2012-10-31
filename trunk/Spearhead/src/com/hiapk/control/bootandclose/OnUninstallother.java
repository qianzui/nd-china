package com.hiapk.control.bootandclose;

import com.hiapk.firewall.Block;
import com.hiapk.logs.SaveRule;

import android.content.Context;
import android.content.SharedPreferences;

public class OnUninstallother {
	Context context;
	String packagename;

	public OnUninstallother(Context context, String packagename) {
		this.context = context;
		this.packagename = packagename;
	}

	public void operetor() {
		final SharedPreferences prefs = context.getSharedPreferences(
				Block.PREFS_NAME, 0);
		String savedPkgname_wifi = prefs.getString(Block.PREF_WIFI_PKGNAME, "");
		String savedPkgname_3g= prefs.getString(Block.PREF_3G_PKGNAME, "");
		SaveRule sr = new SaveRule(context);
	    if(savedPkgname_wifi.contains(packagename)){
				sr.saveUninstalledPkgnameWifi(packagename,true);
	    }
	    if(savedPkgname_3g.contains(packagename)){
				sr.saveUninstalledPkgname3g(packagename,true);
	    }

	}
}
