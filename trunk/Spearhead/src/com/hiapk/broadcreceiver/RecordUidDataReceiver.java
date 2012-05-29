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
				// ����֮ǰʹ�õ������Ǻ�����������ж�
				// network = SQLHelperTotal.TableWiFiOrG23;
				if (SQLHelperTotal.UidWiFiOrG23 == "") {
					network = SQLHelperTotal.TableWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
				} else {
					network = SQLHelperTotal.UidWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
				}
				// �����������ݵļ�¼
				if (SQLStatic.setSQLUidOnUsed(true)) {
					new AsyncTaskonRecordUidData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else
					showLog("Uid���ݿ�æ");
				if (SQLStatic.setSQLUidTotalOnUsed(true)) {
					new AsyncTaskonRecordUidTotal().execute(context);
				} else
					showLog("UidTotal���ݿ�æ");
			} else {
				// �����������½������һ�μ�¼
				if (SQLHelperTotal.UidWiFiOrG23 != "") {
					network = SQLHelperTotal.UidWiFiOrG23;
					SQLHelperTotal.UidWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
					// �����������ݵļ�¼
					if (SQLStatic.setSQLUidOnUsed(true)) {
						new AsyncTaskonRecordUidData().execute(context);
						// showLog(SQLHelperTotal.TableWiFiOrG23);
					} else
						showLog("Uid���ݿ�æ");
					if (SQLStatic.setSQLUidTotalOnUsed(true)) {
						new AsyncTaskonRecordUidTotal().execute(context);
					} else
						showLog("UidTotal���ݿ�æ");
				}
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	/**
	 * ��������
	 * 
	 * @param context
	 */
	private int uidRecord(Context context) {
		// ʵʱ������������1�������ݸ���
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
			// ��������
			sqlhelperUid.updateSQLUidTypes(sqlDataBase,
					SQLHelperUid.uidnumbers, 1, network, 1);
			// ��¼����
			sqlhelperUid.RecordUidwritestats(sqlDataBase,
					SQLHelperUid.uidnumbers, false, network);
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��������uid��������ʧ��");
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
			showLog("���¼�¼���" + time);
			if (result == 0) {
				showLog("��ȡuid��ʧ��");
			}
			if (result == 1) {
				showLog("uid���ݸ���");
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
				showLog("uidTotal���³ɹ�");
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
			if (result == 0) {
				showLog("uidTotal����ʧ����uidindex");
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
