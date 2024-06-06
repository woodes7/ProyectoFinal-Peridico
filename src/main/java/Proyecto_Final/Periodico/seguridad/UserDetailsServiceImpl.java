package Proyecto_Final.Periodico.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Proyecto_Final.Periodico.daos.Usuario;
import Proyecto_Final.Periodico.repositorios.UsuarioRepositorio;

/**
 * Implementación de la interfaz UserDetailsService de Spring Security para
 * gestionar la carga de detalles del usuario durante la autenticación.
 * 
 * @author pabloRG
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UsuarioRepositorio usuarioRepository;

	/**
	 * Se debe sobrescribir este método de la interface {@link UserDetailsService}
	 * para que Spring se encargue de procesar las solicitudes de autenticación del
	 * usuario. Buscando un usuario por su nombre de usuario y después devolviendo
	 * un objeto de tipo UserDetails para que Spring pueda completar la
	 * autenticación
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Intento de inicio de sesión para el usuario: {}", username);

		// El nombre de usuario en la aplicación es el email
		Usuario user = usuarioRepository.findFirstByEmailUsuario(username);

		// Construir la instancia de UserDetails con los datos del usuario
		UserBuilder builder = null;
		if (user != null) {
			logger.info("Usuario encontrado en la base de datos: {}", user.getEmailUsuario());

			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(user.getClaveUsuario());
			builder.authorities("ROLE_" + user.getRol().getNombre());
		} else {
			logger.error("Usuario no encontrado en la base de datos");
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return builder.build();
	}
}
