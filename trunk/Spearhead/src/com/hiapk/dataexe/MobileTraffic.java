package com.hiapk.dataexe;

import android.content.Context;
import android.text.format.Time;

import com.hiapk.sqlhelper.SQLHelperTotal;

class MobileTraffic {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// ��ȡ��ϵͳʱ��
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;

	/**
	 * ȡ��ÿ�ܵ�����
	 * 
	 * @param context
	 * @return ����һ��6λ���顣a[0]Ϊ�ƶ������ܼ�����a[5]Ϊwifi�����ܼ�����
	 *         a[1]-a[2]�ƶ�������ϴ�������������a[3]-a[4]Ϊwifi������ϴ�����������
	 */
	long[] getMobileWeekTraffic(Context context) {
		setTime();
		long[] weektraffic = new long[6];
		weektraffic=TrafficManager.mobile_week_data;
//		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
//				monthDay, weekDay);
		return weektraffic;
	}

//	/**
//	 * ȡ���¶�����
//	 * 
//	 * @param context
//	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
//	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
//	 */
//	long[] getMobileMonthTraffic(Context context) {
//		setTime();
//		long[] monthtraffic = new long[64];
//		//specialfortext   --------����
////		monthtraffic = TrafficManager.mobile_month_data;
//		monthtraffic = TrafficManager.wifi_month_data;
//		
////		 monthtraffic = sqlhelperTotal.SelectMobileData(context, year, month);
////		monthtraffic = sqlhelperTotal.SelectWifiData(context, year, month);
//		return monthtraffic;
//	}

	/**
	 * ����ʱ��
	 */
	void setTime() {
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
	}
}
