package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.UsuarioD;

public interface UsuarioDao {
    	
	void setup (Configuracion config);
	
	// OPERACIONS CRUD BASICAS
	UsuarioD almacena (UsuarioD user);
	UsuarioD modifica (UsuarioD user);
	void elimina (UsuarioD user);	
	UsuarioD recuperaPorNif (String nif);
	
	// OPERACIONS POR ATRIBUTOS LAZY
	UsuarioD restauraEntradasLog (UsuarioD user);   
		// Recibe un usuario coa colección de entradas de log como proxy SEN INICIALIZAR
		// Devolve unha copia do usuario coa colección de entradas de log INICIALIZADA
	
	//QUERIES ADICIONAIS
	List<UsuarioD> recuperaTodos();
}
