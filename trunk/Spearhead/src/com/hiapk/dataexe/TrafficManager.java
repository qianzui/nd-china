package com.hiapk.dataexe;

import android.content.Context;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.total.SQLHelperTotal;
import com.hiapk.sqlhelper.uid.SQLHelperUidother;

/**
 * ���ڻ�ȡ�����������ݵ�����
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	SQLHelperUidother sqlhelperUid = new SQLHelperUidother();
	// ����Ҫɾ������ֵ
	public static long mobile_month_use = 1;
	public static long[] wifi_month_data = new long[64];
	public static long[] wifi_month_data_before = new long[64];
	public static long[] mobile_month_data = new long[64];
	public static long[] mobile_month_data_before = new long[64];

	/**
	 * ��ȡ�¶��ƶ�ʹ������
	 * 
	 * @param context
	 * @return
	 */
	public static long getMonthUseMobile(Context context) {
		long mobile_month_use = 0;
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		mobile_month_use = sharedData.getMonthHasUsedStack();
		if (mobile_month_use == -100) {
			return 0;
		}
		return mobile_month_use;
	}


	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("TrafficManager", string);
	// }
}
