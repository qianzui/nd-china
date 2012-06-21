package com.hiapk.progressbar;

import java.text.DecimalFormat;

import com.hiapk.provider.UiColors;

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
public class SimplePie extends ViewBase {
	int areaX = 1;
	int areaY = 22;
	int areaWidth;
	int areaHight;
	int colors[];
	int percent[];
	int mobilePersent = 0;

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
	public SimplePie(Context context, int[] colors, int[] percent) {
		super(context);
		this.colors = colors;
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

	public SimplePie(Context context, int[] percent, int mobilePersent) {
		super(context);
		int[] colors = UiColors.chartbarcolor;
		this.colors = colors;
		this.percent = percent;
		this.mobilePersent = mobilePersent;
	}

	public void setThickness(int thickness) {
		areaY = thickness + 2;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		areaWidth = width;
		// Log.d("main", "kuan" + width);
		areaHight = height;
		// Log.d("main", "chang" + height);
		int minpie = areaWidth > areaHight ? areaHight : areaWidth;
		minpie = minpie - minpie / 3;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		int tempAngle = 0;
		for (int j = 0; j < percent.length; j++) {
			paint.setColor(colors[j]);
			canvas.drawArc(new RectF((areaWidth - minpie) / 2,
					(areaHight - minpie) / 3,
					(areaWidth - minpie) / 2 + minpie, (areaHight - minpie) / 3
							+ minpie), tempAngle, percent[j], true, paint);
			tempAngle += percent[j];
		}
		paint.setColor(UiColors.simplePieLine);
		// paint.setLinearText(linearText)
		// 画直线
		float[] linespointmobile = new float[4];
		linespointmobile[0] = (areaWidth - minpie) / 2 + minpie / 4 + minpie
				* ((float) (360 - percent[0]) / 360) / 2;
		linespointmobile[1] = (areaHight - minpie) / 3 + minpie / 2 + minpie
				* ((float) (180 - Math.abs((percent[0] - 180))) / 360) * 2 / 3;
		linespointmobile[2] = areaWidth / 2;
		linespointmobile[3] = (areaHight - minpie) / 3 + minpie + minpie / 16;
		// 第一条线
		canvas.drawLine(linespointmobile[0], linespointmobile[1],
				linespointmobile[2], linespointmobile[3], paint);
		canvas.drawLine(linespointmobile[0] + 1, linespointmobile[1] + 1,
				linespointmobile[2] + 1, linespointmobile[3] + 1, paint);
		// canvas.drawLine(10,10 , 20, 20, paint);
		// 设置显示的数字
		// wifi
		float[] linespointwifi = new float[4];
		linespointwifi[0] = (areaWidth - minpie) / 2 + minpie / 4 - minpie
				* ((float) (percent[1] - 360) / 360) / 2;
		linespointwifi[1] = (areaHight - minpie) / 3 + minpie / 2 - minpie
				* ((float) (180 - Math.abs((percent[1] - 180))) / 360) * 2 / 3;
		linespointwifi[2] = (areaWidth - minpie) / 2 + minpie / 2;
		linespointwifi[3] = (areaHight - minpie) / 3 - minpie / 16;

		// 第er条线
		canvas.drawLine(linespointwifi[0], linespointwifi[1],
				linespointwifi[2], linespointwifi[3], paint);
		canvas.drawLine(linespointwifi[0] + 1, linespointwifi[1] + 1,
				linespointwifi[2] + 1, linespointwifi[3] + 1, paint);
		// int floatnum = (int) ((float) percent[0] * 100 / 360);
		// DecimalFormat format = new DecimalFormat("0");
		// String value = format.format(floatnum) + "";

		// 两个数字
		paint.setColor(Color.rgb(80, 80, 80));
		paint.setTextSize(minpie / 8);
		canvas.drawText(mobilePersent + "%", linespointmobile[2] - minpie / 12,
				linespointmobile[3] + minpie / 12, paint);
		canvas.drawText((100 - mobilePersent) + "%", linespointwifi[2] - minpie
				/ 20, linespointwifi[3] - 3, paint);
		// 最下面的说明
		paint.setColor(colors[0]);
		canvas.drawRect((areaWidth - minpie) / 3, areaHight - minpie / 10
				- minpie / 15, (areaWidth - minpie) / 3 + minpie / 15,
				areaHight - minpie / 10, paint);
		canvas.drawText("移动网络", (areaWidth - minpie) / 3 + minpie / 12 + minpie
				/ 24, areaHight - minpie / 10, paint);
		paint.setColor(colors[1]);
		canvas.drawRect((areaWidth - minpie) / 3 + minpie * 2 / 3, areaHight
				- minpie / 10 - minpie / 15, (areaWidth - minpie) / 3 + minpie
				/ 15 + minpie * 2 / 3, areaHight - minpie / 10, paint);
		canvas.drawText("WIFI网络", (areaWidth - minpie) / 3 + minpie / 12
				+ minpie / 24 + minpie * 2 / 3, areaHight - minpie / 10, paint);
	}
}
