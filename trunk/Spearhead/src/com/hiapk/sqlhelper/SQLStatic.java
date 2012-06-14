package com.hiapk.sqlhelper;

import java.util.HashMap;

import com.hiapk.sqlhelper.SQLHelperFireWall.Data;

public class SQLStatic {
	// ���ݿ�����ʹ�á���Ҫ�С�
	public static boolean isSQLTotalOnUsed = false;
	public static boolean isSQLUidOnUsed = false;
	public static boolean isSQLIndexOnUsed = false;
	public static boolean isSQLUidTotalOnUsed = false;
	// �ж��Ƿ񸲸ǰ�װ
	public static boolean isCoverInstall = false;
	// ������uid...ָ���ǵ�ǰ��װ�����
	public static String[] packageName = new String[2];
	public static int uidnumber;
	// ����uid�����У�
	public static int[] uidnumbers = null;
	public static String packagename_ALL = null;
	public static HashMap<Integer, Data> uiddata = null;
//	public static boolean isuiddataOperating = false;
	// ���ڶ�ȡuid����
	public static boolean isuiddataRecording = false;
	// TotalAlarm��¼��
	public static boolean isTotalAlarmRecording = false;
	public static boolean isUidAlarmRecording = false;
	public static boolean isUidTotalAlarmRecording = false;
	/**
	 * ��ʼ����uids
	 */
	public static int[] uids = null;
	/**
	 * ��ʼ����pacs
	 */
	public static String[] packagenames = null;

	public static synchronized boolean setSQLTotalOnUsed(boolean SQLTotalOnUsed) {
		if (SQLTotalOnUsed == true && isSQLTotalOnUsed == false) {
			isSQLTotalOnUsed = SQLTotalOnUsed;
			return true;
		}
		if (SQLTotalOnUsed == true && isSQLTotalOnUsed == true)
			return false;
		if (SQLTotalOnUsed == false && isSQLTotalOnUsed == true) {
			isSQLTotalOnUsed = SQLTotalOnUsed;
			return true;
		} else
			return false;
	}

	public static synchronized boolean setSQLUidOnUsed(boolean SQLUidOnUsed) {
		if (SQLUidOnUsed == true && isSQLUidOnUsed == false) {
			isSQLUidOnUsed = SQLUidOnUsed;
			return true;
		}
		if (SQLUidOnUsed == true && isSQLUidOnUsed == true)
			return false;
		if (SQLUidOnUsed == false && isSQLUidOnUsed == true) {
			isSQLUidOnUsed = SQLUidOnUsed;
			return true;
		} else
			return false;
	}

	public static synchronized boolean setSQLIndexOnUsed(boolean SQLIndexOnUsed) {
		if (SQLIndexOnUsed == true && isSQLIndexOnUsed == false) {
			isSQLIndexOnUsed = SQLIndexOnUsed;
			return true;
		}
		if (SQLIndexOnUsed == true && isSQLIndexOnUsed == true)
			return false;
		if (SQLIndexOnUsed == false && isSQLIndexOnUsed == true) {
			isSQLIndexOnUsed = SQLIndexOnUsed;
			return true;
		} else
			return false;
	}

	public static synchronized boolean setSQLUidTotalOnUsed(
			boolean SQLUidTotalOnUsed) {
		if (SQLUidTotalOnUsed == true && isSQLUidTotalOnUsed == false) {
			isSQLUidTotalOnUsed = SQLUidTotalOnUsed;
			return true;
		}
		if (SQLUidTotalOnUsed == true && isSQLUidTotalOnUsed == true)
			return false;
		if (SQLUidTotalOnUsed == false && isSQLUidTotalOnUsed == true) {
			isSQLUidTotalOnUsed = SQLUidTotalOnUsed;
			return true;
		} else
			return false;
	}

	public static final Object sqlTotalSync = new Object();
	public static final Object sqlIndexSync = new Object();
	public static final Object sqlUidSync = new Object();
	public static final Object sqlUidTotalSync = new Object();
}
