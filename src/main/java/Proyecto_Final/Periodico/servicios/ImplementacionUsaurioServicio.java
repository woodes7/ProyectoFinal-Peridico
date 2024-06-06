package Proyecto_Final.Periodico.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Rol;
import Proyecto_Final.Periodico.daos.Usuario;

import Proyecto_Final.Periodico.dtos.RolDto;
import Proyecto_Final.Periodico.dtos.UsuarioDto;
import Proyecto_Final.Periodico.repositorios.RolRepositorio;
import Proyecto_Final.Periodico.repositorios.UsuarioRepositorio;
import jakarta.persistence.PersistenceException;

@Service
public class ImplementacionUsaurioServicio implements InterfazUsuarioServicio {

	private static final Logger logger = LoggerFactory.getLogger(ImplementacionsArticuloServicio.class);

	@Autowired
	private UsuarioRepositorio repositorio;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private InterfazEmailServicio emailServicio;

	@Autowired
	private InterfazFotoServicio fotoServicio;

	@Autowired
	private InterfazRolServicio rolServicio;

	@Autowired
	private RolRepositorio rolRepositorio;

	@Override
	public UsuarioDto registro(UsuarioDto userDto, Long idAdmin) { // <--- Modificación: Añadido el parámetro idAdmin
	    try {
	        // Comprueba si ya existe un usuario con el email que quiere registrar
	        Usuario usuarioDaoByEmail = repositorio.findFirstByEmailUsuario(userDto.getEmailUsuario());

	        logger.info("Iniciando el método registro");

	        if (usuarioDaoByEmail != null && usuarioDaoByEmail.isEsverificado()) {
	            userDto.setMensajeError("Usuario ya registrado y confirmado");
	            return userDto;
	        }
	        if (usuarioDaoByEmail != null) { // El email se encuentra registrado sin confirmar
	            return null;
	        }
	        userDto.setClaveUsuario(passwordEncoder.encode(userDto.getClaveUsuario()));
	        // Si continua la ejecución es que el email no se encuentra ya registrado
	        logger.info("Antes de dar rol");
	        Usuario usuarioDao = usuarioToDao(userDto);

	        RolDto rolDto = new RolDto(4, "USER");
	        logger.info("Después de obtener y asignar el rolDto rol");
	        // rolServicio.obtenerRolPorId
	        if (rolDto == null) {
	            throw new RuntimeException("No se pudo obtener el rol USER desde la base de datos");
	        }
	        logger.info("Antes de convertir el rolDto a rol para asignárselo a usuarioDao");
	        Rol rol = rolServicio.RolToDao(rolDto);
	        usuarioDao.setRol(rol);
	        logger.info("Rol asignado a usuarioDao");

	        usuarioDao.setFchAltaUsuario(Calendar.getInstance());
	        // usuarioDao.setRol(new Rol("USER"));

	        byte[] fotoPredeterminada = fotoServicio.cargarFotoPredeterminada();
	        usuarioDao.setFotoPerfil(fotoPredeterminada);

	        // Asignar el ID del administrador si es proporcionado
	        if (idAdmin != null) { // <--- Modificación: Asignar el idAdmin si no es null
	            usuarioDao.setAdministrador(usuarioDao); // <--- Modificación: Guardar el idAdmin en el usuario
	        }

	        if (userDto.isConfirmada()) {
	            usuarioDao.setEsverificado(true);
	            repositorio.save(usuarioDao);
	        } else {
	            usuarioDao.setEsverificado(false);
	            // Generar token de confirmación
	            String token = passwordEncoder.encode(RandomStringUtils.random(30));
	            usuarioDao.setToken(token);

	            // Guardar el usuario en la base de datos
	            repositorio.save(usuarioDao);

	            // Enviar el correo de confirmación
	            String nombreUsuario = usuarioDao.getNombreUsuario();
	            emailServicio.enviarEmailConfirmacion(userDto.getEmailUsuario(), nombreUsuario, token);
	        }

	        return userDto;
	    } catch (IllegalArgumentException iae) {
	        logger.error(
	                "[Error ImplementacionUsuarioServicio - registrarUsuario()] Argumento no válido al registrar usuario ",
	                iae);
	    } catch (PersistenceException e) {
	        logger.error(
	                "[Error ImplementacionUsuarioServicio - registrarUsuario()] Error de persistencia al registrar usuario ",
	                e);
	    }
	    return null;
	}

	
	/**
	 * Método que inicializa el superadministrador si no existe.
	 * Crea un rol "SUPERADMIN" si no está presente en la base de datos.
	 * Luego, verifica si ya existe un usuario superadministrador.
	 * Si no existe, crea un nuevo usuario con el rol de superadministrador.
	 */
	private void inicializarSuperAdmin() {
		try {
			logger.info("Iniciando el método inicializarSuperAdmin");
			// Crea un rol "SUPERADMIN" si no existe
			Rol rol = rolRepositorio.findByNombre("SUPERADMIN");
			if (rol == null) {
				rol = new Rol();
				rol.setNombre("SUPERADMIN");
				rolRepositorio.save(rol);
			}

			// Comprueba si ya existe un usuario superadmin
			if (!repositorio.existsByNombreUsuario("superadmin")) {
				// Si no existe, crea un nuevo usuario con rol de superadmin
				Usuario superadmin = new Usuario();
				superadmin.setNombreUsuario("superadmin");

				// Codifica la contraseña antes de establecerla en el usuario
				String passwordEncoded = passwordEncoder.encode("superadmin");
				superadmin.setClaveUsuario(passwordEncoded);

				superadmin.setEmailUsuario("superadmin@example.com");
				superadmin.setTlfUsuario("superadmin");
				superadmin.setRol(rol); // Establece el rol "SUPERADMIN"

				repositorio.save(superadmin);
			}
		} catch (Exception ex) {
			// Manejo de excepciones: registra el error en el logger
			logger.error("Error al inicializar usuario superadmin: " + ex.getMessage());
			ex.printStackTrace(); // Opcional: imprime el stack trace de la excepción
		}
	}

	@Override
	public void cambiarRol(Long userId, Long roleId) {
		try {
			logger.info("Iniciando el método cambiarRol");
			Usuario usuario = repositorio.findById(userId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			Rol nuevoRol = rolRepositorio.findById(roleId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
			usuario.setRol(nuevoRol);
			repositorio.save(usuario);
		} catch (Exception e) {
			logger.error("[Error ImplementacionUsuarioServicio - cambiarRol()] Error al cambiar el rol: ", e);
		}
	}

	/**
	 * Método que se ejecuta cuando la aplicación está lista. Inicializa al
	 * superadministrador después de registrar que la aplicación está lista.
	 */
	/*
	 * @EventListener(ApplicationReadyEvent.class) public void onApplicationReady()
	 * { logger.info("La aplicación está lista"); inicializarSuperAdmin(); }
	 */

	@Override
	public UsuarioDto buscarPorId(long id) {
		try {
			logger.info("Iniciando el método buscarPorId");
			Usuario usuario = repositorio.findById(id).orElse(null);
			if (usuario != null) {
				return usuarioToDto(usuario);
			}
		} catch (IllegalArgumentException e) {
			logger.error("[Error ImplementacionUsuarioServicio - buscarPorId()] Al buscar el usuario por su id ", e);
		}
		return null;
	}

	@Override
	public UsuarioDto buscarPorEmail(String email) {
		try {
			logger.info("Iniciando el método buscarPorEmail");
			List<UsuarioDto> usuarios = new ArrayList<>();
			Usuario usuario = repositorio.findFirstByEmailUsuario(email);

			if (usuario != null) {
				// Convierte el usuario a DTO
				UsuarioDto usuarioDto = usuarioToDto(usuario);

				return usuarioDto;
			}
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - buscarUsuariosPorEmail()] Al buscar el usuario por su email ",
					e);
		}
		return null;
	}

	@Override
	public boolean buscarPorTelefono(String tlfUsuario) {
		try {
			logger.info("Iniciando el método buscarPorTelefono");
			return repositorio.existsByTlfUsuario(tlfUsuario);
		} catch (Exception e) {
			logger.error("[Error ImplementacionUsuarioServicio - buscarPorTelefono()] Error al buscar por teléfono: ",
					e);
			return false;
		}
	}

	@Override
	public List<UsuarioDto> buscarTodos() {
		try {
			logger.info("Iniciando el método buscarTodos");
			return listaUsuarioToDto(repositorio.findAll());
		} catch (Exception e) {
			logger.error("[Error ImplementacionUsuarioServicio - buscarTodos()] Error al buscar todos los usuarios: ",
					e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<UsuarioDto> buscarTodosListaVista() {
		try {
			logger.info("Iniciando el método buscarTodosListaVista");
			List<Usuario> usuarios = repositorio.findAll();

			// Convertir la lista de entidades Usuario a una lista de DTO UsuarioDto
			List<UsuarioDto> usuariosDto = listaUsuarioToDto(usuarios);

			// Filtrar la lista para eliminar a los usuarios con rol "SUPERADMIN"
			usuariosDto = usuariosDto.stream()
					.filter(usuarioDto -> !usuarioDto.getRol().getNombre().equals("SUPERADMIN"))
					.collect(Collectors.toList());

			logger.info("Lista de usuarios filtrada: " + usuariosDto);

			return usuariosDto;
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - buscarTodosListaVista()] Error al buscar todos los usuarios: ",
					e);
			return new ArrayList<>();
		}
	}

	@Override
	public UsuarioDto obtenerUsuarioPorToken(String token) {
		try {
			logger.info("Iniciando el método obtenerUsuarioPorToken");
			Usuario usuarioExistente = repositorio.findByToken(token);

			if (usuarioExistente != null) {
				UsuarioDto usuario = usuarioToDto(usuarioExistente);
				return usuario;
			} else {
				logger.error("No existe el usuario con el token " + token);
				return null;
			}
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - obtenerUsuarioPorToken()] Error al obtener usuario por token: ",
					e);
			return null;
		}
	}

	@Override
	public boolean iniciarResetPassConEmail(String emailUsuario) {
		try {
			logger.info("Iniciando el método iniciarResetPassConEmail");
			Usuario usuarioExistente = repositorio.findFirstByEmailUsuario(emailUsuario);

			if (usuarioExistente != null) {
				// Generar el token y establecer la fecha de expiración
				String token = passwordEncoder.encode(RandomStringUtils.random(30));
				Calendar fechaExpiracion = Calendar.getInstance();
				fechaExpiracion.add(Calendar.MINUTE, 10);
				// Actualizar el usuario con el nuevo token y la fecha de expiración
				usuarioExistente.setToken(token);
				usuarioExistente.setExpiracionToken(fechaExpiracion);

				// Actualizar el usuario en la base de datos
				repositorio.save(usuarioExistente);

				// Enviar el correo de recuperación
				String nombreUsuario = usuarioExistente.getNombreUsuario();
				emailServicio.enviarEmailRecuperacion(emailUsuario, nombreUsuario, token);

				return true;

			} else {
				logger.error("[Error ImplementacionUsuarioServicio - iniciarResetPassConEmail()] El usuario con email "
						+ emailUsuario + " no existe");
				return false;
			}
		} catch (IllegalArgumentException iae) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - iniciarResetPassConEmail()] Argumento no válido al iniciar recuperación con email: ",
					iae);
			return false;
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - iniciarResetPassConEmail()] Error al iniciar recuperación con email: ",
					e);
			return false;
		}
	}

	@Override
	public boolean modificarContraseñaConToken(UsuarioDto usuario) {
		try {
			logger.info("Iniciando el método modificarContraseñaConToken");
			Usuario usuarioExistente = repositorio.findByToken(usuario.getToken());

			if (usuarioExistente != null) {
				String nuevaContraseña = passwordEncoder.encode(usuario.getPassword());
				usuarioExistente.setClaveUsuario(nuevaContraseña);
				usuarioExistente.setToken(null); // Se setea a null para invalidar el token ya consumido al cambiar de
													// contraseña
				repositorio.save(usuarioExistente);

				return true;
			} else {
				logger.error(
						"[Error ImplementacionUsuarioServicio - modificarContraseñaConToken()] No se encontró ningún usuario con el token proporcionado.");
				return false;
			}
		} catch (IllegalArgumentException iae) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - modificarContraseñaConToken()] Argumento no válido al modificar la contraseña con el token: ",
					iae);
			return false;
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - modificarContraseñaConToken()] Error al modificar la contraseña con el token: ",
					e);
			return false;
		}
	}

	@Override
	public void modificarUsuario(UsuarioDto usuarioDto) {
		try {
			logger.info("Iniciando el método modificarUsuario");
			// Convertir el UsuarioDto a una entidad de Usuario antes de guardar
			Usuario usuario = usuarioToDao(usuarioDto);
			// Guardar la entidad actualizada en la base de datos
			repositorio.save(usuario);
		} catch (IllegalArgumentException iae) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - modificarUsuario()] Argumento no válido al modificar el usuario con ID: "
							+ usuarioDto.getId(),
					iae);
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - modificarUsuario()] Error al modificar el usuario con ID: "
							+ usuarioDto.getId(),
					e);
		}
	}

	@Override
	public void eliminar(long id) {
		try {
			logger.info("Iniciando el método eliminar");
			Optional<Usuario> usuarioOptional = repositorio.findById(id);
			if (usuarioOptional.isPresent()) {
				Usuario usuario = usuarioOptional.get();
				repositorio.delete(usuario);
			} else {
				logger.error(
						"[Error ImplementacionUsuarioServicio - eliminar()] No se encontró ningún usuario con el ID proporcionado: "
								+ id);
			}
		} catch (IllegalArgumentException e) {
			logger.error("[Error ImplementacionUsuarioServicio - eliminar()] Al eliminar el usuario por su ID: " + id,
					e);
		}
	}

	@Override
	public boolean confirmarCuenta(String token) {
		try {
			logger.info("Iniciando el método confirmarCuenta");
			Usuario usuarioExistente = repositorio.findByToken(token);

			if (usuarioExistente != null && !usuarioExistente.isEsverificado()) {
				// Entra en esta condición si el usuario existe y su cuenta no se ha confirmado
				usuarioExistente.setEsverificado(true);
				usuarioExistente.setToken(null);
				repositorio.save(usuarioExistente);

				return true;
			} else {
				logger.error("La cuenta no existe o ya está confirmada");
				return false;
			}
		} catch (IllegalArgumentException iae) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - confirmarCuenta()] Error al confirmar la cuenta con token: "
							+ token,
					iae);
			return false;
		} catch (PersistenceException e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - confirmarCuenta()] Error de persistencia al confirmar la cuenta con token: "
							+ token,
					e);
			return false;
		}
	}

	@Override
	public boolean CuentaConfirmada(String email) {
		try {
			logger.info("Iniciando el método CuentaConfirmada");
			Usuario usuarioExistente = repositorio.findFirstByEmailUsuario(email);
			if (usuarioExistente != null && usuarioExistente.isEsverificado()) {
				return true;
			}
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - CuentaConfirmada()] Error al comprobar si la cuenta con email "
							+ email + " ya ha sido confirmada",
					e);
		}
		return false;
	}

	@Override
	public Usuario usuarioToDao(UsuarioDto usuarioDto) {
		try {
			logger.info("Iniciando el método usuarioToDao");
			Usuario usuarioDao = new Usuario();
			usuarioDao.setId(usuarioDto.getId());
			usuarioDao.setNombreUsuario(usuarioDto.getNombreUsuario());
			usuarioDao.setTlfUsuario(usuarioDto.getTlfUsuario());
			usuarioDao.setEmailUsuario(usuarioDto.getEmailUsuario());
			usuarioDao.setClaveUsuario(usuarioDto.getClaveUsuario());

			byte[] imgBytes = fotoServicio.convertirAarrayBytes(usuarioDto.getFotoPerfil());
			usuarioDao.setFotoPerfil(imgBytes);

			usuarioDao.setEsverificado(usuarioDto.isConfirmada());
			usuarioDao.setFchAltaUsuario(usuarioDto.getFechaAlta());
			usuarioDao.setBloqueado(usuarioDto.isBloqueado());
			usuarioDao.setInicioBloqueo(usuarioDto.getInicioBloqueo());
			usuarioDao.setFinBloqueo(usuarioDto.getFinBloqueo());
			usuarioDao.setToken(usuarioDto.getToken());
			usuarioDao.setExpiracionToken(usuarioDto.getExpiracionToken());
			RolDto rolDto = usuarioDto.getRol();
			Rol rol = rolServicio.RolToDao(rolDto);
			usuarioDao.setRol(rol);

			return usuarioDao;
		} catch (Exception e) {
			logger.error(
					"[ERROR UsuarioToDaoImpl - usuarioToDao()] - Error al convertir usuarioDTO a usuarioDAO (return null): ",
					e);
			return null;
		}
	}

	/**
	 * Convierte una lista de DTO de usuario a una lista de entidades de usuario.
	 * 
	 * @param listaUsuarioDto La lista de DTO de usuario a convertir.
	 * @return La lista de entidades de usuario.
	 */
	private List<Usuario> listUsuarioToDao(List<UsuarioDto> listaUsuarioDto) {
		try {
			logger.info("Iniciando el método listUsuarioToDao");
			List<Usuario> listaUsuarioDao = new ArrayList<>();
			for (UsuarioDto usuarioDTO : listaUsuarioDto) {
				listaUsuarioDao.add(usuarioToDao(usuarioDTO));
			}
			return listaUsuarioDao;
		} catch (Exception e) {
			logger.error(
					"[ERROR UsuarioToDaoImpl - listUsuarioToDao()] - Error al convertir lista de usuarioDTO a lista de usuarioDAO (return null): ",
					e);
			return null;
		}
	}

	@Override
	public UsuarioDto usuarioToDto(Usuario usuarioDao) {
		try {
			logger.info("Iniciando el método usuarioToDto");
			UsuarioDto dto = new UsuarioDto();

			// Obtener datos de usuario
			dto.setNombreUsuario(usuarioDao.getNombreUsuario());
			dto.setTlfUsuario(usuarioDao.getTlfUsuario());
			dto.setEmailUsuario(usuarioDao.getEmailUsuario());
			dto.setClaveUsuario(usuarioDao.getClaveUsuario());
			dto.setToken(usuarioDao.getToken());
			dto.setFechaAlta(usuarioDao.getFchAltaUsuario());
			dto.setBloqueado(usuarioDao.isBloqueado());
			dto.setInicioBloqueo(usuarioDao.getInicioBloqueo());
			dto.setFinBloqueo(usuarioDao.getFinBloqueo());
			dto.setEsConfirmada(usuarioDao.isEsverificado());
			dto.setExpiracionToken(usuarioDao.getExpiracionToken());
			dto.setId(usuarioDao.getId());

			// Convertir foto si existe
			if (usuarioDao.getFotoPerfil() != null) {
				String fotoBase64 = fotoServicio.convertirAbase64(usuarioDao.getFotoPerfil());
				dto.setFotoPerfil(fotoBase64);
			}

			// Convertir rol a DTO
			RolDto rolDto = rolServicio.RolToDto(usuarioDao.getRol());
			dto.setRol(rolDto);

			return dto;
		} catch (Exception e) {
			logger.error(
					"[ERROR UsuarioToDtoImpl - usuarioToDto()] - Error al convertir usuario DAO a usuarioDTO (return null): ",
					e);
			return null;
		}
	}

	@Override
	public int contarUsuariosPorRol(String rol) {
		try {
			logger.info("Iniciando el método contarUsuariosPorRol");
			return repositorio.countByRol(rol);
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - contarUsuariosPorRol()] Error al contar usuarios por rol: "
							+ rol,
					e);
			return -1;
		}
	}

	/**
	 * Convierte una lista de entidades de usuario a una lista de DTO de usuario.
	 * 
	 * @param listaUsuarioDao La lista de entidades de usuario a convertir.
	 * @return La lista de DTO de usuario.
	 */
	private List<UsuarioDto> listaUsuarioToDto(List<Usuario> listaUsuarioDao) {
		try {
			logger.info("Iniciando el método listaUsuarioToDto");
			List<UsuarioDto> listaDto = new ArrayList<>();
			for (Usuario u : listaUsuarioDao) {
				listaDto.add(this.usuarioToDto(u));
			}
			return listaDto;
		} catch (Exception e) {
			logger.error(
					"[ERROR UsuarioToDtoImpl - listaUsuarioToDto()] - Error al convertir lista de usuario DAO a lista de usuarioDTO (return null): ",
					e);
			return null;
		}
	}

	@Override
	public void cambiarEstadoBloqueo(Long userId, boolean bloqueado) {
		try {
			logger.info("Iniciando el método cambiarEstadoBloqueo");
			Usuario usuario = repositorio.findById(userId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			usuario.setBloqueado(bloqueado);
			repositorio.save(usuario);
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - cambiarEstadoBloqueo()] Error al cambiar el estado de bloqueo para el usuario con ID: "
							+ userId,
					e);
		}
	}

	@Override
	public void cambiarFechasBloqueo(Long userId, String inicioBloqueo, String finBloqueo) {
		try {
			logger.info("Iniciando el método cambiarFechasBloqueo");
			Usuario usuario = repositorio.findById(userId)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			usuario.setInicioBloqueo(Calendar.getInstance());
			usuario.setFinBloqueo(Calendar.getInstance());
			usuario.getInicioBloqueo().setTime(java.sql.Date.valueOf(inicioBloqueo));
			usuario.getFinBloqueo().setTime(java.sql.Date.valueOf(finBloqueo));
			repositorio.save(usuario);
		} catch (Exception e) {
			logger.error(
					"[Error ImplementacionUsuarioServicio - cambiarFechasBloqueo()] Error al cambiar las fechas de bloqueo para el usuario con ID: "
							+ userId,
					e);
		}
	}

}
