package com.xiaobei.GPS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import org.json.JSONObject;
import app.USB2XXXUSBDeviceOpenLight1;
import app.USB2XXXUSBDeviceOpenLight2;
import app.USB2XXXUSBDeviceOpenLight3;
import com.tena.mq.AbstractMessageExecutor;
import com.tena.mq.IMessageExecutor;
import com.tena.mq.MessageUtil;

/**
 * GPS
 * 
 * @author XiaoBei
 * @date 2018-11-1
 */
public class Gps extends JFrame {
	/**
	 * 
	 */
	public static JLabel hangjituLabel = new JLabel();
	public static JLabel local1 = null;
	public static JLabel local2 = null;
	public static double x1;
	public static double x2;
	public static double y1;
	public static double y2;
	Timer timer = new Timer();
	private static final long serialVersionUID = 1L;
	private JLabel topLabel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JMenuBar menuBar;
	private static JButton stopButton;
	public static JButton startButton;
	private static JButton fileButton;
	private static JButton aboutButton;
	private static JButton pcmButton;
	private static JButton gpsButton;
	private static JButton exitButton;
	private JLabel leftJLabel;
	private String DEFAULT_TIME_FORMAT = "HH:mm:ss.SSS";
	private JTextField timeField;
	private static boolean bool;
	private JScrollPane showJScrollPane;
	private static JTextArea showField;
	public static JTextField ID_text;
	public static JTextField A_text;
	public static JTextField R_text;
	public static JTextField status_text;
	public static JTextField E_text;
	public static JTextField B_text;
	public static JTextField C_text;
	public static JTextField D_text;
	public static JTextField f_text;
	public static JTextField num_text;
	public static JTextField L1_text;
	public static JTextField L2_text;
	public static JTextField pdop_text;
	public static JTextField hdop_text;
	public static JTextField vrms_text;
	public static JTextField hrms_text;
	public static JTextField hZ_text;
	public static JTextField dC_text;
	public static JTextField dD_text;
	public static JTextField speed_text;
	public static JTextField import_text;
	public static JLabel shine;
	public static Icon grey;
	public static Icon green;
	public static Icon icon5;
	public static String format;
	public static JLabel rember;
	public static JLabel gps;
	public static JTextField bjTime_text;
	public static JTextField ldTime_text;
	public static JTextField jiaSuDu_text;
	Font font2 = new Font("黑体", Font.PLAIN, 19);
	Font font3 = new Font("黑体", Font.ROMAN_BASELINE, 17);
	Font font4 = new Font("Arial", Font.ROMAN_BASELINE, 18);
	Font defaultfont_ch = new Font("黑体", Font.ROMAN_BASELINE, 14);
	Font defaultfont_en = new Font("Arial", Font.ROMAN_BASELINE, 16);
	public static boolean light1 = false;
	public static boolean light2 = false;
	public static boolean light3 = false;

	public Gps() {
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
		bool = false;
		init();
	}

	/**
	 * 初始化
	 */

	public void init() {
		this.setUndecorated(true);// 去边框处理
		this.setLayout(null);
		this.setSize(1024, 768);
		this.setAlwaysOnTop(true);// 总在最前面
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		Container mainContainer = this.getContentPane();

		// 标题标签
		topLabel = new JLabel(" GPS多区域联合仿真样机-GPS multi-area joint simulation prototype");
		topLabel.setOpaque(true);// 设置不透明
		topLabel.setBounds(0, 0, mainContainer.getWidth(), 20);
		topLabel.setBackground(Color.GRAY);
		topLabel.setForeground(Color.white);
		mainContainer.add(topLabel);

		// 菜单栏
		JMenuBar menuBar = addMenuBar();
		menuBar.setBounds(0, 21, mainContainer.getWidth(), 62);
		mainContainer.add(menuBar);
		addListener();

		// 信息显示区（左侧）
		leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setBounds(5, 85, 460, 680);
		leftPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mainContainer.add(leftPanel);

		/**
		 * 添加chart显示
		 */
		JPanel XYZ_Series = RealTimeChart_pre.createPanel_XYZ();
		XYZ_Series.setBounds(5, 5, 350, 20);
		XYZ_Series.setVisible(true);
		XYZ_Series.setSize(350, 20);
		leftPanel.add(XYZ_Series);

		// 操控区（右侧）
		rightPanel = new JPanel(null);
		rightPanel.setBounds(479, 83, 538, 768);
		rightPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mainContainer.add(rightPanel);

		JLabel titleJLabel = new JLabel("GPS多区域联合仿真样机");
		titleJLabel.setBounds(30, 8, 460, 60);
		titleJLabel.setFont(new Font("黑体", Font.ROMAN_BASELINE, 35));
		rightPanel.add(titleJLabel);

		JLabel titJLabel = new JLabel("GPS multi-area joint simulation prototype");
		titJLabel.setBounds(30, 60, 460, 30);
		titJLabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 23));
		rightPanel.add(titJLabel);

		JLabel iconLabel5 = new JLabel();
		iconLabel5.setBounds(480, 20, 60, 60);
		icon5 = new ImageIcon("./image/GPS1.png");
		iconLabel5.setIcon(icon5);
		rightPanel.add(iconLabel5);

		JPanel timeJPanel = new JPanel();
		timeJPanel.setBackground(Color.DARK_GRAY);
		timeJPanel.setBounds(20, 98, 510, 80);
		timeJPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		rightPanel.add(timeJPanel);
		grey = new ImageIcon("./image/grey.png");
		green = new ImageIcon("./image/green.png");
		shine = new JLabel();
		shine.setBounds(50, 15, 35, 35);
		shine.setIcon(grey);
		shine.setOpaque(false);
		timeJPanel.add(shine);
		JLabel shineText = new JLabel("GPS");
		shineText.setBounds(50, 40, 40, 40);
		shineText.setForeground(Color.WHITE);
		shineText.setFont(font3);
		shineText.setName("shineText");
		timeJPanel.add(shineText);

		// 点亮
		rember = new JLabel();
		rember.setBounds(123, 15, 35, 35);
		rember.setIcon(grey);
		rember.setOpaque(false);
		timeJPanel.add(rember);
		JLabel remberText = new JLabel("记录");
		remberText.setBounds(120, 40, 40, 40);
		remberText.setForeground(Color.WHITE);
		remberText.setFont(font3);
		timeJPanel.add(remberText);

		// 点亮
		gps = new JLabel();
		gps.setBounds(220, 15, 35, 35);
		gps.setIcon(grey);
		gps.setOpaque(false);
		timeJPanel.add(gps);
		JLabel gpsText = new JLabel("接收信号");
		gpsText.setBounds(200, 40, 80, 40);
		gpsText.setForeground(Color.WHITE);
		gpsText.setFont(font3);
		timeJPanel.add(gpsText);

		timeJPanel.setLayout(null);
		timeField = new JTextField("00:00:00.000");
		timeField.setBounds(300, 10, 195, 65);
		timeField.setBackground(Color.black);
		timeField.setForeground(Color.white);
		timeField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		timeField.setFont(new Font("黑体", Font.ROMAN_BASELINE, 31));
		timeField.setFocusable(false);
		timeJPanel.add(timeField);
		setTime();

		// 显示操作记录

		showField = new JTextArea();
		showField.setBounds(3, 3, 515, 123);
		showField.setFont(font3);
		showJScrollPane = new JScrollPane(showField);
		showJScrollPane.setBounds(15, 183, 520, 150);
		showJScrollPane.setBorder(null);
		showJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		showJScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(
				new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "操作记录"));
		rightPanel.add(showJScrollPane);

		// 显示信息窗口
		JTabbedPane showParam = new JTabbedPane();
		showParam.setBounds(15, 330, 530, 352);
		showParam.setBorder(BorderFactory.createEtchedBorder()); // 设置边框
		rightPanel.add(showParam);

		// 放入航迹图的位置
		leftJLabel = new JLabel();
		leftJLabel.setBounds(2, 0, 476, 485);
		leftJLabel.setSize(506, 301);
		leftJLabel.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("./image/03.png");
		icon.setImage(icon.getImage().getScaledInstance(330, 300, 1));

		// hangjituLabel.setOpaque(false);
		hangjituLabel.setBackground(Color.GRAY);
		hangjituLabel.setIcon(icon);
		hangjituLabel.setPreferredSize(new Dimension(350, 320));
		// leftJLabel.setIcon(icon);
		leftJLabel.setOpaque(true);
		leftJLabel.add(hangjituLabel, BorderLayout.WEST);
		leftJLabel.setOpaque(false);
		leftJLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		showParam.add("航迹图", leftJLabel);
		showParam.setTitleAt(0, "航迹图");
		// 放入航迹图右侧参数面板
		hangjituEast hangjituEast_instance = new hangjituEast();
		leftJLabel.add(hangjituEast_instance, BorderLayout.CENTER);

		/**
		 * local1 local2为两个点的坐标
		 */
		/*
		 * ImageIcon radarImage3 = new
		 * ImageIcon("./image/b79a3ff4f2d03b07609293a162706ba4.png");
		 * radarImage3.setImage(radarImage3.getImage().getScaledInstance(15, 15,
		 * Image.SCALE_DEFAULT)); local1 = new JLabel(radarImage3); x1 = 155; x2 = 0; y1
		 * = 140; y2 = 0; local1.setBounds(x1, y1, 15, 15); local1.setOpaque(false);
		 * local1.setBackground(Color.BLACK);
		 * 
		 * hangjituLabel.add(local1); local2 = new JLabel(radarImage3);
		 * local2.setBounds(x2, y2, 15, 15); local2.setOpaque(false);
		 * local2.setBackground(Color.BLACK); hangjituLabel.add(local2);
		 */
		PointThread pointThread = new PointThread();
		new Thread(pointThread).start();

		// GPS暂止信息
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		p1.setLayout(null);
		p2.setLayout(null);
		p3.setLayout(null);
		p1.setBackground(Color.LIGHT_GRAY);
		p2.setBackground(Color.LIGHT_GRAY);
		p3.setBackground(Color.LIGHT_GRAY);
		showParam.addTab("p1", p1);
		showParam.setEnabledAt(0, true);
		showParam.setTitleAt(1, "GPS");
		showParam.setFont(defaultfont_en);

		JPanel param1 = new JPanel();
		param1.setLayout(null);
		param1.setBorder(BorderFactory.createTitledBorder(null, "站址信息", TitledBorder.LEFT, TitledBorder.TOP, font2,
				Color.white)); // 设置边框
		param1.setBackground(Color.darkGray);
		param1.setBounds(5, 5, 510, 300);
		p1.add(param1);

		JLabel ID = new JLabel("基 站ID：");
		ID.setBounds(20, 30, 100, 30);
		ID.setFont(font3);
		ID.setForeground(Color.WHITE);
		param1.add(ID);
		ID_text = new JTextField();
		ID_text.setBackground(Color.white);
		ID_text.setBounds(120, 30, 115, 30);
		ID_text.setFont(font3);
		ID_text.setText("201800001");
		ID_text.setName("ID_text");
		param1.add(ID_text);
		ID_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(ID_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"baseID\":\"" + ID_text.getText());
					map.put("modeljson", "{" + "\"" + "baseID" + "\"" + ":" + "\"" + ID_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		JLabel A = new JLabel("经   度：");
		A.setBounds(270, 30, 100, 30);
		A.setFont(font3);
		A.setForeground(Color.WHITE);
		param1.add(A);
		A_text = new JTextField();
		A_text.setBackground(Color.white);
		A_text.setBounds(370, 30, 115, 30);
		A_text.setFont(font3);
		A_text.setText("109.14167568");
		A_text.setName("A_text");
		param1.add(A_text);

		A_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(A_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"latitude\":\"" + A_text.getText());
					map.put("modeljson", "{" + "\"" + "latitude" + "\"" + ":" + "\"" + A_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		JLabel R = new JLabel("纬   度：");
		R.setBounds(20, 80, 100, 30);
		R.setFont(font3);
		R.setForeground(Color.WHITE);
		param1.add(R);
		R_text = new JTextField();
		R_text.setBackground(Color.white);
		R_text.setFont(font3);
		R_text.setBounds(120, 80, 115, 30);
		R_text.setText("34.38455812");
		R_text.setName("R_text");
		param1.add(R_text);

		R_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(R_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"longitude\":\"" + R_text.getText());
					map.put("modeljson", "{" + "\"" + "longitude" + "\"" + ":" + "\"" + R_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		JLabel E = new JLabel("椭 球 高：");
		E.setBounds(270, 80, 100, 30);
		E.setFont(font3);
		E.setForeground(Color.white);
		param1.add(E);
		E_text = new JTextField();
		E_text.setBackground(Color.white);
		E_text.setFont(font3);
		E_text.setBounds(370, 80, 115, 30);
		E_text.setText("359.063      m");
		E_text.setName("E_text");
		param1.add(E_text);

		E_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(E_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"ellipsoid\":\"" + E_text.getText());
					map.put("modeljson", "{" + "\"" + "ellipsoid" + "\"" + ":" + "\"" + E_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel f = new JLabel("坐标x(m)：");
		f.setBounds(20, 130, 100, 30);
		f.setFont(font3);
		f.setForeground(Color.white);
		param1.add(f);
		f_text = new JTextField();
		f_text.setBackground(Color.white);
		f_text.setFont(font3);
		f_text.setBounds(120, 130, 115, 30);
		f_text.setText("3833.566");
		f_text.setName("f_text");
		param1.add(f_text);

		f_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(f_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"x\":\"" + f_text.getText());
					map.put("modeljson", "{" + "\"" + "x" + "\"" + ":" + "\"" + f_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		R_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(R_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"angle\":\"" + R_text.getText() +
					// "\",\"accuracy\":\"5.0\"}");
					map.put("modeljson", "{" + "\"" + "angle" + "\"" + ":" + "\"" + R_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);

				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		JLabel status = new JLabel("坐标y(m)：");
		status.setBounds(270, 130, 100, 30);
		status.setFont(font3);
		status.setForeground(Color.white);
		param1.add(status);
		status_text = new JTextField();
		status_text.setBackground(Color.white);
		status_text.setFont(font3);
		status_text.setText("4791.230");
		status_text.setBounds(370, 130, 115, 30);
		status_text.setName("status_text");
		param1.add(status_text);

		status_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(status_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"y\":\"" + status_text.getText());
					map.put("modeljson", "{" + "\"" + "y" + "\"" + ":" + "\"" + status_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel E2 = new JLabel("椭 球 高：");
		E2.setBounds(270, 80, 100, 30);
		E2.setFont(font3);
		E2.setForeground(Color.white);
		param1.add(E2);
		E_text = new JTextField();
		E_text.setBackground(Color.white);
		E_text.setFont(font3);
		E_text.setBounds(370, 80, 115, 30);
		E_text.setText("359.063      m");
		param1.add(E_text);

		JLabel B = new JLabel("解算状态：");
		B.setBounds(20, 180, 100, 30);
		B.setFont(font3);
		B.setForeground(Color.white);
		param1.add(B);
		B_text = new JTextField();
		B_text.setBackground(Color.white);
		B_text.setFont(font3);
		B_text.setBounds(120, 180, 115, 30);
		B_text.setText("单点解");
		B_text.setName("B_text");
		param1.add(B_text);

		B_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(B_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"status\":\"" + B_text.getText());
					map.put("modeljson", "{" + "\"" + "status" + "\"" + ":" + "\"" + B_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel C = new JLabel("差分模式：");
		C.setBounds(270, 180, 100, 30);
		C.setFont(font3);
		C.setForeground(Color.white);
		param1.add(C);
		C_text = new JTextField();
		C_text.setBackground(Color.white);
		C_text.setFont(font3);
		C_text.setBounds(370, 180, 115, 30);
		C_text.setText("RTCM3");
		C_text.setName("C_text");
		param1.add(C_text);

		C_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(C_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"model\":\"" + C_text.getText());
					map.put("modeljson", "{" + "\"" + "model" + "\"" + ":" + "\"" + C_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel D = new JLabel("差分延迟：");
		D.setBounds(20, 230, 100, 30);
		D.setFont(font3);
		D.setForeground(Color.white);
		param1.add(D);
		D_text = new JTextField();
		D_text.setBackground(Color.white);
		D_text.setFont(font3);
		D_text.setBounds(120, 230, 115, 30);
		D_text.setText("无差分信号");
		D_text.setName("D_text");
		param1.add(D_text);

		D_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(D_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"delay\":\"" + D_text.getText());
					map.put("modeljson", "{" + "\"" + "delay" + "\"" + ":" + "\"" + D_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

//卫星信息
		showParam.addTab("p2", p2);
		showParam.setEnabledAt(1, true);
		showParam.setTitleAt(2, "卫星信息");
		showParam.setFont(font3);

		JPanel param2 = new JPanel();
		param2.setLayout(null);
		param2.setBackground(Color.darkGray);
		param2.setBounds(5, 5, 510, 300);
		p2.add(param2);

		JLabel num = new JLabel("卫星个数: ");
		num.setBounds(10, 20, 100, 30);
		num.setFont(font3);
		num.setForeground(Color.WHITE);
		param2.add(num);
		num_text = new JTextField();
		num_text.setBackground(Color.white);
		num_text.setBounds(110, 20, 115, 30);
		num_text.setFont(font3);
		num_text.setText("14/15");
		num_text.setName("num_text");
		param2.add(num_text);

		num_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(num_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"satellite\":\"" + num_text.getText());
					map.put("modeljson",
							"{" + "\"" + "satellite" + "\"" + ":" + "\"" + num_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel L1 = new JLabel("L1信噪比:");
		L1.setBounds(260, 20, 100, 30);
		L1.setFont(font3);
		L1.setForeground(Color.WHITE);
		param2.add(L1);
		L1_text = new JTextField();
		L1_text.setBackground(Color.white);
		L1_text.setBounds(360, 20, 115, 30);
		L1_text.setFont(font3);
		L1_text.setText("S93   S106");
		L1_text.setName("L1_text");
		param2.add(L1_text);

		L1_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(L1_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"L1_SNR\":\"" + L1_text.getText());
					map.put("modeljson", "{" + "\"" + "L1_SNR" + "\"" + ":" + "\"" + L1_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel L2 = new JLabel("L2信噪比:");
		L2.setBounds(10, 70, 100, 30);
		L2.setFont(font3);
		L2.setForeground(Color.WHITE);
		param2.add(L2);
		L2_text = new JTextField();
		L2_text.setBackground(Color.white);
		L2_text.setBounds(110, 70, 115, 30);
		L2_text.setFont(font3);
		L2_text.setText("无");
		L2_text.setName("L2_text");
		param2.add(L2_text);

		L2_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(L2_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"L2_SNR\":\"" + L2_text.getText());
					map.put("modeljson", "{" + "\"" + "L2_SNR" + "\"" + ":" + "\"" + L2_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel pdop = new JLabel("   PDOP：");
		pdop.setBounds(260, 70, 100, 30);
		pdop.setFont(font3);
		pdop.setForeground(Color.WHITE);
		param2.add(pdop);
		pdop_text = new JTextField();
		pdop_text.setBackground(Color.white);
		pdop_text.setBounds(360, 70, 115, 30);
		pdop_text.setFont(font3);
		pdop_text.setText("3.00");
		pdop_text.setName("pdop_text");
		param2.add(pdop_text);

		pdop_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(pdop_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"PDOP\":\"" + pdop_text.getText());
					map.put("modeljson", "{" + "\"" + "PDOP" + "\"" + ":" + "\"" + pdop_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel hdop = new JLabel("   HDOP：");
		hdop.setBounds(10, 120, 100, 30);
		hdop.setFont(font3);
		hdop.setForeground(Color.WHITE);
		param2.add(hdop);
		hdop_text = new JTextField();
		hdop_text.setBackground(Color.white);
		hdop_text.setBounds(110, 120, 115, 30);
		hdop_text.setFont(font3);
		hdop_text.setText("2.40");
		hdop_text.setName("hdop_text");
		param2.add(hdop_text);

		hdop_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(hdop_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"HDOP\":\"" + hdop_text.getText());
					map.put("modeljson", "{" + "\"" + "HDOP" + "\"" + ":" + "\"" + hdop_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel vrms = new JLabel("   VRMS：");
		vrms.setBounds(260, 120, 100, 30);
		vrms.setFont(font3);
		vrms.setForeground(Color.WHITE);
		param2.add(vrms);
		vrms_text = new JTextField();
		vrms_text.setBackground(Color.white);
		vrms_text.setBounds(360, 120, 115, 30);
		vrms_text.setFont(font3);
		vrms_text.setText("4.7497");
		vrms_text.setName("vrms_text");
		param2.add(vrms_text);

		vrms_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(vrms_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"vrms_text\":\"" + vrms_text.getText());
					map.put("modeljson",
							"{" + "\"" + "vrms_text" + "\"" + ":" + "\"" + vrms_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel hrms = new JLabel("   HRMS：");
		hrms.setBounds(10, 170, 100, 30);
		hrms.setFont(font3);
		hrms.setForeground(Color.WHITE);
		param2.add(hrms);
		hrms_text = new JTextField();
		hrms_text.setBackground(Color.white);
		hrms_text.setBounds(110, 170, 115, 30);
		hrms_text.setFont(font3);
		hrms_text.setText("7.0099");
		hrms_text.setName("hrms_text");
		param2.add(hrms_text);

		hrms_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(hrms_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"HRMS\":\"" + hrms_text.getText());
					map.put("modeljson", "{" + "\"" + "HRMS" + "\"" + ":" + "\"" + hrms_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		// 基本信息
		showParam.addTab("p3", p3);
		showParam.setEnabledAt(2, true);
		showParam.setTitleAt(3, "基本信息");
		showParam.setFont(font3);

		JPanel param3 = new JPanel();
		param3.setLayout(null);
		param3.setBackground(Color.darkGray);
		param3.setBounds(5, 5, 510, 300);
		p3.add(param3);

		JLabel hZJLabel = new JLabel("天 线 高：");
		hZJLabel.setBounds(10, 20, 100, 30);
		hZJLabel.setForeground(Color.WHITE);
		hZJLabel.setFont(font3);
		param3.add(hZJLabel);
		hZ_text = new JTextField();
		hZ_text.setBackground(Color.white);
		hZ_text.setFont(font3);
		hZ_text.setBounds(105, 20, 115, 30);
		hZ_text.setText("0.091  m");
		hZ_text.setName("hZ_text");
		param3.add(hZ_text);

		hZ_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(hZ_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"antenna\":\"" + hZ_text.getText());
					map.put("modeljson", "{" + "\"" + "antenna" + "\"" + ":" + "\"" + hZ_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel dCJLabel = new JLabel("电池参数：");
		dCJLabel.setBounds(260, 70, 100, 30);
		dCJLabel.setForeground(Color.WHITE);
		dCJLabel.setFont(font3);
		param3.add(dCJLabel);
		dC_text = new JTextField();
		dC_text.setBackground(Color.white);
		dC_text.setFont(font3);
		dC_text.setBounds(350, 70, 50, 30);
		dC_text.setText("7.4V");
		dC_text.setName("dC_text");
		param3.add(dC_text);
		dD_text = new JTextField();
		dD_text.setBackground(Color.white);
		dD_text.setFont(font3);
		dD_text.setBounds(400, 70, 65, 30);
		dD_text.setText("1500mA");
		param3.add(dD_text);

		JLabel bjTimeJLabel = new JLabel("北京时间：");
		bjTimeJLabel.setBounds(10, 70, 100, 30);
		bjTimeJLabel.setForeground(Color.WHITE);
		bjTimeJLabel.setFont(font3);
		param3.add(bjTimeJLabel);
		bjTime_text = new JTextField();
		bjTime_text.setBackground(Color.white);
		bjTime_text.setFont(font3);
		bjTime_text.setBounds(105, 70, 115, 30);
		bjTime_text.setFocusable(false);
		bjTime_text.setName("bjTime_text");
		param3.add(bjTime_text);
		setTime();

		JLabel ldTimeJLabel = new JLabel("GPS时间：");
		ldTimeJLabel.setBounds(10, 120, 100, 30);
		ldTimeJLabel.setForeground(Color.WHITE);
		ldTimeJLabel.setFont(font3);
		param3.add(ldTimeJLabel);
		ldTime_text = new JTextField();
		ldTime_text.setBackground(Color.white);
		ldTime_text.setFont(font3);
		ldTime_text.setBounds(105, 120, 115, 30);
		ldTime_text.setFocusable(false);
		ldTime_text.setName("ldTime_text");
		param3.add(ldTime_text);
		setTime();

		JLabel speedJLabel = new JLabel("速   度：");
		speedJLabel.setBounds(260, 20, 80, 30);
		speedJLabel.setForeground(Color.WHITE);
		speedJLabel.setFont(font3);
		param3.add(speedJLabel);
		speed_text = new JTextField();
		speed_text.setBackground(Color.white);
		speed_text.setFont(font3);
		speed_text.setBounds(350, 20, 115, 30);
		speed_text.setText("1000 km/h");
		speed_text.setName("speed_text");
		param3.add(speed_text);

		speed_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(speed_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"velocity\":\"" + speed_text.getText());
					map.put("modeljson",
							"{" + "\"" + "velocity" + "\"" + ":" + "\"" + speed_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JLabel borderLabel6 = new JLabel("输出码型: ");
		borderLabel6.setBounds(260, 120, 100, 30);
		borderLabel6.setForeground(Color.white);
		borderLabel6.setFont(font3);
		param3.add(borderLabel6);
		import_text = new JTextField();
		import_text.setBackground(Color.white);
		import_text.setBounds(350, 120, 115, 30);
		import_text.setText("GPFSV");
		import_text.setFont(font3);
		import_text.setName("import_text");
		param3.add(import_text);

		JLabel jiaSuDu = new JLabel("加速度:");
		jiaSuDu.setBounds(10, 170, 80, 30);
		jiaSuDu.setForeground(Color.WHITE);
		jiaSuDu.setFont(font3);
		param3.add(jiaSuDu);
		jiaSuDu_text = new JTextField();
		jiaSuDu_text.setBackground(Color.white);
		jiaSuDu_text.setFont(font3);
		jiaSuDu_text.setBounds(105, 170, 115, 30);
		jiaSuDu_text.setText("1000 m/s2");
		jiaSuDu_text.setName("speed_text");
		param3.add(jiaSuDu_text);

		jiaSuDu_text.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(speed_text.getText());
					Map<String, String> map = new HashMap<String, String>();
					map.put("ID", "Gps_DG2019001");
					// map.put("modeljson", "{\"velocity\":\"" + speed_text.getText());
					map.put("modeljson",
							"{" + "\"" + "jiaSuDu" + "\"" + ":" + "\"" + jiaSuDu_text.getText() + "\"" + "}");
					map.put("name", "Gps");
					MessageUtil.sendToTena(map);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		// 重绘
		this.repaint();

		// socket发送
		// 启动socket线程
		SocketClient socketClient = new SocketClient();
		new Thread(socketClient).start();
	}

	/**
	 * 创建菜单栏
	 * 
	 * @return
	 */
	private JMenuBar addMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setLayout(null);
		startButton = createBtn("打开GPS", "./image/start.png");
		startButton.setBorder(BorderFactory.createRaisedBevelBorder());
		startButton.setFont(defaultfont_ch);
		stopButton = createBtn("关闭GPS", "./image/stop.png");
		stopButton.setBorder(BorderFactory.createRaisedBevelBorder());
		stopButton.setFont(defaultfont_ch);
		fileButton = createBtn("导入数据", "./image/file.png");
		fileButton.setBorder(BorderFactory.createRaisedBevelBorder());
		fileButton.setFont(defaultfont_ch);
		aboutButton = createBtn("记录数据", "./image/about.png");
		aboutButton.setBorder(BorderFactory.createRaisedBevelBorder());
		aboutButton.setFont(defaultfont_ch);
		pcmButton = createBtn("接收开", "./image/pcm.png");
		pcmButton.setBorder(BorderFactory.createRaisedBevelBorder());
		pcmButton.setFont(defaultfont_ch);
		gpsButton = createBtn("接收关", "./image/receviedstop1.png");
		gpsButton.setBorder(BorderFactory.createRaisedBevelBorder());
		gpsButton.setFont(defaultfont_ch);
		exitButton = createBtn("退出", "./image/exit.png");
		exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
		exitButton.setFont(defaultfont_ch);
		startButton.setBounds(0, 2, 60, 57);
		stopButton.setBounds(61, 2, 60, 57);
		fileButton.setBounds(121, 2, 60, 57);
		aboutButton.setBounds(181, 2, 60, 57);
		pcmButton.setBounds(241, 2, 60, 57);
		gpsButton.setBounds(301, 2, 60, 57);
		exitButton.setBounds(361, 2, 60, 57);
		// 增加按钮
		menuBar.add(startButton);
		menuBar.add(stopButton);
		menuBar.add(fileButton);
		menuBar.add(aboutButton);
		menuBar.add(exitButton);
		menuBar.add(pcmButton);
		menuBar.add(gpsButton);

		return menuBar;
	}

	/**
	 * 设置时间
	 */
	public void setTime() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

			@Override
			public void run() {
				format = dateFormatter.format(new Date());
				if (bool) {
					timeField.setText(format);
					bjTime_text.setText(format);
					ldTime_text.setText(getFormatedDateString(0));
				}
			}
		}, 0, 1);
	}

	/**
	 * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * 
	 * @param timeZoneOffset
	 * @return
	 */
	public static String getFormatedDateString(float timeZoneOffset) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}

		int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		sdf.setTimeZone(timeZone);
		return sdf.format(new Date());
	}

	/**
	 * 创建按钮
	 * 
	 * @param text
	 * @param icon
	 * @return
	 */
	private JButton createBtn(String text, String icon) {
		ImageIcon imageIcon = new ImageIcon(icon);
		JButton btn = new JButton(text, imageIcon);
		btn.setUI(new BasicButtonUI());// 恢复基本视觉效果
		btn.setPreferredSize(new Dimension(80, 27));// 设置按钮大小
		btn.setContentAreaFilled(false);// 设置按钮透明
		btn.setFont(new Font("粗体", Font.PLAIN, 10));// 按钮文本样式
		btn.setMargin(new Insets(0, 0, 0, 0));// 按钮内容与边框距离
		btn.setVerticalTextPosition(JButton.BOTTOM);
		btn.setHorizontalTextPosition(JButton.CENTER);
		return btn;
	}

	/**
	 * 设置皮肤
	 */
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加监听
	 */
	private void addListener() {
		// 打开GPS监听
		startButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				startButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				startButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (bool = true) {
					shine.setIcon(green);
					showField.append("  " + format + "启动GPS" + "\r\n");
					try {
						// 打开状态1指示灯
						if (light1 == false) {
							USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
							uSB2XXXUSBDeviceOpenLight1.openLight1();
							light1 = true;
						}
					} catch (RuntimeException e1) {
						// TODO Auto-generated catch block
						showField.append("\n指示灯打开失败!");
						// e1.printStackTrace();
					}
				}

			}
		});
		stopButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				stopButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				stopButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			public void mouseClicked(MouseEvent e) {
				bool = false;
				shine.setIcon(grey);
//					rember.setIcon(grey);
				showField.append("  " + format + "关闭GPS" + "\r\n");
				// 关闭状态1指示灯
				if (light1 == true) {
					USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
					uSB2XXXUSBDeviceOpenLight1.openLight1();
					light1 = false;
				}
			}
		});
		// 导入数据按钮监听
		fileButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				fileButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				fileButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				importDataFromOUT(); // 导入按钮相应
				if (light2 == false) {
					USB2XXXUSBDeviceOpenLight2 uSB2XXXUSBDeviceOpenLight2 = new USB2XXXUSBDeviceOpenLight2();
					uSB2XXXUSBDeviceOpenLight2.openLight2();
					light2 = true;
				}
			}

			// 导入按钮响应
			public void importDataFromOUT() {
				MyJDialog dialog = new MyJDialog(Gps.this);
				dialog.setVisible(true); // 使MyJDialog窗体可见
				showField.append("  " + format + "导入数据文件" + dialog.getFileAdress() + "\r\n");
				rember.setIcon(green);
				Properties pro = new Properties();
				try {
					pro.load(new InputStreamReader(new FileInputStream(dialog.getFileAdress()), "UTF-8"));
					System.out.println("out");
					String baseID = pro.getProperty("baseID");
					System.out.print("名字是" + baseID);
					String latitude = pro.getProperty("latitude");
					String longitude = pro.getProperty("longitude"); // 经度
					String ellipsoid = pro.getProperty("ellipsoid");
					String x = pro.getProperty("x"); // 坐标x
					String y = pro.getProperty("y"); // 坐标y
					String statue = pro.getProperty("statue"); // 解算状态
					String model = pro.getProperty("model"); // 差分模式
					String delay = pro.getProperty("delay"); // 差分延迟

					String satellite = pro.getProperty("satellite");// 卫星个数
					String L1_SNR = pro.getProperty("L1_SNR"); // L1信噪比
					String L2_SNR = pro.getProperty("L2_SNR"); // L2信噪比
					String PDOP = pro.getProperty("PDOP");
					String HDOP = pro.getProperty("HDOP");
					String VRMS = pro.getProperty("VRMS");

					String antenna = pro.getProperty("antenna"); // 天线高
					String velocity = pro.getProperty("velocity"); // 速度

					ID_text.setText(baseID);
					A_text.setText(latitude);
					R_text.setText(longitude);
					E_text.setText(ellipsoid);
					f_text.setText(x);
					status_text.setText(y);
					B_text.setText(statue);
					C_text.setText(model);
					D_text.setText(delay);

					num_text.setText(satellite);
					L1_text.setText(L1_SNR);
					L2_text.setText(L2_SNR);
					pdop_text.setText(PDOP);
					hdop_text.setText(HDOP);
					vrms_text.setText(VRMS);
					hZ_text.setText(antenna);
					speed_text.setText(velocity);
				} catch (Exception e1) {
					// TODO: handle exception
				}
			}
		});

		// 记录按钮监听
		aboutButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				aboutButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				aboutButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (bool = true) {
					rember.setIcon(grey);
					MyJDialog2 dialog = new MyJDialog2(Gps.this);
					dialog.setVisible(true); // 使MyJDialog窗体可见
					showField.append("  " + format + "开始记录GPS信息" + "\r\n");
					Properties prop = new Properties();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(dialog.getFileAdress()));
						// 设置文本内容
						prop.setProperty("baseID", ID_text.getText());
						prop.setProperty("latitude", A_text.getText());
						prop.setProperty("longitude", R_text.getText());
						prop.setProperty("ellipsoid", E_text.getText());
						prop.setProperty("x", f_text.getText());
						prop.setProperty("y", status_text.getText());
						prop.setProperty("statue", B_text.getText());
						prop.setProperty("model", C_text.getText());
						prop.setProperty("delay", D_text.getText());
						prop.setProperty("satellite", num_text.getText());
						prop.setProperty("L1_SNR", L1_text.getText());
						prop.setProperty("L2_SNR", L2_text.getText());
						prop.setProperty("PDOP", pdop_text.getText());
						prop.setProperty("HDOP", hdop_text.getText());
						prop.setProperty("VRMS", vrms_text.getText());
						prop.setProperty("HRMS", hrms_text.getText());
						prop.setProperty("antenna", hZ_text.getText());
						prop.setProperty("velocity", speed_text.getText());
						prop.store(bw, dialog.getFileAdress());
						String a = prop.toString();
						bw.write(a);
						if (light2 == true) {
							USB2XXXUSBDeviceOpenLight2 uSB2XXXUSBDeviceOpenLight2 = new USB2XXXUSBDeviceOpenLight2();
							uSB2XXXUSBDeviceOpenLight2.openLight2();
							light2 = false;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		exitButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				exitButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				MessageUtil.closeMM2Tena();
				if (light1 == true) {
					USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
					uSB2XXXUSBDeviceOpenLight1.openLight1();
					light1=false;
				}
				if (light2 == true) {
					USB2XXXUSBDeviceOpenLight2 uSB2XXXUSBDeviceOpenLight2 = new USB2XXXUSBDeviceOpenLight2();
					uSB2XXXUSBDeviceOpenLight2.openLight2();
					light2 = false;
				}
				if (light3 == true) {
					USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
					uSB2XXXUSBDeviceOpenLight3.openLight3();
					light3 = false;
				}
				System.exit(0);
			}
		});

		// 接收信号按钮监听
		pcmButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				pcmButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				pcmButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (bool = true) {
					gps.setIcon(green);
					showField.append("  " + format + "打开接收信号" + "\r\n");
					if (light3 == false) {
						USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
						uSB2XXXUSBDeviceOpenLight3.openLight3();
						light3 = true;
					}
				}
			}
		});
		// 关闭接收信号
		gpsButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出
				gpsButton.setBorder(BorderFactory.createRaisedBevelBorder());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移进时
				gpsButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (bool = true) {
					gps.setIcon(grey);
					showField.append("  " + format + "关闭接收信号" + "\r\n");
					if (light3 == true) {
						USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
						uSB2XXXUSBDeviceOpenLight3.openLight3();
						light3 = false;
					}

				}
			}
		});
	}

	public static void main(String[] args) {
		IMessageExecutor executor = new Gps().new MMCustomer();
		MessageUtil.addTena2MMMessageExecutor(executor);
		MessageUtil.addMM2TenaMessageExecutor(executor);
	}

	/**
	 * 监听消息代码段
	 * 
	 * @author 47649
	 *
	 */
	class MMCustomer implements IMessageExecutor {

		private MMCustomer() {
			// TODO Auto-generated method stub

		}

		
		@Override
		public void execute(Map<String, String> map) {
			// TODO Auto-generated method stub
			System.out.println(map);
			
			if(map.get("ID").equals("Gps_DG2019001")) {
				long sendTime = Long.parseLong(map.get("sendTime"));
				// 消息发送到接收的时间间隔
				long interval = MessageUtil.getCurrentTimeMills() - sendTime;
				Map<String, String> thirdMap = new HashMap<String, String>();
				thirdMap.put("id", "Gps_DG2019001");
				thirdMap.put("name", "Gps");
				thirdMap.put("interval", "" + interval);
				thirdMap.put("direction", map.get("direction"));
				try {
					MessageUtil.sendToShowInterval(thirdMap);
				} catch (Exception e) {
				 // 发送消息失败，吞掉异常，继续执行仿真设备或Tena的消息处理
					//log.error("给显示时间间隔软件发送消息失败.{}", e.getMessage());
					System.out.println("发送时延回执消息失败");
				}

			}
			
			// TODO Auto-generated method stub
			// 1.如果不是动作类型 即参数类型
			if (!map.containsKey("type")) {
				String gpsId = map.get("ID");
				if ("Gps_DG2019001".equals(gpsId)) {
					System.out.println("这是map:" + map);
					JSONObject jsonObject = new JSONObject(map.get("modeljson"));
					Iterator iterator = jsonObject.keys();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						String value = jsonObject.getString(key);
						switch (key) {
						case "baseID": {
							ID_text.setText(value);
							break;
						}
						case "latitude": {
							A_text.setText(value);
							break;
						}
						case "longitude": {
							R_text.setText(value);
							break;
						}
						case "y": {
							status_text.setText(value);
							break;
						}
						case "ellipsoid": {
							E_text.setText(value);
							break;
						}
						case "statue": {
							B_text.setText(value);
							break;
						}
						case "model": {
							C_text.setText(value);
							break;
						}
						case "delay": {
							D_text.setText(value);
							break;
						}
						case "x": {
							f_text.setText(value);
							break;
						}
						case "satellite": {
							num_text.setText(value);
							break;
						}
						case "L1_SNR": {
							L1_text.setText(value);
							break;
						}
						case "L2_SNR": {
							L2_text.setText(value);
							break;
						}
						case "PDOP": {
							pdop_text.setText(value);
							break;
						}
						case "HDOP": {
							hdop_text.setText(value);
							break;
						}
						case "VRMS": {
							vrms_text.setText(value);
							break;
						}
						case "HRMS": {
							hrms_text.setText(value);
							break;
						}
						case "antenna": {
							hZ_text.setText(value);
							break;
						}
						case "velocity": {
							speed_text.setText(value);
							break;
						}
						case "jiaSuDu": {
							jiaSuDu_text.setText(value);
						}
						}
					}
				} // if

			} // if mapType
			// 2.如果不是动作类型 即参数类型
			else {
				String gpsId = map.get("ID");
				if (gpsId.equals("Gps_DG2019001")) {
					String messageType = map.get("type");
					if (messageType.equals("Command")) {
						Gps.ChangeTextField(map);
					}
				}
			} // if mapType

		}

	}

	/**
	 * 用来响应动作和修改操作记录框的内容
	 * 
	 * @param map
	 */
	public static void ChangeTextField(Map<String, String> map) {
		// TODO Auto-generated method stub
		// 获取到的消息类型 是不是控制命令
		String typeString = map.get("type");
		String specValue = map.get("specValue");
		if (typeString.equals("Command") && specValue.equals("OpenMechine")) {
			if (bool == false) { // 如果当前Gps未打开
				showField.append("\n消息类型:命令消息" + "    " + "消息内容：打开GPS仿真机");
				shine.setIcon(green);
				showField.append("\n  " + format + "启动GPS" + "\r\n");
				try {
					// 打开状态1指示灯
					if (light1 == false) {
						USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
						uSB2XXXUSBDeviceOpenLight1.openLight1();
						light1 = true;
					}
				} catch (RuntimeException e1) {
					// TODO Auto-generated catch block
					showField.append("\n指示灯打开失败!");
					// e1.printStackTrace();
				}
			}
		}
		if (typeString.equals("Command") && specValue.equals("CloseMechine")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：关闭GPS仿真机");
			bool = false;
			shine.setIcon(grey);
//						rember.setIcon(grey);
			showField.append("  " + format + "关闭GPS" + "\r\n");
			// 关闭状态1指示灯
			if (light1 == true) {
				USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
				uSB2XXXUSBDeviceOpenLight1.openLight1();
				light1 = false;
			}
		}
		if (typeString.equals("Command") && specValue.equals("ImportData")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：导入数据");
			showField.append("\n请手动导入数据");
			fileButton.doClick();

		}
		if (typeString.equals("Command") && specValue.equals("RecordData")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：记录数据");
			showField.append("\n请手动记录数据");

			aboutButton.doClick();
		}
		if (typeString.equals("Command") && specValue.equals("ReceiveOpen")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：接收打开");
			gps.setIcon(green);
			showField.append("  " + format + "打开接收信号" + "\r\n");
			if (light3 == false) {
				USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
				uSB2XXXUSBDeviceOpenLight3.openLight3();
				light3 = true;
			}
		}
		if (typeString.equals("Command") && specValue.equals("ReceiveClose")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：接收关闭");
			gps.setIcon(grey);
			showField.append("  " + format + "关闭接收信号" + "\r\n");
			if (light3 == true) {
				USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
				uSB2XXXUSBDeviceOpenLight3.openLight3();
				light3 = false;
			}
		}
		if (typeString.equals("Command") && specValue.equals("ChooseObject1")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：选择目标1");

		}

		if (typeString.equals("Command") && specValue.equals("ChooseObject2")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：选择目标2");
			// startButton.doClick();
		}
		if (typeString.equals("Command") && specValue.equals("CloseGPS")) {
			showField.append("\n消息类型:命令消息" + "    " + "消息内容：关闭GPS仿真机");
			bool = false;
			shine.setIcon(grey);
//						rember.setIcon(grey);
			showField.append("  " + format + "关闭GPS" + "\r\n");
			// 关闭状态1指示灯
			if (light1 == true) {
				USB2XXXUSBDeviceOpenLight1 uSB2XXXUSBDeviceOpenLight1 = new USB2XXXUSBDeviceOpenLight1();
				uSB2XXXUSBDeviceOpenLight1.openLight1();
				light1=false;
			}
			if (light2 == true) {
				USB2XXXUSBDeviceOpenLight2 uSB2XXXUSBDeviceOpenLight2 = new USB2XXXUSBDeviceOpenLight2();
				uSB2XXXUSBDeviceOpenLight2.openLight2();
				light2 = false;
			}
			if (light3 == true) {
				USB2XXXUSBDeviceOpenLight3 uSB2XXXUSBDeviceOpenLight3 = new USB2XXXUSBDeviceOpenLight3();
				uSB2XXXUSBDeviceOpenLight3.openLight3();
				light3 = false;
			}

		}

	}

}

class MyJDialog extends JDialog {
	private String fileAdress;

	public MyJDialog(Gps frame) {
		super(frame, "导入数据", true);
		JDialog a = this;
		Container container = getContentPane();
		container.setLayout(null);
		// 添加选择按钮
		JButton jbu = new JButton("选择文件");
		jbu.setBounds(20, 20, 100, 30);
		container.add(jbu);
		JLabel fileName = new JLabel("请选择文件");
		fileName.setBounds(125, 20, 280, 30);
		jbu.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(jbu, "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println("文件夹:" + file.getAbsolutePath());
					fileAdress = file.getAbsolutePath();
					fileName.setText(file.getAbsolutePath());
				} else if (file.isFile()) {
					System.out.println("文件:" + file.getAbsolutePath());
					fileAdress = file.getAbsolutePath();
					fileName.setText(file.getAbsolutePath());
				}
				System.out.println(jfc.getSelectedFile().getName());
			}
		});

		JButton commit = new JButton("确定");
		commit.setBounds(150, 60, 80, 30);
		commit.setSize(80, 30);
		commit.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				a.dispose();
			}
		});
		JButton cancel = new JButton("取消");
		cancel.setBounds(240, 60, 80, 30);
		cancel.setSize(80, 30);
		cancel.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				a.dispose();
			}
		});
		container.add(fileName);
		container.add(commit);
		container.add(cancel);
		this.setBounds(200, 250, 400, 150);
		this.setSize(400, 150);
	}

	public String getFileAdress() {
		return this.fileAdress;
	}
}

class MyJDialog2 extends JDialog {
	private String fileAdress;
	private String file_Name;

	public MyJDialog2(Gps frame) {
		super(frame, "选择保存位置", true);
		JDialog a = this;
		Container container = getContentPane();
		container.setLayout(null);
		// 添加选择按钮
		JButton jbu = new JButton("选择位置");
		jbu.setBounds(20, 20, 100, 30);
		container.add(jbu);
		JLabel fileName = new JLabel("请选择位置");
		fileName.setBounds(125, 20, 280, 30);
		jbu.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(jbu, "选择");
				File file = jfc.getSelectedFile();
				fileName.setText(file.getAbsolutePath());
				fileAdress = file.getAbsolutePath();
				file_Name = file.getName();
			}
		});

		JButton commit = new JButton("保存");
		commit.setBounds(150, 60, 80, 30);
		commit.setSize(80, 30);
		commit.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				a.dispose();
			}
		});
		JButton cancel = new JButton("取消");
		cancel.setBounds(240, 60, 80, 30);
		cancel.setSize(80, 30);
		cancel.addActionListener(new ActionListener() { // 为按钮添加点击事件

			@Override
			public void actionPerformed(ActionEvent e) {
				a.dispose();
			}
		});
		container.add(fileName);
		container.add(commit);
		container.add(cancel);
		this.setBounds(200, 250, 400, 150);
		this.setSize(400, 150);
	}

	public String getFileAdress() {
		return this.fileAdress;
	}

	public String getFile_Name() {
		return this.file_Name;
	}

}
