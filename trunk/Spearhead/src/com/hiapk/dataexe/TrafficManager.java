package com.hiapk.dataexe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.pub.SQLStatic;

/**
 * 用于获取各种流量数据的总类
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	// SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// SQLHelperUidother sqlhelperUid = new SQLHelperUidother();
	// 后续要删除的数值
	public static long mobile_month_use = 1;
	public static long[] wifi_month_data = new long[64];
	public static long[] wifi_month_data_before = new long[64];
	public static long[] mobile_month_data = new long[64];
	public static long[] mobile_month_data_before = new long[64];
	// 专门为uid存储流量数据的表
	private static String UID_PREFS_NAME = "uidprefes";
	private static String UID_START_STR_UP = "uidnumUP";
	private static String UID_START_STR_DOWN = "uidnumDOWN";

	/**
	 * 获取月度移动使用流量
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

	/**
	 * 清楚被删除软件的流量信息
	 * 
	 * @param context
	 * @param uid
	 *            输入的uid号
	 */
	public static void clearUidtraff(Context context, int uid) {
		String uidstrup = UID_START_STR_UP + uid;
		String uidstrdown = UID_START_STR_DOWN + uid;
		Editor UseEditor = context.getSharedPreferences(UID_PREFS_NAME, 0)
				.edit();
		UseEditor.putLong(uidstrup, 0);
		UseEditor.putLong(uidstrdown, 0);
		showLog(uid + "clear");
		showLog(uid + "clear");
		UseEditor.commit();
	}

	/**
	 * 用于记录防火墙显示的流量数据数量(仅用于初始化一次)从1.0.3之后保留两个版本
	 * 
	 * @param context
	 * @param uid
	 *            输入的uid号
	 * @param upload
	 *            增上传流量
	 * @param download
	 *            增下载流量
	 */
	public static void setUidtraffinit(Context context, int uid, long upload,
			long download) {
		String uidstrup = UID_START_STR_UP + uid;
		String uidstrdown = UID_START_STR_DOWN + uid;
		Editor UseEditor = context.getSharedPreferences(UID_PREFS_NAME, 0)
				.edit();
		UseEditor.putLong(uidstrup, upload);
		UseEditor.putLong(uidstrdown, download);
		showLog(uid + "的上传流量" + upload);
		showLog(uid + "的下载流量" + download);
		UseEditor.commit();
	}

	/**
	 * 用于记录防火墙显示的流量数据数量
	 * 
	 * @param context
	 * @param uid
	 *            输入的uid号
	 * @param upload
	 *            新增上传流量
	 * @param download
	 *            新增下载流量
	 */
	public static void setUidtraff(Context context, int uid, long upload,
			long download) {
		String uidstrup = UID_START_STR_UP + uid;
		String uidstrdown = UID_START_STR_DOWN + uid;
		Editor UseEditor = context.getSharedPreferences(UID_PREFS_NAME, 0)
				.edit();
		SharedPreferences prefs = context.getSharedPreferences(UID_PREFS_NAME,
				0);
		long oldup = prefs.getLong(uidstrup, 0);
		long olddown = prefs.getLong(uidstrdown, 0);
		UseEditor.putLong(uidstrup, oldup + upload);
		UseEditor.putLong(uidstrdown, olddown + download);
		showLog(uid + "的新增上传流量" + upload);
		showLog(uid + "的新增下载流量" + download);
		UseEditor.commit();
	}

	/**
	 * 获取uid的流量信息
	 * 
	 * @param context
	 * @param uid
	 *            输入的uid号
	 * @return 返回3位数组，uidTraff[0]为总流量uidTraff[1]为上传流量uidTraff[2]为下载流量。 默认值为0
	 */
	public static long[] getUidtraff(Context context, int uid) {
		String uidstrup = UID_START_STR_UP + uid;
		String uidstrdown = UID_START_STR_DOWN + uid;
		long[] uidTraff = new long[3];
		SharedPreferences prefs = context.getSharedPreferences(UID_PREFS_NAME,
				0);
		uidTraff[1] = prefs.getLong(uidstrup, 0);
		uidTraff[2] = prefs.getLong(uidstrdown, 0);
		uidTraff[0] = uidTraff[1] + uidTraff[2];
		showLog(uid + "的上传流量" + uidTraff[1]);
		showLog(uid + "的下载流量" + uidTraff[2]);
		return uidTraff;

	}

	private static void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("TrafficManager", string);
		}
	}
}
