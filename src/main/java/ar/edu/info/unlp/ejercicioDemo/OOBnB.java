package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OOBnB {
	private List<Usuario> usuarios;
	private List<Propiedad> propiedades;
		
	
	//Métodos de PROPIEDADES
	
	// DADA UNA PROPIEDAD LA CUAL CUENTA CON RESERVA Y UN USUARIO(INQUILINO) SI LA FECHA DE HOY ES POSTERIOR SE ELIMINA LA RESERVA
	public void eliminarReserva(Propiedad propiedad, Usuario usuario) {
		Propiedad propiedadDeLista = getPropiedadDeLista(propiedad);
		Usuario usuarioDeLista = getUsuarioDeLista(usuario);
		
		LocalDate fechaAlquilada = propiedadDeLista.getFechaOcupada().getDesde();
		
		if(propiedadDeLista.getFechaOcupada().isBefore(fechaAlquilada,LocalDate.now())) {
			
			propiedadDeLista.setFechaAlquiler(null, null);
			usuarioDeLista.terminarReservaActual();
			
		}		
	}
	// DADA UNA PROPIEDAD Y UNA FECHA SE RETORNA EL PRECIO FINAL DE ESA POSIBLE RESERVA
	public double calcularPrecioReserva(Propiedad propiedad, String desde, String hasta) {
		double precioPorNoche = getPropiedadDeLista(propiedad).getPrecioPorNoche();
		int cantidadDeDias = new DateLapse(desde,hasta).cantidadDeDias();
		if(precioPorNoche != 0) {
			
			return precioPorNoche * cantidadDeDias;
			
		}else {
			return 0;
		}
		
	}
	// DADA UNA PROPIEDAD Y UNA FECHA SE RESERVA LA MISMA, Y AL USUARIO SE LE ESTABLECE LA RESERVA 
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
	// DADA UNA PROPIEDAD POR PARAMETRO LA BUSCA EN LA LISTA DE PROPIEDADES Y SI ESTA LA DEVUELVE
	public Propiedad getPropiedadDeLista(Propiedad propiedad) {
		Propiedad propiedadDeLista = this.propiedades.stream()
				 .filter(prop -> prop.getPropietario().equals(propiedad.getPropietario()))
				 .filter(prop -> prop.getNombre() == propiedad.getNombre())
				 .findAny()
				 .orElse(null);
		
		return propiedadDeLista;
	}
	// DADA UNA FECHA DE INICIO Y UNA FECHA FIN DEVUELVE UNA LISTA DE PROPIEDADES DISPONIBLES PARA ALQUILAR
	public List<Propiedad> buscarPropiedadesDisponibles(String desde, String hasta) {
		List<Propiedad> propiedadesDisponibles = this.propiedades.stream()
																 .filter(prop -> prop.estaDisponible(desde, hasta))
																 .collect(Collectors.toList());
		return propiedadesDisponibles;
	}
	// AGREAGA LA PROPIEDAD A LA LISTA DE PROPIEDADES Y AL 
	// PROPIETARIO LE AGREGA LA PROPIEDAD EN SU LISTA DE PROPIEDADES ALQUILADAS
	public Propiedad registrarPropiedadParaAlquilar(String nombre, String descripcion, double precioPorNoche, String direccion, Usuario propietario) {
		Propiedad nuevaPropiedad = new Propiedad(nombre, descripcion, precioPorNoche, direccion, propietario);
		Usuario usuarioDeLista = getUsuarioDeLista(propietario);
		
		if(addPropiedad(nuevaPropiedad)) {
			usuarioDeLista.setPropiedadEnAlquiler(nuevaPropiedad);
			return nuevaPropiedad;
		}else {
			return null;
		}
	}
	
	// VERIFICA QUE LA PROPIEDAD NO ESTE EN LA LISTA DE PROPIEDADES Y LA AGREGA
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
	

	// DADO UN USUARIO Y DOS FECHAS RETORNA LA GANCIA TOTAL ENTRE ESAS FECHAS
	public double ingresoTotalPorAlquileres(Usuario usuario, String desde, String hasta) {
		Usuario usuarioDeLista = getUsuarioDeLista(usuario);
		double montoTotal = usuarioDeLista.getPropiedadesEnAlquiler().stream()
										.mapToDouble(prop -> calcularPrecioReserva(prop, desde, hasta))
										.sum();
		return montoTotal;																	 
	}
	
	// RETORNA EL HISTORIAL DE PROPIEDADES ALQUILADAS POR UN USUARIO 
	public List<Propiedad> todasLasReservas(Usuario usuario){                     // VER
																					// VER	
		return (List<Propiedad>) this.usuarios.stream()								// VER
							.filter(usu -> usu.equals(usuario))							// VER
							.map(usu -> usu.getHistorialDeReservas());			// VER
	}																					// VER
	
	// REGISTRA UN NUEVO USUARIO A LA LISTA DE USUARIOS
	public Usuario registrarUsuario(String nombre, String direccion, int dni) {
		Usuario nuevoUsuario =  new Usuario(nombre, direccion, dni);
		
		if(addUsuario(nuevoUsuario)) {
			return nuevoUsuario;
		}else {
			return null;
		}
		
	}
	// VERIFICA QUE EL USUARIO NO EXISTA Y LO AGREAGA A LA LISTA DE USUARIOS
	public boolean addUsuario(Usuario usuario) {
		if(!this.usuarios.stream()
						 .anyMatch(us -> us.getDni() == usuario.getDni()))
		{
			return this.usuarios.add(usuario);
		}else {
			return false;
		}
	}
	// DADO UN USUARIO POR PARAMETRO LO BUSCA EN LA LISTA DE USUARIOS Y LO DEVUELVE
	public Usuario getUsuarioDeLista(Usuario usuario) {
		Usuario usuarioDeLista = this.usuarios.stream()
				  .filter(usu -> usu.getDni() == usuario.getDni())
				  .findAny().orElse(null);
		
		return usuarioDeLista;
	}
	
}
