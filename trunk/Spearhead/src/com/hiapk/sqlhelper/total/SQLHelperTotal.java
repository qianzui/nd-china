package com.hiapk.sqlhelper.total;

import com.hiapk.bean.TotalTraffs;
import com.hiapk.logs.Logs;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperTotal {

	public SQLHelperTotal() {
		super();
	}

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
	// log
	private String TAG = "databaseTotal";

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
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append(table).append(UpdateSet)
				.append("date='").append(date).append("',time='").append(time)
				.append("',upload='").append(upload).append("',download='")
				.append(download).append("' ,type=").append(typechange)
				.append(Where).append("type=").append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
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
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append(table).append(UpdateSet)
				.append("time='").append(time).append("',upload='")
				.append(upload).append("',download='").append(download)
				.append("' ,type=").append(typechange).append(Where)
				.append("date='").append(date).append(AND).append("type=")
				.append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
	}

	private void updateSQLtotalTypeDate0to3(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append(table).append(UpdateSet)
				.append("time='").append(time).append("',upload='")
				.append(upload).append("',download='").append(download)
				.append("' ,type=").append(typechange).append(" ,date='")
				.append(date).append("' ").append(Where).append("type=")
				.append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
	}

	// /**
	// * 对数据库总体数据进行更新,自动生成时间，日期上传，下载流量
	// *
	// * @param mySQL
	// * 进行写入操作的数据库SQLiteDatagase
	// * @param table
	// * 数据库的表：有wifi，mobile等
	// * @param type
	// * 用于记录数据状态，以统计数据
	// * @param other
	// * 用于记录特殊数据等
	// * @param typechange
	// * 改变type值2的值为数据库中实际记录数据的数值
	// */
	// public void updateSQLtotalType(SQLiteDatabase mySQL, String table,
	// int type, String other, int typechange) {
	// initTime();
	// long[] totalTraff = SQLHelperDataexe.initTotalData(table);
	// StringBuilder string = new StringBuilder();
	// string = UpdateTable + table + UpdateSet + "date='" + date + "',time='"
	// + time + "',upload='" + totalTraff[0] + "',download='"
	// + totalTraff[1] + "' ,type=" + typechange + Where + "type="
	// + type;
	// // UPDATE Person SET
	// // date='date',time='time',upload='upload',download='download'
	// // ,type='typechange' WHERE type=type
	// try {
	// mySQL.execSQL(string.toString());
	// } catch (Exception e) {
	// Logs.d(TAG, string.toString() + "fail" + e);
	// }
	// }

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
		StringBuilder string = new StringBuilder();
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = string.append(SelectTable).append(table).append(Where)
				.append("type=").append(0);
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
		long oldup0 = -50;
		long olddown0 = -50;

		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") ).append( "");
				if (cur.moveToFirst()) {
					// 获得之前的上传下载值
					oldup0 = cur.getLong(minup);
					olddown0 = cur.getLong(mindown);
				}
			} catch (Exception e) {
				Logs.d(TAG, "数据库搜索失败");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		// showLog("oldup0=" + oldup0 + "olddown0=" + olddown0);
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100) {
			if (oldup0 < 0)
				oldup0 = 0;
			if (olddown0 < 0)
				olddown0 = 0;
			cur = null;
			// 初始化写入数据（wifi以及mobile）
			// 如果之前数据大于新的数据，则重新计数
			if ((oldup0 > (upload + 10000)) || (olddown0 > (download + 10000))) {
				oldup0 = upload;
				olddown0 = download;

			} else {
				oldup0 = upload - oldup0;
				olddown0 = download - olddown0;
			}
			// showLog("oldup0 up=" + oldup0 + "olddown0 down=" + olddown0);
			if ((olddown0 != 0 || oldup0 != 0)
					&& ((olddown0 > 512) || (oldup0 > 512))) {
				string = new StringBuilder();
				string = string.append(SelectTable).append(table).append(Where)
						.append("date='").append(date).append(AND)
						.append("type=").append(3);
				try {
					cur = mySQL.rawQuery(string.toString(), null);
				} catch (Exception e) {
					Logs.d(TAG, string.toString() + "fail" + e);
				}
				long oldup3 = 0;
				long olddown3 = 0;
				String olddate3 = "";
				// 进行添加 覆盖+
				// showLog("cur.move" + cur.moveToFirst());
				if (cur.moveToFirst()) {
					// showLog("已有单天数据");
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
						Logs.d(TAG, "cur-searchfail");
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
							if (beforemobile == -100) {
								beforemobile = 0;
							}
							beforemobile = beforemobile + oldup0 + olddown0;
							sharedData.setMonthHasUsedStack(beforemobile);
						}

					}
					// 进行添加add
				} else {
					// showLog("无单天数据");
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
						if (beforemobile == -100) {
							beforemobile = 0;
						}
						beforemobile = beforemobile + oldup0 + olddown0;
						sharedData.setMonthHasUsedStack(beforemobile);
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
		StringBuilder string = new StringBuilder();
		string = string.append(InsertTable).append(table).append(Start)
				.append(InsertColumnTotal).append(End).append(Value)
				.append(date).append(split).append(time).append(split)
				.append(upload).append(split).append(download).append(split)
				.append(type).append(split).append(other).append("'" + End);
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')

		try {
			mySQL.execSQL(string.toString());
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
	 * 记录wifi，mobile流量数据
	 * 
	 * @param context
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void RecordTotalwritestats(Context context,
			SQLiteDatabase sqlDataBase, boolean daily, String network) {
		// 自动进行数据记录---不记录上传下载为0的数据
		// SQLiteDatabase sqlDataBase = creatSQLTotal(context);
		initTime();
		TotalTraffs totalTraff = SQLHelperDataexe.initTotalData();
		// showLog("upload=" + totalTraff[0] + "download=" + totalTraff[1]);
		statsSQLtotal(context, sqlDataBase, "mobile", date, time,
				totalTraff.getMobileUpload(), totalTraff.getMobileDownload(),
				2, null, daily);
		// closeSQL(sqlDataBase);
		// showLog("upload=" + totalTraff[0] + "download=" + totalTraff[1]);
		statsSQLtotal(context, sqlDataBase, "wifi", date, time,
				totalTraff.getWifiUpload(), totalTraff.getWifiDownload(), 2,
				null, daily);
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
		StringBuilder string = new StringBuilder();
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = string.append(SelectTable).append(table).append(Where)
				.append("date").append(Between).append(year).append("-")
				.append(month2).append("-").append("01").append(AND_B)
				.append(year).append("-").append(month2).append("-")
				.append("31").append(AND).append("type=").append(3);
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
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
				Logs.d(TAG, "datacur-searchfail");
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
	 * 清除第一个版本里面多余的type=2数据
	 * 
	 * @param mySQL
	 */
	public void autoClearData(SQLiteDatabase mySQL) {
		// 进行自动数据清理
		initTime();
		if (hour == 3 && minute == 10) {
			StringBuilder string = new StringBuilder();
			// delete from Yookey where tit not in (select min(tit) from
			// Yookey
			// group by SID)
			string = string.append("DELETE   FROM ").append(TableMobile)
					.append(Where).append("type=").append(2);
			try {
				mySQL.execSQL(string.toString());
			} catch (Exception e) {
				Logs.d(TAG, string.toString() + "fail" + e);
			}
			string = new StringBuilder();
			string = string.append("DELETE   FROM ").append(TableWiFi)
					.append(Where).append("type=").append(2);
			try {
				mySQL.execSQL(string.toString());
			} catch (Exception e) {
				Logs.d(TAG, string.toString() + "fail" + e);
			}
		}
	}

}
