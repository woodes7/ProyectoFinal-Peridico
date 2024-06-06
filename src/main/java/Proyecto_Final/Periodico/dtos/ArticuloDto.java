package Proyecto_Final.Periodico.dtos;

import java.util.Calendar;
import java.util.Objects;

import Proyecto_Final.Periodico.daos.Usuario;
/**
 * Entidad que representa un ArtículoDto en la base de datos.
 */
public class ArticuloDto {

	// Atributos
	private long id;
	private String titulo;
	private String contenido;
	private String imagen;
	private Calendar fechaCreacion;
	private Calendar fechaPublicacion;
	private EstadoArticuloDto estado;
	private UsuarioDto usuario;
	private SeccionDto seccion;

	// CONTSTUCTOR

	public ArticuloDto() {
		super();

	}



	public ArticuloDto(long id, String titulo, String contenido, String imagen, Calendar fechaCreacion,
			Calendar fechaPublicacion, EstadoArticuloDto estado, UsuarioDto usuario, SeccionDto seccion) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.imagen = imagen;
		this.fechaCreacion = fechaCreacion;
		this.fechaPublicacion = fechaPublicacion;
		this.estado = estado;
		this.usuario = usuario;
		this.seccion = seccion;
	}
	
	public  ArticuloDto (Calendar fechaCreacion, Calendar fechaPublicacion, EstadoArticuloDto estado,
		UsuarioDto usuario, SeccionDto seccion){
		this.id = id;
		this.titulo = titulo;
		this.fechaCreacion = fechaCreacion;
		this.fechaPublicacion = fechaPublicacion;
		this.estado = estado;
		this.usuario = usuario;
		this.seccion = seccion;
	}
	// GETTERS & SETTERS

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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Calendar getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Calendar fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public EstadoArticuloDto getEstado() {
		return estado;
	}

	public void setEstado(EstadoArticuloDto estado) {
		this.estado = estado;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public SeccionDto getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionDto seccion) {
		this.seccion = seccion;
	}

	// METODOS
	@Override
	public int hashCode() {
		return Objects.hash(contenido, estado, fechaCreacion, fechaPublicacion, id, imagen, seccion, titulo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticuloDto other = (ArticuloDto) obj;
		return Objects.equals(contenido, other.contenido) && Objects.equals(estado, other.estado)
				&& Objects.equals(fechaCreacion, other.fechaCreacion)
				&& Objects.equals(fechaPublicacion, other.fechaPublicacion) && id == other.id
				&& Objects.equals(imagen, other.imagen) && Objects.equals(seccion, other.seccion)
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "ArticuloDto [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", imagen=" + imagen
				+ ", fechaCreacion=" + fechaCreacion + ", fechaPublicacion=" + fechaPublicacion + ", estado=" + estado
				+ ", usuario=" + usuario + ", seccion=" + seccion + "]";
	}

}
