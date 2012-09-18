package com.hiapk.spearhead;

import com.hiapk.control.widget.NotificationFireFailOnsysBoot;
import com.hiapk.firewall.Block;
import com.hiapk.ui.custom.CustomDialog;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WindowNotifyDialog extends Activity {
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.window_alertdialog);
		context = this;
		NotificationFireFailOnsysBoot notifyFireFail = new NotificationFireFailOnsysBoot(
				context);
		notifyFireFail.cancelAlertNotify(context);
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
					Toast.makeText(context, "����ǽ����ʧ�ܡ�", Toast.LENGTH_SHORT)
							.show();
				}
				onBackPressed();
				alertDialog.dismiss();

			}
		});
		Button btn_cancel = (Button) alertDialog
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
				alertDialog.dismiss();
			}
		});

		// 'ע��/����ǽӦ�ù���ʧ�ܣ���Ҫ����RootȨ�ޣ��������ԣ�/����/ȡ��'�� }
	}
}
