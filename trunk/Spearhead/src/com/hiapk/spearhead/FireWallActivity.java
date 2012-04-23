package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hiapk.firewall.AppInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.firewall.Mycomparator;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FireWallActivity extends Activity{
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName"; 
	/** 
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本) 
	 */ 
	private static final String APP_PKG_NAME_22 = "pkg"; 
	/** 
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2) 
	 */ 
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings"; 
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails"; 

	
	private List<PackageInfo> packageInfo;
	private AppListAdapter appListAdapter;
	private MyListView appListView;
	private ArrayList<AppInfo> myAppList;
	private ArrayList<AppInfo> appListComp;
	private Context mContext = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
//	private ListView appListView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		initList();
		
	}

	public void initList()
	{
		myAppList = getInstalledPackageInfo(FireWallActivity.this);
		appListAdapter = new AppListAdapter(FireWallActivity.this, 
				myAppList);
		appListView = (MyListView)findViewById(R.id.app_list);
		appListView.setAdapter(appListAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				menuDialog(arg1); 
		     	}});
		
		
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					 @Override
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
//						myAppList = getInstalledPackageInfo(FireWallActivity.this);
						return null;
				    	}

					@Override
					protected void onPostExecute(Void result) {
						update_Sql();
						myAppList.clear();
						myAppList = getInstalledPackageInfo(FireWallActivity.this);
						appListAdapter = new AppListAdapter(FireWallActivity.this, 
								myAppList);
						appListView = (MyListView)findViewById(R.id.app_list);
						appListView.setAdapter(appListAdapter);
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}

				}.execute(null);
			}
		});
	}
	
	public void update_Sql()
	{    
	   sqlhelperTotal.initTablemobileAndwifi(mContext);
		if (SQLHelperTotal.TableWiFiOrG23 != "" && sqlhelperTotal.getIsInit(mContext)) {
			// 进行数据记录
			sqlhelperTotal.RecordTotalwritestats(mContext, false);
			sqlhelperUid.RecordUidwritestats(mContext, false);
		} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
			sqlhelperTotal.initTablemobileAndwifi(mContext);
	}
	}
	public ArrayList<AppInfo> getInstalledPackageInfo(Context context)
	{
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>();			
		for(int i = 0 ; i<packageInfo.size();i++)
		{   
			PackageInfo pkgInfo = packageInfo.get(i);
		
			//获取总流量
			if((pkgInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0
					&&(pkgInfo.applicationInfo.flags&ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)==0)
			{
				Time time = new Time();
				time.setToNow();
				int year = time.year;
				int month = time.month + 1;
				SQLHelperUid sqlHelperUid =new SQLHelperUid();			  
				long[] trafficArray = new long[64];
				trafficArray = sqlHelperUid.SelectuidData(mContext, year,month,pkgInfo.applicationInfo.uid);
				String trafficUp = unitHandler(trafficArray[0]);
				String trafficDown = unitHandler(trafficArray[63]);	
			    String trafficTotal = unitHandler(trafficArray[0] + trafficArray[63]);
				
			    AppInfo appInfo = new AppInfo();	
			    appInfo.setAppname(pkgInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
			    appInfo.setIcon(pkgInfo.applicationInfo.loadIcon(mContext.getPackageManager()));
			    appInfo.setTrafficDown(trafficDown);
			    appInfo.setTrafficUp(trafficUp);
			    appInfo.setPackageName(pkgInfo.applicationInfo.packageName);
			    appInfo.setTrafficTotal(trafficTotal);
			    appInfo.setTrafficTotalComparator(trafficArray[0] + trafficArray[63]);
			    appList.add(appInfo);

			}else
			{	
			}
		}
		Mycomparator comp = new Mycomparator();				
		appListComp = comp.comparator(appList);
		return appList;
	}
	
	public String unitHandler(long count) {
		String value = null;
		long temp = count;
		float floatnum = count;
		if ((temp = temp / 1000) < 1) {
			value = count + "B";
		} else if ((floatnum = (float) temp / 1000) < 1) {
			value = temp + "KB";
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + "MB";
		}
		return value;
	}
	
  	public void menuDialog(View arg1)
    {
  	   
	   final CharSequence[] items = {"应用管理","卸载","详细","返回"};
	   final String pkname = arg1.getTag(R.id.tag_pkgname).toString();
	   final String trafficup =  arg1.getTag(R.id.tag_traffic_up).toString();
	   final String trafficdown =  arg1.getTag(R.id.tag_traffic_down).toString();
	   
	   AlertDialog dlg = new AlertDialog.Builder(FireWallActivity.this)
	   .setTitle("请选择")
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
			.setTitle("详细信息")
			.setMessage("上传：" + trafficup + "\n" + "下载：" + trafficdown)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
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
