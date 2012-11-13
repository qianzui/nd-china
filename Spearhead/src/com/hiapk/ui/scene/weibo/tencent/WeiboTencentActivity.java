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
 * @author (luopeng@staff.sina.com.cn zhangjie2@staff.sina.com.cn �ٷ�΢����WBSDK
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
		// ͼƬ����
		mPiclayout = (FrameLayout) WeiboTencentActivity.this
				.findViewById(R.id.weibosdk_flPic);
		// ����ͼƬ�Ĵ�С
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
			// ͼƬ
			mImage = (ImageView) this.findViewById(R.id.weibosdk_ivImage);
			File file = new File(screenShootPath);
			mImage.setImageURI(Uri.fromFile(file));

		}

	}

	// /**
	// * ��ȡ����ͼ
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
	 * ���btnSend���е�΢�����ͣ���֤�Ȳ���
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
				// ����Ѷ΢������ƽ̨������δ��Ȩ��Request_Token
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
			Logs.d(TAG, "������֤ҳ��");
			Intent intent = new Intent(WeiboTencentActivity.this,
					OAuthV1AuthorizeWebView.class);// ����Intent��ʹ��WebView���û���Ȩ
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
		Logs.d(TAG, "��ʼ������ͼƬ΢��");
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
		try {
			response = tAPI.add(oAuth, "json", "Android�ͻ�������΢��1", "127.0.0.1");
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
		Logs.d(TAG, "��ʼ����ͼƬ΢��");
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
		try {
			response = tAPI.addPic(oAuth, "json", "Android�ͻ��˴�ͼ������΢��1",
					"127.0.0.1", picpath);
		} catch (Exception e) {
			e.printStackTrace();
			writelog.writeLog(e);
		}
		tAPI.shutdownConnection();

		return response;
	}

	/**
	 * �����Զ���Ի���ɾ������΢��
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
	 * �����Զ���Ի���ɾ��ͼƬ
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
	 * ɾ�������png�ļ��ͷſռ�
	 */
	private void deleteScreenShootPNG() {
		if (screenShootPath == null || screenShootPath == "") {
			return;
		} else {
			File file = new File(screenShootPath);
			try {
				if (file.isFile() && file.exists()) {
					if (file.delete()) {
						Logs.d(TAG, "ɾ���ɹ���");
					} else {
						Logs.d(TAG, "ɾ��ʧ�ܣ�");
					}
				}
			} catch (Exception e) {
				Logs.d(TAG, "�����쳣��ɾ���ļ�ʧ�ܣ�");
			}
			screenShootPath = "";
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logs.d(TAG, "onActivityResult");
		if (requestCode == 1) {
			if (resultCode == OAuthV1AuthorizeWebView.RESULT_CODE) {
				// �ӷ��ص�Intent�л�ȡ��֤��
				oAuth = (OAuthV1) data.getExtras().getSerializable("oauth");
				// Toast.makeText(context,
				// "nverifier=" + oAuth.getOauthVerifier(),
				// Toast.LENGTH_SHORT).show();
				try {
					oAuth = OAuthV1Client.accessToken(oAuth);
					/*
					 * ע�⣺��ʱoauth�е�Oauth_token��Oauth_token_secret�������仯�����»�ȡ����
					 * ����Ȩ��access_token��access_token_secret�滻֮ǰ�洢��δ��Ȩ��request_token
					 * ��request_token_secret.
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