package com.hiapk.ui.custom;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hiapk.firewall.Block;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;

public class CustomDialogMain2Been {
	private Context context;

	public CustomDialogMain2Been(Context context) {
		this.context = context;
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
		alertDialog.show();

		Button btn_ok = (Button) alertDialog.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				Block.clearRules(context);
				alertDialog.dismiss();
				// TODO 开始初始化防火墙代码

			}
		});
	}

}
