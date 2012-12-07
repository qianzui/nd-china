package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class CustomMenuWeibo extends PopupWindow {

	private Button menbtn_weibo_sina, menbtn_weibo_tencent;
	private View mMenuView;
	@SuppressWarnings("unused")
	private String TAG = "MenuWeibo";

	public CustomMenuWeibo(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.menu_layout_weibo, null);
		menbtn_weibo_sina = (Button) mMenuView.findViewById(R.id.menubtn_sina);
		menbtn_weibo_tencent = (Button) mMenuView
				.findViewById(R.id.menubtn_tencent);
		// ���ð�ť����
		menbtn_weibo_tencent.setOnClickListener(itemsOnClick);
		menbtn_weibo_sina.setOnClickListener(itemsOnClick);
		// ��ȡ��Ļ�Ŀ�
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		// ����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth((int) (screenWidth * 0.75));
		// ����SelectPicPopupWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		// ����SelectPicPopupWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.AnimMiddle);
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0x0000000);
		// ����SelectPicPopupWindow��������ı���
		this.setBackgroundDrawable(dw);
		mMenuView.setFocusableInTouchMode(true);
		mMenuView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_MENU)
						&& (event.getAction() == KeyEvent.ACTION_UP)
						&& isShowing()) {
					dismiss();
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * ��Ӧ��submenu����ʱ��ʾ
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 */
	public void showAtLocation2(final View parent, final int gravity,
			final int x, final int y) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				showAtLocation(parent, gravity, x, y);
				super.handleMessage(msg);
			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(CustomMenuMain.DELAYTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	/**
	 * ��ʱ��������Ĵ���
	 */
	public void dismissPop() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dismiss();
				super.handleMessage(msg);
			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(CustomMenuMain.DELAYTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();

	}

}