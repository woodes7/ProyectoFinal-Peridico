package Proyecto_Final.Periodico.daos;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
/**
 * Entidad que representa un EstadoArticulo en la base de datos.
 */
@Entity
@Table(name = "estadoArticulo", schema = "pb_operacional")
public class EstadoArticulo {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	// los estados son borrador, corregido, confirmado, publicado
	@Column(name = "estado", nullable = false, unique = true, length = 150)
	private String estado;

	// CONSTRUCTOR
	public EstadoArticulo() {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	// METODOS
	@Override
	public int hashCode() {
		return Objects.hash(estado, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoArticulo other = (EstadoArticulo) obj;
		return Objects.equals(estado, other.estado) && id == other.id;
	}

	@Override
	public String toString() {
		return "EstadoArticulo [id=" + id + ", estado=" + estado + "]";
	}

}
