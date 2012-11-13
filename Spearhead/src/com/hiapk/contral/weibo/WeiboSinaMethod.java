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
	 * SsoHandler ����sdk֧��ssoʱ��Ч��
	 */
	private SsoHandler mSsoHandler;

	private final String CONSUMER_KEY = "2169350509";// �滻Ϊ����??1??7??��appkey������1??7"1646212860";
	private final String REDIRECT_URL = "http://www.baidu.com/";

	private final String TAG = "weiboSinaM";
	private Weibo mWeibo;
	private Context context;

	public WeiboSinaMethod(Context context) {
		this.context = context;
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
	}

	/**
	 * ��ȡSSohandler
	 * 
	 * @return
	 */
	public SsoHandler getmSsoHandler() {
		return mSsoHandler;
	}

	/**
	 * ����SSohandler
	 * 
	 * @return
	 */
	public void setmSsoHandler(SsoHandler mSsoHandler) {
		this.mSsoHandler = mSsoHandler;
	}

	/**
	 * ��ȡWeibo
	 * 
	 * @return
	 */
	public Weibo getmWeibo() {
		return mWeibo;
	}

	/**
	 * ����weibo
	 * 
	 * @param mWeibo
	 */
	public void setmWeibo(Weibo mWeibo) {
		this.mWeibo = mWeibo;
	}

	/**
	 * �����Ƿ��Ѿ���ȡ��֤
	 * 
	 * @param context
	 * @return
	 */
	public boolean hasAccessToken() {
		accessToken = AccessTokenKeeperSina.readAccessToken(context);
		Logs.d(TAG, "getToken=" + accessToken.getToken() + "getExpiresTime="
				+ accessToken.getExpiresTime());
		if (accessToken.isSessionValid()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����Ƿ��Ѱ�װsina΢��//com.sina.weibo
	 * 
	 * @param context
	 * @return
	 */
	public boolean isSinaInstalled() {
		String packageName = "com.sina.weibo";
		try {
			PackageInfo pacInfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			if (pacInfo.versionCode > 100) {
				return true;
			}

		} catch (NameNotFoundException e) {
			return false;
		}
		return false;
	}

	/**
	 * ����Ƿ����ʹ��SSO��ʽ��ȡsina��Ȩ//com.sina.weibo
	 * 
	 * @param context
	 * @return
	 */
	public boolean isUseSSO() {
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
