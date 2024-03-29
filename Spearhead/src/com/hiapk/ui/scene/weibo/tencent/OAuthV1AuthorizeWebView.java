package com.hiapk.ui.scene.weibo.tencent;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


/**
 * 使用Webview显示OAuth Version 1.0 授权的页面<br>
 * <p>本类使用方法：</p>
 * <li>需要调用本类的地方请添加如下代码
 * <pre>
 * //请将OAuthV1Activity改为所在类的类名
 * Intent intent = new Intent(MainActivity.this, OAuthV1AuthorizeWebView.class);   
 * intent.putExtra("oauth", oAuth);  //oAuth为OAuthV1类的实例，存放授权相关信息
 * startActivityForResult(intent, myRrequestCode);  //请设置合适的requsetCode
 * </pre>
 * <li>重写接收回调信息的方法
 * <pre>
 * if (requestCode==myRrequestCode) {  //对应之前设置的的myRequsetCode
 *     if (resultCode==OAuthV1AuthorizeWebView.RESULT_CODE) {
 *         //取得返回的OAuthV1类实例oAuth
 *         oAuth=(OAuthV1) data.getExtras().getSerializable("oauth");
 *     }
 * }
 * <pre>
 * @see android.app.Activity#onActivityResult(int requestCode, int resultCode,  Intent data)
 */
public class OAuthV1AuthorizeWebView extends Activity
{
    public final static int RESULT_CODE=1;
	private static final String TAG = "OAuthV1AuthorizeWebView";
	private OAuthV1 oAuth;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout=new LinearLayout(this);
		WebView webView = new WebView(this);
		linearLayout.addView(webView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setContentView(linearLayout);
		Intent intent = this.getIntent();
		oAuth = (OAuthV1) intent.getExtras().getSerializable("oauth");
		String urlStr = OAuthConstants.OAUTH_V1_AUTHORIZE_URL+"?oauth_token="+oAuth.getOauthToken();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.requestFocus();
        webView.loadUrl(urlStr);
        System.out.println(urlStr.toString());
        Log.i(TAG, "WebView Starting....");
		WebViewClient client = new WebViewClient()
		{
            /**
             * 回调方法，当页面开始加载时执行
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG, "WebView onPageStarted...");
                Log.i(TAG, "URL = " + url);
                if (url.indexOf("checkType=verifycode") != -1) {
                	Log.i(TAG, "aftercheckURL = " + url);
                    int start=url.indexOf("checkType=verifycode&v=")+23;
                    String verifyCode=url.substring(start, start+6);
                    oAuth.setOauthVerifier(verifyCode);
                    Intent intent = new Intent();
                    intent.putExtra("oauth", oAuth);
                    setResult(RESULT_CODE, intent);
                    view.destroyDrawingCache();
                    finish();
                }
                super.onPageStarted(view, url, favicon);
            }

		};
		webView.setWebViewClient(client);
	}
	
}
