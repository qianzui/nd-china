package com.hiapk.sqlhelper.pub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLHelperCreateClose {

	public SQLHelperCreateClose() {
		super();
		// initTime();
	}

	// SQL
	private static String SQLTotalname = "SQLTotal.db";
	private static String SQLUidname = "SQLUid.db";
	private static String SQLUidTotaldata = "SQLTotaldata.db";
	private static final int MODE_PRIVATE = 0;

	// classes

	/**
	 * ���������ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public static SQLiteDatabase creatSQLTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLTotalname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����uid���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public static SQLiteDatabase creatSQLUid(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����uidTotal���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public static SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * �ر����ݿ�
	 * 
	 * @param mySQL
	 *            ��ָ�����ݿ���йر�
	 */
	public static void closeSQL(SQLiteDatabase mySQL) {
		mySQL.close();
	}

	// /**
	// * ������ʾ��־
	// *
	// * @param string
	// */
	// private void showLog(String string) {
	// Log.d("databaseCreateCloseSQL", string);
	// }
}
