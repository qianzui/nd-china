package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.PieView;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.progressbar.StackedBarChart;
import com.hiapk.sqlhelper.SQLHelperTotal;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	// ����NotificationManager  
    private NotificationManager mNotification;  
    // Notification��ʾID  
    private static final int ID = 1;  
    
	private Context context = this;
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi��mobile����ʹ����
	private long wifi_month_use = 0;
	public static long mobile_month_use = 0;

	// ��ʱ�����������------------
	private int[] uids;
	private String[] packagenames;
	// ------------
	// ��ʾ����ͼ��
	boolean ismobileshowpie = false;
	// ��ȡ��ϵͳʱ��
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	// wifi�¶�����
	long[] wifiTraffic = new long[64];
	/**
	 * �����·ݵ��ƶ���������
	 */
	long[] mobileTraffic = new long[64];
	/**
	 * �����·ݵ��ƶ���������
	 */
	long[] mobileTrafficPart = new long[64];
	// ��Ļ���
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
		setonrefreshclicklistens();
		showNotice("��һ������","�ڶ�������");//���Դ��������ַ���
		
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
		TextView monthMobil2 = (TextView) findViewById(R.id.traffic_month_set);
		TextView monthMobilunit2 = (TextView) findViewById(R.id.unit_month_set);
		// TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		// TextView todayWifiunit = (TextView) findViewById(R.id.unit4);
		// TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		// TextView weekWifiunit = (TextView) findViewById(R.id.unit5);
		// TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		// TextView monthWifiunit = (TextView) findViewById(R.id.unit6);
		// ��ת��У��ҳ
		Button gotoThree = (Button) findViewById(R.id.gotoThree);
		gotoThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoThree();

			}
		});

		wifiTraffic = new long[64];
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
		// �¶���������
		String PREFS_NAME = "allprefs";
		String VALUE_MOBILE_SET = "mobilemonthuse";
		String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// ȡ��ÿ�ܵ�����
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		// ȡ���¶�����
		mobileTraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		//
		// mobileTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		//
		wifiTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		// ������������
		todayMobil.setText(unitHandler(mobileTraffic[monthDay]
				+ mobileTraffic[monthDay + 31], todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		weekMobil.setText(unitHandler(weektraffic[0], weekMobilunit));
		// �¶���������
		MonthlyUseData monthData = new MonthlyUseData();
		mobile_month_use = monthData.getMonthUseData(context);
		long mobileSet = prefs.getLong(VALUE_MOBILE_SET, 52428800);
		long mobileHasUsed = prefs.getLong(VALUE_MOBILE_HASUSED_LONG, 0);
		mobile_month_use = mobile_month_use + mobileHasUsed;
		if (mobile_month_use > mobileSet)
			monthMobil.setTextColor(Color.RED);
		else
			monthMobil.setTextColor(Color.GREEN);

		monthMobil.setText(unitHandler(mobile_month_use, monthMobilunit));
		monthMobil2.setText("/" + unitHandler(mobileSet, monthMobilunit2));
		// todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31],
		// todayWifiunit));
		// weekWifi.setText(unitHandler(weektraffic[5], weekWifiunit));
		// wifi_month_use = wifi[0] + wifi[63];
		// monthWifi.setText(unitHandler(wifi_month_use, monthWifiunit));

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

	public void gotoThree() {
		SpearheadActivity sp = new SpearheadActivity();

		sp.tabThree();
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
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ���ڵĿ��
		windowswidesize = dm.widthPixels / 10;
		// showlog(screenWidth+"");
		myProgressBar_mobile.setTextsize(windowswidesize);
		// myProgressBar_wifi.setTextsize(fontsize);
		ProgressBarForV progforv_mobile = new ProgressBarForV();
		progforv_mobile.j = i;
		progforv_mobile.execute(myProgressBar_mobile);
		// ProgressBarForV progforv_wifi = new ProgressBarForV();
		// progforv_wifi.j = j;
		// progforv_wifi.execute(myProgressBar_wifi);
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
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		myProgressBar_mobile.setProgress(i);
		// myProgressBar_wifi.setProgress(j);
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
		float floatTB = count;
		if ((temp = temp / 1024) < 1) {
			value = count + "";
			unit.setText("B");
		} else if ((floatnum = (float) temp / 1024) < 1) {
			value = temp + "";
			unit.setText("KB");
		} else if ((floatGB = floatnum / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + "";
			unit.setText("MB");
		} else if ((floatTB = floatGB / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatGB) + "";
			unit.setText("GB");
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatTB) + "";
			unit.setText("TB");
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
		initPieBar();
		initWifiBar();

	}

	private void setonrefreshclicklistens() {
		Button btn_refresh = (Button) findViewById(R.id.refresh);
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				initPieBar();
				initWifiBar();
			}
		});
	}

	/**
	 * ��ʼ��wifi���ֵ���״ͼ
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
	 * ����wifi���ֵ���״ͼ
	 * 
	 * @param context
	 * @return ������ʾ����״ͼ
	 */
	private StackedBarChart initStackedBarChart(Context context) {

		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// ���в�������
		// ����x����ʾ��Χ
		int monthtotalDay = countDay(year, month);
		chartbar.setMonthDay(monthtotalDay);
		// ����y����ʾֵ����Χ
		double[] wifi = new double[monthDay];
		long maxwifiTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		for (int i = 0; i < wifi.length; i++) {
			long temp = wifiTraffic[i + 1] + wifiTraffic[i + 32];
			// С����2λ
			wifi[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			// format.format(wifi[i]);
			if (temp > maxwifiTraffic) {
				maxwifiTraffic = temp;
			}
		}
		chartbar.setData1(wifi);
		chartbar.setMaxTraffic((double) (int) maxwifiTraffic / 1048576 * 1.2);
		chartbar.setyMaxvalue((double) (int) maxwifiTraffic / 1048576 * 1.2);
		// ���ñ���ɫ�������ص�����
		// chartbar.setBackgroundColor(Color.BLACK);
		// ���ó�ʼ��ʾͼ��λ��
		if (monthDay + 2 < monthtotalDay) {
			chartbar.setxMinvalue(monthDay - 5);
			chartbar.setxMaxvalue(monthDay + 2);
		} else {
			chartbar.setxMinvalue(monthtotalDay - 7);
			chartbar.setxMaxvalue(monthtotalDay);
		}
		// ������ʾ������
		String[] xaxles = new String[monthtotalDay];
		for (int i = 0; i < monthtotalDay; i++) {
			int j = i + 1;
			xaxles[i] = month + "��" + j + "��";
		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	private void initPieBar() {
		// TODO Auto-generated method stub
		// ��ȡĬ��������ֵ
		final String PREFS_NAME = "allprefs";
		final String VALUE_MOBILE_SET = "mobilemonthuse";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileSet = prefs.getLong(VALUE_MOBILE_SET, 52428800);
		// long moblileTotle = mobileTraffic[0] + mobileTraffic[63];
		int usePercent = 0;
		if (mobile_month_use == 0) {
			usePercent = 0;
		} else if (mobileSet == 0) {
			usePercent = 360;
		} else {
			usePercent = (int) (((float) mobile_month_use / mobileSet) * 360);
		}
		if (usePercent > 360)
			usePercent = 360;

		int mobilePersent = 0;
		// �¶�����Ϊ0�ж�
		if (mobile_month_use == 0) {
			mobilePersent = 0;
		} else if (mobileSet == 0) {
			mobilePersent = 100;
		} else {
			// ���г����ж�
			if (mobile_month_use > mobileSet)
				mobilePersent = 100;
			else
				mobilePersent = (int) ((float) mobile_month_use * 100 / mobileSet);
		}
		int[] percent = new int[] { usePercent, 360 - usePercent };
		final PieView pieView_mobile = new PieView(context, percent,
				mobilePersent);
		// View PieView=findViewById(R.id.pie_bar_mobile);
		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_bar_mobile);
		final LinearLayout laout_mobile_pie = (LinearLayout) findViewById(R.id.linearlayout_piebar_mobile);
		laout_mobile_pie.removeAllViews();
		//
		// laout_mobile_pie.setBackgroundColor(Color.WHITE);
		//
		ismobileshowpie = false;
		layout_mobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				laout_mobile_pie.removeAllViews();
				if (ismobileshowpie) {
					ismobileshowpie = false;
				} else {
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
	 * ��ʼ������������ʵ��ֵ
	 */
	private void initProgressBar() {
		// ��ȡ���õ��¶�ʹ��ֵ��Ĭ��50m
		final String PREFS_NAME = "allprefs";
		final String VALUE_MOBILE_SET = "mobilemonthuse";
		final String VALUE_WIFI_SET = "wifimonthuse";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileSet = prefs.getLong(VALUE_MOBILE_SET, 52428800);
		long wifiSet = prefs.getLong(VALUE_WIFI_SET, 52428800);
		int mobile = 0;
		int wifi = 0;
		// showlog("mobile" + mobile_month_use + "wifi" + wifi_month_use);
		// showlog("mobile" + mobileSet + "wifi" + wifiSet);
		// �¶�����Ϊ0�ж�
		if (mobile_month_use == 0) {
			mobile = 0;
		} else if (mobileSet == 0) {
			mobile = 100;
		} else {

			// ���г����ж�
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
	 * ���㵥���м���
	 * 
	 * @param year
	 *            �������
	 * @param month
	 *            �����·�
	 * @return ��������
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
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("main", string);
	}
	
	
	void showNotice(String textUp,String textDown){
   	 // ���NotificationManagerʵ��  
       String service = Context.NOTIFICATION_SERVICE;  
       mNotification = (NotificationManager)getSystemService(service);  
       // ������ʾͼ�꣬��ͼ�����״̬����ʾ  
       int icon = R.drawable.ic_launcher;   
       // ������ʾ��ʾ��Ϣ������ϢҲ����״̬����ʾ  
       CharSequence tickerText = "�ȷ��������";
       // ��ʾʱ��  
       long when = System.currentTimeMillis();       
       // ʵ����Notification          
       Notification notification = new Notification(icon,tickerText,when);
       RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.notice);
       contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
       contentView.setTextViewText(R.id.textUp, textUp);
       contentView.setTextViewText(R.id.textDown, textDown);
       notification.contentView = contentView;
       notification.flags = Notification.FLAG_ONGOING_EVENT;

       
//       ʵ����Intent  
       Intent intent = new Intent(this, SpearheadActivity.class);  
//       ���PendingIntent  
       PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);   
//       �����¼���Ϣ  
       notification.contentIntent = pi;
       // ����֪ͨ  
       mNotification.notify(ID, notification);  
   	
   }


}