package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.List;

import com.hiapk.firewall.AppListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FireWallActivity extends Activity{
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName"; 
	/** 
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.1��֮ǰ�汾) 
	 */ 
	private static final String APP_PKG_NAME_22 = "pkg"; 
	/** 
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.2) 
	 */ 
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings"; 
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails"; 

	
	private List<PackageInfo> packageInfo;
	private ArrayList<PackageInfo> installedPackageList;
	private AppListAdapter appListAdapter;
	private ListView appListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		appListAdapter = new AppListAdapter(FireWallActivity.this, 
				getInstalledPackageInfo(FireWallActivity.this));
		appListView = (ListView)findViewById(R.id.app_list);
		appListView.setAdapter(appListAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				menuDialog(arg1); 
		     	}});
	}

	
	public ArrayList<PackageInfo> getInstalledPackageInfo(Context context)
	{
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		installedPackageList = new ArrayList<PackageInfo>();
		
		for(int i = 0 ; i<packageInfo.size();i++)
		{   PackageInfo pkgInfo = packageInfo.get(i);
			if((pkgInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0
					&&(pkgInfo.applicationInfo.flags&ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)==0)
			{
				installedPackageList.add(pkgInfo);
			}else
			{	
			}
		}
		return installedPackageList;	
	}
	
  	public void menuDialog(View arg1)
    {
	   final CharSequence[] items = {"Ӧ�ù���","ж��","��ϸ","����"};
	   final String pkname = arg1.getTag(R.id.tag_pkgname).toString();
	
	   AlertDialog dlg = new AlertDialog.Builder(FireWallActivity.this)
	   .setTitle("��ѡ��")
	   .setItems(items,new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog , int item){
		if(item == 0){
			showInstalledAppDetails(FireWallActivity.this, pkname);							
		   }
		if(item == 1){
			Uri uri = Uri.fromParts("package", pkname, null);  
			Intent intent = new Intent(Intent.ACTION_DELETE, uri);  
			startActivity(intent); 
		   }
		if(item == 2){
			AlertDialog detail = new AlertDialog.Builder(FireWallActivity.this)
			.setTitle("��ϸ��Ϣ")
			.setMessage("comging soon!")
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
		}
		else{	
		   }
		 }}).create();
       dlg.show();
       }
    public static void showInstalledAppDetails(Context context,String packageName){
		   Intent intent = new Intent();
		   final int apiLevel = Build.VERSION.SDK_INT;
		   if(apiLevel >= 9){
			   intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		   	   Uri uri = Uri.fromParts("package", packageName, null);
			   intent.setData(uri);
	         	}
		   else{
			   final String appPackageName = (apiLevel == 8 ? APP_PKG_NAME_22  
	                  : APP_PKG_NAME_21);
			   intent.setAction(Intent.ACTION_VIEW);
			   intent.setClassName(APP_DETAILS_PACKAGE_NAME,  
	                APP_DETAILS_CLASS_NAME);
			   intent.putExtra(appPackageName, packageName);
		          }
		    context.startActivity(intent);
	        }
}
