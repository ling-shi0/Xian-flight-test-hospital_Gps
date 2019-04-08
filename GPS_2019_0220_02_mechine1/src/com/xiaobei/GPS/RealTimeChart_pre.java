package com.xiaobei.GPS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.xiaobei.GPS.Counter;
import com.xiaobei.GPS.ReadFile;
public class RealTimeChart_pre{
	static ReadFile rf=new ReadFile();
	private static final long serialVersionUID = 100L;
	final static XYSeries series = new XYSeries("Data");
	final static XYSeries series2 = new XYSeries("Data");
	final static XYSeries series3 = new XYSeries("Data");
	static double height[]=rf.readSpecify(new File("2-gpsc.xls"), 1);
	static double jing[]=rf.readSpecify(new File("2-gpsc.xls"), 3);
	static double wei[]=rf.readSpecify(new File("2-gpsc.xls"), 2);
	private static JFreeChart createChart(String title, String xaxisName,String yaxisName) {
		// TODO A  auto-generated method stub
		// ����ʱ��ͼ����
		for (int i = 0; i < 300; i++) {
            series.add(i,height[i]);
            //hangjituEast.jTextField1.setText(String.valueOf(height[i]));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(title, xaxisName,
        		yaxisName, dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot _xyplot=(XYPlot)chart.getPlot();
        _xyplot.setBackgroundPaint(ChartColor.black);
        XYLineAndShapeRenderer xylinerenderer=(XYLineAndShapeRenderer)_xyplot.getRenderer();
        xylinerenderer.setPaint(ChartColor.GREEN);
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题   
        // 图片标题   
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

		return chart;
    	
	}	
	private static JFreeChart createChart2(String title, String xaxisName,
			String yaxisName) {
		// TODO A  auto-generated method stub
		// ����ʱ��ͼ����
		for (int i = 0; i < 300; i++) {
            series2.add(i,jing[i]);
            //hangjituEast.jTextField2.setText(String.valueOf(jing[i]));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series2);
        JFreeChart chart = ChartFactory.createXYLineChart(title, xaxisName,
        		yaxisName, dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot _xyplot=(XYPlot)chart.getPlot();
        _xyplot.setBackgroundPaint(ChartColor.black);
        XYLineAndShapeRenderer xylinerenderer=(XYLineAndShapeRenderer)_xyplot.getRenderer();
        xylinerenderer.setPaint(ChartColor.GREEN);
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题   
        // 图片标题   
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

		return chart;
    	
	}	
	private static JFreeChart createChart3(String title, String xaxisName,String yaxisName) {
		// TODO A  auto-generated method stub
		// ����ʱ��ͼ����
		for (int i = 0; i < 300; i++) {
            series3.add(i,wei[i] );
            //hangjituEast.jTextField3.setText(String.valueOf(wei[i]));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series3);
        JFreeChart chart = ChartFactory.createXYLineChart(title, xaxisName,
        		yaxisName, dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot _xyplot=(XYPlot)chart.getPlot();
        _xyplot.setBackgroundPaint(ChartColor.black);
        XYLineAndShapeRenderer xylinerenderer=(XYLineAndShapeRenderer)_xyplot.getRenderer();
        xylinerenderer.setPaint(ChartColor.GREEN);
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题   
        // 图片标题   
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
		return chart;
    	
	}

	public static JPanel createPanel_XYZ()
    {
		JFreeChart chart_x = createChart2("经度", "Time", "Value");
		Counter counter=new Counter();
		counter.setCount(series.getItemCount());
		ChartPanel chartpanel_x = new ChartPanel(chart_x){
			public Dimension getPreferredSize() {
				return new Dimension(450, 225);
			}
		};
		JFreeChart chart_y = createChart3("纬度", "Time", "Value");
		ChartPanel chartpanel_y = new ChartPanel(chart_y){
			public Dimension getPreferredSize() {
				return new Dimension(450, 225);
			}
		};
		JFreeChart chart_z = createChart("高度", "Time", "Value");
		ChartPanel chartpanel_z = new ChartPanel(chart_z){
			public Dimension getPreferredSize() {
				return new Dimension(450, 225);
			}
		};
		new Timer(1000, new ActionListener() { 		
    		@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
    			 series.remove(0);
    			 series2.remove(0);
    			 series3.remove(0);
    			 try {
					series.add(counter.getCount(),height[counter.getCount()]);
					double para1 = height[counter.getCount()];
					String para1String = String.format("%.4f", para1);
					hangjituEast.jTextField1.setText(para1String);
					
					
					series2.add(counter.getCount(),jing[counter.getCount()]);
					double para2 = jing[counter.getCount()];
					String para2String = String.format("%.4f", para2);
					hangjituEast.jTextField2.setText(para2String);
					
					
					Gps.x1 = jing[counter.getCount()];
					Gps.y1 = wei[counter.getCount()];
					
					series3.add(counter.getCount(),wei[counter.getCount()]);
					double para3 = wei[counter.getCount()];
					String para3String = String.format("%.4f", para3);
					hangjituEast.jTextField3.setText(para3String);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            counter.CountAdd1();
			}
    		}).start();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
	    panel.setOpaque(false);
	    panel.setSize(360,670);
	    panel.setBackground(Color.BLACK);
		panel.add(chartpanel_x);
		panel.add(chartpanel_y);
		panel.add(chartpanel_z);
		panel.setVisible(true);
		return panel;
    }
}