package com.wind.androiddev.getapplist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainScene extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayList<AppInfo> appInfo;
		appInfo=this.getappinfo();
		appInfo=flitersystemapp(appInfo);
		AppInfoAdapter appinfoAdapter=new AppInfoAdapter(this, appInfo);
		ListView lv_appInfo=(ListView) findViewById(R.id.lv_appInfo);
		lv_appInfo.setDividerHeight(5);
		lv_appInfo.setAdapter(appinfoAdapter);
		TextView tv_count = (TextView) findViewById(R.id.tv_count);
		tv_count.setText("已安装程序总数为 "+lv_appInfo.getCount()+" 个");
	}

	public ArrayList<AppInfo> getappinfo() {
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
		List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageinfo.applicationInfo.loadLabel(
					getPackageManager()).toString();
			tmpInfo.packageName = packageinfo.packageName;
			tmpInfo.versionName = packageinfo.versionName;
			tmpInfo.versionCode = packageinfo.versionCode;
			tmpInfo.appIcon = packageinfo.applicationInfo		
					.loadIcon(getPackageManager());
			tmpInfo.app_flag=packageinfo.applicationInfo.flags;
			appList.add(tmpInfo);
		}
		return appList;

	}

	public ArrayList<AppInfo> flitersystemapp(ArrayList<AppInfo> applist) {
		ArrayList<AppInfo> applist_sys = new ArrayList<AppInfo>();
		ArrayList<AppInfo> applist_user = new ArrayList<AppInfo>();
		
		for (int i = 0; i < applist.size(); i++) {
			AppInfo tempappInfo=applist.get(i);
				if((tempappInfo.app_flag&ApplicationInfo.FLAG_SYSTEM)==0)
			{
			            //非系统应用
				applist_user.add(tempappInfo);
			}
			else
			{
			            //系统应用　　　
				applist_sys.add(tempappInfo);
			}
		}
		
		return applist_user;
	}

}