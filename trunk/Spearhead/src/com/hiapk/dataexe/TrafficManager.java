package com.hiapk.dataexe;

import android.content.Context;

import com.hiapk.sqlhelper.SQLHelperTotal;

public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();

	/**
	 * ȡ��ÿ�ܵ�����
	 * 
	 * @param context
	 * @param year
	 * @param month
	 * @param monthDay
	 * @param weekDay
	 * @return ����һ��6λ���顣a[0]Ϊ�ƶ������ܼ�����a[5]Ϊwifi�����ܼ�����
	 *         a[1]-a[2]�ƶ�������ϴ�������������a[3]-a[4]Ϊwifi������ϴ�����������
	 */
	public long[] getMobileWeekTraffic(Context context, int year, int month,
			int monthDay, int weekDay) {
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		return weektraffic;
	}

	/**
	 * ȡ���¶�����
	 * 
	 * @param context
	 * @param year
	 * @param month
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] getMobileMonthTraffic(Context context, int year, int month) {
		long[] monthtraffic = new long[64];
//		monthtraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		monthtraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		return monthtraffic;
	}

}
