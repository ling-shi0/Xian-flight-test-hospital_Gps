package com.xiaobei.GPS;

import java.util.HashMap;
import java.util.Map;

public class Util {

	private static final int marginTop=235;
	private static final int marginleft=252;
	private static final int px=2;
	
	public static double getAngcle(double flag) {
		return flag*(Math.PI/180);
	}
	
	
	public static Map<String,Integer> getXY(double fangwei,double fuyang){
		
		Map<String,Integer> map = new HashMap<>();
		
		//第三象限
		if(fangwei>180 && fangwei<270) {
			double angcle = 270-fangwei;
			int z =  (int)fuyang*px;
			int _x = (int) (z*(Math.cos(getAngcle(angcle))));
			_x=_x<0?-_x:_x;
			int _y= (int) (z*(Math.sin(getAngcle(angcle))));
			_y=_y<0?-_y:_y;
			int x = marginleft-_x;
			int y = marginTop+_y;
			map.put("x", x);
			map.put("y", y);
		}
		//第二象限
		if(fangwei>90 && fangwei<180) {
			System.out.println("第二象限");
			double angcle = 180-fangwei;
			int z =  (int)fuyang*px;
			int _x = (int) (z*Math.sin(getAngcle(angcle)));
			int _y= (int) (z*Math.cos(getAngcle(angcle)));
			int x = marginleft+_x;
			int y = marginTop+_y;
			map.put("x", x);
			map.put("y", y);
		}
		//第一象限
		if(fangwei>0 && fangwei<90) {
			System.out.println("第一象限");
			double angcle = 180-fangwei;
			int z =  (int)fuyang*px;
			int _x = (int) (z*Math.sin(getAngcle(angcle)));
			int _y= (int) (z*Math.cos(getAngcle(angcle)));
			int x = marginleft+_x;
			int y = marginTop+_y;
			map.put("x", x);
			map.put("y", y);
		}
		//第四象限
		if(fangwei>270 && fangwei<360) {
			System.out.println("第四象限");
			double angcle = 360-fangwei;
			int z =  (int)fuyang*px;
			int _x = (int) (z*Math.sin(getAngcle(angcle)));
			int _y= (int) (z*Math.cos(getAngcle(angcle)));
			int x = marginleft-_x;
			int y = marginTop-_y;
			map.put("x", x);
			map.put("y", y);
		}
		//第四象限
		if(fangwei>90 && fangwei<180) {
			double angcle = 90-fangwei;
			int z =  (int)fuyang*px;
			int _x = (int) (z*Math.cos(angcle));
			int _y= (int) (z*Math.sin(angcle));
			int x = marginleft+_x;
			int y = marginTop-_y;
			map.put("x", x);
			map.put("y", y);
		}
		
		return map;
	}

}
