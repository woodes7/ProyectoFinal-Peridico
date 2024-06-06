package Proyecto_Final.Periodico.dtos;

import java.util.Objects;
/**
 * Entidad que representa un RolDto en la base de datos.
 */
public class RolDto {

	// ATRIBUTOS

	private long id;
	private String nombre;

	// CONTRUCTORES

	public RolDto(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public RolDto(String nombre) {
		super();
		this.nombre = nombre;
	}

	public RolDto() {
		super();
	}

	// GETTERS & SETTERS

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// METODOS

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolDto other = (RolDto) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}

}
