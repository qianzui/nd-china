package com.hiapk.sqlhelper.pub;

import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * �����͹ر����ݿ�
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
	 * ���������ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public static SQLiteDatabase creatSQLTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLTotalname,
				MODE_PRIVATE, null);
		mySQL.setLocale(Locale.CHINA);
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
		mySQL.setLocale(Locale.CHINA);
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
		mySQL.setLocale(Locale.CHINA);
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

}
