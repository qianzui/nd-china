package com.hiapk.sqlhelper.uid;

import java.util.List;

import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.pub.SQLStatic;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperUidRecord {
	private ActivityManager mActivityManager = null;

	public SQLHelperUidRecord(Context context) {
		super();
		// initTime();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// TODO Auto-generated constructor stub
	}

	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String AND = "' AND ";
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
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

	/**
	 * * 对数据库uid数据进行更新
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param date
	 *            记录进数据库的日期
	 * @param time
	 *            记录进数据库的时间
	 * @param uidupload
	 *            上传流量
	 * @param uiddownload
	 *            下载流量
	 * @param uidnumber
	 *            数据库的表：uid的table表的uid号
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param typechange
	 *            改变type值
	 */
	private void updateSQLUidType(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String other, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "date='" + date
				+ "',time='" + time + "',upload='" + uidupload + "',download='"
				+ uiddownload + "' ,type=" + typechange + ", other=" + "'"
				+ other + "'" + Where + "type=" + type;
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
	}

	private void updateSQLUidTypeDate2to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "time='" + time
				+ "',upload='" + uidupload + "',download='" + uiddownload
				+ "' ,type=" + typechange + Where + "date='" + date + AND
				+ "other='" + network + AND + "type=" + type;
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
	}

	private void updateSQLUidTypeDate0to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "time='" + time
				+ "',upload='" + uidupload + "',download='" + uiddownload
				+ "' ,type=" + typechange + " ,date='" + date + "',other='"
				+ network + "'" + Where + "type=" + type;
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
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
	private void exeSQLcreateUidtableSetData(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, long upload, long download, int type,
			String other) {
		String string = null;
		// 表示是否为总流量，总流量初始数据为0
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + upload + split
				+ download + split + type + split + other + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
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

	/**
	 * 记录uid的流量数据
	 * 
	 * @param context
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void RecordUidwritestats(SQLiteDatabase sqlDataBase,
			int[] uidnumbers, boolean daily, String network) {
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();
		initTime();
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			// 通过pacname判断是否为需要记录的应用
			String pacname = appProcessInfo.processName;
			if (SQLStatic.packagename_ALL.contains(pacname)) {
				int uidnumber = appProcessInfo.uid;
				long[] uiddata = SQLHelperDataexe.initUidData(uidnumber);
				if (uiddata[0] != 0 || uiddata[1] != 0) {
					statsSQLuid(sqlDataBase, uidnumber, date, time, uiddata[0],
							uiddata[1], 2, null, daily, network);
				}
			}

		}

		// if (!network.equals("")) {
		// uidRecordwritestats(sqlDataBase, uidnumbers, daily, network);
		// }
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
			String network) {
		String string = null;
		if (network == NETWORK_FLAG) {
			// select oldest upload and download 之前记录的数据的查询操作
			string = SelectTable + "uid" + uidnumber + Where + "type=" + 3;
		} else {
			string = SelectTable + "uid" + uidnumber + Where + "type=" + 4;
		}
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error" + string);
		}
		long[] a = new long[3];
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// 获得之前的上传下载值
					a[1] = cur.getLong(minup);
					a[2] = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = a[1] + a[2];
		return a;
	}

	/**
	 * 对数据库数据进行统计，写入时间范围内的上传，下载数据
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param date
	 *            记录数据的日期
	 * @param time
	 *            记录数据的时间
	 * @param upload
	 *            记录上传流量
	 * @param download
	 *            记录下载流量
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	private void statsSQLuid(SQLiteDatabase mySQL, int uidnumber, String date,
			String time, long upload, long download, int type, String other,
			boolean daily, String network) {
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + "uid" + uidnumber + Where + "type=" + 0;
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			SQLHelperUidSelectFail selectfail = new SQLHelperUidSelectFail();
			selectfail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
		}
		long oldup0 = -100;
		long olddown0 = -100;
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// 获得之前的上传下载值
					oldup0 = cur.getLong(minup);
					olddown0 = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100) {

			// 初始化写入数据（wifi以及g23）
			// 如果之前数据大于新的数据，则重新计数
			if ((oldup0 > (upload + 10000)) || (olddown0 > (download + 10000))) {
				oldup0 = upload;
				olddown0 = upload;
			} else {
				oldup0 = upload - oldup0;
				olddown0 = download - olddown0;
			}

			if ((oldup0 != 0 || olddown0 != 0)
					&& ((olddown0 > 512) || (oldup0 > 512))) {
				cur = null;
				// 覆盖添加判断
				string = SelectTable + "uid" + uidnumber + Where + "date='"
						+ date + AND + "other='" + network + AND + "type=" + 2;
				try {
					cur = mySQL.rawQuery(string, null);
				} catch (Exception e) {
					// TODO: handle exception
					showLog("error" + string);
				}
				long oldup2 = 0;
				long olddown2 = 0;
				String olddate2 = "";
				// 进行添加 覆盖+
				// showLog("cur.move" + cur.moveToFirst());
				if (cur.moveToFirst()) {
					try {
						int minup = cur.getColumnIndex("upload");
						int mindown = cur.getColumnIndex("download");
						int dateIndex = cur.getColumnIndex("date");
						// showLog(cur.getColumnIndex("minute") + "");
						if (cur.moveToFirst()) {
							// 获得之前的上传下载值
							oldup2 = cur.getLong(minup);
							olddown2 = cur.getLong(mindown);
							olddate2 = cur.getString(dateIndex);
						}
					} catch (Exception e) {
						// TODO: handle exception
						showLog("cur-searchfail");
						oldup2 = 0;
						olddown2 = 0;
						olddate2 = "";
					}
					if (olddate2 != "") {
						updateSQLUidTypeDate2to2(mySQL, date, time, oldup2
								+ oldup0, olddown2 + olddown0, uidnumber, 2,
								network, 2);
						updateSQLUidType(mySQL, date, time, upload, download,
								uidnumber, 0, network, 0);
						statsSQLuidTotaldata(mySQL, uidnumber, date, time,
								oldup0, olddown0, network);
					}
					// 进行添加add
				} else {
					updateSQLUidTypeDate0to2(mySQL, date, time, oldup0,
							olddown0, uidnumber, 0, network, 2);
					exeSQLcreateUidtableSetData(mySQL, date, time, uidnumber,
							upload, download, 0, network);
					// updateSQLUidType(mySQL, date, time, upload, download,
					// uidnumber, 0, network, 0);

					statsSQLuidTotaldata(mySQL, uidnumber, date, time, oldup0,
							olddown0, network);
				}
				if (cur != null) {
					cur.close();
				}
			}
		}
	}

	/**
	 * 记录总的流量数据
	 * 
	 * @param mySQL
	 * @param uidnumber
	 * @param date
	 * @param time
	 * @param upload
	 * @param download
	 * @param network
	 */
	private void statsSQLuidTotaldata(SQLiteDatabase mySQL, int uidnumber,
			String date, String time, long upload, long download, String network) {
		if (network == NETWORK_FLAG) {
			long[] beforeTotal = new long[3];
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 3, null, 3);
		} else {
			long[] beforeTotal = new long[3];
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 4, null, 4);
		}
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("databaseUidRecord", string);
	}
}
