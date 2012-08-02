/*
 * Copyright (C) 2011 Patrik Åkerfeldt
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
package com.hiapk.viewflow;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.zip.Inflater;

import com.hiapk.firewall.MyListView;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.FireWallPushNotification;
import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.uidtraff.UidMonthTraff;
import com.hiapk.viewflow.ViewFlow.AdapterDataSetObserver;
import com.hiapk.viewflow.ViewFlow.LazyInit;
import com.hiapk.viewflow.ViewFlow.ViewLazyInitializeListener;
import com.hiapk.viewflow.ViewFlow.ViewSwitchListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.app.ActivityGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class ViewFlowMainScene extends ActivityGroup {

	private ViewFlow viewFlow;
	private ActivityGroup group;
	private Context context;
	private boolean isviewFlow = false;
	private LinkedList<Bitmap> bitmaps;
	private LinkedList<View> views = new LinkedList<View>();
	private View firewall = null;
	private View notifywall = null;
	private int pos = 1;
	LinearLayout mLayout;
	LayoutInflater mInflater;
	// from viewflow

	private String TAG = "viewflowMainScene";
	private boolean isStaticView = false;
	private static final int SNAP_VELOCITY = 1000;
	private static final int INVALID_SCREEN = -1;
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;

	private LinkedList<View> mLoadedViews;
	private LinkedList<View> mRecycledViews;
	private int mCurrentBufferIndex;
	private int mCurrentAdapterIndex;
	private int mSideBuffer = 2;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchState = TOUCH_STATE_REST;
	private float mLastMotionX;
	private int mTouchSlop;
	private int mMaximumVelocity;
	private int mCurrentScreen;
	private int mNextScreen = INVALID_SCREEN;
	private boolean mFirstLayout = true;
	private ViewSwitchListener mViewSwitchListener;
	private ViewLazyInitializeListener mViewInitializeListener;
	private EnumSet<LazyInit> mLazyInit = EnumSet.allOf(LazyInit.class);
	private Adapter mAdapter;
	private int mLastScrollDirection;
	private AdapterDataSetObserver mDataSetObserver;
	private FlowIndicator mIndicator;
	private int mLastOrientation = -1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainviewflow);
		// loading view
		context = this;
		mLayout = (LinearLayout) findViewById(R.id.linearlayout_viewflow);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		 firewall = getLocalActivityManager().startActivity("fire",
		 new Intent(ViewFlowMainScene.this, FireWallActivity.class))
		 .getDecorView();
		 notifywall = getLocalActivityManager().startActivity(
		 "notify",
		 new Intent(ViewFlowMainScene.this,
		 FireWallPushNotification.class)).getDecorView();
		 viewFlow = new ViewFlow(context, 5);
		 views.addLast(firewall);
		 views.addLast(notifywall);
		 AndroidVersionAdapter viewpager_Adapter = new AndroidVersionAdapter(
		 views, context);
		 viewFlow.setAdapter(viewpager_Adapter, pos);
		 mLayout.removeAllViews();
		 LayoutParams params = new LayoutParams(
		 ViewGroup.LayoutParams.FILL_PARENT,
		 ViewGroup.LayoutParams.FILL_PARENT);
		 mLayout.addView(viewFlow, params);

//		firewall = mInflater.inflate(R.layout.main2_flow_list, null);
//		TextView tv_title = (TextView) firewall
//				.findViewById(R.id.flow_list_title);
//		tv_title.setText("¡˜¡ø≈≈––");
//		LinearLayout flowlayout = (LinearLayout) firewall
//				.findViewById(R.id.flow_list_content);
//		flowlayout.removeAllViews();
//		MyListView myListView = new MyListView(context);
//		// TODO
//		flowlayout.addView(myListView);
//		notifywall = mInflater.inflate(R.layout.loading_layout, null);
//		viewFlow = new ViewFlow(context, 5);
//		views.addLast(firewall);
//		views.addLast(notifywall);
//		AndroidVersionAdapter viewpager_Adapter = new AndroidVersionAdapter(
//				views, context);
//		viewFlow.setAdapter(viewpager_Adapter, pos);
//		mLayout.removeAllViews();
//		LayoutParams params = new LayoutParams(
//				ViewGroup.LayoutParams.FILL_PARENT,
//				ViewGroup.LayoutParams.FILL_PARENT);
//		mLayout.addView(viewFlow, params);
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

}