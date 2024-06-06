package Proyecto_Final.Periodico.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.dtos.SeccionDto;
import Proyecto_Final.Periodico.dtos.UsuarioDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
/**
 * Entidad que representa un Artículo en la base de datos.
 */
@Entity
@Table(name = "articulo", schema = "pb_operacional")
public class Articulo {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "titulo", nullable = false, unique = true, length = 200)
	private String titulo;

	@Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
	private String contenido;

	@Column(name = "imagen", nullable = true)
	private byte[] imagen;

	@Column(name = "fch_Creacion", nullable = true, updatable = false)
	private Calendar fchCreacion;

	@Column(name = "fch_publicacion", nullable = true, updatable = false)
	private Calendar fchPublicacion;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
	private Usuario autor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seccion", referencedColumnName = "id", nullable = false)
	private Seccion seccion;

	@ManyToOne(fetch = FetchType.LAZY) // Un artículo puede tener un solo estado
	@JoinColumn(name = "estado_id", referencedColumnName = "id", nullable = false)
	private EstadoArticulo estado;

	// CONSTRUCTORES

	public Articulo() {
		super();

	}

	public Articulo(long id, String titulo, String contenido, byte[] imagen, Calendar fchCreacion,
			Calendar fchPublicacion, Usuario autor, Seccion seccion, EstadoArticulo estado) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.imagen = imagen;
		this.fchCreacion = fchCreacion;
		this.fchPublicacion = fchPublicacion;
		this.autor = autor;
		this.seccion = seccion;
		this.estado = estado;
	}
	
	public  Articulo (long id, String titulo, String contenido, Calendar fchCreacion,
			Calendar fchPublicacion, Usuario autor, Seccion seccion, EstadoArticulo estado){
			this.id = id;
			this.titulo = titulo;
			this.fchCreacion = fchCreacion;
			this.fchPublicacion = fchPublicacion;
			this.estado = estado;
			this.autor = autor;
			this.seccion = seccion;
		}
	
	// GETTERS Y SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Calendar getFchCreacion() {
		return fchCreacion;
	}

	public void setFchCreacion(Calendar fchCreacion) {
		this.fchCreacion = fchCreacion;
	}

	public Calendar getFchPublicacion() {
		return fchPublicacion;
	}

	public void setFchPublicacion(Calendar fchPublicacion) {
		this.fchPublicacion = fchPublicacion;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public EstadoArticulo getEstado() {
		return estado;
	}

	public void setEstado(EstadoArticulo estado) {
		this.estado = estado;
	}

	// METODOS

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(imagen);
		result = prime * result
				+ Objects.hash(autor, contenido, estado, fchPublicacion, id, seccion, titulo);
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
		Articulo other = (Articulo) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(contenido, other.contenido)
				&& Objects.equals(estado, other.estado) && Objects.equals(fchPublicacion, other.fchPublicacion)
				&& id == other.id && Arrays.equals(imagen, other.imagen) 
				&& Objects.equals(seccion, other.seccion) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Articulo [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", imagen="
				+ Arrays.toString(imagen) + ", fchPublicacion=" + fchPublicacion 
				+ ", autor=" + autor + ", seccion=" + seccion + ", estado=" + estado + "]";
	}

}
