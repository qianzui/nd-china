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
import android.os.AsyncTask;
import android.util.Log;

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	long time;
	private String network;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				// 进行之前使用的网络是何种网络进行判断
				// network = SQLHelperTotal.TableWiFiOrG23;
				if (SQLHelperTotal.UidWiFiOrG23 == "") {
					network = SQLHelperTotal.TableWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
				} else {
					network = SQLHelperTotal.UidWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
				}
				// 进行两种数据的记录
				if (SQLStatic.setSQLUidOnUsed(true)) {
					new AsyncTaskonRecordUidData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else
					showLog("Uid数据库忙");
				if (SQLStatic.setSQLUidTotalOnUsed(true)) {
					new AsyncTaskonRecordUidTotal().execute(context);
				} else
					showLog("UidTotal数据库忙");
			} else {
				// 无网络条件下进行最后一次记录
				if (SQLHelperTotal.UidWiFiOrG23 != "") {
					network = SQLHelperTotal.UidWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
					// 进行两种数据的记录
					if (SQLStatic.setSQLUidOnUsed(true)) {
						new AsyncTaskonRecordUidData().execute(context);
						// showLog(SQLHelperTotal.TableWiFiOrG23);
					} else
						showLog("Uid数据库忙");
					if (SQLStatic.setSQLUidTotalOnUsed(true)) {
						new AsyncTaskonRecordUidTotal().execute(context);
					} else
						showLog("UidTotal数据库忙");
				}
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
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
		if (SQLHelperUid.uidnumbers == null) {
			// if (SQLHelperTotal.isSQLIndexOnUsed == false) {
			// SQLHelperTotal.isSQLIndexOnUsed = true;
			if (SQLStatic.setSQLIndexOnUsed(true)) {
				SQLHelperUid.uidnumbers = sqlhelperUid
						.selectSQLUidnumbers(context);
				SQLStatic.setSQLIndexOnUsed(false);
			}
			// SQLHelperTotal.isSQLIndexOnUsed = false;
			// }

		}
		if (SQLHelperUid.uidnumbers == null) {
			return 0;
		}
		SQLiteDatabase sqlDataBase = sqlhelperUid.creatSQLUid(context);
		sqlDataBase.beginTransaction();
		try {
			// 更新数据
			sqlhelperUid.updateSQLUidTypes(sqlDataBase,
					SQLHelperUid.uidnumbers, 1, network, 1);
			// 记录数据
			sqlhelperUid.RecordUidwritestats(sqlDataBase,
					SQLHelperUid.uidnumbers, false, network);
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("批量输入uid网络数据失败");
		} finally {
			sqlDataBase.endTransaction();
		}
		sqlhelperUid.closeSQL(sqlDataBase);
		return 1;
	}

	private class AsyncTaskonRecordUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			// SQLHelperTotal.isSQLUidOnUsed = false;
			SQLStatic.setSQLUidOnUsed(false);
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
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (SQLHelperUid.uidnumbers == null) {
				// if (SQLHelperTotal.isSQLIndexOnUsed == false) {
				// SQLHelperTotal.isSQLIndexOnUsed = true;
				if (SQLStatic.setSQLIndexOnUsed(true)) {
					SQLHelperUid.uidnumbers = sqlhelperUid
							.selectSQLUidnumbers(params[0]);
					SQLStatic.setSQLIndexOnUsed(false);
				}
				// SQLHelperTotal.isSQLIndexOnUsed = false;
				// }

			}
			if (SQLHelperUid.uidnumbers == null) {
				return 0;
			}

			SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
			sqlUidTotal.updateSQLUidTypes(params[0], SQLHelperUid.uidnumbers,
					network);
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
			// if (result == 3) {
			// showLog("uidTotalUnknow");
			// }
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("ReceiverUid", string);
	}

}
