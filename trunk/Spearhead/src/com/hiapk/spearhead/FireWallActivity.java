package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.MyCompName;
import com.hiapk.firewall.MyCompTraffic;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.progressdialog.CustomProgressDialog;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall.Data;
import com.hiapk.uidtraff.UidMonthTraff;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
	CustomProgressDialog customdialog;
	public ArrayList<PackageInfo> myAppList;
	public ArrayList<PackageInfo> myAppList2;
	private Context mContext = this;
	ProgressDialog mydialog;
	ProgressDialog pro;
	long[] traffic;
	HashMap<Integer, Data> mp;
	private ArrayList<Integer> uidList;
	long time = 0;
	Handler handler = new Handler();
	Handler handler2 = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
		// initList();
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(
				mContext);
		customdialog = customProgressDialog.createDialog(mContext);
		customdialog.setCancelable(false);
		customProgressDialog.setTitile("提示");
		customProgressDialog.setMessage("获取列表中,请耐心等待,获取的时间长短取决于您安装软件的数量...");
		customdialog.show();
		handler.post(new Runnable() {
			@Override
			public void run() {
				initList();
			}
		});
		handler2 = new Handler() {
			public void handleMessage(Message msg) {
				try {
					setAdapter();
					customdialog.dismiss();
					if (Block.isShowHelp(mContext)) {
						showHelp(mContext);
						SpearheadActivity.isHide = true;
					} else {
						if (Block.fireTip(mContext)) {
							Toast.makeText(mContext, "下拉列表可以进行刷新!",
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception ex) {
				}
			}
		};
	}

	public void initList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getList(mContext);
				mp = getData();
				if (Block.appList != null && Block.appnamemap != null) {
					int i = 0;
					do {
						try {
							i++;
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (Block.appList.size() == Block.appnamemap.size()) {
							break;
						}
						if (i >= 30) {
							break;
						}
					} while (Block.appList.size() != Block.appnamemap.size());
				} else {
					getList(mContext);
					Splash.getList(mContext);
				}
				uidList = comp(Block.appList);
				handler2.sendEmptyMessage(0);
			}
		}).start();
	}

	public void showHelp(final Context mContext) {
		Drawable d = mContext.getResources().getDrawable(R.drawable.fire_help);
		SpearheadActivity.firehelp.setBackgroundDrawable(d);
		// firehelp.setVisibility(View.VISIBLE);
		SpearheadActivity.firehelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SpearheadActivity.firehelp.setVisibility(View.INVISIBLE);
				Block.isShowHelpSet(mContext, false);
			}
		});
	}

	public void setAdapter() {
		appListView = (MyListView) findViewById(R.id.app_list);
		appListAdapter = new AppListAdapter(FireWallActivity.this, myAppList,
				appListView, mp, Block.appnamemap, Block.appList, uidList);
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
						mp = getData();
						getList(mContext);
						Splash.getList(mContext);
						uidList = comp(Block.appList);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						MyListView.loadImage();
						setAdapter();
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute();
			}
		});
	}

	public ArrayList<PackageInfo> getList(Context context) {
		packageInfo = context.getPackageManager().getInstalledPackages(0);
		final PackageManager pm = getPackageManager();
		myAppList = new ArrayList<PackageInfo>();
		Block.appList = new HashMap<Integer, PackageInfo>();
		for (int i = 0; i < packageInfo.size(); i++) {
			final PackageInfo pkgInfo = packageInfo.get(i);
			final int uid = pkgInfo.applicationInfo.uid;
			final String pkgName = pkgInfo.applicationInfo.packageName;
			if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(
					Manifest.permission.INTERNET, pkgName)) {
				if (Block.filter.contains(pkgName)) {
				} else {
					Block.appList.put(uid, pkgInfo);
					myAppList.add(pkgInfo);
					Block.appList.put(uid, pkgInfo);
				}
			}
		}
		return myAppList;
	}

	public HashMap<Integer, Data> getData() {
		do {
			showLog("TableWiFiOrG23=" + SQLStatic.TableWiFiOrG23);
			if (SQLStatic.TableWiFiOrG23 != "") {
				AlarmSet alset = new AlarmSet();
				alset.StartAlarmUid(mContext);
			} else {
				SQLHelperFireWall SQLFire = new SQLHelperFireWall();
				SQLFire.resetMP(mContext);// alset.StartAlarm(mContext);
			}
			if (SQLStatic.uiddata != null) {
				mp = SQLStatic.uiddata;
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (mp == null);
		return mp;
	}

	public ArrayList<Integer> comp(HashMap<Integer, PackageInfo> appList) {
		uidList = new ArrayList<Integer>();
		ArrayList<Integer> uidList2 = new ArrayList<Integer>();
		ArrayList keys = new ArrayList(appList.keySet());
		for (int i = 0; i < keys.size(); i++) {
			int uid = (Integer) keys.get(i);
			uidList.add(uid);
		}
		MyCompName mn = new MyCompName();
		mn.init(Block.appnamemap);
		Collections.sort(uidList, mn);

		MyCompTraffic mt = new MyCompTraffic();
		mt.init(mp);
		Collections.sort(uidList, mt);
		return uidList;
	}

	public void menuDialog(View arg1) {
		final PackageInfo pkgInfo = (PackageInfo) arg1.getTag(R.id.tag_pkginfo);
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkname = pkgInfo.applicationInfo.packageName;
		final String appname = pkgInfo.applicationInfo.loadLabel(
				getPackageManager()).toString();
		;
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

				traffic_up.setText("上传： " + trafficup);
				traffic_down.setText("下载： " + trafficdown);

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
		if (SQLStatic.TableWiFiOrG23 != "") {
			AlarmSet alset = new AlarmSet();
			alset.StartAlarmUid(mContext);
		} else {
			SQLHelperFireWall SQLFire = new SQLHelperFireWall();
			SQLFire.resetMP(mContext);// alset.StartAlarm(mContext);
		}
		// MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("FireWallActivity", string);
		}
	}
}
