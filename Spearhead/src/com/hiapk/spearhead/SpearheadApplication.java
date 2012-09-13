package com.hiapk.spearhead;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;

import com.hiapk.exception.CustomException;

public class SpearheadApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static SpearheadApplication instance;
	private static Resources res = null;

	/**
	 * ��ʼ��ȫ�ֻ���
	 */
	public SpearheadApplication() {

	}

	@Override
	public void onCreate() {

		super.onCreate();
		CustomException customException = CustomException.getInstance();
		instance = this;
		customException.init(getApplicationContext());
	}

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��
	public static SpearheadApplication getInstance() {
		return instance;
	}

	public Resources getRes() {
		if (res == null) {
			res = this.getResources();
		}
		return res;
	}

	// ���Activity��������
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// ��������Activity��finish
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