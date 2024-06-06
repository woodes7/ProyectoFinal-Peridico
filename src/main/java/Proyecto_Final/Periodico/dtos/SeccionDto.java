package Proyecto_Final.Periodico.dtos;

import java.util.Objects;
/**
 * Entidad que representa un SeccionDto en la base de datos.
 */
public class SeccionDto {

	private long id;
	private String nombre;

	// CONSTRUCTOR

	public SeccionDto(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public SeccionDto() {
		super();
		// TODO Auto-generated constructor stub
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
		SeccionDto other = (SeccionDto) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "SeccionDto [id=" + id + ", nombre=" + nombre + "]";
	}


}
