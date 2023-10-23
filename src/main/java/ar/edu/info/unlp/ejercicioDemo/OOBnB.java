package ar.edu.info.unlp.ejercicioDemo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OOBnB {
	private List<Usuario> usuarios;
	private List<Propiedad> propiedades;
		
	public OOBnB() {
		usuarios = new ArrayList<Usuario>();
		propiedades = new ArrayList<Propiedad>();
	}
	
	//Métodos de PROPIEDADES
	
	// DADA UNA PROPIEDAD LA CUAL CUENTA CON RESERVA Y UN USUARIO(INQUILINO) SI LA FECHA DE HOY ES POSTERIOR SE ELIMINA LA RESERVA
	public double eliminarReserva(Propiedad propiedad, Usuario usuario) {
		double precioReserva = calcularPrecioReserva(propiedad, propiedad.getFechaOcupada().getDesde().toString(), propiedad.getFechaOcupada().getHasta().toString());
		double dias = ChronoUnit.DAYS.between(getPropiedadDeLista(propiedad).getFechaOcupada().getDesde(),LocalDate.now());
		if(getPropiedadDeLista(propiedad).getFechaOcupada().getDesde().isAfter(LocalDate.now())) {	
			getPropiedadDeLista(propiedad).setFechaAlquiler("2000-01-01", "2000-01-01");
			getUsuarioDeLista(usuario).terminarReservaActual();
			double precioTotal = precioReserva * getPropiedadDeLista(propiedad).getCancelacion().politicaDeCancelacion(Math.abs(dias));
			return precioTotal;
		}else {
			return 0;
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
		getPropiedadDeLista(propiedad);
		getUsuarioDeLista(usuario);
		
		
		if(getPropiedadDeLista(propiedad) != null & getUsuarioDeLista(usuario) != null & getPropiedadDeLista(propiedad).estaDisponible(desde, hasta)) {
			
			getPropiedadDeLista(propiedad).setFechaAlquiler(desde, hasta);
			getUsuarioDeLista(usuario).setReserva(propiedad);
			return getPropiedadDeLista(propiedad);
			
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
	public Propiedad registrarPropiedadParaAlquilar(Propiedad propiedad) {	
		if(addPropiedad(propiedad)) {
			propiedad.getPropietario().setPropiedadEnAlquiler(propiedad);
			return propiedad;
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
		double montoTotal = getUsuarioDeLista(usuario).getPropiedadesEnAlquiler().stream()
										.mapToDouble(prop -> calcularPrecioReserva(prop, desde, hasta))
										.sum();
		return montoTotal;																	 
	}
	
	// RETORNA EL HISTORIAL DE PROPIEDADES ALQUILADAS POR UN USUARIO 
	public List<Propiedad> todasLasReservas(Usuario usuario){                           // VER
																					    // VER	
		return (List<Propiedad>) this.usuarios.stream()								    // VER
							.filter(usu -> usu.equals(usuario))							// VER
							.map(usu -> usu.getHistorialDeReservas());			        // VER
	}																					// VER
	
	// REGISTRA UN NUEVO USUARIO A LA LISTA DE USUARIOS
	public Usuario registrarUsuario(Usuario usuario) {
		
		if(addUsuario(usuario)) {
			return usuario;
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
