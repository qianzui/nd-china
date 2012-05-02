package com.hiapk.spearhead;

import java.sql.SQLData;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.AppInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
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

public class FireWallActivity extends Activity {
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
	private ArrayList<PackageInfo> myAppList;
	private Context mContext = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	ProgressDialog mydialog;
	long[] traffic;

	// private ListView appListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Toast.makeText(mContext, "下拉列表可以进行刷新!", Toast.LENGTH_LONG).show();
		// mydialog = ProgressDialog.show(this, "请稍等...", "正在读取...", true);
		initList();

	}

	public void initList() {
		Log.i("get ---- list----start", System.currentTimeMillis() + "");
		myAppList = getCompList(getInstalledPackageInfo(FireWallActivity.this));
		
		Log.i("get ---- list----end", System.currentTimeMillis() + "");
		appListAdapter = new AppListAdapter(FireWallActivity.this, myAppList);
		appListView = (MyListView) findViewById(R.id.app_list);
		appListView.setAdapter(appListAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				menuDialog(arg1);
			}
		});

		Log.i("update----start", System.currentTimeMillis() + "");
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						update_Sql();
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute(null);
			}
		});
		Log.i("update----end", System.currentTimeMillis() + "");
	}
	public ArrayList<PackageInfo> getCompList( ArrayList<PackageInfo> appList)
	{
        traffic = new  long[appList.size()];
        int[] number = new int[traffic.length];
        
		for (int i = 0; i < traffic.length; i++) {
			int uid = appList.get(i).applicationInfo.uid;
			traffic[i]=TrafficStats.getUidRxBytes(uid)
    		+ TrafficStats.getUidTxBytes(uid) ;	
		}
		
		for (int i = 0; i < traffic.length; i++) {
			number[i] = i;
		}
		for (int i = 0; i < traffic.length; i++) {
			long temp = 0;
			int k=0;
			for (int j = i; j < traffic.length; j++) {
				if (traffic[j] > temp) {
					temp = traffic[j];
					traffic[j]=traffic[i];
					traffic[i]=temp;
					k=number[j];
					number[j]=number[i];
					number[i]=k;
				}
			}
		}
		ArrayList<PackageInfo> myAppList = new ArrayList<PackageInfo>();
		for (int i = 0; i < number.length; i++) {
			PackageInfo pk = appList.get(number[i]);
			myAppList.add(pk);
		}
		return myAppList;
	}
	public void update_Sql() {
		sqlhelperTotal.initTablemobileAndwifi(mContext);
		if (SQLHelperTotal.TableWiFiOrG23 != ""
				&& sqlhelperTotal.getIsInit(mContext)) {
			// 进行数据记录
			AlarmSet alset = new AlarmSet();
			alset.StartAlarmMobile(mContext);
			sqlhelperUid.RecordUidwritestats(mContext, false);
		}
	}

	public ArrayList<PackageInfo> getInstalledPackageInfo(Context context) {
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		ArrayList<PackageInfo> appList = new ArrayList<PackageInfo>();

		for (int i = 0; i < packageInfo.size(); i++) {
			PackageInfo pkgInfo = packageInfo.get(i);
			PackageManager pkgmanager = context.getPackageManager();

			if (PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET,
							pkgInfo.applicationInfo.packageName)) {
			} else {
				// 获取总流量
				if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
						&& (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
					
					 if(TrafficStats.getUidRxBytes(pkgInfo.applicationInfo.uid)
	                    		+ TrafficStats.getUidTxBytes(pkgInfo.applicationInfo.uid) > 0)
	                    {
					appList.add(pkgInfo);
	                    }
				} else {
				}
			}
		}
		
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

	public void menuDialog(View arg1) {

		final CharSequence[] items = { "应用管理", "卸载", "详细", "返回" };
		final String pkname = arg1.getTag(R.id.tag_pkgname).toString();
		final String trafficup = arg1.getTag(R.id.tag_traffic_up).toString();
		final String trafficdown = arg1.getTag(R.id.tag_traffic_down)
				.toString();

		AlertDialog dlg = new AlertDialog.Builder(FireWallActivity.this)
				.setTitle("请选择")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							showInstalledAppDetails(FireWallActivity.this,
									pkname);
						}
						if (item == 1) {
							Uri uri = Uri.fromParts("package", pkname, null);
							Intent intent = new Intent(Intent.ACTION_DELETE,
									uri);
							startActivity(intent);
						}
						if (item == 2) {
							AlertDialog detail = new AlertDialog.Builder(
									FireWallActivity.this)
									.setTitle("详细信息")
									.setMessage(
											"上传：" + trafficup + "\n" + "下载："
													+ trafficdown)
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub

												}
											}).show();
						} else {
						}
					}
				}).create();
		dlg.show();
	}

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
}
