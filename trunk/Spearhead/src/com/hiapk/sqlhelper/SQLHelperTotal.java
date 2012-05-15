package com.hiapk.sqlhelper;

import com.hiapk.broadcreceiver.AlarmSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

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
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
	public static String TableWiFiOrG23 = "mobile";
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
	private String time;
	// data
	private long upload;
	private long download;
	private static final int MODE_PRIVATE = 0;
	// pre
	private final String PREFS_NAME = "allprefs";
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";
	// classes
	SQLHelperUid SQLhelperuid = new SQLHelperUid();
	// 数据库正在使用。重要中。
	public static boolean isSQLOnUsed = false;

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
	private void statsSQLtotal(SQLiteDatabase mySQL, String table, String date,
			String time, long upload, long download, int type, String other,
			boolean daily) {
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
		long oldup = 0;
		long olddown = 0;
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// 获得之前的上传下载值
					oldup = cur.getLong(minup);
					olddown = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("数据库搜索失败");
			}
		}
		cur.close();
		// 初始化写入数据（wifi以及mobile）
		// 如果之前数据大于新的数据，则重新计数
		if (oldup > upload || olddown > download) {
			oldup = upload;
			olddown = download;
		} else {
			oldup = upload - oldup;
			olddown = download - olddown;
		}
		if (daily) {
			// showLog("上传数据" + oldup + "B" + "  " + "下载数据" + olddown + "B");
			// 输入实际数据进入数据库
			updateSQLtotalType(mySQL, table, oldup, olddown, 0, other, type);
			// 添加新的两行数据
			updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			exeSQLtotal(mySQL, table, 1, other);
		} else if (olddown != 0 && oldup != 0) {
			// showLog("上传数据" + oldup + "B" + "  " + "下载数据" + olddown + "B");
			// 输入实际数据进入数据库
			updateSQLtotalType(mySQL, table, oldup, olddown, 0, other, type);
			// 添加新的两行数据
			updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			exeSQLtotal(mySQL, table, 1, other);
		}
	}

	/**
	 * 对数据库进行wifi，mobile数据的写入新数据操作的操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，2g/3g等
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 */
	private void exeSQLtotal(SQLiteDatabase mySQL, String table, int type,
			String other) {
		// TODO Auto-generated method stub
		String string = null;
		initTotalData(table);
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
	 * 初始化数据库
	 * 
	 * @param context
	 * @param uidnumbers
	 *            uid集合
	 * @param packagename
	 *            uid对应的包名
	 */
	public void initSQL(Context context, int[] uidnumbers, String[] packagename) {
		// 初始化网络状态
		initTablemobileAndwifi(context);
		initTime();
		// 初始化数据库
		boolean initsuccess = true;
		SQLiteDatabase sqldatabaseTotal = creatSQLTotal(context);
		SQLiteDatabase sqldatabaseUid = creatSQLUid(context);
		sqldatabaseTotal.beginTransaction();
		try {
			String string = null;
			string = CreateTable + TableWiFi + Start + SQLId + SQLTime
					+ CreateparamWiFiAnd23G + End;
			// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
			// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
			// varchar(15),other varchar(15))
			try {
				sqldatabaseTotal.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
				initsuccess = false;
			}
			// 初始化wifi的type=0及type=1的数据
			initTotalData(TableWiFi);
			exeSQLtotal(sqldatabaseTotal, TableWiFi, 0, null);
			exeSQLtotal(sqldatabaseTotal, TableWiFi, 1, null);
			string = CreateTable + TableMobile + Start + SQLId + SQLTime
					+ CreateparamWiFiAnd23G + End;
			// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
			// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
			// varchar(15),other varchar(15))
			try {
				sqldatabaseTotal.execSQL(string);
			} catch (Exception e) {
				// TODO: handle exception
				showLog(string);
				showLog("mobiletable-already exist");
				initsuccess = false;
			}
			// 初始化mobile数据
			initTotalData(TableMobile);
			// 初始化mobile的type=0及type=1的数据
			exeSQLtotal(sqldatabaseTotal, TableMobile, 0, null);
			exeSQLtotal(sqldatabaseTotal, TableMobile, 1, null);
			sqldatabaseTotal.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("初始化Total失败");
			initsuccess = false;
		} finally {
			sqldatabaseTotal.endTransaction();
		}

		sqldatabaseUid.beginTransaction();
		try {
			try {
				// 初始化uid数据库的Index表
				if (initsuccess)
					initsuccess = SQLhelperuid
							.initUidIndexTables(sqldatabaseUid);
				// 不包含uid=0的
				SQLhelperuid.exeSQLcreateUidIndextables(sqldatabaseUid,
						uidnumbers, packagename);
			} catch (Exception e) {
				// TODO: handle exception
				initsuccess = false;
				showLog("初始化uidIndex数据表失败");
			}
			sqldatabaseUid.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("初始化Total失败");
			initsuccess = false;
		} finally {
			sqldatabaseUid.endTransaction();
		}

		sqldatabaseUid.beginTransaction();
		try {
			try {
				// 清除重复表
				uidnumbers = SQLhelperuid.sortUids(uidnumbers);
				// 初始化uid数据库这里使用初始化后的全部uids表
				SQLhelperuid.initUidTables(sqldatabaseUid, uidnumbers);
			} catch (Exception e) {
				// TODO: handle exception
				initsuccess = false;
				showLog("初始化uid数据库失败");
			}
			sqldatabaseUid.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("初始化Total失败");
			initsuccess = false;
		} finally {
			sqldatabaseUid.endTransaction();
		}
		if (initsuccess) {
			// 确保仅进行一次初始化
			Editor passfileEditor = context.getSharedPreferences(PREFS_NAME, 0)
					.edit();
			passfileEditor.putString(PREF_INITSQL, MODE_HASINIT);
			passfileEditor.commit();// 委托，存入数据
			// 开启计时
			AlarmSet alset = new AlarmSet();
			alset.StartAlarm(context);
		}
		closeSQL(sqldatabaseTotal);
		closeSQL(sqldatabaseUid);
	}

	/**
	 * 用于初始化网络状态确定当前使用何种网络
	 * 
	 * @param context
	 */
	public void initTablemobileAndwifi(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo() != null) {
			NetworkInfo info = connec.getActiveNetworkInfo();
			String typeName = info.getTypeName(); // mobile@wifi
			if (typeName.equals("WIFI"))
				TableWiFiOrG23 = "wifi";
			if (typeName.equals("mobile"))
				TableWiFiOrG23 = "mobile";
			// showLog("何种方式连线" + typeName);
		} else {
			TableWiFiOrG23 = "";
			// showLog("无可用网络");
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
	 * 外部程序获取系统时间
	 * 
	 * @return
	 */
	public String gettime() {
		initTime();
		return time;
	}

	/**
	 * 设置IsInit与程序记录同步
	 * 
	 * @param context
	 */
	public boolean getIsInit(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getString(PREF_INITSQL, MODE_NOTINIT).endsWith(
				MODE_HASINIT);
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
	public void RecordTotalwritestats(Context context, boolean daily) {
		// TODO Auto-generated method stub
		// 自动进行数据记录---不记录上传下载为0的数据
		if (!TableWiFiOrG23.equals("")) {
			SQLiteDatabase sqlDataBase = creatSQLTotal(context);
			initTotalData(TableWiFiOrG23);
			initTime();
			statsSQLtotal(sqlDataBase, TableWiFiOrG23, date, time, upload,
					download, 2, null, daily);
			closeSQL(sqlDataBase);
		}
	}

	/**
	 * 进行Wifi历史流量查询
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] SelectWifiData(Context context, int year, int month) {
		return SelectData(context, year, month, TableWiFi);
	}

	/**
	 * 进行移动数据流量历史流量查询
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] SelectMobileData(Context context, int year, int month) {
		return SelectData(context, year, month, TableMobile);
	}

	/**
	 * 进行移动数据流量历史流量查询从某一天到另一设定天
	 * 
	 * @param context
	 *            context
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
	public long[] SelectMobileData(Context context, int year, int month,
			int day, int dayset) {
		return SelectData(context, year, month, day, dayset, TableMobile);
	}

	/**
	 * 进行移动数据流量从设定时间点到当前的流量查询
	 * 
	 * @param context
	 *            context
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
	public long[] SelectMobileData(Context context, int year, int month,
			int day, String time) {

		return SelectData(context, year, month, day, time, TableMobile);
	}

	/**
	 * 进行数据流量历史流量查询
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param table
	 *            要查询的数据类型
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	private long[] SelectData(Context context, int year, int month, String table) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = creatSQLTotal(context);
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "type=" + 2;
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
		cur.close();
		closeSQL(sqlDataBase);
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
	 * @param context
	 *            context
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
	private long[] SelectData(Context context, int year, int month, int day,
			int setday, String table) {
		long[] a = new long[3];
		SQLiteDatabase sqlDataBase = creatSQLTotal(context);
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
						+ month3 + "-" + setday2 + AND + "type=" + 2;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 2;
			}
		} else if (setday > day) {
			string = SelectTable + table + Where + "date" + Between + year
					+ "-" + month2 + "-" + day2 + AND_B + year + "-" + month2
					+ "-" + setday2 + AND + "type=" + 2;
		} else {
			// 进行跨年判断
			if (month != 12) {

				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year + "-"
						+ month3 + "-" + setday2 + AND + "type=" + 2;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 2;
			}
		}
		showLog(string);
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
		cur.close();
		closeSQL(sqlDataBase);
		a[0] = countup + countdown;
		a[1] = countup;
		a[2] = countdown;
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 输出从输入时间到当天截至的某网络总流量
	 * 
	 * @param context
	 *            context
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
	private long[] SelectData(Context context, int year, int month, int day,
			String time, String table) {
		long[] a = new long[3];
		SQLiteDatabase sqlDataBase = creatSQLTotal(context);
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
		cur.close();
		closeSQL(sqlDataBase);
		a[0] = countdown + countup;
		a[1] = countup;
		a[2] = countdown;
		for (int j = 0; j < a.length; j++) {
			showLog(j + "liuliang" + a[j] + "");
		}
		return a;
	}

	/**
	 * 进行数据周使用量查询
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param monthDay
	 *            单月的日期
	 * @param weekDay
	 *            星期
	 * @return 返回一个6位数组。a[0]为移动网络总计流量a[5]为wifi网络总计流量
	 *         a[1]-a[2]移动网络的上传，下载流量，a[3]-a[4]为wifi网络的上传，下载流量
	 */
	public long[] SelectWeekData(Context context, int year, int month,
			int monthDay, int weekDay) {
		if (weekDay == 0) {
			weekDay = 7;
		}
		// showLog(weekDay + "");
		String weekStart = null;
		SQLiteDatabase sqlDataBase = creatSQLTotal(context);
		String string = null;
		string = "select date('now'" + ",'-" + weekDay + " day'" + ")";
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		if (cur != null) {
			try {
				int dateIndex = cur.getColumnIndex("date('now'" + ",'-"
						+ weekDay + " day'" + ")");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					weekStart = cur.getString(dateIndex);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		String monthDay2 = monthDay + "";
		if (monthDay < 10)
			monthDay2 = "0" + month2;
		long[] a = new long[6];
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableMobile + Where + "date" + Between
				+ weekStart + AND_B + year + "-" + month2 + "-" + monthDay2
				+ AND + "type=" + 2;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long newup = 0;
		long newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						newup = cur.getLong(uploadIndex);
						newdown = cur.getLong(downloadIndex);
						// 进行数据累加
						a[1] += newup;
						a[2] += newdown;
					} while (cur.moveToNext());
				}
				// 记录累计数据
				a[0] = a[1] + a[2];
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		string = SelectTable + TableWiFi + Where + "date" + Between + weekStart
				+ AND_B + year + "-" + month2 + "-" + monthDay2 + AND + "type="
				+ 2;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		newup = 0;
		newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						newup = cur.getLong(uploadIndex);
						newdown = cur.getLong(downloadIndex);
						// showLog(newup + "");
						// 进行数据累加
						a[3] += newup;
						a[4] += newdown;
					} while (cur.moveToNext());
				}
				// 记录累计数据
				a[5] = a[3] + a[4];
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		closeSQL(sqlDataBase);
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}
}
