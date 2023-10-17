package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLapse {
	private LocalDate from;
	private LocalDate to;
	
	public DateLapse(String from, String to) {
		this.from = LocalDate.from(LocalDate.parse(from));
		this.to = LocalDate.from(LocalDate.parse(to));
	}
	public LocalDate getFrom() {
		return from;
	}
	public LocalDate getTo() {
		return to;
	}
	public int sizeInDays() {
		return (int)ChronoUnit.DAYS.between(getFrom(), getTo());
	}
	public boolean includesDate(LocalDate other) {
		return other.isAfter(getFrom()) && other.isBefore(getTo());
	}
}