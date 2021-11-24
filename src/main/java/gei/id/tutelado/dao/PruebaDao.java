package gei.id.tutelado.dao;

import java.util.Map;

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
	Prueba restauraSintomas(Prueba prueba);
	Prueba restauraPaciente(Prueba prueba);
	
	// Operaciones Adicionales
	Map<String, Integer> recuperaPositivosPorLocalidad(); // AGREGACION
	Map<String, Integer> recuperaLocalidadMasTest(); // SUBCONSULTA
}
