package com.hiapk.dataexe;

import android.content.Context;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;

/**
 * ���ڻ�ȡ�����������ݵ�����
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	MobileTraffic mobileTraffic = new MobileTraffic();

	/**
	 * ��ȡ�¶��ƶ�ʹ������
	 * 
	 * @param context
	 * @return
	 */
	public long getMonthUseData(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		long mobile_month_use = 0;
//		MonthlyUseData monthData = new MonthlyUseData();
		mobile_month_use = MonthlyUseData.MonthlyUseTraffic;
		mobile_month_use+=sharedData.getMonthMobileHasUse();
		return mobile_month_use;
	}
	/**
	 * �����¶��ƶ�ʹ��������ֵ(����Ԥ��ҳ��)
	 * 
	 * @param context
	 * @return
	 */
	public void setMonthUseDate(Context context){
		MonthlyUseData monthData = new MonthlyUseData();
		monthData.getMonthUseData(context);
	}

	/**
	 * ȡ��ÿ���ƶ�������
	 * 
	 * @param context
	 * @return ����һ��6λ���顣a[0]Ϊ�ƶ������ܼ�����a[5]Ϊwifi�����ܼ�����
	 *         a[1]-a[2]�ƶ�������ϴ�������������a[3]-a[4]Ϊwifi������ϴ�����������
	 */
	public long[] getMobileWeekTraffic(Context context) {
		long[] weektraffic = new long[6];
		weektraffic = mobileTraffic.getMobileWeekTraffic(context);
		return weektraffic;
	}

	/**
	 * ȡ���¶��ƶ�����
	 * 
	 * @param context
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] getMobileMonthTraffic(Context context) {
		long[] monthtraffic = new long[64];
		monthtraffic = mobileTraffic.getMobileMonthTraffic(context);
		return monthtraffic;
	}

	/**
	 * ȡ���¶�wifi����
	 * 
	 * @param context
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] getWifiMonthTraffic(Context context) {
		WifiTraffiic wifiTraffic = new WifiTraffiic();
		long[] monthtraffic = new long[64];
		monthtraffic = wifiTraffic.getWifiMonthTraffic(context);
		return monthtraffic;
	}

	/**
	 * ��¼wifi��mobile��������
	 * @param context
	 * @param forcerecored
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	public void statsTotalTraffic(Context context, boolean forcerecored) {
		sqlhelperTotal.RecordTotalwritestats(context, forcerecored);
	}
}
