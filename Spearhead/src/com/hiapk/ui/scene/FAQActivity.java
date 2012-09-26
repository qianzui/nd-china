package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FAQActivity extends Activity {
	private ImageView faq_back;
	private RelativeLayout faq_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_layout);
		initUi();
		initWebView();
	}

	private void initWebView() {
		WebView faq_webview = (WebView) findViewById(R.id.FAQ_webView);
		final TextView tv_faq = (TextView) findViewById(R.id.tv_webView);
		faq_webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				tv_faq.setText(progress + 1 + "%");
				if (progress == 100)
					tv_faq.setText("");
			}
		});
		faq_webview.loadUrl("file:///android_asset/faq/faq.html");
	}

	private void initUi() {
		faq_title = (RelativeLayout) findViewById(R.id.faq_title);
		faq_title
				.setBackgroundResource(SkinCustomMains.buttonTitleBackground());
		faq_back = (ImageView) findViewById(R.id.faq_back);
		faq_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
