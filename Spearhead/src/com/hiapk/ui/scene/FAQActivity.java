package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.R.id;
import com.hiapk.spearhead.R.layout;
import com.hiapk.ui.skin.SkinCustomMains;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FAQActivity extends Activity {

	private WebView faq_webview;
	private ImageView faq_back;
	private RelativeLayout faq_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_layout);
		initUi();
	}
	private void initUi() {
		// TODO Auto-generated method stub
		faq_webview = (WebView)findViewById(R.id.faq_webview);
		faq_back = (ImageView)findViewById(R.id.faq_back);
		faq_title = (RelativeLayout)findViewById(R.id.faq_title);
		
		faq_webview.loadUrl("file:///android_asset/faq/faq.html");
		faq_title.setBackgroundResource(SkinCustomMains
				.buttonTitleBackground());
		faq_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
}
