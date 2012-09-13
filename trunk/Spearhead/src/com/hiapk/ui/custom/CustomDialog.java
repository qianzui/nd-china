package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
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

		private int tv_size = 0;

		private double windowHeight = 0;

		private double windowWidth = 0;

		private boolean showButton = true;

		private DialogInterface.OnClickListener otherListener,
				positiveListener, negativeListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder(Context context, int theme) {
			this.context = context;
		}

		/**
		 * �Ƿ���ʾ��ť
		 * 
		 * @param showButton
		 * @return
		 */
		public Builder setShowButton(Boolean showButton) {
			this.showButton = showButton;
			return this;
		}

		/**
		 * ����������Ϣ
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * ���ô���߶ȣ�Ĭ��wrap_content
		 * 
		 * @param message
		 * @return
		 */
		public Builder setwindowHeight(double windowHeight) {
			this.windowHeight = windowHeight;
			return this;
		}

		/**
		 * ���ô�������������Ĭ��0.9
		 * 
		 * @param message
		 * @return
		 */
		public Builder setwindowWidth(double windowWidth) {
			this.windowWidth = windowWidth;
			return this;
		}

		/**
		 * ����������Ϣ
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = context.getString(message);
			return this;
		}

		/**
		 * ������Ϣ�����С
		 * 
		 * @param message
		 * @return
		 */
		public Builder setTv_size(int tv_size) {
			this.tv_size = tv_size;
			return this;
		}

		/**
		 * ���ñ���
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * ���ñ���
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = context.getString(title);
			return this;
		}

		/**
		 * ������ͼ
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * ����������ť
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
		 * ����������ť
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
		 * ����ȷ����ť
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
		 * ����ȷ����ť
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
		 * ���÷񶨰�ť
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
		 * ����ȷ����ť
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
		 * �����Զ���Ի���
		 * 
		 * @return
		 */
		public CustomDialog create() {
			LayoutInflater inflater = LayoutInflater.from(context);
			final CustomDialog dialog = new CustomDialog(context,
					R.style.CustomDialog);
			// ��ȡ����õĲ����ļ�
			View layout = inflater.inflate(R.layout.custom_dialog_normal, null);
			// ȡ�ô�������
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			// ��������
			setWindowParam(dialog);
			// ���ñ���
			TextView titleText = (TextView) layout
					.findViewById(R.id.title_text);
			titleText.setText(title);
			LinearLayout titlebar = (LinearLayout) layout
					.findViewById(R.id.title_bar);
			// ����Ƥ��
			titlebar.setBackgroundResource(SkinCustomDialog
					.titleBarBackground());
			// ����������ť
			if (otherBtnStr != null) {
				((Button) layout.findViewById(R.id.otherButton))
						.setText(otherBtnStr);
				// ����Ƥ��
				((Button) layout.findViewById(R.id.otherButton))
						.setBackgroundResource(SkinCustomDialog
								.buttonBackground());
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
			// ����ȷ����ť
			if (positiveBtnStr != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveBtnStr);
				// ����Ƥ��
				((Button) layout.findViewById(R.id.positiveButton))
						.setBackgroundResource(SkinCustomDialog
								.buttonBackground());
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
			// ���÷񶨰�ť
			if (negativeBtnStr != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeBtnStr);
				// ����Ƥ��
				((Button) layout.findViewById(R.id.negativeButton))
						.setBackgroundResource(SkinCustomDialog
								.buttonBackground());
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
			// ��������
			if (message != null) {
				TextView tv_mess = (TextView) layout.findViewById(R.id.message);
				if (tv_size != 0) {
					tv_mess.setTextSize(tv_size);
				}
				tv_mess.setText(message);
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
			}
			// �Ƿ���ʾ��ť��
			if (!showButton) {
				layout.findViewById(R.id.custom_Buttons).setVisibility(
						View.GONE);
				layout.findViewById(R.id.custom_imagview).setVisibility(
						View.GONE);
				layout.findViewById(R.id.content).setPadding(5, 42, 10, 10);
			}
			dialog.setContentView(layout);
			return dialog;
		}

		/**
		 * ���ô��ڲ���
		 */
		private void setWindowParam(CustomDialog dialog) {
			// ȡ�ô�������
			Window window = dialog.getWindow();
			int heigh = window.getWindowManager().getDefaultDisplay()
					.getHeight();
			int width = window.getWindowManager().getDefaultDisplay()
					.getWidth();
			// �����С����
			if (windowHeight == 0) {
				windowHeight = LayoutParams.WRAP_CONTENT;
			} else {
				windowHeight = windowHeight * heigh;
			}
			if (windowWidth == 0) {
				windowWidth = 0.9 * width;
			} else {
				windowWidth = windowWidth * width;
			}
			window.setLayout((int) windowWidth, (int) windowHeight);
		}
	}

}
