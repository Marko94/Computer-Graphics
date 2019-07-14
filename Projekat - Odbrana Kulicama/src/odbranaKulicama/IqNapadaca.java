package odbranaKulicama;
public class IqNapadaca {
	
	public static PutanjaNapadaca putanja;
	
	public static KretanjeAI pomeriAI;
	
	public static int bazaX;
	public static int bazaY;
	public int id;
	//inteligencija napadaca, odredjivanje putanje i polozaja baze
	public IqNapadaca(Nivo nivo) {
		putanja = new PutanjaNapadaca(nivo);
		bazaX = putanja.baza.xPozicija;
		bazaY = putanja.baza.yPozicija;
		
		pomeriAI = new KretanjeAI(0);
	}
	
	public IqNapadaca (int id) {
		this.id = id;
	}
	public PutanjaNapadaca getPutanjaNapadaca(){
		return putanja;
	}
	
	
}
