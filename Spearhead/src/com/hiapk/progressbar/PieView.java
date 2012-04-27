package com.hiapk.progressbar;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;

/**
 * @author Administrator 画饼图类
 */
public class PieView extends ViewBase {
	int areaX = 1;
	int areaY = 22;
	int areaWidth;
	int areaHight;
	int colors[];
	int shade_colors[];
	int percent[];
	private int thickness = 8;

	/**
	 * @param context
	 *            上下文
	 * @param colors
	 *            最上面颜色数组
	 * @param shade_colors
	 *            阴影颜色数组
	 * @param percent
	 *            百分比 (和必须是360)
	 */
	public PieView(Context context, int[] colors, int[] shade_colors,
			int[] percent) {
		super(context);
		this.colors = colors;
		this.shade_colors = shade_colors;
		this.percent = percent;
	}

	// public PieView(Context context, int[] percent) {
	// super(context);
	// int[] colors = new int[] { Color.YELLOW, Color.RED, Color.BLUE,
	// Color.GREEN };
	// int[] shade_colors = new int[] { Color.rgb(180, 180, 0),
	// Color.rgb(180, 20, 10), Color.rgb(3, 23, 163),
	// Color.rgb(15, 165, 0) };
	// this.colors = colors;
	// this.shade_colors = shade_colors;
	// this.percent = percent;
	// }

	public PieView(Context context, int[] percent) {
		super(context);
		int[] colors = new int[] { Color.RED, Color.GREEN };
		int[] shade_colors = new int[] { Color.rgb(180, 20, 10),
				Color.rgb(15, 165, 0) };
		this.colors = colors;
		this.shade_colors = shade_colors;
		this.percent = percent;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
		areaY = thickness + 2;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		areaWidth = width - thickness;
//		Log.d("main", "kuan" + width);
		areaHight = height - thickness;
//		Log.d("main", "chang" + height);
		int minpie = areaWidth > areaHight ? areaHight : areaWidth;
		minpie = minpie - minpie / 4;
		thickness = minpie / 15;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		for (int i = 0; i <= thickness; i++) {
			int tempAngle = 0;
			for (int j = 0; j < percent.length; j++) {
				paint.setColor(shade_colors[j]);
				canvas.drawArc(new RectF(1,
						((areaHight - minpie) - (float) (i)) - 1, minpie + +1,
						(areaHight - (float) (i)) - 1), tempAngle, percent[j],
						true, paint);
				tempAngle += percent[j];
			}
			if (i == thickness) {
				for (int j = 0; j < percent.length; j++) {
					paint.setColor(colors[j]);
					canvas.drawArc(new RectF(1,
							((areaHight - minpie) - (float) (i)) - 1, minpie
									+ +1, (areaHight - (float) (i)) - 1),
							tempAngle, percent[j], true, paint);
					tempAngle += percent[j];
				}
			}
		}
		paint.setColor(Color.BLUE);
		// paint.setLinearText(linearText)
		// 画直线
		float[] linespoint = new float[4];
		linespoint[0] = minpie / 2 + 1;
		linespoint[1] = (areaHight - minpie / 2 - (float) (thickness)) - 1;
		linespoint[2] = (minpie + (float) ((thickness) + 1)) * 3 / 4;
		linespoint[3] = (((areaHight - minpie) - (float) (thickness)) - 1);
		//第一条线
		canvas.drawLine(linespoint[0], linespoint[1], linespoint[2],
				linespoint[3], paint);
		canvas.drawLine(linespoint[2], linespoint[3], linespoint[2] + minpie
				/ 2*3, linespoint[3], paint);
		canvas.drawLine(linespoint[0]+1, linespoint[1]+1, linespoint[2],
				linespoint[3]+1, paint);
		canvas.drawLine(linespoint[2], linespoint[3]+1, linespoint[2] + minpie
				/ 2*3, linespoint[3]+1, paint);
		// canvas.drawLine(10,10 , 20, 20, paint);
		// 设置显示的数字
		paint.setColor(Color.WHITE);
		paint.setTextSize(minpie / 6);
		int floatnum=percent[0]*100/360;
//		DecimalFormat format = new DecimalFormat("0.#");
//		String value = format.format(floatnum) + "";
		canvas.drawText("已用"+floatnum+"%", linespoint[2], linespoint[3]-3,
				paint);

	}
}
