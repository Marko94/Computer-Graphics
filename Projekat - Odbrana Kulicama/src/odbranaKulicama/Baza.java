package odbranaKulicama;

public class Baza {

	int yPozicija;
	int xPozicija;
	//inicijalizacija baze
	int bazaBlok;
	
	int desno = 1;
	int dole = 2;
	int levo = 3;
	int gore = 4;
	
	public Baza(int xPozicija, int yPozicija){
		this.xPozicija = xPozicija;
		this.yPozicija = yPozicija;
	}
}
