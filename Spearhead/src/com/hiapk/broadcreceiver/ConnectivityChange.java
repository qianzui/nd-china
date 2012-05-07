package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityChange extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 设置闹钟与数据库操作
		AlarmSet alset = new AlarmSet();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		if (sqlhelperTotal.getIsInit(context)) {
			// 网络状态变化时
			if (intent.getAction().equals(
					"android.net.conn.CONNECTIVITY_CHANGE")) {
				ConnectivityManager connec = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connec.getActiveNetworkInfo() != null) {
					NetworkInfo info = connec.getActiveNetworkInfo();
					String typeName = info.getTypeName(); // mobile@wifi
					if (typeName.equals("WIFI")) {
						// 总流量与uid自动记录功能并修改网络属性
						sqlhelperTotal.RecordTotalwritestats(context, false);
						sqlhelperUid.RecordUidwritestats(context, false);
						SQLHelperTotal.TableWiFiOrG23 = "wifi";
					}
					if (typeName.equals("mobile")) {
						// 总流量与uid自动记录功能并修改网络属性
						sqlhelperTotal.RecordTotalwritestats(context, false);
						sqlhelperUid.RecordUidwritestats(context, false);
						SQLHelperTotal.TableWiFiOrG23 = "mobile";
					}
					// showLog("何种方式连线" + typeName);
					// 启动闹钟
					alset.StartAlarm(context);
				} else {
					sqlhelperTotal.RecordTotalwritestats(context, false);
					sqlhelperUid.RecordUidwritestats(context, false);
					SQLHelperTotal.TableWiFiOrG23 = "";
					showLog("无可用网络");
					alset.StopAlarm(context);
				}
			}
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
