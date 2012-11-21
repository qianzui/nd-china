package com.hiapk.ui.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.firewall.Block;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.Splash;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.util.SharedPrefrenceData;

public class CustomDialogMain2Been {
	private Context context;
	private SharedPrefrenceData sharedData;

	public CustomDialogMain2Been(Context context) {
		this.context = context;
		sharedData = new SharedPrefrenceData(context);
	}

	/**
	 * ����ǽ����ʧ��ʱ�������ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogFireWallOpenFail() {
		final CustomDialog alertDialog = new CustomDialog.Builder(context)
				.setTitle("ע��").setMessage("����ǽӦ�ù���ʧ�ܣ���Ҫ����RootȨ�ޣ��������ԣ�")
				.setPositiveButton("����", null).setNegativeButton("ȡ��", null)
				.create();
		FireWallActivity.isInScene = false;
		alertDialog.show();

		Button btn_ok = (Button) alertDialog.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = true;
				boolean isOpenSucess = false;
				isOpenSucess = Block.applyIptablesRules(context, true, false);
				if (!isOpenSucess) {
					Block.clearRules(context);
					Toast.makeText(context, "����ǽ����ʧ�ܡ�", Toast.LENGTH_SHORT)
							.show();
				} else {
					SpearheadApplication.getInstance().getsharedData()
							.setIsFireWallOpenFail(false);
				}
				alertDialog.dismiss();
				// TODO ��ʼ��ʼ������ǽ����

			}
		});
		Button btn_cancel = (Button) alertDialog
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = true;
				Block.clearRules(context);
				alertDialog.dismiss();
				// TODO ��ʼ��ʼ������ǽ����

			}
		});
	}

	/**
	 * ֪ͨ�������Ҫ��ȡrootȨ��-----δ��ʹ��
	 */
	public void dialogNotificationNeedRoot() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("ע�⣺").setMessage("����⹦����ҪRootȨ�ޣ�")
				.setPositiveButton("ȷ��", null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// SharedPrefrenceDataOnUpdate sharedUP = new
				// SharedPrefrenceDataOnUpdate(
				// context);
				// sharedUP.setAdbRootAllow(true);
				// NotificationInfo.startRootcomand(context);
				monthSetAlert.dismiss();
			}
		});
	}

	/**
	 * ֪ͨ�����ʱ��ȡrootȨ��ʧ��
	 */
	public void dialogNotificationRootFail() {

		final CustomDialog scanNotificationRootFail = new CustomDialog.Builder(
				context)
				.setTitle("����ʧ��")
				.setMessage(
						"��ȡRootȨ��ʧ�ܣ�����ԭ��\n\n1.�����豸δ�ƽ�\n2.���Ի�ȡRootʧ��\n�����˳������³��ԡ�")
				.setPositiveButton("����", null).setNegativeButton("ȡ��", null)
				.create();
		scanNotificationRootFail.show();
		FireWallActivity.isInScene = false;
		// FireWallActivity.isNotifFail = true;
		Button btn_ok = (Button) scanNotificationRootFail
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = false;
				// FireWallActivity.isNotifFail = false;
				// NotificationInfo.callbyonResume = false;
				NotificationInfo.isgettingdata = false;
				NotificationInfo.notificationRes = new StringBuilder();
				NotificationInfo.hasdata = false;
				Splash.switchScene(0);
				Splash.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
		Button btn_cancel = (Button) scanNotificationRootFail
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = true;
				// FireWallActivity.isNotifFail = false;
				sharedData.setFireWallType(0);
				NotificationInfo.callbyonCancel = true;
				NotificationInfo.notificationRes = new StringBuilder();
				Splash.switchScene(0);
				Splash.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
		scanNotificationRootFail.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				FireWallActivity.isInScene = true;
				sharedData.setFireWallType(0);
				NotificationInfo.notificationRes = new StringBuilder();
				NotificationInfo.callbyonCancel = true;
				Splash.switchScene(0);
				Splash.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
	}
	
	public void dialogOpenFireWallFail() {
		final CustomDialog monthSetAlert;
		monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("δ�ܿ�������ǽ")
				.setTv_size(18)
				.setMessage(
						"���ڰ�׿ϵͳ������,ֻ�л�����Ȩ��(��Ϊ\"Root\")�Ļ�������ʹ�÷���ǽ���ܡ�\n��ǰ����ʧ��,����ԭ����:\n\n1.���ܾ���RootȨ�� \n2.ϵͳԭ��,����ǽӦ��ʧ�� \n3.���ֻ��Ͳ�֧�ַ���ǽ����")
				.setPositiveButton("ȷ��", null).create();
		FireWallActivity.isRootFail = true;
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FireWallActivity.isRootFail = false;
				monthSetAlert.dismiss();
			}
		});
		monthSetAlert.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				FireWallActivity.isRootFail = false;
				monthSetAlert.dismiss();
			}
		});
	}
}
