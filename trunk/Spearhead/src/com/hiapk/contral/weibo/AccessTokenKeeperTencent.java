package com.hiapk.contral.weibo;

import com.hiapk.ui.scene.weibo.tencent.OAuthV1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 该类用于保存Oauth2AccessToken到sharepreference，并提供读取功能
 * 
 * @author xiaowei6@staff.sina.com.cn
 * 
 */
public class AccessTokenKeeperTencent {
	private static final String PREFERENCES_NAME = "com_weibo_sdk_android_tencent";

	/**
	 * 保存accesstoken到SharedPreferences
	 * 
	 * @param context
	 *            Activity 上下文环墄1�7
	 * @param token
	 *            Oauth2AccessToken
	 */
	public static void keepAccessToken(Context context, OAuthV1 oAuth) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("oauth_token", oAuth.getOauthToken());
		editor.putString("oauth_token_secreate", oAuth.getOauthTokenSecret());
		editor.commit();
	}

	/**
	 * 清空sharepreference
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 从SharedPreferences读取accessstoken
	 * 
	 * @param context
	 * @return Oauth2AccessToken
	 */
	public static OAuthV1 readAccessToken(Context context) {
		OAuthV1 oAuth = new OAuthV1();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		WeiboTecentMethod weiboTm=new WeiboTecentMethod(context);
		oAuth.setOauthCallback("null");
		oAuth.setOauthConsumerKey(weiboTm.getOauthConsumeKey());
		oAuth.setOauthConsumerSecret(weiboTm.getOauthConsumerSecret());
		oAuth.setOauthToken(pref.getString("oauth_token", ""));
		oAuth.setOauthTokenSecret(pref.getString("oauth_token_secreate", ""));
		return oAuth;
	}
}
