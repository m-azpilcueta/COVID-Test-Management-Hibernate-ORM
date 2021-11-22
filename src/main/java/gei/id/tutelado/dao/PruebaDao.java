package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Prueba;

public interface PruebaDao {
void setup(Configuracion config);
	
	// Operaciones CRUD
	Prueba almacena(Prueba prueba);
	Prueba modifica(Prueba prueba);
	void elimina(Prueba prueba);
	Prueba recuperaPorCod(String cod);
	
	// Operaciones LAZY
	
	// Operaciones Adicionales
}
