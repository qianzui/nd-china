package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.sqlhelper.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;

public class RecordUidTotalDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	long time;
	private String network;

	@Override
	public void onReceive(Context context, Intent intent) {
		SQLStatic.isUidTotalAlarmRecording = true;
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				// 进行之前使用的网络是何种网络进行判断
				// network = SQLHelperTotal.TableWiFiOrG23;
				network = SQLHelperTotal.TableWiFiOrG23;
				if (SQLStatic.setSQLUidTotalOnUsed(true)) {
					new AsyncTaskonRecordUidTotal().execute(context);
				} else {
					SQLStatic.setSQLUidTotalOnUsed(false);
					SQLStatic.isUidTotalAlarmRecording = false;
					showLog("UidTotal数据库忙");
				}

			} else {
				// 无网络条件下进行最后一次记录
				network = SQLHelperTotal.TableWiFiOrG23;
				// 进行两种数据的记录
				if (SQLStatic.setSQLUidTotalOnUsed(true)) {
					new AsyncTaskonRecordUidTotal().execute(context);
				} else {
					SQLStatic.setSQLUidTotalOnUsed(false);
					SQLStatic.isUidTotalAlarmRecording = false;
					showLog("UidTotal数据库忙");
				}

			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
			SQLStatic.isUidTotalAlarmRecording = false;
		}
	}

	private class AsyncTaskonRecordUidTotal extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// SQLHelperTotal.isSQLUidTotalOnUsed = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			if (SQLStatic.uidnumbers == null) {
				// if (SQLHelperTotal.isSQLIndexOnUsed == false) {
				// SQLHelperTotal.isSQLIndexOnUsed = true;
				// if (SQLStatic.setSQLIndexOnUsed(true)) {
				// SQLHelperUid.uidnumbers = sqlhelperUid
				// .selectSQLUidnumbers(params[0]);
				// SQLStatic.setSQLIndexOnUsed(false);
				// }
				// 重新定义静态的uid集合
				SQLStatic.uidnumbers = sqlhelperUid.selectUidnumbers(params[0]);
				// SQLHelperTotal.isSQLIndexOnUsed = false;
				// }

			}
			if (SQLStatic.uidnumbers == null) {
				return 0;
			}
			SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
			SQLiteDatabase sqlDataBase = sqlUidTotal
					.creatSQLUidTotal(params[0]);
			sqlDataBase.beginTransaction();
			try {
				sqlUidTotal.updateSQLUidTypes(sqlDataBase,
						SQLStatic.uidnumbers, network);
				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				showLog("批量读取uid流量-Context");
			} finally {
				sqlDataBase.endTransaction();
			}
			sqlUidTotal.closeSQL(sqlDataBase);

			// sqlUidTotal.updateSQLUidTypes(params[0], SQLStatic.uidnumbers,
			// network);

			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result == 1) {
				showLog("uidTotal更新成功");
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
			if (result == 0) {
				showLog("uidTotal更新失败无uidindex");
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
			SQLStatic.isUidTotalAlarmRecording = false;
			// if (result == 3) {
			// showLog("uidTotalUnknow");
			// }
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("ReceiverUid", string);
	}

}
