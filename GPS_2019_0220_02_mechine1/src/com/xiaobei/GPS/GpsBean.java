package com.xiaobei.GPS;

import app.AbstractNodeBaseModel;

public class GpsBean extends AbstractNodeBaseModel{
	private String ID="GPS多区域联合靶场仿真样机_DG2019001";
	private int baseID=0; 
	private double latitude=0;
	private double longitude=0;
	private double ellipsoid=0;
	private double x=0; 
	private double y=0; 
	private String statue="";
	private String model=""; 
	private String delay=""; 
	private String D="";
	private String B="";
	private String L="";
	private String satellite="14/15";
	private String L1_SNR="S93 S106";
	private String L2_SNR="无"; 
	private String PDOP="3.0";
	private String HDOP="2.40";
	private String VRMS="4.7497";
	private String HRMS="7.0099";
	private String hangJi="正常";
	private String antenna="0.091";
	private String velocity="1000";
	
	
	public String getHangJi() {
		return hangJi;
	}
	public void setHangJi(String hangJi) {
		this.hangJi = hangJi;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public int getBaseID() {
		return baseID;
	}
	public void setBaseID(int baseID) {
		this.baseID = baseID;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getEllipsoid() {
		return ellipsoid;
	}
	public void setEllipsoid(double ellipsoid) {
		this.ellipsoid = ellipsoid;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getStatue() {
		return statue;
	}
	public void setStatue(String statue) {
		this.statue = statue;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getSatellite() {
		return satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	public String getL1_SNR() {
		return L1_SNR;
	}
	public void setL1_SNR(String l1_SNR) {
		L1_SNR = l1_SNR;
	}
	public String getL2_SNR() {
		return L2_SNR;
	}
	public void setL2_SNR(String l2_SNR) {
		L2_SNR = l2_SNR;
	}
	public String getPDOP() {
		return PDOP;
	}
	public void setPDOP(String pDOP) {
		PDOP = pDOP;
	}
	public String getHDOP() {
		return HDOP;
	}
	public void setHDOP(String hDOP) {
		HDOP = hDOP;
	}
	public String getVRMS() {
		return VRMS;
	}
	public void setVRMS(String vRMS) {
		VRMS = vRMS;
	}
	public String getHRMS() {
		return HRMS;
	}
	public void setHRMS(String hRMS) {
		HRMS = hRMS;
	}
	public String getAntenna() {
		return antenna;
	}
	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}
	public String getVelocity() {
		return velocity;
	}
	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}
	
	
}
