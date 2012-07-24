package com.hiapk.spearhead;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 还有一种比较流行的Android经典完美退出方法，使用单例模式创建一个Activity管理对象，
 * 该对象中有一个Activity容器（具体实现自己处理，使用LinkedList等）
 * 专门负责存储新开启的每一个Activity，并且容易理解、易于操作，非常不错！
 * 
 * 
 * 在每一个Activity中的onCreate方法里添加该Activity到MyApplication对象实例容器中
 * Mapplication.getInstance().addActivity(this); 在需要结束所有Activity的时候调用exit方法
 * Mapplication.getInstance().exit();
 * 
 * 有人反映，如果程序崩溃，可能会导致该类的被迫关闭并重建，使得前期放入的Activity无法正常关闭。
 * 
 * */
public class Mapplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static Mapplication instance;
	Context context = this;

	private Mapplication() {
	}

	// 单例模式中获取唯一的MyApplication实例
	public static Mapplication getInstance() {
		if (null == instance) {
			instance = new Mapplication();
		}
		return instance;
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