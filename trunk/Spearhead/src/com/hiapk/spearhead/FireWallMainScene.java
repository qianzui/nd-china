/*
 * Copyright (C) 2011 Patrik 乲erfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiapk.spearhead;

import com.hiapk.spearhead.R;
import android.graphics.Bitmap;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FireWallMainScene extends TabActivity {
	private static RadioGroup group;
	public static TabHost tabHost;
	public static final String TAB_FIREWALL = "tabfire";
	public static final String TAB_NOTIFYADB = "tabnotify";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_sub_tabs);
		SpearheadApplication.getInstance().addActivity(this);
		initScene();
		switchScene();

	}

	/**
	 * 初始化
	 */
	private void initScene() {
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.addTab(tabHost
				.newTabSpec(TAB_FIREWALL)
				.setIndicator(TAB_FIREWALL)
				.setContent(
						new Intent(FireWallMainScene.this,
								FireWallActivity.class)));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_NOTIFYADB)
				.setIndicator(TAB_NOTIFYADB)
				.setContent(
						new Intent(FireWallMainScene.this,
								FireWallPushNotification.class)));
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_FIREWALL);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_NOTIFYADB);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 初始显示第几个页面
	 */
	private void switchScene() {
		// 选择界面
		group.clearCheck();
		group.check(R.id.radio_button0);
		tabHost.setCurrentTabByTag(TAB_FIREWALL);
	}

	public static void switScene(int scenenumber) {
		switch (scenenumber) {
		case 0:
			group.clearCheck();
			group.check(R.id.radio_button0);
			tabHost.setCurrentTabByTag(TAB_FIREWALL);
			break;
		default:
			group.clearCheck();
			group.check(R.id.radio_button1);
			tabHost.setCurrentTabByTag(TAB_FIREWALL);
			tabHost.setCurrentTabByTag(TAB_NOTIFYADB);
			break;
		}

	}
	// @Override
	// public boolean onTouchEvent(MotionEvent ev) {
	//
	// // if (mVelocityTracker == null) {
	// // mVelocityTracker = VelocityTracker.obtain();
	// // }
	// // mVelocityTracker.addMovement(ev);
	// final int action = ev.getAction();
	// final float x = ev.getX();
	// switch (action) {
	// case MotionEvent.ACTION_DOWN:
	// Log.d(TAG, "touch_actionDOWN");
	// // Remember where the motion event started
	// mLastMotionX = x;
	// return true;
	// // break;
	// case MotionEvent.ACTION_MOVE:
	// Log.d(TAG, "touch_actionMOVE");
	// final int deltaX = (int) (mLastMotionX - x);
	//
	// boolean xMoved = Math.abs(deltaX) > mTouchSlop;
	//
	// if (xMoved) {
	// // Scroll if the user moved far enough along the X axis
	// Log.d(TAG, "isviewFlow=" + isviewFlow);
	// if (isviewFlow == false) {
	// isviewFlow = true;
	// viewFlow = new ViewFlow(context, 5);
	// if (bitmaps == null) {
	// bitmaps = new LinkedList<Bitmap>();
	// bitmaps.addLast(null);
	// bitmaps.addLast(firewall.getDrawingCache());
	// if (notifywall == null) {
	// bitmaps.addLast(null);
	// } else {
	// bitmaps.addLast(notifywall.getDrawingCache());
	// }
	//
	// bitmaps.addLast(null);
	// }
	// AndroidVersionAdapter viewpager_Adapter = new AndroidVersionAdapter(
	// bitmaps, context);
	// viewFlow.setAdapter(viewpager_Adapter, pos);
	// mLayout.removeAllViews();
	// LayoutParams params = new LayoutParams(
	// ViewGroup.LayoutParams.FILL_PARENT,
	// ViewGroup.LayoutParams.FILL_PARENT);
	// mLayout.addView(viewFlow, params);
	// // mLayout.addView(viewFlow);
	//
	// }
	// }
	// break;
	//
	// case MotionEvent.ACTION_UP:
	// Log.d(TAG, "touch_actionUP");
	// if (isviewFlow == true) {
	// pos = viewFlow.getSelectedItemPosition();
	// if (pos == 1) {
	// mLayout.removeAllViews();
	// if (firewall == null) {
	// firewall = mInflater.inflate(R.layout.loading_layout,
	// null);
	// }
	// mLayout.addView(firewall);
	// } else {
	// mLayout.removeAllViews();
	// if (notifywall == null) {
	// notifywall = mInflater
	// .inflate(R.layout.load_fail, null);
	// }
	// mLayout.addView(notifywall);
	// }
	// bitmaps = null;
	// // if (mVelocityTracker != null) {
	// // mVelocityTracker.recycle();
	// // mVelocityTracker = null;
	// // }
	// isviewFlow = false;
	// }
	// break;
	// }
	// return super.onTouchEvent(ev);
	// }
	public Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}