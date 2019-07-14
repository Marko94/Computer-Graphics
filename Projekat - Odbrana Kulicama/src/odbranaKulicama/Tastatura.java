package odbranaKulicama;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//implementacija key listenera
public class Tastatura implements KeyListener{
	
	private Prozor prozor;
	private Prozor.KeyTyped keyTyped;
	
	public Tastatura(Prozor prozor){
		this.prozor = prozor;
		this.keyTyped = this.prozor.new KeyTyped();
	}
	
	public void keyPressed(KeyEvent e) {
		int kodDugmeta = e.getKeyCode();
		
		System.out.println(kodDugmeta);
		
		if(kodDugmeta == 27) {
			this.keyTyped.dugmeESC();
		}
		
		if(kodDugmeta == 32) {
			this.keyTyped.dugmeSPACE();
		}
		
		if(kodDugmeta == 10){
			this.keyTyped.dugmeENTER();
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	
}
