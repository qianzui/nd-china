package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	SQLiteDatabase sqlDataBase;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				if (SQLHelperTotal.isSQLOnUsed != true) {
					sqlDataBase = sqlhelperUid.creatSQL(context);
					new AsyncTaskonRecordUidData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				}
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	private void uidRecord() {
		// 实时更新数据两个1代表数据更新
		int[] uidnumbers = sqlhelperUid.selectSQLUidnumbers(sqlDataBase);
		sqlhelperUid.updateSQLUidTypes(sqlDataBase, uidnumbers, 1,
				SQLHelperTotal.TableWiFiOrG23, 1);
		sqlhelperUid.closeSQL(sqlDataBase);
		showLog("uid数据更新");
	}

	private class AsyncTaskonRecordUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SQLHelperTotal.isSQLOnUsed = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			uidRecord();
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			SQLHelperTotal.isSQLOnUsed=false;
		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}

}
