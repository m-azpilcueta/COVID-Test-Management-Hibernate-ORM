package gei.id.tutelado.model;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = "Paciente.recuperarNumPositivosPorNss",
			query = "SELECT p.nss, count(*) FROM Paciente p INNER JOIN p.pruebas pr WHERE p.nss=:nss AND pr.resultado IS TRUE"),
	@NamedQuery(name = "Paciente.recuperarPacienteSinPruebas", 
			query = "SELECT p FROM Paciente p LEFT OUTER JOIN p.pruebas pr WHERE pr IS NULL")
})

@Entity
public class Paciente extends Usuario {
	
	@Column(nullable = false, unique = true, length = 12)
	private String nss;
	
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
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
	
	public void addPrueba(Prueba prueba) {
		if (prueba.getPaciente() != null) throw new RuntimeException("¡Paciente ya asignado!");
		prueba.setPaciente(this);
		this.pruebas.add(prueba);
	}
	
	public void removePrueba(Prueba prueba) {
		if (prueba == null) throw new RuntimeException("¡Intentando eliminar prueba nula!");
		if (!this.pruebas.contains(prueba)) throw new RuntimeException("¡El paciente no tiene la prueba!");
		this.pruebas.remove(prueba);
		prueba.setPaciente(null);
	}
	
	@Override
	public boolean esSanitario() {
		return false;
	}
	
}
