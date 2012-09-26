package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.Collections;

import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.ui.custom.CustomDialogOtherBeen;
import com.hiapk.util.SQLStatic;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.MyCompNotifName;
import com.hiapk.firewall.NotifListAdapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
	 * �洢֪ͨ����Ϣ����
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
		// Ϊ���˳���
		SpearheadApplication.getInstance().addActivity(this);
		context = this;
		callbyonCreate = true;
		new AsyncTaskGetAdbArrayListonCreate().execute(context);
	}

	/**
	 * ��ʾ֪ͨ��������Ŀ��Ϣ
	 */
	public void showNotificationView() {
		notificationInfos = NotificationInfo
				.getNotificationApp(NotificationInfo.notificationRes);
		LinearLayout notificationLayout = (LinearLayout) findViewById(R.id.adblock_content);
		notificationLayout.removeAllViews();
		showLog("**********���������ʾ(�������Ǹ�����)*************");
		// notificationLayout.addView();
		notifListView = new ListView(context);
		notifListView.setDivider(context.getResources().getDrawable(
				R.drawable.divider));
		notificationLayout.addView(notifListView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setAdapter();
		NotificationInfo.notificationRes = new StringBuilder();
	}

	public void setAdapter() {
		MyCompNotifName comp = new MyCompNotifName();
		comp.init(context);
		Collections.sort(notificationInfos, comp);
		NotifListAdapter notifAdapter = new NotifListAdapter(context,
				notificationInfos);
		notifListView.setAdapter(notifAdapter);
		notifListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				menu(view);
			}
		});
	}

	/**
	 * ��ʾ��ȡ��
	 */
	public void showLoadingView() {
		LinearLayout notificationLayout = (LinearLayout) findViewById(R.id.adblock_content);
		notificationLayout.removeAllViews();
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View loading = mInflater.inflate(R.layout.loading_layout, null);
		TextView text = (TextView) loading.findViewById(R.id.tv_loading);
		text.setText("����ɨ��֪ͨ����Ϣ������");
		notificationLayout.addView(loading);
	}

	public void menu(View arg1) {
		final PackageInfo pkgInfo = (PackageInfo) arg1
				.getTag(R.id.tag_notif_pkgInfo);
		final int uid = pkgInfo.applicationInfo.uid;
		final String pkgname = pkgInfo.applicationInfo.packageName.toString();
		final String idString = (String) arg1.getTag(R.id.tag_notif_id);
		int ids = 1987;
		try {
			ids = Integer.parseInt(idString);
		} catch (Exception e) {
		}
		final int id = ids;
		LayoutInflater factory = LayoutInflater
				.from(FireWallPushNotification.this);
		final View mNotifDialogView = factory.inflate(
				R.layout.fire_notif_options, null);
		final AlertDialog mNotifDialog = new AlertDialog.Builder(getParent())
				.create();
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
				Uri uri = Uri.fromParts("package", pkgname, null);
				Intent intent = new Intent(Intent.ACTION_DELETE, uri);
				startActivity(intent);
				mNotifDialog.cancel();
			}
		});

		if (FireWallActivity.uidList.contains(uid)
				&& (PackageManager.PERMISSION_GRANTED == getPackageManager()
						.checkPermission(Manifest.permission.INTERNET, pkgname))
				&& SQLStatic.packagename_ALL.contains(pkgname)
				&& !Block.filter.contains(pkgname)) {
			ban.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < FireWallActivity.uidList.size(); i++) {
						if (FireWallActivity.uidList.get(i) == uid) {
						}
					}
					FireWallMainScene.switScene(0);
					mNotifDialog.cancel();
				}
			});
		} else {
			ban.setTextColor(Color.GRAY);
		}
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// NotificationManager nm = (NotificationManager)
				// getSystemService(context.NOTIFICATION_SERVICE);
				// nm.cancel(id);
				// clear.setBackgroundDrawable(d);
				// mNotifDialog.cancel();
				// Uri uri = Uri.fromParts("package", pkgname, null);
				// Intent intent = new Intent(Intent.action_a, uri);
				// startActivity(intent);
				ShowAppInfo.showInstalledAppDetails(context, pkgname);
				mNotifDialog.cancel();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mNotifDialog.cancel();
			}
		});

	}

	@Override
	protected void onResume() {
		showLog("onResume");
		showLog("callbyonResume=" + NotificationInfo.callbyonCancel);
		super.onResume();
		if (callbyonCreate)
			return;
		if (NotificationInfo.callbyonCancel)
			return;
		if (NotificationInfo.callbyonFirstBacktoFire)
			return;

		showLoadingView();
		NotificationInfo.callbyonCancel = true;
		if (NotificationInfo.notificationRes.length() == 0) {
			new AsyncTaskGetAdbArrayListonResume().execute(context);
		}
	}

	/**
	 * ��onCreate��ִ�еĻ�ȡ֪ͨ����Ϣ�ķ���
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
	 * ��onResume��ִ�еĻ�ȡ֪ͨ����Ϣ�ķ���
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
				NotificationInfo.callbyonCancel = false;
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

	static class ShowAppInfo {
		private static final String SCHEME = "package";
		/**
		 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.1��֮ǰ�汾)
		 */
		private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
		/**
		 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.2)
		 */
		private static final String APP_PKG_NAME_22 = "pkg";
		/**
		 * InstalledAppDetails���ڰ���
		 */
		private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
		/**
		 * InstalledAppDetails����
		 */
		private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

		/**
		 * ����ϵͳInstalledAppDetails������ʾ�Ѱ�װӦ�ó������ϸ��Ϣ�� ����Android 2.3��Api Level
		 * 9�����ϣ�ʹ��SDK�ṩ�Ľӿڣ� 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩��
		 * 
		 * @param context
		 * 
		 * @param packageName
		 *            Ӧ�ó���İ���
		 */
		static void showInstalledAppDetails(Context context, String packageName) {
			Intent intent = new Intent();
			final int apiLevel = Build.VERSION.SDK_INT;
			if (apiLevel >= 9) { // 2.3��ApiLevel 9�����ϣ�ʹ��SDK�ṩ�Ľӿ�
				intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
				Uri uri = Uri.fromParts(SCHEME, packageName, null);
				intent.setData(uri);
			} else { // 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩
				// 2.2��2.1�У�InstalledAppDetailsʹ�õ�APP_PKG_NAME��ͬ��
				final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
						: APP_PKG_NAME_21);
				intent.setAction(Intent.ACTION_VIEW);
				intent.setClassName(APP_DETAILS_PACKAGE_NAME,
						APP_DETAILS_CLASS_NAME);
				intent.putExtra(appPkgName, packageName);
			}
			context.startActivity(intent);
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
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("FireNotif", string);
		}
	}
}
