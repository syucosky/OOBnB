package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLapse {
	private LocalDate desde;
	private LocalDate hasta;
	
	public DateLapse() {
		
	}
	public DateLapse(String desde, String hasta) {
		this.desde = LocalDate.from(LocalDate.parse(desde));
		this.hasta = LocalDate.from(LocalDate.parse(hasta));
	}
	public LocalDate getDesde() {
		return desde;
	}
	public LocalDate getHasta() {
		return hasta;
	}
	public void setDesde(String desde) {
		LocalDate fechaDesde = LocalDate.from(LocalDate.parse(desde));
		this.desde = fechaDesde;
	}
	public void setHasta(String hasta) {
		LocalDate fechaHasta = LocalDate.from(LocalDate.parse(hasta));
		this.hasta = fechaHasta;
	}
	public int cantidadDeDias() {
		return (int)ChronoUnit.DAYS.between(getDesde(), getHasta());
	}
	public boolean estaLibre(String desde, String hasta) {
		DateLapse fechaAlquiler = new DateLapse(desde,hasta);
		return ( fechaAlquiler.getDesde().isEqual(this.getHasta()) || fechaAlquiler.getDesde().isAfter(this.getHasta()) );

	}
	public boolean isBefore(LocalDate fechaAlquilada, LocalDate desde) {
		return fechaAlquilada.isBefore(desde) ;
	}
}