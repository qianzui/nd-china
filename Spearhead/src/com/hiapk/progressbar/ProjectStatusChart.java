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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.hiapk.provider.UiColors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

/**
 * Project status demo chart.
 */
public class ProjectStatusChart extends ViewBase {
	int windowswidesize = 300;

	public ProjectStatusChart(Context context, int windowswidesize) {
		super(context);
		this.windowswidesize = windowswidesize;
		// Log.d("main", windowswidesize+"windowswidesize");
		// TODO Auto-generated constructor stub
	}

	// 名称数的个数要与data数与color数统一！
	// 需要写入的数据1前方
	double[] dataMobile = new double[] { 1402, 1023, 1042, 1502, 1409 };
	// 需要写入的数据2后方
	double[] dataWifi = new double[] { 146, 155, 146, 142, 123 };
	int year = 110;
	int month = 5;
	int dayofMonth = 5;
	int beforeDayofMonth = 31;
	int showDay = 35;
	List<Date[]> dates = new ArrayList<Date[]>();
	String[] titles = new String[] { "移动数据流量", "WIFI数据流量" };
	// 本月的最大流量数
	double MaxTraffic = 10;
	double MinTraffic = 0;
	// X轴显示的字
	String XaxisText = "日期";
	//标题
	String mainTitle = "日流量统计";
	/**
	 * 主标题
	 * 
	 * @param mainTitle
	 */
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public void setXaxisText(String XaxisText) {
		this.XaxisText = XaxisText;
	}

	/**
	 * 初始化日期(先初始化日期再初始化数据)
	 * 
	 * @param year
	 * @param month
	 * @param dayofMonth
	 */
	public void initDate(int year, int month, int dayofMonth) {
		// List<Date[]> dates = new ArrayList<Date[]>();
		// 初始化前月的天数与这个月天数
		if (month != 1) {
			beforeDayofMonth = countDay(year, month - 1);
		} else {
			beforeDayofMonth = 31;
		}
		// int date1 = year - 1900;
		// int date2 = month - 1;
		// 显示的天数
		showDay = beforeDayofMonth + dayofMonth;

		String[] xaxles = new String[62];
		for (int i = 0; i < beforeDayofMonth; i++) {
			if (month != 1) {
				xaxles[i] = (month - 1) + "月" + (i + 1) + "日";
			} else {
				xaxles[i] = 12 + "月" + (i + 1) + "日";
				// showlog(xaxles[i]);
			}
		}
		int j = 0;
		for (int i = beforeDayofMonth; i < xaxles.length; i++) {
			j++;
			xaxles[i] = month + "月" + j + "日";
			// showlog(xaxles[i]);
		}
		this.xaxles = xaxles;

		// int length = titles.length;
		// for (int i = 0; i < length; i++) {
		// dates.add(new Date[showDay]);
		// if (month != 1) {
		// for (int j = 0; j < beforeDayofMonth; j++) {
		// dates.get(i)[j] = new Date(date1, date2 - 1, j + 1);
		// // showlog(date1 + "," + (date2 - 1) + "," + (j + 1));
		// }
		// for (int j = beforeDayofMonth; j < showDay; j++) {
		// dates.get(i)[j] = new Date(date1, date2, j
		// - beforeDayofMonth + 1);
		// // showlog(date1 + "," + (date2) + ","
		// // + (j - beforeDayofMonth + 1));
		// }
		// } else {
		// for (int j = 0; j < beforeDayofMonth; j++) {
		// dates.get(i)[j] = new Date(date1 - 1, 11, j + 1);
		// // showlog(date1 - 1 + "," + 11 + "," + (j + 1));
		// }
		// for (int j = beforeDayofMonth; j < showDay; j++) {
		// dates.get(i)[j] = new Date(date1, date2, j
		// - beforeDayofMonth + 1);
		// // showlog(date1 + "," + (date2) + ","
		// // + (j - beforeDayofMonth + 1));
		// }
		// }
		//
		// // dates.get(i)[0] = new Date(108, 9, 1);
		// }
		// this.dates = dates;
	}

	/**
	 * 输入4个64位数据，初始化实际使用的表格数据，必须在日期初始化之后
	 * 
	 * @param mobileDatabefore
	 * @param mobileDatanow
	 * @param wifiDatabefor
	 * @param wifiDatanow
	 */
	public void initData(long[] mobileDatabefore, long[] mobileDatanow,
			long[] wifiDatabefor, long[] wifiDatanow) {
		double[] dataMobile = new double[showDay];
		double[] dataWifi = new double[showDay];
		double MaxTraffic = 0;
		double MinTraffic = 0;
		long temp = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		// showlog("showDay" + showDay);
		// showlog("beforeDayofMonth" + beforeDayofMonth);
		for (int i = 0; i < beforeDayofMonth; i++) {
			temp = mobileDatabefore[i + 1] + mobileDatabefore[i + 32];
			dataMobile[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataMobile[i] > MaxTraffic)
				MaxTraffic = dataMobile[i];
			if (dataMobile[i] < MinTraffic)
				MinTraffic = dataMobile[i];
			temp = wifiDatabefor[i + 1] + wifiDatabefor[i + 32];
			dataWifi[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataWifi[i] > MaxTraffic)
				MaxTraffic = dataWifi[i];
			if (dataWifi[i] < MinTraffic)
				MinTraffic = dataWifi[i];
		}
		int j = 1;
		for (int i = beforeDayofMonth; i < showDay; i++) {
			temp = mobileDatanow[j] + mobileDatanow[j + 31];
			dataMobile[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataMobile[i] > MaxTraffic)
				MaxTraffic = dataMobile[i];
			if (dataMobile[i] < MinTraffic)
				MinTraffic = dataMobile[i];
			temp = wifiDatanow[j] + wifiDatanow[j + 31];
			dataWifi[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataWifi[i] > MaxTraffic)
				MaxTraffic = dataWifi[i];
			if (dataWifi[i] < MinTraffic)
				MinTraffic = dataWifi[i];
			j++;
		}
		this.dataMobile = dataMobile;
		this.dataWifi = dataWifi;
		// for (int i = 0; i < dataMobile.length; i++) {
		// // showlog("dataMobile=" + dataMobile[i] + "dataWifi=" +
		// // dataWifi[i]);
		// }
		if (MaxTraffic == 0) {
			this.MaxTraffic = 1;
		} else {
			this.MaxTraffic = (double) MaxTraffic * 1.2;
		}

		if (MinTraffic > 0) {
			this.MinTraffic = MinTraffic * 4 / 5;
		} else {
			this.MinTraffic = 0;
		}

	}

	// x轴显示的数字
	String[] xaxles = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	// 柱状条颜色
	int[] chartbarcolor = new int[] { Color.CYAN, Color.CYAN };
	// int[] chartbarcolor = new int[] { Color.CYAN };
	float AxisTitleTextSize = windowswidesize / 2;
	float ChartTitleTextSize = windowswidesize / 2;
	float LabelsTextSize = windowswidesize / 3;
	float LegendTextSize = windowswidesize / 3;
	float ChartValuesTextsize = windowswidesize / 3;

	public View execute(Context context) {
		String[] titles = new String[] { "移动数据流量", "WIFI数据流量" };
		int length = titles.length;
		// List<Date[]> dates = new ArrayList<Date[]>();
		List<double[]> values = new ArrayList<double[]>();
		values.add(dataMobile);
		values.add(dataWifi);
		// dates = this.dates;
		int[] colors = UiColors.chartbarcolor;
		PointStyle[] styles = new PointStyle[] { PointStyle.SQUARE,
				PointStyle.DIAMOND };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		for (int i = 0; i < length; i++) {

			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		// 设置背景色
		renderer.setMarginsColor(Color.WHITE);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setApplyBackgroundColor(true);
		// X轴
		renderer.setXLabels(0);
		int i = 0;
		for (String xaxis : xaxles) {
			i++;
			String name = xaxis;
			renderer.addTextLabel(i, name);
		}
		renderer.setPointSize(windowswidesize / 70);
		renderer.setChartValuesTextSize(windowswidesize / 11);
		setChartSettings(renderer, mainTitle, "", "流量（MB）", showDay - 5.5,
				showDay + 0.5, MinTraffic, MaxTraffic, Color.rgb(80, 80, 80),
				Color.rgb(80, 80, 80));
		// setChartSettings(renderer, mainTitle, XaxisText, YaxisText,
		// xMinvalue,
		// xMaxvalue, yMinvalue, yMaxvalue, AxisColor, lableColor);
		// setChartSettings(renderer, "历史流量统计", "日期", "流量（MB）",
		//
		// dates.get(0)[0].getTime(), dates.get(0)[3].getTime(), 0, 50,
		// Color.GRAY, Color.LTGRAY);
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
		// renderer.setXLabels(5);
		renderer.setYLabels(10);
		length = renderer.getSeriesRendererCount();
		// for (int i = 0; i < length; i++) {
		// SimpleSeriesRenderer seriesRenderer = renderer
		// .getSeriesRendererAt(i);
		// seriesRenderer.setDisplayChartValues(true);
		// }

		// 设置边界等
		// Log.d("main", width+"");
		double[] limit = new double[] { 0.5, showDay + 0.5, 0, MaxTraffic / 3 };
		double[] limit2 = new double[] { 1, showDay - 1, 0, 0 };
		renderer.setPanLimits(limit);
		renderer.setZoomLimits(limit2);
		renderer.setZoomRate(0f);
		return ChartFactory.getLineChartView(context,
				buildBarDataset(titles, values)
				// buildDateDataset(titles, dates, values)
				, renderer);
	}

	// ,"MM/dd/yyyy"

	// (context,
	// buildDateDataset(titles, dates, values), renderer,
	// Type.DEFAULT);

	/**
	 * Builds an XY multiple series renderer.
	 * 
	 * @param colors
	 *            the series rendering colors
	 * @param styles
	 *            the series point styles
	 * @return the XY multiple series renderers
	 */
	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	@SuppressWarnings("deprecation")
	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		// 轴上的日期
		renderer.setAxisTitleTextSize(windowswidesize / 16);
		renderer.setChartTitleTextSize(windowswidesize / 10);
		// 12345等数字
		renderer.setLabelsTextSize(windowswidesize / 18);
		// 有颜色的左下小标题
		renderer.setLegendTextSize(windowswidesize / 11);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
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
	private XYMultipleSeriesDataset buildBarDataset(String[] titles,
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
	private void setChartSettings(XYMultipleSeriesRenderer renderer,
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
	 * Builds an XY multiple time dataset using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param xValues
	 *            the values for the X axis
	 * @param yValues
	 *            the values for the Y axis
	 * @return the XY multiple time dataset
	 */
	private XYMultipleSeriesDataset buildDateDataset(String[] titles,
			List<Date[]> xValues, List<long[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			Date[] xV = xValues.get(i);
			long[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * 计算单月有几天
	 * 
	 * @param year
	 *            输入年份
	 * @param month
	 *            输入月份
	 * @return 返回天数
	 */
	private int countDay(int year, int month) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))
				&& month == 2) {
			return 29;
		} else {
			switch (month) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			}
		}
		return 31;
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		// Log.d("project", string);
	}
}
