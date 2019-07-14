
import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.Util;
import rafgfxlib.GameFrame.GFMouseButton;


public class Zombie {

	public float x=400;
	public int hp=4;
	public float y=300;
	public float dX;
	public float dY;
	float speed=3;
	Random rand = new Random();
	int p=rand.nextInt(2) + 1;
	public BufferedImage slika = Util.loadImage("Zombie1.png");
	public int centerX = slika.getWidth() / 2;
	public int centerY = slika.getHeight() / 2;
	double angle=0; 
	
	public Zombie(float x, float y){
		super();
		this.x = x;
		this.y = y;
		slika = Util.loadImage("Zombie"+(p)+".png");
	}
	
	public void update(ZombieSmash z){
		if (hp==2){
			slika = Util.loadImage("ZombieBezGlave"+(p)+".png");
		}
		float xDistance = z.h.x - x ;
		float yDistance = z.h.y - y;
		float distance = (int) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		dX=xDistance/(distance/speed);
		dY=yDistance/(distance/speed);
		if (distance>20){
			x+=dX;
			y+=dY;
		}else{
			for (int i=0; i<20; i++) z.blood.add(new Blood(z.h.x + z.h.centerX, z.h.y + z.h.centerY, false, dX, dY));
				
		}
		angle = Math.atan2(centerY+y - (z.h.y+z.h.centerY),x+centerX - (z.h.x+z.h.centerX)) - Math.PI / 2;
	}
}
