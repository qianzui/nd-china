package com.hiapk.sqlhelper.uid;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.util.SQLStatic;

public class SQLHelperFireWall {
	long time;
	private static boolean isReseting = false;
	private String TAG = "SQLFireWall";

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
		Logs.iop(TAG, "alarmover" + (System.currentTimeMillis() - time));
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
			Logs.iop(TAG, "getuids" + (System.currentTimeMillis() - time));
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
			Logs.iop(TAG, "startRecord" + (System.currentTimeMillis() - time));
			sqlDataBase.beginTransaction();
			try {

				// 获取uid的总流量数据
				mp = sqlhelperUidRecordall.getSQLUidtraff(sqlDataBase, numbers);

				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				Logs.d(TAG, "获取防火墙页面流量信息失败");
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
			Logs.iop(TAG, "Recordover" + (System.currentTimeMillis() - time));
			SQLStatic.uiddata = result;
			isReseting = false;
		}
	}

}
