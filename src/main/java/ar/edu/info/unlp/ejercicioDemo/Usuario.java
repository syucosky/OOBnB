package ar.edu.info.unlp.ejercicioDemo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private String direccion;
	private int dni;
	private List<Propiedad> propiedadesEnAlquiler;
	private Propiedad reservaActual;
	private List<Propiedad> historialDeReservas;
	
	public  Usuario(String nombre, String direccion, int dni) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.dni = dni;
		this.propiedadesEnAlquiler = new ArrayList<Propiedad>();
		this.reservaActual = null;
		this.historialDeReservas = new ArrayList<Propiedad>();
	}
	
	public int getDni() {
		return this.dni;
	}
	
	// Métodos de USUARIO PROPIETARIO
	public void setPropiedadEnAlquiler(Propiedad propiedad) {
		this.propiedadesEnAlquiler.add(propiedad);
	}
	public List<Propiedad> getPropiedadesEnAlquiler(){
		return this.propiedadesEnAlquiler;
	}
	
	
	
	// Métodos de USUARIO INQUILINO 
	public void setReserva(Propiedad propiedad) {
		this.reservaActual = propiedad;
		this.setReservaEnHistorial(propiedad);
	}
	public List<Propiedad> getHistorialDeReservas(){
		return this.historialDeReservas;
	}
	public void setReservaEnHistorial(Propiedad propiedad) {
		this.historialDeReservas.add(propiedad);
	}
	public void terminarReservaActual() {
		this.reservaActual = null;
	}
}
