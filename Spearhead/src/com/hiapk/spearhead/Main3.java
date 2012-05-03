package com.hiapk.spearhead;

import java.text.DecimalFormat;

import com.hiapk.dataexe.MonthlyUseData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Main3 extends Activity {
	Button combo;
	Button monthWarning;
	Button dayWarning;
	Button warningAct;
	EditText ed2;
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 总流量long
	String VALUE_MOBILE_SET = "mobilemonthuse";
	// 显示在预警页面的int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// 已使用总流量int
	String VALUE_MOBILE_HASUSED_OF_INT = "mobileHasusedint";
	// 设置单位（已使用）
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// 已使用总流量long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	Context context = this;
	EditText edit;
	TextView tv;
	// 设置时间
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main3);
		init_TextView_month();
		init_TextView_HasUsed();
		init_Spinner();
		combo = (Button) findViewById(R.id.combo);
		monthWarning = (Button) findViewById(R.id.monthWarning);
		dayWarning = (Button) findViewById(R.id.dayWarning);
		warningAct = (Button) findViewById(R.id.warningAct);
		combo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogCombo();

			}
		});
		monthWarning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthWarning();

			}
		});

		dayWarning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// dialogDayWarning();
				// dialogMonthSet().show();

			}
		});

		warningAct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogWarningAct();

			}
		});

	}

	/**
	 * 设置下拉条
	 */
	private void init_Spinner() {
		// TODO Auto-generated method stub
		// 初始化
		Spinner spinnerUnit = (Spinner) findViewById(R.id.spinnerUnit);
		Spinner dayUnit = (Spinner) findViewById(R.id.dayUnit);
		Spinner spinnerHasUsed = (Spinner) findViewById(R.id.spinnerhasused);
		// 设置Adapter
		ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(this,
				R.array.unit, R.layout.sptext);
		ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(this,
				R.array.day, R.layout.sptext);
		ArrayAdapter<CharSequence> adp3 = ArrayAdapter.createFromResource(this,
				R.array.unit, R.layout.sptext);
		adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerUnit.setAdapter(adp1);
		dayUnit.setAdapter(adp2);
		spinnerHasUsed.setAdapter(adp3);
		// 设置月度流量与结算日期的默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// 0代表mb，1代表gb
		int mobileSetUnit = prefs.getInt(MOBILE_SET_UNIT, 0);
		// 从0-30分别代表1-31日
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		// 设置初始显示项目
		spinnerUnit.setSelection(mobileSetUnit);
		dayUnit.setSelection(mobileSetCountDay);
		// 设置监听包月流量
		spinnerUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// 点击时记录流量数据
				SharedPreferences prefs = context.getSharedPreferences(
						PREFS_NAME, 0);
				int mobileValue = prefs.getInt(VALUE_MOBILE_SET_OF_INT, 50);
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				if (position == 0) {
					passfileEditor.putLong(VALUE_MOBILE_SET,
							(long) mobileValue * 1048576);
				} else {
					passfileEditor.putLong(VALUE_MOBILE_SET,
							(long) mobileValue * 1048576 * 1024);
				}
				passfileEditor.putInt(MOBILE_SET_UNIT, position);
				passfileEditor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				// Log.d("main3", "nono");
			}
		});
		// 设置监听 月结算日
		dayUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = context.getSharedPreferences(
						PREFS_NAME, 0);
				int beforeSetCount = prefs.getInt(MOBILE_COUNT_DAY, 0);
				if ((beforeSetCount) != position) {
					// 结算日期变化时做日期变化并重置本月已用数值
					Editor passfileEditor = context.getSharedPreferences(
							PREFS_NAME, 0).edit();
					// Log.d("main3", i + "");
					// String time=gettime();
					passfileEditor.putInt(MOBILE_COUNT_DAY, position);
					// passfileEditor.putInt(MOBILE_COUNT_SET_YEAR, year);
					// passfileEditor.putInt(MOBILE_COUNT_SET_MONTH, month);
					// passfileEditor.putInt(MOBILE_COUNT_SET_DAY, monthDay);
					// passfileEditor.putString(MOBILE_COUNT_SET_TIME, time);
					//
					// Log.d("main3", i + "");
					passfileEditor.putInt(MOBILE_COUNT_SET_YEAR, 1977);
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG, 0);
					passfileEditor.putInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
					passfileEditor.commit();// 委托，存入数据
					MonthlyUseData monthlyUse = new MonthlyUseData();
					long hasuseTraffic = monthlyUse.getMonthUseData(context);
					setHasUsedTextView(hasuseTraffic);
					//

				}

				// Log.d("main3", ((TextView) arg1).getText() + "");
				// Log.d("main3", arg2 + "");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				// Log.d("main3", "nono");
			}
		});
		// 本月已用页面
		spinnerHasUsed.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// 点击时记录流量数据
				SharedPreferences prefs = context.getSharedPreferences(
						PREFS_NAME, 0);
				int beforeUnit = prefs.getInt(MOBILE_HASUSED_SET_UNIT, 0);
				if (beforeUnit != position) {
					int hasUsedValueInt = prefs.getInt(
							VALUE_MOBILE_HASUSED_OF_INT, 0);
					Editor passfileEditor = context.getSharedPreferences(
							PREFS_NAME, 0).edit();
					if (position == 0) {
						passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
								(long) hasUsedValueInt * 1048576);
					} else {
						passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
								(long) hasUsedValueInt * 1048576 * 1024);
					}
					passfileEditor.putInt(MOBILE_HASUSED_SET_UNIT, position);
					// 记录点击修改已使用流量的时间
					passfileEditor.commit();
					commitUsedTrafficTime();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				// Log.d("main3", "nono");
			}
		});
	}

	/**
	 * 对本月已用进行初始化设置等
	 */
	private void init_TextView_HasUsed() {
		// TODO Auto-generated method stub
		final TextView TextView_HasUsed = (TextView) findViewById(R.id.tv_hasused);
		// 设置默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileSet = prefs.getInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
		TextView_HasUsed.setText(mobileSet + " ");
		// 设置监听
		TextView_HasUsed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthHasUsed(TextView_HasUsed).show();

			}
		});
	}

	/**
	 * 对月度显示文本进行初始化设置等
	 */
	private void init_TextView_month() {
		final TextView TextView_month = (TextView) findViewById(R.id.tv_month);
		// 设置默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileSet = prefs.getInt(VALUE_MOBILE_SET_OF_INT, 50);
		TextView_month.setText(mobileSet + " ");
		// 设置监听
		TextView_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthSet(TextView_month).show();

			}
		});
	}

	/**
	 * 月度显示弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	protected AlertDialog dialogMonthSet(final TextView TextView_month) {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		return new AlertDialog.Builder(Main3.this)
				.setTitle("请设置每月流量限额")
				.setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// 输入的数值
						int i = 0;
						try {
							i = Integer.valueOf(et_month.getText().toString());
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						TextView_month.setText(String.valueOf(i) + " ");
						SharedPreferences prefs = context.getSharedPreferences(
								PREFS_NAME, 0);
						int mobileUnit = prefs.getInt(MOBILE_SET_UNIT, 0);
						Editor passfileEditor = context.getSharedPreferences(
								PREFS_NAME, 0).edit();
						// Log.d("main3", i + "");
						if (mobileUnit == 0) {
							passfileEditor.putLong(VALUE_MOBILE_SET,
									(long) i * 1048576);
						} else {
							passfileEditor.putLong(VALUE_MOBILE_SET,
									(long) i * 1048576 * 1024);
						}
						passfileEditor.putInt(VALUE_MOBILE_SET_OF_INT, i);
						passfileEditor.commit();// 委托，存入数据
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();

	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	protected AlertDialog dialogMonthHasUsed(final TextView TextView_Used) {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		return new AlertDialog.Builder(Main3.this)
				.setTitle("请设置本月已用流量")
				.setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// 输入的数值
						int i = 0;
						try {
							i = Integer.valueOf(et_month.getText().toString());
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						TextView_Used.setText(String.valueOf(i) + " ");
						SharedPreferences prefs = context.getSharedPreferences(
								PREFS_NAME, 0);
						int mobileHasUsedUnit = prefs.getInt(
								MOBILE_HASUSED_SET_UNIT, 0);
						Editor passfileEditor = context.getSharedPreferences(
								PREFS_NAME, 0).edit();
						// Log.d("main3", i + "");
						if (mobileHasUsedUnit == 0) {
							passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
									(long) i * 1048576);
						} else {
							passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
									(long) i * 1048576 * 1024);
						}
						passfileEditor.putInt(VALUE_MOBILE_HASUSED_OF_INT, i);
						passfileEditor.commit();// 委托，存入数据
						commitUsedTrafficTime();
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();

	}

	public void dialogCombo() {
		new AlertDialog.Builder(this).setTitle("手动查询").setMessage("手动查询事件")
				.setPositiveButton("确定", null).show();

	}

	public void dialogMonthWarning() {
		new AlertDialog.Builder(this).setTitle("月预警").setMessage("月预警事件")
				.setPositiveButton("确定", null).show();

	}

	public void dialogDayWarning() {
		// edit = new EditText(Main3.this);
		// edit.setGravity(Gravity.CENTER);
		// final FrameLayout fl = new FrameLayout(null);
		// fl.addView(edit,
		// new FrameLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT)); // 给某屏幕添加组件
		// edit.setText("Preset Text");
		//
		new AlertDialog.Builder(this).setTitle("日预警").setMessage("日预警事件")
		// .setView(fl)
				.setPositiveButton("确定", null).show();

	}

	public void dialogWarningAct() {
		new AlertDialog.Builder(this).setTitle("预警动作").setMessage("预警动作")
				.setPositiveButton("确定", null).show();

	}

	public boolean onTouchEvent(MotionEvent event) {

		// float x = event.getX();
		// float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 触摸屏幕时刻
			// editText_month.clearFocus();
			// ed2.clearFocus();
			// Toast.makeText(getParent(),"x="+x+" y="+y,
			// Toast.LENGTH_LONG).show();
			break;
		// 触摸并移动时刻
		case MotionEvent.ACTION_MOVE:

			break;
		// 终止触摸时刻
		case MotionEvent.ACTION_UP:
			break;
		}

		return true;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		long hasuseTraffic = Main.mobile_month_use;
		setHasUsedTextView(hasuseTraffic);

	}

	/**
	 * 设置本月已用的现实值
	 */
	private void setHasUsedTextView(long hasuseTraffic) {
		Spinner spinnerHasUsed = (Spinner) findViewById(R.id.spinnerhasused);
		TextView TextView_hseUsed = (TextView) findViewById(R.id.tv_hasused);
		float hasuseshow = (float) hasuseTraffic / 1024 / 1024;
		Log.d("main3", hasuseshow + "");
		DecimalFormat format = new DecimalFormat("0.##");
		// value = format.format(floatnum) + "";
		if ((hasuseTraffic = hasuseTraffic / 1024 / 1024) < 1) {
			TextView_hseUsed.setText("<1");
			spinnerHasUsed.setSelection(0);
		} else if ((hasuseTraffic / 1024) < 1) {
			// hasuseTraffic=hasuseTraffic / 1024;
			TextView_hseUsed.setText(format.format(hasuseshow) + "");
			spinnerHasUsed.setSelection(0);
		} else {
			hasuseshow = hasuseshow / 1024;
			Log.d("main3", hasuseshow + "");
			TextView_hseUsed.setText(format.format(hasuseshow) + "");
			spinnerHasUsed.setSelection(1);
		}
	}

	/**
	 * 获取时间
	 * 
	 * @return 返回00:00:00的时间
	 */
	private String gettime() {
		initTime();
		return time;
	}

	/**
	 * 记录修改已使用流量的时间
	 */
	private void commitUsedTrafficTime() {
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// 记录点击修改已使用流量的时间
		String time = gettime();
		UseEditor.putInt(MOBILE_COUNT_SET_YEAR, year);
		UseEditor.putInt(MOBILE_COUNT_SET_MONTH, month);
		UseEditor.putInt(MOBILE_COUNT_SET_DAY, monthDay);
		UseEditor.putString(MOBILE_COUNT_SET_TIME, time);
		UseEditor.commit();
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
		hour = t.hour; // 0-23
		minute = t.minute;
		second = t.second;
		String hour2 = hour + "";
		String minute2 = minute + "";
		String second2 = second + "";
		if (hour < 10)
			hour2 = "0" + hour2;
		if (minute < 10)
			minute2 = "0" + minute2;
		if (second < 10)
			second2 = "0" + second2;
		time = hour2 + ":" + minute2 + ":" + second2;
	}
}
