package com.hiapk.customspinner;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.hiapk.alertdialog.CustomDialog;
import com.hiapk.alertdialog.CustomDialogMain3Been;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomSPBeen {
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
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	// ���õ�λ����ʹ�ã�
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// ��ʹ��������long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	// ����Ԥ��
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// Ԥ������
	String WARNING_ACTION = "warningaction";
	Context context;

	int beforeDay = 0;

	public CustomSPBeen(Context context) {
		this.context = context;
	}

	/**
	 * �������ѷ�ʽ�����ĶԻ���
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogAlertType(final Button btn_Alert) {

		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int beforeAlert = sharedData.getAlertAction();
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.list_spinner_notify, null);
		// ȷ��֮ǰ������İ�ť����Ϊ����
		Button btn_before = findAlertButton(textEntryView, beforeAlert);
		btn_before.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				R.drawable.radiobtn_on, 0);
		final CustomSPDialog notifySet;
		CustomSPDialog.heighpar = 0.45;
		notifySet = new CustomSPDialog.Builder(context).setTitle("��ѡ��Ԥ������")
				.setContentView(textEntryView).setNegativeButton("ȡ��", null)
				.create();
		notifySet.show();
		// ����cancel�ļ���
		Button btn_cancel = (Button) notifySet
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				showlog(sharedData.getCountDay() + "");
				notifySet.dismiss();
			}
		});

		// �����м䰴ť�ļ���
		buttonAlertSetonClick(textEntryView, notifySet, btn_Alert);
	}

	private void buttonAlertSetonClick(final View textEntryView,
			final CustomSPDialog notifySet, final Button mainBtn) {
		// TODO Auto-generated method stub
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final Button btn_day1 = (Button) textEntryView
				.findViewById(R.id.notify_only);
		btn_day1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setAlertAction(0);
				mainBtn.setText("��֪ͨ����ʾ");
				operatorOnAlertClick();
				notifySet.dismiss();
			}
		});

		final Button btn_day2 = (Button) textEntryView
				.findViewById(R.id.notify_vitonly);
		btn_day2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setAlertAction(1);
				mainBtn.setText("����ʾ");
				operatorOnAlertClick();
				notifySet.dismiss();
			}
		});

		final Button btn_day3 = (Button) textEntryView
				.findViewById(R.id.notify_autocut);
		btn_day3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setAlertAction(2);
				mainBtn.setText("�Զ�����");
				operatorOnAlertClick();
				notifySet.dismiss();
			}
		});

		final Button btn_day4 = (Button) textEntryView
				.findViewById(R.id.notify_vit_autocut);
		btn_day4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setAlertAction(3);
				operatorOnAlertClick();
				mainBtn.setText("��+�Զ�����");
				notifySet.dismiss();
			}
		});

	}

	protected void operatorOnAlertClick() {
		// TODO Auto-generated method stub

	}

	private Button findAlertButton(View textEntryView, int beforeAlert) {
		// TODO Auto-generated method stub
		Button btn_before = null;
		switch (beforeAlert) {
		case 0:
			btn_before = (Button) textEntryView.findViewById(R.id.notify_only);
			break;
		case 1:
			btn_before = (Button) textEntryView
					.findViewById(R.id.notify_vitonly);
			break;
		case 2:
			btn_before = (Button) textEntryView
					.findViewById(R.id.notify_autocut);
			break;
		case 3:
			btn_before = (Button) textEntryView
					.findViewById(R.id.notify_vit_autocut);
			break;
		default:
			btn_before = (Button) textEntryView.findViewById(R.id.notify_only);
			break;
		}
		return btn_before;
	}

	/**
	 * �¶Ƚ�������ʾ�����ĶԻ���
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogDaySet(final Button btn_date, Button btn_HasUsed) {

		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		beforeDay = sharedData.getCountDay();
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.list_spinner, null);
		// ȷ��֮ǰ������İ�ť����Ϊ����
		Button btn_before = findButton(textEntryView, beforeDay);
		btn_before.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				R.drawable.radiobtn_on, 0);
		// ����scrollview
		// ScrollView scroll = (ScrollView) textEntryView
		// .findViewById(R.id.scroll_list_spinner);
		// scroll.scrollTo(300, 300);
		// btn_before.scrollTo(0, 300);
		// textEntryView.scrollTo(0, 500);
		// textEntryView.scrollBy(0, 500);
		// ����spinner
		final CustomSPDialog dateSet;
		CustomSPDialog.heighpar = 0.8;
		dateSet = new CustomSPDialog.Builder(context).setTitle("�����ý�������")
				.setContentView(textEntryView).setNegativeButton("ȡ��", null)
				.create();
		dateSet.show();
		// �����м䰴ť�ļ���
		buttonSetonClick(textEntryView, dateSet, btn_date, btn_HasUsed);
		// ����cancel�ļ���
		Button btn_cancel = (Button) dateSet.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				showlog(sharedData.getCountDay() + "");
				dateSet.dismiss();
			}
		});
	}

	private void operatorOnClick(Button btn_HasUsed) {
		// ��������������
		long month_used = TrafficManager.getMonthUseData(context);
		UnitHandler FormatUnit = new UnitHandler();
		btn_HasUsed.setText(FormatUnit.unitHandler(month_used));
		// ���������������������Ի���
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("ע�⣡").setMessage("���ý����պ������¶���������У׼��")
				// .setView(textEntryView)
				.setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
				.create();
		dayWarning.show();
		Button btn_ok = (Button) dayWarning.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthHasUsed();
				dayWarning.dismiss();
			}
		});
		Button btn_cancel = (Button) dayWarning
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dayWarning.dismiss();
			}
		});
		// ˢ��С������֪ͨ��
		// AlarmSet alset = new AlarmSet();
		// alset.StartWidgetAlarm(context);
		SetText.resetWidgetAndNotify(context);
	}

	private void buttonSetonClick(final View textEntryView,
			final CustomSPDialog dateSet, final Button mainBtn,
			final Button btn_HasUsed) {
		// TODO Auto-generated method stub
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final Button btn_day1 = (Button) textEntryView.findViewById(R.id.day1);
		btn_day1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(0);
				mainBtn.setText("1 ��");
				if (beforeDay != 0) {
					if (beforeDay != 0) {
						operatorOnClick(btn_HasUsed);
					}
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day2 = (Button) textEntryView.findViewById(R.id.day2);
		btn_day2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(1);
				mainBtn.setText("2 ��");
				if (beforeDay != 1) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day3 = (Button) textEntryView.findViewById(R.id.day3);
		btn_day3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(2);
				mainBtn.setText("3 ��");
				if (beforeDay != 2) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day4 = (Button) textEntryView.findViewById(R.id.day4);
		btn_day4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(3);
				if (beforeDay != 3) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("4 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day5 = (Button) textEntryView.findViewById(R.id.day5);
		btn_day5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(4);
				mainBtn.setText("5 ��");
				if (beforeDay != 4) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day6 = (Button) textEntryView.findViewById(R.id.day6);
		btn_day6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(5);
				if (beforeDay != 5) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("6 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day7 = (Button) textEntryView.findViewById(R.id.day7);
		btn_day7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(6);
				mainBtn.setText("7 ��");
				if (beforeDay != 6) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day8 = (Button) textEntryView.findViewById(R.id.day8);
		btn_day8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(7);
				if (beforeDay != 7) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("8 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day9 = (Button) textEntryView.findViewById(R.id.day9);
		btn_day9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(8);
				if (beforeDay != 8) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("9 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day10 = (Button) textEntryView
				.findViewById(R.id.day10);
		btn_day10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(9);
				if (beforeDay != 9) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("10 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day11 = (Button) textEntryView
				.findViewById(R.id.day11);
		btn_day11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(10);
				mainBtn.setText("11 ��");
				if (beforeDay != 10) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day12 = (Button) textEntryView
				.findViewById(R.id.day12);
		btn_day12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(11);
				mainBtn.setText("12 ��");
				if (beforeDay != 11) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day13 = (Button) textEntryView
				.findViewById(R.id.day13);
		btn_day13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(12);
				mainBtn.setText("13 ��");
				if (beforeDay != 12) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day14 = (Button) textEntryView
				.findViewById(R.id.day14);
		btn_day14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(13);
				mainBtn.setText("14 ��");
				if (beforeDay != 13) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day15 = (Button) textEntryView
				.findViewById(R.id.day15);
		btn_day15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(14);
				mainBtn.setText("15 ��");
				if (beforeDay != 14) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day16 = (Button) textEntryView
				.findViewById(R.id.day16);
		btn_day16.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(15);
				mainBtn.setText("16 ��");
				if (beforeDay != 15) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day17 = (Button) textEntryView
				.findViewById(R.id.day17);
		btn_day17.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(16);
				mainBtn.setText("17 ��");
				if (beforeDay != 16) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day18 = (Button) textEntryView
				.findViewById(R.id.day18);
		btn_day18.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(17);
				mainBtn.setText("18 ��");
				dateSet.dismiss();
				if (beforeDay != 17) {
					operatorOnClick(btn_HasUsed);
				}
			}
		});

		final Button btn_day19 = (Button) textEntryView
				.findViewById(R.id.day19);
		btn_day19.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(18);
				if (beforeDay != 18) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("19 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day20 = (Button) textEntryView
				.findViewById(R.id.day20);
		btn_day20.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(19);
				if (beforeDay != 19) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("20 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day21 = (Button) textEntryView
				.findViewById(R.id.day21);
		btn_day21.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(20);
				if (beforeDay != 20) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("21 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day22 = (Button) textEntryView
				.findViewById(R.id.day22);
		btn_day22.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(21);
				if (beforeDay != 21) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("22 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day23 = (Button) textEntryView
				.findViewById(R.id.day23);
		btn_day23.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(22);
				mainBtn.setText("23 ��");
				dateSet.dismiss();
				if (beforeDay != 22) {
					operatorOnClick(btn_HasUsed);
				}
			}
		});

		final Button btn_day24 = (Button) textEntryView
				.findViewById(R.id.day24);
		btn_day24.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(23);
				mainBtn.setText("24 ��");
				if (beforeDay != 23) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day25 = (Button) textEntryView
				.findViewById(R.id.day25);
		btn_day25.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(24);
				if (beforeDay != 24) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("25 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day26 = (Button) textEntryView
				.findViewById(R.id.day26);
		btn_day26.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(25);
				mainBtn.setText("26 ��");
				if (beforeDay != 25) {
					operatorOnClick(btn_HasUsed);
				}
				dateSet.dismiss();
			}
		});

		final Button btn_day27 = (Button) textEntryView
				.findViewById(R.id.day27);
		btn_day27.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(26);
				if (beforeDay != 26) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("27 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day28 = (Button) textEntryView
				.findViewById(R.id.day28);
		btn_day28.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(27);
				mainBtn.setText("28 ��");
				dateSet.dismiss();
				if (beforeDay != 27) {
					operatorOnClick(btn_HasUsed);
				}
			}
		});

		final Button btn_day29 = (Button) textEntryView
				.findViewById(R.id.day29);
		btn_day29.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(28);
				if (beforeDay != 28) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("29 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day30 = (Button) textEntryView
				.findViewById(R.id.day30);
		btn_day30.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(29);
				if (beforeDay != 29) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("30 ��");
				dateSet.dismiss();
			}
		});

		final Button btn_day31 = (Button) textEntryView
				.findViewById(R.id.day31);
		btn_day31.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setCountDay(30);
				if (beforeDay != 30) {
					operatorOnClick(btn_HasUsed);
				}
				mainBtn.setText("31 ��");
				dateSet.dismiss();
			}
		});
	}

	// �ҵ�֮ǰ���õĵ�button
	private Button findButton(View textEntryView, int beforeDay) {
		// TODO Auto-generated method stub
		Button btn_before = null;
		switch (beforeDay) {
		case 0:
			btn_before = (Button) textEntryView.findViewById(R.id.day1);
			break;
		case 1:
			btn_before = (Button) textEntryView.findViewById(R.id.day2);
			break;
		case 2:
			btn_before = (Button) textEntryView.findViewById(R.id.day3);
			break;
		case 3:
			btn_before = (Button) textEntryView.findViewById(R.id.day4);
			break;
		case 4:
			btn_before = (Button) textEntryView.findViewById(R.id.day5);
			break;
		case 5:
			btn_before = (Button) textEntryView.findViewById(R.id.day6);
			break;
		case 6:
			btn_before = (Button) textEntryView.findViewById(R.id.day7);
			break;
		case 7:
			btn_before = (Button) textEntryView.findViewById(R.id.day8);
			break;
		case 8:
			btn_before = (Button) textEntryView.findViewById(R.id.day9);
			break;
		case 9:
			btn_before = (Button) textEntryView.findViewById(R.id.day10);
			break;
		case 10:
			btn_before = (Button) textEntryView.findViewById(R.id.day11);
			break;
		case 11:
			btn_before = (Button) textEntryView.findViewById(R.id.day12);
			break;
		case 12:
			btn_before = (Button) textEntryView.findViewById(R.id.day13);
			break;
		case 13:
			btn_before = (Button) textEntryView.findViewById(R.id.day14);
			break;
		case 14:
			btn_before = (Button) textEntryView.findViewById(R.id.day15);
			break;
		case 15:
			btn_before = (Button) textEntryView.findViewById(R.id.day16);
			break;
		case 16:
			btn_before = (Button) textEntryView.findViewById(R.id.day17);
			break;
		case 17:
			btn_before = (Button) textEntryView.findViewById(R.id.day18);
			break;
		case 18:
			btn_before = (Button) textEntryView.findViewById(R.id.day19);
			break;
		case 19:
			btn_before = (Button) textEntryView.findViewById(R.id.day20);
			break;
		case 20:
			btn_before = (Button) textEntryView.findViewById(R.id.day21);
			break;
		case 21:
			btn_before = (Button) textEntryView.findViewById(R.id.day22);
			break;
		case 22:
			btn_before = (Button) textEntryView.findViewById(R.id.day23);
			break;
		case 23:
			btn_before = (Button) textEntryView.findViewById(R.id.day24);
			break;
		case 24:
			btn_before = (Button) textEntryView.findViewById(R.id.day25);
			break;
		case 25:
			btn_before = (Button) textEntryView.findViewById(R.id.day26);
			break;
		case 26:
			btn_before = (Button) textEntryView.findViewById(R.id.day27);
			break;
		case 27:
			btn_before = (Button) textEntryView.findViewById(R.id.day28);
			break;
		case 28:
			btn_before = (Button) textEntryView.findViewById(R.id.day29);
			break;
		case 29:
			btn_before = (Button) textEntryView.findViewById(R.id.day30);
			break;
		case 30:
			btn_before = (Button) textEntryView.findViewById(R.id.day31);
			break;

		default:
			btn_before = (Button) textEntryView.findViewById(R.id.day1);
			break;
		}
		return btn_before;
	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	private void dialogMonthHasUsed() {
		// TODO Auto-generated method stub
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final DecimalFormat format = new DecimalFormat("0.##");
		int mobileUseUnit = sharedData.getMonthHasUsedUnit();
		// float mobileUsefloat = sharedData.getMonthMobileHasUseOffloat();
		long mobileUselong = TrafficManager.getMonthUseData(context);
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_dialog_on_main_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		final Spinner spin_unit = (Spinner) textEntryView
				.findViewById(R.id.sp_unit);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(
				context, R.array.unit, R.layout.sptext_on_alert);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_unit.setAdapter(adp);
		// ��ʼ����ֵ
		spin_unit.setSelection(mobileUseUnit);
		int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
		// ����ֵΪ0������ʾ��
		if (mobileUselong != 0) {
			String mobileUseString;
			if (mobileHasUsedUnit == 0) {
				mobileUseString = format
						.format(((double) mobileUselong) / 1024 / 1024);
			} else {
				mobileUseString = format
						.format(((double) mobileUselong) / 1024 / 1024 / 1024);
			}

			et_month.setText(mobileUseString);
			et_month.setSelection(String.valueOf(mobileUseString).length());
		} else {
			et_month.setText("");
		}

		final CustomDialog monthHasUsedAlert = new CustomDialog.Builder(context)
				.setTitle("�����ñ�����������").setContentView(textEntryView)
				.setOtherButton("���Ų�ѯ", null).setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null).create();
		monthHasUsedAlert.show();

		Button btn_other = (Button) monthHasUsedAlert
				.findViewById(R.id.otherButton);
		btn_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();
				Intent intent = new Intent(context, Regulate.class);
				context.startActivity(intent);

			}
		});
		Button btn_ok = (Button) monthHasUsedAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// �������ֵ
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					// TODO: handle exception
					showlog(i + "shuziError" + et_month.getText().toString());
				}

				// btn_Used.setText(format.format(i));
				int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				// Log.d("main3", i + "");
				//
				//

				if (mobileHasUsedUnit == 0) {
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
							(long) (i * 1048576));
				} else {
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
							(long) (i * 1048576 * 1024));
				}
				passfileEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, i);

				passfileEditor.putInt(MOBILE_HASUSED_SET_UNIT,
						mobileHasUsedUnit);
				passfileEditor.commit();// ί�У���������
				// commitUsedTrafficTime();
				// init_btn_HasUsed();
				/* User clicked OK so do some stuff */
				long hasusedlong = sharedData.getMonthMobileHasUse();
				long setlong = sharedData.getMonthMobileSetOfLong();
				if (hasusedlong > setlong) {
					dialogHasUsedLongTooMuch();
				}
				SetText.resetWidgetAndNotify(context);
				PrefrenceOperatorUnit.resetHasWarning(context);
				// ����Ի���
				monthHasUsedAlert.dismiss();
			}
		});
		Button btn_cancel = (Button) monthHasUsedAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();
			}
		});

	}

	/**
	 * ���õı�����������������������
	 * 
	 * @return
	 */
	public void dialogHasUsedLongTooMuch() {
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("ע�⣡").setMessage("�����õı�������������������������")
				// .setView(textEntryView)
				.setPositiveButton("ȷ��", null).create();
		dayWarning.show();
		Button btn_cancel = (Button) dayWarning
				.findViewById(R.id.positiveButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PrefrenceOperatorUnit.resetHasWarning(context);
				dayWarning.dismiss();
			}
		});

	}

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("CustomSPBeen", string);
	}
}
