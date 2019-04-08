package com.xiaobei.GPS;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JToggleButton;





public class SocketClient implements Runnable{

	
	public static long flowNum;	//流量数值
	
    /**
     * Socket客户端
     */
	public SocketClient() {
	}
    public void SocketSendMes(Map<String,String> map) throws UnknownHostException, SocketException {
    	DatagramSocket s = new DatagramSocket();        
        byte[] bs =map.toString().getBytes(); 
        DatagramPacket dp = new DatagramPacket(bs, bs.length, InetAddress.getByName("192.168.1.13"), 8888);
        try {
            s.send(dp);
        } catch (IOException e) {
            System.out.println("发送失败： ");
            e.printStackTrace();
        }
        s.close();

    }

    
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
    	Map<String,String> mes=new HashMap<String,String>();

		while(true){

			//name
			String name = "GPS";
			mes.put("name", name);
			//ID
			String ID = "Gps_DG2019001";
			mes.put("ID", ID);
			
			//高度
			String high = hangjituEast.jTextField1.getText();
			mes.put("high", high);
			//经度
			String longitude = hangjituEast.jTextField2.getText();
			mes.put("longitude", longitude);
			//经度
			String latitude = hangjituEast.jTextField3.getText();
			mes.put("latitude", latitude);
			//基站ID
			String  baseID = Gps.ID_text.getText();
			mes.put("baseID", baseID);
			//椭球高
			String  ellipsoidHigh = Gps.E_text.getText();
			mes.put("ellipsoidHigh", ellipsoidHigh);
			//坐标X
			String  positionX = Gps.f_text.getText();
			mes.put("positionX", positionX);
			//坐标Y
			String  status_text = Gps.status_text.getText();
			mes.put("status_text", status_text);
			//解算状态
			String  calcuStatus = Gps.B_text.getText();
			mes.put("calcuStatus", calcuStatus);
			//差分模式
			String  differenceModle = Gps.C_text.getText();
			mes.put("differenceModle", differenceModle);
			//差分延迟
			String  differenceDelay = Gps.D_text.getText();
			mes.put("differenceDelay", differenceDelay);
			//卫星个数
			String  sataNum = Gps.num_text.getText();
			mes.put("sataNum", sataNum);
			//L1信噪比
			String  L1 = Gps.L1_text.getText();
			mes.put("L1", L1);
			//L2信噪比
			String  L2 = Gps.L2_text.getText();
			mes.put("L2", L2);
			//PDOP
			String  PDOP = Gps.pdop_text.getText();
			mes.put("PDOP", PDOP);
			//hdop
			String  hdop = Gps.hdop_text.getText();
			mes.put("hdop", hdop);
			//VRMS
			String  VRMS = Gps.vrms_text.getText();
			mes.put("VRMS", VRMS);
			//HRMS
			String  HRMS = Gps.hrms_text.getText();
			mes.put("HRMS", HRMS);
			//天线高
			String  antenna_high = Gps.hZ_text.getText();
			mes.put("antenna_high", antenna_high);
			//速度
			String  volicity = Gps.speed_text.getText();
			mes.put("volicity", volicity);
			//电池参数
			String  battery  = Gps.dC_text.getText();
			mes.put("battery", battery);
			//输出码型
			String  outputCodePatter  = Gps.import_text.getText();
			mes.put("outputCodePatter", outputCodePatter);
			
			
			

			try {
				new SocketClient().SocketSendMes(mes);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println("发送了当前时间的Gps被监控内容:" + new Date().toGMTString());
			
			//休息一会儿
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
