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
		Cancelacion estricta = new Estricta();
		Cancelacion flexible = new Flexible();
		Cancelacion moderada = new Moderada();
		propiedad = new Propiedad("Casa","lorem",10,"12345",usuario,flexible);
		propiedad1 = new Propiedad("Dpto","lorem",10,"123",usuario1,estricta);
		propiedad2 = new Propiedad("Dpto2","lorem",10,"123",usuario1,moderada);
		oobnb = new OOBnB();
		oobnb.registrarUsuario(usuario1);
		oobnb.registrarUsuario(usuario3);
		propiedad2.getFechaOcupada().setDesde("2023-10-23");
		propiedad2.getFechaOcupada().setHasta("2023-10-30");
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
		assertEquals(listaPropiedades, oobnb.buscarPropiedadesDisponibles("2023-10-24", "2023-10-26"),"Las listas no son iguales");
		oobnb.reservarPropiedad(propiedad1, "2023-10-24", "2023-10-26", usuario3);
		listaPropiedades.clear();
		assertEquals(listaPropiedades, oobnb.buscarPropiedadesDisponibles("2023-10-24", "2023-10-26"),"Las listas no son iguales");
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
	@Test
	void eliminarReservaTestEstricta() {
		oobnb.reservarPropiedad(propiedad1, "2023-10-01", "2023-10-02", usuario3);
		assertEquals(0, oobnb.eliminarReserva(propiedad1, usuario3));	
	}
	@Test
	void eliminarReservaTestModeradaYFlexible() {
		oobnb.reservarPropiedad(propiedad2, "2023-10-30", "2023-11-03", usuario3);
		assertEquals(40, oobnb.eliminarReserva(propiedad2, usuario3));
		oobnb.eliminarReserva(propiedad2, usuario3);
		oobnb.reservarPropiedad(propiedad2, "2023-10-26", "2023-10-29", usuario3);
		assertEquals(15, oobnb.eliminarReserva(propiedad2, usuario3)); // DEFINIR LOS DIAS CORRECTOS EN LA LINEA DE ARRIBA
	}
}


