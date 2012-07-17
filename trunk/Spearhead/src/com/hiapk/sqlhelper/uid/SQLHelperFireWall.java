package com.hiapk.sqlhelper.uid;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.Splash;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLStatic;

public class SQLHelperFireWall {
	long time;
	private static boolean isReseting = false;

	/**
	 * Small structure to hold an application info
	 */
	public static class Data {
		public long upload;
		public long download;

	}

	public void resetMP(Context context) {
		time = System.currentTimeMillis();
		// AlarmSet alset = new AlarmSet();
		// alset.StartAlarm(context);
		showLog("alarmover" + (System.currentTimeMillis() - time));
		if (SQLStatic.getIsInit(context)) {
			if (isReseting == false) {
				isReseting = true;
				new AsyncTaskonResume().execute(context);
			}
		}
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, HashMap<Integer, Data>> {
		@Override
		protected HashMap<Integer, Data> doInBackground(Context... params) {
			int[] numbers = null;
			while (SQLStatic.uidnumbers == null) {
				SQLStatic.getuidsAndpacname(params[0]);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			showLog("getuids" + (System.currentTimeMillis() - time));
			numbers = SQLStatic.uidnumbers;
			SQLHelperUidRecordFire sqlhelperUidRecordall = new SQLHelperUidRecordFire(
					params[0]);
			HashMap<Integer, Data> mp = null;
			while (!SQLStatic.setSQLUidOnUsed(true)) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			SQLiteDatabase sqlDataBase = SQLHelperCreateClose
					.creatSQLUid(params[0]);
			showLog("startRecord" + (System.currentTimeMillis() - time));
			sqlDataBase.beginTransaction();
			try {

				// 获取uid的总流量数据
				mp = sqlhelperUidRecordall.getSQLUidtraff(sqlDataBase, numbers);

				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				showLog("获取防火墙页面流量信息失败");
			} finally {
				sqlDataBase.endTransaction();
			}

			SQLHelperCreateClose.closeSQL(sqlDataBase);
			SQLStatic.setSQLUidOnUsed(false);
			// SQLStatic.uiddata = mp;

			return mp;
		}

		@Override
		protected void onPostExecute(HashMap<Integer, Data> result) {
			showLog("Recordover" + (System.currentTimeMillis() - time));
			SQLStatic.uiddata = result;
			isReseting = false;
		}
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("SQLFireWall", string);
		}
	}

}
