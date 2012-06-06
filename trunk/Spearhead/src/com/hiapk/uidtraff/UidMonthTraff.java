package com.hiapk.uidtraff;

import com.hiapk.dataexe.UnitHandler;
import com.hiapk.progressbar.BudgetPie;
import com.hiapk.progressbar.ProjectStatusChart;
import com.hiapk.spearhead.R;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.sqlhelper.SQLStatic;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UidMonthTraff extends Activity {
	int year;
	int month;
	int monthDay;
	Context context = this;
	long[] pieValue = new long[2];
	long[] mobileBefore = new long[64];
	long[] mobileNow = new long[64];
	long[] wifiBefore = new long[64];
	long[] wifiNow = new long[64];
	int uidnumber;
	/**
	 * true的话，表示可以进行uid数据读取
	 */
	boolean uidtraff_UidSQL = false;
	/**
	 * true的话，表示可以进行uidTotal数据读取
	 */
	// boolean uidtraff_UidTotalSQL = false;
	int windowswidesize = 200;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// umeng
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uid_traff);
		MobclickAgent.onError(this);
		// 获取导入数据
		Bundle bData = this.getIntent().getExtras();
		uidnumber = bData.getInt("uid");
		String appname = bData.getString("appname");
		String pkname = bData.getString("pkname");
		// 初始化数据
		initTime();
		initSurface(uidnumber, appname, pkname);
		// 取得窗口属性
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		// 初始化视图
		new AsyncTaskonInitProChart().execute(context);
		new AsyncTaskonInitPieChart().execute(context);
		// View viewPro = initProjectChart(uidnumber);
		// View viewPie = initPie(uidnumber);

		// 添加视图
		// LinearLayout linearProject = (LinearLayout)
		// findViewById(R.id.new_series);
		// linearProject.addView(viewPro);
		// LinearLayout linearPie = (LinearLayout)
		// findViewById(R.id.new_budget);
		// linearPie.addView(viewPie);

	}

	private class AsyncTaskonInitPieChart extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// uidtraff_UidTotalSQL = false;
			LinearLayout linearPie = (LinearLayout) findViewById(R.id.new_budget);
			LayoutInflater factory = LayoutInflater.from(context);
			View loading = factory.inflate(R.layout.loading_layout, null);
			linearPie.addView(loading);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			int timetap = 0;
			while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
				try {
					Thread.sleep(300);
					timetap += 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 3) {
					SQLStatic.setSQLUidTotalOnUsed(false);
					return false;
				}
			}
			// onProgressUpdate((long) 1);
			// try {
			// Thread.sleep(20);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// if (uidtraff_UidTotalSQL == false)
			// return false;
			SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
			pieValue = sqlUidTotal.SelectUidNetData(params[0], uidnumber);
			// if ((pieValue[0] == 0 )&& (pieValue[1] == 0)) {
			// budgetPie.setValues(new long[] { 1, 1 });
			// }

			return true;
		}

		// @Override
		// protected void onProgressUpdate(Long... values) {
		// // TODO Auto-generated method stub
		// super.onProgressUpdate(values);
		// if (values[0] == 1) {
		// if (SQLHelperTotal.isSQLUidTotalOnUsed == false) {
		// SQLHelperTotal.isSQLUidTotalOnUsed = true;
		// uidtraff_UidTotalSQL = true;
		// }
		// showlog("values[0]==1");
		// }
		// }

		@Override
		protected void onPostExecute(Boolean result) {
			View view = null;
			final LinearLayout linearPie = (LinearLayout) findViewById(R.id.new_budget);
			if (result == true) {
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				SQLStatic.setSQLUidTotalOnUsed(false);
				// uidtraff_UidTotalSQL = false;
				BudgetPie budgetPie = new BudgetPie(context, windowswidesize);
				budgetPie.setValues(pieValue);
				view = budgetPie.execute(context);
				TextView tv_mobile = (TextView) findViewById(R.id.tv_mobile);
				TextView tv_wifi = (TextView) findViewById(R.id.tv_wifi);
				UnitHandler unitHandler = new UnitHandler();
				tv_mobile.setText(unitHandler.unitHandlerAccurate(pieValue[0]));
				tv_wifi.setText(unitHandler.unitHandlerAccurate(pieValue[1]));
			} else {
				LayoutInflater factory = LayoutInflater.from(context);
				view = factory.inflate(R.layout.load_fail, null);
				Button btn_reload = (Button) view
						.findViewById(R.id.btn_loading_fail);
				btn_reload.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						linearPie.removeAllViews();
						new AsyncTaskonInitPieChart().execute(context);
					}
				});
			}
			linearPie.removeAllViews();
			linearPie.addView(view);
		}
	}

	private class AsyncTaskonInitProChart extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			uidtraff_UidSQL = false;
			LinearLayout linear = (LinearLayout) findViewById(R.id.new_series);
			LayoutInflater factory = LayoutInflater.from(context);
			View loading = factory.inflate(R.layout.loading_layout, null);
			linear.addView(loading);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			int timetap = 0;
			// while (SQLHelperTotal.isSQLUidOnUsed == true) {
			while (!SQLStatic.setSQLUidOnUsed(true)) {
				try {
					Thread.sleep(300);
					timetap += 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 3) {
					SQLStatic.setSQLUidOnUsed(false);
					return false;
				}
			}
			// onProgressUpdate((long) 1);
			// try {
			// Thread.sleep(20);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// if (uidtraff_UidSQL == false)
			// return false;
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			mobileBefore = new long[64];
			mobileNow = new long[64];
			wifiBefore = new long[64];
			wifiNow = new long[64];
			mobileNow = sqlhelperUid.SelectuidWifiorMobileData(params[0], year,
					month, uidnumber, "mobile");
			wifiNow = sqlhelperUid.SelectuidWifiorMobileData(params[0], year,
					month, uidnumber, "wifi");
			if (month == 1) {
				mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(
						params[0], year - 1, 12, uidnumber, "mobile");
				wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(params[0],
						year - 1, 12, uidnumber, "wifi");
			} else {
				mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(
						params[0], year, month - 1, uidnumber, "mobile");
				wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(params[0],
						year, month - 1, uidnumber, "wifi");
			}
			return true;
		}

		// @Override
		// protected void onProgressUpdate(Long... values) {
		// // TODO Auto-generated method stub
		// super.onProgressUpdate(values);
		// if (values[0] == 1) {
		// if (SQLHelperTotal.isSQLUidOnUsed == false) {
		// SQLHelperTotal.isSQLUidOnUsed = true;
		// uidtraff_UidSQL = true;
		// }
		// showlog("values[0]==1");
		// }
		// }

		@Override
		protected void onPostExecute(Boolean result) {
			View view = null;
			final LinearLayout linear = (LinearLayout) findViewById(R.id.new_series);
			if (result == true) {
				// SQLHelperTotal.isSQLUidOnUsed = false;
				// uidtraff_UidSQL = false;
				SQLStatic.setSQLUidOnUsed(false);
				ProjectStatusChart projectChart = new ProjectStatusChart(
						context, windowswidesize);
				projectChart.initDate(year, month, monthDay);
				projectChart.initData(mobileBefore, mobileNow, wifiBefore,
						wifiNow);
				projectChart.setXaxisText(year + "年");
				view = projectChart.execute(context);
			} else {
				LayoutInflater factory = LayoutInflater.from(context);
				view = factory.inflate(R.layout.load_fail, null);
				Button btn_reload = (Button) view
						.findViewById(R.id.btn_loading_fail);
				btn_reload.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						linear.removeAllViews();
						new AsyncTaskonInitProChart().execute(context);
					}
				});
			}
			linear.removeAllViews();
			linear.addView(view);
		}
	}

	private void initSurface(int uidnumber, String appname, String pkname) {
		// TODO Auto-generated method stub
		PackageManager pm;
		// ApplicationInfo appInfo;
		Drawable appIcon = null;
		pm = context.getPackageManager();
		// appInfo=pm.getApplicationInfo(pkname, PackageManager.GET_META_DATA);
		try {
			appIcon = pm.getApplicationIcon(pkname);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageView imav_app = (ImageView) findViewById(R.id.img_icon);
		TextView tv_appname = (TextView) findViewById(R.id.tv_appname);
		TextView tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		TextView tv_wifi = (TextView) findViewById(R.id.tv_wifi);
		imav_app.setImageDrawable(appIcon);
		tv_appname.setText(appname);
		tv_mobile.setText("加载中..");
		tv_wifi.setText("加载中..");
	}

	// private View initProjectChart(int uidnumber) {
	// SQLHelperUid sqlhelperUid = new SQLHelperUid();
	// ProjectStatusChart projectChart = new ProjectStatusChart(context,
	// windowswidesize);
	// projectChart.initDate(year, month, monthDay);
	// mobileBefore = new long[64];
	// mobileNow = new long[64];
	// wifiBefore = new long[64];
	// wifiNow = new long[64];
	// if (SQLHelperTotal.isSQLUidOnUsed != true) {
	// SQLHelperTotal.isSQLUidOnUsed = true;
	// mobileNow = sqlhelperUid.SelectuidWifiorMobileData(context, year,
	// month, uidnumber, "mobile");
	// wifiNow = sqlhelperUid.SelectuidWifiorMobileData(context, year,
	// month, uidnumber, "wifi");
	// if (month == 1) {
	// mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
	// year - 1, 12, uidnumber, "mobile");
	// wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
	// year - 1, 12, uidnumber, "wifi");
	// } else {
	// mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
	// year, month, uidnumber, "mobile");
	// wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
	// year, month, uidnumber, "wifi");
	// }
	// SQLHelperTotal.isSQLUidOnUsed = false;
	// }
	// projectChart.initData(mobileBefore, mobileNow, wifiBefore, wifiNow);
	// View viewPro = projectChart.execute(this);
	// return viewPro;
	// }
	//
	// private View initPie(int uidnumber) {
	// SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
	// BudgetPie budgetPie = new BudgetPie(context, windowswidesize);
	// if (SQLHelperTotal.isSQLUidOnUsed != true) {
	// SQLHelperTotal.isSQLUidOnUsed = true;
	// pieValue = sqlUidTotal.SelectUidNetData(context, uidnumber);
	// SQLHelperTotal.isSQLUidOnUsed = false;
	// }
	// // if ((pieValue[0] == 0 )&& (pieValue[1] == 0)) {
	// // budgetPie.setValues(new long[] { 1, 1 });
	// // }
	// budgetPie.setValues(pieValue);
	// View viewPie = budgetPie.execute(this);
	// return viewPie;
	// }

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
//		Log.d("UidMonthTraff", string);
	}
}
