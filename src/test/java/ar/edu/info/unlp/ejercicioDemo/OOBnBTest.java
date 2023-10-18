package ar.edu.info.unlp.ejercicioDemo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OOBnBTest {
	private Usuario usuario;
	private Usuario usuario1;
	private Usuario usuario3;
	private OOBnB oobnb;
	private Propiedad propiedad;
	private Propiedad propiedad1;
	private Propiedad propiedad2;
	List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
	
	@BeforeEach
	void setUp() {
		usuario = new Usuario("Propietario","12345",123456);
		usuario1 = new Usuario("Sebas","12345",123);
		usuario3 = new Usuario("Sebas3","123457",1234);
		propiedad = new Propiedad("Casa","lorem",12345,"12345",usuario);
		propiedad1 = new Propiedad("Dpto","lorem",10,"123",usuario1);
		propiedad2 = new Propiedad("Dpto2","lorem",10,"123",usuario1);
		oobnb = new OOBnB();
		oobnb.registrarUsuario(usuario1);
		oobnb.registrarUsuario(usuario3);
		propiedad2.getFechaOcupada().setDesde("2023-10-01");
		propiedad2.getFechaOcupada().setHasta("2023-10-20");
		oobnb.registrarPropiedadParaAlquilar(propiedad1);
		oobnb.registrarPropiedadParaAlquilar(propiedad2);	
		listaPropiedades.add(propiedad1);
	}
	
	@Test
	void addUsuarioTest() {
		assertEquals(true, oobnb.addUsuario(usuario));
		assertEquals(false, oobnb.addUsuario(usuario1));
	}
	@Test
	void getUsuarioDeListaTest() {
		assertEquals(usuario1, oobnb.getUsuarioDeLista(usuario1));
		assertEquals(null, oobnb.getUsuarioDeLista(usuario));
	}
	@Test
	void addPropiedadTest() {
		assertEquals(true, oobnb.addPropiedad(propiedad));
		assertEquals(false, oobnb.addPropiedad(propiedad1));
	}
	@Test
	void getPropiedadDeListaTest() {
		 assertEquals(propiedad1, oobnb.getPropiedadDeLista(propiedad1));
		 assertEquals(null, oobnb.getPropiedadDeLista(propiedad));
	}
	@Test
	void buscarPropiedadDispTest() {
		assertEquals(listaPropiedades, oobnb.buscarPropiedadesDisponibles("2023-10-01", "2023-10-20"),"Las listas no son iguales");
		oobnb.reservarPropiedad(propiedad1, "2023-10-01", "2023-10-20", usuario3);
		listaPropiedades.clear();
		assertEquals(listaPropiedades, oobnb.buscarPropiedadesDisponibles("2023-10-01", "2023-10-20"),"Las listas no son iguales");
		oobnb.eliminarReserva(propiedad1, usuario3);
		listaPropiedades.add(propiedad1);
		assertEquals(listaPropiedades, oobnb.buscarPropiedadesDisponibles("2023-10-01", "2023-10-20"),"Las listas no son iguales");
	}
	@Test
	void calcularPrecioReservaTest() {
		assertEquals(10, oobnb.calcularPrecioReserva(propiedad1, "2023-10-01", "2023-10-02"));
	}
	@Test
	void ingresoTotalPorAlquileresTest() {
		assertEquals(20, oobnb.ingresoTotalPorAlquileres(usuario1, "2023-10-01", "2023-10-02"));
	}
}
