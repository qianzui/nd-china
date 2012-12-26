package com.hiapk.ui.chart;

import com.hiapk.spearhead.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class TriangleCanvas extends Canvas {
	// private static final String TAG = TriangleCanvas.class.getName();

	Context cnt;

	private int dispWidth;
	private int dispHeight;
	public static final int Triangle_UP = 0;
	public static final int Triangle_LEFT = 1;
	public static final int Triangle_RIGHT = 2;
	public static final int Triangle_DOWN = 3;

	public TriangleCanvas(Context c, Bitmap bmp, int arrow, int flag) {
		super(bmp);
		cnt = c;
		dispWidth = getWidth();
		dispHeight = getHeight();

		// Log.i(TAG, "W = " + dispWidth + " H = " + dispHeight);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.argb(0, 0, 0, 0));

		this.save();
		// this.drawColor(cnt.getResources().getColor(R.color.orange));
		this.drawRoundRect(new RectF(0, 0, dispWidth, dispHeight), 2, 2, paint);
		paint.setStyle(Paint.Style.STROKE);
		if (arrow == Triangle_UP) {
			// paint.setColor(Color.GREEN);
			this.rotate(-90, dispWidth / 2, dispHeight / 2);
		} else if (arrow == Triangle_DOWN) {
			// paint.setColor(Color.BLUE);
			this.rotate(90, dispWidth / 2, dispHeight / 2);
		} else if (arrow == Triangle_LEFT) {
			// paint.setColor(Color.RED);
			this.rotate(180, dispWidth / 2, dispHeight / 2);
		} else if (arrow == Triangle_RIGHT) {
			// paint.setColor(Color.MAGENTA);
		} else {
			;
		}
		switch (flag) {
		case 0:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_blue_gradient_btn_center));
			break;
		case 1:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_orange_gradient_btn_center));
			break;
		case 2:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_purple_gradient_btn_center));
			break;
		case 3:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_green_gradient_btn_center));
			break;
		case 4:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_pink_gradient_btn_center));
			break;
		case 5:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_grey_gradient_btn_center));
			break;
		default:
			paint.setColor(cnt.getResources().getColor(
					R.color.nova_space_blue_gradient_btn_center));
			break;
		}
		Path path = drawTriangle((float) dispWidth);
		paint.setStyle(Paint.Style.FILL);
		// paint.setColor(Color.GRAY);
		this.drawPath(path, paint);
		this.restore();

	}

	Path drawTriangle(float r) {
		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(0, r);
		path.lineTo(r, r);
		path.lineTo(0, 0);

		return path;
	}

}