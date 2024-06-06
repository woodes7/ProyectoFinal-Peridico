package Proyecto_Final.Periodico.dtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
/**
 * Entidad que representa un UsuarioDto en la base de datos.
 */
public class UsuarioDto {

	// ATRIBUTOS

	private long id;
	private String nombreUsuario;
	private String tlfUsuario;
	private String emailUsuario;
	private String claveUsuario;
	private String fotoPerfil;
	private RolDto rol;
	private String token;
	private String password;
	private String password2;
	private Calendar expiracionToken;
	private Calendar fechaAlta;
	private boolean confirmada;
	private List<ArticuloDto> listaArticulos = new ArrayList<>();
	private String mensajeError = "aaaaaa";
	private boolean bloqueado = false;
	private Calendar inicioBloqueo;
	private Calendar finBloqueo;	
	private UsuarioDto administrador =null;
	
	// CONSTRUCTOR

	public UsuarioDto() {
		super();

	}

	
	public UsuarioDto(long id, String nombreUsuario, String emailUsuario, String fotoPerfil, RolDto rol) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.fotoPerfil = fotoPerfil;
		this.rol = rol;		
	}
	public UsuarioDto(long id, String nombreUsuario, String emailUsuario, String fotoPerfil, RolDto rol, UsuarioDto administrador) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.fotoPerfil = fotoPerfil;
		this.rol = rol;
		this.administrador = administrador;
	}
	
	// GETTERS & SETTERS
		
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

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public RolDto getRol() {
		return rol;
	}

	public void setRol(RolDto rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Calendar getExpiracionToken() {
		return expiracionToken;
	}

	public void setExpiracionToken(Calendar expiracionToken) {
		this.expiracionToken = expiracionToken;
	}

	public Calendar getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Calendar fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public boolean isConfirmada() {
		return confirmada;
	}

	public void setEsConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}

	public List<ArticuloDto> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos(List<ArticuloDto> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
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

	public void setConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}
	
	public UsuarioDto getAdministrador() {
		return administrador;
	}

	public void setAdministrador(UsuarioDto administrador) {
		this.administrador = administrador;
	}
	// METODOS


	@Override
	public int hashCode() {
		return Objects.hash(administrador, bloqueado, claveUsuario, confirmada, emailUsuario, expiracionToken,
				fechaAlta, finBloqueo, fotoPerfil, id, inicioBloqueo, listaArticulos, mensajeError, nombreUsuario,
				password, password2, rol, tlfUsuario, token);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDto other = (UsuarioDto) obj;
		return Objects.equals(administrador, other.administrador) && bloqueado == other.bloqueado
				&& Objects.equals(claveUsuario, other.claveUsuario) && confirmada == other.confirmada
				&& Objects.equals(emailUsuario, other.emailUsuario)
				&& Objects.equals(expiracionToken, other.expiracionToken) && Objects.equals(fechaAlta, other.fechaAlta)
				&& Objects.equals(finBloqueo, other.finBloqueo) && Objects.equals(fotoPerfil, other.fotoPerfil)
				&& id == other.id && Objects.equals(inicioBloqueo, other.inicioBloqueo)
				&& Objects.equals(listaArticulos, other.listaArticulos)
				&& Objects.equals(mensajeError, other.mensajeError)
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(password, other.password)
				&& Objects.equals(password2, other.password2) && Objects.equals(rol, other.rol)
				&& Objects.equals(tlfUsuario, other.tlfUsuario) && Objects.equals(token, other.token);
	}

	//To String

	@Override
	public String toString() {
		return "UsuarioDto [id=" + id + ", nombreUsuario=" + nombreUsuario + ", tlfUsuario=" + tlfUsuario
				+ ", emailUsuario=" + emailUsuario + ", claveUsuario=" + claveUsuario + ", fotoPerfil=" + fotoPerfil
				+ ", rol=" + rol + ", token=" + token + ", password=" + password + ", password2=" + password2
				+ ", expiracionToken=" + expiracionToken + ", fechaAlta=" + fechaAlta + ", confirmada=" + confirmada
				+ ", listaArticulos=" + listaArticulos + ", mensajeError=" + mensajeError + ", bloqueado=" + bloqueado
				+ ", inicioBloqueo=" + inicioBloqueo + ", finBloqueo=" + finBloqueo + ", administrador=" + administrador
				+ "]";
	}


}
