import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.Util;


public class Blood {
	public float x;
	public float y;
	public float dX;
	public float dY;
	public int life=30;
	
	
	
	
	
	public Blood(float x, float y, boolean death, float dX2, float dY2) {
		super();
		int b=20;
		this.x = (int) x;
		this.y = (int) y;
		if (death) b= 30;
		
		Random r= new Random();
		dX= (r.nextInt(b) *(r.nextFloat()-0.5f)+4*dX2/Math.abs(dX2));
		dY= (r.nextInt(b) *(r.nextFloat()-0.5f)+4*dY2/Math.abs(dY2));
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
