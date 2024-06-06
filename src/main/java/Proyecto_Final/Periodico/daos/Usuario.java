package Proyecto_Final.Periodico.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Entidad que representa un Usuario en la base de datos.
 */
@Entity
@Table(name = "usuarios", schema = "pb_usu")
public class Usuario {

	// ATRIBUTOS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "nombre_Usuario", nullable = false, length = 50)
	private String nombreUsuario;

	@Column(name = "tlf_usuario", nullable = true)
	private String tlfUsuario;

	@Column(name = "email_usuario", nullable = false, unique = true, length = 100)
	private String emailUsuario;

	@Column(name = "clave_usuario", nullable = false)
	private String claveUsuario;

	@Column(name = "foto_perfil")
	private byte[] fotoPerfil;

	@Column(name = "es_verificado")
	private boolean esverificado;

	@Temporal(TemporalType.DATE)
	@Column(name = "fch_alta_usuario", nullable = true, updatable = false)
	private Calendar fchAltaUsuario;

	@Column(name = "token_recuperacion", nullable = true, length = 100)
	private String token;

	@Column(name = "expiracion_token", nullable = true, length = 100)
	private Calendar expiracionToken;

	@Column(name = "bloqueado")
	private boolean bloqueado = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inicio_bloqueo", nullable = true)
	private Calendar inicioBloqueo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fin_bloqueo", nullable = true)
	private Calendar finBloqueo;

	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private List<Articulo> articulos = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false)
	private Rol rol;

	@ManyToOne
	@JoinColumn(name = "id_administrador", referencedColumnName = "id", nullable = true)
	private Usuario administrador;

	// CONSTRUCTOR

	public Usuario() {
		super();

	}

	public Usuario(long id, String nombreUsuario, String tlfUsuario, String emailUsuario, String claveUsuario,
			byte[] fotoPerfil, boolean esverificado, Calendar fchAltaUsuario, String token, Calendar expiracionToken,
			Rol rol) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.tlfUsuario = tlfUsuario;
		this.emailUsuario = emailUsuario;
		this.claveUsuario = claveUsuario;
		this.fotoPerfil = fotoPerfil;
		this.esverificado = esverificado;
		this.fchAltaUsuario = fchAltaUsuario;
		this.token = token;
		this.expiracionToken = expiracionToken;
		this.rol = rol;
	}

	public Usuario(long id, String nombreUsuario, String tlfUsuario, String emailUsuario, String claveUsuario,
			byte[] fotoPerfil, boolean esverificado, Usuario administrador, Calendar fchAltaUsuario, String token,
			Calendar expiracionToken, Rol rol) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.tlfUsuario = tlfUsuario;
		this.emailUsuario = emailUsuario;
		this.claveUsuario = claveUsuario;
		this.fotoPerfil = fotoPerfil;
		this.esverificado = esverificado;
		this.administrador = administrador;
		this.fchAltaUsuario = fchAltaUsuario;
		this.token = token;
		this.expiracionToken = expiracionToken;
		this.rol = rol;
	}
	// GETTERS && SETTERS

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getTlfUsuario() {
		return tlfUsuario;
	}

	public void setTlfUsuario(String tlfUsuario) {
		this.tlfUsuario = tlfUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public boolean isEsverificado() {
		return esverificado;
	}

	public void setEsverificado(boolean esverificado) {
		this.esverificado = esverificado;
	}

	public Calendar getFchAltaUsuario() {
		return fchAltaUsuario;
	}

	public void setFchAltaUsuario(Calendar fchAltaUsuario) {
		this.fchAltaUsuario = fchAltaUsuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getExpiracionToken() {
		return expiracionToken;
	}

	public void setExpiracionToken(Calendar expiracionToken) {
		this.expiracionToken = expiracionToken;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Calendar getInicioBloqueo() {
		return inicioBloqueo;
	}

	public void setInicioBloqueo(Calendar inicioBloqueo) {
		this.inicioBloqueo = inicioBloqueo;
	}

	public Calendar getFinBloqueo() {
		return finBloqueo;
	}

	public void setFinBloqueo(Calendar finBloqueo) {
		this.finBloqueo = finBloqueo;
	}

	public Usuario getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	// METODOS

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fotoPerfil);
		result = prime * result + Objects.hash(administrador, articulos, bloqueado, claveUsuario, emailUsuario,
				esverificado, expiracionToken, fchAltaUsuario, finBloqueo, id, inicioBloqueo, nombreUsuario, rol,
				tlfUsuario, token);
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
		return Objects.equals(administrador, other.administrador) && Objects.equals(articulos, other.articulos)
				&& bloqueado == other.bloqueado && Objects.equals(claveUsuario, other.claveUsuario)
				&& Objects.equals(emailUsuario, other.emailUsuario) && esverificado == other.esverificado
				&& Objects.equals(expiracionToken, other.expiracionToken)
				&& Objects.equals(fchAltaUsuario, other.fchAltaUsuario) && Objects.equals(finBloqueo, other.finBloqueo)
				&& Arrays.equals(fotoPerfil, other.fotoPerfil) && id == other.id
				&& Objects.equals(inicioBloqueo, other.inicioBloqueo)
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(rol, other.rol)
				&& Objects.equals(tlfUsuario, other.tlfUsuario) && Objects.equals(token, other.token);
	}

	//To String
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", tlfUsuario=" + tlfUsuario
				+ ", emailUsuario=" + emailUsuario + ", claveUsuario=" + claveUsuario + ", fotoPerfil="
				+ Arrays.toString(fotoPerfil) + ", esverificado=" + esverificado + ", fchAltaUsuario=" + fchAltaUsuario
				+ ", token=" + token + ", expiracionToken=" + expiracionToken + ", bloqueado=" + bloqueado
				+ ", inicioBloqueo=" + inicioBloqueo + ", finBloqueo=" + finBloqueo + ", articulos=" + articulos
				+ ", rol=" + rol + ", administrador=" + administrador + "]";
	}

}
