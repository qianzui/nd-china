package com.hiapk.sqlhelper.uid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.hiapk.bean.DatauidHash;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

public class SQLHelperFireWall {
	long time;
	private static boolean isReseting = false;
	private String TAG = "SQLFireWall";
	private int fireWallType = 0;

	public void resetMP(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		fireWallType = sharedData.getFireWallType();
		if (fireWallType > 2)
			fireWallType = 2;
		setCacheuiddata(fireWallType);
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
			AsyncTask<Context, Integer, HashMap<Integer, DatauidHash>> {
		@Override
		protected HashMap<Integer, DatauidHash> doInBackground(
				Context... params) {
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
			SQLHelperUidSelectDataFire sqlhelperUidRecordall = new SQLHelperUidSelectDataFire(
					params[0]);
			HashMap<Integer, DatauidHash> mp = null;
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
				mp = sqlhelperUidRecordall.getSQLUidtraffMonth(sqlDataBase,
						numbers, fireWallType);

				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				Logs.d(TAG, "获取防火墙页面流量信息失败");
				mp = new HashMap<Integer, DatauidHash>();
			} finally {
				sqlDataBase.endTransaction();
			}
			SQLHelperCreateClose.closeSQL(sqlDataBase);
			SQLStatic.setSQLUidOnUsed(false);

			return mp;
		}

		@Override
		protected void onPostExecute(HashMap<Integer, DatauidHash> result) {
			Logs.iop(TAG, "Recordover" + (System.currentTimeMillis() - time));
			Logs.iop(TAG, "result.size()=" + result.size());
			Set<Integer> key = result.keySet();
			for(Iterator it = key.iterator();it.hasNext();){
				Integer dh = (Integer)it.next();
				Log.i("test","traffic:" + result.get(dh).getTotalTraff());
			}
			
			switch (fireWallType) {
			case 1:
				SQLStatic.uiddataWeek = result;

				break;
			case 2:
				SQLStatic.uiddataMonth = result;
				break;
			default:
				SQLStatic.uiddataToday = result;
				break;
			}
			SQLStatic.uiddata = result;
			isReseting = false;
		}
	}

	private void setCacheuiddata(int fireWallType) {
		switch (fireWallType) {
		case 1:
			if (SQLStatic.uiddataWeek != null) {
				SQLStatic.uiddata = SQLStatic.uiddataWeek;
			}
			break;
		case 2:
			if (SQLStatic.uiddataMonth != null) {
				SQLStatic.uiddata = SQLStatic.uiddataMonth;
			}
			break;
		default:
			if (SQLStatic.uiddataToday != null) {
				SQLStatic.uiddata = SQLStatic.uiddataToday;
			}
			break;
		}

	}
}
