package com.hiapk.sqlhelper;

import java.util.List;

import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperUid {

	public SQLHelperUid() {
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
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String CreateparamUidIndex = "uid INTEGER,packagename varchar(255),permission,other varchar(15)";
	private String TableUid = "uid";
	private String TableUidIndex = "uidindex";
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
		SQLStatic.uidnumbers = selectUidnumbers(context);
		// SQLStatic.packagename_ALL = selectPackagenames(context);
		SQLStatic.packagename_ALL = sharedData.getPackageNames();
		if (SQLStatic.packagename_ALL.contains(packageName)) {
			// 覆盖安装
			return null;
		} else {
			// 新安装软件
			SQLStatic.packagename_ALL = selectPackagenames(context);
			sharedData.setPackageNames(SQLStatic.packagename_ALL);
			return new int[] { 1019 };
		}

		// int[] uids = null;
		// // 更新uidIndex数据
		// SQLiteDatabase mySQL = creatSQLUidIndex(context);
		// mySQL.beginTransaction();
		// int j = 0;
		// try {
		// exeSQLUidIndextable(mySQL, uidnumber, packageName, other);
		// SQLStatic.isCoverInstall = isCoveringInstall(mySQL, packageName,
		// uidnumber);
		// // 判断是否覆盖安装。
		// showLog(SQLStatic.isCoverInstall + "是否覆盖安装" + packageName
		// + uidnumber);
		// if (SQLStatic.isCoverInstall) {
		// // showLog("覆盖安装" + packageName + uidnumber);
		// updateSQLUidIndexOther(mySQL, packageName, uidnumber, other);
		// sortSQLUidIndex(mySQL);
		// return new int[] { uidnumber };
		// }
		//
		// uids = delSQLUidIndexAndTable(mySQL);
		// for (int i = 0; i < uids.length; i++) {
		// if (isUidExistingInUidIndex(mySQL, packageName, uids[i])) {
		// uids[i] = 0;
		// j++;
		// }
		// }
		// mySQL.setTransactionSuccessful();
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("更新索引表失败");
		// } finally {
		// mySQL.endTransaction();
		// }
		// closeSQL(mySQL);
		// if ((uids.length - j) > 0) {
		// int[] uidnumbers = new int[uids.length - j];
		// int k = 0;
		// for (int i = 0; i < uids.length; i++) {
		// if (uids[i] != 0) {
		// uidnumbers[k] = uids[i];
		// k++;
		// }
		// }
		// return uidnumbers;
		// } else {
		// return new int[] { 1019 };
		// }

	}

	public void updateSQLUidOnInstall(Context context, int uidnumber,
			String packageName, String other, List<Integer> uid_List_Add,
			List<Integer> uid_List_Del) {
		SQLiteDatabase mySQL = creatSQLUid(context);
		// 更新Uid数据库
		mySQL = creatSQLUid(context);
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
					initTime();
					initUidTable(mySQL, uid);
					String string = "";
					string = SelectTable + "uid" + uid + Where + "type=" + 0;
					// showLog(string);
					try {
						cur = mySQL.rawQuery(string, null);
					} catch (Exception e) {
						// TODO: handle exception
						// 搜索失败则新建表
						showLog("selectfail" + string);
					}
					if (!cur.moveToFirst()) {
						exeSQLcreateUidtable(mySQL, date, time, uid, 0, null);
						exeSQLcreateUidtable(mySQL, date, time, uid, 1, null);
					} else {
						cur.close();
					}

				}

			}
			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("更新索引表失败");
		} finally {
			mySQL.endTransaction();
			closeSQL(mySQL);
		}

	}

	/**
	 * 安装新软件时依据包名对数据库uidIndex以及other进行更新
	 * 
	 * @param mySQL
	 * @param packagename
	 *            包名
	 * @param uidnumber
	 *            程序uid
	 * @param other
	 *            程序状态记录
	 */
	private void updateSQLUidIndexOther(SQLiteDatabase mySQL,
			String packagename, int uidnumber, String other) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + TableUidIndex + UpdateSet + "other='" + other
				+ "'" + Where + "packagename='" + packagename + AND + " uid="
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
	 * 卸载程序时对数据库uidIndex表进行更新
	 * 
	 * @param mySQL
	 * @param packagename
	 *            包名
	 * @param other
	 *            程序状态记录
	 */
	public void updateSQLUidIndexOtherOnUnInstall(SQLiteDatabase mySQL,
			String packagename, String other) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + TableUidIndex + UpdateSet + "other='" + other
				+ "'" + Where + "packagename='" + packagename + "'";
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		// 更新
	}

	/**
	 * 对数据库uid数据进行更新
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param date
	 *            记录进数据库的日期
	 * @param time
	 *            记录进数据库的时间
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
			String time, int uidnumber, int type, String other, int typechange) {
		initUidData(uidnumber);
		// TODO Auto-generated method stub
		if (uidupload != 0 || uiddownload != 0) {
			String string = null;
			string = UpdateTable + "uid" + uidnumber + UpdateSet + "date='"
					+ date + "',time='" + time + "',upload='" + uidupload
					+ "',download='" + uiddownload + "' ,type=" + typechange
					+ ", other=" + "'" + other + "'" + Where + "type=" + type;
			// UPDATE Person SET
			// date='date',time='time',upload='upload',download='download'
			// ,type='typechange' WHERE type=type
			try {
				mySQL.execSQL(string);
			} catch (Exception e) {
				initUidTables(mySQL, new int[] { uidnumber });
				// TODO: handle exception
				showLog(string);
			}
		}

	}

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

	/**
	 * 对数据库uid数据进行批量更新，自动生成时间和上传下载数据
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库sqlDataBase
	 * @param uidnumbers
	 *            数据库的表数组集合：uid的table表的uid号，
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param typechange
	 *            改变type值
	 */
	public void updateSQLUidTypes(SQLiteDatabase sqlDataBase, int[] uidnumbers,
			int type, String other, int typechange) {
		// TODO Auto-generated method stub
		initTime();
		for (int uidnumber : uidnumbers) {
			updateSQLUidType(sqlDataBase, date, time, uidnumber, type, other,
					typechange);
		}

	}

	/**
	 * 提取所有应用的不重复uid集合
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库
	 * @return
	 */
	public int[] selectUidnumbers(Context context) {
		
		int j = 0;
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		int[] uidstemp = new int[packages.size()];
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = Block.filter;
			String pacname = packageinfo.packageName;
			int uid = packageinfo.applicationInfo.uid;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					uidstemp[j] = uid;
					showLog("进行显示的uid=" + uid);
					j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		int[] uids = new int[j];
		for (int i = 0; i < j; i++) {
			uids[i] = uidstemp[i];
		}
		return uids;
	}

	/**
	 * 提取所有应用的不重复包名
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库
	 * @return
	 */
	public String selectPackagenames(Context context) {
		// TODO Auto-generated method stub
		// SQLiteDatabase sqlDataBase = creatSQLUidIndex(context);
		// String string = null;
		// // select oldest upload and download 之前记录的数据的查询操作
		// // SELECT * FROM table WHERE type=0
		// string = "SELECT DISTINCT uid FROM " + TableUidIndex + Where
		// + "other='" + "Install" + "'";
		// try {
		// cur = sqlDataBase.rawQuery(string, null);
		// // showLog(string);
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog(string);
		// }
		// int[] uids = new int[cur.getCount()];
		// if (cur != null) {
		// try {
		// int mindown = cur.getColumnIndex("uid");
		// // showLog(cur.getColumnIndex("minute") + "");
		// int i = 0;
		// if (cur.moveToFirst()) {
		// do {
		// uids[i] = (int) cur.getLong(mindown);
		// i++;
		// } while (cur.moveToNext());
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("cur-searchfail");
		// }
		// }
		// cur.close();
		// // for (int i = 0; i < uids.length; i++) {
		// // showLog(uids[i] + "");
		// // }
		// closeSQL(sqlDataBase);
		// return uids;
		// int j = 0;
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

	// /**
	// * 提取所有应用的不重复uid集合
	// *
	// * @param sqlDataBase
	// * 进行操作的数据库
	// * @return
	// */
	// public int[] selectSQLUidnumbers(SQLiteDatabase sqlDataBase) {
	// // TODO Auto-generated method stub
	// // SQLiteDatabase sqlDataBase = creatSQLUidIndex(context);
	// String string = null;
	// // select oldest upload and download 之前记录的数据的查询操作
	// // SELECT * FROM table WHERE type=0
	// string = "SELECT DISTINCT uid FROM " + TableUidIndex + Where
	// + "other='" + "Install" + "'";
	// try {
	// cur = sqlDataBase.rawQuery(string, null);
	// // showLog(string);
	// } catch (Exception e) {
	// // TODO: handle exception
	// showLog(string);
	// }
	// int[] uids = new int[cur.getCount()];
	// if (cur != null) {
	// try {
	// int mindown = cur.getColumnIndex("uid");
	// // showLog(cur.getColumnIndex("minute") + "");
	// int i = 0;
	// if (cur.moveToFirst()) {
	// do {
	// uids[i] = (int) cur.getLong(mindown);
	// i++;
	// } while (cur.moveToNext());
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// showLog("cur-searchfail");
	// }
	// }
	// cur.close();
	// // for (int i = 0; i < uids.length; i++) {
	// // showLog(uids[i] + "");
	// // }
	// // closeSQL(sqlDataBase);
	// return uids;
	// }

	/**
	 * 对数据库进行uid数据的写入操作的操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, int uidnumber,
			int type, String other) {
		initTime();
		initUidData(uidnumber);
		String string = null;
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + uidupload + split
				+ uiddownload + split + type + split + other + "'" + End;
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
		initUidData(uidnumber);
		String string = null;
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + uidupload + split
				+ uiddownload + split + type + split + other + "'" + End;
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
	 * 在UidIndex中踢出包名重复数据留最上面的
	 * 
	 * @param mySQL
	 */
	private void sortSQLUidIndex(SQLiteDatabase mySQL) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = "DELETE   FROM " + TableUidIndex + Where + "_id"
				+ " not in (select min(" + "_id" + ") from " + TableUidIndex
				+ " group by packagename)";

		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * 删除多余的UidIndex数据项返回删除的uid[]
	 * 
	 * @param mySQL
	 */
	private int[] delSQLUidIndexAndTable(SQLiteDatabase mySQL) {
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "other='UnInstall'";
		// showLog(string);
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		int i = 0;
		int[] uids = new int[cur.getCount()];
		if (cur != null) {
			try {
				int uidIndex = cur.getColumnIndex("uid");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						if (cur.getInt(uidIndex) != 0) {
							uids[i] = cur.getInt(uidIndex);
							i++;
						}
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
		// 删除other为UnInstall的项目
		delSQLUidIndex(mySQL);
		// for (int j = 0; j < uids.length; j++) {
		// // showLog(uids[j] + "");
		// // showLog(isUidExistingInUidIndex(mySQL, uids[j]) + "");
		// // 不能清空uid=0的表
		//
		// }
		return uids;
	}

	/**
	 * 清空uid表内容
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            要清空的uid表
	 */
	private void DropUnusedUidTable(SQLiteDatabase mySQL, int uidnumber) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = DeleteTable + "uid" + uidnumber;

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
	 * 判断uid是否已经在uidIndex中存在
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            进行判断的uid
	 * @return 存在返回true，不存在返回false
	 */
	private boolean isUidExistingInUidIndex(SQLiteDatabase mySQL,
			String packageName, int uidnumber) {
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "uid='" + uidnumber
				+ AND + "packagename='" + packageName + "'";
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		if (cur.moveToFirst()) {
			cur.close();
			return true;
		} else {
			cur.close();
			return false;
		}
	}

	/**
	 * 是否覆盖安装
	 * 
	 * @param mySQL
	 * @param packagename
	 *            包名
	 * @return 是覆盖安装，返回true，新安装软件返回false
	 */
	private boolean isCoveringInstall(SQLiteDatabase mySQL, String packagename,
			int uidnumber) {
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "packagename='"
				+ packagename + AND + "uid='" + uidnumber + "'";
		// showLog(string);
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		int[] uids = new int[cur.getCount()];
		if (cur != null) {
			int i = 0;
			try {
				int uid = cur.getColumnIndex("uid");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						uids[i] = (int) cur.getInt(uid);
						i++;
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
		// for (int i = 0; i < uids.length; i++) {
		// showLog(uids[i] + "");
		// }

		if (uids.length == 1) {
			return false;
		}
		return true;
	}

	/**
	 * 清空uidIndex 中other为UnInstalld的数据
	 * 
	 * @param mySQL
	 */
	private void delSQLUidIndex(SQLiteDatabase mySQL) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = "DELETE   FROM " + TableUidIndex + Where + "other='"
				+ "UnInstall" + "'";
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * 安装新软件时添加uid索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param packagename
	 *            包名
	 * @param other
	 *            包的安装状态
	 */
	private void exeSQLUidIndextable(SQLiteDatabase mySQL, int uidnumber,
			String packagename, String other) {
		String string = null;
		string = InsertTable + TableUidIndex + Start
				+ InsertUidIndexColumnTotal + ",other" + End + Value
				+ uidnumber + split + packagename + split + 0 + split
				+ "Install" + "'" + End;
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
	 * 建立uid索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param packagename
	 *            包名
	 */
	private void exeSQLcreateUidIndextable(SQLiteDatabase mySQL, int uidnumber,
			String packagename) {
		String string = null;
		string = InsertTable + TableUidIndex + Start
				+ InsertUidIndexColumnTotal + ",other" + End + Value
				+ uidnumber + split + packagename + split + 0 + split
				+ "Install" + "'" + End;
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
	 * 初始化uid索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumbers
	 *            数据库的表集合
	 * @param packagename
	 *            包名
	 */
	protected void exeSQLcreateUidIndextables(SQLiteDatabase mySQL,
			int[] uidnumbers, String[] packagenames) {
		for (int i = 0; i < uidnumbers.length; i++) {
			exeSQLcreateUidIndextable(mySQL, uidnumbers[i], packagenames[i]);
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
	 * 初始化uid索引表
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidIndexTables(SQLiteDatabase sqldatabase) {
		// TODO Auto-generated method stub
		String string = null;
		string = CreateTable + TableUidIndex + Start + SQLId
				+ CreateparamUidIndex + End;
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
	 * 初始化全部的uid表
	 * 
	 * @param sqldatabase
	 *            进行操作的数据库
	 * @param uidnumbers
	 *            要建立的uid表数组
	 */
	protected void initUidTables(SQLiteDatabase sqldatabase, int[] uidnumbers) {
		initTime();
		try {
			// initUidTable(sqldatabase, 0);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 0, null);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 1, null);
			for (int uidnumber : uidnumbers) {
				// -1为重复项
				if (uidnumber != -1) {
					initUidTable(sqldatabase, uidnumber);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 0,
							null);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 1,
							null);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			showLog("初始化全部的uid表失败");
		}
	}

	/**
	 * 提取不重复的uid
	 * 
	 * @param uidnumbers
	 * @return 提取完成的uid集合
	 */
	protected int[] sortUids(int[] uidnumbers) {
		int[] newnumber = uidnumbers;
		for (int i = 0; i < newnumber.length; i++) {
			if (newnumber[i] != -1) {
				for (int j = i + 1; j < newnumber.length; j++) {
					if (newnumber[i] == newnumber[j]) {
						newnumber[j] = -1;
					}
				}
			}

		}
		return newnumber;
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
		String string = null;
		string = CreateTable + "uid" + uidnumber + Start + SQLId
				+ CreateparamUid + End;
		try {
			sqldatabase.execSQL(string);
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
	 * 外部程序获取系统时间
	 * 
	 * @return 格式为00:00:00
	 */
	public String gettime() {
		initTime();
		return time;
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
	 * 记录uid的流量数据
	 * 
	 * @param context
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void RecordUidwritestats(Context context, int[] uidnumbers,
			boolean daily, String network) {
		// TODO Auto-generated method stub
		if (!network.equals("")) {
			SQLiteDatabase sqlDataBase = creatSQLUid(context);
			sqlDataBase.beginTransaction();
			try {
				uidRecordwritestats(sqlDataBase, uidnumbers, daily, network);
				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				showLog("批量更新uid数据失败-Context");
			} finally {
				sqlDataBase.endTransaction();
			}
			closeSQL(sqlDataBase);
		}
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
		// TODO Auto-generated method stub
		if (!network.equals("")) {
			uidRecordwritestats(sqlDataBase, uidnumbers, daily, network);
		}
	}

	/**
	 * 记录uid的流量数据
	 * 
	 * @param sqlDataBase
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	private void uidRecordwritestats(SQLiteDatabase sqlDataBase,
			int[] uidnumbers, boolean daily, String network) {
		// TODO Auto-generated method stub

		// sqlDataBase.beginTransaction();
		// try {
		initTime();
		statsSQLuids(sqlDataBase, uidnumbers, date, time, 2, null, daily,
				network);
		// sqlDataBase.setTransactionSuccessful();
		// } catch (Exception e) {
		// // TODO: handle exception
		// showLog("批量更新uid数据失败");
		// } finally {
		// sqlDataBase.endTransaction();
		// }
	}

	/**
	 * 记录uid流量数据
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库
	 * @param uidnumbers
	 *            进行记录的uid号
	 * @param date
	 *            记录数据的日期
	 * @param time
	 *            记录数据的时间
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param daily
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	private void statsSQLuids(SQLiteDatabase sqlDataBase, int[] uidnumbers,
			String date, String time, int type, String other, boolean daily,
			String network) {
		// TODO Auto-generated method stub

		for (int uidnumber : uidnumbers) {
			initUidData(uidnumber);
			if (uidupload != 0 || uiddownload != 0) {
				statsSQLuid(sqlDataBase, uidnumber, date, time, uidupload,
						uiddownload, type, other, daily, network);
			}
		}

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
			showLog("error" + string);
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
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		// 初始化写入数据（wifi以及g23）
		// 如果之前数据大于新的数据，则重新计数
		if (oldup > upload || olddown > download) {
			oldup = upload;
			olddown = download;
		} else {
			oldup = upload - oldup;
			olddown = download - olddown;
		}
		if (daily) {
			// showLog("上传uid" + uidnumber + "数据" + oldup + "B" + "  " + "下载uid"
			// + uidnumber + "数据" + olddown + "B");
			// 输入实际数据进入数据库
			updateSQLUidType(mySQL, date, time, oldup, olddown, uidnumber, 0,
					network, 2);
			// 添加新的两行数据
			updateSQLUidType(mySQL, date, time, upload, download, uidnumber, 1,
					network, 0);
			exeSQLcreateUidtable(mySQL, uidnumber, 1, network);
		} else if ((oldup != 0 || olddown != 0)
				&& ((olddown > 1024) || (oldup > 1024))) {
			// showLog("上传uid" + uidnumber + "数据" + oldup + "B" + "  " + "下载uid"
			// + uidnumber + "数据" + olddown + "B");
			// 输入实际数据进入数据库
			updateSQLUidType(mySQL, date, time, oldup, olddown, uidnumber, 0,
					network, 2);
			// 添加新的两行数据
			updateSQLUidType(mySQL, date, time, upload, download, uidnumber, 1,
					network, 0);
			exeSQLcreateUidtable(mySQL, uidnumber, 1, network);
		}

	}

	// /**
	// * 进行uid历史流量查询包括wifi与mobile
	// *
	// * @param context
	// * context
	// * @param year
	// * 输入查询的年份2000.
	// * @param month
	// * 输入查询的月份.
	// * @param uid
	// * 输入查询的uid号
	// * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	// * a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	// */
	// public long[] SelectuidData(Context context, int year, int month, int
	// uid) {
	// return SelectData(context, year, month, TableUid + uid);
	// }

	/**
	 * 进行uid历史流量查询包括wifi与mobile
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param uid
	 *            输入查询的uid号
	 * @param other
	 *            要查询的网络类型"wifi"or"mobile"
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] SelectuidWifiorMobileData(Context context, int year,
			int month, int uid, String other) {
		return SelectUidmobileOrwifiData(context, year, month, TableUid + uid,
				uid, other);
	}

	// /**
	// * 进行数据流量历史流量查询
	// *
	// * @param context
	// * context
	// * @param year
	// * 输入查询的年份2000.
	// * @param month
	// * 输入查询的月份.
	// * @param table
	// * 要查询的数据类型
	// * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	// * a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	// */
	// private long[] SelectData(Context context, int year, int month, String
	// table) {
	// long[] a = new long[64];
	// SQLiteDatabase sqlDataBase = creatSQLUid(context);
	// String month2 = month + "";
	// if (month < 10)
	// month2 = "0" + month2;
	// String string = null;
	// // select oldest upload and download 之前记录的数据的查询操作
	// // SELECT * FROM table WHERE type=0
	// string = SelectTable + table + Where + "date" + Between + year + "-"
	// + month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
	// + "31" + AND + "type=" + 2;
	// // showLog(string);
	// try {
	// cur = sqlDataBase.rawQuery(string, null);
	// } catch (Exception e) {
	// // TODO: handle exception
	// showLog(string);
	// }
	// String newdate = "";
	// String countdate = "";
	// String dateStr1 = year + "-" + month2 + "-" + "0";
	// String dateStr2 = year + "-" + month2 + "-";
	// long newup = 0;
	// long newdown = 0;
	// int i = 1;
	// if (cur != null) {
	// try {
	// int dateIndex = cur.getColumnIndex("date");
	// int uploadIndex = cur.getColumnIndex("upload");
	// int downloadIndex = cur.getColumnIndex("download");
	// // showLog(cur.getColumnIndex("minute") + "");
	// if (cur.moveToFirst()) {
	// do {
	// if (i < 10)
	// countdate = dateStr1 + i;
	// else
	// countdate = dateStr2 + i;
	// newdate = cur.getString(dateIndex);
	// newup = cur.getLong(uploadIndex);
	// newdown = cur.getLong(downloadIndex);
	// if (newdate.equals(countdate)) {
	// a[i] += newup;
	// a[i + 31] += newdown;
	// } else {
	// a[0] += a[i];
	// a[63] += a[i + 31];
	// while (!newdate.equals(countdate)) {
	// i++;
	// if (i < 10)
	// countdate = dateStr1 + i;
	// else
	// countdate = dateStr2 + i;
	// if (i > 31) {
	// break;
	// }
	// }
	// a[i] += newup;
	// a[i + 31] += newdown;
	// }
	// } while (cur.moveToNext());
	// }
	// a[0] += a[i];
	// a[63] += a[i + 31];
	// } catch (Exception e) {
	// // TODO: handle exception
	// showLog("cur-searchfail");
	// }
	// }
	// cur.close();
	// closeSQL(sqlDataBase);
	// // for (int j = 0; j < a.length; j++) {
	// // showLog(j + "liuliang" + a[j] + "");
	// // }
	// return a;
	// }

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
	 * @param other
	 *            要查询的网络类型"wifi"or"mobile"
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	private long[] SelectUidmobileOrwifiData(Context context, int year,
			int month, String table, int uid, String other) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = creatSQLUid(context);
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "other=" + "'" + other + AND + "type=" + 2;
		showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			// 搜索失败则新建表
			selectfails(sqlDataBase, table, uid);
			cur = null;
			showLog("selectfail" + string);

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
							a[i] += newup;
							a[i + 31] += newdown;
						} else {
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
									}
									break;
								}

							}
							a[i] += newup;
							a[i + 31] += newdown;
						}
					} while (cur.moveToNext());
				}
				a[0] += a[i];
				a[63] += a[i + 31];
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		closeSQL(sqlDataBase);
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	private void selectfails(SQLiteDatabase sqlDataBase, String table, int uid) {
		// TODO Auto-generated method stub
		String string = null;
		string = CreateTable + table + Start + SQLId + CreateparamUid + End;
		try {
			sqlDataBase.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		initTime();
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 0, null);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 1, null);
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("databaseUid", string);
	}
}
