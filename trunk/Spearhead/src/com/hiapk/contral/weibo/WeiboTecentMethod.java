package com.hiapk.contral.weibo;

import com.hiapk.ui.scene.weibo.tencent.OAuthV1;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class WeiboTecentMethod {

//	private final String TAG = "weiboTecentM";
	private Context context;
	// !!!请根据您的实际情况修改!!! 认证成功后浏览器会被重定向到这个url中 本例子中不需改动
//	private String oauthCallback = "null";
	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP KEY
	private String oauthConsumeKey = "801268730";

	public String getOauthConsumerSecret() {
		return oauthConsumerSecret;
	}

	public String getOauthConsumeKey() {
		return oauthConsumeKey;
	}

	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP SECRET
	private String oauthConsumerSecret = "69ad6e15e837751cfa2ac16e71d3ea3b";

	public WeiboTecentMethod(Context context) {
		this.context = context;
	}

	/**
	 * 检测是否已安装sina微博//com.tencent.WBlog
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
	 * 检测是否可以使用SSO方式获取sina授权//com.sina.weibo
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
