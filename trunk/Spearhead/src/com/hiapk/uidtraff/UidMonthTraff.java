package com.hiapk.uidtraff;

import com.hiapk.progressbar.BudgetPie;
import com.hiapk.progressbar.ProjectStatusChart;
import com.hiapk.spearhead.R;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;

public class UidMonthTraff extends Activity {
	int year;
	int month;
	int monthDay;
	Context context = this;
	int uidnumber = 10045;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uid_traff);
		Bundle bData = this.getIntent().getExtras();
		uidnumber = bData.getInt("uid");
		initTime();
		initProjectChart();
		initPie();

	}

	private void initProjectChart() {
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		ProjectStatusChart projectChart = new ProjectStatusChart();
		projectChart.initDate(year, month, monthDay);
		long[] mobileBefore = new long[64];
		long[] mobileNow = new long[64];
		long[] wifiBefore = new long[64];
		long[] wifiNow = new long[64];
		if (SQLHelperTotal.isSQLUidOnUsed != true) {
			SQLHelperTotal.isSQLUidOnUsed = true;
			mobileNow = sqlhelperUid.SelectuidWifiorMobileData(context, year,
					month, uidnumber, "mobile");
			wifiNow = sqlhelperUid.SelectuidWifiorMobileData(context, year,
					month, uidnumber, "wifi");
			if (month == 1) {
				mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
						year - 1, 12, uidnumber, "mobile");
				wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
						year - 1, 12, uidnumber, "wifi");
			} else {
				mobileBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
						year, month, uidnumber, "mobile");
				wifiBefore = sqlhelperUid.SelectuidWifiorMobileData(context,
						year, month, uidnumber, "wifi");
			}
			SQLHelperTotal.isSQLUidOnUsed = false;
		}
		projectChart.initData(mobileBefore, mobileNow, wifiBefore, wifiNow);
		View view = projectChart.execute(this);
		LinearLayout linearProject = (LinearLayout) findViewById(R.id.new_series);
		linearProject.addView(view);
	}

	private void initPie() {
		SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
		BudgetPie budgetPie = new BudgetPie();
		long[] pieValue = new long[2];
		if (SQLHelperTotal.isSQLUidOnUsed != true) {
			SQLHelperTotal.isSQLUidOnUsed = true;
			pieValue = sqlUidTotal.SelectUidNetData(context, uidnumber);
			SQLHelperTotal.isSQLUidOnUsed = false;
		}
		if (pieValue[0] == 0 && pieValue[1] == 0) {
			pieValue[1] = 1;
		}
		budgetPie.setValues(pieValue);
		View viewPie = budgetPie.execute(this);

		LinearLayout linearPie = (LinearLayout) findViewById(R.id.new_budget);
		linearPie.addView(viewPie);
	}

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

}
