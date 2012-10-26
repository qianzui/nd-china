package com.hiapk.contral.weibo;

import com.hiapk.logs.Logs;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.sso.SsoHandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class WeiboSinaMethod {

	private Oauth2AccessToken accessToken;
	/**
	 * SsoHandler 仅当sdk支持sso时有效，
	 */
//	public SsoHandler mSsoHandler;

	private final String TAG = "weiboSinaM";
	private Weibo mWeibo;

	private Context context;

	public WeiboSinaMethod(Context context) {
		this.context = context;
	}

//	public Weibo getmWeibo() {
//		return mWeibo;
//	}
//
//	public void setmWeibo(Weibo mWeibo) {
//		this.mWeibo = mWeibo;
//	}

	/**
	 * 测试是否已经获取认证
	 * 
	 * @param context
	 * @return
	 */
	public boolean hasAccessToken(Context context) {
		if (accessToken == null || accessToken.equals("")) {
			accessToken = AccessTokenKeeper.readAccessToken(context);
		}
		Logs.d(TAG, "getToken=" + accessToken.getToken() + "getExpiresTime="
				+ accessToken.getExpiresTime());
		if (accessToken.isSessionValid()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测是否可以使用SSO方式获取sina授权//com.sina.weibo
	 * 
	 * @param context
	 * @return
	 */
	public boolean isUseSSO(Context context) {
		String packageName = "com.sina.weibo";
		try {
			PackageInfo pacInfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			if (pacInfo.versionCode > 185) {
				return true;
			}

		} catch (NameNotFoundException e) {
			return false;
		}
		return false;
	}

}
