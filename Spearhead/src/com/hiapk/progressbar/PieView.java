package com.hiapk.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

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
		int[] colors = new int[] {  Color.GREEN ,Color.RED};
		int[] shade_colors = new int[] { 
				Color.rgb(15, 165, 0),Color.rgb(180, 20, 10) };
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
		areaWidth = width - 2;
		areaHight = height - 2;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		for (int i = 0; i <= thickness; i++) {
			int tempAngle = 0;
			for (int j = 0; j < percent.length; j++) {
				paint.setColor(shade_colors[j]);
				canvas.drawArc(new RectF(areaX + i + 1, areaY - i + 1, areaX
						+ areaWidth, areaHight - i - 1), tempAngle, percent[j],
						true, paint);
				tempAngle += percent[j];
			}
			if (i == thickness) {
				for (int j = 0; j < percent.length; j++) {
					paint.setColor(colors[j]);
					canvas.drawArc(new RectF(areaX + i + 1, areaY - i + 1,
							areaX + areaWidth, areaHight - i - 1), tempAngle,
							percent[j], true, paint);
					tempAngle += percent[j];
				}
			}
		}
	}
}
