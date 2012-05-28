package com.hiapk.broadcreceiver;

import com.hiapk.rebootandstartaction.Onreinstall;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 设置数据库
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		String packageNames = intent.getDataString();
		String packageName[] = packageNames.split(":");
		if (sqlhelperTotal.getIsInit(context)) {
			// 监听包的安装
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				// 检查为自身安装
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					sqlhelperTotal.initTablemobileAndwifi(context,false);
				} else {
					// 其他软件安装
					// 检测网络权限
					PackageManager pkgmanager = context.getPackageManager();
					if (PackageManager.PERMISSION_GRANTED != pkgmanager
							.checkPermission(Manifest.permission.INTERNET,
									packageName[1])) {
						// 无网络权限
						// showLog("没网络权限的安装");
					} else {
						// 有网络权限进行更新表格
						// showLog("有网络权限的安装");
						int uidnumber = 99999;
						try {
							uidnumber = context.getPackageManager()
									.getPackageInfo(packageName[1],
											getResultCode()).applicationInfo.uid;
						} catch (NameNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							showLog("获取包信息失败");
						}
						if (uidnumber != 99999) {
							sqlhelperUid.updateSQLUidIndexOnInstall(context,
									uidnumber, packageName[1], "Install");
						}
					}
				}

			}
			// 监听包的卸载
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					Onreinstall reinstall = new Onreinstall();
					reinstall.reInstallAction(context);
					// showLog("自身安装");
					showLog("卸载" + packageName[1]);
				} else {
					SQLiteDatabase mySQL = sqlhelperUid
							.creatSQLUidIndex(context);
					sqlhelperUid.updateSQLUidIndexOtherOnUnInstall(mySQL,
							packageName[1], "UnInstall");
					sqlhelperUid.closeSQL(mySQL);
					showLog("其他卸载" + packageName[1]);
				}

			}
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
