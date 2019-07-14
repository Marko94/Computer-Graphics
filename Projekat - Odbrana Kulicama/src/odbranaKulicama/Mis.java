package odbranaKulicama;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//implementacija mouse listenera
public class Mis implements MouseListener, MouseMotionListener{

	private Prozor prozor;
	private Prozor.MouseHeld mouseHeld;
	
	public Mis(Prozor prozor) {
		this.prozor = prozor;
		this.mouseHeld = this.prozor.new MouseHeld();
	}
	
	public void mouseDragged(MouseEvent e) {	
		this.mouseHeld.mouseMoved(e);
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseHeld.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		this.mouseHeld.mouseDown(e);
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
