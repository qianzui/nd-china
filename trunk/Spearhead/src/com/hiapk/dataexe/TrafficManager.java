package com.hiapk.dataexe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.pub.SQLStatic;

/**
 * ���ڻ�ȡ�����������ݵ�����
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	// SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// SQLHelperUidother sqlhelperUid = new SQLHelperUidother();
	// ����Ҫɾ������ֵ
	public static long mobile_month_use = 1;
	public static long[] wifi_month_data = new long[64];
	public static long[] wifi_month_data_before = new long[64];
	public static long[] mobile_month_data = new long[64];
	public static long[] mobile_month_data_before = new long[64];
	// ר��Ϊuid�洢�������ݵı�
	private static String UID_PREFS_NAME = "uidprefes";
	private static String UID_START_STR_UP = "uidnumUP";
	private static String UID_START_STR_DOWN = "uidnumDOWN";

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

	/**
	 * �����ɾ�������������Ϣ
	 * 
	 * @param context
	 * @param uid
	 *            �����uid��
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
	 * ���ڼ�¼����ǽ��ʾ��������������(�����ڳ�ʼ��һ��)��1.0.3֮���������汾
	 * 
	 * @param context
	 * @param uid
	 *            �����uid��
	 * @param upload
	 *            ���ϴ�����
	 * @param download
	 *            ����������
	 */
	public static void setUidtraffinit(Context context, int uid, long upload,
			long download) {
		String uidstrup = UID_START_STR_UP + uid;
		String uidstrdown = UID_START_STR_DOWN + uid;
		Editor UseEditor = context.getSharedPreferences(UID_PREFS_NAME, 0)
				.edit();
		UseEditor.putLong(uidstrup, upload);
		UseEditor.putLong(uidstrdown, download);
		showLog(uid + "���ϴ�����" + upload);
		showLog(uid + "����������" + download);
		UseEditor.commit();
	}

	/**
	 * ���ڼ�¼����ǽ��ʾ��������������
	 * 
	 * @param context
	 * @param uid
	 *            �����uid��
	 * @param upload
	 *            �����ϴ�����
	 * @param download
	 *            ������������
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
		showLog(uid + "�������ϴ�����" + upload);
		showLog(uid + "��������������" + download);
		UseEditor.commit();
	}

	/**
	 * ��ȡuid��������Ϣ
	 * 
	 * @param context
	 * @param uid
	 *            �����uid��
	 * @return ����3λ���飬uidTraff[0]Ϊ������uidTraff[1]Ϊ�ϴ�����uidTraff[2]Ϊ���������� Ĭ��ֵΪ0
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
		showLog(uid + "���ϴ�����" + uidTraff[1]);
		showLog(uid + "����������" + uidTraff[2]);
		return uidTraff;

	}

	private static void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("TrafficManager", string);
		}
	}
}
