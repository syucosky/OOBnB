package ar.edu.info.unlp.ejercicioDemo;

public class Estricta implements Cancelacion {

	@Override
	public double politicaDeCancelacion(double tiempo) {
		return 0;
	}

}
