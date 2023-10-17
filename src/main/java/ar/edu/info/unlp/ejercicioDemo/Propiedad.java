package ar.edu.info.unlp.ejercicioDemo;

public class Propiedad {
	private String nombre;
	private String descripcion;
	private double precioPorNoche;
	private String direccion;
	private Usuario propietario;
	private DateLapse fechaOcupada;
	
	public Propiedad(String nombre, String descripcion, double precioPorNoche, String direccion, Usuario propietario) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precioPorNoche = precioPorNoche;
		this.direccion = direccion;
		this.propietario = propietario;
		this.fechaOcupada = null;
	}
	
	public void setFechaAlquiler(String desde, String hasta) {
		this.fechaOcupada = new DateLapse(desde, hasta);
	}
	public String getNombre() {
		return this.nombre;
	}
	public double getPrecioPorNoche() {
		return this.precioPorNoche;
	}
	public DateLapse getFechaOcupada() {
		return this.fechaOcupada;
	}
	public Usuario getPropietario() {
		return this.propietario;
	}
	public boolean estaDisponible(String desde, String hasta) {
		return this.fechaOcupada.estaLibre(desde,hasta);
	}
}
