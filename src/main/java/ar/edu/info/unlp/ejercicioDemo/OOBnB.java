package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OOBnB {
	private List<Usuario> usuarios;
	private List<Propiedad> propiedades;
		
	
	//Métodos de PROPIEDADES
	
	public void eliminarReserva(Propiedad propiedad, Usuario usuario) {
		Propiedad propiedadDeLista = getPropiedadDeLista(propiedad);
		Usuario usuarioDeLista = getUsuarioDeLista(usuario);
		
		LocalDate fechaAlquilada = propiedadDeLista.getFechaOcupada().getDesde();
		
		if(propiedadDeLista.getFechaOcupada().isBefore(fechaAlquilada,LocalDate.now())) {
			
			propiedadDeLista.setFechaAlquiler(null, null);
			usuarioDeLista.terminarReservaActual();
			
		}		
	}
	
	public double calcularPrecioReserva(Propiedad propiedad, String desde, String hasta) {
		double precioPorNoche = getPropiedadDeLista(propiedad).getPrecioPorNoche();
		int cantidadDeDias = new DateLapse(desde,hasta).cantidadDeDias();
		if(precioPorNoche != 0) {
			
			return precioPorNoche * cantidadDeDias;
			
		}else {
			return 0;
		}
		
	}
	
	public Propiedad reservarPropiedad(Propiedad propiedad,String desde, String hasta, Usuario usuario) {
		Propiedad propiedadDeLista = getPropiedadDeLista(propiedad);
		Usuario usuarioDeLista = getUsuarioDeLista(usuario);
		
		
		if(propiedadDeLista != null & usuarioDeLista != null & propiedadDeLista.estaDisponible(desde, hasta)) {
			
			propiedadDeLista.setFechaAlquiler(desde, hasta);
			usuarioDeLista.setReserva(propiedad);
			return propiedadDeLista;
			
		}else {
			return null;
		}
		
		
	}
	public Propiedad getPropiedadDeLista(Propiedad propiedad) {
		Propiedad propiedadDeLista = this.propiedades.stream()
				 .filter(prop -> prop.getPropietario().equals(propiedad.getPropietario()))
				 .filter(prop -> prop.getNombre() == propiedad.getNombre())
				 .findAny()
				 .orElse(null);
		
		return propiedadDeLista;
	}
	
	public List<Propiedad> buscarPropiedadesDisponibles(String desde, String hasta) {
		List<Propiedad> propiedadesDisponibles = this.propiedades.stream()
																 .filter(prop -> prop.estaDisponible(desde, hasta))
																 .collect(Collectors.toList());
		return propiedadesDisponibles;
	}
	public Propiedad registrarPropiedadParaAlquilar(String nombre, String descripcion, double precioPorNoche, String direccion, Usuario propietario) {
		Propiedad nuevaPropiedad = new Propiedad(nombre, descripcion, precioPorNoche, direccion, propietario);
		
		if(addPropiedad(nuevaPropiedad)) {
			return nuevaPropiedad;
		}else {
			return null;
		}
	}
	public boolean addPropiedad(Propiedad propiedad) {
		if(!this.propiedades.stream()
							.filter(prop -> prop.getPropietario().equals(propiedad.getPropietario()))
							.anyMatch(prop -> prop.getNombre() == propiedad.getNombre()))
		{
			return this.propiedades.add(propiedad);
			
		}else {
			return false;
		}
	}
	
	//Métodos de USUARIOS
//	Obtener las reservas de un usuario: dado un usuario, obtener todas las reservas 
//	que ha efectuado (pasadas o futuras). 

	public List<Propiedad> todasLasReservas(Usuario usuario){
		
		return (List<Propiedad>) this.usuarios.stream()
							.filter(usu -> usu.equals(usuario))
							.map(usu -> usu.getHistorialDeReservas());
	}
	
	
	public Usuario registrarUsuario(String nombre, String direccion, int dni) {
		Usuario nuevoUsuario =  new Usuario(nombre, direccion, dni);
		
		if(addUsuario(nuevoUsuario)) {
			return nuevoUsuario;
		}else {
			return null;
		}
		
	}
	
	public boolean addUsuario(Usuario usuario) {
		if(!this.usuarios.stream()
						 .anyMatch(us -> us.getDni() == usuario.getDni()))
		{
			return this.usuarios.add(usuario);
		}else {
			return false;
		}
	}
	public Usuario getUsuarioDeLista(Usuario usuario) {
		Usuario usuarioDeLista = this.usuarios.stream()
				  .filter(usu -> usu.getDni() == usuario.getDni())
				  .findAny().orElse(null);
		
		return usuarioDeLista;
	}
	
}
