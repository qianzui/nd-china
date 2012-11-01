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

public class CustomMenuSub extends PopupWindow {

	private Button menbtn_shareto, menbtn_updateinfo, menbtn_about;
	private View mMenuView;
	@SuppressWarnings("unused")
	private String TAG = "MenuSub";

	public CustomMenuSub(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.menu_layout_sub, null);
		menbtn_shareto = (Button) mMenuView.findViewById(R.id.menubtn_share);
		menbtn_updateinfo = (Button) mMenuView
				.findViewById(R.id.menubtn_updatemess);
		menbtn_about = (Button) mMenuView.findViewById(R.id.menubtn_about);
		// ���ð�ť����
		menbtn_updateinfo.setOnClickListener(itemsOnClick);
		menbtn_about.setOnClickListener(itemsOnClick);
		menbtn_shareto.setOnClickListener(itemsOnClick);
		// ��ȡ��Ļ�Ŀ�
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		// ����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth((int) (screenWidth * 0.65));
		// ����SelectPicPopupWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		// ����SelectPicPopupWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.AnimBottom);
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
