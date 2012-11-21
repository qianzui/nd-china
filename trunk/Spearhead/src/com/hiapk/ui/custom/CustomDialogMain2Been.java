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
	 * 防火墙启动失败时，弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogFireWallOpenFail() {
		final CustomDialog alertDialog = new CustomDialog.Builder(context)
				.setTitle("注意").setMessage("防火墙应用规则失败，需要申请Root权限，请点击重试！")
				.setPositiveButton("重试", null).setNegativeButton("取消", null)
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
					Toast.makeText(context, "防火墙启动失败。", Toast.LENGTH_SHORT)
							.show();
				} else {
					SpearheadApplication.getInstance().getsharedData()
							.setIsFireWallOpenFail(false);
				}
				alertDialog.dismiss();
				// TODO 开始初始化防火墙代码

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
				// TODO 开始初始化防火墙代码

			}
		});
	}

	/**
	 * 通知栏广告需要获取root权限-----未被使用
	 */
	public void dialogNotificationNeedRoot() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("注意：").setMessage("广告检测功能需要Root权限！")
				.setPositiveButton("确定", null).create();
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
	 * 通知栏检测时获取root权限失败
	 */
	public void dialogNotificationRootFail() {

		final CustomDialog scanNotificationRootFail = new CustomDialog.Builder(
				context)
				.setTitle("操作失败")
				.setMessage(
						"获取Root权限失败，可能原因：\n\n1.您的设备未破解\n2.尝试获取Root失败\n建议退出后重新尝试。")
				.setPositiveButton("重试", null).setNegativeButton("取消", null)
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
				.setTitle("未能开启防火墙")
				.setTv_size(18)
				.setMessage(
						"由于安卓系统的限制,只有获得最高权限(称为\"Root\")的机器才能使用防火墙功能。\n当前操作失败,可能原因有:\n\n1.您拒绝了Root权限 \n2.系统原因,防火墙应用失败 \n3.部分机型不支持防火墙操作")
				.setPositiveButton("确定", null).create();
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
