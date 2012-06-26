package com.hiapk.firewall;

import android.graphics.drawable.Drawable;

public class AppInfo {
	
	public Drawable d;
	public String appname;
	public String packagename;
	public int uid;
	public long up;
	public long down;
	
	public AppInfo(Drawable d ,String appname , String packagename , int uid
			, long up , long down){
		this.d = d;
		this.appname = appname;
		this.packagename = packagename;
		this.uid = uid; 
		this.up = up;
		this.down = down;
	}
}
