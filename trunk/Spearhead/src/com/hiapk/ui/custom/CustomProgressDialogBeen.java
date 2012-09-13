package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CustomProgressDialogBeen {
	private Context context;
	private View textEntryView;

	public CustomProgressDialogBeen(Context context) {
		this.context = context;
	}

	public CustomDialog progressDialog() {
		LayoutInflater factory = LayoutInflater.from(context);
		textEntryView = factory.inflate(R.layout.custom_progress_dialog, null);
		final CustomDialog progressDialog = new CustomDialog.Builder(context)
				.setTitle("�����ʷ��¼...").setContentView(textEntryView)
				.setShowButton(false).create();
		return progressDialog;
	}
	// public CustomProgressDialogBeen createDialog(Context context) {
	// customProgressDialog = new CustomProgressDialogBeen(context,
	// R.style.CustomProgressDialog);
	// LayoutInflater factory = LayoutInflater.from(context);
	// textEntryView = factory.inflate(R.layout.custom_progress_dialog, null);
	// customProgressDialog.setContentView(textEntryView);
	// // ���ô���߿�
	// Window window = customProgressDialog.getWindow();
	// int heigh = window.getWindowManager().getDefaultDisplay().getHeight();
	// int width = window.getWindowManager().getDefaultDisplay().getWidth();
	// window.setLayout((int) (width * 0.9), (int) (heigh * 0.25));
	// // ����λ��
	// customProgressDialog.getWindow().getAttributes().gravity =
	// Gravity.CENTER;
	//
	// return customProgressDialog;
	// }
	//
	// /**
	// *
	// * [Summary] setTitile ���}
	// *
	// * @param strTitle
	// * @return
	// *
	// */
	// public void setTitile(String strTitle) {
	// if (strTitle != "") {
	// TextView tv_title = (TextView) textEntryView
	// .findViewById(R.id.custom_progressbar_title);
	// tv_title.setText(strTitle);
	// }
	// }
	//
	// /**
	// *
	// * [Summary] setMessage ��ʾ����
	// *
	// * @param strMessage
	// * @return
	// *
	// */
	// public void setMessage(String strMessage) {
	// if (strMessage != "") {
	// TextView tv_mess = (TextView) textEntryView
	// .findViewById(R.id.custom_progressbar_message);
	// tv_mess.setText(strMessage);
	// }
	// }
}
