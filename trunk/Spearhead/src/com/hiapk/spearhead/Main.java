package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.PieView;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.progressbar.StackedBarChart;
import com.hiapk.sqlhelper.SQLHelperTotal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {
	private Context context = this;
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi与mobile单月使用量
	private long wifi_month_use = 0;
	private long mobile_month_use = 0;

	// 临时存放两个数据------------
	private int[] uids;
	private String[] packagenames;
	// ------------
	// 显示何种图形
	boolean ismobileshowpie = false;
	// 获取的系统时间
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	// wifi月度流量
	long[] wifiTraffic = new long[64];
	long[] mobileTraffic = new long[64];
	// 屏幕宽度
	int windowswidesize;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// temp------------
		getuids();
		// ------------
		initSQLdatabase(uids, packagenames);
	}

	/**
	 * 初始化显示数值
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		// 初始化小部件
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		TextView weekMobil = (TextView) findViewById(R.id.weekRate);
		TextView weekMobilunit = (TextView) findViewById(R.id.unit2);
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		// TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		// TextView todayWifiunit = (TextView) findViewById(R.id.unit4);
		// TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		// TextView weekWifiunit = (TextView) findViewById(R.id.unit5);
		// TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		// TextView monthWifiunit = (TextView) findViewById(R.id.unit6);
		//跳转到校正页
		Button gotoThree = (Button)findViewById(R.id.gotoThree);
		gotoThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoThree();

			}
		});
		
		
		wifiTraffic = new long[64];
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
		// 取得每周的流量
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		// 取得月度流量
		mobileTraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		wifiTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		// 进行流量设置
		todayMobil.setText(unitHandler(mobileTraffic[monthDay]
				+ mobileTraffic[monthDay + 31], todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		weekMobil.setText(unitHandler(weektraffic[0], weekMobilunit));
		mobile_month_use = mobileTraffic[0] + mobileTraffic[63];
		monthMobil.setText(unitHandler(mobile_month_use, monthMobilunit));
		// todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31],
		// todayWifiunit));
		// weekWifi.setText(unitHandler(weektraffic[5], weekWifiunit));
		// wifi_month_use = wifi[0] + wifi[63];
		// monthWifi.setText(unitHandler(wifi_month_use, monthWifiunit));

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
		if (!sqlhelperTotal.getIsInit(context)) {
			sqlhelperTotal.initSQL(context, uids, packagename);
		}
	}
	// ----------
	
	public  void gotoThree(){		
		SpearheadActivity sp = new SpearheadActivity();
		
		sp.tabThree();
	}
	

	// ----------
	/**
	 * 临时函数
	 */
	private void getuids() {
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		uids = new int[packages.size()];
		packagenames = new String[packages.size()];
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			packagenames[i] = packageinfo.packageName;
			// Log.d("pac", packagenames[i]);
			uids[i] = packageinfo.applicationInfo.uid;
			// Log.d("uid", uids[i] + "");
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
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		windowswidesize = dm.widthPixels / 10;
		// showlog(screenWidth+"");
		myProgressBar_mobile.setTextsize(windowswidesize);
		// myProgressBar_wifi.setTextsize(fontsize);
		ProgressBarForV progforv_mobile = new ProgressBarForV();
		progforv_mobile.j = i;
		progforv_mobile.execute(myProgressBar_mobile);
//		ProgressBarForV progforv_wifi = new ProgressBarForV();
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
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		myProgressBar_mobile.setProgress(i);
		// myProgressBar_wifi.setProgress(j);
	}

	/**
	 * 单位标准化，mb，gb等
	 * 
	 * @param count
	 *            输入的long型数
	 * @param unit
	 *            数值后面要显示的textview
	 * @return 返回String型值
	 */
	private String unitHandler(long count, TextView unit) {
		String value = null;
		long temp = count;
		float floatnum = count;
		float floatGB = count;
		if ((temp = temp / 1000) < 1) {
			value = count + "";
			unit.setText("B");
		} else if ((floatnum = (float) temp / 1000) < 1) {
			value = temp + "";
			unit.setText("KB");
		} else if ((floatGB = floatnum / 1000) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + "";
			unit.setText("MB");
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatGB) + "";
			unit.setText("GB");
		}
		return value;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AlarmSet alset = new AlarmSet();
		// 初始化网络状态
		sqlhelperTotal.initTablemobileAndwifi(context);
		if (SQLHelperTotal.TableWiFiOrG23 != ""
				&& sqlhelperTotal.getIsInit(context)) {
			// 启动闹钟
			alset.StartAlarmMobile(context);
			// 进行数据记录
			sqlhelperTotal.RecordTotalwritestats(context, false);
		} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
			alset.StartAlarmMobile(context);
			sqlhelperTotal.initTablemobileAndwifi(context);
		}
		initValues();
		initProgressBar();
		initPieBar();
		initWifiBar();
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

		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// 进行参数设置
		// 设置x轴显示范围
		int monthtotalDay = countDay(year, month);
		chartbar.setMonthDay(monthtotalDay);
		// 设置y轴显示值及范围
		double[] wifi = new double[monthDay];
		long maxwifiTraffic = 0;
		DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		for (int i = 0; i < wifi.length; i++) {
			long temp = wifiTraffic[i + 1] + wifiTraffic[i + 32];
			// 小数点2位
			wifi[i] = (double) ((int) temp / 10000) / 100;
			// format.format(wifi[i]);
			if (temp > maxwifiTraffic) {
				maxwifiTraffic = temp;
			}
		}
		chartbar.setData1(wifi);
		chartbar.setMaxTraffic((double) (int) maxwifiTraffic / 1000000 * 1.2);
		chartbar.setyMaxvalue((double) (int) maxwifiTraffic / 1000000 * 1.2);
		// 设置背景色（被隐藏的条）
		// chartbar.setBackgroundColor(Color.BLACK);
		// 设置初始显示图像位置
		if (monthDay + 2 < monthtotalDay) {
			chartbar.setxMinvalue(monthDay - 5);
			chartbar.setxMaxvalue(monthDay + 2);
		} else {
			chartbar.setxMinvalue(monthtotalDay - 7);
			chartbar.setxMaxvalue(monthtotalDay);
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
		final String PREFS_NAME = "allprefs";
		final String VALUE_MOBILE_SET = "mobilemonthuse";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileSet = prefs.getLong(VALUE_MOBILE_SET, 50000000);
		long moblileTotle = mobileTraffic[0] + mobileTraffic[63];
		int usePercent = (int) (moblileTotle / mobileSet * 360);
		// int usePercent=50;
		if (usePercent > 360)
			usePercent = 360;
		int[] percent = new int[] { usePercent, 360 - usePercent };
		final PieView pieView_mobile = new PieView(context, percent);
		// View PieView=findViewById(R.id.pie_bar_mobile);
		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_bar_mobile);
		final LinearLayout laout_mobile_pie = (LinearLayout) findViewById(R.id.linearlayout_piebar_mobile);
		//
		// laout_mobile_pie.setBackgroundColor(Color.WHITE);
		//
		ismobileshowpie = false;
		layout_mobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ismobileshowpie) {
					laout_mobile_pie.removeAllViews();
					ismobileshowpie = false;
				} else {
					laout_mobile_pie.removeAllViews();
					laout_mobile_pie.addView(pieView_mobile);
					ismobileshowpie = true;
				}
			}
		});
		// laout_mobile_pie.removeAllViews();
		// laout_mobile_pie.addView(pieView_mobile);
		// laout_mobile.removeAllViews();
	}

	/**
	 * 初始化进度条的现实数值
	 */
	private void initProgressBar() {
		// 获取设置的月度使用值，默认50m
		final String PREFS_NAME = "allprefs";
		final String VALUE_MOBILE_SET = "mobilemonthuse";
		final String VALUE_WIFI_SET = "wifimonthuse";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileSet = prefs.getLong(VALUE_MOBILE_SET, 50000000);
		long wifiSet = prefs.getLong(VALUE_WIFI_SET, 50000000);
		int mobile = 0;
		int wifi = 0;
		// showlog("mobile" + mobile_month_use + "wifi" + wifi_month_use);
		// showlog("mobile" + mobileSet + "wifi" + wifiSet);
		// 进行超百判断
		if (mobile_month_use > mobileSet)
			mobile = 100;
		else
			mobile = (int) (mobile_month_use * 100 / mobileSet);
		if (wifi_month_use > wifiSet)
			wifi = 100;
		else
			wifi = (int) (wifi_month_use * 100 / wifiSet);
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
		Log.d("main", string);
	}

}