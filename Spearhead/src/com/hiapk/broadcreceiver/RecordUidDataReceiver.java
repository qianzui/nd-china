package com.hiapk.broadcreceiver;

import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.uid.SQLHelperUidRecord;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataOnUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	long time;
	private String network;
	private String TAG = "ReceiverUid";

	@Override
	public void onReceive(Context context, Intent intent) {
		showLog("isUidAlarmRecording=" + SQLStatic.isUidAlarmRecording);
		if (SQLStatic.isUidAlarmRecording == false) {
			SQLStatic.isUidAlarmRecording = true;
			SQLStatic.initTablemobileAndwifi(context);
			if (SQLStatic.TableWiFiOrG23 == "") {
				network = SQLStatic.TableWiFiOrG23Before;
			} else {
				network = SQLStatic.TableWiFiOrG23;
			}
			showLog("TableWiFiOrG23=" + SQLStatic.TableWiFiOrG23);
			showLog("network=" + network);
			if (SQLStatic.getIsInit(context)) {
				if (network != "") {
					// 进行之前使用的网络是何种网络进行判断
					// network = SQLHelperTotal.TableWiFiOrG23;
					if (SQLStatic.setSQLUidOnUsed(true)) {
						new AsyncTaskonRecordUidData().execute(context);
						// showLog(SQLHelperTotal.TableWiFiOrG23);
					} else {
						SQLStatic.isUidAlarmRecording = false;
						showLog("Uid数据库忙");
					}

					// } else {
					// // 无网络条件下进行最后一次记录
					// if (SQLStatic.setSQLUidOnUsed(true)) {
					// new AsyncTaskonRecordUidData().execute(context);
					// // showLog(SQLHelperTotal.TableWiFiOrG23);
					// } else {
					// SQLStatic.setSQLUidOnUsed(false);
					// SQLStatic.isUidAlarmRecording = false;
					// showLog("Uid数据库忙");
					// }
				} else {
					// 1.0.3更新额外添加语句
					SharedPrefrenceDataOnUpdate sharedUpdate = new SharedPrefrenceDataOnUpdate(
							context);
					boolean isupdated = sharedUpdate.isUidRecordUpdated();
					if (isupdated == false) {
						if (SQLStatic.setSQLUidOnUsed(true)) {
							new AsyncTaskonRecordUidData().execute(context);
							// showLog(SQLHelperTotal.TableWiFiOrG23);
						} else {
							SQLStatic.isUidAlarmRecording = false;
							showLog("Uid数据库忙");
						}
					} else
						SQLStatic.isUidAlarmRecording = false;
				}
			} else {
				// sqlhelper.initSQL(context);
				showLog("please init the database");
				SQLStatic.isUidAlarmRecording = false;
			}
		}
	}

	/**
	 * 批量更新
	 * 
	 * @param context
	 */
	private int uidRecord(Context context) {
		// 实时更新数据两个1代表数据更新
		// showLog(SQLHelperTotal.isSQLIndexOnUsed+"");
		int[] numbers = null;
		if (SQLStatic.uidnumbers == null) {
			// 重新定义静态的uid集合
			SQLStatic.getuidsAndpacname(context);

		}
		if (SQLStatic.uidnumbers != null) {
			numbers = SQLStatic.uidnumbers;
		} else {
			return 0;
		}
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		sqlDataBase.beginTransaction();
		try {
			// 记录数据
			SQLHelperUidRecord sqlhelperUidRecord = new SQLHelperUidRecord(
					context);
			sqlhelperUidRecord.RecordUidwritestats(context, sqlDataBase,
					numbers, false, network);
			// // // // 获取uid的总流量数据
			// SQLStatic.uiddata = sqlhelperUidFire.getSQLUidtraff(sqlDataBase,
			// numbers);

			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			showLog("批量输入uid网络数据失败" + e);
		} finally {
			sqlDataBase.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);

		return 1;
	}

	private class AsyncTaskonRecordUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			time = System.currentTimeMillis();
			// SQLHelperTotal.isSQLUidOnUsed = true;

		}

		@Override
		protected Integer doInBackground(Context... params) {
			return uidRecord(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// SQLHelperTotal.isSQLUidOnUsed = false;
			SQLStatic.setSQLUidOnUsed(false);
			SQLStatic.isUidAlarmRecording = false;
			time = System.currentTimeMillis() - time;
			showLog("更新记录完毕" + time);
			if (result == 0) {
				showLog("获取uid表失败");
			}
			if (result == 1) {
				showLog("uid数据更新");
			}
		}

	}

	private void showLog(String string) {
		Logs.d(TAG, string);
	}

}
