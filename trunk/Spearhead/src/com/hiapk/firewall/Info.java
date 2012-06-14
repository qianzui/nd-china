package com.hiapk.firewall;

import android.graphics.drawable.Drawable;

public class Info {
	public Drawable d;
	public String appname;
	public int uid;
	public String pkgname;
	public long up;
	public long down;
	public Info(Drawable d,String appname,long up,long down){
		this.d = d;
		this.appname = appname;
		this.up = up;
		this.down = down;

	}
	
}
