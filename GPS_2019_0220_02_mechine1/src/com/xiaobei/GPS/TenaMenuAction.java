package com.xiaobei.GPS;

import actions.OperationAction;

public class TenaMenuAction extends OperationAction {

	public TenaMenuAction(Object part) {
		super(part);
	}
	
	public void openGps() {
		System.out.println("打开GPS");
	}
	public void closeGps() {
		System.out.println("关闭GPS");
	}
	public void receiveSignal() {
		System.out.println("接收信号");
	}
	public void closeSignal() {
		System.out.println("关闭信号");
	}
}
