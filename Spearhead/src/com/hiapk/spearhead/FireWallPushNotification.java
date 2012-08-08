package com.hiapk.spearhead;

import java.util.ArrayList;

import com.hiapk.dataexe.NotificationInfo;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.alertdialog.CustomDialogOtherBeen;
import com.hiapk.firewall.NotifListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FireWallPushNotification extends Activity {
	long time = 0;
	/**
	 * 存储通知栏信息数据
	 */
	ArrayList<String[]> notificationInfos = new ArrayList<String[]>();
	Context context;
	boolean callbyonCreate = false;
    public ListView notifListView;
	
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
//		notificationLayout.addView();
		notifListView  = new ListView(context);
		notifListView.setDivider(context.getResources().getDrawable(R.drawable.divider));
		notifListView.setDividerHeight(2);
		
		Log.i("test", notificationInfos.size() + "");
		notificationLayout.addView(notifListView,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		setAdapter();
		NotificationInfo.notificationRes = new StringBuilder();
	}

	public void setAdapter(){
		NotifListAdapter notifAdapter = new NotifListAdapter(context, notificationInfos);
		notifListView.setAdapter(notifAdapter);
		notifListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				menu(view);
			}
		});
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
	public void menu(View arg1){
		final Drawable d = context.getResources().getDrawable(
				R.drawable.bg_fire_option);
		final PackageInfo pkgInfo = (PackageInfo)arg1.getTag(R.id.tag_notif_pkgInfo);
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkgname = pkgInfo.applicationInfo.packageName.toString();
		final String idString = (String) arg1.getTag(R.id.tag_notif_id);
		int ids = 1987;
		try {
			ids = Integer.parseInt(idString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		final int id = ids;
		LayoutInflater factory = LayoutInflater.from(FireWallPushNotification.this);
		final View mNotifDialogView = factory.inflate(R.layout.fire_notif_options, null);
		final AlertDialog mNotifDialog = new AlertDialog.Builder(
				getParent()).create();
		mNotifDialog.show();
		Window window = mNotifDialog.getWindow();
		window.setContentView(mNotifDialogView, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		final int heigh = window.getWindowManager().getDefaultDisplay()
				.getHeight();
		final int width = window.getWindowManager().getDefaultDisplay()
				.getWidth();
		window.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);
		final TextView uninstall = (TextView) mNotifDialogView
				.findViewById(R.id.button_notif_uninstalled);
		final TextView ban = (TextView) mNotifDialogView
				.findViewById(R.id.button_notif_Ban);
		final TextView clear = (TextView) mNotifDialogView
				.findViewById(R.id.button_notif_clear);
		final TextView back = (TextView) mNotifDialogView
				.findViewById(R.id.button_notif_back);
		
		uninstall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.fromParts("package", pkgname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE, uri);
				startActivity(intent);
				uninstall.setBackgroundDrawable(d);
				mNotifDialog.cancel();
			}
		});
		
		ban.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < FireWallActivity.uidList.size(); i++) {
					if(FireWallActivity.uidList.get(i) == uid){
						FireWallActivity.banPosition = i + 1;
					}
				}
				FireWallMainScene.switScene(0);
				ban.setBackgroundDrawable(d);
				mNotifDialog.cancel();
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NotificationManager nm = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
				nm.cancel(id);
				clear.setBackgroundDrawable(d);
				mNotifDialog.cancel();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back.setBackgroundDrawable(d);
				mNotifDialog.cancel();
			}
		});
		
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
		if (NotificationInfo.notificationRes.length() == 0) {
			new AsyncTaskGetAdbArrayListonResume().execute(context);
		}
	}

	/**
	 * 在onCreate中执行的获取通知栏信息的方法
	 * 
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
			while (NotificationInfo.notificationRes.length() == 0) {
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
	 * 
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
			while (NotificationInfo.notificationRes.length() == 0) {
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
		if (SQLStatic.isshowLog) {
			Log.d("FireNotif", string);
		}
	}
}
