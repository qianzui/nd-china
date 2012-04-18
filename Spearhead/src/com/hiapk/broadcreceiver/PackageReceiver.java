package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �������ݿ�
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		String packageNames = intent.getDataString();
		String packageName[] = packageNames.split(":");
		if (sqlhelperTotal.getIsInit(context)) {
			// �������İ�װ
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				if (packageNames
						.equals("package:com.wind.androiddev.showtraffic")) {
					sqlhelperTotal.initTablemobileAndwifi(context);
					// showLog("��װ" + packageName[1]);
				} else {
					int uidnumber = 99999;
					try {
						uidnumber = context.getPackageManager().getPackageInfo(
								packageName[1], getResultCode()).applicationInfo.uid;
					} catch (NameNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						showLog("��ȡ����Ϣʧ��");
					}
					if (uidnumber != 99999) {
						sqlhelperUid.updateSQLUidIndexOnInstall(context,
								uidnumber, packageName[1], "Install");
					}
				}

			}
			// ��������ж��
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames
						.equals("package:com.wind.androiddev.showtraffic")) {
					sqlhelperTotal.initTablemobileAndwifi(context);
					// ����IsInit��Ϣ
					sqlhelperTotal.getIsInit(context);
					showLog("ж��" + packageName[1]);
				} else {
					SQLiteDatabase mySQL = sqlhelperUid.creatSQL(context);
					sqlhelperUid.updateSQLUidIndexOtherOnUnInstall(mySQL,
							packageName[1], "UnInstall");
					sqlhelperUid.closeSQL(mySQL);
					showLog("����ж��" + packageName[1]);
				}

			}
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}
}
