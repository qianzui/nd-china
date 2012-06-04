package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.Info;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.uidtraff.UidMonthTraff;
import com.umeng.analytics.MobclickAgent;

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
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
	public static MyListView appListView;
	public ArrayList<PackageInfo> myAppList;
	private Context mContext = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	ProgressDialog mydialog;
	ProgressDialog pro;
	long[] traffic;
	HashMap imageAndNameMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		initList();
		showtTip();
		
	}
	public void showtTip(){
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					if (Block.fireTip(mContext)) {
						Toast.makeText(mContext, "下拉列表可以进行刷新!",
								Toast.LENGTH_SHORT).show();
						Toast.makeText(mContext, "点击任意应用可查看更多选项!",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception ex) {
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void initList() {
		pro = ProgressDialog.show(mContext, "提示", "获取列表中,请耐心等待,获取的时间长短取决于您安装软件的数量...");
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					setAdapter();
					pro.dismiss();
				} catch (Exception ex) {
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("get ---- list----start", System.currentTimeMillis() + "");
				myAppList = getCompList(getInstalledPackageInfo(FireWallActivity.this));
				Log.i("get ---- list----end", System.currentTimeMillis() + "");
				Log.i("get ---- list.size", myAppList.size() + "");
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void setAdapter() {
		appListAdapter = new AppListAdapter(FireWallActivity.this, myAppList,
				imageAndNameMap);
		appListView = (MyListView) findViewById(R.id.app_list);
		appListView.setAdapter(appListAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				menuDialog(arg1);
			}
		});
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute(null);
			}
		});
	}

	// 排序
	public ArrayList<PackageInfo> getCompList(ArrayList<PackageInfo> appList) {
		traffic = new long[appList.size()];
		int[] number = new int[traffic.length];

		for (int i = 0; i < traffic.length; i++) {
			int uid = appList.get(i).applicationInfo.uid;
			traffic[i] = TrafficStats.getUidRxBytes(uid)
					+ TrafficStats.getUidTxBytes(uid);
		}

		for (int i = 0; i < traffic.length; i++) {
			number[i] = i;
		}
		for (int i = 0; i < traffic.length; i++) {
			long temp = 0;
			int k = 0;
			for (int j = i; j < traffic.length; j++) {
				if (traffic[j] > temp) {
					temp = traffic[j];
					traffic[j] = traffic[i];
					traffic[i] = temp;
					k = number[j];
					number[j] = number[i];
					number[i] = k;
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

	// 获取应用列表
	public ArrayList<PackageInfo> getInstalledPackageInfo(Context context) {
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		PackageManager pm = getPackageManager();
		ArrayList<PackageInfo> appList = new ArrayList<PackageInfo>();
		imageAndNameMap = new HashMap<Integer, Info>();
		for (int i = 0; i < packageInfo.size(); i++) {
			PackageInfo pkgInfo = packageInfo.get(i);
			if (pkgInfo.applicationInfo.uid >= 10000
					&& PackageManager.PERMISSION_GRANTED == pm.checkPermission(
							Manifest.permission.INTERNET,
							pkgInfo.applicationInfo.packageName)) {
				if (Block.filter.contains(pkgInfo.applicationInfo.packageName)) {
				} else {
					// if ((pkgInfo.applicationInfo.flags &
					// ApplicationInfo.FLAG_SYSTEM) == 0
					// && (pkgInfo.applicationInfo.flags &
					// ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
					Info info = new Info(
							pkgInfo.applicationInfo
									.loadIcon(getPackageManager()),
							pkgInfo.applicationInfo.loadLabel(
									getPackageManager()).toString());
					imageAndNameMap.put(pkgInfo.applicationInfo.uid, info);
					appList.add(pkgInfo);
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

		final CharSequence[] items = { "应用管理", "卸载", "流量详情", "返回" };

		final PackageInfo pkgInfo = (PackageInfo) arg1.getTag(R.id.tag_pkginfo);
		final Drawable icon = pkgInfo.applicationInfo.loadIcon(mContext
				.getPackageManager());
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkname = pkgInfo.applicationInfo.packageName;
		final String appname = pkgInfo.applicationInfo.loadLabel(
				mContext.getPackageManager()).toString();

		long down = judge(TrafficStats.getUidRxBytes(uid));
		long up = judge(TrafficStats.getUidTxBytes(uid));
		final String trafficup = unitHandler(up);
		final String trafficdown = unitHandler(down);

		final AlertDialog dlg = new AlertDialog.Builder(FireWallActivity.this)
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
											})
									.setNegativeButton(
											"历史统计",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													Intent intent = new Intent();
													intent.setClass(mContext,
															UidMonthTraff.class);
													Bundle bData = new Bundle();
													bData.putInt("uid", uid);
													bData.putString("appname",
															appname);
													bData.putString("pkname",
															pkname);
													intent.putExtras(bData);
													mContext.startActivity(intent);
												}
											}).show();
						} else {
						}
					}
				}).create();
		dlg.show();
	}

	public long judge(long tff) {
		if (tff == -1)
			tff = 0;
		return tff;
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// umeng
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
