package odbranaKulicama;

import java.awt.BasicStroke;
import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class Prozor extends JPanel implements Runnable {
	Thread nit = new Thread(this);
	
	Okvir okvir;
	Nivo nivo;
	NivoFajl nivoFajl;
	IqNapadaca iqNapadaca;
	Talas talas;
	
	
	Korisnik korisnik;
	private int fps = 0;	
	public int scena = 0;
	
	public int ruka = 0;
	public int rukaX = 0;
	public int rukaY = 0;
	//da li je igrica pokrenuta?
	public boolean pokrenuto = false;
	public static int DELAY = 1000/60;
	public double sirinaKulice = 1;
	public double visinaKulice = 1;
	
	public int [][] mapa = new int [22][12];
	public Kulica[][] mapaKulica = new Kulica[22][12];
	
	public Image[] teren = new Image[200];
	public Image stizeUskoro = new ImageIcon("res/Kulice/StizeUskoro.png").getImage();
	public Image startPozadina = new ImageIcon("res/Scene/Pocetak.png").getImage();
	public Image startPozadina1 = new ImageIcon("res/Scene/Pozadina1.png").getImage();
	public Image poraz = new ImageIcon("res/Scene/Poraz.png").getImage();
	public Image informacije = new ImageIcon("res/Interfejs/Informacije.png").getImage();
	public Image polje = new ImageIcon("res/Interfejs/Polje.png").getImage();
	public Image dugmici = new ImageIcon("res/Interefejs/Dugmici.png").getImage();
	
	public static double velicinaKulice = (1024 - (5 * (1024 / 22))) / 22 ;
	public double zakasnjenjeUdarca = 0;
	
	public	KretanjeNapadaca[] mapaNapadaca = new KretanjeNapadaca[100];
	
	public Kulica selektovanaKulica;
	
	public Prozor(Okvir okvir){
		this.okvir = okvir;
		
		this.okvir.addKeyListener(new Tastatura(this));
		this.okvir.addMouseListener(new Mis(this));
		this.okvir.addMouseMotionListener(new Mis(this));		
		nit.start(); 
	}
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, this.okvir.getWidth(), this.okvir.getHeight());
		double kvadrat = (this.okvir.getWidth() - (5 * (this.okvir.getWidth() / 22))) / 22;
		
		//Bojenje pozadine u odnosu na scene(dela) igrice
		if(scena == 0){
			g.drawImage(startPozadina,0,0,1024,576,null);
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.setColor(new Color(140,230,255,150));
		} else if (scena == 1) {
			//pozadina
			g.drawImage(startPozadina1,0,0,1024,576,null);
			//mreza
			g.setColor(Color.BLACK);
			sirinaKulice = (int)kvadrat;
			visinaKulice = (int)kvadrat;
			for(int x=0; x<22;x++){
				for (int y=0; y<12; y++){
					g.drawImage(teren[mapa[x][y]],(int) sirinaKulice - (int)(0.009 * this.okvir.getWidth()) + x *(int) sirinaKulice,(int) visinaKulice - (int)(0.015 * this.okvir.getHeight())+ y *(int) visinaKulice,(int) sirinaKulice,(int) visinaKulice, null);
					g.drawRect((int)27 + (x * (int)kvadrat), 28 + (y * (int)kvadrat), (int) kvadrat, (int) kvadrat);
				}
			}
			// neprijatelji
				for(int i = 0; i < mapaNapadaca.length; i++){
					if(mapaNapadaca[i] != null){ 
						g.drawImage(mapaNapadaca[i].napadac.tekstura, 27 + (int) mapaNapadaca[i].x, 28 + (int) mapaNapadaca[i].y, (int)sirinaKulice, (int)visinaKulice, null);
					}
				}
			
			// zdravlje, novac i opcije
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawImage(informacije, (int)(0.03*this.okvir.getWidth()),  this.okvir.getHeight() - (int)(0.195 * this.okvir.getHeight()), (int)(0.15*this.okvir.getWidth()), (int)(0.15*this.okvir.getHeight()), null);
				g.setColor(Color.BLACK);
				g.drawString("Zivoti:" + korisnik.igrac.zivoti,(int)(0.084*this.okvir.getWidth()), this.okvir.getHeight() - (int)(0.163 * this.okvir.getHeight()));
				g.setColor(Color.BLACK);
				g.drawString("Novac:" + korisnik.igrac.novac,(int)(0.08*this.okvir.getWidth()), this.okvir.getHeight() - (int)(0.194 * this.okvir.getHeight()) + (int)(0.08*this.okvir.getHeight()));
				g.setFont(new Font("Arial", Font.PLAIN, 12));

			//dumici za listanje kulica
				g.drawImage(dugmici,(int)(0.036 * this.okvir.getWidth())+(int)(0.16*this.okvir.getWidth()), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()),(int) kvadrat, (int)(0.138*this.okvir.getHeight()), null);
		//lista kulica
				for (int x=0; x<12; x++){
					for (int y=0; y<2; y++){
						//ukoliko postoji kulica se ubacuje u tabelu
						if(Kulica.listaKulica[x * 2 +y]!=null) {
							g.drawImage(polje,(int)(0.245  *this.okvir.getWidth()) + (x * (int)kvadrat), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()) + (y * (int)kvadrat),(int)kvadrat,(int)kvadrat,null);
							g.drawImage(Kulica.listaKulica[x * 2 +y].texture,(int)(0.245  *this.okvir.getWidth()) + (x * (int)kvadrat), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()) + (y * (int)kvadrat),(int)kvadrat,(int)kvadrat,null);
						// ako nema para da kupi kulicu
							if(Kulica.listaKulica[x * 2 + y].cena > this.korisnik.igrac.novac) {
								g.setColor(new Color(255,0,0,100));
								g.fillRect((int)(0.245  *this.okvir.getWidth()) + (x * (int)kvadrat), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()) + (y * (int)kvadrat),(int)kvadrat,(int)kvadrat);
							}}else{
								g.drawImage(polje,(int)(0.245  *this.okvir.getWidth()) + (x * (int)kvadrat), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()) + (y * (int)kvadrat),(int)kvadrat,(int)kvadrat,null);
								g.drawImage(stizeUskoro,(int)(0.245  *this.okvir.getWidth()) + (x * (int)kvadrat), this.okvir.getHeight() - (int)(0.19 * this.okvir.getHeight()) + (y * (int)kvadrat),(int)kvadrat,(int)kvadrat,null);
							}
						g.setColor(Color.GRAY);
					}
				}
				//Postavljanje kulica na odredjeno mesto u mrezi
				for(int x=0; x<22; x++){
					for(int y=0; y<12; y++){
						if(mapaKulica[x][y] != null){
							if(selektovanaKulica != null){
								if(selektovanaKulica == mapaKulica[x][y]){
									g.setColor(Color.RED);
									g.drawOval(27 + (x * (int) sirinaKulice) - (mapaKulica[x][y].domet * 2 * (int) sirinaKulice)/2 , 28 + (y*(int) visinaKulice) - (mapaKulica[x][y].domet * 2 * (int) visinaKulice)/2, mapaKulica[x][y].domet * 2 * (int)sirinaKulice + (int)sirinaKulice , mapaKulica[x][y].domet * 2 * (int)visinaKulice + (int)visinaKulice);
									g.setColor(new Color(64,64,64,64));
									g.fillOval(27 + (x * (int) sirinaKulice) - (mapaKulica[x][y].domet * 2 * (int) sirinaKulice)/2 , 28 + (y*(int) visinaKulice)  - (mapaKulica[x][y].domet * 2 * (int) visinaKulice)/2, mapaKulica[x][y].domet * 2 * (int)sirinaKulice + (int)sirinaKulice , mapaKulica[x][y].domet * 2 * (int)visinaKulice + (int)visinaKulice);
								}
							}	
							g.drawImage(Kulica.listaKulica[mapaKulica[x][y].id].texture, 27 + (x * (int) sirinaKulice),28 + (y * (int)visinaKulice), (int) sirinaKulice, (int) visinaKulice, null);
						
							if(mapaKulica[x][y]!=null && mapaKulica[x][y].meta != null){
								g.setColor(Color.BLUE);
								g2.setStroke(new BasicStroke(3));
								g2.drawLine( 27 + (x * (int) sirinaKulice + (int)sirinaKulice / 2),28 + (y * (int)visinaKulice) + (int)visinaKulice / 2, 27 + (int) sirinaKulice/2 + (int)mapaKulica[x][y].meta.x, 28 + (int) visinaKulice/2 + (int)mapaKulica[x][y].meta.y);
								g.setColor(Color.CYAN);
								g2.setStroke(new BasicStroke(1));
								g2.drawLine( 27 + (x * (int) sirinaKulice + (int)sirinaKulice / 2),28 + (y * (int)visinaKulice) + (int)visinaKulice / 2, 27 + (int) sirinaKulice/2 + (int)mapaKulica[x][y].meta.x, 28 + (int) visinaKulice/2 + (int)mapaKulica[x][y].meta.y);
								}
						}
					}
				}
				//postavljamo sliku odabrane kulice u ruku
				if(ruka != 0 && Kulica.listaKulica[ruka-1] != null){
					g.drawImage(Kulica.listaKulica[ruka - 1].texture,this.rukaX - (int)this.sirinaKulice /2, this.rukaY - (int)this.visinaKulice, (int)this.sirinaKulice , (int)this.visinaKulice ,null);
				}
				
		} else { if (scena==3){
			g.drawImage(poraz,0,0,1024,576,null);

		}else{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.okvir.getWidth(), this.okvir.getHeight());
		}
		}
		//Frejmovi na kraju!
	
		g.drawString(fps + "", 20, 20);
		frejmovi++;
	}
	//samo prvi put se izvrsava!
	public void ucitajIgru(){
		korisnik = new Korisnik(this);
		nivoFajl = new NivoFajl();
		talas = new Talas(this);
		
		ClassLoader cl = this.getClass().getClassLoader();
		for (int y = 0; y < 10; y++) {
			for(int x = 0; x < 10; x++) {
				teren[x + (y * 10)] = new ImageIcon(cl.getResource("teren.png")).getImage();
				teren[x + (y * 10)] = createImage (new FilteredImageSource(teren[x + (y * 10)].getSource(), new CropImageFilter(x * 25, y*25, 25,25)));
			}
		}
		
		
		pokrenuto = true;
	}
	//pokretanje igre
	public void pokreniIgru(Korisnik korisnik, String nivo){
		korisnik.napraviIgraca();
		
		this.nivo = nivoFajl.getNivo(nivo);
		this.nivo.nadjiMestoStvaranja();
		this.mapa = this.nivo.mapa;
		this.iqNapadaca = new IqNapadaca(this.nivo);
		
		//prvi nivo
		this.scena = 1;
		this.talas.brojTalasa =0;
	}
	int frejmovi = 0;
	//igra je u toku
	public void run() {
		System.out.println("[Success] Frame Created!");
		int synchronized_fps = 0;

		long poslednjiFrejm = System.currentTimeMillis();
		
		
		ucitajIgru();
		
		//broji prolaske kroz petlju (u sekundi) i ponovo sve iscrtava, koristi se uglavnom za debugovanje
		
		while(pokrenuto){
			
			repaint();
			
			
			if(System.currentTimeMillis() - 1000 >= poslednjiFrejm){
				fps= frejmovi;
				frejmovi = 0;
				poslednjiFrejm = System.currentTimeMillis();
			}
			
			//sinhronizacija frejmova u slucaju da ima mnogo odstupanja u osvezavanju, pomaze kod starijih kompijutera
			
			double vreme = (double) System.currentTimeMillis() / 1000;
			
			int Milisekunde = (int) Math.round((vreme - (int)vreme)*1000);
			
			if(Milisekunde > synchronized_fps * 1000/25){
				synchronized_fps++;
				osvezi();	
			
				if(synchronized_fps == 40){
					synchronized_fps = 0;
				}
			}
			
			if(Milisekunde + 1000/25 < synchronized_fps * 1000/25){
				synchronized_fps = 0;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//ukoliko igrica nije pokrenuta iskljuci program
		System.exit(0);
	}
	//osvezavanje stanja napadaca i njihovog polozaja
	public void osveziNapadace(){
		for(int i = 0; i < mapaNapadaca.length; i++){
			if(mapaNapadaca[i]!= null) {
				if(!mapaNapadaca[i].napad){
					IqNapadaca.pomeriAI.pomeri(mapaNapadaca[i], velicinaKulice);	
				}else{
					if(this.mapaNapadaca[i].napadac.zakasnjenjeUdarca > this.mapaNapadaca[i].napadac.brzinaUdaracaNapadaca){
						
						if(korisnik.igrac.zivoti > 0){
							korisnik.igrac.zivoti -= this.mapaNapadaca[i].napadac.jacinaUdarcaNapadaca;
							this.mapaNapadaca[i].napadac.zakasnjenjeUdarca = 0;
							
							System.out.println("[Baza] Baza je napadnuta! Njeno preostalo zdravlje je: " +korisnik.igrac.zivoti);
										
						}else{
							this.scena=3;
						}
					
					}else{
						this.mapaNapadaca[i].napadac.zakasnjenjeUdarca+=1;
					}
				}
				
				mapaNapadaca[i] = mapaNapadaca[i].osvezi(); // proverava njihov status
				
			}
		}
	}
	//osvezavanje stanja kulica
	public void osveziKulice(){
		for(int x=0; x < 22; x++){
			for (int y=0; y<12; y++){
				if(mapaKulica[x][y] != null){
					udaracKulice(x,y);
				}
			}
		}
	}
	//jadni napadaci
	public void udaracKulice(int x, int y){
		if(this.mapaKulica[x][y].meta == null){
			//pronadji metu za ovu kulicu
			if(this.mapaKulica[x][y].zakasnjenjeUdarca > this.mapaKulica[x][y].maksimalnoZakasnjenje){
				KretanjeNapadaca trenutniNapadac = this.mapaKulica[x][y].izracunajNapadaca(mapaNapadaca, x, y);
				
				if(trenutniNapadac != null){
					trenutniNapadac.zdravlje -= this.mapaKulica[x][y].udarac;
					this.mapaKulica[x][y].meta = trenutniNapadac;
					this.mapaKulica[x][y].vremeUdarca = 0;
					this.mapaKulica[x][y].zakasnjenjeUdarca = 0;
					if(trenutniNapadac.zdravlje <= 0){
						korisnik.igrac.novac += trenutniNapadac.napadac.nagrada;
					}								
				}
			
			}else{
				this.mapaKulica[x][y].zakasnjenjeUdarca+=1;
			}
		}else{
			if(this.mapaKulica[x][y].vremeUdarca < this.mapaKulica[x][y].maksimalnoVreme){
				this.mapaKulica[x][y].vremeUdarca+=1;
			}else{
				this.mapaKulica[x][y].meta = null;
			}
		}
	}
	
	//poziva se osvezavanje napadaca i kulica i stvaranje napadaca tokom talasa
	public void osvezi(){
		osveziNapadace();
		osveziKulice();
		
		if(talas.pokrenutTalas){
			talas.stvoriNapadace();
		}
	}

	//stvaranje napadaca
	public void stvoriNapadace(int idNapadaca){
		for(int i = 0; i < mapaNapadaca.length; i++ ){
			if(mapaNapadaca[i] == null){
				mapaNapadaca[i] = new KretanjeNapadaca(Napadac.listaNapadaca[idNapadaca], nivo.mestoStvaranja);
				break;
			}
		}
	}	
	
	//kupovina kulice iz prodavnice i njeno postavljanje na mrezu 
	public void postaviKulicu(int x, int y) {
		int xPozicija = x / (int)sirinaKulice;
		int yPozicija = y / (int)visinaKulice;
		
		xPozicija --;
		yPozicija --;
		
		if(xPozicija > 22 || xPozicija < 0 || yPozicija < 0 || yPozicija > 12) {
			
		}else if (mapaKulica[xPozicija][yPozicija] == null && mapa[xPozicija][yPozicija] == 0){
			korisnik.igrac.novac -= Kulica.listaKulica[ruka - 1].cena;
			mapaKulica[xPozicija][yPozicija] = (Kulica) Kulica.listaKulica[ruka -1].clone();
			selektovanaKulica = mapaKulica[xPozicija][yPozicija];
		}
	}
	
	//oznacavanje kulice prilikom postavljanja i njeno deselektovanje ako se klikne na polje u kojoj se ne nalazi ta kulica
	public void selektujKulicu(int x, int y){
		int xPozicija = x / (int)sirinaKulice;
		int yPozicija = y / (int)visinaKulice;
		
		xPozicija --;
		yPozicija --;
		
		if(xPozicija > 22 || xPozicija < 0 || yPozicija < 0 || yPozicija > 12) {
			
			selektovanaKulica = null;
			
		}else{
			selektovanaKulica = null;
			if (mapaKulica[xPozicija][yPozicija] == null && mapa[xPozicija][yPozicija] == 0){
				selektovanaKulica = mapaKulica[xPozicija][yPozicija];
			}
		}
	}
	//prevlacenje
	public class MouseHeld{
		boolean mouseDown = false;
		
		double kvadrat = (okvir.getWidth() - (5 * (okvir.getWidth() / 22))) / 22;
		
		public void mouseMoved(MouseEvent e) {
			rukaX = e.getXOnScreen() - okvir.getLocationOnScreen().x;
			rukaY = e.getYOnScreen() - okvir.getLocationOnScreen().y;
		}
		//stavljanje kulice iz prodavnice u ruku
		public void promeniMisa (MouseEvent e){
			if (scena == 1){
				if(mouseDown && ruka == 0){
					//da li se mis nalazi u listi kulica?
					if (((e.getXOnScreen() - okvir.getLocationOnScreen().x) >= (int)(0.248 * okvir.getWidth())) && ((e.getXOnScreen() - okvir.getLocationOnScreen().x) <= ((int)(0.248  * okvir.getWidth()) + 12 * kvadrat))) {
						if (((e.getYOnScreen() - okvir.getLocationOnScreen().y) >= (okvir.getHeight() - (int)(0.15 * okvir.getHeight()))) && ( (e.getYOnScreen() - okvir.getLocationOnScreen().y) <= okvir.getHeight() - (int)(0.15 * okvir.getHeight()) + 2 * kvadrat)) {
							for(int i = 0; i < Kulica.listaKulica.length; i++){
								if(((e.getXOnScreen() - okvir.getLocationOnScreen().x)>= (int)(0.248 * okvir.getWidth()) + (int)(i/2) * sirinaKulice ) && ((e.getXOnScreen() - okvir.getLocationOnScreen().x )<= ((int)(0.248 * okvir.getWidth()) + kvadrat)+ (int)(i/2) * sirinaKulice ) && (((e.getYOnScreen() - okvir.getLocationOnScreen().y) >= okvir.getHeight() - (int)(0.15 * okvir.getHeight()) + (int)(i%2) * visinaKulice) && ( (e.getYOnScreen() - okvir.getLocationOnScreen().y) <= okvir.getHeight() - (int)(0.15 * okvir.getHeight()) + kvadrat+ (int)(i%2) * visinaKulice ))){
									//ako ima novca
									if (korisnik.igrac.novac >= Kulica.listaKulica[i].cena){
										System.out.println ("Kupio si kulicu za " +Kulica.listaKulica[i].cena + " novcica!");
										ruka = i + 1;
									
										return;
									}
								}
							}
						}
					}
				}
			}
		}

		public void mouseDown(MouseEvent e) {
			mouseDown = true;
			
			if (ruka != 0){
				//postavi kulicu
				postaviKulicu((int)e.getXOnScreen() - okvir.getLocationOnScreen().x + (int)(0.005 * okvir.getWidth()),(int)e.getYOnScreen() - okvir.getLocationOnScreen().y - (int)(0.03 * okvir.getHeight()));
				System.out.println("[Prodavnica] Kupili ste kulicu za: " + Kulica.listaKulica[ruka - 1].cena);

				ruka = 0;
			}else{
				selektujKulicu((int)e.getXOnScreen() - okvir.getLocationOnScreen().x + (int)(0.005 * okvir.getWidth()+27),(int)e.getYOnScreen() - okvir.getLocationOnScreen().y - (int)(0.03 * okvir.getHeight())+28);
				}
			
			promeniMisa(e);
			
		}
		
	}

	
	
	
	
	//kreiranje funkcija za odredjene dugmice na tastaturi
	public class KeyTyped{
		public void dugmeESC(){
			pokrenuto = false;
		}
		public void dugmeENTER(){
			int moze=1;
			for(int i = 0;i<mapaNapadaca.length; i++){
					if(mapaNapadaca[i]!=null){
				
					moze=0;
					}
				if(!talas.pokrenutTalas && moze==1){
					talas.sledeciTalas();
				}
			}
			
		}

		public void dugmeSPACE() {
			pokreniIgru(korisnik, "Nivo1");
		}
	}
	
}
