package odbranaKulicama;

import java.util.Random;

public class Talas {
	Prozor prozor;
	
	int brojTalasa = 0;
	int trenutniPoeni;
	int poeniOveRunde;
	
	boolean pokrenutTalas;
	public Talas(Prozor prozor) {
		this.prozor = prozor;
	}
	//pokretanje sledeceg talasa podrazumeva prestanak proslog
	public void sledeciTalas() {
		this.brojTalasa++;
		this.poeniOveRunde = this.brojTalasa * 10;
		this.trenutniPoeni = 0;
		this.pokrenutTalas = true;
	    
		System.out.println("[Talas] Broj sledeceg talasa: " + this.brojTalasa);
		for(int i = 0; i < this.prozor.mapaNapadaca.length; i++ ) {
			this.prozor.mapaNapadaca[i] = null;
		}
	}
	private int trenutnoZakasnjenje = 0;
	private int brzinaStvaranja = 50;//interval stvaranja napadaca
	
	//stvaranje napadaca nakon izvesnog intervala
	public void stvoriNapadace(){
		if (this.trenutniPoeni < this.poeniOveRunde) {
			if(trenutnoZakasnjenje < brzinaStvaranja) {
				trenutnoZakasnjenje++;

			}else{
				trenutnoZakasnjenje = 0;
				System.out.println("[Talas] Napadac je stvoren!");
				
				int[] stvorljiviNapadaciId = new int[Napadac.listaNapadaca.length];
				int doSadaStvorljiviNapadaci = 0;
						
				// poeni se racunaju tako sto se broj trenutnog talasa pomnozi sa 10 
				//u talasu mogu da se stvaraju samo napadaci tezine koja je manja ili jednaka broju trenutnog talasa
				for (int i = 0; i < Napadac.listaNapadaca.length; i++){
					if(Napadac.listaNapadaca[i] != null){
						if(Napadac.listaNapadaca[i].poeni + trenutniPoeni <= this.poeniOveRunde && Napadac.listaNapadaca[i].poeni <= this.brojTalasa){
							stvorljiviNapadaciId[doSadaStvorljiviNapadaci] = Napadac.listaNapadaca[i].idNapadaca;
							doSadaStvorljiviNapadaci++;
						}
					}
				}
				
				int idNapadaca = new Random().nextInt(doSadaStvorljiviNapadaci);
				this.trenutniPoeni += Napadac.listaNapadaca[idNapadaca].poeni;
				this.prozor.stvoriNapadace(stvorljiviNapadaciId[idNapadaca]);
		
			}
		}else{
			this.pokrenutTalas = false;
			
		}
	}
	
	
}
