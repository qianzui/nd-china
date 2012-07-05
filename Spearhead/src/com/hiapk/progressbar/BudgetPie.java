package com.hiapk.progressbar;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class BudgetPie extends ViewBase {
	int windowswidesize = 300;

	public BudgetPie(Context context, int windowswidesize) {
		super(context);
		this.windowswidesize = windowswidesize;
		// TODO Auto-generated constructor stub
	}

	long[] values = new long[] { 100000000, 100000000 };

	int[] colors = new int[] { Color.BLUE, Color.GREEN };

	// float ChartTitleTextSize = 20;
	//
	// float LabelsTextSize = 20;
	float Scale = (float) 0.7;

	public void setValues(long[] values) {
		if (values[0] == values[1]) {
			this.values = new long[] { 1, 1 };
		} else
			this.values = values;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	// float LegendTextSize = 20;

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View execute(Context context) {

		// , Color.MAGENTA,
		// Color.YELLOW, Color.CYAN };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		// renderer.setZoomButtonsVisible(true);
		// renderer.setZoomEnabled(true);
		renderer.setChartTitleTextSize(windowswidesize / 20);
		renderer.setScale(Scale);
		// 设置边界等
		// Log.d("main", width+"");
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
		// renderer.setPanLimits(limit);
		// renderer.setZoomLimits(limit2);
		// renderer.setZoomRate(1.1f);
		return ChartFactory.getPieChartView(context,
				buildCategoryDataset("Project budget", values), renderer);

		// return ChartFactory.getPieChartIntent(context,
		// buildCategoryDataset("Project budget", values), renderer,
		// "Budget");
	}

	/**
	 * Builds a category renderer to use the provided colors.
	 * 
	 * @param colors
	 *            the colors
	 * @return the category renderer
	 */
	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLabelsTextSize(windowswidesize / 17);
		// 左下
		renderer.setLegendTextSize(windowswidesize / 11);
		renderer.setMargins(new int[] { 0, 0, 0, 0 });
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	public void setScale(float scale) {
		Scale = scale;
	}

	/**
	 * Builds a category series using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the category series
	 */
	protected CategorySeries buildCategoryDataset(String title, long[] values) {
		CategorySeries series = new CategorySeries(title);
		// int k = 0;
		// for (double value : values) {
		int percentMobile = (int) (100 * values[0] / (values[0] + values[1]));
		int percentWifi = 100 - percentMobile;

		if (percentMobile < 2) {
			series.add("移动 " + percentMobile + "%", 1);
			series.add("WIFI " + percentWifi + "%", 100);
		} else if (percentWifi < 2) {
			series.add("移动 " + percentMobile + "%", 100);
			series.add("WIFI " + percentWifi + "%", 1);
		} else {
			series.add("移动 " + percentMobile + "%", values[0]);
			series.add("WIFI " + percentWifi + "%", values[1]);
		}
		// }

		return series;
	}
}
