package com.hiapk.control.traff;

import com.hiapk.util.SharedPrefrenceData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��ȡ��ǰ�µ������յ�����ʹ�����������������������֮��
 * 
 * @author Administrator
 * 
 */
public class MonthlyUseData {
	// �¶�ʹ����������
	public static long MonthlyUseTraffic = 0;
	// ------------
	// ��ʾ����ͼ��
	boolean ismobileshowpie = false;
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

	/**
	 * ��ȡ�¶�ʹ������
	 * 
	 * @param sqlDataBase
	 * @return ����ʹ��������ֵ
	 */
	public long getMonthUseData(Context context, SQLiteDatabase sqlDataBase) {
		SharedPrefrenceData shareData = new SharedPrefrenceData(context);
		long monthHasUseBefore = shareData.getMonthHasUsedStack();
		// ���и�ֵ���´β���ȡ
		if (monthHasUseBefore == -100) {
			return 0;
			// if (TrafficManager.mobile_month_use_afterSet != 1) {
			// monthHasUseBefore = TrafficManager.getMonthUseData(context);
			// shareData.setMonthHasUsedStack(monthHasUseBefore);
			// }
		}
		return monthHasUseBefore;
		// MonthlyUseTraffic=mobile_month_use;
	}

	// /**
	// * ��ʾ��־
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// // Log.d("MonthlyUseData", string);
	// }

}
