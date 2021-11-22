package gei.id.tutelado.model;



import java.time.LocalDate;
import javax.persistence.*;

@TableGenerator(name="id_us_gen", table = "tabla_ids", pkColumnName = "nombre_id", pkColumnValue = "idUser", 
valueColumnName = "ultimo_valor_id", initialValue = 0, allocationSize = 1)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {
	
	@Id
	@GeneratedValue(generator = "id_us_gen")
	private Long id;
	
	@Column(nullable = false, unique = false)
	private String nombre;
	
	@Column(nullable = false, unique = false)
	private String apellidos;
	
	@Column(nullable = false, unique = false)
	private LocalDate fechaNac;
	
	@Column(nullable = false, unique = true, length = 9)
	private String dni;
	
	@Column(nullable = false, unique = false)
	private char sexo;
	
	@Column(nullable = false, unique = false)
	private String calle;
	
	@Column(nullable = false, unique = false)
	private String localidad;
	
	@Column(nullable = false, unique = false)
	private String provincia;
	
	@Column(nullable = false, unique = false, length = 9)
	private String telefono;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Usuario other = (Usuario) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNac=" + fechaNac
				+ ", dni=" + dni + ", sexo=" + sexo + ", calle=" + calle + ", localidad=" + localidad + ", provincia="
				+ provincia + ", telefono=" + telefono + "]";
	}
		
	
	
}
