import java.awt.image.BufferedImage;

import rafgfxlib.Util;
import rafgfxlib.GameFrame.GFMouseButton;


public class Hero {

	public float x=400;
	public float y=300;
	public float dX;
	public float dY;
	boolean fire = false;
	public BufferedImage slika= Util.loadImage("Hero.png");
	public int centerX = slika.getWidth() / 2;
	public int centerY = slika.getHeight() / 2;
	double angle=0; 
	
	
	public void update(ZombieSmash z){
		
		if (z.kp==87){
			dY=-5;
		}else if (z.kp==65){
			dX=-5;
		}else if (z.kp==68 ){
			dX=5;
		}else if (z.kp==83){
			dY=5;
		}if (z.kr==87){
			dY=0;
			z.kr=0;
			z.kp=0;
		}else if (z.kr==65){
			dX=0;
			z.kr=0;
			z.kp=0;
		}else if (z.kr==68 ){
			dX=0;
			z.kr=0;
			z.kp=0;
		}else if (z.kr==83){
			dY=0;
			z.kr=0;
			z.kp=0;
		}
		
		
		if(x>-10 && x<765)
		x+=dX;
		else if (x<=0) x++; 
			 else x--;
		
		if(y>-10 && y<580)
		y+=dY;
		else if (y<=0) y++; 
			 else y--;
		
		angle = Math.atan2(centerY+y - z.mY,x+centerX - z.mX) - Math.PI / 2;
	}
}
