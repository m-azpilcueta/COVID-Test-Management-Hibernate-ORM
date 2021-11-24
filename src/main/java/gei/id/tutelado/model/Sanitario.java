package gei.id.tutelado.model;

import javax.persistence.*;

@NamedQuery(name = "Sanitario.recuperarNumPruebas",
			query = "SELECT count(*) FROM Prueba p INNER JOIN p.sanitario s WHERE s.dni=:dni")

@Entity
public class Sanitario extends Usuario {
	
	@Column(nullable = false, unique = true)
	private String codSanitario;
	
	@Column(nullable = false, unique = false)
	private String centro;

	public String getCodSanitario() {
		return codSanitario;
	}

	public void setCodSanitario(String codSanitario) {
		this.codSanitario = codSanitario;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}
	
	@Override
	public boolean esSanitario() {
		return true;
	}
	
}
