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
 * ������״�������ֻ����2��.
 */
public class StackedBarChart extends ViewBase {
	int windowswidesize = 300;

	public StackedBarChart(Context context, int width) {
		super(context);
		this.windowswidesize = width;
		// Log.d("main", windowswidesize+"windowswidesize");
	}

	// �������ĸ���Ҫ��data����color��ͳһ��
	// String[] paramstitles = new String[] { "2008" };
	String topTitle = "������";
	// ������
	String mainTitle = "������ͳ��";
	// X���ʶ
	String XaxisText = "����";
	// Y���ʶ
	String YaxisText = "������MB��";
	// ��״��������
	String[] paramstitles = new String[] { topTitle, topTitle };
	// ��Ҫд�������1ǰ��
	double[] data1 = new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
			22030, 21200, 19500, 15500, 12600, 14000 };
	// ��Ҫд�������2��
	double[] data2 = new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
			22030, 21200, 19500, 15500, 12600, 14000 };
	// X���ʼ��Χ
	double xMinvalue = 0.5;
	// X��β����Χ
	double xMaxvalue = 12.5;
	// Y���ʼ��Χ
	double yMinvalue = 0;
	// Y��β����Χ
	double yMaxvalue = 24000;
	// ������ɫ
	int AxisColor = Color.rgb(80, 80, 80);
	// �����ʶ��ɫ
	int lableColor = Color.rgb(80, 80, 80);
	// ������ɫ
	int backgroundcolor = Color.WHITE;

	// ���µ�����
	double showDay = 62;
	// x����ʾ������
	String[] xaxles = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/**
	 * ����x����ʾ������
	 */
	public void setXaxles(String[] xaxles) {
		this.xaxles = xaxles;
	}

	/**
	 * ���µ�����
	 * 
	 * @param monthDay
	 */
	public void setShowDay(double monthDay) {
		this.showDay = monthDay;
	}

	// ���µ����������
	double MaxTraffic = 20000;

	/**
	 * ���µ����������
	 * 
	 * @param maxTraffic
	 */
	public void setMaxTraffic(double maxTraffic) {
		MaxTraffic = maxTraffic;
	}

	/**
	 * ���ñ�����ɫ
	 * 
	 * @param backgroundcolor
	 */
	public void setBackgroundcolor(int backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}

	// ��״����ɫ
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
	 * ������
	 * 
	 * @param mainTitle
	 */
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public void setTopTitle(String topTitle) {
		paramstitles = new String[] { topTitle + "    ", "WIFI����" };
	}

	/**
	 * X������
	 * 
	 * @param xaxisText
	 */
	public void setXaxisText(String xaxisText) {
		XaxisText = xaxisText;
	}

	/**
	 * Y���ı�
	 * 
	 * @param yaxisText
	 */
	public void setYaxisText(String yaxisText) {
		YaxisText = yaxisText;
	}

	/**
	 * ���ú���״����ɫ
	 * 
	 * @param data1
	 */
	public void setData1(double[] data1, double[] data2) {
		this.data1 = data1;
		this.data2 = data2;
	}

	/**
	 * ����ǰ����״����ɫ
	 * 
	 * @param data2
	 */
	public void setData2(double[] data2) {
		this.data2 = data2;
	}

	/**
	 * x��������Сֵ
	 * 
	 * @param xMinvalue
	 */
	public void setxMinvalue(double xMinvalue) {
		this.xMinvalue = xMinvalue;
	}

	/**
	 * x�������ֵ
	 * 
	 * @param xMaxvalue
	 */
	public void setxMaxvalue(double xMaxvalue) {
		this.xMaxvalue = xMaxvalue;
	}

	/**
	 * Y�����ֵ
	 * 
	 * @param yMaxvalue
	 */
	public void setyMaxvalue(double yMaxvalue) {
		this.yMaxvalue = yMaxvalue;
	}
	
	/**
	 * ����������ɫ
	 * 
	 * @param axisColor
	 */
	public void setAxisColor(int axisColor) {
		AxisColor = axisColor;
	}

	/**
	 * �����ʶ��ɫ
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
		// ��ʾ������ֵ
		// values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
		// 22030, 21200, 19500, 15500,
		// 12600, 14000 });
		values.add(data1);
		values.add(data2);
		// int[] colors = new int[] { Color.BLUE, Color.CYAN };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(chartbarcolor);
		// ���ý��������������ȡ�
		setChartSettings(renderer, mainTitle, XaxisText, YaxisText, xMinvalue,
				xMaxvalue, yMinvalue, yMaxvalue, AxisColor, lableColor);
		// renderer.setXLabels(7);
		renderer.setXLabels(0);
		// x��
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
		// ���ñ߽��
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
		// renderer.setMargins(new int[] {20, 30, 15,0}); // ����4������
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
