package com.hiapk.contral.weibo;

import com.hiapk.ui.scene.weibo.tencent.OAuthV1;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class WeiboTecentMethod {

//	private final String TAG = "weiboTecentM";
	private Context context;
	// !!!���������ʵ������޸�!!! ��֤�ɹ���������ᱻ�ض������url�� �������в���Ķ�
//	private String oauthCallback = "null";
	// !!!���������ʵ������޸�!!! ��Ϊ��Ϊ�Լ���Ӧ�����뵽��APP KEY
	private String oauthConsumeKey = "801268730";

	public String getOauthConsumerSecret() {
		return oauthConsumerSecret;
	}

	public String getOauthConsumeKey() {
		return oauthConsumeKey;
	}

	// !!!���������ʵ������޸�!!! ��Ϊ��Ϊ�Լ���Ӧ�����뵽��APP SECRET
	private String oauthConsumerSecret = "69ad6e15e837751cfa2ac16e71d3ea3b";

	public WeiboTecentMethod(Context context) {
		this.context = context;
	}

	/**
	 * ����Ƿ��Ѱ�װsina΢��//com.tencent.WBlog
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTecentInstalled() {
		String packageName = "com.tencent.WBlog";
		try {
			PackageInfo pacInfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			if (pacInfo.versionCode > 20) {
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
		String packageName = "com.tencent.WBlog";
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

	public boolean hasAccessToken() {
		OAuthV1 oAuth = AccessTokenKeeperTencent.readAccessToken(context);
		if (oAuth.getOauthToken() != "" && oAuth.getOauthTokenSecret() != "") {
			return true;
		}
		return false;
	}

}
