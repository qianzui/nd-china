package com.hiapk.customspinner;

import com.hiapk.spearhead.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

	public static void initToast(Context context, String message) {
		LayoutInflater factory = LayoutInflater.from(context);
		View toastRoot = factory.inflate(R.layout.custom_toast, null);
		// View toastRoot = context.getLayoutInflater().inflate(
		// R.layout.custom_toast, null);
		TextView tv_message = (TextView) toastRoot.findViewById(R.id.message);
		tv_message.setText(message);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.BOTTOM, 0, 70);
		toastStart.setDuration(Toast.LENGTH_SHORT);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
}
