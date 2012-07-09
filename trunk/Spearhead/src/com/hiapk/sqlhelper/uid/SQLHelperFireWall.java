package com.hiapk.sqlhelper.uid;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLStatic;

public class SQLHelperFireWall {
	long time;

	/**
	 * Small structure to hold an application info
	 */
	public static class Data {
		public long upload;
		public long download;

	}

	public void resetMP(Context context) {
		time = System.currentTimeMillis();
		AlarmSet alset = new AlarmSet();
		alset.StartAlarmUid(context);
		showLog("alarmover" + (System.currentTimeMillis() - time));
		int[] numbers = null;
		while (SQLStatic.uidnumbers == null) {
			SQLStatic.uidnumbers = SQLStatic.selectUidnumbers(context);

		}
		showLog("getuids" + (System.currentTimeMillis() - time));
		numbers = SQLStatic.uidnumbers;
		SQLHelperUidRecordAll sqlhelperUidRecordall = new SQLHelperUidRecordAll(
				context);
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		HashMap<Integer, Data> mp = null;
		while (SQLStatic.setSQLUidOnUsed(true)) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		showLog("startRecord" + (System.currentTimeMillis() - time));
		sqlDataBase.beginTransaction();
		try {

			// 获取uid的总流量数据
			mp = sqlhelperUidRecordall.getSQLUidtraff(sqlDataBase, numbers);

			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sqlDataBase.endTransaction();
		}
		showLog("Recordover" + (System.currentTimeMillis() - time));
		SQLHelperCreateClose.closeSQL(sqlDataBase);
		SQLStatic.setSQLUidOnUsed(false);
		SQLStatic.uiddata = mp;

	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("SQLFireWall", string);
	}

}
