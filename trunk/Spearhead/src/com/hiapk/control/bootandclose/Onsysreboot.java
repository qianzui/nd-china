package com.hiapk.control.bootandclose;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.NotificationFireFailOnsysBoot;
import com.hiapk.control.widget.SetText;
import com.hiapk.firewall.Block;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class Onsysreboot {
	boolean isNotifyOpen = true;
	boolean isFloatOpen = false;
	boolean isWidget1X4Open = true;
	private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
	Context context;
	AlarmSet alset = new AlarmSet();
	private static boolean isbooting = false;

	public void onsysreboot(Context context) {
		if (isbooting) {
			return;
		}
//		Toast.makeText(context, "startingboot", Toast.LENGTH_SHORT).show();
		isbooting = true;
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		// 初始化网络信号
		SQLStatic.initTablemobileAndwifi(context);
		alset.StartAlarm(context);
		// 查看数据库是否已初始化
		if (SQLStatic.getIsInit(context)) {
			isNotifyOpen = sharedDatawidget.isNotifyOpen();
			isFloatOpen = sharedDatawidget.isFloatOpen();
			isWidget1X4Open = sharedDatawidget.isWidGet14Open();
			// showLog("isNotifyOpen"+isNotifyOpen);
			// showLog("isFloatOpen"+isFloatOpen);
			if (TrafficManager.mobile_month_use == 1) {
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLHelperDataexe.initShowDataOnBroadCast(context);
				} else
					new AsyncTaskonBootStartWidget().execute(context);
			} else {
				if (isFloatOpen) {
					context.startService(new Intent("com.hiapk.server"));
				} else {
					context.stopService(new Intent("com.hiapk.server"));
				}
				SetText.resetWidgetAndNotify(context);
			}

			// Intent intentTextUpdate = new Intent();
			// intentTextUpdate.setAction(BROADCAST_TRAFF);
			// context.sendBroadcast(intentTextUpdate);
			// Toast.makeText(context, "qidong", Toast.LENGTH_SHORT);
			showLog("xitongqidong");
		}
		// 发送零点重置广播
		context.sendBroadcast(new Intent(ACTION_TIME_CHANGED));
		// 开启防火墙
		// if (!Block.iptableEmpty(SpearheadApplication.getInstance()
		// .getApplicationContext())) {
		// isOpenSucess = Block.applyIptablesRules(SpearheadApplication
		// .getInstance().getApplicationContext(), true, false);
		// if (!isOpenSucess) {
		// SpearheadApplication.getInstance().getsharedData()
		// .setIsFireWallOpenFail(true);
		// }
		// NotificationFireFailOnsysBoot openFireFail = new
		// NotificationFireFailOnsysBoot(
		// SpearheadApplication.getInstance().getApplicationContext());
		// openFireFail.startNotifyDay(SpearheadApplication.getInstance()
		// .getApplicationContext(), false);
		//
		// }
		if (!Block.iptableEmpty(SpearheadApplication.getInstance()
				.getApplicationContext())) {
			new AsyncTaskonBootGetRoot().execute(context);
		}

	}

	private class AsyncTaskonBootStartWidget extends
			AsyncTask<Context, Long, Long> {

		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_use == 1) {
				timetap++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (timetap > 10) {
					break;
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			if (isFloatOpen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			if (isNotifyOpen || isWidget1X4Open) {
				alset.StartWidgetAlarm(context);
			} else {
				alset.StopWidgetAlarm(context);
			}
			isbooting = false;
		}
	}

	private class AsyncTaskonBootGetRoot extends
			AsyncTask<Context, Long, Boolean> {

		@Override
		protected Boolean doInBackground(Context... params) {
			boolean isOpenSucess = false;
			isOpenSucess = Block.applyIptablesRules(SpearheadApplication
					.getInstance().getApplicationContext(), true, false);
			return isOpenSucess;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			boolean isOpenSucess = result;
			if (!isOpenSucess) {
				SpearheadApplication.getInstance().getsharedData()
						.setIsFireWallOpenFail(true);
				NotificationFireFailOnsysBoot openFireFail = new NotificationFireFailOnsysBoot(
						SpearheadApplication.getInstance()
								.getApplicationContext());
				openFireFail.startNotifyDay(SpearheadApplication.getInstance()
						.getApplicationContext(), false);
			}
		}
	}

	private void showLog(String string) {
		// Log.d("Onsysreboot", string);
	}
}
