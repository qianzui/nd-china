package com.hiapk.alertdialog;

import com.hiapk.spearhead.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * Builder
	 * 
	 * @author wolf_jr
	 * 
	 */
	public static class Builder {

		private Context context;

		private String title;

		private String message;

		private String otherBtnStr;

		private String positiveBtnStr;

		private String negativeBtnStr;

		private View contentView;

		private DialogInterface.OnClickListener otherListener,
				positiveListener, negativeListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * 设置内容消息
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * 设置内容消息
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = context.getString(message);
			return this;
		}

		/**
		 * 设置标题
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 设置标题
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = context.getString(title);
			return this;
		}

		/**
		 * 设置视图
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * 设置其他按钮
		 * 
		 * @param otherButtonText
		 * @param listener
		 * @return
		 */
		public Builder setOtherButton(int otherButtonText,
				DialogInterface.OnClickListener listener) {
			this.otherBtnStr = context.getString(otherButtonText);
			this.otherListener = listener;
			return this;
		}

		/**
		 * 设置其他按钮
		 * 
		 * @param otherButtonText
		 * @param listener
		 * @return
		 */
		public Builder setOtherButton(String otherButtonText,
				DialogInterface.OnClickListener listener) {
			this.otherBtnStr = otherButtonText;
			this.otherListener = listener;
			return this;
		}

		/**
		 * 设置确定按钮
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveBtnStr = context.getString(positiveButtonText);
			this.positiveListener = listener;
			return this;
		}

		/**
		 * 设置确定按钮
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveBtnStr = positiveButtonText;
			this.positiveListener = listener;
			return this;
		}

		/**
		 * 设置否定按钮
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeBtnStr = context.getString(negativeButtonText);
			this.negativeListener = listener;
			return this;
		}

		/**
		 * 设置确定按钮
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeBtnStr = negativeButtonText;
			this.negativeListener = listener;
			return this;
		}

		/**
		 * 创建自定义对话框
		 * 
		 * @return
		 */
		public CustomDialog create() {
			LayoutInflater inflater = LayoutInflater.from(context);
			final CustomDialog dialog = new CustomDialog(context,
					R.style.CustomDialog);
			// 获取定义好的布局文件
			View layout = inflater.inflate(
					R.layout.custom_alert_dialog_on_main, null);
			// 取得窗口属性
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			DisplayMetrics dm = new DisplayMetrics();
			// 取得窗口属性
			Window window = dialog.getWindow();
			int heigh = window.getWindowManager().getDefaultDisplay()
					.getHeight();
			int width = window.getWindowManager().getDefaultDisplay()
					.getWidth();
			window.setLayout((int) (width * 0.8), (int) (heigh * 0.3));
			// 设置标题
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// 设置其他按钮
			if (otherBtnStr != null) {
				((Button) layout.findViewById(R.id.otherButton))
						.setText(otherBtnStr);
				if (otherListener != null) {
					((Button) layout.findViewById(R.id.otherButton))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									otherListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				layout.findViewById(R.id.otherButton).setVisibility(View.GONE);
			}
			// 设置确定按钮
			if (positiveBtnStr != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveBtnStr);
				if (positiveListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									positiveListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// 设置否定按钮
			if (negativeBtnStr != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeBtnStr);
				if (negativeListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									negativeListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// 设置内容
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}

}
