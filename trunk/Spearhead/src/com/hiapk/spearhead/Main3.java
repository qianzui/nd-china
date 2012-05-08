package com.hiapk.spearhead;

import com.hiapk.alertaction.AlertActionNotify;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.regulate.Regulate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Main3 extends Activity {
	Button combo;
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
	// 流量预警
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	String WARNING_ACTION = "warningaction";
	Context context = this;
	EditText edit;
	TextView tv;
	// 预警的标识
	boolean flagWarning = false;
	// 设置时间
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String time;
	// 调用其他函数
	UnitHandler FormatUnit = new UnitHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		init_Spinner();
		init_btn_month();
		init_monthWarning();
		init_dayWarning();
		init_warningAct();
		combo = (Button) findViewById(R.id.combo);
		combo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Main3.this, Regulate.class);
				startActivity(it);
			}
		});

	}

	/**
	 * 初始化预警动作按钮
	 */
	private void init_warningAct() {
		// TODO Auto-generated method stub
		Spinner warningAct = (Spinner) findViewById(R.id.warningAct);
		// 设置Adapter
		ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(this,
				R.array.warningaction, R.layout.sptext);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		warningAct.setAdapter(adp2);
		// 初始化数值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		final int beforeWarningAction = prefs.getInt(WARNING_ACTION, 0);
		warningAct.setSelection(beforeWarningAction);
		warningAct.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if ((beforeWarningAction) != position) {
					// 结算日期变化时做日期变化并重置本月已用数值
					Editor passfileEditor = context.getSharedPreferences(
							PREFS_NAME, 0).edit();
					// Log.d("main3", i + "");
					passfileEditor.putInt(WARNING_ACTION, position);
					passfileEditor.commit();// 委托，存入数据
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 初始化日流量预警按钮
	 */
	private void init_dayWarning() {
		// TODO Auto-generated method stub
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileWarning = prefs.getLong(MOBILE_WARNING_DAY, 5 * 1024 * 1024);
		// float a=Float.valueOf(mobileWarning).floatValue();
		dayWarning.setText(FormatUnit.unitHandler(mobileWarning));
		dayWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogDayWarning(dayWarning).show();

			}
		});
	}

	/**
	 * 初始化月流量预警按钮
	 */
	private void init_monthWarning() {
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileWarning = prefs.getLong(MOBILE_WARNING_MONTH,
				45 * 1024 * 1024);
		// float a=Float.valueOf(mobileWarning).floatValue();
		monthWarning.setText(FormatUnit.unitHandler(mobileWarning));
		monthWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthWarning(monthWarning).show();

			}
		});
	}

	/**
	 * 设置下拉条
	 */
	private void init_Spinner() {
		// TODO Auto-generated method stub
		// 初始化
		// Spinner spinnerUnit = (Spinner) findViewById(R.id.spinnerUnit);
		Spinner dayUnit = (Spinner) findViewById(R.id.dayUnit);
		// Spinner spinnerHasUsed = (Spinner) findViewById(R.id.spinnerhasused);
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
		// spinnerUnit.setAdapter(adp1);
		dayUnit.setAdapter(adp2);
		// spinnerHasUsed.setAdapter(adp3);
		// 设置月度流量与结算日期的默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// 从0-30分别代表1-31日
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		// 设置初始显示项目
		// spinnerUnit.setSelection(mobileSetUnit);
		dayUnit.setSelection(mobileSetCountDay);
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
					passfileEditor.putInt(MOBILE_COUNT_DAY, position);
					// Log.d("main3", i + "");
					passfileEditor.putInt(MOBILE_COUNT_SET_YEAR, 1977);
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG, 0);
					passfileEditor.putInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
					passfileEditor.commit();// 委托，存入数据
					init_btn_HasUsed();

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 对本月已用进行初始化设置等
	 */
	private void init_btn_HasUsed() {
		// TODO Auto-generated method stub
		final Button btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		// 设置默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// 设置的使用值
		long mobileUsedSet = prefs.getLong(VALUE_MOBILE_HASUSED_LONG, 0);
		MonthlyUseData monthData = new MonthlyUseData();
		// 计算出来的设置数值之后计算出来的使用量
		long month_used = monthData.getMonthUseData(context);
		showlog(mobileUsedSet + "");
		showlog(month_used + "");
		btn_HasUsed.setText(FormatUnit.unitHandler(mobileUsedSet + month_used));
		// 设置监听
		btn_HasUsed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthHasUsed(btn_HasUsed).show();

			}
		});
	}

	/**
	 * 对月度显示文本进行初始化设置等
	 */
	private void init_btn_month() {
		final Button btn_month = (Button) findViewById(R.id.btn_monthSet_Unit);
		// 设置默认显示值
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long mobileSetLong = prefs.getLong(VALUE_MOBILE_SET, 50 * 1024 * 1024);
		showlog(mobileSetLong + "");
		btn_month.setText(FormatUnit.unitHandler(mobileSetLong));
		// 设置监听
		btn_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthSet(btn_month).show();

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
	protected AlertDialog dialogMonthSet(final Button btn_month) {
		// TODO Auto-generated method stub
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileUnit = prefs.getInt(MOBILE_SET_UNIT, 0);
		int mobileSetInt = prefs.getInt(VALUE_MOBILE_SET_OF_INT, 50);
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		final Spinner spin_unit = (Spinner) textEntryView
				.findViewById(R.id.sp_unit);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,
				R.array.unit, R.layout.sptext);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_unit.setAdapter(adp);
		// 初始化数值
		spin_unit.setSelection(mobileUnit);
		et_month.setText(mobileSetInt + "");
		et_month.setSelection(String.valueOf(mobileSetInt).length());

		AlertDialog monthSetAlert = new AlertDialog.Builder(Main3.this)
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
						showlog(i + "");
						int mobileUnit = spin_unit.getSelectedItemPosition();
						Editor passfileEditor = context.getSharedPreferences(
								PREFS_NAME, 0).edit();
						// Log.d("main3", i + "");

						if (mobileUnit == 0) {
							long monthsetTraffMB = (long) i * 1024 * 1024;
							showlog(monthsetTraffMB + "");
							passfileEditor.putLong(VALUE_MOBILE_SET,
									monthsetTraffMB);
							passfileEditor.putLong(MOBILE_WARNING_MONTH,
									monthsetTraffMB * 9 / 10);
							passfileEditor.putLong(MOBILE_WARNING_DAY,
									monthsetTraffMB / 10);
						} else if (mobileUnit == 1) {
							long monthsetTraffGB = (long) i * 1024 * 1024 * 1024;
							showlog(monthsetTraffGB + "");
							passfileEditor.putLong(VALUE_MOBILE_SET,
									monthsetTraffGB);
							passfileEditor.putLong(MOBILE_WARNING_MONTH,
									monthsetTraffGB * 9 / 10);
							passfileEditor.putLong(MOBILE_WARNING_DAY,
									monthsetTraffGB / 10);
						}
						passfileEditor.putInt(MOBILE_SET_UNIT, mobileUnit);
						passfileEditor.putInt(VALUE_MOBILE_SET_OF_INT, i);
						passfileEditor.commit();// 委托，存入数据
						init_btn_month();
						init_dayWarning();
						init_monthWarning();
						// 重置预警状态
						resetHasWarning();
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();
		return monthSetAlert;

	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	protected AlertDialog dialogMonthHasUsed(final Button btn_Used) {
		// TODO Auto-generated method stub
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileUseUnit = prefs.getInt(MOBILE_HASUSED_SET_UNIT, 0);
		int mobileUseInt = prefs.getInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		final Spinner spin_unit = (Spinner) textEntryView
				.findViewById(R.id.sp_unit);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,
				R.array.unit, R.layout.sptext);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_unit.setAdapter(adp);
		// 初始化数值
		spin_unit.setSelection(mobileUseUnit);
		et_month.setText(mobileUseInt + "");
		et_month.setSelection(String.valueOf(mobileUseInt).length());

		AlertDialog monthHasUsedAlert = new AlertDialog.Builder(Main3.this)
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
						btn_Used.setText(String.valueOf(i) + " ");
						int mobileHasUsedUnit = spin_unit
								.getSelectedItemPosition();
						Editor passfileEditor = context.getSharedPreferences(
								PREFS_NAME, 0).edit();
						// Log.d("main3", i + "");
						//

						//

						if (mobileHasUsedUnit == 0) {
							passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
									(long) i * 1048576);
						} else {
							passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
									(long) i * 1048576 * 1024);
						}
						passfileEditor.putInt(VALUE_MOBILE_HASUSED_OF_INT, i);

						passfileEditor.putInt(MOBILE_HASUSED_SET_UNIT,
								mobileHasUsedUnit);
						passfileEditor.commit();// 委托，存入数据
						commitUsedTrafficTime();
						init_btn_HasUsed();

						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();
		return monthHasUsedAlert;

	}

	View textEntryView;

	/**
	 * 月度预警弹出的对话框
	 */
	public AlertDialog dialogMonthWarning(Button button) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		textEntryView = factory.inflate(
				R.layout.month_warning_set_alert_dialog, null);
		// 流量预警设置窗口上方的文本
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		tv_month_Traff.setTextSize(20);
		tv_month_Traff.setTextColor(Color.BLACK);
		// tv_month_warning.setText("月度预警流量：");
		// tv_month_warning.setGravity(Gravity.CENTER);
		// 设置拖动进度条
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// 包月流量
		final long monthset = prefs.getLong(VALUE_MOBILE_SET, 50 * 1024 * 1024);
		// final int monthset_MB = (int) (monthset / 1024 / 1024);
		long warningMonthset = prefs.getLong(MOBILE_WARNING_MONTH,
				45 * 1024 * 1024);
		if (monthset != 0) {
			// 进行初始化
			// 流量数值前方的说明文字
			final String text = "";
			seekbar_warning
					.setProgress((int) (warningMonthset * 100 / monthset));
			tv_month_Traff.setText(text
					+ FormatUnit.unitHandler(warningMonthset));
			seekbar_warning
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							// TODO Auto-generated method stub
							tv_month_Traff.setText(text
									+ FormatUnit.unitHandler(monthset
											* progress / 100));
						}
					});
			AlertDialog monthWarning = new AlertDialog.Builder(Main3.this)
					.setTitle("每月流量达到下列数值时，自动报警")
					.setView(textEntryView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Editor UseEditor = context
											.getSharedPreferences(PREFS_NAME, 0)
											.edit();
									int progre = seekbar_warning.getProgress();
									long newmonthset = monthset * progre / 100;
									// 最小值1M
									UseEditor.putLong(MOBILE_WARNING_MONTH,
											newmonthset);
									UseEditor.commit();
									init_monthWarning();
									// 重置预警状态
									resetHasWarning();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();
			return monthWarning;
		} else {
			AlertDialog monthWarning = new AlertDialog.Builder(Main3.this)
					.setTitle("请进行包月流量设置")
					// .setView(textEntryView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
			return monthWarning;
		}

	}

	public void dialogCombo() {
		new AlertDialog.Builder(this).setTitle("手动查询").setMessage("手动查询事件")
				.setPositiveButton("确定", null).show();

	}

	/**
	 * 日流量预警对话框
	 * 
	 * @param button
	 * @return
	 */
	public AlertDialog dialogDayWarning(Button button) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		textEntryView = factory.inflate(
				R.layout.month_warning_set_alert_dialog, null);
		// final TextView tv_day_warning = (TextView) textEntryView
		// .findViewById(R.id.tv_warning_alert);
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		tv_month_Traff.setTextSize(20);
		tv_month_Traff.setTextColor(Color.BLACK);
		// tv_day_warning.setText("日预警流量：");
		// tv_day_warning.setGravity(Gravity.CENTER);
		// 设置拖动进度条
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// 包月流量
		final long dayset = prefs.getLong(VALUE_MOBILE_SET, 50 * 1024 * 1024);
		// final int dayset_MB = (int) (dayset / 1024 / 1024);
		long warningDayset = prefs.getLong(MOBILE_WARNING_DAY, 5 * 1024 * 1024);
		final long monthset = prefs.getLong(VALUE_MOBILE_SET, 50 * 1024 * 1024);
		if (monthset != 0) {
			// 进行初始化
			final String text = "";
			seekbar_warning.setProgress((int) (warningDayset * 100 / dayset));
			tv_month_Traff
					.setText(text + FormatUnit.unitHandler(warningDayset));
			seekbar_warning
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							// TODO Auto-generated method stub
							tv_month_Traff.setText(text
									+ FormatUnit.unitHandler(progress * dayset
											/ 100));
						}
					});
			AlertDialog dayWarning = new AlertDialog.Builder(Main3.this)
					.setTitle("当天流量达到下列数值时，自动报警")
					.setView(textEntryView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Editor UseEditor = context
											.getSharedPreferences(PREFS_NAME, 0)
											.edit();
									int progre = seekbar_warning.getProgress();
									// 最小值1M
									long newdayset = dayset * progre / 100;
									UseEditor.putLong(MOBILE_WARNING_DAY,
											newdayset);
									UseEditor.commit();
									init_dayWarning();
									// 重置预警状态
									resetHasWarning();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();
			return dayWarning;
		} else {
			AlertDialog monthWarning = new AlertDialog.Builder(Main3.this)
					.setTitle("请进行包月流量设置")
					// .setView(textEntryView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
			return monthWarning;
		}

	}

	public void dialogWarningAct() {
		new AlertDialog.Builder(this).setTitle("预警动作").setMessage("预警动作")
				.setPositiveButton("确定", null).show();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init_btn_HasUsed();

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

	/**
	 * 重置预警状态
	 */
	private void resetHasWarning() {
		String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
		String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, false);
		UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, false);
		UseEditor.commit();
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("main3", string);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
