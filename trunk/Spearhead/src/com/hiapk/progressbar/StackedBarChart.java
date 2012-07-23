/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiapk.progressbar;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.hiapk.provider.UiColors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

/**
 * 设置柱状条，最多只能有2条.
 */
public class StackedBarChart extends ViewBase {
	int windowswidesize = 300;

	public StackedBarChart(Context context, int width) {
		super(context);
		this.windowswidesize = width;
		// Log.d("main", windowswidesize+"windowswidesize");
	}

	// 名称数的个数要与data数与color数统一！
	// String[] paramstitles = new String[] { "2008" };
	String topTitle = "总流量";
	// 主标题
	String mainTitle = "总流量统计";
	// X轴标识
	String XaxisText = "日期";
	// Y轴标识
	String YaxisText = "流量（MB）";
	// 柱状条的名称
	String[] paramstitles = new String[] { topTitle, topTitle };
	// 需要写入的数据1前方
	double[] data1 = new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
			22030, 21200, 19500, 15500, 12600, 14000 };
	// 需要写入的数据2后方
	double[] data2 = new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
			22030, 21200, 19500, 15500, 12600, 14000 };
	// X轴初始范围
	double xMinvalue = 0.5;
	// X轴尾部范围
	double xMaxvalue = 12.5;
	// Y轴初始范围
	double yMinvalue = 0;
	// Y轴尾部范围
	double yMaxvalue = 24000;
	// 数轴颜色
	int AxisColor = Color.rgb(80, 80, 80);
	// 数轴标识颜色
	int lableColor = Color.rgb(80, 80, 80);
	// 背景颜色
	int backgroundcolor = Color.WHITE;

	// 本月的天数
	double showDay = 62;
	// x轴显示的数字
	String[] xaxles = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/**
	 * 设置x轴显示的数字
	 */
	public void setXaxles(String[] xaxles) {
		this.xaxles = xaxles;
	}

	/**
	 * 本月的天数
	 * 
	 * @param monthDay
	 */
	public void setShowDay(double monthDay) {
		this.showDay = monthDay;
	}

	// 本月的最大流量数
	double MaxTraffic = 20000;

	/**
	 * 本月的最大流量数
	 * 
	 * @param maxTraffic
	 */
	public void setMaxTraffic(double maxTraffic) {
		MaxTraffic = maxTraffic;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param backgroundcolor
	 */
	public void setBackgroundcolor(int backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}

	// 柱状条颜色
	int[] chartbarcolor = UiColors.chartbarcolor;
	// int[] chartbarcolor = new int[] { Color.CYAN };
	float AxisTitleTextSize = windowswidesize / 2;
	float ChartTitleTextSize = windowswidesize / 2;
	float LabelsTextSize = windowswidesize / 3;
	float LegendTextSize = windowswidesize / 3;
	float ChartValuesTextsize = windowswidesize / 3;

	public void setParamstitles(String[] paramstitles) {
		this.paramstitles = paramstitles;
	}

	/**
	 * 主标题
	 * 
	 * @param mainTitle
	 */
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public void setTopTitle(String topTitle) {
		paramstitles = new String[] { topTitle + "    ", "WIFI网络" };
	}

	/**
	 * X轴文字
	 * 
	 * @param xaxisText
	 */
	public void setXaxisText(String xaxisText) {
		XaxisText = xaxisText;
	}

	/**
	 * Y轴文本
	 * 
	 * @param yaxisText
	 */
	public void setYaxisText(String yaxisText) {
		YaxisText = yaxisText;
	}

	/**
	 * 设置后方柱状条颜色
	 * 
	 * @param data1
	 */
	public void setData1(double[] data1, double[] data2) {
		this.data1 = data1;
		this.data2 = data2;
	}

	/**
	 * 设置前方柱状条颜色
	 * 
	 * @param data2
	 */
	public void setData2(double[] data2) {
		this.data2 = data2;
	}

	/**
	 * x轴数组最小值
	 * 
	 * @param xMinvalue
	 */
	public void setxMinvalue(double xMinvalue) {
		this.xMinvalue = xMinvalue;
	}

	/**
	 * x轴组最大值
	 * 
	 * @param xMaxvalue
	 */
	public void setxMaxvalue(double xMaxvalue) {
		this.xMaxvalue = xMaxvalue;
	}

	/**
	 * Y轴最大值
	 * 
	 * @param yMaxvalue
	 */
	public void setyMaxvalue(double yMaxvalue) {
		this.yMaxvalue = yMaxvalue;
	}
	
	/**
	 * 设置数轴颜色
	 * 
	 * @param axisColor
	 */
	public void setAxisColor(int axisColor) {
		AxisColor = axisColor;
	}

	/**
	 * 数轴标识颜色
	 * 
	 * @param lableColor
	 */
	public void setLableColor(int lableColor) {
		this.lableColor = lableColor;
	}
	
	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public View execute(Context context) {
		initSize();
		// String[] titles = new String[] { "2008", "2007" };
		List<double[]> values = new ArrayList<double[]>();
		// 显示的数据值
		// values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
		// 22030, 21200, 19500, 15500,
		// 12600, 14000 });
		values.add(data1);
		values.add(data2);
		// int[] colors = new int[] { Color.BLUE, Color.CYAN };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(chartbarcolor);
		// 设置进度条其他参数等。
		setChartSettings(renderer, mainTitle, XaxisText, YaxisText, xMinvalue,
				xMaxvalue, yMinvalue, yMaxvalue, AxisColor, lableColor);
		// renderer.setXLabels(7);
		renderer.setXLabels(0);
		// x轴
		int i = 0;
		for (String xaxis : xaxles) {
			i++;
			String name = xaxis;
			renderer.addTextLabel(i, name);
		}
		//
		renderer.setYLabels(10);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.LEFT);
		// renderer.setZoomEnabled(false);
		// other
		renderer.setShowGrid(true);
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(ChartValuesTextsize);
		// 设置边界等
		// Log.d("main", width+"");
		double[] limit = new double[] { 0.5, showDay + 0.5, 0, MaxTraffic };
		double[] limit2 = new double[] { 1, 31, 0, MaxTraffic };
		renderer.setPanLimits(limit);
		renderer.setPanEnabled(true, false);
		renderer.setZoomLimits(limit2);
		renderer.setZoomEnabled(true, false);
		renderer.setBarSpacing(1.2f);
		return ChartFactory.getBarChartView(context,
				buildBarDataset(paramstitles, values), renderer, Type.DEFAULT);
	}

	private void initSize() {
		// TODO Auto-generated method stub
		AxisTitleTextSize = windowswidesize / 16;
		ChartTitleTextSize = windowswidesize / 12;
		LabelsTextSize = windowswidesize / 16;
		LegendTextSize = windowswidesize / 13;
		ChartValuesTextsize = windowswidesize / 15;
	}

	/**
	 * Builds a bar multiple series renderer to use the provided colors.
	 * 
	 * @param colors
	 *            the series renderers colors
	 * @return the bar multiple series renderer
	 */
	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(AxisTitleTextSize);
		renderer.setChartTitleTextSize(ChartTitleTextSize);
		renderer.setLabelsTextSize(LabelsTextSize);
		renderer.setLegendTextSize(LegendTextSize);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		// renderer.setMargins(new int[] {20, 30, 15,0}); // 设置4边留白
		renderer.setMarginsColor(Color.WHITE);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setApplyBackgroundColor(true);
		return renderer;
	}

	/**
	 * Sets a few of the series renderer settings.
	 * 
	 * @param renderer
	 *            the renderer to set the properties to
	 * @param title
	 *            the chart title
	 * @param xTitle
	 *            the title for the X axis
	 * @param yTitle
	 *            the title for the Y axis
	 * @param xMin
	 *            the minimum value on the X axis
	 * @param xMax
	 *            the maximum value on the X axis
	 * @param yMin
	 *            the minimum value on the Y axis
	 * @param yMax
	 *            the maximum value on the Y axis
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * Builds a bar multiple series dataset using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the XY multiple bar dataset
	 */
	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

}
