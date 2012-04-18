package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
//		showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				sqlDataBase = sqlhelperUid.creatSQL(context);
				totalRecord();
//				showLog(SQLHelperTotal.TableWiFiOrG23);
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	private void totalRecord() {
		// ʵʱ������������1�������ݸ���
		int[] uidnumbers = sqlhelperUid.selectSQLUidnumbers(sqlDataBase);
		sqlhelperUid.updateSQLUidTypes(sqlDataBase, uidnumbers, 1,
				SQLHelperTotal.TableWiFiOrG23, 1);
		sqlhelperUid.closeSQL(sqlDataBase);
		showLog("uid���ݸ���");
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}

}
