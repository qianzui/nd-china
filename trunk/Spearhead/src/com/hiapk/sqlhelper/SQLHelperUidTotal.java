package com.hiapk.sqlhelper;

import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;
import android.widget.SlidingDrawer;

public class SQLHelperUidTotal {
	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidIndex = "SQLUidIndex.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15),states varchar(15)";
	private String CreateparamUidIndex = "uid INTEGER,packagename varchar(255),permission INTEGER,type INTEGER ,other varchar(15)";
	private String CreateparamUidTotal = "uid INTEGER,packagename varchar(255),upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15),states varchar(15)";
	private String TableUid = "uid";
	private String TableUidTotal = "uidtotal";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String DeleteTable = "DELETE FROM ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND = "' AND ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
	private String InsertUidIndexColumnTotal = "uid,packagename,permission";
	private String InsertUidTotalColumn = "uid,packagename,upload,download,permission,type,other,states";
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
	private long uidupload;
	private long uiddownload;
	private static final int MODE_PRIVATE = 0;

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
	 * 初始化uidTotal索引表
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidTotalTables(SQLiteDatabase sqldatabase) {
		// TODO Auto-generated method stub
		String string = null;
		string = CreateTable + TableUidTotal + Start + SQLId
				+ CreateparamUidTotal + End;
		// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
		// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
		// varchar(15),other varchar(15))
		try {
			sqldatabase.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
			showLog("initUidIndexTables-fail");
			return false;
		}
		return true;
	}

	/**
	 * 初始化uidTotal索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumbers
	 *            数据库的表集合
	 */
	protected void exeSQLcreateUidTotaltables(SQLiteDatabase mySQL,
			int[] uidnumbers, String[] packagenames) {
		for (int i = 0; i < uidnumbers.length; i++) {
			if (uidnumbers[i] != -1) {
				long upload = TrafficStats.getUidTxBytes(uidnumbers[i]);
				if (upload == -1)
					upload = 0;
				long download = TrafficStats.getUidRxBytes(uidnumbers[i]);
				if (download == -1)
					download = 0;
				// 输入初始数据
				// 0为安装软件时系统记录的数据
				// 1为临时变量
				// 2为记录的软件使用流量情况
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], upload, download, 0, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], upload, download, 1, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], 0, 0, 2, "wifi");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], 0, 0, 2, "mobile");
			}

		}
	}

	/**
	 * 建立uid索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param packagename
	 *            包名
	 */
	private void exeSQLcreateUidTotaltable(SQLiteDatabase mySQL, int uidnumber,
			String packagename, long upload, long download, int type,
			String other) {
		String string = null;
		string = InsertTable + TableUidTotal + Start + InsertUidTotalColumn
				+ End + Value + uidnumber + split + packagename + split
				+ upload + split + download + split + 0 + split + type + split
				+ other + split + "Install" + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * 对数据库uid数据进行批量更新，
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
	 */
	public void updateSQLUidTypes(Context context, int[] uidnumbers,
			String network) {
		// TODO Auto-generated method stub
		// SQLHelperTotal.isSQLUidTotalOnUsed = true;
		// String other = SQLHelperTotal.TableWiFiOrG23;
		SQLiteDatabase sqlDataBase = creatSQLUidTotal(context);
		sqlDataBase.beginTransaction();
		try {
			for (int uidnumber : uidnumbers) {
				updateSQLUidTotal(sqlDataBase, uidnumber, network);
			}
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("批量输入uidTotal网络数据失败");
		} finally {
			sqlDataBase.endTransaction();
			closeSQL(sqlDataBase);
			// SQLHelperTotal.isSQLUidTotalOnUsed = false;
		}

	}

	/**
	 * 卸载程序时对数据库uidTotal表进行更新
	 * 
	 * @param mySQL
	 * @param uid
	 *            uid
	 * @param states
	 *            程序状态记录
	 */
	public void updateSQLUidTotalStatesOnUnInstall(SQLiteDatabase mySQL,
			int uidname, String states) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + TableUidTotal + UpdateSet + "states='" + states
				+ "'" + Where + "uid='" + uidname + "'";
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
	 * 查询对于uid的之前流量
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param uidnumber
	 *            要查询的uid
	 * @return 返回一个3位数组。a[0]为总流量a[1]为总计上传流量a[2]为总计下载流量
	 */
	private long[] SelectUidTotalData(SQLiteDatabase sqlDataBase,
			int uidnumber, int type) {
		long[] a = new long[3];
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidTotal + Where + "type='" + type + AND
				+ "uid=" + uidnumber;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error@" + string);
		}
		long newup = 0;
		long newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					newup = cur.getLong(uploadIndex);
					newdown = cur.getLong(downloadIndex);
					a[1] = newup;
					a[2] = newdown;
					a[0] = newup + newdown;
					// showLog("a[0]="+a[0]);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
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
	 * 查询对于uid的之前流量
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param uidnumber
	 *            要查询的uid
	 * @return 返回一个3位数组。a[0]为总流量a[1]为总计上传流量a[2]为总计下载流量
	 */
	private long[] SelectUidTotalData(SQLiteDatabase sqlDataBase,
			int uidnumber, int type, String other) {
		long[] a = new long[3];
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		string = SelectTable + TableUidTotal + Where + "type='" + type + AND
				+ "uid='" + uidnumber + AND + "other='" + other + "'";
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error@" + string);
		}
		long newup = 0;
		long newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					newup = cur.getLong(uploadIndex);
					newdown = cur.getLong(downloadIndex);
					a[1] = newup;
					a[2] = newdown;
					a[0] = newup + newdown;
					// showLog("a[0]="+a[0]);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
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
	 * 对数据库uid数据进行更新
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid的table表的uid号
	 */
	private void updateSQLUidTotal(SQLiteDatabase mySQL, int uidnumber,
			String other) {
		// 获得之前记录的临时数据流量
		long[] beforeData1 = SelectUidTotalData(mySQL, uidnumber, 1);
		// 获取当前trafficststa数据
		initUidData(uidnumber);
		// 判断是否数据有变化
		if ((beforeData1[1] == uidupload) && (beforeData1[2] == uiddownload)) {
		} else {
			// 获取之前记录的数据
			long[] beforeData2 = SelectUidTotalData(mySQL, uidnumber, 2, other);
			long newupload;
			long newdownload;
			// 计算增加的流量
			if (uidupload > beforeData1[1]) {
				newupload = uidupload - beforeData1[1];
			} else {
				newupload = uidupload;
			}
			if (uiddownload > beforeData1[2]) {
				newdownload = uiddownload - beforeData1[2];
			} else {
				newdownload = uiddownload;
			}
			// 更新两个数据
			updateSQLUidTotalType(mySQL, uidnumber, beforeData2[1] + newupload,
					beforeData2[2] + newdownload, 2, other);
			updateSQLUidTotalType(mySQL, uidnumber, uidupload, uiddownload, 1);
		}

	}

	/**
	 * 对数据库uid数据更新操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 * @param upload
	 * @param download
	 */
	private void updateSQLUidTotalType(SQLiteDatabase mySQL, int uidnumber,
			long upload, long download, int type, String other) {
		String string = null;
		string = UpdateTable + TableUidTotal + UpdateSet + "upload='" + upload
				+ "',download='" + download + "'" + Where + "type=" + type
				+ " AND " + "other='" + other + "'" + " AND " + "uid="
				+ uidnumber;
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
	 * 对数据库uid数据更新操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 * @param upload
	 * @param download
	 */
	private void updateSQLUidTotalType(SQLiteDatabase mySQL, int uidnumber,
			long upload, long download, int type) {
		String string = null;
		string = UpdateTable + TableUidTotal + UpdateSet + "upload='" + upload
				+ "',download='" + download + "'" + Where + "type=" + type
				+ " AND " + "uid=" + uidnumber;
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
	 * 查询uid总流量
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param uidnumber
	 *            要查询的uid
	 * @return 返回一个2位数组。rate[0]为总计mobile，rate[1]为wifi
	 */
	public long[] SelectUidNetData(Context context, int uidnumber) {
		SQLiteDatabase sqlDataBase = creatSQLUidTotal(context);
		long[] mobileData = new long[3];
		long[] wifiData = new long[3];
		mobileData = SelectUidNetTotalData(sqlDataBase, uidnumber, "mobile");
		wifiData = SelectUidNetTotalData(sqlDataBase, uidnumber, "wifi");
		closeSQL(sqlDataBase);
		long[] rate = new long[2];
		rate[0] = mobileData[0];
		rate[1] = wifiData[0];
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return rate;
	}

	/**
	 * 查询uid分流量
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param uidnumber
	 *            要查询的uid
	 * @return 返回一个3位数组。a[0]为总流量a[1]为总计上传流量a[2]为总计下载流量
	 */
	private long[] SelectUidNetTotalData(SQLiteDatabase sqlDataBase,
			int uidnumber, String other) {
		long[] a = new long[3];
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidTotal + Where + "type='" + 2 + AND
				+ "uid='" + uidnumber + AND + "other='" + other + "'";
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error@" + string);
		}
		long newup = 0;
		long newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					newup = cur.getLong(uploadIndex);
					newdown = cur.getLong(downloadIndex);
					a[1] = newup;
					a[2] = newdown;
					a[0] = newup + newdown;
					// showLog("a[0]="+a[0]);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
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
	 * 清空uidTotal对应UID
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            要清空的uid表
	 */
	public void DeleteUnusedUidTotalDatabyUid(SQLiteDatabase mySQL, int uid) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = DeleteTable + TableUidTotal + Where + "uid='" + uid + "'";
		// string = InsertTable + TableUidIndex + Start
		// + InsertUidIndexColumnTotal + ",other" + End + Value
		// + uidnumber + split + packagename + split + 0 + split
		// + "Install" + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		// showLog(string);
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * 清空uidTotal对应UID
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            要清空的uid表
	 */
	public void DeleteUnusedUidTotalDatabyPacname(SQLiteDatabase mySQL,
			String packagename) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = DeleteTable + TableUidTotal + Where + " packagename='"
				+ packagename + "'";
		// string = InsertTable + TableUidIndex + Start
		// + InsertUidIndexColumnTotal + ",other" + End + Value
		// + uidnumber + split + packagename + split + 0 + split
		// + "Install" + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		showLog(string);
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	public List<Integer> updateSQLUidTotalOnInstallgetDel(Context context,
			int uidnumber, String packageName, String other, int[] uids) {
		List<Integer> uid_List_Del = new LinkedList<Integer>();
		// List<Integer> uid_List_Add = new LinkedList<Integer>();
		SQLiteDatabase mySQL = creatSQLUidTotal(context);
		// 更新UidTotal数据库
		mySQL = creatSQLUidTotal(context);
		mySQL.beginTransaction();
		try {
			// 选择现有的uid数据
			String string = null;
			// select oldest upload and download 之前记录的数据的查询操作
			// SELECT * FROM table WHERE type=0
			string = "SELECT * FROM " + TableUidTotal + Where + "type='" + "0"
					+ "'";
			try {
				cur = mySQL.rawQuery(string, null);
				// showLog(string);
			} catch (Exception e) {
				// TODO: handle exception
				showLog("fail-List-cur" + string);
			}
			String[] pacs_hasset = new String[cur.getCount()];
			int[] uid_hasset = new int[cur.getCount()];
			// showLog("curnumber="+cur.getCount()+"");
			if (cur != null) {
				try {
					int pac = cur.getColumnIndex("packagename");
					int uid = cur.getColumnIndex("uid");
					// showLog(cur.getColumnIndex("minute") + "");
					int i = 0;
					// showLog("i ="+i );
					if (cur.moveToFirst()) {
						do {
							pacs_hasset[i] = (String) cur.getString(pac);
							uid_hasset[i] = cur.getInt(uid);
							i++;
						} while (cur.moveToNext());
					}
				} catch (Exception e) {
					// TODO: handle exception
					showLog("cur-searchfail" + e);
				}
			}
			if (cur != null) {
				cur.close();
			}

			// if (uids != null && uids[0] != 1019) {
			// for (int i = 0; i < uids.length; i++) {
			// DeleteUnusedUidTotalData(mySQL, uids[i]);
			// }
			// }

			// showLog("新安装软件" + packageName + uidnumber);
			// 删除多余的uid数据
			for (int i = 0; i < pacs_hasset.length; i++) {
				// if (!SQLStatic.packagename_ALL.contains(pacs_hasset[i]))
				// {
				// 找出存在的新软件
				if (!SQLStatic.packagename_ALL.contains(pacs_hasset[i])) {
					// showLog("不包含的" + pacs_hasset[i]);
					DeleteUnusedUidTotalDatabyPacname(mySQL,
							pacs_hasset[i].trim());
					uid_List_Del.add(uid_hasset[i]);
					// } else {
					// int uid = 999999;
					// showLog("show！=-1packagenmae=" + pacs_hasset[i]);
					// try {
					// PackageManager pm = context.getPackageManager();
					// ApplicationInfo ai = pm.getApplicationInfo(
					// pacs_hasset[i],
					// PackageManager.GET_ACTIVITIES);
					// uid = ai.uid;
					// // Log.d("!!", "!!" + ai.uid);
					// } catch (NameNotFoundException e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// showLog("存在未添加的packagenmae=" + pacs_hasset[i] + uid);
					// }
					// uid_List.add(uid);
					// exeSQLcreateUidTotaltables(mySQL, new int[] { uid },
					// new String[] { pacs_hasset[i] });
				}
			}

			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("更新索引表失败");
		} finally {
			mySQL.endTransaction();
		}
		closeSQL(mySQL);

		return uid_List_Del;
	}

	public List<Integer> updateSQLUidTotalOnInstallgetAdd(Context context,
			int uidnumber, String packageName, String other, int[] uids) {
		// List<Integer> uid_List_Del = new LinkedList<Integer>();
		List<Integer> uid_List_Add = new LinkedList<Integer>();
		SQLiteDatabase mySQL = creatSQLUidTotal(context);
		// 更新UidTotal数据库
		mySQL = creatSQLUidTotal(context);
		// mySQL.beginTransaction();
		// try {
		// // 选择现有的uid数据
		// String string = null;
		// // select oldest upload and download 之前记录的数据的查询操作
		// // SELECT * FROM table WHERE type=0
		// string = "SELECT * FROM " + TableUidTotal + Where + "type='" + "0"
		// + "'";
		// try {
		// cur = mySQL.rawQuery(string, null);
		// // showLog(string);
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("fail-List-cur" + string);
		// }
		// String[] pacs_hasset = new String[cur.getCount()];
		// int[] uid_hasset = new int[cur.getCount()];
		// // showLog("curnumber="+cur.getCount()+"");
		// if (cur != null) {
		// try {
		// int pac = cur.getColumnIndex("packagename");
		// int uid = cur.getColumnIndex("uid");
		// // showLog(cur.getColumnIndex("minute") + "");
		// int i = 0;
		// // showLog("i ="+i );
		// if (cur.moveToFirst()) {
		// do {
		// pacs_hasset[i] = (String) cur.getString(pac);
		// uid_hasset[i] = cur.getInt(uid);
		// i++;
		// } while (cur.moveToNext());
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("cur-searchfail" + e);
		// }
		// }
		// cur.close();
		//
		// // if (uids != null && uids[0] != 1019) {
		// // for (int i = 0; i < uids.length; i++) {
		// // DeleteUnusedUidTotalData(mySQL, uids[i]);
		// // }
		// // }
		//
		// if (uids != null) {
		// // showLog("新安装软件" + packageName + uidnumber);
		// // 删除多余的uid数据
		// for (int i = 0; i < pacs_hasset.length; i++) {
		// if (!SQLStatic.packagename_ALL.contains(pacs_hasset[i]))
		// {
		// 找出存在的新软件
		// if (!SQLStatic.packagename_ALL.contains(pacs_hasset[i]))
		// {
		// showLog("不包含的" + pacs_hasset[i]);
		// DeleteUnusedUidTotalDatabyPacname(mySQL, pacs_hasset[i]);
		// uid_List_Del.add(uid_hasset[i]);
		// // } else {
		// // int uid = 999999;
		// // showLog("show！=-1packagenmae=" + pacs_hasset[i]);
		// // try {
		// // PackageManager pm = context.getPackageManager();
		// // ApplicationInfo ai = pm.getApplicationInfo(
		// // pacs_hasset[i],
		// // PackageManager.GET_ACTIVITIES);
		// // uid = ai.uid;
		// // // Log.d("!!", "!!" + ai.uid);
		// // } catch (NameNotFoundException e1) {
		// // // TODO Auto-generated catch block
		// // e1.printStackTrace();
		// // showLog("存在未添加的packagenmae=" + pacs_hasset[i] + uid);
		// // }
		// // uid_List.add(uid);
		// // exeSQLcreateUidTotaltables(mySQL, new int[] { uid },
		// // new String[] { pacs_hasset[i] });
		// }
		// 不再表中的进行新加
		// if (!(pacs_hasset[i].indexOf(SQLStatic.packagename_ALL) != -1)) {
		uid_List_Add.add(uidnumber);
		exeSQLcreateUidTotaltables(mySQL, new int[] { uidnumber },
				new String[] { packageName });
		// }

		// }
		// // 需要添加的包
		// // uid_List.add(uidnumber);
		//
		// }
		// mySQL.setTransactionSuccessful();
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("更新索引表失败");
		// } finally {
		// mySQL.endTransaction();
		// }
		closeSQL(mySQL);

		return uid_List_Add;
	}

	/**
	 * 初始化流量数据
	 * 
	 * @param uidnumber
	 *            依据uid获取流量数据
	 */
	private void initUidData(int uidnumber) {
		uidupload = TrafficStats.getUidTxBytes(uidnumber);
		uiddownload = TrafficStats.getUidRxBytes(uidnumber);
		if (uidupload == -1) {
			uidupload = 0;
		}
		if (uiddownload == -1) {
			uiddownload = 0;
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
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
//		Log.d("databaseUidTotal", string);
	}

}
