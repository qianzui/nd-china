package com.hiapk.spearhead;

import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.Info;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.progressdialog.CustomProgressDialog;
import com.hiapk.sqlhelper.SQLHelperFireWall.Data;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLStatic;
import com.hiapk.uidtraff.UidMonthTraff;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FireWallActivity extends Activity {
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	private static final String APP_PKG_NAME_22 = "pkg";
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	private List<PackageInfo> packageInfo;
	private AppListAdapter appListAdapter;
	public MyListView appListView;
	public ArrayList<PackageInfo> myAppList;
	private Context mContext = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	ProgressDialog mydialog;
	ProgressDialog pro;
	CustomProgressDialog customdialog;
	long[] traffic;
	HashMap<Integer, Data> mp;
	HashMap<Integer, Info> imageAndNameMap = new HashMap<Integer, Info>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		if (Block.fireTip(mContext)) {
			Toast.makeText(mContext, "下拉列表可以进行刷新!", Toast.LENGTH_SHORT).show();
		}
		initList();
		if (Block.isShowHelp(mContext)) {
			showHelp();
			Block.isShowHelpSet(mContext, false);
		}
	}

	public void showHelp() {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					SpearheadActivity.showHelp(mContext);
				} catch (Exception ex) {
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void initList() {
//		pro = ProgressDialog.show(mContext, "提示",
//				"获取列表中,请耐心等待,获取的时间长短取决于您安装软件的数量...");
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(
				mContext);
		customdialog = customProgressDialog.createDialog(mContext);
		customdialog.setCancelable(false);
		customProgressDialog.setTitile("提示");
		customProgressDialog.setMessage("获取列表中,请耐心等待,获取的时间长短取决于您安装软件的数量...");
		customdialog.show();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					setAdapter();
					customdialog.dismiss();
//					pro.dismiss();
				} catch (Exception ex) {
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (mp == null) {
					if (SQLStatic.uiddata != null) {
						mp = SQLStatic.uiddata;
					} else {
						AlarmSet alset = new AlarmSet();
						alset.StartAlarmUidTotal(mContext);
					}

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				myAppList = getCompList(getInstalledPackageInfo(FireWallActivity.this));
				getImageMap(myAppList);
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void setAdapter() {
		appListAdapter = new AppListAdapter(FireWallActivity.this, myAppList,
				imageAndNameMap);
		appListView = (MyListView)findViewById(R.id.app_list);
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
				new AsyncTask<Context, Void, Void>() {
					@Override
					protected Void doInBackground(Context... params) {
						AlarmSet alset = new AlarmSet();
						alset.StartAlarmUidTotal(mContext);
						while (mp == null) {
							if (SQLStatic.uiddata != null) {
								mp = SQLStatic.uiddata;
							}
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (Block.fireTip(mContext)) {
							Toast.makeText(mContext, "点击任意应用可查看更多选项!",
									Toast.LENGTH_SHORT).show();
						}
						myAppList = getCompList(getInstalledPackageInfo(FireWallActivity.this));
						getImageMap(myAppList);
						setAdapter();
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute(mContext);
			}
		});
	}

	// 排序
	public ArrayList<PackageInfo> getCompList(ArrayList<PackageInfo> appList) {
		traffic = new long[appList.size()];
		int[] number = new int[traffic.length];
		
		for (int i = 0; i < traffic.length; i++) {
			int uid = appList.get(i).applicationInfo.uid;
			if (mp == null) {
				if (SQLStatic.uiddata != null) {
					mp = SQLStatic.uiddata;
					if (mp.containsKey(uid)) {
						traffic[i] = mp.get(uid).upload + mp.get(uid).download;
					} else {
						traffic[i] = -1000;
					}
				} else {
					AlarmSet alset = new AlarmSet();
					alset.StartAlarmUidTotal(mContext);
					mp = SQLStatic.uiddata;
					if (mp.containsKey(uid)) {
						traffic[i] = mp.get(uid).upload + mp.get(uid).download;
					} else {
						traffic[i] = -1000;
					}
				}
			} else {
				if (SQLStatic.uiddata == null) {
					AlarmSet alset = new AlarmSet();
					alset.StartAlarmUidTotal(mContext);
					mp = SQLStatic.uiddata;
					
					if (mp.containsKey(uid)) {
						traffic[i] = mp.get(uid).upload + mp.get(uid).download;
					} else {
						traffic[i] = -1000;
					}
				} else {
					mp = SQLStatic.uiddata;
					if (mp.containsKey(uid)) {
						traffic[i] = mp.get(uid).upload + mp.get(uid).download;
					} else {
						traffic[i] = -1000;
					}
				}
			}
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
		//获取流量大小排序
		ArrayList<PackageInfo> myAppList = new ArrayList<PackageInfo>();
		ArrayList<PackageInfo> myAppList2 = new ArrayList<PackageInfo>();
		ArrayList<PackageInfo> showList = new ArrayList<PackageInfo>();
		for (int i = 0; i < number.length; i++) {
			PackageInfo pk = appList.get(number[i]);
			int uid = pk.applicationInfo.uid;
			
			myAppList.add(pk);
			long trafficdata = -1000;
			if (mp.containsKey(uid)) {
			trafficdata = mp.get(uid).upload + mp.get(uid).download;
			}
			if( trafficdata > 0){
				showList.add(pk);
			}else{
				myAppList2.add(pk);
			}
		}
		//按首字母排序
		for (int i = 0; i < showList.size(); i++) {
			PackageInfo pk = showList.get(i);
			int uid = pk.applicationInfo.uid;
		}
		String[] appname = new String[myAppList2.size()];
		for (int i = 0; i < appname.length; i++) {
			appname[i] = myAppList2.get(i).applicationInfo.loadLabel(getPackageManager()).toString()
			   .replaceAll(" ","").replaceAll(" ","");
		}
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		Arrays.sort(appname,cmp);
		int name[] = new int[appname.length];
		for (int i = 0; i < appname.length; i++) {
			for (int j = 0; j < myAppList2.size(); j++) {
				if(appname[i].equals(myAppList2.get(j).applicationInfo.loadLabel(getPackageManager()).toString().replaceAll(" ","").replaceAll(" ",""))){
					name[i] = j;
				}
			}
		}
		for (int i = 0; i < name.length; i++) {
			PackageInfo pk = myAppList2.get(name[i]);
			showList.add(pk);
		}
		return showList;
	}

	public HashMap<Integer, Info> getImageMap(ArrayList<PackageInfo> myAppList) {
		for (int i = 0; i < myAppList.size(); i++) {
			PackageInfo pkgInfo = myAppList.get(i);
			int uid = pkgInfo.applicationInfo.uid;
			final long up;
			final long down;
			if (mp.containsKey(uid)) {
				up = mp.get(uid).upload;
				down = mp.get(uid).download;
			} else {
				up = -1000;
				down = -1000;
			}
			Info info = new Info(
					pkgInfo.applicationInfo.loadIcon(getPackageManager()),
					pkgInfo.applicationInfo.loadLabel(getPackageManager())
							.toString(), up, down);
			imageAndNameMap.put(i, info);
		}
		return imageAndNameMap;
	}

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
		final PackageInfo pkgInfo = (PackageInfo) arg1.getTag(R.id.tag_pkginfo);
		final Drawable icon = pkgInfo.applicationInfo.loadIcon(mContext
				.getPackageManager());
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkname = pkgInfo.applicationInfo.packageName;
		final String appname = pkgInfo.applicationInfo.loadLabel(
				mContext.getPackageManager()).toString();
		final long up;
		final long down;
		if (mp.containsKey(uid)) {
			up = mp.get(uid).upload;
			down = mp.get(uid).download;
		} else {
			up = -1000;
			down = -1000;
		}
		final String trafficup;
		final String trafficdown;
		if (up == -1000 && down == -1000) {
			trafficup = unitHandler(0);
			trafficdown = unitHandler(0);
		} else {
			trafficup = unitHandler(up);
			trafficdown = unitHandler(down);
		}
		final Drawable d = mContext.getResources().getDrawable(R.drawable.bg_fire_option);
	    LayoutInflater factory = LayoutInflater.from(mContext);
		final View mDialogView = factory.inflate(R.layout.fire_options, null);
		final AlertDialog mDialog = new AlertDialog.Builder(FireWallActivity.this).create();
		mDialog.show();
		Window window = mDialog.getWindow();
		window.setContentView(mDialogView, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		final int heigh = window.getWindowManager().getDefaultDisplay()
		.getHeight();
        final int width = window.getWindowManager().getDefaultDisplay()
		.getWidth();
    	window.setLayout((int) (width * 0.8),LayoutParams.WRAP_CONTENT);
		final TextView manager = (TextView)mDialogView.findViewById(R.id.button_manager);
		final TextView detail = (TextView)mDialogView.findViewById(R.id.button_detail);
		final TextView uninstalled = (TextView)mDialogView.findViewById(R.id.button_uninstall);
		final TextView back = (TextView)mDialogView.findViewById(R.id.button_back);
		
		manager.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showInstalledAppDetails(FireWallActivity.this,
						pkname);
				manager.setBackgroundDrawable(d);
				mDialog.cancel();
				
			}
		});
		
		detail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			    LayoutInflater infalter = LayoutInflater.from(mContext);
			    final View  mDetailView = infalter.inflate(R.layout.fire_detail,null);
				final AlertDialog detailDialog = new AlertDialog.Builder(FireWallActivity.this).create();
				detailDialog.show();
				Window wd = detailDialog.getWindow();
				wd.setContentView(mDetailView, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				wd.setLayout((int) (width * 0.8),LayoutParams.WRAP_CONTENT);
				
				final TextView traffic_up = (TextView)mDetailView.findViewById(R.id.fire_traffic_up);
				final TextView traffic_down = (TextView)mDetailView.findViewById(R.id.fire_traffic_down);
				final Button detail_ok = (Button)mDetailView.findViewById(R.id.detail_ok);
				final Button detail_history = (Button)mDetailView.findViewById(R.id.detail_history);
				
				traffic_up.setText("上传： "+ trafficup);
				traffic_down.setText("下载： "+ trafficdown);
				
				detail_ok.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						detailDialog.cancel();
					}
				});
				detail_history.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext,UidMonthTraff.class);
						Bundle bData = new Bundle();
						bData.putInt("uid", uid);
						bData.putString("appname",appname);
						bData.putString("pkname",pkname);
						intent.putExtras(bData);
						mContext.startActivity(intent);
						detailDialog.cancel();
					}
				});
				detail.setBackgroundDrawable(d);
				mDialog.cancel();
			} 
		});
		
		uninstalled.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.fromParts("package", pkname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE,
						uri);
				startActivity(intent);
				uninstalled.setBackgroundDrawable(d);
				mDialog.cancel();
			}
			
		});
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back.setBackgroundDrawable(d);
				mDialog.cancel();
			}
			
		});
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
		// MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
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
