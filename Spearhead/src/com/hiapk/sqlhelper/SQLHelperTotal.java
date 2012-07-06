package com.hiapk.sqlhelper;

import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;

public class SQLHelperTotal {

	public SQLHelperTotal() {
		super();
		// initTime();
		// TODO Auto-generated constructor stub
	}

	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidIndex = "SQLUidIndex.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
	private String TableWiFi = "wifi";
	private String TableMobile = "mobile";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND = "' AND ";
	private String Start = " (";
	private String End = ") ";
	private String InsertColumnTotal = "date,time,upload,download,type,other";
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
	private String dateDelete;
	private String time;
	// data
	private long upload;
	private long download;
	private static final int MODE_PRIVATE = 0;
	// classes
	SQLHelperUid SQLhelperuid = new SQLHelperUid();
	SQLHelperUidTotal SQLhelperuidTotal = new SQLHelperUidTotal();

	/**
	 * 创建总数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public SQLiteDatabase creatSQLTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLTotalname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * 创建uid数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public SQLiteDatabase creatSQLUid(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * 创建uidIndex数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public SQLiteDatabase creatSQLUidIndex(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidIndex,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * 创建uidTotal数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * 对数据库总体数据进行更新
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，mobile等
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
	 * @param typechange
	 *            改变type值
	 */
	private void updateSQLtotalType(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "date='" + date + "',time='"
				+ time + "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "type=" + type;
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
	 * 对数据库总体数据进行更新
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，mobile等
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
	 * @param typechange
	 *            改变type值
	 */
	private void updateSQLtotalTypeDate(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "time='" + time
				+ "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "date='" + date + AND
				+ "type=" + type;
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

	private void updateSQLtotalTypeDate0to3(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "time='" + time
				+ "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + " ,date='" + date + "' " + Where
				+ "type=" + type;
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
	 * 对数据库总体数据进行更新,自动生成时间，日期上传，下载流量
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，mobile等
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param typechange
	 *            改变type值2的值为数据库中实际记录数据的数值
	 */
	public void updateSQLtotalType(SQLiteDatabase mySQL, String table,
			int type, String other, int typechange) {
		initTime();
		initTotalData(table);
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "date='" + date + "',time='"
				+ time + "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "type=" + type;
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
	 * 对数据库总体数据进行统计，写入时间范围内的上传，下载数据
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，mobile等
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
	 *            是否进行强制添加
	 */
	private void statsSQLtotal(Context context, SQLiteDatabase mySQL,
			String table, String date, String time, long upload, long download,
			int type, String other, boolean daily) {
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "type=" + 0;
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
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
				showLog("数据库搜索失败");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		showLog("oldup0=" + oldup0 + "olddown0=" + olddown0);
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100) {
			cur = null;
			// 初始化写入数据（wifi以及mobile）
			// 如果之前数据大于新的数据，则重新计数
			if ((oldup0 > upload) || (olddown0 > download)) {
				oldup0 = upload;
				olddown0 = download;

			} else {
				oldup0 = upload - oldup0;
				olddown0 = download - olddown0;
			}
			showLog("oldup0 up=" + oldup0 + "olddown0 down=" + olddown0);
			if ((olddown0 != 0 || oldup0 != 0)
					&& ((olddown0 > 512) || (oldup0 > 512))) {

				string = SelectTable + table + Where + "date='" + date + AND
						+ "type=" + 3;
				try {
					cur = mySQL.rawQuery(string, null);
				} catch (Exception e) {
					// TODO: handle exception
					showLog(string);
				}
				long oldup3 = 0;
				long olddown3 = 0;
				String olddate3 = "";
				// 进行添加 覆盖+
				// showLog("cur.move" + cur.moveToFirst());
				if (cur.moveToFirst()) {
					showLog("已有单天数据");
					try {
						int minup = cur.getColumnIndex("upload");
						int mindown = cur.getColumnIndex("download");
						int dateIndex = cur.getColumnIndex("date");
						// showLog(cur.getColumnIndex("minute") + "");
						if (cur.moveToFirst()) {
							// 获得之前的上传下载值
							oldup3 = cur.getLong(minup);
							olddown3 = cur.getLong(mindown);
							olddate3 = cur.getString(dateIndex);
						}
					} catch (Exception e) {
						// TODO: handle exception
						showLog("cur-searchfail");
						oldup3 = 0;
						olddown3 = 0;
						olddate3 = "";
					}
					// showLog(oldup2+"olddown2="+olddown2+olddate2);
					// 3为日统计流量
					if (olddate3 != "") {

						updateSQLtotalTypeDate(mySQL, table, oldup3 + oldup0,
								olddown3 + olddown0, 3, other, 3);
						updateSQLtotalType(mySQL, table, upload, download, 0,
								other, 0);
						// 时刻对于的数据
						// exeSQLtotalSetData(mySQL, table, oldup0, olddown0, 2,
						// other);
						if (table == "mobile") {
							SharedPrefrenceData sharedData = new SharedPrefrenceData(
									context);
							long beforemobile = sharedData
									.getMonthHasUsedStack();
							if (beforemobile != -100) {
								beforemobile = beforemobile + oldup0 + olddown0;
								sharedData.setMonthHasUsedStack(beforemobile);
							}
						}

					}
					// 进行添加add
				} else {
					showLog("无单天数据");
					updateSQLtotalTypeDate0to3(mySQL, table, oldup0, olddown0,
							0, other, 3);
					exeSQLtotalSetData(mySQL, table, upload, download, 0, other);
					// updateSQLtotalType(mySQL, table, upload, download, 0,
					// other, 0);
					// 时刻对于的数据
					// exeSQLtotalSetData(mySQL, table, oldup0, olddown0, 2,
					// other);
					SharedPrefrenceData sharedData = new SharedPrefrenceData(
							context);
					if (table == "mobile") {
						long beforemobile = sharedData.getMonthHasUsedStack();
						if (beforemobile != -100) {

							beforemobile = beforemobile + oldup0 + olddown0;
							sharedData.setMonthHasUsedStack(beforemobile);
						}
					}
				}
				if (cur != null) {
					cur.close();
				}
			}
			// if (daily) {
			// // showLog("上传数据" + oldup + "B" + "  " + "下载数据" + olddown + "B");
			// // 输入实际数据进入数据库
			// updateSQLtotalType(mySQL, table, oldup0, olddown0, 0, other,
			// type);
			// // 添加新的两行数据
			// updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			// exeSQLtotal(mySQL, table, 1, other);
			// } else if ((olddown0 != 0 || oldup0 != 0)
			// && ((olddown0 > 1024) || (oldup0 > 1024))) {
			// // showLog("上传数据" + oldup + "B" + "  " + "下载数据" + olddown + "B");
			// // 输入实际数据进入数据库
			// updateSQLtotalType(mySQL, table, oldup0, olddown0, 0, other,
			// type);
			// // 添加新的两行数据
			// updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			// exeSQLtotal(mySQL, table, 1, other);
			// }
		}
	}

	private void exeSQLtotalSetData(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other) {
		// TODO Auto-generated method stub
		String string = null;
		string = InsertTable + table + Start + InsertColumnTotal + End + Value
				+ date + split + time + split + upload + split + download
				+ split + type + split + other + "'" + End;
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
	 * 关闭数据库
	 * 
	 * @param mySQL
	 *            对指定数据库进行关闭
	 */
	public void closeSQL(SQLiteDatabase mySQL) {
		mySQL.close();
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
	 * 外部程序获取系统时间
	 * 
	 * @return
	 */
	public String gettime() {
		initTime();
		return time;
	}

	/**
	 * 初始化流量数据
	 * 
	 * @param table
	 *            wifi或者mobile，若为空则无数据
	 */
	private void initTotalData(String table) {
		if (table == "wifi") {
			upload = TrafficStats.getTotalTxBytes()
					- TrafficStats.getMobileTxBytes();
			download = TrafficStats.getTotalRxBytes()
					- TrafficStats.getMobileRxBytes();
			if (upload == 1) {
				upload = 0;
			}
			if (download == 1) {
				download = 0;
			}
		}
		if (table == "mobile") {
			upload = TrafficStats.getMobileTxBytes();
			download = TrafficStats.getMobileRxBytes();
			if (upload == -1) {
				upload = 0;
			}
			if (download == -1) {
				download = 0;
			}
		}
		if (table == "") {
			upload = 0;
			download = 0;
		}
	}

	/**
	 * 记录wifi，mobile流量数据
	 * 
	 * @param context
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void RecordTotalwritestats(Context context,
			SQLiteDatabase sqlDataBase, boolean daily, String network) {
		// TODO Auto-generated method stub
		// 自动进行数据记录---不记录上传下载为0的数据
		if (!network.equals("")) {
			// SQLiteDatabase sqlDataBase = creatSQLTotal(context);
			initTotalData(network);
			initTime();
			showLog("upload=" + upload + "download=" + download);
			statsSQLtotal(context, sqlDataBase, network, date, time, upload,
					download, 2, null, daily);
			// closeSQL(sqlDataBase);
		}
	}

	/**
	 * 记录wifi，mobile流量数据
	 * 
	 * @param context
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void RecordTotalwritestats(Context context, boolean daily,
			String network) {
		// TODO Auto-generated method stub
		// 自动进行数据记录---不记录上传下载为0的数据
		if (!network.equals("")) {
			SQLiteDatabase sqlDataBase = creatSQLTotal(context);
			initTotalData(network);
			initTime();
			statsSQLtotal(context, sqlDataBase, network, date, time, upload,
					download, 2, null, daily);
			closeSQL(sqlDataBase);
		}
	}

	/**
	 * 进行Wifi历史流量查询
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] SelectWifiData(SQLiteDatabase sqlDataBase, int year, int month) {
		return SelectData(sqlDataBase, year, month, TableWiFi);
	}

	/**
	 * 进行移动数据流量历史流量查询
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	// specialfortext TableWiFi- TableMobile
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month) {
		return SelectData(sqlDataBase, year, month, TableMobile);
	}

	/**
	 * 进行移动数据流量历史流量查询从某一天到另一设定天
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param day
	 *            输入查询数据开始的日期.
	 * @param setday
	 *            输入查询数据结束的日期.（同月的话最多只能相等，然后返回单天数据）
	 * @param table
	 *            要查询的数据类型
	 * @return 返回一个3位数组。a[0]为总计流量a[1]总计上传流量a[2]总计下载流量
	 */
	// specialfortext TableWiFi----TableMobile
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month, int day, int dayset) {
		return SelectData(sqlDataBase, year, month, day, dayset, TableMobile);
	}

	/**
	 * 进行移动数据流量从设定时间点到当前的流量查询
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param day
	 *            输入查询的日期.
	 * @param time
	 *            输入查询的时间.
	 * @return 返回一个3位数组。a[0]为总计流量a[1]为上传流量a[2]下载流量
	 * 
	 */
	// specialfortext TableMobile----------TableWiFi
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month, int day, String time) {

		return SelectData(sqlDataBase, year, month, day, time, TableMobile);
	}

	/**
	 * 进行数据流量历史流量查询
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param table
	 *            要查询的数据类型
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			String table) {
		long[] a = new long[64];
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "type=" + 3;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		String newdate = "";
		String countdate = "";
		String dateStr1 = year + "-" + month2 + "-" + "0";
		String dateStr2 = year + "-" + month2 + "-";
		long newup = 0;
		long newdown = 0;
		int i = 1;
		if (cur != null) {
			try {
				int dateIndex = cur.getColumnIndex("date");
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						if (i < 10)
							countdate = dateStr1 + i;
						else
							countdate = dateStr2 + i;
						newdate = cur.getString(dateIndex);
						newup = cur.getLong(uploadIndex);
						newdown = cur.getLong(downloadIndex);
						if (newdate.equals(countdate)) {
							// 记录每日数据
							a[i] += newup;
							a[i + 31] += newdown;
						} else {
							// 记录累计数据
							a[0] += a[i];
							a[63] += a[i + 31];
							while (!newdate.equals(countdate)) {
								// 用于跨越天数
								i++;
								if (i < 10)
									countdate = dateStr1 + i;
								else
									countdate = dateStr2 + i;
								if (i > 31) {
									// 如果天数顺序不正确，进行恢复
									for (int j = 1; j < 32; j++) {
										if (j < 10)
											countdate = dateStr1 + j;
										else
											countdate = dateStr2 + j;
										if (newdate.equals(countdate)) {
											i = j;
											break;
										}
										;
									}
									break;
								}

							}
							// 记录每日数据
							a[i] += newup;
							a[i + 31] += newdown;
						}
					} while (cur.moveToNext());
				}
				// 记录累计数据
				a[0] += a[i];
				a[63] += a[i + 31];
			} catch (Exception e) {
				// TODO: handle exception
				showLog("datacur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 格式化日期数据
	 * 
	 * @param input
	 *            输入的日期等int
	 * @return 返回格式化后的String型数据
	 */
	private String formateMonthAndDay(int input) {
		if (input < 10) {
			String data2 = "0" + input;
			return data2;
		} else {
			return input + "";
		}

	}

	/**
	 * 进行数据流量历史累计返回的是到setday之前的那天的数据记录
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param day
	 *            输入查询数据开始的日期.
	 * @param setday
	 *            输入查询数据结束的日期.（同月的话，然后返回整月数据day到setday-1）
	 * @param table
	 *            要查询的数据类型
	 * @return 返回一个3位数组。a[0]为总计流量a[1]总计上传流量a[2]总计下载流量
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			int day, int setday, String table) {
		long[] a = new long[3];
		String month2 = formateMonthAndDay(month);
		String day2 = formateMonthAndDay(day);
		String setday2 = formateMonthAndDay(setday - 1);
		// showLog(month2);
		String string = null;
		int year2 = year + 1;
		String month3 = formateMonthAndDay(month + 1);
		if (day == setday) {
			if (month != 12) {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year + "-"
						+ month3 + "-" + setday2 + AND + "type=" + 3;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 3;
			}
		} else if (setday > day) {
			string = SelectTable + table + Where + "date" + Between + year
					+ "-" + month2 + "-" + day2 + AND_B + year + "-" + month2
					+ "-" + setday2 + AND + "type=" + 3;
		} else {
			// 进行跨年判断
			if (month != 12) {

				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year + "-"
						+ month3 + "-" + setday2 + AND + "type=" + 3;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 3;
			}
		}
		// showLog("testmonthUsetraff" + string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long countup = 0;
		long countdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						countup += cur.getLong(uploadIndex);
						countdown += cur.getLong(downloadIndex);
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = countup + countdown;
		a[1] = countup;
		a[2] = countdown;
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 输出从输入时间到当天截至的某网络总流量--未来作废
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param day
	 *            输入查询的日期.
	 * @param time
	 *            输入查询时间.
	 * @param table
	 *            要查询的数据类型
	 * @return 返回一个3位数组。a[0]为总计流量a[1]为上传流量a[2]下载流量
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			int day, String time, String table) {
		long[] a = new long[3];
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		String day2 = day + "";
		if (day < 10)
			day2 = "0" + day2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "time" + Between + time + AND_B
				+ "23:59:59" + AND + "type=" + 2 + " AND " + "date=" + "'"
				+ year + "-" + month2 + "-" + day2 + "'";
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long countup = 0;
		long countdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						countup += cur.getLong(uploadIndex);
						countdown += cur.getLong(downloadIndex);
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = countdown + countup;
		a[1] = countup;
		a[2] = countdown;
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 清除第一个版本里面多余的type=2数据
	 * 
	 * @param mySQL
	 */
	public void autoClearData(SQLiteDatabase mySQL) {
		// 进行自动数据清理
		initTime();
		if (hour == 3 && minute == 10) {
			String string = null;
			// delete from Yookey where tit not in (select min(tit) from
			// Yookey
			// group by SID)
			string = "DELETE   FROM " + TableMobile + Where + "type=" + 2;
			try {
				mySQL.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
			}
			string = "DELETE   FROM " + TableWiFi + Where + "type=" + 2;
			try {
				mySQL.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
			}
		}
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// Log.d("databaseTotal", string);
	}
}
