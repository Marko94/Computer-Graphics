package odbranaKulicama;

public class Igrac {

	int zivoti;
	int novac;
	//inicijalizacija igraca, cilj je da se u buducjnosti jedan korisnik moci da ima vise profila, tj igraca
	public Igrac(Korisnik korisnik) {
		this.zivoti = korisnik.pocetniZivoti;
		this.novac = korisnik.pocetniNovac;
		
	}

}
