package com.hiapk.alertdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import com.hiapk.spearhead.R;

public class CustomDialogFAQBeen {
	private Context context;

	public CustomDialogFAQBeen(Context context) {
		this.context = context;
	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogAbout() {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.faq, null);
		final CustomDialog monthHasUsedAlert = new CustomDialog.Builder(context)
				.setwindowHeight(0.65).setTitle("�����ȷ��������")
				.setContentView(textEntryView).setPositiveButton("ȷ��", null)
				.create();
		try {
			WebView wb = (WebView) textEntryView.findViewById(R.id.webview);
			String url = "file:///android_asset/about/about.html";
			wb.loadUrl(url);
		} catch (Exception e) {
			System.out.println("Exception while showing PopUp : "
					+ e.getMessage());
		}
		monthHasUsedAlert.show();

		Button btn_ok = (Button) monthHasUsedAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();

			}
		});

	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogFAQ() {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.faq, null);
		final CustomDialog monthHasUsedAlert = new CustomDialog.Builder(context)
				.setwindowHeight(0.85).setwindowWidth(0.9)
				.setTitle("�ȷ��������  FAQ :").setContentView(textEntryView)
				.setPositiveButton("ȷ��", null).create();
		try {
			WebView wb = (WebView) textEntryView.findViewById(R.id.webview);
			String url = "file:///android_asset/faq/faq.html";
			wb.loadUrl(url);
		} catch (Exception e) {
			System.out.println("Exception while showing PopUp : "
					+ e.getMessage());
		}
		monthHasUsedAlert.show();

		Button btn_ok = (Button) monthHasUsedAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();

			}
		});

	}

//	/**
//	 * ��ʾ��־
//	 * 
//	 * @param string
//	 */
//	private void showlog(String string) {
//		// Log.d("main3", string);
//	}
}
