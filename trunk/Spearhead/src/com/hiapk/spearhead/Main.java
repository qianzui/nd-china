package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.GraphicalView;

import com.hiapk.alertdialog.CustomDialogMainBeen;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.customspinner.CustomToast;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.GetRoot;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.PieView;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.progressbar.StackedBarChart;
import com.hiapk.provider.ColorChangeMainBeen;
import com.hiapk.provider.UiColors;
import com.hiapk.sqlhelper.SQLHelperFireWall.Data;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.sqlhelper.SQLStatic;
import com.hiapk.widget.ProgramNotify;
import com.hiapk.widget.SetText;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.UmengOnlineConfigureListener;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.inputmethodservice.Keyboard.Key;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {

	private Context context = this;
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi与mobile单月使用量
	public static long mobile_month_use = 0;

	// 临时存放两个数据------------

	// ------------
	// 显示何种图形
	boolean ismobileshowpie = false;
	// 获取的系统时间
	private int year;
	private int month;
	private int monthDay;
	// wifi月度流量
	long[] wifiTraffic = new long[64];
	/**
	 * 完整月份的移动数据流量
	 */
	long[] mobileTraffic = new long[64];
	/**
	 * 部分月份的移动数据流量
	 */
	long[] mobileTrafficPart = new long[64];
	// 屏幕宽度
	int windowswidesize;
	// 系统设置
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	SharedPrefrenceData sharedData;
	TrafficManager trafficManager = new TrafficManager();
	// fortest
	long time;

	// 柱状图标识0为总流量1为mobile，2为wifi
	// int stackflag = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// umeng
		// .. MobclickAgent.onError(this);
		// 获取固定存放数据
		sharedData = new SharedPrefrenceData(context);

		if (sharedData.isSQLinited() == false) {
			if (SQLStatic.uids == null) {
				getuids();
			}
		}
		// ------------
		AlarmSet alset = new AlarmSet();
		SetText.setText(context);
		if (sharedData.isNotifyOpen()) {
			alset.StartWidgetAlarm(context);
		}
		if (sharedData.isSQLinited() == false) {
			initSQLdatabase(SQLStatic.uids, SQLStatic.packagenames);
		}

		setonclicklistens();
		// setontvclicklisten();
	}

	/**
	 * 初始化界面
	 */
	private void initScene() {
		// TODO Auto-generated method stub
		// 设置按钮显示文字
		boolean hasTraffSet = sharedData.isMonthSetHasSet();
		Button btn_toThree = (Button) findViewById(R.id.setTaoCan);
		if (hasTraffSet) {
			btn_toThree.setText("  校准流量  ");
		} else {
			btn_toThree.setText("  请设置流量套餐  ");
		}

	}

	/**
	 * 初始化显示数值
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		// 初始化小部件
		// 今日已用
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		// TextView leftMobil = (TextView) findViewById(R.id.weekRate);
		// TextView leftMobilunit = (TextView) findViewById(R.id.unit2);
		// 本月已用
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		// TextView monthMobil2 = (TextView)
		// findViewById(R.id.traffic_month_set);
		// TextView monthMobilunit2 = (TextView)
		// findViewById(R.id.unit_month_set);
		// 本月剩余
		TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
		TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
		// 包月流量
		TextView monthSet = (TextView) findViewById(R.id.monthSet);
		TextView monthSetunit = (TextView) findViewById(R.id.unit5);
		// TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		// TextView monthWifiunit = (TextView) findViewById(R.id.unit6);
		// 流量获取函数
		wifiTraffic = new long[64];
		// 初始化流量获取函数
		// 取得月度流量
		mobileTraffic = TrafficManager.mobile_month_data;
		wifiTraffic = TrafficManager.wifi_month_data;
		// 进行流量设置
		todayMobil.setText(UnitHandler.unitHandlerAcurrac(
				mobileTraffic[monthDay] + mobileTraffic[monthDay + 31],
				todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		// 月度流量设置
		mobile_month_use = TrafficManager.getMonthUseData(context);
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		long monthLeft = 0;
		monthLeft = ColorChangeMainBeen.setRemainTraff(mobileSet,
				mobile_month_use, monthMobil);
		//
		monthMobil.setText(UnitHandler.unitHandlerAcurrac(mobile_month_use,
				monthMobilunit));
		monthRemain
				.setText(UnitHandler.unitHandler(monthLeft, monthRemainunit));
		if (mobileSet != 0) {
			monthSet.setText(UnitHandler.unitHandler(mobileSet, monthSetunit));
		} else {
			monthSet.setText("未设置");
			monthSet.setTextColor(UiColors.colorRed);
			monthSetunit.setText("");
		}

		// monthMobil2.setText("/" + unitHandler(mobileSet, monthMobilunit2));
		// leftMobil.setText(unitHandler(mobileSet - mobile_month_use,
		// leftMobilunit));
		// todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31],
		// todayWifiunit));
		// weekWifi.setText(unitHandler(weektraffic[5], weekWifiunit));
		// wifi_month_use = wifi[0] + wifi[63];
		// monthWifi.setText(unitHandler(wifi_month_use, monthWifiunit));

	}

	// ----------

	// ----------
	/**
	 * 临时函数
	 */
	private void getuids() {
		int j = 0;
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		int[] uidstp = new int[packages.size()];
		String[] packagenamestp = new String[packages.size()];
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = Block.filter;
			String pacname = packageinfo.packageName;
			int uid = packageinfo.applicationInfo.uid;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					uidstp[j] = uid;
					packagenamestp[j] = pacname;
					// showLog("进行显示的uid=" + uid);
					j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		SQLStatic.uids = new int[j];
		SQLStatic.packagenames = new String[j];
		for (int i = 0; i < j; i++) {
			SQLStatic.uids[i] = uidstp[i];
			SQLStatic.packagenames[i] = packagenamestp[i];
		}
		// SQLHelperUid sqlhelpuid = new SQLHelperUid();
		// uids = sqlhelpuid.selectUidnumbers(context);
		// packagenames = sqlhelpuid.selectPackagenames(context);
	}

	/**
	 * 初始化数据库
	 * 
	 * @param uids
	 *            uid数组
	 * @param packagename
	 *            uid对应的包名组
	 */
	private void initSQLdatabase(int[] uids, String[] packagename) {
		// TODO Auto-generated method stub
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		if (!sqlhelperTotal.getIsInit(context)) {
			sqlhelperTotal.initSQL(context, uids, packagename);
		}
	}

	// ------------
	/**
	 * 执行动态进度条设置
	 * 
	 * @param i
	 *            移动数据
	 * @param j
	 *            wifi
	 */
	private void RefreshProgressBar(int i, int j) {
		// ProgressBar myProgressBar = (ProgressBar)
		// findViewById(R.id.progressbar);
		// MyProgressBar myProgressBar_mobile = (MyProgressBar)
		// findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		// DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		// windowswidesize = dm.widthPixels / 10;
		// windowswidesize = dm.densityDpi / 5;
		// showlog(screenWidth+"");
		// myProgressBar_mobile.setTextsize(windowswidesize);
		// // myProgressBar_wifi.setTextsize(fontsize);
		// ProgressBarForV progforv_mobile = new ProgressBarForV();
		// progforv_mobile.j = i;
		// progforv_mobile.execute(myProgressBar_mobile);
		// ProgressBarForV progforv_wifi = new ProgressBarForV();
		// progforv_wifi.j = j;
		// progforv_wifi.execute(myProgressBar_wifi);
	}

	/**
	 * 进度条设置
	 * 
	 * @param i
	 *            移动数据
	 * @param j
	 *            wifi
	 */
	private void ProgressBarSet(int i, int j) {
		// MyProgressBar myProgressBar_mobile = (MyProgressBar)
		// findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		// myProgressBar_mobile.setProgress(i);
		// myProgressBar_wifi.setProgress(j);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initScene();
		// umeng
		// MobclickAgent.onResume(this);
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		AlarmSet alset = new AlarmSet();
		alset.StartAlarm(context);
//		initWifiBar();
		// if (TrafficManager.mobile_month_data[0] == 0
		// && TrafficManager.wifi_month_data[0] == 0
		// && TrafficManager.mobile_month_data[63] == 0
		// && TrafficManager.wifi_month_data[63] == 0) {
		//
		// new AsyncTaskonRefreshMain().execute(context);
		// } else {
		initValues();
		initProgressBar();
		initPieBar();
		initWifiBar();
		// }

		// 数据记录功能，放在flesh上面
		// AlarmSet alset = new AlarmSet();
		// // 初始化网络状态
		// sqlhelperTotal.initTablemobileAndwifi(context);
		// if (SQLHelperTotal.TableWiFiOrG23 != ""
		// && sqlhelperTotal.getIsInit(context)) {
		// // 启动闹钟
		// alset.StartAlarmMobile(context);
		// // 进行数据记录
		// trafficManager.statsTotalTraffic(context, false);
		// } else if (SQLHelperTotal.TableWiFiOrG23 != "") {
		// alset.StartAlarmMobile(context);
		// sqlhelperTotal.initTablemobileAndwifi(context);
		// }
		// time=System.currentTimeMillis();

		// time = System.currentTimeMillis() - time;
		// showlog("更新main" + time);
	}

	private class AsyncTaskonRefreshMain extends AsyncTask<Context, Long, Long> {
		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_data[0] == 0
					&& TrafficManager.wifi_month_data[0] == 0
					&& TrafficManager.mobile_month_data[63] == 0
					&& TrafficManager.wifi_month_data[63] == 0) {
				try {
					Thread.sleep(20);
					timetap += 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 50)
					break;

			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			SetText.resetWidgetAndNotify(context);
			initValues();
			initProgressBar();
			initPieBar();
			initWifiBar();
		}
	}

	// private void setontvclicklisten() {
	// final TextView tvtraff = (TextView) findViewById(R.id.tv_stackChart);
	// tvtraff.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// if (stackflag < 2) {
	// stackflag++;
	// } else
	// stackflag = 0;
	// initWifiBar();
	// }
	// });
	// }

	private void setonclicklistens() {
		final Button btn_refresh = (Button) findViewById(R.id.refresh);
		btn_refresh.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					btn_refresh
							.setBackgroundResource(R.drawable.arrow_refresh_icon_on);
					return true;
				}
				if (event.getAction() == KeyEvent.ACTION_UP) {
					btn_refresh
							.setBackgroundResource(R.drawable.arrow_refresh_icon_off);
					// showlog("Product Model: " + android.os.Build.MODEL + ","
					// + android.os.Build.VERSION.SDK + ","
					// + android.os.Build.VERSION.RELEASE);
					// 记录点击刷新次数
					// MobclickAgent.onEvent(context, "refresh");
					AlarmSet alset = new AlarmSet();
					// 初始化网络状态
					sqlhelperTotal.initTablemobileAndwifi(context, false);
					if (SQLHelperTotal.TableWiFiOrG23 != ""
							&& sqlhelperTotal.getIsInit(context)) {
						// 启动闹钟
						alset.StartAlarmMobile(context);
						// 进行数据记录
						// trafficManager.statsTotalTraffic(context, false);
						// sqlhelperTotal.RecordTotalwritestats(context, false);
					} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
						alset.StartAlarmMobile(context);
						sqlhelperTotal.initTablemobileAndwifi(context, false);
					}
					SetText.resetWidgetAndNotify(context);
					initValues();
					initProgressBar();
					initPieBar();
					initWifiBar();

					// test
					// SQLHelperUid sqlhelperUid = new SQLHelperUid();
					// if (SQLStatic.uidnumbers == null) {
					// SQLStatic.uidnumbers = sqlhelperUid
					// .selectUidnumbers(context);
					// }
					// if (SQLStatic.uiddata == null) {
					// alset.StartAlarmUidTotal(context);
					//
					// }
					// showlog(SQLStatic.uidnumbers.length + "");
					// showlog(SQLStatic.uiddata.size() + "data");
					//
					// for (int i = 0; i < SQLStatic.uidnumbers.length; i++) {
					// int uid = SQLStatic.uidnumbers[i];
					// Data datt = SQLStatic.uiddata.get(uid);
					// showlog("" + SQLStatic.uiddata.get(uid).download
					// + datt.download);
					// }
					// CustomToast.initToast(context, "test");
					return true;
				}
				return false;
			}
		});

		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// specialfortext----test
				// SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
				// sqlUidTotal.updateSQLUidTypes(context, uids);
			}
		});

		// 跳转到校正页
		Button gotoThree = (Button) findViewById(R.id.setTaoCan);
		gotoThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// gotoThree();
				CustomDialogMainBeen customDialog = new CustomDialogMainBeen(
						context);
				boolean hasTraffSet = sharedData.isMonthSetHasSet();
				if (!hasTraffSet) {
					Button btn_toThree = (Button) findViewById(R.id.setTaoCan);
					// 包月流量
					TextView monthSet = (TextView) findViewById(R.id.monthSet);
					TextView monthSetunit = (TextView) findViewById(R.id.unit5);
					TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
					TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
					customDialog.dialogMonthSet_Main(btn_toThree, monthSet,
							monthSetunit, monthRemain, monthRemainunit);
				} else {
					TextView monthMobil = (TextView) findViewById(R.id.monthRate);
					TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
					TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
					TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
					customDialog.dialogMonthHasUsed(monthMobil, monthMobilunit,
							monthRemain, monthRemainunit);
				}

			}
		});
	}

	/**
	 * 初始化wifi部分的柱状图
	 */
	private void initWifiBar() {
		// TODO Auto-generated method stub

		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_wifi);
		StackedBarChart chartbar = initStackedBarChart(context);
		View view = chartbar.execute(context);
		layout_mobile.removeAllViews();
		layout_mobile.addView(view);
	}

	/**
	 * 设置wifi部分的柱状图
	 * 
	 * @param context
	 * @return 返回显示的柱状图
	 */
	private StackedBarChart initStackedBarChart(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// chartbar.setXaxisText(year + "年");
		chartbar.setXaxisText("");
		// 进行参数设置
		// 设置x轴显示范围
		int monthtotalDay = countDay(year, month);
		chartbar.setMonthDay(monthtotalDay);
		// 设置y轴显示值及范围
		double[] totalTraff = new double[monthDay];
		long maxTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		// TextView tvtraff = (TextView) findViewById(R.id.tv_stackChart);
		// switch (stackflag) {
		// case 0:
		for (int i = 0; i < totalTraff.length; i++) {
			long temp = TrafficManager.wifi_month_data[i + 1]
					+ TrafficManager.wifi_month_data[i + 32]
			// + TrafficManager.mobile_month_data[i + 1]
			// + TrafficManager.mobile_month_data[i + 32]
			;
			// 小数点2位
			totalTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			// format.format(wifi[i]);
			if (temp > maxTraffic) {
				maxTraffic = temp;
			}
		}
		chartbar.setMainTitle("流量统计(总)");
		chartbar.setTopTitle("移动网络");
		// tvtraff.setText("   总流量");
		double[] mobileTraff = new double[monthDay];
		// break;
		// case 1:
		for (int i = 0; i < mobileTraff.length; i++) {
			long temp = TrafficManager.mobile_month_data[i + 1]
					+ TrafficManager.mobile_month_data[i + 32];
			// 小数点2位
			mobileTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			if (temp > maxTraffic) {
				maxTraffic = temp;
			}
			// format.format(wifi[i]);
		}
		// chartbar.setMainTitle("移动流量统计");
		// chartbar.setTopTitle("移动流量");
		// tvtraff.setText("   移动流量");
		// break;
		// case 2:
		// for (int i = 0; i < totalTraff.length; i++) {
		// long temp = TrafficManager.wifi_month_data[i + 1]
		// + TrafficManager.wifi_month_data[i + 32];
		// // 小数点2位
		// totalTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
		// // format.format(wifi[i]);
		// if (temp > maxwifiTraffic) {
		// maxwifiTraffic = temp;
		// }
		// }
		// chartbar.setMainTitle("WIFI流量统计");
		// chartbar.setTopTitle("WIFI流量");
		// tvtraff.setText("   WIFI流量");
		// break;
		// default:
		// for (int i = 0; i < totalTraff.length; i++) {
		// long temp = TrafficManager.wifi_month_data[i + 1]
		// + TrafficManager.wifi_month_data[i + 32]
		// + TrafficManager.mobile_month_data[i + 1]
		// + TrafficManager.mobile_month_data[i + 32];
		// // 小数点2位
		// totalTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
		// // format.format(wifi[i]);
		// if (temp > maxwifiTraffic) {
		// maxwifiTraffic = temp;
		// }
		// }
		// chartbar.setMainTitle("总流量统计");
		// chartbar.setTopTitle("总流量");
		// break;
		// }
		chartbar.setData1(mobileTraff, totalTraff);
		if (maxTraffic < 848576) {
			chartbar.setyMaxvalue(1);
			chartbar.setMaxTraffic(1);
		} else {
			chartbar.setMaxTraffic((double) (long) maxTraffic / 1048576 * 1.2);
			chartbar.setyMaxvalue((double) (long) maxTraffic / 1048576 * 1.2);
		}

		// 设置背景色（被隐藏的条）
		// chartbar.setBackgroundColor(Color.BLACK);
		// 设置初始显示图像位置
		if ((monthDay + 2) > monthtotalDay) {
			chartbar.setxMinvalue(monthtotalDay - 6.5);
			chartbar.setxMaxvalue(monthtotalDay + 0.5);
		} else if ((monthDay - 5) < 0) {
			chartbar.setxMinvalue(0.5);
			chartbar.setxMaxvalue(7.5);
		} else {
			chartbar.setxMinvalue(monthDay - 4.5);
			chartbar.setxMaxvalue(monthDay + 2.5);
		}
		// 设置显示的日期
		String[] xaxles = new String[monthtotalDay];
		for (int i = 0; i < monthtotalDay; i++) {
			int j = i + 1;
			xaxles[i] = month + "月" + j + "日";
		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	private void initPieBar() {
		// TODO Auto-generated method stub
		// 获取默认流量数值
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		// long moblileTotle = mobileTraffic[0] + mobileTraffic[63];
		int usePercent = 0;
		if (mobile_month_use == 0) {
			usePercent = 0;
		} else if (mobileSet == 0) {
			usePercent = 360;
		} else {
			usePercent = (int) (((double) mobile_month_use / mobileSet) * 360);
		}
		if (usePercent > 360)
			usePercent = 360;

		int mobilePersent = 0;
		// 月度流量为0判断
		if (mobile_month_use == 0) {
			mobilePersent = 0;
		} else if (mobileSet == 0) {
			mobilePersent = 100;
		} else {
			// 进行超百判断
			if (mobile_month_use > mobileSet)
				mobilePersent = 100;
			else
				mobilePersent = (int) ((double) mobile_month_use * 100 / mobileSet);
		}
		int[] percent = new int[] { usePercent, 360 - usePercent };
		final PieView pieView_mobile = new PieView(context, percent,
				mobilePersent);
		// View PieView=findViewById(R.id.pie_bar_mobile);
		// LinearLayout layout_mobile = (LinearLayout)
		// findViewById(R.id.linearlayout_bar_mobile);
		// final LinearLayout laout_mobile_pie = (LinearLayout)
		// findViewById(R.id.linearlayout_piebar_mobile);
		// laout_mobile_pie.removeAllViews();
		//
		// laout_mobile_pie.setBackgroundColor(Color.WHITE);
		//
		ismobileshowpie = false;
		// layout_mobile.setOnClickListener(new OnClickListener() {

		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// laout_mobile_pie.removeAllViews();
		// if (ismobileshowpie) {
		// ismobileshowpie = false;
		// } else {
		// laout_mobile_pie.addView(pieView_mobile);
		// ismobileshowpie = true;
		// }
		// }
		// });
		// laout_mobile_pie.removeAllViews();
		// laout_mobile_pie.addView(pieView_mobile);
		// laout_mobile.removeAllViews();
	}

	/**
	 * 初始化进度条的现实数值
	 */
	private void initProgressBar() {
		// 获取设置的月度使用值，默认50m
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		int mobile = 0;
		int wifi = 0;
		// showlog("mobile" + mobile_month_use + "wifi" + wifi_month_use);
		// showlog("mobile" + mobileSet + "wifi" + wifiSet);
		// 月度流量为0判断
		if (mobile_month_use == 0) {
			mobile = 0;
		} else if (mobileSet == 0) {
			mobile = 100;
		} else {

			// 进行超百判断
			if (mobile_month_use > mobileSet)
				mobile = 100;
			else
				// mobile = (int) ((float) ((int) ((float) mobile_month_use /
				// mobileSet * 360)) / 360 * 100);
				mobile = (int) ((float) mobile_month_use * 100 / mobileSet);
			// if (wifi_month_use > wifiSet)
			// wifi = 100;
			// else
			// wifi = (int) ((float)wifi_month_use * 100 / wifiSet);
		}

		// showlog("mobile" + mobile + "wifi" + wifi);
		RefreshProgressBar(mobile, wifi);
	}

	/**
	 * 计算单月有几天
	 * 
	 * @param year
	 *            输入年份
	 * @param month
	 *            输入月份
	 * @return 返回天数
	 */
	private int countDay(int year, int month) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))
				&& month == 2) {
			return 29;
		} else {
			switch (month) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			}
		}
		return 31;
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		// Log.d("main", string);
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
