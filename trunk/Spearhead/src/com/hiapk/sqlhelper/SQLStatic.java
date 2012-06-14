package com.hiapk.sqlhelper;

import java.util.HashMap;

import com.hiapk.sqlhelper.SQLHelperFireWall.Data;

public class SQLStatic {
	// 数据库正在使用。重要中。
	public static boolean isSQLTotalOnUsed = false;
	public static boolean isSQLUidOnUsed = false;
	public static boolean isSQLIndexOnUsed = false;
	public static boolean isSQLUidTotalOnUsed = false;
	// 判断是否覆盖安装
	public static boolean isCoverInstall = false;
	// 包名与uid...指的是当前安装的软件
	public static String[] packageName = new String[2];
	public static int uidnumber;
	// 库存的uid表（所有）
	public static int[] uidnumbers = null;
	public static String packagename_ALL = null;
	public static HashMap<Integer, Data> uiddata = null;
//	public static boolean isuiddataOperating = false;
	// 正在读取uid流量
	public static boolean isuiddataRecording = false;
	// TotalAlarm记录中
	public static boolean isTotalAlarmRecording = false;
	public static boolean isUidAlarmRecording = false;
	public static boolean isUidTotalAlarmRecording = false;
	/**
	 * 初始化用uids
	 */
	public static int[] uids = null;
	/**
	 * 初始化用pacs
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
