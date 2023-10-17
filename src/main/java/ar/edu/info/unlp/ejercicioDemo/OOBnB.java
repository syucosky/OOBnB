package ar.edu.info.unlp.ejercicioDemo;

import java.util.List;

public class OOBnB {
	private List<Usuario> usuarios;
	private List<Propiedad> propiedades;
	
	
	
	public Usuario registrarUsuario(String nombre, String direccion, int dni) {
		Usuario nuevoUsuario =  new Usuario(nombre, direccion, dni);
		
		if(addUsuario(nuevoUsuario)) {
			return nuevoUsuario;
		}else {
			return null;
		}
		
	}
	
	public boolean addUsuario(Usuario usuario) {
		if(this.usuarios.stream().anyMatch(us -> us.getDni() == usuario.getDni())){
			return this.usuarios.add(usuario);
		}else {
			return false;
		}
	}
}
