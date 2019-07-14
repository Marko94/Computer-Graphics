import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;


public class ZombieSmash extends GameFrame {
	int timer=0;
	boolean death = false;
	int kr,kp,bc=25;
	int mX,mY;
	int spawn=50;
	private static final int TILE_W = 64;
	private static final int TILE_H = 64;
	private int mapW = 20;
	private int mapH = 20;
	Hero h = new Hero();
	ArrayList<Zombie> zombies= new ArrayList<Zombie>();
	ArrayList<Blood> blood = new ArrayList<Blood>();
	ArrayList<Mist> mist= new ArrayList<Mist>();
	ArrayList<Head> heads = new ArrayList<Head>();
	
	public BufferedImage dark = Util.loadImage("darkness.png");
	boolean remove;
	int removeID;
	ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	protected ZombieSmash(String title, int sizeX, int sizeY) {
		super(title, sizeX, sizeY);
		this.setUpdateRate(30);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		   
		Image image = toolkit.getImage("Crosshair1.png");
		Point hotSpot = new Point(0,0); 
		Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "Crosshair1.png");
		setCursor(cursor); 
		
		for(int i = 0; i <= 31; ++i)
		{
			 tileset[i] = new Tile("Tajl" + i + ".jpg", i); 
		}
		
		Random rnd = new Random();
		for(int y = 0; y < mapH; ++y)
		{
			for(int x = 0; x < mapW; ++x)
			{
				tileMap[x][y] = Math.abs(rnd.nextInt()) % 32;
			}
		}
		
		startThread();
		
	}
	private class Tile
	{
		public BufferedImage image = null;
		public int offsetX = 0;
		public int offsetY = 0;
		@SuppressWarnings("unused")
		public int tileID = 0;
		
		public Tile(String fileName, int ID)
		{
			image = Util.loadImage(fileName);
			tileID = ID;
			if(image != null)
			{
				offsetX = 0;
				offsetY = -(image.getHeight() - TILE_H);
			}
			else
			{
				System.out.println("Fail at \"" + fileName + "\"");
			}
		}
	}
	
	private Tile[] tileset = new Tile[32];
	private int[][] tileMap = new int[mapW][mapH];


	@Override
	public void handleWindowInit() {
		
		
	}

	@Override
	public void handleWindowDestroy() {
		
		
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		//System.out.println(blood.size());
		AffineTransform identity = new AffineTransform();
		
		for(int y = 0; y <= 12; ++y)
		{
			for(int x = 0; x <= 12; ++x)
			{
				g.drawImage(tileset[tileMap[x][y]].image, 
						x * TILE_W + tileset[tileMap[x][y]].offsetX , 
						y * TILE_H + tileset[tileMap[x][y]].offsetY , 
						null);
			}
		}
		
		for(Bullet b : bullets){
			g.rotate(b.angle, b.x + b.centerX, b.y + b.centerY);
			g.drawImage(b.slika, (int)b.x,(int)b.y, null);
			//g.rotate(-b.angle, b.x + b.centerX, b.y + b.centerY);
			g.setTransform(identity);
		}
		
		g.rotate(h.angle, h.x + h.centerX, h.y + h.centerY);
		g.drawImage(h.slika, (int)h.x,(int)h.y, null);
		//g.rotate(-h.angle, h.x + h.centerX, h.y + h.centerY);
		g.setTransform(identity);
		for (Blood bl: blood){
			g.setColor(Color.RED);
			g.drawLine((int)(bl.x - bl.dX), (int)(bl.y - bl.dY), (int)bl.x, (int)bl.y);
		}
		for (Zombie z: zombies){
			g.rotate(z.angle, z.x + z.centerX, z.y + z.centerY);
			g.drawImage(z.slika, (int)z.x,(int)z.y, null);
			//g.rotate(-z.angle, z.x + h.centerX, z.y + z.centerY);
			g.setTransform(identity);
		}
		for (Head h: heads){
			g.rotate(h.angle+Math.PI, h.x + h.centerX, h.y + h.centerY);
			g.drawImage(h.slika, (int)h.x,(int)h.y, null);
			g.setTransform(identity);
		}
		g.rotate(h.angle, h.x + h.centerX, h.y + h.centerY);
		g.drawImage(dark, (int) (h.x-967), (int) (h.y-970), null);
		g.setTransform(identity);
		
	}

	@Override
	public void update() {
		timer++;
		h.update(this);
		//System.out.println(h.fire);
		
		if (timer%1000==0 && spawn>5){spawn-=5;}
		if (timer%spawn==0){
			Random r = new Random();
			int a= r.nextInt(1500);
			int b= r.nextInt(1100);
			if (a<780) a=a*-1;
			if (b<590) b=b*-1;
			zombies.add(new Zombie(a,b));
		}
		
		for(int i=0;i<blood.size();i++){
			blood.get(i).update(this);
			if (blood.get(i).life==0 ) {
				blood.remove(i);
			}
		}
		for(int i=0;i<heads.size();i++){
			heads.get(i).update(this);
			
			if (heads.get(i).life==0 ) {
				heads.remove(i);
			}
		}
		
		for(int i=0;i<zombies.size();i++){
			zombies.get(i).update(this);
			
			float zx = zombies.get(i).x;
			float zy = zombies.get(i).y;
			for (int j=0; j<bullets.size();j++){
				if (bullets.get(j).x>zx && bullets.get(j).x<zx+50 && bullets.get(j).y>zy && bullets.get(j).y<zy+50 ){
					zombies.get(i).hp--;
					if(zombies.get(i).hp == 2) {
						heads.add(new Head(zx+12,zy+19,zombies.get(i).p,bullets.get(j).angle));
					}
					else if(zombies.get(i).hp == 0) {
						zombies.get(i).slika= Util.loadImage("ZombieBezGlave"+ zombies.get(i).p + ".png") ;
						zombies.remove(i);
						bc=200;
						death=true;
					}else{ bc=50; death= false;}
					for (int k=0;k<bc;k++){
						blood.add(new Blood(bullets.get(j).x,bullets.get(j).y, death,bullets.get(j).dX,bullets.get(j).dY));
					} 
					 
					bullets.remove(j);
				}
			}
		}
		for(int i=0;i<bullets.size();i++){
			bullets.get(i).update(this);
			
			if (bullets.get(i).x<0 || bullets.get(i).x>800 || bullets.get(i).y<0 || bullets.get(i).y>600) {
				bullets.remove(i);
			}
		}
		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
	    
		bullets.add(new Bullet(x,y,h.x+h.centerX,h.y+h.centerY));
		h.fire=true;
		
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		
	}

	@Override
	public void handleMouseMove(int x, int y) {
		mX=x;
		mY=y;
		
	}
	

	@Override
	public void handleKeyDown(int keyCode) {
	
		
	}
	
	public void keyPressed(KeyEvent e){
		kp=e.getExtendedKeyCode();
		
	}
	public void keyReleased(KeyEvent e) {
		kr=e.getExtendedKeyCode();
	}
	@Override
	public void handleKeyUp(int keyCode) {
		
	}
	

}
