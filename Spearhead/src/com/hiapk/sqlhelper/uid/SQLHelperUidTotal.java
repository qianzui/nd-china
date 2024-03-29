package com.hiapk.sqlhelper.uid;

import java.util.LinkedList;
import java.util.List;

import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.util.SQLStatic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;

public class SQLHelperUidTotal {
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUidTotal = "uid INTEGER,packagename varchar(255),upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15),states varchar(15)";
	private String TableUidTotal = "uidtotal";
	private String InsertTable = "INSERT INTO ";
	private String DeleteTable = "DELETE FROM ";
	private String Where = " WHERE ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidTotalColumn = "uid,packagename,upload,download,permission,type,other,states";
	private String Value = "values('";
	private String split = "','";
	private Cursor cur;
	// logs
	private String TAG = "databaseUidTotal";

	/**
	 * 初始化uidTotal索引表
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidTotalTables(SQLiteDatabase sqldatabase) {
		StringBuilder string = new StringBuilder();
		string = string.append(CreateTable).append(TableUidTotal).append(Start)
				.append(SQLId).append(CreateparamUidTotal).append(End);
		// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
		// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
		// varchar(15),other varchar(15))
		try {
			sqldatabase.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "initUidIndexTables");
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
		StringBuilder string = new StringBuilder();
		string = string.append(InsertTable).append(TableUidTotal).append(Start)
				.append(InsertUidTotalColumn).append(End).append(Value)
				.append(uidnumber).append(split).append(packagename)
				.append(split).append(upload).append(split).append(download)
				.append(split).append(0).append(split).append(type)
				.append(split).append(other).append(split).append("Install")
				.append("'").append(End);
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
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
		StringBuilder string = new StringBuilder();
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = string.append(DeleteTable).append(TableUidTotal).append(Where)
				.append(" packagename='").append(packagename).append("'");
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
	}

	public List<Integer> updateSQLUidTotalOnInstallgetDel(Context context,
			int uidnumber, String packageName, String other, int[] uids) {
		List<Integer> uid_List_Del = new LinkedList<Integer>();
		// List<Integer> uid_List_Add = new LinkedList<Integer>();
		SQLiteDatabase mySQL = SQLHelperCreateClose.creatSQLUidTotal(context);
		mySQL.beginTransaction();
		try {
			// 选择现有的uid数据
			StringBuilder string = new StringBuilder();
			// select oldest upload and download 之前记录的数据的查询操作
			// SELECT * FROM table WHERE type=0
			string = string.append("SELECT * FROM ").append(TableUidTotal)
					.append(Where).append("type='").append("0").append("'");
			try {
				cur = mySQL.rawQuery(string.toString(), null);
				// showLog(string);
			} catch (Exception e) {
				Logs.d(TAG, "fail-List-cur" + string.toString());
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
					Logs.d(TAG, "cur-searchfail" + e);
				}
			}
			if (cur != null) {
				cur.close();
			}

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
				}
			}

			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			Logs.d(TAG, "更新索引表失败");
		} finally {
			mySQL.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(mySQL);

		return uid_List_Del;
	}

	public List<Integer> updateSQLUidTotalOnInstallgetAdd(Context context,
			int uidnumber, String packageName, String other, int[] uids) {
		// List<Integer> uid_List_Del = new LinkedList<Integer>();
		List<Integer> uid_List_Add = new LinkedList<Integer>();
		SQLiteDatabase mySQL = SQLHelperCreateClose.creatSQLUidTotal(context);
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
		// showLog("更新索引表失败");
		// } finally {
		// mySQL.endTransaction();
		// }
		SQLHelperCreateClose.closeSQL(mySQL);

		return uid_List_Add;
	}

}
