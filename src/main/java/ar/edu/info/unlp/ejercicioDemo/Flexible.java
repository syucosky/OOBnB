package ar.edu.info.unlp.ejercicioDemo;

public class Flexible implements Cancelacion {
	
	
	@Override
	public double politicaDeCancelacion(double tiempo) {	
		return 1;
	}
}
