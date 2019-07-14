package odbranaKulicama;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Kulica implements Cloneable{
	
	public String tekstura = "";
	public Image texture;
	
	public static final Kulica[] listaKulica = new Kulica[36];
	//inicijalizacija kulica
	public static final Kulica kulicaGromova = new KulicaGromova(0, 10, 2, 3, 5, 20 ).getTextureFile("kulicagromova");
	public static final Kulica kulicaGromovaZuta = new KulicaGromova(1, 25, 2, 5, 5, 20 ).getTextureFile("kulicagromova2");
	public static final Kulica kulicaGromovaPlava = new KulicaGromova(2, 50, 2, 10, 5, 20 ).getTextureFile("kulicagromova3");
	public static final Kulica kulicaGromovaCrvena = new KulicaGromova(3, 100, 3, 15, 5, 20 ).getTextureFile("kulicagromova4");

	public int id;
	public int cena;
	public int domet;
	public int udarac;
	public int vremeUdarca; // (tajmer)
	public int zakasnjenjeUdarca; // (tajmer)
	public int maksimalnoVreme;//vreme koje prodje dok udarac ne stigne do mete
	public int maksimalnoZakasnjenje;// vreme izmedju dva udarca
	
	public int PRVI = 1;//udara prvog neprijatelja
	public int RANDOM = 2; //nasumicno bira koga ce da udara
	
	//biranje strategije napada ce biti implementirano u buducnosti
	public int strategijaNapada = RANDOM;
	
	public KretanjeNapadaca meta;
	
	public Kulica(int id, int cena, int domet, int udarac, int maksimalnoVreme, int maksimalnoZakasnjenje) {
		if(listaKulica[id] != null){
			System.out.println("[InicijalizacijaKulice] Dve kulice imaju isti Id! id=" +id);
		} else {
			listaKulica[id] = this;
			this.id = id;
			this.cena = cena;
			this.domet = domet;
			this.udarac = udarac;
			this.maksimalnoVreme = maksimalnoVreme;
			this.maksimalnoZakasnjenje = maksimalnoZakasnjenje;
			this.vremeUdarca = 0;
			this.zakasnjenjeUdarca = 0;
		}
		
	}
	
	public KretanjeNapadaca izracunajNapadaca(KretanjeNapadaca[] napadaci, int x, int y){
		//Koji napadaci su u dometu kulice?
			KretanjeNapadaca[] napadaciUDometu = new KretanjeNapadaca[napadaci.length];
		
			int kulicaX = x;
			int kulicaY = y;
			
			int dometKulice = this.domet;
			int dometNapadaca = 1;
			
			int napadacX;
			int napadacY;
			//da li je napadac u dometu?
			for(int i = 0; i < napadaci.length; i++){
				if(napadaci[i] != null){
					napadacX =(int)( napadaci[i].x / Prozor.velicinaKulice);
					napadacY =(int)( napadaci[i].y / Prozor.velicinaKulice);
					
					int dx = napadacX - kulicaX;
					int dy = napadacY - kulicaY;
					
					int poluprecnik = dometKulice + dometNapadaca;
					
					if((dx*dx)+(dy*dy) < poluprecnik * poluprecnik) {
						napadaciUDometu[i] = napadaci[i];
					}
				}
			}
		//implementiranje strategije RANDOM koja bira nasumicnog neprijatelja u dometu kulice	
		if(this.strategijaNapada == RANDOM){
			int ukupnoNapadaca = 0;
			
			for(int i = 0; i < napadaciUDometu.length; i++){
				if(napadaciUDometu[i] != null){
				ukupnoNapadaca++;
				}
			}
			
			if(ukupnoNapadaca > 0){
				int napadac = new Random().nextInt(ukupnoNapadaca);
				int uzetihNapadaca = 0;
				int i = 0;
				
				while(true){
					if(uzetihNapadaca == napadac && napadaciUDometu[i] != null){
						return napadaciUDometu[i];
					}
					
					if(napadaciUDometu[i] != null){
						uzetihNapadaca++;
					}
					
					i++;
					
				}
			}	
		}
			
		return null;
	}
	//kloniranje kulice da bi bile nezavise jedna od druge
	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//dodavanje teksture kulicama
	public Kulica getTextureFile(String string){
		this.tekstura = string;
		
		this.texture = new ImageIcon("res/Kulice/" + this.tekstura +".png").getImage();
		
		return null;
	}
	
}
