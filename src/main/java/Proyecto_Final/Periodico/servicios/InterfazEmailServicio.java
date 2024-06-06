package Proyecto_Final.Periodico.servicios;

/**
 * Interfaz que define los métodos necesarios para la gestión de correos
 * electrónicos.
 * 
 * @author pabloRG
 */
public interface InterfazEmailServicio {

	/**
	 * Envía un correo electrónico de recuperación de contraseña.
	 * 
	 * @param emailDestino  Dirección de correo electrónico del usuario
	 *                      destinatario.
	 * @param nombreUsuario Nombre del usuario para mostrarlo en el email enviado.
	 * @param token         Token asociado a la recuperación.
	 */
	public void enviarEmailRecuperacion(String emailDestino, String nombreUsuario, String token);

	/**
	 * Envía un correo electrónico de confirmación de cuenta.
	 * 
	 * @param emailUsuario  Dirección de correo electrónico del usuario.
	 * @param nombreUsuario Nombre del usuario para mostrarlo en el email enviado.
	 * @param token         Token asociado a la confirmación de cuenta.
	 */
	public void enviarEmailConfirmacion(String emailUsuario, String nombreUsuario, String token);
}
