package odbranaKulicama;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class NivoFajl {

	FileInputStream fajl;
	InputStreamReader citac;
	
	Scanner skener;
	
	Nivo nivo = new Nivo();
	
	//ucitavanje novoa
	public Nivo getNivo (String imeFajla) {
		try{
			fajl = new FileInputStream("nivo/" + imeFajla + ".nivo");
			citac = new InputStreamReader(fajl);
			
			skener = new Scanner(citac);
			
			nivo.mapa = new int[22][12];
			int x = 0;
			int y = 0;
			//cita dok ne dodje do kraja dajla
			while(skener.hasNext()) {
				nivo.mapa[x][y] = skener.nextInt();
				//dodaje po x osi dok ne dodje do kraja reda, kad dodje prelazi u novi
				if(x < 22 -1){
					x++;
				}else{
					y++;
					x = 0;
				} 
			}
		
		return nivo;	
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
