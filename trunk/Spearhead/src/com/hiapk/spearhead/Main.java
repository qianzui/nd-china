package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.PieView;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.sqlhelper.SQLHelperTotal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {
	private Context context = this;
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi��mobile����ʹ����
	private long wifi_month_use = 0;
	private long mobile_month_use = 0;

	// ��ʱ�����������------------
	private int[] uids;
	private String[] packagenames;

	// ------------
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
	 * ��ʼ����ʾ��ֵ
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		// ��ʼ��С����
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		TextView weekMobil = (TextView) findViewById(R.id.weekRate);
		TextView weekMobilunit = (TextView) findViewById(R.id.unit2);
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		TextView todayWifiunit = (TextView) findViewById(R.id.unit4);
		TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		TextView weekWifiunit = (TextView) findViewById(R.id.unit5);
		TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		TextView monthWifiunit = (TextView) findViewById(R.id.unit6);
		long[] mobile = new long[64];
		long[] wifi = new long[64];
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		int weekDay = t.weekDay;
		// ȡ��ÿ�ܵ�����
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		// ȡ���¶�����
		mobile = sqlhelperTotal.SelectMobileData(context, year, month);
		wifi = sqlhelperTotal.SelectWifiData(context, year, month);
		// ������������
		todayMobil.setText(unitHandler(
				mobile[monthDay] + mobile[monthDay + 31], todayMobilunit));
		weekMobil.setText(unitHandler(weektraffic[0], weekMobilunit));
		mobile_month_use = mobile[0] + mobile[63];
		monthMobil.setText(unitHandler(mobile_month_use, monthMobilunit));
		todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31],
				todayWifiunit));
		weekWifi.setText(unitHandler(weektraffic[5], weekWifiunit));
		wifi_month_use = wifi[0] + wifi[63];
		monthWifi.setText(unitHandler(wifi_month_use, monthWifiunit));

	}


	/**
	 * ��ʼ�����ݿ�
	 * 
	 * @param uids
	 *            uid����
	 * @param packagename
	 *            uid��Ӧ�İ�����
	 */
	private void initSQLdatabase(int[] uids, String[] packagename) {
		// TODO Auto-generated method stub
		if (!sqlhelperTotal.getIsInit(context)) {
			sqlhelperTotal.initSQL(context, uids, packagename);
		}
	}

	// ----------
	/**
	 * ��ʱ����
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
	 * ִ�ж�̬����������
	 * 
	 * @param i
	 *            �ƶ�����
	 * @param j
	 *            wifi
	 */
	private void RefreshProgressBar(int i, int j) {
		// ProgressBar myProgressBar = (ProgressBar)
		// findViewById(R.id.progressbar);
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		MyProgressBar myProgressBar_wifi = (MyProgressBar) findViewById(R.id.progressbar_wifi);
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ���ڵĿ��
		int fontsize = dm.widthPixels / 10;
		// showlog(screenWidth+"");
		myProgressBar_mobile.setTextsize(fontsize);
		myProgressBar_wifi.setTextsize(fontsize);
		ProgressBarForV progforv_mobile = new ProgressBarForV();
		progforv_mobile.j = i;
		progforv_mobile.execute(myProgressBar_mobile);
		ProgressBarForV progforv_wifi = new ProgressBarForV();
		progforv_wifi.j = j;
		progforv_wifi.execute(myProgressBar_wifi);
	}

	/**
	 * ����������
	 * 
	 * @param i
	 *            �ƶ�����
	 * @param j
	 *            wifi
	 */
	private void ProgressBarSet(int i, int j) {
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		MyProgressBar myProgressBar_wifi = (MyProgressBar) findViewById(R.id.progressbar_wifi);
		myProgressBar_mobile.setProgress(i);
		myProgressBar_wifi.setProgress(j);
	}

	/**
	 * ��λ��׼����mb��gb��
	 * 
	 * @param count
	 *            �����long����
	 * @param unit
	 *            ��ֵ����Ҫ��ʾ��textview
	 * @return ����String��ֵ
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
			DecimalFormat format = new DecimalFormat("0.###");
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
		// ��ʼ������״̬
		sqlhelperTotal.initTablemobileAndwifi(context);
		if (SQLHelperTotal.TableWiFiOrG23 != ""
				&& sqlhelperTotal.getIsInit(context)) {
			// ��������
			alset.StartAlarmMobile(context);
			// �������ݼ�¼
			sqlhelperTotal.RecordTotalwritestats(context, false);
		} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
			alset.StartAlarmMobile(context);
			sqlhelperTotal.initTablemobileAndwifi(context);
		}
		initValues();
		initProgressBar();
//		initPieBar();
		

	}

	private void initPieBar() {
		// TODO Auto-generated method stub
		int[] colors = new int[] { Color.YELLOW, Color.RED, Color.BLUE,
				Color.GREEN };
		int[] shade_colors = new int[] { Color.rgb(180, 180, 0),
				Color.rgb(180, 20, 10), Color.rgb(3, 23, 163),
				Color.rgb(15, 165, 0) };
		int[] percent = new int[] { 50, 140, 100, 70 };
		PieView pieView = new PieView(context, colors, shade_colors, percent);
//		View PieView=findViewById(R.id.pie_bar_mobile);
		LinearLayout laout_mobile=(LinearLayout) findViewById(R.id.linearlayout_bar_mobile);
		laout_mobile.removeAllViews();
		laout_mobile.addView(pieView);
	}

	/**
	 * ��ʼ������������ʵ��ֵ
	 */
	private void initProgressBar() {
		// ��ȡ���õ��¶�ʹ��ֵ��Ĭ��50m
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
		// ���г����ж�
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

	private void showlog(String string) {
		Log.d("main", string);
	}

}