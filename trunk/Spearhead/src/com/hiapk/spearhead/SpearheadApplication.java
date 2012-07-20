package com.hiapk.spearhead;

import android.app.Application;

import com.hiapk.exception.CustomException;

public class SpearheadApplication extends Application {
	@Override
	public void onCreate() {

		super.onCreate();

		CustomException customException = CustomException.getInstance();

		customException.init(getApplicationContext());
	}
}