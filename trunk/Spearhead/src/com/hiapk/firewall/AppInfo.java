package com.hiapk.firewall;

import android.graphics.drawable.Drawable;

public class AppInfo {
	/*
	 * 用于存储listview的app信息
	 * 
	*/
	Drawable icon;
	String appname;
	String trafficUp;
	String trafficDown;
	String packageName;
	String trafficTotal;
	long trafficTotalComparator;
	
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable drawable) {
		this.icon = drawable;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getTrafficUp() {
		return trafficUp;
	}
	public void setTrafficUp(String trafficUp) {
		this.trafficUp = trafficUp;
	}
	public String getTrafficDown() {
		return trafficDown;
	}
	public void setTrafficDown(String trafficDown) {
		this.trafficDown = trafficDown;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getTrafficTotal() {
		return trafficTotal;
	}
	public void setTrafficTotal(String trafficTotal) {
		this.trafficTotal = trafficTotal;
	}
	public long getTrafficTotalComparator() {
		return trafficTotalComparator;
	}
	public void setTrafficTotalComparator(long trafficTotalComparator) {
		this.trafficTotalComparator = trafficTotalComparator;
	}
	
	
	

	
	
	

}
