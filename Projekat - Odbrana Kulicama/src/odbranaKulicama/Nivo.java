package odbranaKulicama;

public class Nivo {

	int [] [] mapa;
	
	MestoStvaranja mestoStvaranja;
	//pronalazenje mesta stvaranja u fajlu nivoa
	public void nadjiMestoStvaranja(){
		for(int x = 0; x < mapa.length; x++){
			for(int y = 0; y < mapa[0].length; y++){
				if(mapa[x][y] == 3){
					mestoStvaranja = new MestoStvaranja(x,y);
				}
			}
		}
	}
	
}
