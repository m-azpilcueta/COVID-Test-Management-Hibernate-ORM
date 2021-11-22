package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Usuario;

public interface UsuarioDao {
	void setup(Configuracion config);
	
	// Operaciones CRUD
	Usuario almacena(Usuario usuario);
	Usuario modifica(Usuario usuario);
	void elimina(Usuario usuario);
	Usuario recuperaPorDni(String dni);
	
	// Operaciones LAZY
	
	// Operaciones Adicionales
}
