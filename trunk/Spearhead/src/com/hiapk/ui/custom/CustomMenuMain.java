package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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

	public CustomMenuMain(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.menu_layout_main, null);
		btn_setting = (Button) mMenuView.findViewById(R.id.menubtn_setting);
		btn_faq = (Button) mMenuView.findViewById(R.id.menubtn_faq);
		btn_more = (Button) mMenuView.findViewById(R.id.menubtn_more);
		btn_cancel = (Button) mMenuView.findViewById(R.id.menubtn_exit);
		// 取消按钮
		btn_cancel.setOnClickListener(itemsOnClick);
		// 设置按钮监听
		btn_faq.setOnClickListener(itemsOnClick);
		btn_more.setOnClickListener(itemsOnClick);
		btn_setting.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);

		mMenuView.setFocusableInTouchMode(true);
		mMenuView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_MENU)
						&& (event.getAction() == KeyEvent.ACTION_UP)) {
					if (isMenuMainShow) {
						dismiss();// 这里写明模拟menu的PopupWindow退出就行
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

	@Override
	public void dismiss() {
		isMenuMainShow = false;
		super.dismiss();
	}
}
