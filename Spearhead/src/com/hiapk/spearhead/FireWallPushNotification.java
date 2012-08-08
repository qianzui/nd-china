package com.hiapk.spearhead;

import java.util.ArrayList;

import com.hiapk.alertdialog.CustomDialogOtherBeen;
import com.hiapk.dataexe.NotificationInfo;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FireWallPushNotification extends Activity {
	long time = 0;
	/**
	 * 存储通知栏信息数据
	 */
	ArrayList<String[]> notificationInfos = new ArrayList<String[]>();
	Context context;
	boolean callbyonCreate = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showLog("onCreate");
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2_nofity);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
		context = this;
		callbyonCreate = true;
		new AsyncTaskGetAdbArrayListonCreate().execute(context);

	}

	/**
	 * 显示通知栏所有条目信息
	 */
	public void showNotificationView() {
		notificationInfos = NotificationInfo
				.getNotificationApp(NotificationInfo.notificationRes);
		LinearLayout notificationLayout = (LinearLayout) findViewById(R.id.adblock_content);
		notificationLayout.removeAllViews();
		showLog("**********这里进行显示(用下面那个方法)*************");
		// notificationLayout.addView(xxx);
		NotificationInfo.notificationRes = new StringBuilder();
	}

	/**
	 * 显示读取中
	 */
	public void showLoadingView() {
		LinearLayout notificationLayout = (LinearLayout) findViewById(R.id.adblock_content);
		notificationLayout.removeAllViews();
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View loading = mInflater.inflate(R.layout.loading_layout, null);
		TextView text = (TextView) loading.findViewById(R.id.tv_loading);
		text.setText("正在扫描通知栏信息。。。");
		notificationLayout.addView(loading);
	}

	@Override
	protected void onResume() {
		showLog("onResume");
		showLog("callbyonResume=" + NotificationInfo.callbyonResume);
		super.onResume();
		if (callbyonCreate)
			return;
		if (NotificationInfo.callbyonResume)
			return;
		showLoadingView();
		NotificationInfo.callbyonResume = true;
		if (String.valueOf(NotificationInfo.notificationRes).trim() == "") {
			new AsyncTaskGetAdbArrayListonResume().execute(context);
		}
	}
/**
 * 在onCreate中执行的获取通知栏信息的方法
 * @author Administrator
 *
 */
	private class AsyncTaskGetAdbArrayListonCreate extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationInfo.startRootcomand(context);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			while (String.valueOf(NotificationInfo.notificationRes).trim() == "") {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			showLog("onCreatenotificationRes="
					+ NotificationInfo.notificationRes.toString());
			if (String.valueOf(NotificationInfo.notificationRes).contains(
					"Notification")) {
				showNotificationView();
			} else {
				// if
				// (NotificationInfo.notificationRes.indexOf("Permission denied")
				// == 0) {
				CustomDialogOtherBeen customDialog = new CustomDialogOtherBeen(
						FireWallPushNotification.this.getParent());
				customDialog.dialogNotificationRootFail();
			}
			callbyonCreate = false;
		}
	}
	/**
	 * 在onResume中执行的获取通知栏信息的方法
	 * @author Administrator
	 *
	 */
	private class AsyncTaskGetAdbArrayListonResume extends
			AsyncTask<Context, Long, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationInfo.startRootcomand(context);
		}

		@Override
		protected Boolean doInBackground(Context... params) {
			while (String.valueOf(NotificationInfo.notificationRes).trim() == "") {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			showLog("onResumenotificationRes="
					+ NotificationInfo.notificationRes.toString());
			if (String.valueOf(NotificationInfo.notificationRes).contains(
					"Notification")) {
				showNotificationView();
				NotificationInfo.callbyonResume = false;
			} else {
				// if
				// (NotificationInfo.notificationRes.indexOf("Permission denied")
				// == 0) {
				CustomDialogOtherBeen customDialog = new CustomDialogOtherBeen(
						FireWallPushNotification.this.getParent());
				customDialog.dialogNotificationRootFail();
			}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// if (SQLStatic.isshowLog) {
		Log.d("FireNotif", string);
		// }
	}
}
