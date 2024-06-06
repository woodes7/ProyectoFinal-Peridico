package Proyecto_Final.Periodico.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * Entidad que representa un Seccion en la base de datos.
 */
@Entity
@Table(name = "seccion", schema = "pb_operacional")
public class Seccion {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "nombre", nullable = true, unique = true, length = 150)
	private String nombre;

	// CONSTRUCTORES
	public Seccion(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Seccion() {
		super();
	}

	// GETTERS Y SETTERS
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
	public String toString() {
		return "Seccion [id=" + id + ", nombre=" + nombre + "]";
	}

}
