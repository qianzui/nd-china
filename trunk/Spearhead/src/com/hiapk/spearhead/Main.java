package com.hiapk.spearhead;

import java.util.List;
import com.hiapk.alertdialog.CustomDialogMainBeen;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.MonthDay;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.progressbar.StackedBarChart;
import com.hiapk.provider.ColorChangeMainBeen;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.widget.SetText;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
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
	// wifi��mobile����ʹ����
	private long mobile_month_use = 0;
	// ��ȡ��ϵͳʱ��
	private int year;
	private int month;
	private int monthDay;
	/**
	 * �����·ݵ��ƶ���������
	 */
	private long[] mobileTraffic = new long[64];
	// ��Ļ���
	private int windowswidesize;
	private SharedPrefrenceData sharedData;
	// Alarm
	private AlarmSet alset = new AlarmSet();

	// fortest

	// ��״ͼ��ʶ0Ϊ������1Ϊmobile��2Ϊwifi
	// int stackflag = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Ϊ���˳���
		Mapplication.getInstance().addActivity(this);
		// umeng
		// .. MobclickAgent.onError(this);
		// ��ȡ�̶��������
		sharedData = new SharedPrefrenceData(context);

		if (SQLStatic.getIsInit(context) == false) {
			if (SQLStatic.uids == null) {
				getuids();
			}
		}
		// ------------
		SetText.setText(context);
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		if (sharedDatawidget.isNotifyOpen()) {
			alset.StartWidgetAlarm(context);
		}
		if (SQLStatic.getIsInit(context) == false) {
			initSQLdatabase(SQLStatic.uids, SQLStatic.packagenames);
		}

		setonclicklistens();
		// setontvclicklisten();
	}

	/**
	 * ��ʼ������
	 */
	private void initScene() {
		// TODO Auto-generated method stub
		// ���ð�ť��ʾ����
		boolean hasTraffSet = sharedData.isMonthSetHasSet();
		Button btn_toThree = (Button) findViewById(R.id.setTaoCan);
		if (hasTraffSet) {
			btn_toThree.setText("  У׼����  ");
		} else {
			btn_toThree.setText("  �����������ײ�  ");
		}

	}

	/**
	 * ��ʼ����ʾ��ֵ
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		// ��ʼ��С����
		// ��������
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		// TextView leftMobil = (TextView) findViewById(R.id.weekRate);
		// TextView leftMobilunit = (TextView) findViewById(R.id.unit2);
		// ��������
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		// ����ʣ��
		TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
		TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
		// ��������
		TextView monthSet = (TextView) findViewById(R.id.monthSet);
		TextView monthSetunit = (TextView) findViewById(R.id.unit5);
		// ��ʼ��������ȡ����
		// ȡ���¶�����
		mobileTraffic = TrafficManager.mobile_month_data;
		// ������������
		todayMobil.setText(UnitHandler.unitHandlerAcurrac(
				mobileTraffic[monthDay] + mobileTraffic[monthDay + 31],
				todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		// �¶���������
		mobile_month_use = TrafficManager.getMonthUseMobile(context);
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
			monthSet.setTextColor(ColorChangeMainBeen.colorBlue);
		} else {
			monthSet.setText("δ����");
			monthSet.setTextColor(ColorChangeMainBeen.colorRed);
			monthSetunit.setText("");
		}

	}

	// ----------

	// ----------
	/**
	 * ��ʱ����
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
					// showLog("������ʾ��uid=" + uid);
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
	 * ��ʼ�����ݿ�
	 * 
	 * @param uids
	 *            uid����
	 * @param packagename
	 *            uid��Ӧ�İ�����
	 */
	private void initSQLdatabase(int[] uids, String[] packagename) {
		// TODO Auto-generated method stub
		SQLHelperInitSQL sqlhelperInit = new SQLHelperInitSQL();
		if (!SQLStatic.getIsInit(context)) {
			sqlhelperInit.initSQL(context, uids, packagename);
		}
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
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		alset.StartAlarm(context);
		initValues();
		initWifiBar();
	}

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
					// ��¼���ˢ�´���
					// MobclickAgent.onEvent(context, "refresh");
					// ��ʼ������״̬
					// ��������
					alset.StartAlarm(context);
					SetText.resetWidgetAndNotify(context);
					initValues();
					initWifiBar();

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

		// ��ת��У��ҳ
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
					// ��������
					TextView monthSet = (TextView) findViewById(R.id.monthSet);
					TextView monthSetunit = (TextView) findViewById(R.id.unit5);
					TextView monthMobil = (TextView) findViewById(R.id.monthRate);
					TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
					TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
					TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
					customDialog.dialogMonthSet_Main(btn_toThree, monthSet,
							monthSetunit, monthRemain, monthRemainunit,
							monthMobil, monthMobilunit);
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
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ���ڵĿ��
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// chartbar.setXaxisText(year + "��");
		chartbar.setXaxisText("");
		MonthDay.countDay(year, month);
		int monthbeforetotalDay = 0;
		if (month == 1) {
			monthbeforetotalDay = MonthDay.countDay(year - 1, 12);
		} else {
			monthbeforetotalDay = MonthDay.countDay(year, month - 1);
		}
		chartbar.setShowDay(monthbeforetotalDay + monthDay);
		// ����y����ʾֵ����Χ
		double[] wifiTraff = new double[monthbeforetotalDay + monthDay];
		long maxTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		// TextView tvtraff = (TextView) findViewById(R.id.tv_stackChart);
		// switch (stackflag) {
		// case 0:
		for (int i = 0; i < wifiTraff.length; i++) {
			long temp = 0;
			if (i < monthbeforetotalDay) {
				temp = TrafficManager.wifi_month_data_before[i + 1]
						+ TrafficManager.wifi_month_data_before[i + 32];
			} else {
				temp = TrafficManager.wifi_month_data[i - monthbeforetotalDay
						+ 1]
						+ TrafficManager.wifi_month_data[i
								- monthbeforetotalDay + 32];
			}

			// + TrafficManager.mobile_month_data[i + 1]
			// + TrafficManager.mobile_month_data[i + 32]

			// С����2λ
			wifiTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			// format.format(wifi[i]);
			if (temp > maxTraffic) {
				maxTraffic = temp;
			}
		}
		// showlog(TrafficManager.wifi_month_data_before[0]
		// + TrafficManager.wifi_month_data_before[63] + "");
		chartbar.setMainTitle("����ͳ��(��)");
		chartbar.setTopTitle("�ƶ�����");
		// tvtraff.setText("   ������");
		double[] mobileTraff = new double[monthbeforetotalDay + monthDay];
		// break;
		// case 1:
		for (int i = 0; i < mobileTraff.length; i++) {
			long temp = 0;
			if (i < monthbeforetotalDay) {
				temp = TrafficManager.mobile_month_data_before[i + 1]
						+ TrafficManager.mobile_month_data_before[i + 32];
			} else {
				temp = TrafficManager.mobile_month_data[i - monthbeforetotalDay
						+ 1]
						+ TrafficManager.mobile_month_data[i
								- monthbeforetotalDay + 32];
			}
			// С����2λ
			mobileTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			if (temp > maxTraffic) {
				maxTraffic = temp;
			}
			// format.format(wifi[i]);
		}
		chartbar.setData1(mobileTraff, wifiTraff);
		if (maxTraffic < 848576) {
			chartbar.setyMaxvalue(1);
			chartbar.setMaxTraffic(1);
		} else {
			chartbar.setMaxTraffic((double) (long) maxTraffic / 1048576 * 1.2);
			chartbar.setyMaxvalue((double) (long) maxTraffic / 1048576 * 1.2);
		}

		chartbar.setxMinvalue(monthbeforetotalDay + monthDay - 6.5);
		chartbar.setxMaxvalue(monthbeforetotalDay + monthDay + 0.5);
		// ������ʾ������
		String[] xaxles = new String[monthDay + monthbeforetotalDay];
		for (int i = 0; i < monthDay + monthbeforetotalDay; i++) {
			if (i < monthbeforetotalDay) {
				int j = i + 1;
				if (month == 1) {
					xaxles[i] = 12 + "��" + j + "��";
				} else {
					xaxles[i] = month - 1 + "��" + j + "��";
				}
			} else {
				int j = i - monthbeforetotalDay + 1;
				xaxles[i] = month + "��" + j + "��";
			}

		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	// /**
	// * ��ʾ��־
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// // Log.d("main", string);
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
