package com.hiapk.util;

import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hiapk.bean.DatauidHash;
import com.hiapk.firewall.Block;

/**
 * 用于存储在程序运行过程中使用的某些静态变量
 * 
 * @author Administrator
 * 
 */

public class SQLStatic {

	// pre
	private final static String PREFS_NAME = "allprefs";
	private final static String PREF_INITSQL = "isSQLINIT";
	private final static String MODE_NOTINIT = "SQLisnotINIT";
	private final static String MODE_HASINIT = "SQLhasINIT";
	// 正在获取UidNumber中
	private static boolean isGettinguidsAndpacname = false;
	// 数据库正在使用。重要中。
	public static boolean isSQLTotalOnUsed = false;
	public static boolean isSQLUidOnUsed = false;
	public static boolean isSQLUidTotalOnUsed = false;
	// 判断是否覆盖安装
	public static boolean isCoverInstall = false;
	// 包名与uid...指的是当前安装的软件
	public static String[] packageName = new String[2];
	public static int uidnumber;
	// 库存的uid表（所有）
	public static int[] uidnumbers = null;
	// 在SQLuidother里面也有更新
	public static String packagename_ALL = null;
	/**
	 * 今日uid数据
	 */
	public static HashMap<Integer, DatauidHash> uiddataToday = null;
	/**
	 * 本周uid数据
	 */
	public static HashMap<Integer, DatauidHash> uiddataWeek = null;
	/**
	 * 本月uid数据
	 */
	public static HashMap<Integer, DatauidHash> uiddataMonth = null;
	// public static boolean isuiddataOperating = false;
	// 正在读取uid流量
	public static boolean isuiddataRecording = false;
	// TotalAlarm记录中
	public static boolean isTotalAlarmRecording = false;
	public static boolean isUidAlarmRecording = false;
	public static boolean isUidTotalAlarmRecording = false;
	// 记录网络状态
	public static String TableWiFiOrG23 = "";
	public static String TableWiFiOrG23Before = "";
	// 连接变化时使用，用于检测是否正在等待中
	public static boolean ConnectSleepWaiting = false;
	/**
	 * 初始化用pacs
	 */
	public static String[] packagenames = null;
	// show log
	public static boolean isshowLog = true;

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

	/**
	 * 用于初始化网络状态确定当前使用何种网络
	 * 
	 * @param context
	 * 
	 * @param allset
	 *            是否改变uid与total的记录值
	 */
	public static synchronized void initTablemobileAndwifi(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo() != null) {
			NetworkInfo info = connec.getActiveNetworkInfo();
			String typeName = info.getTypeName(); // mobile@wifi
			if (typeName.equals("WIFI"))
				TableWiFiOrG23 = "wifi";
			if (typeName.equals("mobile"))
				TableWiFiOrG23 = "mobile";
			showLog("何种方式连线" + typeName);
		} else {

			if (TableWiFiOrG23 != "") {
				TableWiFiOrG23Before = TableWiFiOrG23;
			}
			TableWiFiOrG23 = "";
			showLog("无可用网络");
		}
	}

	/**
	 * 设置IsInit与程序记录同步
	 * 
	 * @param context
	 */
	public static boolean getIsInit(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getString(PREF_INITSQL, MODE_NOTINIT).endsWith(
				MODE_HASINIT);
	}

	// /**
	// * 提取所有应用的不重复uid集合
	// *
	// * @param sqlDataBase
	// * 进行操作的数据库
	// * @return
	// */
	// public static int[] selectUidnumbers(Context context) {
	//
	// int j = 0;
	// PackageManager pkgmanager = context.getPackageManager();
	// List<PackageInfo> packages = context.getPackageManager()
	// .getInstalledPackages(0);
	// int[] uidstemp = new int[packages.size()];
	// String pacstemp = new String();
	// for (int i = 0; i < packages.size(); i++) {
	// PackageInfo packageinfo = packages.get(i);
	// String pacname = packageinfo.packageName;
	// int uid = packageinfo.applicationInfo.uid;
	// if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
	// .checkPermission(Manifest.permission.INTERNET, pacname))) {
	// if (!Block.filter.contains(pacname)) {
	// pacstemp += pacname;
	// boolean issameUid = false;
	// for (int k = 0; k < j; k++) {
	// if (uidstemp[k] == uid) {
	// issameUid = true;
	// break;
	// }
	// }
	// if (!issameUid) {
	// uidstemp[j] = uid;
	// // showLog("进行显示的uid=" + uid);
	// j++;
	// }
	//
	// }
	// }
	// }
	// int[] uids = new int[j];
	// for (int i = 0; i < j; i++) {
	// uids[i] = uidstemp[i];
	// }
	// if (packagename_ALL == null) {
	// packagename_ALL = pacstemp;
	// }
	//
	// return uids;
	// }

	public static synchronized void getuidsAndpacname(Context context) {
		if (isGettinguidsAndpacname == true) {
			return;
		}
		isGettinguidsAndpacname = true;
		int j = 0;
		PackageManager pkgmanager = context.getPackageManager();
		List<ApplicationInfo> appInfos = pkgmanager.getInstalledApplications(0);

		int[] uidstp = new int[appInfos.size()];
		String[] packagenamestp = new String[appInfos.size()];
		StringBuilder pacstemp = new StringBuilder();
		for (int i = 0; i < appInfos.size(); i++) {
			ApplicationInfo appInfo = appInfos.get(i);
			String fliter = Block.filter;
			String pacname = appInfo.packageName;
			int uid = appInfo.uid;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					pacstemp.append(pacname);
					uidstp[j] = uid;
					packagenamestp[j] = pacname;
					// showLog("进行显示的uid=" + uid);
					j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		SQLStatic.uidnumbers = new int[j];
		SQLStatic.packagenames = new String[j];
		for (int i = 0; i < j; i++) {
			SQLStatic.uidnumbers[i] = uidstp[i];
			SQLStatic.packagenames[i] = packagenamestp[i];
		}
		if (packagename_ALL == null) {
			packagename_ALL = pacstemp.toString();
		}
		isGettinguidsAndpacname = false;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private static void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("SQLStatic", string);
		}
	}
}
