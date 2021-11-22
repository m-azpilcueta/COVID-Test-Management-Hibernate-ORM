package gei.id.tutelado.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = "Prueba.findByCodigo",
			query = "SELECT p from Prueba p WHERE p.codPrueba=:codPrueba"),
	
})

@TableGenerator(name="id_prueba_gen", table = "tabla_ids", pkColumnName = "nombre_id", pkColumnValue = "idPrueba", 
valueColumnName = "ultimo_valor_id", initialValue = 0, allocationSize = 1)
@Entity
public class Prueba implements Comparable<Prueba> {
	@Id
	@GeneratedValue(generator = "id_prueba_gen")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String codPrueba;
	
	@Column(nullable = false, unique = false)
	private String tipo;
	
	@Column(nullable = false, unique = false)
	private LocalDateTime fecha;
	
	@Column(nullable = false, unique = false)
	private String lugar;
	
	@Column(nullable = false, unique = false)
	private String localidad;
	
	@Column(nullable = false, unique = false)
	private String provincia;
	
	// FALTA EL FETCH
	@ManyToOne(cascade = {})
	@JoinColumn(name = "pac_prue", nullable = false, unique = false)
	private Paciente paciente;
	
	@ElementCollection
	@CollectionTable(name = "Sintomas", joinColumns=@JoinColumn(name = "id_prue", nullable = false))
	@Column(name = "sintoma", nullable = false)
	private Set<String> sintomas = new HashSet<String>();
	
	// FALTA EL FETCH
	@ManyToOne(cascade = {})
	@JoinColumn(name = "sanit_prue", nullable = true, unique = false)
	private Sanitario sanitario;
	
	@Column(nullable = true, unique = false)
	private boolean asistencia;
	
	@Column(nullable = true, unique = false)
	private boolean resultado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodPrueba() {
		return codPrueba;
	}

	public void setCodPrueba(String codPrueba) {
		this.codPrueba = codPrueba;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Set<String> getSintomas() {
		return sintomas;
	}

	public void setSintomas(Set<String> sintomas) {
		this.sintomas = sintomas;
	}

	public Sanitario getSanitario() {
		return sanitario;
	}

	public void setSanitario(Sanitario sanitario) {
		this.sanitario = sanitario;
	}

	public boolean isAsistencia() {
		return asistencia;
	}

	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPrueba == null) ? 0 : codPrueba.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prueba other = (Prueba) obj;
		if (codPrueba == null) {
			if (other.codPrueba != null)
				return false;
		} else if (!codPrueba.equals(other.codPrueba))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prueba [id=" + id + ", codPrueba=" + codPrueba + ", tipo=" + tipo + ", fecha=" + fecha + ", lugar="
				+ lugar + ", localidad=" + localidad + ", provincia=" + provincia + ", paciente=" + paciente
				+ ", sintomas=" + sintomas + ", sanitario=" + sanitario + ", asistencia=" + asistencia + ", resultado="
				+ resultado + "]";
	}

	@Override
	public int compareTo(Prueba o) {
		return (this.fecha.isBefore(o.getFecha()) ? -1 : 1);
	}
	
	
	
}
