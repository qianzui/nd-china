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
	// ����sharedprefrence
	String PREFS_NAME = "allprefs";
	// ������long
	String VALUE_MOBILE_SET = "mobilemonthuse";
	// ��ʾ��Ԥ��ҳ���int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// ���õ�λ���¶����ã�
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// ��ʹ��������int
	String VALUE_MOBILE_HASUSED_OF_INT = "mobileHasusedint";
	// ���õ�λ����ʹ�ã�
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// ��ʹ��������long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	Context context = this;
	EditText edit;
	TextView tv;
	// ����ʱ��
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
	 * ����������
	 */
	private void init_Spinner() {
		// TODO Auto-generated method stub
		// ��ʼ��
		Spinner spinnerUnit = (Spinner) findViewById(R.id.spinnerUnit);
		Spinner dayUnit = (Spinner) findViewById(R.id.dayUnit);
		Spinner spinnerHasUsed = (Spinner) findViewById(R.id.spinnerhasused);
		// ����Adapter
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
		// �����¶�������������ڵ�Ĭ����ʾֵ
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// 0����mb��1����gb
		int mobileSetUnit = prefs.getInt(MOBILE_SET_UNIT, 0);
		// ��0-30�ֱ����1-31��
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		// ���ó�ʼ��ʾ��Ŀ
		spinnerUnit.setSelection(mobileSetUnit);
		dayUnit.setSelection(mobileSetCountDay);
		// ���ü�����������
		spinnerUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// ���ʱ��¼��������
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
		// ���ü��� �½�����
		dayUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = context.getSharedPreferences(
						PREFS_NAME, 0);
				int beforeSetCount = prefs.getInt(MOBILE_COUNT_DAY, 0);
				if ((beforeSetCount) != position) {
					// �������ڱ仯ʱ�����ڱ仯�����ñ���������ֵ
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
					passfileEditor.commit();// ί�У���������
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
		// ��������ҳ��
		spinnerHasUsed.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// ���ʱ��¼��������
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
					// ��¼����޸���ʹ��������ʱ��
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
	 * �Ա������ý��г�ʼ�����õ�
	 */
	private void init_TextView_HasUsed() {
		// TODO Auto-generated method stub
		final TextView TextView_HasUsed = (TextView) findViewById(R.id.tv_hasused);
		// ����Ĭ����ʾֵ
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileSet = prefs.getInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
		TextView_HasUsed.setText(mobileSet + " ");
		// ���ü���
		TextView_HasUsed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthHasUsed(TextView_HasUsed).show();

			}
		});
	}

	/**
	 * ���¶���ʾ�ı����г�ʼ�����õ�
	 */
	private void init_TextView_month() {
		final TextView TextView_month = (TextView) findViewById(R.id.tv_month);
		// ����Ĭ����ʾֵ
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int mobileSet = prefs.getInt(VALUE_MOBILE_SET_OF_INT, 50);
		TextView_month.setText(mobileSet + " ");
		// ���ü���
		TextView_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthSet(TextView_month).show();

			}
		});
	}

	/**
	 * �¶���ʾ�����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	protected AlertDialog dialogMonthSet(final TextView TextView_month) {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		return new AlertDialog.Builder(Main3.this)
				.setTitle("������ÿ�������޶�")
				.setView(textEntryView)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// �������ֵ
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
						passfileEditor.commit();// ί�У���������
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();

	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	protected AlertDialog dialogMonthHasUsed(final TextView TextView_Used) {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		return new AlertDialog.Builder(Main3.this)
				.setTitle("�����ñ�����������")
				.setView(textEntryView)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// �������ֵ
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
						passfileEditor.commit();// ί�У���������
						commitUsedTrafficTime();
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked cancel so do some stuff */
					}
				}).create();

	}

	public void dialogCombo() {
		new AlertDialog.Builder(this).setTitle("�ֶ���ѯ").setMessage("�ֶ���ѯ�¼�")
				.setPositiveButton("ȷ��", null).show();

	}

	public void dialogMonthWarning() {
		new AlertDialog.Builder(this).setTitle("��Ԥ��").setMessage("��Ԥ���¼�")
				.setPositiveButton("ȷ��", null).show();

	}

	public void dialogDayWarning() {
		// edit = new EditText(Main3.this);
		// edit.setGravity(Gravity.CENTER);
		// final FrameLayout fl = new FrameLayout(null);
		// fl.addView(edit,
		// new FrameLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT)); // ��ĳ��Ļ������
		// edit.setText("Preset Text");
		//
		new AlertDialog.Builder(this).setTitle("��Ԥ��").setMessage("��Ԥ���¼�")
		// .setView(fl)
				.setPositiveButton("ȷ��", null).show();

	}

	public void dialogWarningAct() {
		new AlertDialog.Builder(this).setTitle("Ԥ������").setMessage("Ԥ������")
				.setPositiveButton("ȷ��", null).show();

	}

	public boolean onTouchEvent(MotionEvent event) {

		// float x = event.getX();
		// float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ������Ļʱ��
			// editText_month.clearFocus();
			// ed2.clearFocus();
			// Toast.makeText(getParent(),"x="+x+" y="+y,
			// Toast.LENGTH_LONG).show();
			break;
		// �������ƶ�ʱ��
		case MotionEvent.ACTION_MOVE:

			break;
		// ��ֹ����ʱ��
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
	 * ���ñ������õ���ʵֵ
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
	 * ��ȡʱ��
	 * 
	 * @return ����00:00:00��ʱ��
	 */
	private String gettime() {
		initTime();
		return time;
	}

	/**
	 * ��¼�޸���ʹ��������ʱ��
	 */
	private void commitUsedTrafficTime() {
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// ��¼����޸���ʹ��������ʱ��
		String time = gettime();
		UseEditor.putInt(MOBILE_COUNT_SET_YEAR, year);
		UseEditor.putInt(MOBILE_COUNT_SET_MONTH, month);
		UseEditor.putInt(MOBILE_COUNT_SET_DAY, monthDay);
		UseEditor.putString(MOBILE_COUNT_SET_TIME, time);
		UseEditor.commit();
	}

	/**
	 * ��ʼ��ϵͳʱ��
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
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
