package odbranaKulicama;

public class Korisnik {

	private Prozor prozor;
	
	Igrac igrac;
	//inicijalizacija pocetnih zivota i novca
	int pocetniNovac = 30;
	int pocetniZivoti = 20;
	
	public Korisnik(Prozor prozor) {
		this.prozor = prozor;
		//postavlja scenu glavnog menija
		this.prozor.scena = 0; 
	}
		//jedan korisnik moze imati vise "igraca" odnosno zapamcenih igara
	public void napraviIgraca(){
		this.igrac = new Igrac(this);
	}
	
}
