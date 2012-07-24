package com.hiapk.progressdialog;

import com.hiapk.spearhead.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {
	private CustomProgressDialog customProgressDialog = null;
	private View textEntryView;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		LayoutInflater factory = LayoutInflater.from(context);
		textEntryView = factory.inflate(R.layout.custom_progress_dialog, null);
		customProgressDialog.setContentView(textEntryView);
		// 设置窗体高宽
		Window window = customProgressDialog.getWindow();
		int heigh = window.getWindowManager().getDefaultDisplay().getHeight();
		int width = window.getWindowManager().getDefaultDisplay().getWidth();
		window.setLayout((int) (width * 0.9), (int) (heigh * 0.25));
		// 设置位置
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setTitile 標題
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public void setTitile(String strTitle) {
		if (strTitle != "") {
			TextView tv_title = (TextView) textEntryView
					.findViewById(R.id.custom_progressbar_title);
			tv_title.setText(strTitle);
		}
	}

	/**
	 * 
	 * [Summary] setMessage 提示內容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public void setMessage(String strMessage) {
		if (strMessage != "") {
			TextView tv_mess = (TextView) textEntryView
					.findViewById(R.id.custom_progressbar_message);
			tv_mess.setText(strMessage);
		}
	}
}
