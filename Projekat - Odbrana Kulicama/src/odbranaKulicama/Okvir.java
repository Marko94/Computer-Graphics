package odbranaKulicama;

import javax.swing.JFrame; 
import javax.swing.SwingUtilities;

import java.awt.Toolkit;

public class Okvir extends JFrame {
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Okvir();
			}
			
		});
		
	}
	
	public Okvir() {
		new JFrame(); 
		this.setTitle("Odbrana kulicama - Marko Nenadovic");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//podesimo prozor da bude preko celog ekrana 
		this.setSize(1024, 576);
		//this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		Prozor prozor = new Prozor(this);
		this.add(prozor);
	}
	//uskladjivanje prozora zbog crtanja
	private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  
	    int uskladjivanje = this.getWidth()/2;
	    int koordinata = this.getHeight()/2;
	    int x = (Toolkit.getDefaultToolkit().getScreenSize().width/2)-uskladjivanje;
	    int y = (Toolkit.getDefaultToolkit().getScreenSize().height/2)-koordinata;
	    this.setLocation(x, y);
	}
}
