package com.xiaobei.GPS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class RealTimeChart implements Runnable {

	int count = 0;//用来从数组中取出数据
	static ReadFile rf=new ReadFile();
	private static final long serialVersionUID = 1L;
	private static TimeSeries timeSeries;
	private static JFreeChart chart;
	private static RealTimeChart rtcp;
	static double height[]=rf.readSpecify(new File("gpsshuju.xls"), 0);
	static double jing[]=rf.readSpecify(new File("gpsshuju.xls"), 1);
	static double wei[]=rf.readSpecify(new File("gpsshuju.xls"), 2);
	

	private static JFreeChart createChart(String chartContent, String title,
			String yaxisName) {
		// TODO Auto-generated method stub
		// 创建时序图对�?
		timeSeries = new TimeSeries(chartContent, Millisecond.class);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(
				timeSeries);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,
				"Time", yaxisName, timeseriescollection, true, true, false);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设�?
		ValueAxis valueaxis = xyplot.getDomainAxis();
		// 自动设置数据轴数据范�?
		valueaxis.setAutoRange(true);
		// 数据轴固定数据范�? 30s
		valueaxis.setFixedAutoRange(30000D);

		valueaxis = xyplot.getRangeAxis();
		// valueaxis.setRange(0.0D,200D);
		return jfreechart;
	}

	private double randomNum() {
		//System.out.println((Math.random() * 20 + 80));
		//return (long) (Math.random() * 20);
		return height[count];
	}

	@Override
	public void run() {
		while (true) {
			try {
				timeSeries.add(new Millisecond(), randomNum());
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static JPanel createPanel()
    {
		rtcp = new RealTimeChart();
		chart = RealTimeChart.createChart("Random Data", "EchoSignal", "Value");
		(new Thread(rtcp)).start();
        JPanel panel = new ChartPanel(chart); //将chart对象放入Panel面板中去，ChartPanel类已继承Jpanel
        panel.setSize(300, 180);
        panel.setOpaque(false);
        panel.setBackground(Color.DARK_GRAY);
        return panel;
    }
	
	/**
	 * 让本chart动起来
	 */
	public static void MotiveChart(){
		(new Thread(rtcp)).start();
	}
	
	
}
