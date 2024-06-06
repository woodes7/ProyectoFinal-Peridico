package Proyecto_Final.Periodico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Usuario;

/**
 * Interfaz que define un repositorio para la entidad {@link Usuario}. Extiende
 * la interfaz JpaRepository de Spring Data para realizar operaciones CRUD y
 * otras consultas relacionadas con la entidad Usuario en la base de datos.
 * 
 * @author pabloRG
 */
@Service
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	/**
	 * Busca al primer usuario que tiene la dirección de correo electrónico
	 * especificada
	 * 
	 * @param email La dirección de correo electrónico del usuario a buscar.
	 * @return El primer usuario encontrado con la dirección de correo electrónico
	 *         especificada o null en caso contrario.
	 */
	public Usuario findFirstByEmailUsuario(String email);

	/**
	 * Busca si un usuario tiene el apodo especificado.
	 * 
	 * @param apodo El APODOW del usuario a buscar.
	 * @return true si el usuario tiene el APODO especificado, false en caso
	 *         contrario.
	 */
	public boolean existsByTlfUsuario(String tlfUsuario);

	/**
	 * Busca un usuario por su token de recuperación.
	 * 
	 * @param token de recuperacion del usuario que se le estableció cuando se
	 *              inicio el proceso de recuperacion
	 * @return el usuario buscado por el token
	 */
	public Usuario findByToken(String token);

	/**
	 * Comprueba si existe un usuario con el nombre de usuario especificado.
	 * 
	 * @param nombreUsuario El nombre de usuario del usuario a buscar.
	 * @return true si existe un usuario con el nombre de usuario especificado,
	 *         false en caso contrario.
	 */
	public boolean existsByNombreUsuario(String nombreUsuario);

	/**
	 * Cuenta el numero de usuarios que coinciden con el rol dado por parametro
	 * 
	 * @param rol El rol del usuario
	 * @return el numero de usuarios que coinciden con el rol
	 */
	@Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
	public int countByRol(@Param("rol") String rol);
}
