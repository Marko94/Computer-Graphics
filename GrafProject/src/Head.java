import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.Util;


public class Head {
		public float x;
		public float y;
		public float dX;
		public float dY;
		public int life=30;
		public BufferedImage slika= Util.loadImage("Head1.png");
		public int centerX = slika.getWidth() / 2;
		public int centerY = slika.getHeight() / 2;
		double angle=0; 
		
		
		
		
		
		public Head(float x, float y, int p, double angle) {
			super();
			slika = Util.loadImage("Head"+(p)+".png");
			this.x = (int) x;
			this.y = (int) y;
			this.angle=angle;
			dX= (float) (10* Math.sin(angle));
			dY= (float) (10* Math.cos(angle)*-1);
		}
		public void update(ZombieSmash z){
			if(dX>1) x+=dX--;
			else if(dX<0) x+=dX++;
			else dX=0;
			if(dY>1) y+=dY--;
			else if(dY<0) y+=dY++;
			else dY=0;
			life--;
			
		}
	}


