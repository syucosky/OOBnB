package ar.edu.info.unlp.ejercicioDemo;

public class Moderada implements Cancelacion{
	
	@Override
	public double politicaDeCancelacion(double tiempo) {
		if(tiempo >= 7){
			return 1;
		}else if(tiempo >= 2) {
			return 0.5;
		}else {
			return 0;
		}
	}
}
