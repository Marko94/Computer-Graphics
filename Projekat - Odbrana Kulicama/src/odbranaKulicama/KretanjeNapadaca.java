package odbranaKulicama;
public class KretanjeNapadaca {
	Napadac napadac;
	
	double x;
	double y;
	
	int putanjaX;
	int putanjaY;
	
	int zdravlje;
	
	boolean napad;
	//poziva se funkcija koja stvara napadace
	public KretanjeNapadaca(Napadac napadac, MestoStvaranja mestoStvaranja){
		this.napadac = napadac;
		this.putanjaX = mestoStvaranja.getX();
		this.putanjaY = mestoStvaranja.getY();
		this.x = mestoStvaranja.getX() * Prozor.velicinaKulice;
		this.y = mestoStvaranja.getY() * Prozor.velicinaKulice;
		this.napad = false;
		this.zdravlje = napadac.zdravljeNapadaca;
	}
	//osvezavanje napadaca
	public KretanjeNapadaca osvezi(){
		KretanjeNapadaca trenutniNapadac = this;
		if(trenutniNapadac.zdravlje <= 0){
			
			return null;
		} else {
			return trenutniNapadac;
		}
	}

}
