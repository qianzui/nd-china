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
		alertDialog.show();

		Button btn_ok = (Button) alertDialog.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				Block.clearRules(context);
				alertDialog.dismiss();
				// TODO ��ʼ��ʼ������ǽ����

			}
		});
	}

}
