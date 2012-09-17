package com.hiapk.spearhead;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;

import com.hiapk.exception.CustomException;

public class SpearheadApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static SpearheadApplication instance = null;
	private Resources res = null;

	/**
	 * 初始化全局环境
	 */
	public SpearheadApplication() {
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		CustomException customException = CustomException.getInstance();
		customException.init(getApplicationContext());
	}

	// 单例模式中获取唯一的MyApplication实例
	public static SpearheadApplication getInstance() {
		if (instance == null) {
			instance = new SpearheadApplication();
		}
		return instance;
	}

	public Resources getRes() {
		if (res == null) {
			res = getApplicationContext().getResources();
		}
		return res;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
		// if (sharedWidget.isNotifyOpen() || sharedWidget.isWidGet14Open()) {
		// SetText.resetWidgetAndNotify(context);
		// }
	}
}