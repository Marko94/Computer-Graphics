package odbranaKulicama;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Napadac {
	
	public static final Napadac[] listaNapadaca = new Napadac[10];
	// inicijalizacija napadaca
	public static final Napadac ljigaZelena = new NapadacLjiga(0, 1, 1, 10, 1, 3.0, 50.0, 1.0).getTextureFile("NapadacLjiga");
	public static final Napadac ljigaZuta = new NapadacLjiga(1, 3, 3, 15, 2, 2.0, 50.0, 1.0).getTextureFile("NapadacLjiga2");
	public static final Napadac ljigaPlava = new NapadacLjiga(2, 5, 5, 20, 5, 1.0, 50.0, 1.0).getTextureFile("NapadacLjiga3");
	public static final Napadac ljigaCrvena = new NapadacLjiga(3, 10, 10, 25, 10, 1.0, 50.0, 1.0).getTextureFile("NapadacLjiga4");

	public String fajlTekstura = "";
	public Image tekstura = null;
	
	public int idNapadaca;
	public int nagrada;
	public int jacinaUdarcaNapadaca;
	public int zdravljeNapadaca;
	public int poeni;//koliko poena vredi ovaj neprijatelj i kada se stvara (mora biti manje od broja nivoa na kom se stvara)
	public double brzinaNapadaca;
	public double brzinaUdaracaNapadaca;
	public double zakasnjenjeUdarca;
	public String slika;
	
	public Napadac (int idNapadaca, int nagrada, int jacinaUdarcaNapadaca, int zdravljeNapadaca, int poeni,double brzinaNapadaca,double brzinaUdaracaNapadaca,double zakasnjenjeUdarca) {
		
		if(listaNapadaca[idNapadaca] != null){
			System.out.println("[Inicijalizacija Neprijatelja] dva neprijatelja sa istim id"+ idNapadaca);
		}else{
			listaNapadaca[idNapadaca] = this;
			
			this.idNapadaca = idNapadaca;
			this.nagrada = nagrada;
			this.jacinaUdarcaNapadaca = jacinaUdarcaNapadaca;
			this.zdravljeNapadaca = zdravljeNapadaca;
			this.poeni = poeni;
			this.brzinaNapadaca = brzinaNapadaca;
			this.brzinaUdaracaNapadaca = brzinaUdaracaNapadaca;
			this.zakasnjenjeUdarca = zakasnjenjeUdarca;
		}
	}
	//dodavanje teksture napadacima
	public Napadac getTextureFile(String string){
		this.slika = string;
		this.tekstura = new ImageIcon("res/Napadaci/" + this.slika +".png").getImage();
		return this;
	}
	
}
