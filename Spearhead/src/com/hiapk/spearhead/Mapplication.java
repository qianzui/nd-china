package com.hiapk.spearhead;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * ����һ�ֱȽ����е�Android���������˳�������ʹ�õ���ģʽ����һ��Activity�������
 * �ö�������һ��Activity����������ʵ���Լ�����ʹ��LinkedList�ȣ�
 * ר�Ÿ���洢�¿�����ÿһ��Activity������������⡢���ڲ������ǳ�����
 * 
 * 
 * ��ÿһ��Activity�е�onCreate��������Ӹ�Activity��MyApplication����ʵ��������
 * Mapplication.getInstance().addActivity(this); ����Ҫ��������Activity��ʱ�����exit����
 * Mapplication.getInstance().exit();
 * 
 * ���˷�ӳ�����������������ܻᵼ�¸���ı��ȹرղ��ؽ���ʹ��ǰ�ڷ����Activity�޷������رա�
 * 
 * */
public class Mapplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static Mapplication instance;
	Context context = this;

	private Mapplication() {
	}

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��
	public static Mapplication getInstance() {
		if (null == instance) {
			instance = new Mapplication();
		}
		return instance;
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