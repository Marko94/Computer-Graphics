package odbranaKulicama;

public class PutanjaNapadaca {

	Nivo nivo;
	
	int [][] putanja = new int [22][12];
	//inicijalizujemo pravce zbog lakseg koriscenja
	int desno = 1;
	int dole = 2;
	int levo = 3;
	int gore = 4;
	
	int poslednjaPozicija = -1;
	
	int xPozicija;
	int yPozicija;
	//baza je oznacena sa brojem 4 u fajlu nivoa
	int bazaBlok = 4;

	Baza baza;
	//uzima se nivo stvaranja radi izracunavanja putanje
	public PutanjaNapadaca(Nivo nivo){
		this.nivo = nivo;
		
		this.xPozicija = this.nivo.mestoStvaranja.getX();
		this.yPozicija = this.nivo.mestoStvaranja.getY();
		izracunajPutanju();	
	}
	//da bi se izracunala putanja mora se izracunati sledeca pozicija
	private void izracunajPutanju() {
		while(baza == null){
			izracunajSledecuPoziciju();
		}
	}
	//gleda se gde je napadac prethodno bio i gde moze da ide, to se radi za sva cetiri smera: gore, desno, dole, levo 
	private void izracunajSledecuPoziciju() {
		for(int i = 1; i<5; i++){
			if(i != poslednjaPozicija){
				//gore
				if(i == gore && yPozicija > 0){
					if(nivo.mapa[xPozicija][yPozicija - 1] == 1){
						poslednjaPozicija = dole;
						putanja[xPozicija][yPozicija] = gore;
						yPozicija-- ;
						break;
					}else if(nivo.mapa[xPozicija][yPozicija-1] == bazaBlok){
						
						baza = new Baza(xPozicija, yPozicija);
						break;
					}
				}
				//desno
				if(i == desno && xPozicija < 21){
					if(nivo.mapa[xPozicija+1][yPozicija] == 1){
						poslednjaPozicija = levo;
						putanja[xPozicija][yPozicija] = desno;
						xPozicija++ ;
						
						break;
					}else if(nivo.mapa[xPozicija+1][yPozicija] == bazaBlok){
						
						this.baza = new Baza(xPozicija, yPozicija);
						break;
					}
				}
				//levo
				if(i == levo && xPozicija > 0){
					if(nivo.mapa[xPozicija-1][yPozicija] == 1){
						poslednjaPozicija = desno;
						putanja[xPozicija][yPozicija] = levo;
						xPozicija-- ;
						
						break;
					}else if(nivo.mapa[xPozicija-1][yPozicija] == bazaBlok){
						
						baza = new Baza(xPozicija, yPozicija);
						break;
					}
				}
				//gore
				if(i == dole && yPozicija < 11){
					if(nivo.mapa[xPozicija][yPozicija + 1] == 1){
						poslednjaPozicija = gore;
						putanja[xPozicija][yPozicija] = dole;
						yPozicija++ ;
						
						break;
					}else if(nivo.mapa[xPozicija][yPozicija + 1] == bazaBlok){
						
						baza = new Baza(xPozicija, yPozicija);
						break;
					}
				}
			}
		}	
	}	
}
