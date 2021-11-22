package gei.id.tutelado.model;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.*;

@Entity
public class Paciente extends Usuario {
	
	@Column(nullable = false, unique = true, length = 12)
	private String nss;
	
	// FALTA PONER EL FETCH
	@OneToMany(mappedBy = "paciente", cascade = {CascadeType.REMOVE})
	@OrderBy("fecha DESC")
	private SortedSet<Prueba> pruebas = new TreeSet<Prueba>();

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public SortedSet<Prueba> getPruebas() {
		return pruebas;
	}

	public void setPruebas(SortedSet<Prueba> pruebas) {
		this.pruebas = pruebas;
	}
	
	
}
