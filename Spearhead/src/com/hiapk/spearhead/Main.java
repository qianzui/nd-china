package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity {
	private TextView todayMobil;
	private TextView weekMobil;
	private Context context = this;
	private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();

	// ��ʱ�����������------------
	private int[] uids;
	private String[] packagenames;

	// ------------
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initWidgets();
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
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView weekMobil = (TextView) findViewById(R.id.weekRate);
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		long[] mobile = new long[64];
		long[] wifi = new long[64];
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		mobile = sqlhelperTotal.SelectMobileData(context, year, month);
		wifi = sqlhelperTotal.SelectWifiData(context, year, month);
		todayMobil
				.setText(unitHandler(mobile[monthDay] + mobile[monthDay + 31]));
//		setweek(Time t,);
		Log.d("database", t.weekDay+"");
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
			Log.d("pac", packagenames[i]);
			uids[i] = packageinfo.applicationInfo.uid;
			Log.d("uid", uids[i] + "");
		}
	}

	// ------------
	/**
	 * ��ʼ�����ֲ���
	 */
	private void initWidgets() {
		// todayMobil = (TextView) findViewById(R.id.todayRate);
		// weekMobil
	}

	/**
	 * ��λ��׼����mb��gb��
	 * 
	 * @param count
	 *            �����long����
	 * @return ����String��ֵ
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
		sqlhelperTotal.initTablemobileAndwifi(context);
		if (SQLHelperTotal.TableWiFiOrG23 != "" && sqlhelperTotal.getIsInit(context)) {
			// ��������
			alset.StartAlarm(context);
			// ��ʼ������״̬
			sqlhelperTotal.initTablemobileAndwifi(context);
			// �������ݼ�¼
			sqlhelperTotal.RecordTotalwritestats(context, false);
			sqlhelperUid.RecordUidwritestats(context, false);
		} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
			sqlhelperTotal.initTablemobileAndwifi(context);
			alset.StartAlarm(context);
		}
		initValues();
	}
}