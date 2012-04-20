package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main extends Activity {
	private Context context = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();

	// 临时存放两个数据------------
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
	 * 初始化显示数值
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView weekMobil = (TextView) findViewById(R.id.weekRate);
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		long[] mobile = new long[64];
		long[] wifi = new long[64];
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		mobile = sqlhelperTotal.SelectMobileData(context, year, month);
		wifi = sqlhelperTotal.SelectWifiData(context, year, month);
		todayMobil
				.setText(unitHandler(mobile[monthDay] + mobile[monthDay + 31]));
		// setweek(Time t,);
		Log.d("database", t.weekDay + "");
		// weekMobil.setText(unitHandler(mobile[0] + mobile[62]));
		monthMobil.setText(unitHandler(mobile[0] + mobile[63]));
		todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31]));
		// weekWifi.setText(unitHandler(wifi[0] + wifi[62]));
		monthWifi.setText(unitHandler(wifi[0] + wifi[63]));

	}

	private void setweek() {
		// TODO Auto-generated method stub

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
			Log.d("pac", packagenames[i]);
			uids[i] = packageinfo.applicationInfo.uid;
			Log.d("uid", uids[i] + "");
		}
	}

	// ------------
	/**
	 * 执行动态进度条设置
	 * @param i
	 * 移动数据
	 * @param j
	 * wifi
	 */
	private void RefreshProgressBar(int i,int j) {
//		ProgressBar myProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		MyProgressBar myProgressBar_wifi = (MyProgressBar) findViewById(R.id.progressbar_wifi);
		ProgressBarForV progforv_mobile= new  ProgressBarForV();
		progforv_mobile.j=i;
		progforv_mobile.execute(myProgressBar_mobile);
		ProgressBarForV progforv_wifi= new  ProgressBarForV();
		progforv_wifi.j=j;
		progforv_wifi.execute(myProgressBar_wifi);
	}
	/**
	 * 进度条设置
	 * @param i
	 * 移动数据
	 * @param j
	 * wifi
	 */
	private void ProgressBarSet(int i,int j) {
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		MyProgressBar myProgressBar_wifi = (MyProgressBar) findViewById(R.id.progressbar_wifi);
		myProgressBar_mobile.setProgress(i);
		myProgressBar_wifi.setProgress(j);
	}

	/**
	 * 单位标准化，mb，gb等
	 * 
	 * @param count
	 *            输入的long型数
	 * @return 返回String型值
	 */
	private String unitHandler(long count) {
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
		RefreshProgressBar(0,0);
	}

	private void showlog(String string) {
		Log.d("widget", string);
	}
}