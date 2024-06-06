package Proyecto_Final.Periodico.dtos;

import java.util.Objects;
/**
 * Entidad que representa un EstadoArticuloDto en la base de datos.
 */
public class EstadoArticuloDto {

	private long id;
	public String nombre;

	// CONSTRUCTOR

	public EstadoArticuloDto(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	// GETTERS & SETTERS

	public EstadoArticuloDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return nombre;
	}

	public void setEstado(String nombre) {
		this.nombre = nombre;
	}

	// METODOS

	@Override
	public int hashCode() {
		return Objects.hash(nombre, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoArticuloDto other = (EstadoArticuloDto) obj;
		return Objects.equals(nombre, other.nombre) && id == other.id;
	}

	@Override
	public String toString() {
		return "EstadoArticuloDto [id=" + id + ", nombre=" + nombre + "]";
	}

}
