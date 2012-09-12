package com.hiapk.sqlhelper.pub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 创建和关闭数据库
 * 
 * @author Administrator
 * 
 */
public class SQLHelperCreateClose {

	public SQLHelperCreateClose() {
		super();
	}

	// SQL
	private static String SQLTotalname = "SQLTotal.db";
	private static String SQLUidname = "SQLUid.db";
	private static String SQLUidTotaldata = "SQLTotaldata.db";
	private static final int MODE_PRIVATE = 0;

	/**
	 * 创建总数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public static SQLiteDatabase creatSQLTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLTotalname,
				MODE_PRIVATE, null);
		return mySQL;
	}

	/**
	 * 创建uid数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public static SQLiteDatabase creatSQLUid(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidname,
				MODE_PRIVATE, null);
		return mySQL;
	}

	/**
	 * 创建uidTotal数据库
	 * 
	 * @param context
	 * @return 返回创建的数据库
	 */
	public static SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
				MODE_PRIVATE, null);
		return mySQL;
	}

	/**
	 * 关闭数据库
	 * 
	 * @param mySQL
	 *            对指定数据库进行关闭
	 */
	public static void closeSQL(SQLiteDatabase mySQL) {
		mySQL.close();
	}

}
