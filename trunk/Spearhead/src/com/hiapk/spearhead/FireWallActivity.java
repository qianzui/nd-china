package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.firewall.AppInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.MyComparator;
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
import android.view.View;
import android.view.View.OnClickListener;
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
	ArrayList<AppInfo> mList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		initList();
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
		// pro = ProgressDialog.show(mContext, "��ʾ",
		// "��ȡ�б���,�����ĵȴ�,��ȡ��ʱ�䳤��ȡ��������װ���������...");
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(
				mContext);
		customdialog = customProgressDialog.createDialog(mContext);
		customdialog.setCancelable(false);
		customProgressDialog.setTitile("��ʾ");
		customProgressDialog.setMessage("��ȡ�б���,�����ĵȴ�,��ȡ��ʱ�䳤��ȡ��������װ���������...");
		customdialog.show();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					
					setAdapter();
					customdialog.dismiss();
					if (Block.isShowHelp(mContext)) {
						SpearheadActivity.showHelp(mContext);
						Block.isShowHelpSet(mContext, false);
					} else {
						if (Block.fireTip(mContext)) {
							Toast.makeText(mContext, "�����б���Խ���ˢ��!",
									Toast.LENGTH_SHORT).show();
						}
					} 
					// pro.dismiss();
				} catch (Exception ex) {
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mList = getCompList(getList(mContext));
				handler.sendEmptyMessage(0);
			}
		}).start();
	}
 
	public void setAdapter() {
		appListAdapter = new AppListAdapter(FireWallActivity.this, mList);
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
						mList = getCompList(getList(mContext));
						setAdapter();
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute();
			}
		});
	}

	public ArrayList<AppInfo> getList(Context context) {
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		PackageManager pm = getPackageManager();
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>();

		Log.i("start..........", System.currentTimeMillis() + "");
//		do {
//			AlarmSet alset = new AlarmSet();
//			alset.StartAlarmUidTotal(mContext);
//			if (SQLStatic.uiddata != null) {
//				mp = SQLStatic.uiddata;
//				break;
//			}
//		} while(mp == null);
//		
//		Iterator it = mp.entrySet().iterator();
//		while(it.hasNext()){ 
//			Log.i("mp's key..........", it.next() + "");
//		} 
		Log.i("end..........", System.currentTimeMillis() + "");
		for (int i = 0; i < packageInfo.size(); i++) {
			PackageInfo pkgInfo = packageInfo.get(i);
			int uid = pkgInfo.applicationInfo.uid;
			if (pkgInfo.applicationInfo.uid >= 10000
					&& PackageManager.PERMISSION_GRANTED == pm.checkPermission(
							Manifest.permission.INTERNET,
							pkgInfo.applicationInfo.packageName)) {
				if (Block.filter.contains(pkgInfo.applicationInfo.packageName)) {
				} else {
					AppInfo ai = new AppInfo(
							pkgInfo.applicationInfo.loadIcon(pm),
							pkgInfo.applicationInfo.loadLabel(pm).toString(),
							pkgInfo.applicationInfo.packageName, uid, 0, 0);
					appList.add(ai);
				}
			}
		}
		return appList;
	}
	
    public ArrayList<AppInfo>  getCompList(ArrayList<AppInfo> list){
		do {
			AlarmSet alset = new AlarmSet();
			alset.StartAlarmUidTotal(mContext);
			if (SQLStatic.uiddata != null) {
				mp = SQLStatic.uiddata;
				break;
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while(mp == null);
		for (int i = 0; i < list.size(); i++) {
			AppInfo ai = list.get(i);
			long up = 0;
			long down = 0;
			if (mp.containsKey(ai.uid)) {
				up = mp.get(ai.uid).upload;
				down = mp.get(ai.uid).download;
			} else {
				up = -1000;
				down = -1000;
			}
			ai.up = up;
			ai.down = down;
			Log.i("mlist", ai.up + ai.down + "");
		}
		return comp(list);
	}

	public ArrayList<AppInfo> comp(ArrayList<AppInfo> appList) {
		ArrayList<AppInfo> showList = new ArrayList<AppInfo>();
		ArrayList<AppInfo> showList2 = new ArrayList<AppInfo>();
		for (int i = 0; i < appList.size(); i++) {
			AppInfo ai1 = appList.get(i);
			for (int j = i; j < appList.size(); j++) {
				AppInfo ai2 = appList.get(j);
				if (ai1.up + ai1.down < ai2.up + ai2.down) {
					AppInfo temp = new AppInfo(ai1.d, ai1.appname,
							ai1.packagename, ai1.uid, ai1.up, ai1.down);
					ai1.appname = ai2.appname;
					ai1.d = ai2.d;
					ai1.packagename = ai2.packagename;
					ai1.uid = ai2.uid;
					ai1.up = ai2.up;
					ai1.down = ai2.down;

					ai2.appname = temp.appname;
					ai2.d = temp.d;
					ai2.packagename = temp.packagename;
					ai2.uid = temp.uid;
					ai2.up = temp.up;
					ai2.down = temp.down;
				}
			}
		}
		for (int i = 0; i < appList.size(); i++) {
			AppInfo ai = appList.get(i);
			if (ai.up + ai.down > 0) {
				showList.add(ai);
			} else {
				showList2.add(ai);
			}
		}
		MyComparator mc = new MyComparator();
		Collections.sort(showList2, mc);
		for (int i = 0; i < showList2.size(); i++) {
			AppInfo ai = showList2.get(i);
			showList.add(ai);
		}
		return showList;
	}
	public void menuDialog(View arg1) {
		final AppInfo pkgInfo = (AppInfo) arg1.getTag(R.id.tag_pkginfo);
		final Drawable icon = pkgInfo.d;
		final int uid = pkgInfo.uid;
		final String pkname = pkgInfo.packagename;
		final String appname = pkgInfo.appname;
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
			trafficup = UnitHandler.unitHandlerAccurate(0);
			trafficdown = UnitHandler.unitHandlerAccurate(0);
		} else {
			trafficup = UnitHandler.unitHandlerAccurate(up);
			trafficdown = UnitHandler.unitHandlerAccurate(down);
		}
		final Drawable d = mContext.getResources().getDrawable(
				R.drawable.bg_fire_option);
		LayoutInflater factory = LayoutInflater.from(mContext);
		final View mDialogView = factory.inflate(R.layout.fire_options, null);
		final AlertDialog mDialog = new AlertDialog.Builder(
				FireWallActivity.this).create();
		mDialog.show();
		Window window = mDialog.getWindow();
		window.setContentView(mDialogView, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		final int heigh = window.getWindowManager().getDefaultDisplay()
				.getHeight();
		final int width = window.getWindowManager().getDefaultDisplay()
				.getWidth();
		window.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);
		final TextView manager = (TextView) mDialogView
				.findViewById(R.id.button_manager);
		final TextView detail = (TextView) mDialogView
				.findViewById(R.id.button_detail);
		final TextView uninstalled = (TextView) mDialogView
				.findViewById(R.id.button_uninstall);
		final TextView back = (TextView) mDialogView
				.findViewById(R.id.button_back);

		manager.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showInstalledAppDetails(FireWallActivity.this, pkname);
				manager.setBackgroundDrawable(d);
				mDialog.cancel();

			}
		});

		detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater infalter = LayoutInflater.from(mContext);
				final View mDetailView = infalter.inflate(R.layout.fire_detail,
						null);
				final AlertDialog detailDialog = new AlertDialog.Builder(
						FireWallActivity.this).create();
				detailDialog.show();
				Window wd = detailDialog.getWindow();
				wd.setContentView(mDetailView, new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				wd.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);

				final TextView traffic_up = (TextView) mDetailView
						.findViewById(R.id.fire_traffic_up);
				final TextView traffic_down = (TextView) mDetailView
						.findViewById(R.id.fire_traffic_down);
				final Button detail_ok = (Button) mDetailView
						.findViewById(R.id.detail_ok);
				final Button detail_history = (Button) mDetailView
						.findViewById(R.id.detail_history);

				traffic_up.setText("�ϴ��� " + trafficup);
				traffic_down.setText("���أ� " + trafficdown);

				detail_ok.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						detailDialog.cancel();
					}
				});
				detail_history.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext, UidMonthTraff.class);
						Bundle bData = new Bundle();
						bData.putInt("uid", uid);
						bData.putString("appname", appname);
						bData.putString("pkname", pkname);
						intent.putExtras(bData);
						mContext.startActivity(intent);
						detailDialog.cancel();
					}
				});
				detail.setBackgroundDrawable(d);
				mDialog.cancel();
			}
		});

		uninstalled.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.fromParts("package", pkname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE, uri);
				startActivity(intent);
				uninstalled.setBackgroundDrawable(d);
				mDialog.cancel();
			}

		});

		back.setOnClickListener(new OnClickListener() {

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
