package com.hiapk.widget;

import com.hiapk.dataexe.UnitHandler;
import com.hiapk.spearhead.R;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FloatService extends Service {
	WindowManager wm = null;
	WindowManager.LayoutParams wmParams = null;
	View view;
	private float mTouchStartX;
	private float mTouchStartY;
	private float x;
	private float y;
	int state;
	// TextView tx1;
	TextView tx;
	ImageView iv;
	private float StartX;
	private float StartY;
	int delaytime = 3000;
	Context context = this;
	SharedPrefrenceDataWidget sharedata;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		showLog("oncreate");
		super.onCreate();
		sharedata = new SharedPrefrenceDataWidget(context);
		view = LayoutInflater.from(this)
				.inflate(R.layout.floating_widget, null);
		tx = (TextView) view.findViewById(R.id.textUp);
		// tx1 = (TextView) view.findViewById(R.id.textDown);
		tx.setText(" "
				+ UnitHandler.unitHandlerAccurate(TrafficInfomation
						.getspeed(this)) + "/s ");
		// tx1.setText("" + TrafficInfomation.getspeed(this) + "KB");
		iv = (ImageView) view.findViewById(R.id.image);
		iv.setImageResource(R.drawable.cross);
		iv.setVisibility(View.GONE);
		createView();
		handler.postDelayed(task, delaytime);
	}

	private void createView() {
		// TODO Auto-generated method stub
		// 获取WindowManager
		wm = (WindowManager) getApplicationContext().getSystemService("window");
		// 设置LayoutParams(全局变量）相关参数
		wmParams = new WindowManager.LayoutParams();
		// TYPE_PHONE
		wmParams.type = 2002;
		// FLAG_NOT_FOCUSABLE
		wmParams.flags |= 8;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		int x_p = sharedata.getIntX();
		int y_p = sharedata.getIntY();
		wmParams.x = x_p;
		wmParams.y = y_p;
		// 设置悬浮窗口长宽数据
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// RGBA_8888
		wmParams.format = 1;
		wm.addView(view, wmParams);

		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// 获取相对屏幕的坐标，即以屏幕左上角为原点
				x = event.getRawX();
				y = event.getRawY(); // 25是系统状态栏的高度
				showLog("currX" + x + "====currY" + y); // 调试信息
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					state = MotionEvent.ACTION_DOWN;
					StartX = x;
					StartY = y;
					// 获取相对View的坐标，即以此View左上角为原点
					mTouchStartX = event.getX();
					mTouchStartY = event.getY();
					showLog("startX" + mTouchStartX + "====startY"
							+ mTouchStartY);// 调试信息
					break;
				case MotionEvent.ACTION_MOVE:
					state = MotionEvent.ACTION_MOVE;
					updateViewPosition();
					break;

				case MotionEvent.ACTION_UP:
					state = MotionEvent.ACTION_UP;
					updateViewPosition();
					showImg();
					mTouchStartX = mTouchStartY = 0;
					break;
				}
				return true;
			}
		});

		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent serviceStop = new Intent();
				SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
						FloatService.this);
				sharedDatawidget.setFloatOpen(false);
				sharedDatawidget.setIntX(SetText.FloatIntX);
				sharedDatawidget.setIntY(SetText.FloatIntY);
				// boolean isFloatOpen = sharedData.isFloatOpen();
				serviceStop.setClass(FloatService.this, FloatService.class);
				stopService(serviceStop);
				// showLog("isFloatOpen"+isFloatOpen);
			}
		});
	}

	public void showImg() {
		if (Math.abs(x - StartX) < 10 && Math.abs(y - StartY) < 10
				&& !iv.isShown()) {
			iv.setVisibility(View.VISIBLE);
		} else if (iv.isShown()) {
			iv.setVisibility(View.GONE);
		}
	}

	private Handler handler = new Handler();
	private Runnable task = new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			dataRefresh();
			handler.postDelayed(this, delaytime);
			wm.updateViewLayout(view, wmParams);
		}
	};

	public void dataRefresh() {
		if (SQLStatic.TableWiFiOrG23 != "") {
			tx.setText(" "
					+ UnitHandler.unitHandlerAccurate(TrafficInfomation
							.getspeed(this)) + "/s ");
		} else {
			tx.setText(" " + "0 KB" + "/s ");
		}
		// tx1.setText("" + TrafficInfomation.getspeed(this) + "KB");
	}

	private void updateViewPosition() {
		// 更新浮动窗口位置参数
		SetText.FloatIntX = (int) (x - mTouchStartX);
		SetText.FloatIntY = (int) (y - mTouchStartY - 46);
		wmParams.x = SetText.FloatIntX;
		wmParams.y = SetText.FloatIntY;
		wm.updateViewLayout(view, wmParams);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		showLog("onStart");
		setForeground(true);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(task);
		showLog("onDestroy");
		wm.removeView(view);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("FloatService", string);
	}
}
