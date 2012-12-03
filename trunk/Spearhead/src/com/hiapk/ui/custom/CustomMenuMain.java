package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class CustomMenuMain extends PopupWindow {

	private Button btn_setting, btn_faq, btn_more, btn_cancel;
	private View mMenuView;
	@SuppressWarnings("unused")
	private String TAG = "MenuMain";
	public static boolean isMenuMainShow = false;
	/**
	 * menu�Ի���Ĳ�����ʱ
	 */
	public static int DELAYTIME = 200;

	public CustomMenuMain(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.menu_layout_main, null);
		btn_setting = (Button) mMenuView.findViewById(R.id.menubtn_setting);
		btn_faq = (Button) mMenuView.findViewById(R.id.menubtn_faq);
		btn_more = (Button) mMenuView.findViewById(R.id.menubtn_more);
		btn_cancel = (Button) mMenuView.findViewById(R.id.menubtn_exit);
		// ȡ����ť
		btn_cancel.setOnClickListener(itemsOnClick);
		// ���ð�ť����
		btn_faq.setOnClickListener(itemsOnClick);
		btn_more.setOnClickListener(itemsOnClick);
		btn_setting.setOnClickListener(itemsOnClick);
		// ����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth(LayoutParams.FILL_PARENT);
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
						&& (event.getAction() == KeyEvent.ACTION_UP)) {
					if (isMenuMainShow) {
						dismiss();// ����д��ģ��menu��PopupWindow�˳�����
					}
					if (isShowing()) {
						isMenuMainShow = true;
					}
					return true;
				}
				return false;
			}
		});
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
					Thread.sleep(DELAYTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();

	}

	@Override
	public void dismiss() {
		isMenuMainShow = false;
		super.dismiss();
	}
}