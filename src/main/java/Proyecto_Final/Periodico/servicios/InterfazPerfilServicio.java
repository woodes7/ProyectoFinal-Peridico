package Proyecto_Final.Periodico.servicios;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz que define los métodos que debe implementar un servicio para la
 * gestión del perfil de usuario.
 * 
 * @author pabloRG
 */
public interface InterfazPerfilServicio {

	/**
	 * Modifica la foto de perfil de un usuario.
	 * 
	 * @param email El email del usuario cuya foto de perfil se va a modificar.
	 * @param file  El archivo de imagen que se utilizará como nueva foto de perfil.
	 * @return true si se modifica la foto de perfil correctamente, false si ocurre
	 *         algún error.
	 * @throws IOException Si ocurre un error de entrada/salida al procesar el
	 *                     archivo de imagen.
	 */
	boolean modificarFoto(String email, MultipartFile file) throws IOException;

}
