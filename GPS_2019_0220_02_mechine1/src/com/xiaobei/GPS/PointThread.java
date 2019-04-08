package com.xiaobei.GPS;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PointThread implements Runnable {
	ImageIcon radarImage3 = new ImageIcon("./image/dian.png");
	int x1 = 109;
//	int x2 = 50;
	int y1 = 130;
//	int y2 = 80;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			// System.out.println("这是点轨迹线程");
			radarImage3.setImage(radarImage3.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
			Gps.local1 = new JLabel(radarImage3);
			// x1 = (x1) * 5;
			// y1 = ((int)Gps.y1) * 2;
			Gps.local1.setBounds(x1, y1, 15, 15);
			Gps.local1.setOpaque(false);
			Gps.local1.setBackground(Color.BLACK);

//			Gps.local2 = new JLabel(radarImage3);
//			Gps.local2.setBounds((int) Gps.x2, (int) Gps.y2, 15, 15);
//			Gps.local2.setOpaque(false);
//			Gps.local2.setBackground(Color.BLACK);

			Gps.hangjituLabel.add(Gps.local1);
//			Gps.hangjituLabel.add(Gps.local2);

			Gps.hangjituLabel.updateUI();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Gps.hangjituLabel.remove(Gps.local1);
			//Gps.hangjituLabel.remove(Gps.local2);
			
			if(x1<=165&&y1<=142) {
				x1=x1+1;
				y1=y1-1;
			}else if(x1<=165&&y1>=142){
				System.out.println("yyy");
				x1=x1-1;
				y1=y1-1;
			}else if(x1>=165&&y1<=142) {
				x1=x1+1;
				y1=y1+1;
			}else if(x1>=165&&y1>=142) {
				x1=x1-1;
				y1=y1+1;
			}
		}

	}

}
