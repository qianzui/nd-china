package com.hiapk.dataexe;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

/**
 * ���ڻ�ȡ�����������ݵ�����
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	MobileTraffic mobileTraffic = new MobileTraffic();
	public static long mobile_month_use_afterSet = 0;
	public static long[] wifi_month_data = new long[64];
	public static long[] mobile_month_data = new long[64];
	public static long[] mobile_week_data = new long[6];

	/**
	 * ��ȡ�¶��ƶ�ʹ������
	 * 
	 * @param context
	 * @return
	 */
	public static long getMonthUseData(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		long mobile_month_use = 0;
		// statsTotalTraffic(context, false);
		// MonthlyUseData monthData = new MonthlyUseData();
		// mobile_month_use = monthData.getMonthUseData(context);
		mobile_month_use = mobile_month_use_afterSet;
		mobile_month_use += sharedData.getMonthMobileHasUse();
		return mobile_month_use;
	}

	// /**
	// * �����¶��ƶ�ʹ��������ֵ
	// *
	// * @param context
	// * @return
	// */
	// public static void setMonthUseDate(Context context) {
	// MonthlyUseData monthData = new MonthlyUseData();
	// mobile_month_use_afterSet = monthData.getMonthUseData(context);
	// }

	// /**
	// * ����¶��ƶ�ʹ��������ֵ
	// *
	// * @param context
	// * @return
	// */
	// public long countMonthUseDate(Context context) {
	// MonthlyUseData monthData = new MonthlyUseData();
	// long data = monthData.getMonthUseData(context);
	// return data;
	// }

	// /**
	// * ȡ��ÿ���ƶ�������
	// *
	// * @param context
	// * @return ����һ��6λ���顣a[0]Ϊ�ƶ������ܼ�����a[5]Ϊwifi�����ܼ�����
	// * a[1]-a[2]�ƶ�������ϴ�������������a[3]-a[4]Ϊwifi������ϴ�����������
	// */
	// public long[] getMobileWeekTraffic(Context context) {
	// long[] weektraffic = new long[6];
	// weektraffic = mobileTraffic.getMobileWeekTraffic(context);
	// return weektraffic;
	// }

	// /**
	// * ȡ���¶��ƶ�����
	// *
	// * @param context
	// * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	// * a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	// */
	// public long[] getMobileMonthTraffic(Context context) {
	// return mobile_month_data;
	// }

	// /**
	// * ȡ���¶�wifi����
	// *
	// * @param context
	// * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	// * a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	// */
	// public long[] getWifiMonthTraffic(Context context) {
	// WifiTraffiic wifiTraffic = new WifiTraffiic();
	// long[] monthtraffic = new long[64];
	// monthtraffic = wifiTraffic.getWifiMonthTraffic(context);
	// return monthtraffic;
	// }

//	/**
//	 * ��¼wifi��mobile��������
//	 * 
//	 * @param context
//	 * @param forcerecored
//	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0�����ݹ̶�Ϊfalse
//	 */
//	public void statsTotalTraffic(Context context, boolean forcerecored,
//			String network) {
//		if (SQLHelperTotal.isSQLTotalOnUsed != true) {
//			SQLHelperTotal.isSQLTotalOnUsed = true;
//			sqlhelperTotal.RecordTotalwritestats(context, true, network);
//			SQLHelperTotal.isSQLTotalOnUsed = false;
//			showLog("����total��¼�ɹ�");
//		} else {
//			showLog("�������δ���м�¼��¼");
//		}
//	}
//
//	/**
//	 * ��¼wifi��mobile��������
//	 * 
//	 * @param context
//	 * @param forcerecored
//	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0�����ݹ̶�Ϊfalse
//	 */
//	public void statsUidTraffic(Context context, boolean forcerecored,
//			String network) {
//		if (SQLHelperTotal.isSQLUidOnUsed != true) {
//			SQLHelperTotal.isSQLUidOnUsed = true;
//			if (SQLHelperUid.uidnumbers == null) {
//				if (SQLHelperTotal.isSQLIndexOnUsed == false) {
//					SQLHelperTotal.isSQLIndexOnUsed = true;
//					SQLHelperUid.uidnumbers = sqlhelperUid
//							.selectSQLUidnumbers(context);
//					SQLHelperTotal.isSQLIndexOnUsed = false;
//				}
//
//			}
//			if (SQLHelperUid.uidnumbers != null)
//				sqlhelperUid.RecordUidwritestats(context,
//						SQLHelperUid.uidnumbers, true, network);
//			SQLHelperTotal.isSQLUidOnUsed = false;
//			showLog("����uid��¼�ɹ�-traff");
//		} else {
//			showLog("�������δ���м�¼��¼");
//		}
//	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("TrafficManager", string);
	}
}
