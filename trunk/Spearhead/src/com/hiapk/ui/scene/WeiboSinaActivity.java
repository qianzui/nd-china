package com.hiapk.ui.scene;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hiapk.contral.weibo.AccessTokenKeeper;
import com.hiapk.contral.weibo.ScreenShot;
import com.hiapk.contral.weibo.WeiboSinaMethod;
import com.hiapk.logs.Logs;
import com.hiapk.spearhead.R;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.sso.SsoHandler;

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

public class WeiboSinaActivity extends Activity implements OnClickListener,
		RequestListener {
	private Context context = this;
	private TextView mTextNum;
	private Button mSend;
	private EditText mEdit;
	private FrameLayout mPiclayout;
	private ImageView mImage;
	private String mPicPath = "";
	private String mContent = "6548645348945348971634875641837218675187381768541738473";
	private String TAG = "weiboActivity";
	private WeiboSinaMethod weiboMethod;
	private final String CONSUMER_KEY = "2169350509";// 替换为开发??1??7??的appkey，例妄1??7"1646212860";
	private final String REDIRECT_URL = "http://www.baidu.com/";
	private Weibo mWeibo;
	SsoHandler mSsoHandler;

	private static final String EXTRA_WEIBO_CONTENT = "com.weibo.android.content";
	private static final String EXTRA_PIC_URI = "com.weibo.android.pic.uri";
	private static final String EXTRA_ACCESS_TOKEN = "com.weibo.android.accesstoken";
	private static final String EXTRA_EXPIRES_IN = "com.weibo.android.token.expires";
	public static final int WEIBO_MAX_LENGTH = 140;
	private String screenShootPath;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.weibosdk_share_mblog_view);
		// Intent in = this.getIntent();
		// mAccessToken = in.getStringExtra(EXTRA_ACCESS_TOKEN);
		// mAccessToken = "234534";
		// mAccessToken
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		weiboMethod = new WeiboSinaMethod(context);
		Intent intent = getIntent();
		screenShootPath = intent.getExtras().getString("path");

		Button close = (Button) this.findViewById(R.id.weibosdk_btnClose);
		close.setOnClickListener(this);
		mSend = (Button) this.findViewById(R.id.weibosdk_btnSend);
		Logs.d(TAG, "AccessToken=" + isAccessTokened());
		if (isAccessTokened()) {
			mSend.setText(getResources().getString(R.string.weibosdk_send_send));
		} else {
			mSend.setText(getResources()
					.getString(R.string.weibosdk_send_login));
		}
		mSend.setOnClickListener(this);
		LinearLayout total = (LinearLayout) this
				.findViewById(R.id.weibosdk_ll_text_limit_unit);
		total.setOnClickListener(this);
		mTextNum = (TextView) this.findViewById(R.id.weibosdk_tv_text_limit);

		mEdit = (EditText) this.findViewById(R.id.weibosdk_etEdit);
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
		// 图片部分
		mPiclayout = (FrameLayout) WeiboSinaActivity.this
				.findViewById(R.id.weibosdk_flPic);
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

			// if (TextUtils.isEmpty(this.mPicPath)) {
			// mPiclayout.setVisibility(View.GONE);
			// } else {
			//
			// File file = new File(mPicPath);
			// if (file.exists()) {
			// Bitmap pic = BitmapFactory.decodeFile(this.mPicPath);
			//
			// mImage.setImageBitmap(pic);
			// } else {
			// mPiclayout.setVisibility(View.GONE);
			// }
			// }
		}
	}

	@Override
	public void onComplete(String response) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, R.string.weibosdk_send_sucess,
						Toast.LENGTH_LONG).show();
			}
		});
		this.finish();
	}

	@Override
	public void onIOException(IOException e) {
		Toast.makeText(
				context,
				String.format(context.getString(R.string.weibosdk_send_failed)
						+ ":%s", e.getMessage()), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError(final WeiboException e) {
		System.out.println(e);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(
						context,
						String.format(
								context.getString(R.string.weibosdk_send_failed)
										+ ":%s", e.getMessage()),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();

		if (viewId == R.id.weibosdk_btnClose) {
			finish();
		} else if (viewId == R.id.weibosdk_btnSend) {
			StatusesAPI api = new StatusesAPI(
					AccessTokenKeeper.readAccessToken(context));
			if (isAccessTokened()) {
				this.mContent = mEdit.getText().toString();
				if (TextUtils.isEmpty(mContent)) {
					Toast.makeText(this, "请输入内容!", Toast.LENGTH_LONG).show();
					return;
				}
				if (screenShootPath == null || screenShootPath == "") {
					// Just update a text weibo!
					Toast.makeText(this, "开始发送无图片微博", Toast.LENGTH_SHORT)
							.show();
					api.update(this.mContent, null, null, this);
				} else {
					Toast.makeText(this, "开始发送图片微博", Toast.LENGTH_SHORT).show();
					api.upload(this.mContent, screenShootPath, null, null, this);
					// finish();
				}
				mSend.setEnabled(false);
			} else {
				Logs.d(TAG, "进入验证页面");
				getOAUTH2(WeiboSinaActivity.this);
				// mSsoHandler = new SsoHandler(WeiboSinaActivity.this, mWeibo);
				// mSsoHandler.authorize(new AuthDialogListener());
			}
		} else if (viewId == R.id.weibosdk_ll_text_limit_unit) {

			Dialog dialog = new AlertDialog.Builder(this)
					.setTitle(R.string.weibosdk_attention)
					.setMessage(R.string.weibosdk_delete_all)
					.setPositiveButton(R.string.weibosdk_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									mEdit.setText("");
								}
							})
					.setNegativeButton(R.string.weibosdk_cancel, null).create();
			dialog.show();
		} else if (viewId == R.id.weibosdk_ivDelPic) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setTitle(R.string.weibosdk_attention)
					.setMessage(R.string.weibosdk_del_pic)
					.setPositiveButton(R.string.weibosdk_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									mPiclayout.setVisibility(View.GONE);
									screenShootPath = "";
								}
							})
					.setNegativeButton(R.string.weibosdk_cancel, null).create();
			dialog.show();
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
		if (screenShootPath == null || screenShootPath == "") {

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
		}

		super.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Bitmap photo = null;
		// if (data != null) {
		// Uri uri = data.getData();
		// if (uri != null) {
		// photo = BitmapFactory.decodeFile(uri.getPath());
		// System.out.println(mPicPath);
		// }
		// if (photo == null) {
		// Bundle bundle = data.getExtras();
		// if (bundle != null) {
		// photo = (Bitmap) bundle.get("data");
		// mImage.setImageBitmap(photo);
		// } else {
		// return;
		// }
		// }
		// } else {
		// Uri selectedImage = imageUri;
		// mPicPath = selectedImage.getPath();
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 2;
		// options.inDither = true;
		// photo = BitmapFactory.decodeFile(this.mPicPath, options);
		// mPiclayout.setVisibility(View.VISIBLE);
		// mImage = (ImageView) this.findViewById(R.id.weibosdk_ivImage);
		// mImage.setImageBitmap(photo);
		// // Util.showToast(this, mPicPath);
		// }
		/**
		 * 下面两个注释掉的代码，仅当sdk支持sso时有效，
		 */
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void getOAUTH2(Activity activity) {
		// weiboMethod.setmWeibo(Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL));
		Logs.d(TAG, "isUseSSO=" + weiboMethod.isUseSSO(context));
		if (weiboMethod.isUseSSO(context)) {
			mSsoHandler = new SsoHandler(activity, mWeibo);
			mSsoHandler.authorize(new AuthDialogListener());
		} else {
			mWeibo.authorize(activity, new AuthDialogListener());
		}
	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			Logs.d(TAG, "认证成功完成");
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			Oauth2AccessToken oauth2Token = new Oauth2AccessToken(token,
					expires_in);
			if (oauth2Token.isSessionValid()) {
				mSend.setText(getResources().getString(
						R.string.weibosdk_send_send));
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(oauth2Token.getExpiresTime()));
				// mText.setText("认证成功: \r\n access_token: " + token + "\r\n"
				// + "expires_in: " + expires_in + "\r\n有效期：" + date);
				Logs.d(TAG, "认证成功: \r\n access_token: " + token + "\r\n"
						+ "expires_in: " + expires_in + "\r\n有效期：" + date);
				try {
					Class sso = Class
							.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
					// apiBtn.setVisibility(View.VISIBLE);
					// mSend.setText("发布");
					Logs.d(TAG, "AuthDialogListener=" + "send");
				} catch (ClassNotFoundException e) {
					// e.printStackTrace();
					Logs.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

				}
				// cancelBtn.setVisibility(View.VISIBLE);
				Logs.d(TAG, "AuthDialogListener=" + "login");
				// mSend.setText("登录");
				AccessTokenKeeper.keepAccessToken(context, oauth2Token);
				Toast.makeText(context, "认证成功", Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(context, "认证失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(context, "Auth error : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(context, "Auth cancel", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(context, "Auth exception : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 是否已经获取认证
	 * 
	 * @return
	 */
	protected boolean isAccessTokened() {
		return weiboMethod.hasAccessToken(context);
	}

}