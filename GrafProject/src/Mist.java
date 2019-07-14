import java.awt.image.BufferedImage;

import rafgfxlib.Util;


public class Mist {
	public float x;
	public float y;
	public float dX=1;
	public BufferedImage slika= Util.loadImage("Mist.png");
	public Mist(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	public void update(ZombieSmash z){
		x+=dX;
	}
}
