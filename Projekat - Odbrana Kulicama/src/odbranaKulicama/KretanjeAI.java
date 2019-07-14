package odbranaKulicama;
public class KretanjeAI extends IqNapadaca{
	
	
	public KretanjeAI (int id) {
		super(id);
	}
	public void pomeri(KretanjeNapadaca napadac, double velicinaKulice){
		//proverava da li napadac upada u mrezu i u koju stranu ide, ako nije u mrezi, podesi se da bude
		if((napadac.x % (int) velicinaKulice == 0 && napadac.y % (int) velicinaKulice == 0 && napadac.putanjaX == (napadac.x / (int)velicinaKulice) && napadac.putanjaY == (napadac.y / (int)velicinaKulice))){
			if(napadac.putanjaX == bazaX && napadac.putanjaY == bazaY){
				napadac.napad = true;
			}else{
				if(putanja.putanja[napadac.putanjaX][napadac.putanjaY] == putanja.gore){
					napadac.putanjaY--;
				}else if(putanja.putanja[napadac.putanjaX][napadac.putanjaY] == putanja.dole){
					napadac.putanjaY++;
				}else if(putanja.putanja[napadac.putanjaX][napadac.putanjaY] == putanja.desno){
					napadac.putanjaX++;
				}else if(putanja.putanja[napadac.putanjaX][napadac.putanjaY] == putanja.levo){
					napadac.putanjaX--;
				}else{
					NemaPutanje();
				
				}
			}
		}else {
			double xPoz = (napadac.x / velicinaKulice) + 27;
			double yPoz = (napadac.y / velicinaKulice) + 28;				
			
			//Pomeranje napadaca po mrezi	
			if(xPoz > napadac.putanjaX +27) {
				napadac.x-=(double)napadac.napadac.brzinaNapadaca ;
				if(xPoz < napadac.putanjaX+27){
					napadac.x = napadac.putanjaX * (int)velicinaKulice;
			}
			}
	
			if(xPoz < napadac.putanjaX +27){
				napadac.x+=(double)napadac.napadac.brzinaNapadaca ;
				if(xPoz > napadac.putanjaX+27){
					napadac.x = napadac.putanjaX * (int)velicinaKulice;
				}
			}
	
			if(yPoz > napadac.putanjaY +28){
				napadac.y-=(double)napadac.napadac.brzinaNapadaca ;
				if(yPoz < napadac.putanjaY +28){
					napadac.y = napadac.putanjaY * (int)velicinaKulice;
			}
			}
	
			if(yPoz < napadac.putanjaY +28){
				napadac.y+=(double)napadac.napadac.brzinaNapadaca ;
				if(yPoz > napadac.putanjaY+28){
					napadac.y = napadac.putanjaY * (int)velicinaKulice;
			}
			}
		}
}	
	//poziva se ako ne moze da se nadje odgovarajuca putanja
	public void NemaPutanje(){
		System.out.println("[KretanjeAI] Ne moze se naci sledeci pomeraj");
	}
	
}

