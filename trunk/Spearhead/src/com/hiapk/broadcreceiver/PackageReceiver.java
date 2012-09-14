package com.hiapk.broadcreceiver;

import java.util.LinkedList;
import java.util.List;

import com.hiapk.control.bootandclose.OnUninstallitself;
import com.hiapk.sqlhelper.uid.SQLHelperUidother;
import com.hiapk.sqlhelper.uid.SQLHelperUidTotal;
import com.hiapk.util.SQLStatic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 设置数据库
		String packageNames = intent.getDataString();
		SQLStatic.packageName = packageNames.split(":");
		if (SQLStatic.getIsInit(context)) {
			// 监听包的卸载
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					OnUninstallitself uninstall = new OnUninstallitself();
					uninstall.unInstallAction(context);
					showLog("卸载" + SQLStatic.packageName[1]);
				} else {
					// new AsyTaskOnUninstall().execute(context);
					SQLStatic.getuidsAndpacname(context);
					showLog("其他卸载" + SQLStatic.packageName[1]);
				}

			}
			// 监听包的安装
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				// 检查为自身安装
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					// OnReinstallitself reInstall = new OnReinstallitself();
					// reInstall.reInstallAction(context);
					showLog("安装" + SQLStatic.packageName[1]);
				} else {
					// 其他软件安装
					// 检测网络权限
					PackageManager pkgmanager = context.getPackageManager();
					if (PackageManager.PERMISSION_GRANTED != pkgmanager
							.checkPermission(Manifest.permission.INTERNET,
									SQLStatic.packageName[1])) {
						// 无网络权限
						showLog("没网络权限的安装");
					} else {
						SQLStatic.initTablemobileAndwifi(context);
						// 有网络权限进行更新表格
						showLog("有网络权限的安装");
						new AsyTaskOnInstall().execute(context);

					}
				}

			}
		}

	}

	private class AsyTaskOnInstall extends AsyncTask<Context, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// this.uid = SQLStatic.uidnumber;
			// this.pacName = SQLStatic.packageName[1];
			// isUidIndexSQLonUse_Asy = false;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SQLStatic.uidnumber = 999999;
			try {
				SQLStatic.uidnumber = params[0].getPackageManager()
						.getPackageInfo(SQLStatic.packageName[1],
								getResultCode()).applicationInfo.uid;
			} catch (NameNotFoundException e1) {
				e1.printStackTrace();
				showLog("获取包信息失败");
			}
			if (SQLStatic.uidnumber != 999999) {
				showLog("安装" + SQLStatic.packageName[1] + SQLStatic.uidnumber);
				SQLHelperUidother sqlhelperUid = new SQLHelperUidother();
				// 判断是否是新安装程序
				int[] uids = sqlhelperUid.updateSQLUidOnInstall(params[0],
						SQLStatic.uidnumber, SQLStatic.packageName[1],
						"Install");
				// 依据返回值进行软件uid表清空
				List<Integer> uid_List_Add = new LinkedList<Integer>();
				List<Integer> uid_List_Del = new LinkedList<Integer>();
				if (uids == null) {
					showLog("N覆盖安装");
				}
				if (uids != null) {
					showLog("Nuids!=null");
					while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// list不是空即有新添加软件
					showLog("building list");
					SQLHelperUidTotal sqlhelperUidTotal = new SQLHelperUidTotal();
					uid_List_Add = sqlhelperUidTotal
							.updateSQLUidTotalOnInstallgetAdd(params[0],
									SQLStatic.uidnumber,
									SQLStatic.packageName[1], "Install", uids);
					uid_List_Del = sqlhelperUidTotal
							.updateSQLUidTotalOnInstallgetDel(params[0],
									SQLStatic.uidnumber,
									SQLStatic.packageName[1], "Install", uids);
				}
				SQLStatic.setSQLUidTotalOnUsed(false);
				if ((uid_List_Add != null) || (uid_List_Del != null)) {
					if (uid_List_Add != null) {
						// showLog("N新安装软件");
					}
					if (uid_List_Del != null) {
						// showLog("N有软件要删除");
					}
					while (!SQLStatic.setSQLUidOnUsed(true)) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sqlhelperUid.updateSQLUidOnInstall(params[0],
							SQLStatic.uidnumber, SQLStatic.packageName[1],
							"Install", uid_List_Add, uid_List_Del);
				}
				SQLStatic.setSQLUidOnUsed(false);
				// new AsyTaskOnInstall().execute(context);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

	private void showLog(String string) {
		// Log.d("Receiver", string);
	}
}
