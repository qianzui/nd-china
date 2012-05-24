package com.hiapk.progressbar;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

public class BudgetPie {

	long[] values = new long[] { 100000000, 100000000 };

	int[] colors = new int[] { Color.BLUE, Color.GREEN };

	float ChartTitleTextSize = 20;

	float LabelsTextSize = 20;
	float Scale = (float) 0.8;

	public void setValues(long[] values) {
		this.values = values;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	float LegendTextSize = 20;

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
		renderer.setChartTitleTextSize(ChartTitleTextSize);
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
		renderer.setLabelsTextSize(LabelsTextSize);
		renderer.setLegendTextSize(LegendTextSize);
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
		series.add("移动 ", values[0]);
		series.add("WIFI ", values[1]);
		// }

		return series;
	}
}
