package com.hiapk.ui.scene.weibo.tencent;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hiapk.contral.weibo.AccessTokenKeeperTencent;
import com.hiapk.contral.weibo.WeiboTecentMethod;
import com.hiapk.control.widget.DetectNetwork;
import com.hiapk.logs.Logs;
import com.hiapk.logs.WriteLog;
import com.hiapk.spearhead.R;
import com.hiapk.ui.custom.CustomDialog;
import com.hiapk.ui.skin.SkinCustomMains;

/**
 * A dialog activity for sharing any text or image message to weibo. Three
 * parameters , accessToken, tokenSecret, consumer_key, are needed, otherwise a
 * WeiboException will be throwed.
 * 
 * ShareActivity should implement an interface, RequestListener which will
 * return the request result.
 * 
 * @author (luopeng@staff.sina.com.cn zhangjie2@staff.sina.com.cn 官方微博：WBSDK
 *         http://weibo.com/u/2791136085)
 */

public class WeiboTencentActivity extends Activity implements OnClickListener {
	private Context context = this;
	private TextView mTextNum;
	private Button mSend;
	private Button close;
	private EditText mEdit;
	private FrameLayout mPiclayout;
	private ImageView mImage;
	private String mContent;
	private String TAG = "weibotencentActivity";
	private WeiboTecentMethod weiboTencentM;
	private WriteLog writelog;
	public static final int WEIBO_MAX_LENGTH = 140;
	private String screenShootPath;
	private OAuthV1 oAuth;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.weibosdk_share_mblog_view_tencent);
		weiboTencentM = new WeiboTecentMethod(context);
		writelog = new WriteLog(context);
		Intent intent = getIntent();
		screenShootPath = intent.getExtras().getString("path");
		// oAuth = (OAuthV1) intent.getExtras().getSerializable("oauth");
		// Logs.d(TAG, oAuth.getOauthConsumerKey());
		// Logs.d(TAG, oAuth.getOauthConsumerSecret());
		Logs.d(TAG, screenShootPath);
		initScene();
		initbtns();
		initEdit();
		initPic();

	}

	private void initScene() {
		close = (Button) this.findViewById(R.id.weibosdk_btnClose);
		mSend = (Button) this.findViewById(R.id.weibosdk_btnSend);
		mTextNum = (TextView) this.findViewById(R.id.weibosdk_tv_text_limit);
		mEdit = (EditText) this.findViewById(R.id.weibosdk_etEdit);
		RelativeLayout title = (RelativeLayout) findViewById(R.id.weibosdk_rlTitle);
		title.setBackgroundResource(SkinCustomMains.titleBackground());
		close.setBackgroundResource(SkinCustomMains.buttonBackgroundLight());
		mSend.setBackgroundResource(SkinCustomMains.buttonBackgroundLight());
	}

	private void initbtns() {
		close.setOnClickListener(this);
		Logs.d(TAG, "AccessToken=" + weiboTencentM.hasAccessToken());
		if (weiboTencentM.hasAccessToken()) {
			mSend.setText(getResources().getString(R.string.weibosdk_send_send));
		} else {
			mSend.setText(getResources()
					.getString(R.string.weibosdk_send_login));
		}
		mSend.setOnClickListener(this);
	}

	private void initEdit() {
		mContent = getResources().getString(R.string.weibosdk_edittext_content);
		LinearLayout text_limit_unit_layout = (LinearLayout) this
				.findViewById(R.id.weibosdk_ll_text_limit_unit);
		text_limit_unit_layout.setOnClickListener(this);
		mEdit.setSingleLine(false);
		mEdit.setHorizontallyScrolling(false);
		mEdit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String mText = mEdit.getText().toString();
				int len = mText.length();
				if (len <= WEIBO_MAX_LENGTH) {
					mTextNum.setTextColor(getResources().getColor(
							R.color.weibo_textnumber_color));
					len = WEIBO_MAX_LENGTH - len;
					// mTextNum.setTextColor(R.color.weibosdk_text_num_gray);
					if (!mSend.isEnabled())
						mSend.setEnabled(true);
				} else {
					len = len - WEIBO_MAX_LENGTH;
					mTextNum.setTextColor(Color.RED);
					if (mSend.isEnabled())
						mSend.setEnabled(false);
				}
				mTextNum.setText(String.valueOf(len));
			}
		});
		mEdit.setText(mContent);
		mEdit.setSelection(String.valueOf(mContent).length());

	}

	private void initPic() {
		// 图片部分
		mPiclayout = (FrameLayout) WeiboTencentActivity.this
				.findViewById(R.id.weibosdk_flPic);
		// 设置图片的大小
		int width = this.getWindowManager().getDefaultDisplay().getWidth();
		int height = this.getWindowManager().getDefaultDisplay().getHeight();
		ViewGroup.LayoutParams param = mPiclayout.getLayoutParams();
		param.height = (int) (height / 2.5);
		param.width = (int) (width / 2);
		mPiclayout.setLayoutParams(param);
		if (screenShootPath == null || screenShootPath == "") {
			mPiclayout.setVisibility(View.GONE);
		} else {
			// xx
			ImageView picture = (ImageView) this
					.findViewById(R.id.weibosdk_ivDelPic);
			picture.setOnClickListener(this);
			// 图片
			mImage = (ImageView) this.findViewById(R.id.weibosdk_ivImage);
			File file = new File(screenShootPath);
			mImage.setImageURI(Uri.fromFile(file));

		}

	}

	// /**
	// * 获取缩略图
	// *
	// * @param options
	// * @return
	// */
	// private Bitmap loadImageBitmap() {
	// File file = new File(screenShootPath);
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// BufferedInputStream buf;
	// Bitmap bitmap = null;
	// options.inSampleSize = 2;
	// options.inJustDecodeBounds = false;
	// try {
	// buf = new BufferedInputStream();
	// bitmap = BitmapFactory.decodeStream(buf, null, options);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return bitmap;
	// }

	// @Override
	// public void onComplete(String response) {
	// runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// Toast.makeText(context, R.string.weibosdk_send_sucess,
	// Toast.LENGTH_LONG).show();
	// }
	// });
	// this.finish();
	// }
	//
	// @Override
	// public void onIOException(IOException e) {
	// Toast.makeText(context, R.string.weibosdk_send_failed,
	// Toast.LENGTH_LONG).show();
	// mSend.setEnabled(true);
	// writelog.writeLog(e);
	// Logs.d(TAG,
	// "onIOException"
	// + String.format(
	// context.getString(R.string.weibosdk_send_failed)
	// + ":%s", e.getMessage()));
	// }
	//
	// @Override
	// public void onError(final WeiboException e) {
	// System.out.println(e);
	// runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// if (e.getMessage().contains(":20019,")) {
	// Toast.makeText(context,
	// R.string.weibosdk_send_failed_sendsame,
	// Toast.LENGTH_LONG).show();
	// } else {
	// Toast.makeText(context, R.string.weibosdk_send_failed,
	// Toast.LENGTH_LONG).show();
	// }
	// mSend.setEnabled(true);
	// writelog.writeLog(e);
	// Logs.d(TAG,
	// "onError"
	// + String.format(
	// context.getString(R.string.weibosdk_send_failed)
	// + ":%s", e.getMessage()));
	// }
	// });
	// }

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.weibosdk_btnClose) {
			finish();
		} else if (viewId == R.id.weibosdk_btnSend) {
			if (DetectNetwork.isNetworkAvailable(context)) {
				btnSendOnPressed();
			} else {
				Toast.makeText(context, R.string.weibosdk_no_network,
						Toast.LENGTH_SHORT).show();
			}
		} else if (viewId == R.id.weibosdk_ll_text_limit_unit) {
			customDialogClearEdittext();

		} else if (viewId == R.id.weibosdk_ivDelPic) {
			customDialogDeletePic();
		}
	}

	// static Uri imageUri;

	// public void takePicture() {
	//
	// Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	//
	// File photo = new File(Environment.getExternalStorageDirectory(),
	// "pic1.jpg");
	// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
	// imageUri = Uri.fromFile(photo);
	//
	// this.startActivityForResult(intent, 32 + 0 + 1);
	// }
	@Override
	public void finish() {
		deleteScreenShootPNG();
		super.finish();
	}

	/**
	 * 点击btnSend进行的微博发送，认证等操作
	 */
	private void btnSendOnPressed() {
		if (weiboTencentM.hasAccessToken()) {
			this.mContent = mEdit.getText().toString();
			if (TextUtils.isEmpty(mContent)) {
				Toast.makeText(this, R.string.weibosdk_send_sending_enmpty,
						Toast.LENGTH_LONG).show();
				return;
			}
			oAuth = AccessTokenKeeperTencent.readAccessToken(context);
			String response = "";
			mSend.setEnabled(false);
			if (screenShootPath == null || screenShootPath == "") {
				response = sendnoPicweibo(oAuth);
				// Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
			} else {
				response = sendPicweibo(oAuth, screenShootPath);
				// Toast.makeText(this, response, Toast.LENGTH_LONG).show();
			}
			Logs.d(TAG, response);
			if (response.indexOf("errcode\":0,") != -1) {
				Toast.makeText(this, R.string.weibosdk_send_sucess,
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				writelog.writeLog(response);
				Toast.makeText(this, R.string.weibosdk_send_failed,
						Toast.LENGTH_LONG).show();
				mSend.setEnabled(true);
			}
		} else {
			oAuth = new OAuthV1("null");
			oAuth.setOauthConsumerKey(weiboTencentM.getOauthConsumeKey());
			oAuth.setOauthConsumerSecret(weiboTencentM.getOauthConsumerSecret());
			try {
				// 向腾讯微博开放平台请求获得未授权的Request_Token
				oAuth = OAuthV1Client.requestToken(oAuth);
			} catch (Exception e) {
				e.printStackTrace();
				writelog.writeLog(e);
				Toast.makeText(context,
						R.string.weibosdk_tencent_response_fail,
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (oAuth.getStatus() == 1) {
				Toast.makeText(context,
						R.string.weibosdk_tencent_response_fail,
						Toast.LENGTH_SHORT).show();
				return;
			}
			Logs.d(TAG, "进入验证页面");
			Intent intent = new Intent(WeiboTencentActivity.this,
					OAuthV1AuthorizeWebView.class);// 创建Intent，使用WebView让用户授权
			intent.putExtra("oauth", oAuth);
			startActivityForResult(intent, 1);
			// getOAUTH2(WeiboTencentActivity.this);
			// mSsoHandler = new SsoHandler(WeiboSinaActivity.this, mWeibo);
			// mSsoHandler.authorize(new AuthDialogListener());
		}

	}

	private String sendnoPicweibo(OAuthV1 oAuth) {
		TAPI tAPI;
		String response = "";
		Toast.makeText(this,
				getResources().getString(R.string.weibosdk_send_sending),
				Toast.LENGTH_SHORT).show();
		Logs.d(TAG, "开始发送无图片微博");
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
		try {
			response = tAPI.add(oAuth, "json", getResources().getString(R.string.weibosdk_edittext_content), "127.0.0.1");
		} catch (Exception e) {
			e.printStackTrace();
			writelog.writeLog(e);
		}
		tAPI.shutdownConnection();
		return response;
	}

	private String sendPicweibo(OAuthV1 oAuth, String picpath) {
		TAPI tAPI;
		String response = "";
		Toast.makeText(this,
				getResources().getString(R.string.weibosdk_send_sending),
				Toast.LENGTH_SHORT).show();
		Logs.d(TAG, "开始发送图片微博");
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
		try {
			response = tAPI.addPic(oAuth, "json", getResources().getString(R.string.weibosdk_edittext_content),
					"127.0.0.1", picpath);
		} catch (Exception e) {
			e.printStackTrace();
			writelog.writeLog(e);
		}
		tAPI.shutdownConnection();

		return response;
	}

	/**
	 * 弹出自定义对话框删除整条微博
	 */
	private void customDialogClearEdittext() {
		final CustomDialog clearEdittext = new CustomDialog.Builder(context)
				.setTitle(R.string.weibosdk_attention)
				.setMessage(R.string.weibosdk_delete_all)
				.setPositiveButton(R.string.weibosdk_ok, null)
				.setNegativeButton(R.string.weibosdk_cancel, null).create();
		clearEdittext.show();
		Button btn_yes = (Button) clearEdittext
				.findViewById(R.id.positiveButton);
		btn_yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEdit.setText("");
				clearEdittext.dismiss();
			}
		});
		Button btn_no = (Button) clearEdittext
				.findViewById(R.id.negativeButton);
		btn_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearEdittext.dismiss();
			}
		});

	}

	/**
	 * 弹出自定义对话框删除图片
	 */
	private void customDialogDeletePic() {

		final CustomDialog deletePNG = new CustomDialog.Builder(context)
				.setTitle(R.string.weibosdk_attention)
				.setMessage(R.string.weibosdk_del_pic)
				.setPositiveButton(R.string.weibosdk_ok, null)
				.setNegativeButton(R.string.weibosdk_cancel, null).create();
		deletePNG.show();
		Button btn_yes = (Button) deletePNG.findViewById(R.id.positiveButton);
		btn_yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPiclayout.setVisibility(View.GONE);
				deleteScreenShootPNG();
				deletePNG.dismiss();
			}
		});
		Button btn_no = (Button) deletePNG.findViewById(R.id.negativeButton);
		btn_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deletePNG.dismiss();
			}
		});

	}

	/**
	 * 删除保存的png文件释放空间
	 */
	private void deleteScreenShootPNG() {
		if (screenShootPath == null || screenShootPath == "") {
			return;
		} else {
			File file = new File(screenShootPath);
			try {
				if (file.isFile() && file.exists()) {
					if (file.delete()) {
						Logs.d(TAG, "删除成功！");
					} else {
						Logs.d(TAG, "删除失败！");
					}
				}
			} catch (Exception e) {
				Logs.d(TAG, "发生异常，删除文件失败！");
			}
			screenShootPath = "";
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logs.d(TAG, "onActivityResult");
		if (requestCode == 1) {
			if (resultCode == OAuthV1AuthorizeWebView.RESULT_CODE) {
				// 从返回的Intent中获取验证码
				oAuth = (OAuthV1) data.getExtras().getSerializable("oauth");
				// Toast.makeText(context,
				// "nverifier=" + oAuth.getOauthVerifier(),
				// Toast.LENGTH_SHORT).show();
				try {
					oAuth = OAuthV1Client.accessToken(oAuth);
					/*
					 * 注意：此时oauth中的Oauth_token和Oauth_token_secret将发生变化，用新获取到的
					 * 已授权的access_token和access_token_secret替换之前存储的未授权的request_token
					 * 和request_token_secret.
					 */
					AccessTokenKeeperTencent.keepAccessToken(context, oAuth);
					mSend.setText(R.string.weibosdk_send_send);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Toast.makeText(
				// context,
				// "\naccess_token:\n" + oAuth.getOauthToken()
				// + "\naccess_token_secret:\n"
				// + oAuth.getOauthTokenSecret(),
				// Toast.LENGTH_SHORT).show();
			}
		}

	}

}