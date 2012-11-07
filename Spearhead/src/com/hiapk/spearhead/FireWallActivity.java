package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.comparator.ComparatorUtil;
import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.FireWallItemMenu;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.NotifListAdapter;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.firewall.Rotate3dAnimation;
import com.hiapk.logs.Logs;
import com.hiapk.logs.SaveRule;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall;
import com.hiapk.ui.custom.CustomDialog;
import com.hiapk.ui.custom.CustomDialogOtherBeen;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.Extra;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FireWallActivity extends Activity implements OnClickListener {
	private static final int SENSOR_SHAKE = 10;
	private static final int DATA_READY = 9;
	private static final int ANIMOTION_MID = 8;
	private SensorManager sensorManager;
	private String TAG = "firewallActivity";
	private List<PackageInfo> packageInfo;
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
	public ViewGroup container;
	public Dialog bubbleDialog;
	public Animation showAction;
	public View bubbleView;
	public String savedUids_wifi = "";
	public String savedUids_3g = "";
	private Context mContext = this;
	public ProgressDialog mydialog;
	public ProgressDialog pro;
	public static ArrayList<Integer> uidList = new ArrayList<Integer>();
	Handler handler;
	public static boolean isloading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2);
		init();
		if (sharedpref.IsFireWallOpenFail() && !Block.isShowHelp(mContext)) {
			dialogFireWallOpenFail();
		} else {
			initList();
		}
		handerControl();
	}



	public void init() {
		Logs.i("test", " firewall init()");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(mSensorEventListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_NORMAL);

		sharedpref = new SharedPrefrenceData(mContext);

		container = (ViewGroup)findViewById(R.id.container);
		loading_content = (LinearLayout) findViewById(R.id.loading_content);
		appListView = (MyListView) findViewById(R.id.app_list);
		firewall_details = (TextView) findViewById(R.id.firewall);
		firewall_title = (TextView) findViewById(R.id.firewall_title);
		setting_button = (Button) findViewById(R.id.setting_button);
		main2TitleBackground = (RelativeLayout) findViewById(R.id.main2TitleBackground);
		title_normal = (RelativeLayout) findViewById(R.id.title_normal);
		title_notif = (TextView) findViewById(R.id.title_notif);
		loading_content.setVisibility(View.VISIBLE);

		container.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mPop.isShowing()) {
					mPop.dismiss();
				}
				int type = 1;
				if (sharedpref.getFireWallType() == 5) {
					type = 2;
				}
				final FireWallItemMenu menu = new FireWallItemMenu(mContext,
						arg1, type);
				menu.show();
			}
		});

		// 为了退出。
		SpearheadApplication.getInstance().addActivity(this);
		firstSetTitle();
		settingShowList();
		initUidData();
	}

	
	public void handerControl() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DATA_READY:
					firstSetAdapter();
					break;
				case SENSOR_SHAKE:
					shakeAndSwitch();
					break;
				}
				if(msg.what >= 0 && msg.what <= 5){
					switchList(msg.what);
				}
			}
		};
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
		switch (v.getId()) {
		case R.id.bt_today:
			switchListAnimation(0);
			break;
		case R.id.bt_week:
			switchListAnimation(1);
			break;
		case R.id.bt_month:
			switchListAnimation(2);
			break;
		case R.id.bt_mobile:
			switchListAnimation(3);
			break;
		case R.id.bt_wifi:
			switchListAnimation(4);
			break;
		case R.id.bt_notif:
			switchListAnimation(5);
			break;
		}
	}
	
	

	private void initUidData() {
		Logs.i("test", " firewall initdata()");
		SQLStatic.uiddata = null;
		SQLHelperFireWall sql = new SQLHelperFireWall();
		sql.resetMP(mContext);
	}
	
	
	public void firstSetTitle() {
		main2TitleBackground.setBackgroundResource(SkinCustomMains
				.titleBackground());
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
			firewall_title.setText("WiFi流量排行");
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
	}


	public void firstSetAdapter() {
		Logs.i("test", " firewall firstSetAdapter()");
		try {
			if (sharedpref.getFireWallType() == 5) {
				if (NotificationInfo.notificationRes.length() == 0) {
					if (NotificationInfo.isgettingdata == false) {
						if (NotificationInfo.hasdata == false) {
							new AsyncTaskGetAdbArrayListonResume()
									.execute(mContext);
						}
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
					Toast.makeText(mContext, "下拉列表可以进行刷新!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		} catch (Exception ex) {
		}

	}
	

	
	public void switchListAnimation(int position){
		applyRotation(position,0,90);
	}

	public void settingShowList() {
		bubbleView = getLayoutInflater().inflate(R.layout.fire_setting, null);
		final Button bt_today = (Button) bubbleView.findViewById(R.id.bt_today);
		final Button bt_week = (Button) bubbleView.findViewById(R.id.bt_week);
		final Button bt_month = (Button) bubbleView.findViewById(R.id.bt_month);
		final Button bt_mobile = (Button) bubbleView
				.findViewById(R.id.bt_mobile);
		final Button bt_wifi = (Button) bubbleView.findViewById(R.id.bt_wifi);
		final Button bt_notif = (Button) bubbleView.findViewById(R.id.bt_notif);
		final Button[] button = { bt_today, bt_week, bt_month, bt_mobile,
				bt_wifi, bt_notif };
		bt_today.setOnClickListener(this);
		bt_week.setOnClickListener(this);
		bt_month.setOnClickListener(this);
		bt_mobile.setOnClickListener(this);
		bt_wifi.setOnClickListener(this);
		bt_notif.setOnClickListener(this);

		mPop = new PopupWindow(bubbleView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		setting_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mPop.isShowing()) {
					mPop.dismiss();
				} else {
					setButtonColor(button);
					mPop.showAsDropDown(v);
				}
			}
		});
	}

	public void setButtonColor(Button[] button) {
		ColorStateList whiteColor = getResources().getColorStateList(
				R.color.button_color_light);
		int i = sharedpref.getFireWallType();
		for (int j = 0; j < button.length; j++) {
			button[j].setTextColor(whiteColor);
		}
		if (isloading) {
			button[i].setTextColor(Color.BLACK);
		} else {
		}
	}

	public void shakeAndSwitch() {
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
		if (sharedpref.isShakeToSwitch()) {
			if (isloading) {
			} else {
				if (sharedpref.getFireWallType() == 5) {
					 switchListAnimation(0);
				} else {
					switchListAnimation(sharedpref.getFireWallType() + 1);
				}
			}
		}
	}

	public void switchList(int i) {
		if (isloading || sharedpref.getFireWallType() == i) {
		} else {
			isloading = true;
			sharedpref.setFireWallType(i);
			main2TitleBackground.setBackgroundResource(SkinCustomMains
					.titleBackground());
			switch (i) {
			case 0:
				loading_content.setVisibility(View.VISIBLE);
				appListView.setVisibility(View.INVISIBLE);
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
				firewall_title.setText("WiFi流量排行");
				setNewDataForList();
				break;
			case 5:
				title_normal.setVisibility(View.INVISIBLE);
				title_notif.setVisibility(View.VISIBLE);
				appListView.setVisibility(View.INVISIBLE);
				firewall_title.setText("通知栏流量排行");
				if (NotificationInfo.notificationRes.length() == 0) {
					if (NotificationInfo.isgettingdata == false) {
						new AsyncTaskGetAdbArrayListonResume()
								.execute(mContext);
					} else {
						Logs.i("test", "notificationInfo.isgettingdata is true");
					}
				} else {
					setAdapterNotif();
				}
				break;

			}
		}

	}

	public void setAdapter() {
		title_notif.setVisibility(View.INVISIBLE);
		title_normal.setVisibility(View.VISIBLE);
		appListView.setVisibility(View.VISIBLE);
		loading_content.setVisibility(View.INVISIBLE);
		firewall_details.setText(" " + Extra.getAppNum(sharedpref, uidList)
				+ " ");
		appListAdapter = new AppListAdapter(mContext, myAppList,
				Block.appnamemap, SQLStatic.uiddata, Block.appList, uidList);
		appListView.setAdapter(appListAdapter);
		isloading = false;
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
						uidList = ComparatorUtil.compUidList(mContext,
								Block.appList);
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

	public void setAdapterNotif() {
		appListView.setVisibility(View.VISIBLE);
		loading_content.setVisibility(View.INVISIBLE);
		notificationInfos = NotificationInfo
				.getNotificationApp(NotificationInfo.notificationRes);
		notificationInfos = ComparatorUtil.compNotifList(mContext,
				notificationInfos);
		final NotifListAdapter notifAdapter = new NotifListAdapter(mContext,
				notificationInfos);
		appListView.setAdapter(notifAdapter);
		isloading = false;
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
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
		Logs.i("test", " firewall initlist()");
		isloading = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				getList(mContext);
				Splash.getList(mContext);
				int i = 0;
				int j = 0;
				do {
					try {
						i++;
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (Block.appList.size() == Block.appnamemap.size()) {
						break;
					} else if (i >= 30) {
						break;
					}
				} while (Block.appList.size() != Block.appnamemap.size());
				while (SQLStatic.uiddata == null) {
					try {
						j++;
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (SQLStatic.uiddata != null) {
						break;
					}
					if (j >= 10) {
						initUidData();
						j = 0;
					}
				}
				uidList = ComparatorUtil.compUidList(mContext, Block.appList);
				Message msg = new Message();
				msg.what = DATA_READY;
				handler.sendMessage(msg);
			}
		}).start();
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
				uidList = ComparatorUtil.compUidList(mContext, Block.appList);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				loading_content.setVisibility(View.INVISIBLE);
				setAdapter();
			}
		}.execute();
	}

	public void showHelp(final Context mContext) {
		Drawable d = mContext.getResources().getDrawable(R.drawable.fire_help);
		SpearheadActivity.firehelp.setBackgroundDrawable(d);
		SpearheadActivity.firehelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
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
					myAppList.add(pkgInfo);
					Block.appList.put(uid, pkgInfo);
				}
			}
		}
		return myAppList;
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
				main2TitleBackground.setBackgroundResource(SkinCustomMains
						.titleBackground());
				loading_content.setVisibility(View.VISIBLE);
				firewall_title.setText("今日流量排行");
				setNewDataForList();
			}

		}

		// MobclickAgent.onResume(this);
	}


	@Override
	protected void onPause() {
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
		if (Block.isChange) {
			Block.isChange = false;
			SharedPrefrenceData sharedDate = new SharedPrefrenceData(mContext);
			if (sharedDate.isAutoSaveFireWallRule()) {
				SaveRule sr = new SaveRule(mContext);
				sr.copyToSD();
			}
		}
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
			setNewDataForList();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (Block.isChange) {
			SaveRule sr = new SaveRule(mContext);
			sr.copyToSD();
			Block.isChange = false;
		}
		if (sensorManager != null) {
			sensorManager.unregisterListener(mSensorEventListener);
			sensorManager = null;
		}
		super.finish();
	}

	private SensorEventListener mSensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			float[] values = event.values;
			float x = values[0];
			float y = values[1];
			float z = values[2];
			int medumValue = 12;
			if (x > medumValue || x < -medumValue || y > medumValue
					|| y < -medumValue || z > medumValue || z < -medumValue) {
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	public void dialogFireWallOpenFail() {
		final CustomDialog alertDialog = new CustomDialog.Builder(mContext)
				.setTitle(R.string.caution)
				.setMessage("防火墙应用规则失败，需要申请Root权限，请点击重试！")
				.setPositiveButton("重试", null).setNegativeButton("取消", null)
				.create();
		alertDialog.show();

		Button btn_ok = (Button) alertDialog.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isOpenSucess = false;
				isOpenSucess = Block.applyIptablesRules(mContext, true, false);
				if (!isOpenSucess) {
					Block.clearRules(mContext);
					Toast.makeText(mContext, "防火墙启动失败。", Toast.LENGTH_SHORT)
							.show();
					initList();

				} else {
					SpearheadApplication.getInstance().getsharedData()
							.setIsFireWallOpenFail(false);
					initList();
				}
				alertDialog.dismiss();

			}
		});
		Button btn_cancel = (Button) alertDialog
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Block.clearRules(mContext);
				SpearheadApplication.getInstance().getsharedData()
						.setIsFireWallOpenFail(false);
				initList();
				alertDialog.dismiss();

			}
		});
	}

	private class AsyncTaskGetAdbArrayListonResume extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationInfo.isgettingdata = true;
			loading_content.setVisibility(View.VISIBLE);
			NotificationInfo.startRootcomand(mContext);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			int timetap = 0;
			while (NotificationInfo.notificationRes.length() == 0) {
				timetap++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (timetap > 10) {
					return false;
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (String.valueOf(NotificationInfo.notificationRes).contains(
					"Notification")
					&& result) {
				loading_content.setVisibility(View.INVISIBLE);
				NotificationInfo.hasdata = true;
				setAdapterNotif();
			} else {
				isloading = false;
				NotificationInfo.notificationRes.append("result-fail");
				CustomDialogOtherBeen customDialog = new CustomDialogOtherBeen(
						FireWallActivity.this.getParent());
				customDialog.dialogNotificationRootFail();
			}
			NotificationInfo.isgettingdata = false;
		}
	}
	
	private void applyRotation(int position,float start,float end){
		final float centerX  = container.getWidth()/2.0f;
		final float centerY = container.getHeight()/2.0f;
		
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY,
				310.0f, true);
		rotation.setDuration(1000);
		rotation.setFillAfter(false);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));
		container.startAnimation(rotation);
	}
	
	private final class DisplayNextView implements Animation.AnimationListener{
		private final int position;
		private DisplayNextView(int position){
			this.position = position;
		}
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			container.post(new SwapViews(position));
		}
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private final class SwapViews implements Runnable{
		private final int position;
		private SwapViews(int position){
			this.position = position;
		}
		@Override
		public void run() {
			Message msg  = new Message();
			msg.what = position;
			handler.sendMessage(msg);
			final float centerX = container.getWidth()/2.0f;
			final float centerY = container.getHeight()/2.0f;
			Rotate3dAnimation rotation;
			rotation = new Rotate3dAnimation(270, 360, centerX, centerY, 310.0f, false);
			rotation.setDuration(500);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			container.startAnimation(rotation);
		}
	}
	

}
