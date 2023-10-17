package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLapse {
	private LocalDate desde;
	private LocalDate hasta;
	
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
	public int cantidadDeDias() {
		return (int)ChronoUnit.DAYS.between(getDesde(), getHasta());
	}
	public boolean estaLibre(String desde, String hasta) {
		LocalDate fechaDesde = LocalDate.from(LocalDate.parse(desde));
		LocalDate fechaHasta = LocalDate.from(LocalDate.parse(hasta));
		return ( fechaDesde.isEqual(getDesde()) || fechaDesde.isAfter(getDesde()) ) && ( fechaHasta.isBefore(getHasta()) || fechaHasta.isEqual(getHasta()) );
	}
	public boolean isBefore(LocalDate fechaAlquilada, LocalDate desde) {
		return fechaAlquilada.isBefore(desde);
	}
}