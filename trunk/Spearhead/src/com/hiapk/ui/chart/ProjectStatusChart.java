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
package com.hiapk.ui.chart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.hiapk.ui.skin.UiColors;
import com.hiapk.util.MonthDay;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
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
	}

	// �������ĸ���Ҫ��data����color��ͳһ��
	// ��Ҫд�������1ǰ��
	double[] dataMobile = new double[] { 1402, 1023, 1042, 1502, 1409 };
	// ��Ҫд�������2��
	double[] dataWifi = new double[] { 146, 155, 146, 142, 123 };
	int year = 110;
	int month = 5;
	int dayofMonth = 5;
	int beforeDayofMonth = 31;
	int showDay = 35;
	List<Date[]> dates = new ArrayList<Date[]>();
	String[] titles = new String[] { "�ƶ���������", "WIFI��������" };
	// ���µ����������
	double MaxTrafficWifi = 10;
	double MaxTrafficMobile = 10;
	double MinTrafficWifi = 0;
	double MinTrafficMobile = 0;
	// X����ʾ����
	String XaxisText = "����";
	// ����
	String mainTitle = "������ͳ��";
	int backgroundColor = UiColors.colorMainWhiteDark;

	/**
	 * ������
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
	 * ��ʼ������(�ȳ�ʼ�������ٳ�ʼ������)
	 * 
	 * @param year
	 * @param month
	 * @param dayofMonth
	 */
	public void initDate(int year, int month, int dayofMonth) {
		// List<Date[]> dates = new ArrayList<Date[]>();
		// ��ʼ��ǰ�µ����������������
		if (month != 1) {
			beforeDayofMonth = MonthDay.countDay(year, month - 1);
		} else {
			beforeDayofMonth = 31;
		}
		// int date1 = year - 1900;
		// int date2 = month - 1;
		// ��ʾ������
		showDay = beforeDayofMonth + dayofMonth;

		String[] xaxles = new String[62];
		for (int i = 0; i < beforeDayofMonth; i++) {
			if (month != 1) {
				xaxles[i] = (month - 1) + "��" + (i + 1) + "��";
			} else {
				xaxles[i] = 12 + "��" + (i + 1) + "��";
				// showlog(xaxles[i]);
			}
		}
		int j = 0;
		for (int i = beforeDayofMonth; i < xaxles.length; i++) {
			j++;
			xaxles[i] = month + "��" + j + "��";
			// showlog(xaxles[i]);
		}
		this.xaxles = xaxles;
	}

	/**
	 * ����4��64λ���ݣ���ʼ��ʵ��ʹ�õı�����ݣ����������ڳ�ʼ��֮��
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
		double MaxTrafficWifi = 0;
		double MinTrafficWifi = 0;
		double MaxTrafficMobile = 0;
		double MinTrafficMobile = 0;
		long temp = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		// showlog("showDay" + showDay);
		// showlog("beforeDayofMonth" + beforeDayofMonth);
		for (int i = 0; i < beforeDayofMonth; i++) {
			temp = mobileDatabefore[i + 1] + mobileDatabefore[i + 32];
			dataMobile[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataMobile[i] > MaxTrafficMobile)
				MaxTrafficMobile = dataMobile[i];
			if (dataMobile[i] < MinTrafficMobile)
				MinTrafficMobile = dataMobile[i];
			temp = wifiDatabefor[i + 1] + wifiDatabefor[i + 32];
			dataWifi[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataWifi[i] > MaxTrafficWifi)
				MaxTrafficWifi = dataWifi[i];
			if (dataWifi[i] < MinTrafficWifi)
				MinTrafficWifi = dataWifi[i];
		}
		int j = 1;
		for (int i = beforeDayofMonth; i < showDay; i++) {
			temp = mobileDatanow[j] + mobileDatanow[j + 31];
			dataMobile[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataMobile[i] > MaxTrafficMobile)
				MaxTrafficMobile = dataMobile[i];
			if (dataMobile[i] < MinTrafficMobile)
				MinTrafficMobile = dataMobile[i];
			temp = wifiDatanow[j] + wifiDatanow[j + 31];
			dataWifi[i] = Double.valueOf(df.format((double) temp / 1048576));
			if (dataWifi[i] > MaxTrafficWifi)
				MaxTrafficWifi = dataWifi[i];
			if (dataWifi[i] < MinTrafficWifi)
				MinTrafficWifi = dataWifi[i];
			j++;
		}
		this.dataMobile = dataMobile;
		this.dataWifi = dataWifi;
		if (MaxTrafficWifi == 0) {
			this.MaxTrafficWifi = 1;
		} else {
			this.MaxTrafficWifi = (double) MaxTrafficWifi * 1.35;
		}
		if (MaxTrafficMobile == 0) {
			this.MaxTrafficMobile = 1;
		} else {
			this.MaxTrafficMobile = (double) MaxTrafficMobile * 1.35;
		}

		if (MinTrafficWifi > 0) {
			this.MinTrafficWifi = MinTrafficWifi * 4 / 5;
		} else {
			this.MinTrafficWifi = 0;
		}
		if (MinTrafficMobile > 0) {
			this.MinTrafficMobile = MinTrafficMobile * 4 / 5;
		} else {
			this.MinTrafficMobile = 0;
		}

	}

	// x����ʾ������
	String[] xaxles = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	// ��״����ɫ
	int[] chartbarcolor = new int[] { Color.CYAN, Color.CYAN };
	// int[] chartbarcolor = new int[] { Color.CYAN };
	float AxisTitleTextSize = windowswidesize / 2;
	float ChartTitleTextSize = windowswidesize / 2;
	float LabelsTextSize = windowswidesize / 3;
	float LegendTextSize = windowswidesize / 3;
	float ChartValuesTextsize = windowswidesize / 3;

	public View execute(Context context) {
		String[] titles = new String[] { "�ƶ���������", "WIFI��������" };
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
		// ���ñ���ɫ
		renderer.setMarginsColor(backgroundColor);
		renderer.setBackgroundColor(backgroundColor);
		renderer.setApplyBackgroundColor(true);
		// X��
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setXLabels(0);
		int i = 0;
		for (String xaxis : xaxles) {
			i++;
			String name = xaxis;
			renderer.addXTextLabel(i, name);
		}
		renderer.setPointSize(windowswidesize / 70);
		// // ������ʼ����
		// setChartSettings(renderer, mainTitle, "", "������MB��", showDay - 3.5,
		// showDay + 0.5, -MaxTrafficMobile / 15, MaxTrafficMobile,
		// Color.rgb(80, 80, 80), Color.rgb(80, 80, 80));
		renderer.setYLabels(10);
		// ���ñ߽��
		// Log.d("main", width+"");
		double[] limit = new double[] { 0.5, showDay + 0.5, 0, MaxTrafficMobile };
		double[] limit2 = new double[] { 1, showDay - 1, 0, 10 };
		renderer.setPanLimits(limit);
		renderer.setPanEnabled(true, false);
		renderer.setZoomLimits(limit2);
		renderer.setBarSpacing(1f);
		renderer.setZoomRate(1f);
		// �����ľ�������
		for (int j = 0; j < length; j++) {
			SimpleSeriesRenderer seriesRenderer = renderer
					.getSeriesRendererAt(j);
			seriesRenderer.setDisplayChartValues(true);
			seriesRenderer.setChartValuesTextSize(windowswidesize / 11);
			seriesRenderer.setChartValuesSpacing(5);
			((XYSeriesRenderer) seriesRenderer).setFillPoints(true);
		}
		return ChartFactory.getLineChartView(context,
				buildBarDataset(titles, values)
				// buildDateDataset(titles, dates, values)
				, renderer);
	}

	public View execute2(Context context) {
		String[] titles = new String[] { "�ƶ�����" };
		List<double[]> x = new ArrayList<double[]>();
		double[] dou = new double[showDay];
		for (int i = 0; i < showDay; i++) {
			dou[i] = i + 1;
		}
		x.add(dou);
		// showlog("MaxTrafficMobile=" + MaxTrafficMobile);
		showlog("MaxTrafficWifi=" + MaxTrafficWifi);
		List<double[]> values = new ArrayList<double[]>();
		values.add(dataMobile);

		// List<double[]> x = new ArrayList<double[]>();
		// for (int i = 0; i < titles.length; i++) {
		// x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
		// }
		// List<double[]> values = new ArrayList<double[]>();
		// values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4,
		// 26.1, 23.6, 20.3, 17.2, 13.9 });

		int[] colors = UiColors.chartbarcolor;
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.SQUARE };
		// XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(2);
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

		// int length = renderer.getSeriesRendererCount();
		// for (int i = 0; i < length; i++) {
		// XYSeriesRenderer r = (XYSeriesRenderer) renderer
		// .getSeriesRendererAt(i);
		// r.setPointStyle(PointStyle.POINT);
		// r.setPointStyle(PointStyle.SQUARE);
		// r.setLineWidth(3f);
		// }
		//
		// ���ñ���ɫ
		renderer.setMarginsColor(backgroundColor);
		renderer.setBackgroundColor(backgroundColor);
		renderer.setApplyBackgroundColor(true);
		// X��
		// renderer.setYLabelsAlign(Align.LEFT);
		renderer.setXLabels(0);
		int i = 0;
		for (String xaxis : xaxles) {
			i++;
			String name = xaxis;
			renderer.addXTextLabel(i, name);
		}
		//

		// setRenderer(renderer, colors, styles);
		// int length = renderer.getSeriesRendererCount();
		// for (int j = 0; j < length; j++) {
		// XYSeriesRenderer r = (XYSeriesRenderer) renderer
		// .getSeriesRendererAt(j);
		// r.setLineWidth(3f);
		// }
		setChartSettings(renderer, "", "",
				new String[] { "        ����(MB)", "         " },
				// new String[] { "        ", "        " },
				new double[] { showDay - 5.5, showDay - 5.5 }, new double[] {
						showDay + 0.5, showDay + 0.5 }, new double[] {
						-MaxTrafficMobile / 20, -MaxTrafficWifi / 6 },
				new double[] { MaxTrafficMobile, MaxTrafficWifi * 0.85 },
				Color.rgb(80, 80, 80), colors);
		// renderer.setYLabels(10);

		// renderer.setXLabels(12);
		// renderer.setYLabels(10);
		// renderer.setShowGrid(true);
		// renderer.setXLabelsAlign(Align.RIGHT);
		// renderer.setYLabelsAlign(Align.RIGHT);
		// renderer.setZoomButtonsVisible(true);
		// renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		// renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
		// renderer.setZoomRate(1.05f);

		// ���ñ߽��
		// Log.d("main", width+"");
		double[] limit = new double[] { 0.5, showDay + 0.5, 0, MaxTrafficMobile };
		double[] limit2 = new double[] { 1, showDay - 1, 0, 10 };
		renderer.setPanLimits(limit);
		renderer.setPanEnabled(true, false);
		renderer.setZoomLimits(limit2);
		renderer.setBarSpacing(1f);
		renderer.setZoomRate(1f);

		// renderer.setLabelsColor(Color.WHITE);
		// renderer.setXLabelsColor(Color.GREEN);
		// renderer.setYLabelsColor(0, colors[0]);
		// renderer.setYLabelsColor(1, colors[1]);

		// renderer.addYTextLabel(20, "Test", 0);
		// renderer.addYTextLabel(10, "New Test", 1);

		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		values.clear();
		values.add(dataWifi);
		// values.add(new double[] { 4.3, 4.9, 5.9, 8.8, 10.8, 11.9, 13.6, 12.8,
		// 11.4, 9.5, 7.5, 5.5 });
		addXYSeries(dataset, new String[] { "WIFI����" }, x, values, 1);

		// // �����ľ�������
		// for (int j = 0; j < 2; j++) {
		// SimpleSeriesRenderer seriesRenderer = renderer
		// .getSeriesRendererAt(j);
		// seriesRenderer.setDisplayChartValues(true);
		// seriesRenderer.setChartValuesTextSize(windowswidesize / 11);
		// seriesRenderer.setChartValuesSpacing(1);
		// // ((XYSeriesRenderer) seriesRenderer).setFillPoints(true);
		// }
		// View view = ChartFactory.getLineChartView(context, dataset,
		// renderer);
		View view = ChartFactory.getCubeLineChartView(context, dataset,
				renderer, 0f);
		// ChartFactory.getCubicLineChartIntent(context, dataset,
		// renderer, 0.3f, "Average temperature");
		return view;
	}

	protected XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			for (int j = 0; j < xV.length; j++) {
				// showlog("xV" + j + "=" + xV[j]);
			}
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	// ///////
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
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(2);
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		// ���ϵ�����
		renderer.setAxisTitleTextSize(windowswidesize / 13);
		renderer.setChartTitleTextSize((float) (windowswidesize / 9.5));
		// 12345������
		renderer.setLabelsTextSize(windowswidesize / 13);
		// ����ɫ������С����
		renderer.setLegendTextSize(windowswidesize / 11);
		renderer.setPointSize(windowswidesize / 70);
		renderer.setMargins(new int[] { 20, 32, 28, 8 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			r.setLineWidth(2f);
			r.setFillPoints(true);
			r.setGradientEnabled(true);
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
			String title, String xTitle, String[] yTitle, double[] xMin,
			double[] xMax, double[] yMin, double[] yMax, int axesColor,
			int[] labelsColor) {
		// new String[] { "�ƶ����磨MB��", "WIFI���磨MB��" },
		renderer.setYTitle("       �ƶ����磨MB��", 0);
		renderer.setYTitle("       WIFI���磨MB��", 1);
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setXAxisMin(xMin[0], 0);
		renderer.setXAxisMax(xMax[0], 0);
		renderer.setXAxisMin(xMin[1], 1);
		renderer.setXAxisMax(xMax[1], 1);
		renderer.setYAxisMin(yMin[0], 0);
		renderer.setYAxisMax(yMax[0], 0);
		renderer.setYAxisMin(yMin[1], 1);
		renderer.setYAxisMax(yMax[1], 1);
		renderer.setAxesColor(axesColor);
		renderer.setXLabelsColor(axesColor);
		renderer.setLabelsColor(axesColor);
		renderer.setYLabelsColor(0, labelsColor[0]);
		renderer.setYLabelsColor(1, labelsColor[1]);
		renderer.setYTitle(yTitle[0], 0);
		renderer.setYTitle(yTitle[1], 1);
		renderer.setYAxisAlign(Align.LEFT, 0);
		renderer.setYLabelsAlign(Align.LEFT, 0);
		renderer.setYAxisAlign(Align.RIGHT, 1);
		renderer.setYLabelsAlign(Align.RIGHT, 1);

	}

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("project", string);
	}
}
