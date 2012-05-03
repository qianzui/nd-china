package com.hiapk.dataexe;

import com.hiapk.sqlhelper.SQLHelperTotal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class MonthlyUseData {
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi��mobile����ʹ����
	private long wifi_month_use = 0;

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

	public long getMonthUseData(Context context) {
		long mobile_month_use = 0;
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
		// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
		String MOBILE_COUNT_DAY = "mobileMonthCountDay";
		String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
		String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
		String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
		String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		//
		long[] b = new long[3];
		// long[] a = new long[3];
		// a = sqlhelperTotal.SelectMobileData(context, year, month, monthDay,
		// "10:10:10");
		b = sqlhelperTotal.SelectMobileData(context, year, month, 31, 30);
		//
		// ȡ��ÿ�ܵ�����
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		// ȡ���¶�����
		mobileTraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		//
		mobileTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		//
		wifiTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		// �¶���������
		// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
		// ��������
		/**
		 * ���ñ��µĽ�������
		 */
		int mobilecountDay = (prefs.getInt(MOBILE_COUNT_DAY, 0)+1);
		// ���ý������ڵ�ʱ��
		int mobilecountSetYear = prefs.getInt(MOBILE_COUNT_SET_YEAR, 1977);
		int mobilecountSetMonth = prefs.getInt(MOBILE_COUNT_SET_MONTH, 1);
		/**
		 * ���ñ�����������������
		 */
		int mobilecountSetDay = prefs.getInt(MOBILE_COUNT_SET_DAY, 1);
		String mobilecountSetTime = prefs.getString(MOBILE_COUNT_SET_TIME,
				"00:00:00");
		long[] oneday = new long[3];
		long[] leftday = new long[3];
		// �Ƿ��Ѿ����ù���������
		//���δ�Ա������������������ã���Ĭ��Ϊ�ǵ���
		if ((mobilecountSetYear != 1977) || (mobilecountDay != 1)) {
			// �������������������������ͬ
			if (mobilecountSetDay == mobilecountDay) {
				showlog("SetDay = countDay");
				// �ڵ������õ�һ����ð���
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					oneday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay, mobilecountSetTime);
					leftday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay + 1, mobilecountDay);
					mobile_month_use = oneday[0] + leftday[0];
					showlog("���£����쿪ʼ���¸��½�����");
					showlog(oneday[0] + "oneday");
					showlog(leftday[0] + "leftday");
					showlog(mobilecountSetYear + "��" + mobilecountSetMonth
							+ "yue" + mobilecountSetDay);
					showlog(mobilecountDay + "������");
					showlog(mobilecountSetTime + "");

					// �����������ڻ��߼������ں󼸸���
				} else {
					// ��ǰ���������õĽ�����֮ǰ
					if (monthDay < mobilecountDay) {
						// �жϿ���
						if (month != 1) {
							showlog("�ǵ��£����쵽����½������޿���");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("�ǵ��£����쵽����½����տ���ȥ��12�½����յ�����1�½�����");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// ��ǰ���������õĽ�����֮��
					} else {
						showlog("�ǵ��£����쵽�¸��½�����");
						showlog("monthDay >= mobilecountDay");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}

				}
				// ���������������ڴ��ڽ�������
			} else if (mobilecountSetDay > mobilecountDay) {
				showlog("SetDay > countDay");
				showlog("mobilecountSetDay > mobilecountDay");
				showlog(oneday[0] + "oneday");
				showlog(leftday[0] + "leftday");
				showlog(mobilecountSetYear + "��" + mobilecountSetMonth + "yue"
						+ mobilecountSetDay);
				showlog(mobilecountDay + "������");
				showlog(mobilecountSetTime + "");
				// �Ƿ���
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					showlog("���£����쿪ʼ���¸��½�����");
					oneday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay, mobilecountSetTime);
					leftday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay + 1, mobilecountDay);
					mobile_month_use = oneday[0] + leftday[0];
				} else {
					if (monthDay < mobilecountDay) {
						// �жϿ���
						if (month != 1) {
							showlog("�ǵ��£����쵽����½������޿���");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("�ǵ��£����쵽����½����տ���ȥ��12�½����յ�����1�½�����");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// ��ǰ���������õĽ�����֮��
					} else {
						showlog("�ǵ��£����쵽�¸��½�����");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}
				}
				// ����������������С�ڽ�������
			} else {
				showlog("SetDay < countDay");
				showlog("mobilecountSetDay <= mobilecountDay");
				showlog(oneday[0] + "oneday");
				showlog(leftday[0] + "leftday");
				showlog(mobilecountSetYear + "��" + mobilecountSetMonth + "yue"
						+ mobilecountSetDay);
				showlog(mobilecountDay + "������");
				showlog(mobilecountSetTime + "");
				// �Ƿ���
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					if ((mobilecountSetDay + 1) == mobilecountDay) {
						showlog("���£����쿪ʼ�������");
						oneday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay, mobilecountSetTime);
						mobile_month_use = oneday[0];
					} else {
						showlog("���£����쿪ʼ������½�����");
						oneday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay, mobilecountSetTime);
						leftday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay + 1, mobilecountDay);
						mobile_month_use = oneday[0] + leftday[0];
					}

				} else {
					// ��ǰ���������õĽ�����֮ǰ
					if (monthDay < mobilecountDay) {
						// �жϿ���
						if (month != 1) {
							showlog("�ǵ��£����쵽����½������޿���");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("�ǵ��£����쵽����½����տ���ȥ���12�½����յ�����1�µĽ�����");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// ��ǰ���������õĽ�����֮��
					} else {
						showlog("�ǵ��£����쵽�¸��½�����");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}
				}
			}
			// ��δ���ù���������
		} else {
			showlog("��δ����");
			mobile_month_use = mobileTraffic[0] + mobileTraffic[63];
		}
		return mobile_month_use;
	}

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("MonthlyUseData", string);
	}

}
