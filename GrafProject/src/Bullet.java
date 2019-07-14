import java.awt.image.BufferedImage;

import rafgfxlib.Util;


public class Bullet {
	public float x;
	public float y;
	public float dX;
	public float dY;
	public float speed= 30;
	public BufferedImage slika= Util.loadImage("bullet.png");
	double angle=0; 
	public int centerX = slika.getWidth() / 2;
	public int centerY = slika.getHeight() / 2;
	
	
	public Bullet(float x, float y, float hX, float hY) {
		super();
		this.x = hX;
		this.y = hY;
		float xDistance = x - hX;
		float yDistance = y - hY;
		float distance = (int) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		dX=xDistance/(distance/speed);
		dY=yDistance/(distance/speed);
		angle = Math.atan2(centerY+hY - y,hX+centerX - x) - Math.PI / 2;
	}


	public void update(ZombieSmash z){
		y+=dY;
		x+=dX;
	}
}
