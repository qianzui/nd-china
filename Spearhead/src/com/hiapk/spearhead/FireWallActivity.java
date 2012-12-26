package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.broadcreceiver.AppUninstalledReceiver;
import com.hiapk.comparator.ComparatorUtil;
import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.Rotate3dAnimation;
import com.hiapk.firewall.viewpager.FlowIndicator;
import com.hiapk.firewall.viewpager.MyPagerAdapter;
import com.hiapk.firewall.viewpager.SetListView;
import com.hiapk.firewall.viewpager.SetNotifListView;
import com.hiapk.firewall.viewpager.ViewPager;
import com.hiapk.firewall.viewpager.SetListView.OnDragRefreshListener;
import com.hiapk.firewall.viewpager.SetNotifListView.OnDragNotifRefreshListener;
import com.hiapk.firewall.viewpager.ViewPager.OnPageChangeListener;
import com.hiapk.logs.Logs;
import com.hiapk.logs.SaveRule;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall;
import com.hiapk.ui.custom.CustomDialog;
import com.hiapk.ui.custom.CustomDialogMain2Been;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.Extra;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FireWallActivity extends Activity implements OnClickListener {
	private static final int SENSOR_SHAKE = 10;
	private static final int SHAKE_AND_SWITCH = 11;
	private static final int DATA_READY = 9;
	private static final int ANIMOTION_MID = 8;
	private SensorManager sensorManager;
	private AppUninstalledReceiver uninstalledReceiver;
	private String TAG = "firewallActivity";
	protected SharedPrefrenceData sharedpref;
	public static PopupWindow mPop;
	private List<PackageInfo> packageInfo;
	public ArrayList<PackageInfo> myAppList;
	public static ArrayList<Integer> uidList = new ArrayList<Integer>();

	private Button setting_button;
	public TextView firewall_details;
	public RelativeLayout title_normal;
	public TextView title_notif;
	public TextView firewall_title;
	public RelativeLayout main2TitleBackground;
	public LinearLayout view_content;
	public Animation showAction;
	public View bubbleView;

	private Context mContext = this;
	public Handler handler;
	public ViewPager vPager;
	public static boolean isloading = false;
	public static boolean isInScene = false;
	public static boolean isRootFail = false;
	public static boolean isRefreshList = false;

	public View todayView;
	public View weekView;
	public View monthView;
	public View mobileView;
	public View wifiView;
	public View notifView;
	public SetListView today;
	public SetListView week;
	public SetListView month;
	public SetListView mobile;
	public SetListView wifi;
	public SetNotifListView notif;

	private ArrayList<SetListView> myViewControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		isInScene = true;
		setContentView(R.layout.main2);
		init();
		initVPager();
		FirstLoadData();
		handerControl();
		if (Block.fireTip(mContext)) {
			Toast.makeText(mContext, "下拉列表可以进行刷新!", Toast.LENGTH_SHORT).show();
		}
	}

	public void FirstLoadData() {
		if (sharedpref.IsFireWallOpenFail() && !Block.isShowHelp(mContext)) {
			dialogFireWallOpenFail();
		} else if (sharedpref.getFireWallType() == 0) {
			initList();
		} else {
			vPager.setCurrentItem(sharedpref.getFireWallType());
			initList();
		}
	}

	public void init() {
		sharedpref = new SharedPrefrenceData(mContext);
		view_content = (LinearLayout) findViewById(R.id.view_content);

		LayoutInflater mInflater = getLayoutInflater();
		todayView = mInflater.inflate(R.layout.firewall_list, null);
		weekView = mInflater.inflate(R.layout.firewall_list, null);
		monthView = mInflater.inflate(R.layout.firewall_list, null);
		mobileView = mInflater.inflate(R.layout.firewall_list, null);
		wifiView = mInflater.inflate(R.layout.firewall_list, null);
		notifView = mInflater.inflate(R.layout.firewall_list, null);

		firewall_details = (TextView) findViewById(R.id.firewall);
		firewall_title = (TextView) findViewById(R.id.firewall_title);
		setting_button = (Button) findViewById(R.id.setting_button);
		main2TitleBackground = (RelativeLayout) findViewById(R.id.main2TitleBackground);
		title_normal = (RelativeLayout) findViewById(R.id.title_normal);
		title_notif = (TextView) findViewById(R.id.title_notif);

		// 为了退出。
		SpearheadApplication.getInstance().addActivity(this);
		setTitle();
		settingShowList();
		initUidData();
	}

	public void initVPager() {
		vPager = (ViewPager) findViewById(R.id.vPager);
		ArrayList<View> pageList = new ArrayList<View>();
		myViewControl = new ArrayList<SetListView>();

		week = new SetListView(weekView, mContext);
		today = new SetListView(todayView, mContext);
		month = new SetListView(monthView, mContext);
		mobile = new SetListView(mobileView, mContext);
		wifi = new SetListView(wifiView, mContext);
		notif = new SetNotifListView(notifView, mContext);

		myViewControl.add(today);
		myViewControl.add(week);
		myViewControl.add(month);
		myViewControl.add(mobile);
		myViewControl.add(wifi);

		pageList.add(todayView);
		pageList.add(weekView);
		pageList.add(monthView);
		pageList.add(mobileView);
		pageList.add(wifiView);
		pageList.add(notifView);

		week.setOnDragRefreshListener(onDragRefreshListener);
		today.setOnDragRefreshListener(onDragRefreshListener);
		mobile.setOnDragRefreshListener(onDragRefreshListener);
		month.setOnDragRefreshListener(onDragRefreshListener);
		wifi.setOnDragRefreshListener(onDragRefreshListener);
		notif.setOnDragNotifRefreshListener(onDragNotifRefreshListener);
		FlowIndicator cursor = (FlowIndicator) findViewById(R.id.cursor);
		cursor.setSize(pageList.size());
		vPager.setChildrenDrawingCacheEnabled(false);
		vPager.setAdapter(new MyPagerAdapter(pageList));
		vPager.setFlowIndicator(cursor);
		vPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public void handerControl() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DATA_READY:
					setDataForList();
					break;
				case SENSOR_SHAKE:
					if (isInScene) {
						if (!isRootFail) {
							shakeAndSwitch();
						}
					}
					break;
				case SHAKE_AND_SWITCH:
					rotateAndSwitch();
					break;
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		if(mPop.isShowing()){
			mPop.dismiss();
		}
		switch (v.getId()) {
		case R.id.bt_today:
			vPager.setCurrentItem(0);
			break;
		case R.id.bt_week:
			vPager.setCurrentItem(1);
			break;
		case R.id.bt_month:
			vPager.setCurrentItem(2);
			break;
		case R.id.bt_mobile:
			vPager.setCurrentItem(3);
			break;
		case R.id.bt_wifi:
			vPager.setCurrentItem(4);
			break;
		case R.id.bt_notif:
			vPager.setCurrentItem(5);
			break;
		}
	}

	public void rotateAndSwitch() {
		int i = sharedpref.getFireWallType();
		if (i == 5) {
			vPager.setCurrentItem(0);
		} else {
			vPager.setCurrentItem(i + 1);
		}
	}

	public void setDataForList() {
		isloading = false;
		if (Block.isShowNewHelp(mContext)) {
			showHelp(mContext);
			SpearheadActivity.isHide = true;
		}
		int i = sharedpref.getFireWallType();
		if (i == 5) {
		} else {
			myViewControl.get(i).setAdapter(myAppList);
			myViewControl.get(i).compeletRefresh();
		}
	}

	private void initUidData() {
		SQLStatic.uiddata = null;
		SQLHelperFireWall sql = new SQLHelperFireWall();
		sql.resetMP(mContext);
	}

	public void setTitle() {
		main2TitleBackground.setBackgroundResource(SkinCustomMains
				.titleBackground());
		firewall_details.setText(" " + Extra.getAppNum(sharedpref, uidList)
				+ " ");
		switch (sharedpref.getFireWallType()) {
		case 0:
			title_normal.setVisibility(View.VISIBLE);
			title_notif.setVisibility(View.INVISIBLE);
			firewall_title.setText("今日流量排行");
			break;
		case 1:
			title_normal.setVisibility(View.VISIBLE);
			title_notif.setVisibility(View.INVISIBLE);
			firewall_title.setText("本周流量排行");
			break;
		case 2:
			title_normal.setVisibility(View.VISIBLE);
			title_notif.setVisibility(View.INVISIBLE);
			firewall_title.setText("本月流量排行");
			break;
		case 3:
			title_normal.setVisibility(View.VISIBLE);
			title_notif.setVisibility(View.INVISIBLE);
			firewall_title.setText("移动流量排行");
			break;
		case 4:
			title_normal.setVisibility(View.VISIBLE);
			title_notif.setVisibility(View.INVISIBLE);
			firewall_title.setText("WiFi流量排行");
			break;
		case 5:
			title_normal.setVisibility(View.INVISIBLE);
			title_notif.setVisibility(View.VISIBLE);
			firewall_title.setText("通知栏流量排行");
			break;
		}
	}

	public OnDragRefreshListener onDragRefreshListener = new OnDragRefreshListener() {
		@Override
		public void onDragRefresh() {
			if (SQLStatic.TableWiFiOrG23 != "") {
				AlarmSet alset = new AlarmSet();
				alset.StartAlarmUid(mContext);
			}
			myViewControl.get(sharedpref.getFireWallType()).resetAdaper();
			initList();
		}
	};
	public OnDragNotifRefreshListener onDragNotifRefreshListener = new OnDragNotifRefreshListener() {
		@Override
		public void onDragRefresh() {
			new AsyncTaskGetAdbArrayListonResume().execute(mContext);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Logs.i("test", "onPageSelected:" + arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (mPop.isShowing()) {
				mPop.dismiss();
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
				int currentItem =  vPager.getCurrentItem();
				int fireType  = sharedpref.getFireWallType();
				if(currentItem != fireType){
					sharedpref.setFireWallType(currentItem);
					setTitle();
					if (currentItem == 5) {
						notif.setLoading();
						new AsyncTaskGetAdbArrayListonResume().execute();
					}else{
						if(!myViewControl.get(currentItem).isLoadinged)
						initList();
					}
				}
			}
			Logs.i("test", "onPageScrollStateChanged:" + arg0);
		}
	}

	public void switchListAnimation(int position) {
		applyRotation(position, 0, 90);
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
		int i = sharedpref.getFireWallType();
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
		if (i == 5) {
			notif.menuDismiss();
		} else {
			myViewControl.get(i).menuDismiss();
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

	public void initList() {
		Logs.i("test", " data init");
		isRefreshList = false;
		isloading = true;
		initUidData();
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
				Set<Integer> key = SQLStatic.uiddata.keySet();
				for (Iterator it = key.iterator(); it.hasNext();) {
					int x = (Integer) it.next();
					if (SQLStatic.uiddata.get(x).getTotalTraffWeek() != 0) {
						Logs.i("test", "---getWeek:"
								+ SQLStatic.uiddata.get(x).getTotalTraffWeek()
								+ "---" + SQLStatic.uiddata.size());
					}
				}
				for (Iterator it = key.iterator(); it.hasNext();) {
					int x = (Integer) it.next();
					if (SQLStatic.uiddata.get(x).getTotalTraffToday() != 0) {
						Logs.i("test", "---getToday:"
								+ SQLStatic.uiddata.get(x).getTotalTraffToday()
								+ "---" + SQLStatic.uiddata.size());
					}
				}
				uidList = ComparatorUtil.compUidList(mContext, Block.appList);
				Message msg = new Message();
				msg.what = DATA_READY;
				handler.sendMessage(msg);
			}
		}).start();
	}
 
	public void showHelp(final Context mContext) {
		SpearheadActivity.firehelp.setVisibility(View.VISIBLE);
		Drawable d = mContext.getResources().getDrawable(R.drawable.fire_help);
		SpearheadActivity.firehelp.setBackgroundDrawable(d);
		SpearheadActivity.firehelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SpearheadActivity.firehelp.setVisibility(View.INVISIBLE);
				Block.isShowNewHelpSet(mContext, false);
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
		isInScene = true;
		Logs.d("test", "onResume");
		if (SQLStatic.TableWiFiOrG23 != "") {
			AlarmSet alset = new AlarmSet();
			alset.StartAlarmUid(mContext);
		}
		if (sharedpref.getFireWallType() == 5) {
			if (NotificationInfo.isgettingdata == false) {
				if (NotificationInfo.notificationRes.length() == 0) {
					if (NotificationInfo.hasdata == false) {
						Logs.d(TAG, "start-AsyncTaskGetAdbArrayListonResume");
						notif.setLoading();
						new AsyncTaskGetAdbArrayListonResume()
								.execute(mContext);
					}
				}
			}
		}
		if (sharedpref.getFireWallType() == 0) {
			if (NotificationInfo.callbyonCancel == true) {
				NotificationInfo.callbyonCancel = false;
				main2TitleBackground.setBackgroundResource(SkinCustomMains
						.titleBackground());
				firewall_title.setText("今日流量排行");
				vPager.setCurrentItem(sharedpref.getFireWallType());
			}
		}
		if (isRefreshList) {
			myViewControl.get(sharedpref.getFireWallType()).resetAdaper();
			initList();
		}
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		Logs.i("test", "onPause");
		isInScene = false;
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
	public void finish() {
		if (Block.isChange) {
			SaveRule sr = new SaveRule(mContext);
			sr.copyToSD();
			Block.isChange = false;
		}
		if (sensorManager != null) {
			sensorManager.unregisterListener(mSensorEventListener);
			sensorManager = null;
		}
		if (uninstalledReceiver != null) {
			this.unregisterReceiver(uninstalledReceiver);
		}
		super.finish();
	}

	private SensorEventListener mSensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			float medumValue = sharedpref.getMedianValues();
			float[] values = event.values;
			float x = values[0];
			float y = values[1];
			float z = values[2];
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

	private void applyRotation(int position, float start, float end) {
		final float centerX = view_content.getWidth() / 2.0f;
		final float centerY = view_content.getHeight() / 2.0f;
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 310.0f, true);
		rotation.setDuration(1000);
		rotation.setFillAfter(false);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));
		view_content.startAnimation(rotation);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(mSensorEventListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		uninstalledReceiver = new AppUninstalledReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.PACKAGE_REMOVED");
		filter.addAction("android.intent.action.PACKAGE_ADDED");
		filter.addDataScheme("package");
		this.registerReceiver(uninstalledReceiver, filter);
		super.onStart();

	}

	private final class DisplayNextView implements Animation.AnimationListener {
		private final int position;

		private DisplayNextView(int position) {
			this.position = position;
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view_content.post(new SwapViews(position));
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	private final class SwapViews implements Runnable {
		private final int position;

		private SwapViews(int position) {
			this.position = position;
		}

		@Override
		public void run() {
			final float centerX = view_content.getWidth() / 2.0f;
			final float centerY = view_content.getHeight() / 2.0f;
			Rotate3dAnimation rotation;
			rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
					310.0f, false);
			rotation.setDuration(500);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			view_content.startAnimation(rotation);
			Message msg = new Message();
			msg.what = SHAKE_AND_SWITCH;
			handler.sendMessage(msg);
		}
	}

	private class AsyncTaskGetAdbArrayListonResume extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationInfo.isgettingdata = true;
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
					timetap = 0;
					return false;
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Logs.i("test", "AsyncTaskGetAdbArrayListonResume");
			if (String.valueOf(NotificationInfo.notificationRes).contains(
					"Notification")
					&& result) {
				NotificationInfo.hasdata = true;
				isloading = false;
				notif.setAdapter();
				notif.completeRefresh();
			} else {
				if (sharedpref.getFireWallType() == 5) {
					notif.completeRefresh();
					NotificationInfo.notificationRes.append("result-fail");
					CustomDialogMain2Been customDialog = new CustomDialogMain2Been(
							mContext);
					customDialog.dialogNotificationRootFail();
				}

			}
			NotificationInfo.isgettingdata = false;
		}
	}

}
