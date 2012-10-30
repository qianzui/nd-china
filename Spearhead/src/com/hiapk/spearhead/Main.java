package com.hiapk.spearhead;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.ui.chart.StackedBarChart;
import com.hiapk.ui.custom.CustomDialogMainBeen;
import com.hiapk.ui.skin.ColorChangeMainBeen;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.ui.skin.UiColors;
import com.hiapk.util.MonthDay;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main extends Activity {

	private Context context = this;
	// wifi与mobile单月使用量
	private long mobile_month_use = 0;
	// 获取的系统时间
	private int year;
	private int month;
	private int monthDay;
	// 屏幕宽度
	private int windowswidesize;
	private SharedPrefrenceData sharedData;
	// Alarm
	private AlarmSet alset = new AlarmSet();

	LinearLayout layout_mobile;
	/**
	 * 图表种类，0代表移动，1代表wifi
	 */
	private int chartType = 0;
	private String TAG = "Main";

	// fortest

	// 柱状图标识0为总流量1为mobile，2为wifi
	// int stackflag = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		onCreateWifiBar();
		// 为了退出。
		SpearheadApplication.getInstance().addActivity(this);
		// umeng
		// .. MobclickAgent.onError(this);
		// 获取固定存放数据
		sharedData = new SharedPrefrenceData(context);

		// if (SQLStatic.getIsInit(context) == false) {
		// if (SQLStatic.uids == null) {
		// SQLStatic.uids=SQLStatic.selectUidnumbers(context);
		// }
		// }
		// ------------
		setonclicklistens();
		// setontvclicklisten();
	}

	/**
	 * 初始化界面
	 */
	private void initScene() {
		// 设置按钮显示文字
		boolean hasTraffSet = sharedData.isMonthSetHasSet();
		Button btn_toThree = (Button) findViewById(R.id.setTaoCan);
		RelativeLayout title = (RelativeLayout) findViewById(R.id.mainTitleBackground);
		// 设置皮肤
		btn_toThree.setBackgroundResource(SkinCustomMains
				.buttonBackgroundLight());
		title.setBackgroundResource(SkinCustomMains.titleBackground());
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
		// 初始化小部件
		// 今日已用
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		// TextView leftMobil = (TextView) findViewById(R.id.weekRate);
		// TextView leftMobilunit = (TextView) findViewById(R.id.unit2);
		// 本月已用
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		// 本月剩余
		TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
		TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
		// 初始化流量获取函数
		// 取得月度流量
		// mobileTraffic = TrafficManager.mobile_month_data;
		long todayUsedLong = sharedData.getTodayMobileDataLong();
		// 进行流量设置
		todayMobil.setText(UnitHandler.unitHandlerAcurrac(todayUsedLong,
				todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		// 月度流量设置
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

	}

	@Override
	protected void onPause() {
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
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
		alset.StartAlarm(context);
		initValues();
		// initChartBar();
		new AsyncTaskoninitChartBar().execute(context);
		SetText.resetWidgetAndNotify(context);
	}

	private void setonclicklistens() {
		final Button btn_refresh = (Button) findViewById(R.id.refresh);
		btn_refresh.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
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
					// 初始化网络状态
					// 启动闹钟
					alset.StartAlarmMobile(context);
					initValues();
					initChartBar();
					SetText.resetWidgetAndNotify(context);
					return true;
				}
				return false;
			}
		});

		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

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
				// gotoThree();
				CustomDialogMainBeen customDialog = new CustomDialogMainBeen(
						context);
				boolean hasTraffSet = sharedData.isMonthSetHasSet();
				if (!hasTraffSet) {
					Button btn_toThree = (Button) findViewById(R.id.setTaoCan);
					// 包月流量
					TextView monthSet = (TextView) findViewById(R.id.monthSet);
					TextView monthMobil = (TextView) findViewById(R.id.monthRate);
					TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
					TextView monthRemain = (TextView) findViewById(R.id.monthRemain);
					TextView monthRemainunit = (TextView) findViewById(R.id.unit4);
					customDialog.dialogMonthSet_Main(btn_toThree, monthSet,
							monthRemain, monthRemainunit, monthMobil,
							monthMobilunit);
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
		ImageButton img_btn_chart = (ImageButton) findViewById(R.id.img_btn_change_chart);
		img_btn_chart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (chartType) {
				case 0:
					chartType = 1;
					initChartBar();
					break;
				case 1:
					chartType = 0;
					initChartBar();
					break;
				}
			}
		});
	}

	/**
	 * 初始化wifi部分的柱状图
	 */
	private void onCreateWifiBar() {
		layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_wifi);
		LayoutInflater factory = LayoutInflater.from(context);
		View loading = factory.inflate(R.layout.loading_layout, null);
		layout_mobile.removeAllViews();
		layout_mobile.addView(loading);
	}

	/**
	 * onResume时初始化chart
	 */
	private void initChartBar() {
		Logs.d(TAG, "month=" + month);
		switch (chartType) {
		case 0:
			initMobileBar();
			break;
		case 1:
			initWifiBar();
			break;
		default:
			initMobileBar();
			break;
		}
	}

	/**
	 * 初始化mobile部分的柱状图
	 */
	private void initMobileBar() {
		StackedBarChart chartbar = initStackedBarMobileChart(context);
		View view = chartbar.execute(context);
		layout_mobile.removeAllViews();
		layout_mobile.addView(view);
	}

	/**
	 * 初始化wifi部分的柱状图
	 */
	private void initWifiBar() {
		StackedBarChart chartbar = initStackedBarWifiChart(context);
		View view = chartbar.execute(context);
		layout_mobile.removeAllViews();
		layout_mobile.addView(view);
	}

	/**
	 * 设置mobile部分的柱状图
	 * 
	 * @param context
	 * @return 返回显示的柱状图
	 */
	private StackedBarChart initStackedBarMobileChart(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// chartbar.setXaxisText(year + "年");
		chartbar.setXaxisText("");
		MonthDay.countDay(year, month);
		int monthbeforetotalDay = 0;
		if (month == 1) {
			monthbeforetotalDay = MonthDay.countDay(year - 1, 12);
		} else {
			monthbeforetotalDay = MonthDay.countDay(year, month - 1);
		}
		chartbar.setShowDay(monthbeforetotalDay + monthDay);
		// 设置y轴显示值及范围
		double[] wifiTraff = new double[monthbeforetotalDay + monthDay];
		long maxTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		// TextView tvtraff = (TextView) findViewById(R.id.tv_stackChart);
		// switch (stackflag) {
		// case 0:
		// 统计最大wifi流量
		// for (int i = 0; i < wifiTraff.length; i++) {
		// long temp = 0;
		// if (i < monthbeforetotalDay) {
		// temp = TrafficManager.wifi_month_data_before[i + 1]
		// + TrafficManager.wifi_month_data_before[i + 32];
		// } else {
		// temp = TrafficManager.wifi_month_data[i - monthbeforetotalDay
		// + 1]
		// + TrafficManager.wifi_month_data[i
		// - monthbeforetotalDay + 32];
		// }
		//
		// // + TrafficManager.mobile_month_data[i + 1]
		// // + TrafficManager.mobile_month_data[i + 32]
		//
		// // 小数点2位
		// wifiTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
		// // format.format(wifi[i]);
		// if (temp > maxTraffic) {
		// maxTraffic = temp;
		// }
		// }
		//
		// showlog(TrafficManager.wifi_month_data_before[0]
		// + TrafficManager.wifi_month_data_before[63] + "");
		chartbar.setMainTitle("流量统计(2G/3G)");
		chartbar.setTopTitle("移动网络");
		chartbar.setChartbarcolor(UiColors.chartbarcolorMobile);
		// tvtraff.setText("   总流量");
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
			// 小数点2位
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
		chartbar.setxMinvalue(monthbeforetotalDay + monthDay - 5.5);
		chartbar.setxMaxvalue(monthbeforetotalDay + monthDay + 0.5);
		// 设置显示的日期
		String[] xaxles = new String[monthDay + monthbeforetotalDay];
		for (int i = 0; i < monthDay + monthbeforetotalDay; i++) {
			if (i < monthbeforetotalDay) {
				int j = i + 1;
				if (month == 1) {
					xaxles[i] = 12 + "月" + j + "日";
				} else {
					xaxles[i] = month - 1 + "月" + j + "日";
				}
			} else {
				int j = i - monthbeforetotalDay + 1;
				xaxles[i] = month + "月" + j + "日";
			}

		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	/**
	 * 设置wifi部分的柱状图
	 * 
	 * @param context
	 * @return 返回显示的柱状图
	 */
	private StackedBarChart initStackedBarWifiChart(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		// chartbar.setXaxisText(year + "年");
		chartbar.setXaxisText("");
		MonthDay.countDay(year, month);
		int monthbeforetotalDay = 0;
		if (month == 1) {
			monthbeforetotalDay = MonthDay.countDay(year - 1, 12);
		} else {
			monthbeforetotalDay = MonthDay.countDay(year, month - 1);
		}
		chartbar.setShowDay(monthbeforetotalDay + monthDay);
		// 设置y轴显示值及范围
		double[] wifiTraff = new double[monthbeforetotalDay + monthDay];
		long maxTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		// TextView tvtraff = (TextView) findViewById(R.id.tv_stackChart);
		// switch (stackflag) {
		// case 0:
		// 统计最大wifi流量
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

			// 小数点2位
			wifiTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			// format.format(wifi[i]);
			if (temp > maxTraffic) {
				maxTraffic = temp;
			}
		}

		// showlog(TrafficManager.wifi_month_data_before[0]
		// + TrafficManager.wifi_month_data_before[63] + "");
		chartbar.setMainTitle("流量统计(WIFI)");
		chartbar.setTopTitle("WIFI网络");
		chartbar.setChartbarcolor(UiColors.chartbarcolorWifi);
		// tvtraff.setText("   总流量");
		double[] mobileTraff = new double[monthbeforetotalDay + monthDay];
		// break;
		// case 1:
		// for (int i = 0; i < mobileTraff.length; i++) {
		// long temp = 0;
		// if (i < monthbeforetotalDay) {
		// temp = TrafficManager.mobile_month_data_before[i + 1]
		// + TrafficManager.mobile_month_data_before[i + 32];
		// } else {
		// temp = TrafficManager.mobile_month_data[i - monthbeforetotalDay
		// + 1]
		// + TrafficManager.mobile_month_data[i
		// - monthbeforetotalDay + 32];
		// }
		// // 小数点2位
		// mobileTraff[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
		// if (temp > maxTraffic) {
		// maxTraffic = temp;
		// }
		// // format.format(wifi[i]);
		// }
		chartbar.setData1(wifiTraff, mobileTraff);
		if (maxTraffic < 848576) {
			chartbar.setyMaxvalue(1);
			chartbar.setMaxTraffic(1);
		} else {
			chartbar.setMaxTraffic((double) (long) maxTraffic / 1048576 * 1.2);
			chartbar.setyMaxvalue((double) (long) maxTraffic / 1048576 * 1.2);
		}
		chartbar.setxMinvalue(monthbeforetotalDay + monthDay - 5.5);
		chartbar.setxMaxvalue(monthbeforetotalDay + monthDay + 0.5);
		// 设置显示的日期
		String[] xaxles = new String[monthDay + monthbeforetotalDay];
		for (int i = 0; i < monthDay + monthbeforetotalDay; i++) {
			if (i < monthbeforetotalDay) {
				int j = i + 1;
				if (month == 1) {
					xaxles[i] = 12 + "月" + j + "日";
				} else {
					xaxles[i] = month - 1 + "月" + j + "日";
				}
			} else {
				int j = i - monthbeforetotalDay + 1;
				xaxles[i] = month + "月" + j + "日";
			}

		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	/**
	 * 避免achareengine在初始化时的报错
	 * 
	 * @author Administrator
	 * 
	 */
	private class AsyncTaskoninitChartBar extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected Long doInBackground(Context... params) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			initChartBar();
		}
	}

	// /**
	// * 显示日志
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// // Log.d("main", string);
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
