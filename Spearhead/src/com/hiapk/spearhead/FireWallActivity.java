package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hiapk.bean.DatauidHash;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.MyCompName;
import com.hiapk.firewall.MyCompNotifName;
import com.hiapk.firewall.MyCompTraffic;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.NotifListAdapter;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall;
import com.hiapk.ui.custom.CustomDialogOtherBeen;
import com.hiapk.ui.scene.UidMonthTraff;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FireWallActivity extends Activity {
	private String TAG = "firewallActivity";
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	private static final String APP_PKG_NAME_22 = "pkg";
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	private List<PackageInfo> packageInfo;
	protected ArrayList<LinearLayout> menuList = new ArrayList<LinearLayout>();
	protected ArrayList<String[]> notificationInfos = new ArrayList<String[]>();
	protected SharedPrefrenceData sharedpref;
	public static AppListAdapter appListAdapter;
	public static MyListView appListView;
	public static PopupWindow mPop;
	public LinearLayout loading_content;
	public ArrayList<PackageInfo> myAppList;
	public ArrayList<PackageInfo> myAppList2;
	private Button setting_button;
	public TextView firewall_details;
	public RelativeLayout title_normal;
	public TextView title_notif;
	public TextView firewall_title;
	public RelativeLayout main2TitleBackground;
	public Dialog bubbleDialog;
	public Animation showAction;
	public View bubbleView;
	public String savedUids_wifi = "";
	public String savedUids_3g = "";
	private Context mContext = this;
	public ProgressDialog mydialog;
	public ProgressDialog pro;
	public static ArrayList<Integer> uidList = new ArrayList<Integer>();;
	long time = 0;
	Handler handler = new Handler();
	Handler handler2 = new Handler();
	public static int banPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		init();
		initUidData();
		handler.post(new Runnable() {
			@Override
			public void run() {
				initList();
			}
		});
		handler2 = new Handler() {
			public void handleMessage(Message msg) {
				try {
					if (sharedpref.getFireWallType() == 5) {
						if (NotificationInfo.notificationRes.length() == 0) {
							if (NotificationInfo.isgettingdata == false) {
								new AsyncTaskGetAdbArrayListonResume()
										.execute(mContext);
							}
						} else {
							setAdapterNotif();
						}
					} else {
						setAdapter();
					}
					loading_content.setVisibility(View.GONE);
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

	private void initUidData() {
		SQLStatic.uiddata = null;
		SQLHelperFireWall sql = new SQLHelperFireWall();
		sql.resetMP(mContext);
	}

	public void init() {
		sharedpref = new SharedPrefrenceData(mContext);
		loading_content = (LinearLayout) findViewById(R.id.loading_content);
		appListView = (MyListView) findViewById(R.id.app_list);
		firewall_details = (TextView) findViewById(R.id.firewall);
		firewall_title = (TextView) findViewById(R.id.firewall_title);
		setting_button = (Button) findViewById(R.id.setting_button);
		main2TitleBackground = (RelativeLayout) findViewById(R.id.main2TitleBackground);
		title_normal = (RelativeLayout) findViewById(R.id.title_normal);
		title_notif = (TextView) findViewById(R.id.title_notif);
		main2TitleBackground.setBackgroundResource(SkinCustomMains
				.buttonTitleBackground());
		loading_content.setVisibility(View.VISIBLE);
		// 为了退出。
		SpearheadApplication.getInstance().addActivity(this);
		switch (sharedpref.getFireWallType()) {
		case 0:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("今日流量排行");
			break;
		case 1:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("本周流量排行");
			break;
		case 2:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("本月流量排行");
			break;
		case 3:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("移动流量排行");
			break;
		case 4:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("WIFI流量排行");
			break;
		case 5:
			title_normal.setVisibility(View.INVISIBLE);
			title_notif.setVisibility(View.VISIBLE);
			firewall_title.setText("通知栏流量排行");
			break;
		default:
			title_normal.setVisibility(View.VISIBLE);
			firewall_title.setText("今日流量排行");
			break;
		}
		settingShowList();
		;
	}

	public void settingShowList() {
		bubbleView = getLayoutInflater().inflate(R.layout.fire_setting, null);
		Button bt_today = (Button) bubbleView.findViewById(R.id.bt_today);
		Button bt_week = (Button) bubbleView.findViewById(R.id.bt_week);
		Button bt_month = (Button) bubbleView.findViewById(R.id.bt_month);
		Button bt_mobile = (Button) bubbleView.findViewById(R.id.bt_mobile);
		Button bt_wifi = (Button) bubbleView.findViewById(R.id.bt_wifi);
		Button bt_notif = (Button) bubbleView.findViewById(R.id.bt_notif);
		mPop = new PopupWindow(bubbleView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		setting_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPop.isShowing()) {
					mPop.dismiss();
				} else {
					mPop.showAsDropDown(v);
				}
			}
		});
		bt_today.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchList(0);

			}
		});
		bt_week.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchList(1);

			}
		});
		bt_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchList(2);
			}
		});
		bt_mobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchList(3);
			}
		});
		bt_wifi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchList(4);
			}
		});
		bt_notif.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchList(5);
			}
		});
	}

	public void switchList(int i) {
		mPop.dismiss();
		sharedpref.setFireWallType(i);
		main2TitleBackground.setBackgroundResource(SkinCustomMains
				.buttonTitleBackground());
		switch (i) {
		case 0:
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			firewall_title.setText("今日流量排行");
			setNewDataForList();
			break;
		case 1:
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			firewall_title.setText("本周流量排行");
			setNewDataForList();
			break;
		case 2:
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			firewall_title.setText("本月流量排行");
			setNewDataForList();
			break;
		case 3:
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			firewall_title.setText("移动流量排行");
			setNewDataForList();
			break;
		case 4:
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			firewall_title.setText("WIFI流量排行");
			setNewDataForList();
			break;
		case 5:
			firewall_title.setText("通知栏流量排行");
			if (NotificationInfo.notificationRes.length() == 0) {
				if (NotificationInfo.isgettingdata == false) {
					new AsyncTaskGetAdbArrayListonResume().execute(mContext);
				}
			} else {
				setAdapterNotif();
			}
			break;

		}
	}

	public void setNewDataForList() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				getList(mContext);
				Splash.getList(mContext);
				initUidData();
				while (SQLStatic.uiddata == null) {
					if (SQLStatic.uiddata != null) {
						break;
					}
				}
				;
				uidList = comp(Block.appList);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				loading_content.setVisibility(View.INVISIBLE);
				appListView.setVisibility(View.VISIBLE);
				setAdapter();
			}
		}.execute();
	}

	public void setAdapterNotif() {
		title_normal.setVisibility(View.INVISIBLE);
		title_notif.setVisibility(View.VISIBLE);
		appListView.setVisibility(View.VISIBLE);
		loading_content.setVisibility(View.INVISIBLE);
		notificationInfos = NotificationInfo
				.getNotificationApp(NotificationInfo.notificationRes);
		MyCompNotifName comp = new MyCompNotifName();
		comp.init(mContext);
		Collections.sort(notificationInfos, comp);
		final NotifListAdapter notifAdapter = new NotifListAdapter(mContext,
				notificationInfos);
		appListView.setAdapter(notifAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mPop.isShowing()) {
					mPop.dismiss();
				}
				notifMenuDialog(view);
			}
		});
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						getList(mContext);
						Splash.getList(mContext);
						initUidData();
						while (SQLStatic.uiddata == null) {
							if (SQLStatic.uiddata != null) {
								break;
							}
						}
						;
						uidList = comp(Block.appList);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						if (NotificationInfo.notificationRes.length() == 0) {
							if (NotificationInfo.isgettingdata == false) {
								new AsyncTaskGetAdbArrayListonResume()
										.execute(mContext);
							}
						} else {
							setAdapterNotif();
						}
						notifAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute();
			}
		});
		NotificationInfo.notificationRes = new StringBuilder();
	}

	public void initList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getList(mContext);
				Splash.getList(mContext);
				int i = 0;
				Logs.i("test", "begin");
				do {
					try {
						i++;
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (Block.appList.size() == Block.appnamemap.size()) {
						Logs.i("test", "end1");
						break;
					}
					if (i >= 30) {
						Logs.i("test", "end1.1");
						break;
					}
				} while (Block.appList.size() != Block.appnamemap.size());
				do {
					if (SQLStatic.uiddata != null) {
						Logs.i("test", "end2");
						break;
					}
				} while (SQLStatic.uiddata == null);
				uidList = comp(Block.appList);
				handler2.sendEmptyMessage(0);
			}
		}).start();
	}

	public void setAdapter() {
		title_notif.setVisibility(View.INVISIBLE);
		title_normal.setVisibility(View.VISIBLE);
		firewall_details.setText(" " + getAppNum(0) + " ");
		appListView.setVisibility(View.VISIBLE);
		loading_content.setVisibility(View.INVISIBLE);
		Context context = FireWallActivity.this.getParent();
		appListAdapter = new AppListAdapter(context, myAppList,
				Block.appnamemap, SQLStatic.uiddata, Block.appList, uidList);
		appListView.setAdapter(appListAdapter);
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mPop.isShowing()) {
					mPop.dismiss();
				}
				menuDialog(arg1);
			}
		});
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						getList(mContext);
						Splash.getList(mContext);
						initUidData();
						while (SQLStatic.uiddata == null) {
							if (SQLStatic.uiddata != null) {
								break;
							}
						}
						uidList = comp(Block.appList);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						MyListView.loadImage();
						if (sharedpref.getFireWallType() == 5) {
							if (NotificationInfo.notificationRes.length() == 0) {
								if (NotificationInfo.isgettingdata == false) {
									new AsyncTaskGetAdbArrayListonResume()
											.execute(mContext);
								}
							} else {
								setAdapterNotif();
							}
						} else {
							setAdapter();
						}
						appListAdapter.notifyDataSetChanged();
						appListView.onRefreshComplete();
					}
				}.execute();
			}
		});
	}

	public void showHelp(final Context mContext) {
		Drawable d = mContext.getResources().getDrawable(R.drawable.fire_help);
		SpearheadActivity.firehelp.setBackgroundDrawable(d);
		SpearheadActivity.firehelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SpearheadActivity.firehelp.setVisibility(View.INVISIBLE);
				Block.isShowHelpSet(mContext, false);
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
		mt.init(mContext, SQLStatic.uiddata);
		Collections.sort(uidList, mt);

		return uidList;
	}

	public int getAppNum(int type) {
		int j = 0;
		int m = 0;
		int w = 0;
		if (sharedpref.getFireWallType() == 3) {
			for (int i = 0; i < uidList.size(); i++) {
				if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
					if ((SQLStatic.uiddata.get(uidList.get(i))
							.getUploadmobile() + SQLStatic.uiddata.get(
							uidList.get(i)).getDownloadmobile()) > 0) {
						m++;
					}
				}
			}
			return m;
		} else if (sharedpref.getFireWallType() == 4) {
			for (int i = 0; i < uidList.size(); i++) {
				if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
					if ((SQLStatic.uiddata.get(uidList.get(i)).getUploadwifi() + SQLStatic.uiddata
							.get(uidList.get(i)).getDownloadwifi()) > 0) {
						w++;
					}
				}
			}
			return w;
		} else {
			for (int i = 0; i < uidList.size(); i++) {
				if (SQLStatic.uiddata.containsKey(uidList.get(i))) {
					if (SQLStatic.uiddata.get(uidList.get(i)).getTotalTraff() > 0) {
						j++;
					}
				}
			}
			return j;
		}
	}

	public void notifMenuDialog(View arg1) {
		final PackageInfo pkgInfo = (PackageInfo) arg1
				.getTag(R.id.tag_notif_pkgInfo);
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkgname = pkgInfo.applicationInfo.packageName;
		final LinearLayout ll = (LinearLayout) arg1
				.findViewById(R.id.notif_menu);
		if (menuList.size() == 0) {
			if (ll.isShown()) {
				ll.setVisibility(View.GONE);
				menuList.clear();
			} else {
				ll.setVisibility(View.VISIBLE);
				menuList.add(ll);
			}
		} else {
			if (ll.isShown()) {
				ll.setVisibility(View.GONE);
				menuList.clear();
			} else {
				for (int i = 0; i < menuList.size(); i++) {
					menuList.get(i).setVisibility(View.GONE);
				}
				ll.setVisibility(View.VISIBLE);
				menuList.add(ll);
			}
		}

		Button bt_manager = (Button) arg1.findViewById(R.id.notif_manage);
		Button bt_detail = (Button) arg1.findViewById(R.id.notif_detail);
		Button bt_uninstall = (Button) arg1
				.findViewById(R.id.notif_uninstalled);

		bt_manager.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showInstalledAppDetails(FireWallActivity.this, pkgname);
				ll.setVisibility(View.GONE);
			}
		});

		if (FireWallActivity.uidList.contains(uid)
				&& (PackageManager.PERMISSION_GRANTED == getPackageManager()
						.checkPermission(Manifest.permission.INTERNET, pkgname))
				&& SQLStatic.packagename_ALL.contains(pkgname)
				&& !Block.filter.contains(pkgname)) {
			final SharedPreferences prefs = mContext.getSharedPreferences(
					Block.PREFS_NAME, 0);
			final String uids_wifi = prefs.getString(Block.PREF_WIFI_UIDS, "");
			final String uids_3g = prefs.getString(Block.PREF_3G_UIDS, "");
			if (uids_3g.contains(uid + "") && uids_wifi.contains(uid + "")) {
				bt_detail.setTextColor(Color.GRAY);
			} else {
				bt_detail.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (uids_wifi.contains(uid + "")) {
						} else {
							savedUids_wifi = uids_wifi + "|" + uid;
						}
						if (uids_3g.contains(uid + "")) {
						} else {
							savedUids_3g = uids_3g + "|" + uid;
						}
						final Editor edit = prefs.edit();
						edit.putString(Block.PREF_WIFI_UIDS, savedUids_wifi);
						edit.putString(Block.PREF_3G_UIDS, savedUids_3g);
						edit.putBoolean(Block.PREF_S, true);
						edit.commit();
						if (Block.applyIptablesRules(mContext, true, true)) {
							Toast.makeText(mContext, R.string.fire_applyed,
									Toast.LENGTH_SHORT).show();
						} else {
							final Editor edit2 = prefs.edit();
							edit2.putString(Block.PREF_WIFI_UIDS, uids_wifi);
							edit2.putString(Block.PREF_3G_UIDS, uids_3g);
							edit2.putBoolean(Block.PREF_S, true);
							edit2.commit();
						}
						ll.setVisibility(View.GONE);
					}
				});
			}
		} else {
			bt_detail.setTextColor(Color.GRAY);
		}

		bt_uninstall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.fromParts("package", pkgname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE, uri);
				startActivity(intent);
				ll.setVisibility(View.GONE);
			}
		});

	}

	public void menuDialog(View arg1) {
		final PackageInfo pkgInfo = (PackageInfo) arg1.getTag(R.id.tag_pkginfo);
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkname = pkgInfo.applicationInfo.packageName;
		final String appname = pkgInfo.applicationInfo.loadLabel(
				getPackageManager()).toString();
		final LinearLayout ll = (LinearLayout) arg1
				.findViewById(R.id.detail_menu);
		if (menuList.size() == 0) {
			if (ll.isShown()) {
				ll.setVisibility(View.GONE);
				menuList.clear();
			} else {
				ll.setVisibility(View.VISIBLE);
				menuList.add(ll);
			}
		} else {
			if (ll.isShown()) {
				ll.setVisibility(View.GONE);
				menuList.clear();
			} else {
				for (int i = 0; i < menuList.size(); i++) {
					menuList.get(i).setVisibility(View.GONE);
				}
				ll.setVisibility(View.VISIBLE);
				menuList.add(ll);
			}
		}

		Button bt_manager = (Button) arg1.findViewById(R.id.bt_manage);
		Button bt_detail = (Button) arg1.findViewById(R.id.bt_detail);
		Button bt_uninstall = (Button) arg1.findViewById(R.id.bt_uninstalled);

		bt_manager.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showInstalledAppDetails(FireWallActivity.this, pkname);
				ll.setVisibility(View.GONE);
			}
		});

		bt_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				LayoutInflater infalter = LayoutInflater.from(mContext);
				final View mDetailView = infalter.inflate(R.layout.fire_detail,
						null);
				final AlertDialog detailDialog = new AlertDialog.Builder(
						FireWallActivity.this.getParent()).create();
				detailDialog.show();
				Window wd = detailDialog.getWindow();
				wd.setContentView(mDetailView, new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				final int width = wd.getWindowManager().getDefaultDisplay()
						.getWidth();
				wd.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);

				final TextView traffic_up = (TextView) mDetailView
						.findViewById(R.id.fire_traffic_up);
				final TextView traffic_down = (TextView) mDetailView
						.findViewById(R.id.fire_traffic_down);
				final Button detail_ok = (Button) mDetailView
						.findViewById(R.id.detail_ok);
				final Button detail_history = (Button) mDetailView
						.findViewById(R.id.detail_history);

				if (SQLStatic.uiddata != null) {
					if(sharedpref.getFireWallType() == 3){
						traffic_up.setText("上传： "
								+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getUploadmobile()));
						traffic_down.setText("下载： "
								+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getDownloadmobile()));
						
					}else if(sharedpref.getFireWallType() == 4){
						traffic_up.setText("上传： "
								+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getUploadwifi()));
						traffic_down.setText("下载： "
								+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getDownloadwifi()));
					}else{
					traffic_up.setText("上传： "
							+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
									.get(uid).getAllUpload()));
					traffic_down.setText("下载： "
							+ UnitHandler.unitHandlerAccurate(SQLStatic.uiddata
									.get(uid).getAllDownload()));
					}
				} else {
					traffic_up.setText("上传： " + "0 KB");
					traffic_down.setText("下载： " + "0 KB");
				}

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
				ll.setVisibility(View.GONE);
			}
		});

		bt_uninstall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.fromParts("package", pkname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE, uri);
				startActivity(intent);
				ll.setVisibility(View.GONE);
			}
		});

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
		super.onResume();
		Logs.d(TAG, "startonResume");
		if (SQLStatic.TableWiFiOrG23 != "") {
			AlarmSet alset = new AlarmSet();
			alset.StartAlarmUid(mContext);
		}
		if (sharedpref.getFireWallType() == 5) {
			if (NotificationInfo.isgettingdata == false) {
				if (NotificationInfo.notificationRes.length() == 0) {
					if (NotificationInfo.hasdata == false) {
						Logs.d(TAG, "start-AsyncTaskGetAdbArrayListonResume");
						new AsyncTaskGetAdbArrayListonResume()
								.execute(mContext);
					}
				}
			}
		}
		if (sharedpref.getFireWallType() == 0) {
			if (NotificationInfo.callbyonCancel == true) {
				Logs.d(TAG, "start-callbyonResume");
				NotificationInfo.callbyonCancel = false;
				switchList(0);
			}

		}
		// MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mPop.isShowing()) {
				mPop.dismiss();
			}
			break;
		}
		return true;
	}

	private class AsyncTaskGetAdbArrayListonResume extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationInfo.isgettingdata = true;
			appListView.setVisibility(View.INVISIBLE);
			loading_content.setVisibility(View.VISIBLE);
			NotificationInfo.startRootcomand(mContext);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			while (NotificationInfo.notificationRes.length() == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (String.valueOf(NotificationInfo.notificationRes).contains(
					"Notification")) {
				loading_content.setVisibility(View.INVISIBLE);
				NotificationInfo.hasdata = true;
				setAdapterNotif();
			} else {
				CustomDialogOtherBeen customDialog = new CustomDialogOtherBeen(
						FireWallActivity.this.getParent());
				customDialog.dialogNotificationRootFail();
			}
			NotificationInfo.isgettingdata = false;
		}
	}
}
