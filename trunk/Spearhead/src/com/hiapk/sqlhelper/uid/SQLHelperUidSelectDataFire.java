package com.hiapk.sqlhelper.uid;

import java.util.HashMap;
import java.util.List;

import com.hiapk.bean.DatauidHash;
import com.hiapk.logs.Logs;
import com.hiapk.util.SQLStatic;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperUidSelectDataFire {
	private ActivityManager mActivityManager = null;
	// log
	private String TAG = "SQLHelperUidSelectDate";
	// date
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	private String weekStart;
	private String date;
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND_A = " AND ";

	public SQLHelperUidSelectDataFire(Context context) {
		super();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	private String SelectTable = "SELECT * FROM ";
	private String Where = " WHERE ";
	private Cursor cur;
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

	// /**
	// * 对数据库uid数据进行批量更新，
	// *
	// * @param sqlDataBase
	// * 进行操作的数据库SQLiteDatagase
	// * @param uidnumbers
	// * 数据库的表数组集合：uid的table表的uid号，
	// * @param type
	// * 用于记录数据状态，以统计数据
	// * @param other
	// * 用于记录特殊数据等
	// * @param typechange
	// * 改变type值
	// */
	// public HashMap<Integer, Data> initSQLUidtraff(SQLiteDatabase sqlDataBase,
	// int[] uidnumbers) {
	// HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
	// mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers);
	// return mp;
	// }

	/**
	 * 对数据库uid数据进行批量更新，（月度流量数据）
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库SQLiteDatagase
	 * @param uidnumbers
	 *            数据库的表数组集合：uid的table表的uid号，
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param typechange
	 *            改变type值
	 * @param flagFire
	 *            防火墙的值类型，0-2，0为每日，1为每周，2为每月
	 */
	public HashMap<Integer, DatauidHash> getSQLUidtraffMonth(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		mp = SelectUiddownloadAndupload(sqlDataBase, uidnumbers, flagFire);
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAndupload(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		initTime();
		switch (flagFire) {
		case 1:
			weekStart = selectWeekStart(sqlDataBase, year, month, monthDay,
					weekDay);
			if (SQLStatic.uiddataWeek != null) {
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataWeek);
			} else {
				mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers,
						flagFire);
			}
			break;
		case 2:
			if (SQLStatic.uiddataMonth != null) {
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataMonth);
			} else {
				mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers,
						flagFire);
			}
			break;
		default:
			if (SQLStatic.uiddataToday != null) {
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataToday);
			} else {
				mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers,
						flagFire);
			}
			break;
		}
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAnduploadPart(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire,
			HashMap<Integer, DatauidHash> mp) {
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			// 通过uidnumber判断是否为需要记录的应用
			String pacname = appProcessInfo.processName;
			int uidnumber = appProcessInfo.uid;
			if (SQLStatic.packagename_ALL.contains(pacname)) {
				long[] tpmobile = new long[3];
				long[] tpwifi = new long[3];
				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumber,
						NETWORK_FLAG, flagFire);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumber, "wifi",
						flagFire);
				DatauidHash temp = new DatauidHash();
				temp.setUploadmobile(tpmobile[1]);
				temp.setDownloadmobile(tpmobile[2]);
				temp.setUploadwifi(tpwifi[1]);
				temp.setDownloadwifi(tpwifi[2]);
				mp.put(uidnumber, temp);
				// showLog(uidnumber[i]+"traff"+get[1]+"");
			}
		}
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAnduploadAll(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		for (int i = 0; i < uidnumbers.length; i++) {
			if (uidnumbers[i] != -1) {

				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
						NETWORK_FLAG, flagFire);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi",
						flagFire);
				DatauidHash temp = new DatauidHash();
				temp.setUploadmobile(tpmobile[1]);
				temp.setDownloadmobile(tpmobile[2]);
				temp.setUploadwifi(tpwifi[1]);
				temp.setDownloadwifi(tpwifi[2]);
				mp.put(uidnumbers[i], temp);
				// showLog(uidnumber[i]+"traff"+get[1]+"");
			}
		}
		return mp;
	}

	/**
	 * 依据网络状态选择对应的之前流量。
	 * 
	 * @param mySQL
	 * @param uidnumber
	 * @param network
	 * @return a[0]为总计流量a[1]总计上传流量a[2]总计下载流量
	 */
	private long[] getSQLuidtotalData(SQLiteDatabase mySQL, int uidnumber,
			String network, int flagFire) {
		StringBuilder string = new StringBuilder();
		String strDate = getDateString(mySQL, flagFire);
		if (network == NETWORK_FLAG) {
			// select oldest upload and download 之前记录的数据的查询操作
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("type=").append(3).append(strDate);
		} else {
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("type=").append(4).append(strDate);
		}
		// Logs.d(TAG, string.toString());
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, "error+CreateTable" + string);
			// TODO
			// SQLHelperUidSelectFail selectFail = new SQLHelperUidSelectFail();
			// selectFail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
		}
		long[] a = new long[3];
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					a[1] = cur.getLong(minup);
					a[2] = cur.getLong(mindown);
				}
				while (cur.moveToNext()) {
					a[1] += cur.getLong(minup);
					a[2] += cur.getLong(mindown);
				}
			} catch (Exception e) {
				Logs.d(TAG, "cur-searchfail" + e);
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = a[1] + a[2];
		return a;
	}

	private String getDateString(SQLiteDatabase mySQL, int flagFire) {
		StringBuilder strDate = new StringBuilder();
		// 格式化日期
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// 字段开头
		strDate.append(AND_A).append("date");
		switch (flagFire) {
		case 1:
			return strDate.append(Between).append(weekStart).append(AND_B)
					.append(date).append("'").toString();
		case 2:
			return strDate.append(Between).append(year).append("-")
					.append(month2).append("-").append(00).append(AND_B)
					.append(date).append("'").toString();
		default:
			return strDate.append("='").append(date).append("'").toString();
		}

	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
		// SQLname = year + SQLname;
		String month2 = month + "";
		String monthDay2 = monthDay + "";
		if (month < 10)
			month2 = "0" + month2;
		if (monthDay < 10)
			monthDay2 = "0" + monthDay2;
		date = year + "-" + month2 + "-" + monthDay2;
		// Table = Table + year;
		// showLog("日期：" + date + "，" + SQLname + "，tableName：" + Table);
	}

	/**
	 * 进行数据周使用量查询
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param monthDay
	 *            单月的日期
	 * @param weekDay
	 *            星期
	 * @return 星期一的具体的日期格式
	 */
	private String selectWeekStart(SQLiteDatabase sqlDataBase, int year,
			int month, int monthDay, int weekDay) {
		if (weekDay == 0) {
			weekDay = 6;
		} else {
			weekDay = weekDay - 1;
		}
		// showLog(weekDay + "");
		String weekStart = null;
		StringBuilder stringB = new StringBuilder();
		stringB.append("select date('now'").append(",'-").append(weekDay)
				.append(" day'" + ")");
		// Logs.d(TAG, stringB.toString());
		try {
			cur = sqlDataBase.rawQuery(stringB.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, stringB.toString() + "fail" + e);
		}
		if (cur != null) {
			try {
				int dateIndex = cur.getColumnIndex("date('now'" + ",'-"
						+ weekDay + " day'" + ")");
				if (cur.moveToFirst()) {
					weekStart = cur.getString(dateIndex);
				}
			} catch (Exception e) {
				Logs.d(TAG, "cur-searchfail" + e);
			}
		}
		if (cur != null) {
			cur.close();
		}
		// Logs.d(TAG, weekStart);
		return weekStart;
	}

}
