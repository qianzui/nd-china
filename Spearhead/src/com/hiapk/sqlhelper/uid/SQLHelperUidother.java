package com.hiapk.sqlhelper.uid;

import java.util.List;

import com.hiapk.bean.UidTraffs;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperUidother {

	public SQLHelperUidother() {
		super();
	}

	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String DeleteTable = "DELETE FROM ";
	private String Where = " WHERE ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
	private String Value = "values('";
	private String split = "','";
	private Cursor cur;
	// date
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String date;
	private String time;
	// log
	private String TAG = "databaseUidother";

	/**
	 * 在安装新程序时更新uidIndex目录
	 * 
	 * @param context
	 * @param uidnumber
	 *            新增的程序uid号
	 * @param packageName
	 *            新增的程序包名
	 * @param other
	 *            程序状态 返回要进行处理的uid数组覆盖安装返回null，新安装返回{1019}
	 */
	public int[] updateSQLUidOnInstall(Context context, int uidnumber,
			String packageName, String other) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		// 重新定义静态的uid集合
		// String newpackage = selectPackagenames(context);
		SQLStatic.getuidsAndpacname(context);
		// SQLStatic.packagename_ALL = selectPackagenames(context);
		String pacTemp = sharedData.getPackageNames();
		if (pacTemp != "") {
			SQLStatic.packagename_ALL = pacTemp;
		}

		if (SQLStatic.packagename_ALL.contains(packageName)) {
			// 覆盖安装
			return null;
		} else {
			// 新安装软件
			SQLStatic.packagename_ALL = selectPackagenames(context);
			sharedData.setPackageNames(SQLStatic.packagename_ALL);
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.initTablemobileAndwifi(context);
			}
			AlarmSet alset = new AlarmSet();
			alset.StartAlarm(context);
			return new int[] { 1019 };
		}

	}

	public void updateSQLUidOnInstall(Context context, int uidnumber,
			String packageName, String other, List<Integer> uid_List_Add,
			List<Integer> uid_List_Del) {
		SQLiteDatabase mySQL = SQLHelperCreateClose.creatSQLUid(context);
		// 更新Uid数据库
		mySQL.beginTransaction();
		try {
			// if (uids != null && uids[0] != 1019) {
			// for (int i = 0; i < uids.length; i++) {
			// if (uids[i] != 0) {
			// DropUnusedUidTable(mySQL, uids[i]);
			// }
			// }
			// }
			if (uid_List_Del != null) {
				for (Integer uid : uid_List_Del) {
					DropUnusedUidTable(mySQL, uid);
					// initTime();
					// initUidTable(mySQL, uid);
					// exeSQLcreateUidtable(mySQL, date, time, uid, 0, null);
					// exeSQLcreateUidtable(mySQL, date, time, uid, 1, null);
				}
			}

			// showLog("新安装软件" + packageName + uidnumber);
			// 清除表再建表
			if (uid_List_Add != null) {
				for (Integer uid : uid_List_Add) {
					// DropUnusedUidTable(mySQL, uid);
					// 新软件清除旧的uid数据
					TrafficManager.clearUidtraff(context, uid);
					initTime();
					initUidTable(mySQL, uid);
					StringBuilder string = new StringBuilder();
					string = string.append(SelectTable).append("uid")
							.append(uid).append(Where).append("type=")
							.append(0);
					// showLog(string);
					try {
						cur = mySQL.rawQuery(string.toString(), null);
					} catch (Exception e) {
						// 搜索失败则新建表
						Logs.d(TAG, "selectfail" + string.toString());
					}
					if (!cur.moveToFirst()) {
						exeSQLcreateUidtable(mySQL, date, time, uid, 0, null);
						exeSQLcreateUidtable(mySQL, date, time, uid, 3, null);
						exeSQLcreateUidtable(mySQL, date, time, uid, 4, null);
					} else {
						cur.close();
					}

				}

			}
			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			Logs.d(TAG, "更新索引表失败");
		} finally {
			mySQL.endTransaction();
			SQLHelperCreateClose.closeSQL(mySQL);
		}

	}

	/**
	 * 提取所有应用的不重复包名
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库
	 * @return
	 */
	public String selectPackagenames(Context context) {
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		String pacstemp = new String();
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = Block.filter;
			String pacname = packageinfo.packageName;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					pacstemp += pacname;
					// j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		// String[] pacs = new String[j];
		// for (int i = 0; i < j; i++) {
		// pacs[i] = pacstemp[i];
		// }
		return pacstemp;
	}

	/**
	 * 对数据库进行uid数据的写入操作的操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, int type, String other) {
		UidTraffs uiddata = SQLHelperDataexe.initUidData(uidnumber);
		StringBuilder string = new StringBuilder();
		// 表示是否为总流量，总流量初始数据为0
		if (type == 3 || type == 4) {
			string = string.append(InsertTable).append("uid").append(uidnumber)
					.append(Start).append(InsertUidColumnTotal).append(End)
					.append(Value).append(date).append(split).append(time)
					.append(split).append("0").append(split).append("0")
					.append(split).append(type).append(split).append(other)
					.append("'").append(End);
		} else {
			string = string.append(InsertTable).append("uid").append(uidnumber)
					.append(Start).append(InsertUidColumnTotal).append(End)
					.append(Value).append(date).append(split).append(time)
					.append(split).append(uiddata.getUpload()).append(split)
					.append(uiddata.getDownload()).append(split).append(type)
					.append(split).append(other).append("'").append(End);
		}
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
	}

	/**
	 * 清空uid表内容
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            要清空的uid表
	 */
	private void DropUnusedUidTable(SQLiteDatabase mySQL, int uidnumber) {
		StringBuilder string = new StringBuilder();
		string = string.append(DeleteTable).append("uid").append(uidnumber);
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string + "fail");
		}
	}

	/**
	 * 初始化uid数据库时建立单表
	 * 
	 * @param sqldatabase
	 *            进行操作的数据库
	 * @param uidnumber
	 *            要建立的uid表
	 */
	private void initUidTable(SQLiteDatabase sqldatabase, int uidnumber) {
		StringBuilder string = new StringBuilder();
		string = string.append(CreateTable).append("uid").append(uidnumber)
				.append(Start).append(SQLId).append(CreateparamUid).append(End);
		try {
			sqldatabase.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
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
		hour = t.hour; // 0-23
		minute = t.minute;
		second = t.second;
		// SQLname = year + SQLname;
		String month2 = month + "";
		String monthDay2 = monthDay + "";
		String hour2 = hour + "";
		String minute2 = minute + "";
		String second2 = second + "";
		if (month < 10)
			month2 = "0" + month2;
		if (monthDay < 10)
			monthDay2 = "0" + monthDay2;
		if (hour < 10)
			hour2 = "0" + hour2;
		if (minute < 10)
			minute2 = "0" + minute2;
		if (second < 10)
			second2 = "0" + second2;
		date = year + "-" + month2 + "-" + monthDay2;
		time = hour2 + ":" + minute2 + ":" + second2;
		// Table = Table + year;
		// showLog("日期：" + date + "，" + SQLname + "，tableName：" + Table);
	}

}
