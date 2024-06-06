package Proyecto_Final.Periodico.controladores;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Proyecto_Final.Periodico.daos.Rol;
import Proyecto_Final.Periodico.dtos.UsuarioDto;
import Proyecto_Final.Periodico.repositorios.RolRepositorio;
import Proyecto_Final.Periodico.servicios.InterfazFotoServicio;
import Proyecto_Final.Periodico.servicios.InterfazUsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsuarioControlador {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

	@Autowired
	private InterfazUsuarioServicio usuarioServicio;
	@Autowired
	private InterfazFotoServicio fotoServicio;
	@Autowired
	private RolRepositorio RolServicio;

    /**
     * Gestiona la solicitud HTTP GET para la url /privada/editarPerfil y muestra el
     * formulario para editar el perfil del usuario.   
     * @param model  Modelo que se utiliza para enviar el usuarioDTO a la vista.
     * @param authentication Objeto Authentication que contiene el nombre de usuario.
     * @return La vista de edición de perfil (editarPerfil.html).
     */
    @GetMapping("/privada/editarPerfil")
    public String mostrarFormularioEdicionPerfil(Model model, Authentication authentication) {
        logger.info("Iniciando formulario de edición de perfil.");
        try {
            UsuarioDto usuarioDto = usuarioServicio.buscarPorEmail(authentication.getName());
            String fotoPerfil = usuarioDto.getFotoPerfil(); // Obtener la foto de perfil en bytes
            model.addAttribute("usuarioDto", usuarioDto);
            model.addAttribute("fotoPerfilBase64", fotoPerfil); // Agregar la foto de perfil a la vista
            return "editarPerfil";
        } catch (Exception e) {
            logger.error("Error al mostrar formulario de edición de perfil: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "home"; // Otra vista adecuada si hay un error
        }
    }

    /**
     * Gestiona la solicitud HTTP POST para editar el perfil del usuario.     
     * @param usuarioDto     Objeto UsuarioDto que contiene los datos actualizados del usuario.
     * @param archivoFoto    Archivo de imagen que contiene la nueva foto de perfil del usuario.
     * @param model          Modelo que se utiliza para enviar mensajes de éxito o
     *                       error.
     * @return Redirecciona a la página de edición de perfil.
     */
    @PostMapping("/privada/editarPerfilpost")
    public String editarPerfil(@ModelAttribute UsuarioDto usuarioDto, @RequestParam("archivo") MultipartFile archivoFoto,
            Model model) {
        logger.info("Iniciando proceso de edición de perfil.");
        try {
            // Obtener el usuario actual de la base de datos
            UsuarioDto usuarioActualDto = usuarioServicio.buscarPorId(usuarioDto.getId());

            // Comprobar si hay datos nuevos en el objeto UsuarioDto
            if (usuarioDto.getNombreUsuario() != null && !usuarioDto.getNombreUsuario().isEmpty()) {
                usuarioActualDto.setNombreUsuario(usuarioDto.getNombreUsuario());
            }
            if (usuarioDto.getEmailUsuario() != null && !usuarioDto.getEmailUsuario().isEmpty()) {
                usuarioActualDto.setEmailUsuario(usuarioDto.getEmailUsuario());
            }
            if (usuarioDto.getTlfUsuario() != null && !usuarioDto.getTlfUsuario().isEmpty()) {
                usuarioActualDto.setTlfUsuario(usuarioDto.getTlfUsuario());
            }
            if (!archivoFoto.isEmpty()) {
                // Convertir la foto a base64
                byte[] fotoBytes = archivoFoto.getBytes();
                String fotoBase64 = Base64.getEncoder().encodeToString(fotoBytes);
                // Establecer la foto en el DTO
                usuarioActualDto.setFotoPerfil(fotoBase64);
            }

            // Modificar el usuario con los datos actualizados
            usuarioServicio.modificarUsuario(usuarioActualDto);

            logger.info("Usuario modificado correctamente: {}", usuarioActualDto.getId());
            model.addAttribute("successMessage", "¡Perfil actualizado correctamente!");
            return "redirect:/privada/editarPerfil"; // Redirigir para evitar reenvío del formulario
        } catch (Exception e) {
            logger.error("Error al modificar el usuario: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al modificar el perfil del usuario");
            return "editarPerfil";
        }
    }

    /**
     * Gestiona la solicitud HTTP GET para la url /privada/listado y muestra el
     * listado de usuarios.     *
     * @param model Modelo que se utiliza para enviar el listado del usuarios a la vista.
     * @param request HttpServletRequest para comprobar el rol del usuario.
     * @param authentication Objeto Authentication que contiene el nombre de usuario.
     * @return La vista del listado de usuarios (listado.html).
     */
    @GetMapping("/privada/listado")
    public String listadoUsuarios(Model model, HttpServletRequest request, Authentication authentication) {
        logger.info("Iniciando listado de usuarios.");
        try {
            UsuarioDto usuarioLogeado = usuarioServicio.buscarPorEmail(authentication.getName());
            model.addAttribute("usuarioLogeado", usuarioLogeado);

            List<UsuarioDto> listUsuariosDto = usuarioServicio.buscarTodosListaVista();
            model.addAttribute("usuarios", listUsuariosDto);
            model.addAttribute("usuLogeado", usuarioLogeado);

            if (request.isUserInRole("ADMIN") || request.isUserInRole("SUPERADMIN")) {
                return "listado";
            }
            model.addAttribute("noAdmin", "No eres admin");
            model.addAttribute("nombreUsuario", authentication.getName());
            return "home";
        } catch (Exception e) {
            logger.error("Error al cargar el listado de usuarios: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al cargar el listado de usuarios.");
            return "error";
        }
    }
	
    /**
     * Gestiona la solicitud HTTP POST para cambiar el rol de un usuario.
     *
     * @param userId  ID del usuario cuyo rol se va a cambiar.
     * @param roleId  ID del nuevo rol que se asignará al usuario.
     * @param model   Modelo utilizado para enviar mensajes a la vista.
     * @return Redirige a la página de listado de usuarios después de realizar la operación.
     */
    @PostMapping("/privada/cambiarRol")
    public String cambiarRolUsuario(@RequestParam Long userId, @RequestParam Long roleId, Model model) {
        try {
            // Registro del inicio del método en el log
            logger.info("Iniciando cambio de rol de usuario.");
            usuarioServicio.cambiarRol(userId, roleId);
            model.addAttribute("successMessage", "¡Rol del usuario actualizado correctamente!");
        } catch (Exception e) {
            // Registro de errores en el log en caso de excepción
            logger.error("Error al cambiar el rol del usuario: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Error al cambiar el rol del usuario.");
        }
        return "redirect:/privada/listado";
    }


	/**
	 * Gestiona la solicitud HTTP GET para la url /privada/eliminar-usuario/{id} y
	 * elimina al usuario con el ID proporcionado.	 *
	 * @param id ID del usuario a eliminar.
	 * @param model Modelo que se utiliza para enviar mensajes y el listado actualizado a la vista.
	 * @param request HttpServletRequest para comprobar el rol del usuario.
	 * @return La vista de administración de usuarios con el resultado de la eliminación.
	 */
	@GetMapping("/privada/eliminarUsuario/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
	    try {
	        // Registro del inicio del método en el log
	        logger.info("Iniciando eliminación de usuario.");
	        // Búsqueda y almacenamiento del usuario a eliminar utilizando el método buscarPorId()
	        UsuarioDto usuarioDto = usuarioServicio.buscarPorId(id); // Referencia 1
	        List<UsuarioDto> usuarios = usuarioServicio.buscarTodos();

	        String emailUsuarioActual = request.getUserPrincipal().getName();

	        if (!(request.isUserInRole("ADMIN") || request.isUserInRole("SUPERADMIN"))) {
	            model.addAttribute("noAdmin", "No tiene los permisos suficientes para acceder al recurso");
	            model.addAttribute("usuarios", usuarios);
	            return "home";
	        } else if (emailUsuarioActual.equals(usuarioDto.getEmailUsuario())) {
	            model.addAttribute("noTePuedesEliminar", "No puedes eliminarte a ti mismo como administrador");
	            model.addAttribute("usuarios", usuarios);
	            return "redirect:/privada/listado";
	        } else {
	            // Eliminación del usuario utilizando el método eliminar() con el identificador
	            // almacenado en 'id'
	            usuarioServicio.eliminar(id); // Referencia 2
	            model.addAttribute("eliminado", "Usuario eliminado correctamente");
	            return "redirect:/privada/listado";
	        }
	    } catch (Exception e) {
	        // Registro de errores en el log en caso de excepción
	        logger.error("Error al eliminar el usuario: {}", e.getMessage(), e);
	        model.addAttribute("error", "Se produjo un error al eliminar el usuario");
	        return "listado";
	    }
	}

	/**
	 * Gestiona la solicitud HTTP GET para la url /privada/crearUsuario y muestra el
	 * formulario para crear un nuevo usuario.	 *
	 * @param model Modelo que se utiliza para agregar el ID del administrador logueado.
	 * @param authentication Objeto Authentication que contiene la información de autenticación del usuario.
	 * @return La página HTML del formulario de creación de usuario.
	 */
	@GetMapping("/privada/crearUsuario")    
	public String mostrarFormularioCrearUsuario(Model model, Authentication authentication) {
	    // Registro del inicio del método en el log
	    logger.info("Mostrando formulario de creación de usuario.");
	    // Obtener el usuario logueado por email
	    UsuarioDto usuarioLogeado = usuarioServicio.buscarPorEmail(authentication.getName());    
	    
	    // Establecer el ID del administrador logueado en el nuevo usuarioDto
	    model.addAttribute("idAdmin", usuarioLogeado.getId());

	    // Retornar el nombre de la plantilla Thymeleaf que contiene el formulario
	    return "crearUsuario"; // Nombre de la plantilla HTML
	}

	/**
	 * Gestiona la solicitud HTTP POST para crear un nuevo usuario por parte del administrador.	 
	 * @param usuarioDto Objeto UsuarioDto que contiene la información del nuevo usuario.
	 * @param idAdmin ID del administrador que crea el nuevo usuario.
	 * @param model Modelo que se utiliza para agregar mensajes de éxito o error.
	 * @return Redirecciona a la página de listado de usuarios o vuelve al formulario de creación en caso de error.
	 */
	@PostMapping("/privada/crearUsuario")
	public String crearUsuarioPorAdmin(@ModelAttribute UsuarioDto usuarioDto, 
	        @RequestParam("idAdministrador") Long idAdmin, Model model) {
	    try {
	        boolean esVerificado = false;
	        UsuarioDto nuevoUsuario = usuarioServicio.registro(usuarioDto, idAdmin);
	        UsuarioDto checkUser = usuarioServicio.buscarPorEmail(usuarioDto.getEmailUsuario());
	        if (checkUser != null) {
	            esVerificado = checkUser.isConfirmada();
	        }

	        if (nuevoUsuario == null) {
	            model.addAttribute("usuarioRegistradoPeroNoConfirmado", "Ya existe un usuario con ese email sin confirmar");
	            return "/privada/listado";
	        } else if (esVerificado) {
	            model.addAttribute("emailYaRegistrado", "Ya existe un usuario con ese email");
	            return "/privada/listado";
	        } else {
	            model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
	            return "redirect:/privada/listado";
	        }
	    } catch (Exception e) {
	        // Registro de errores en el log en caso de excepción
	        logger.error("Error al procesar la solicitud de creación de usuario: {}", e.getMessage(), e);
	        model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	        return "crearUsuario";
	    }
	}
	/**
	 * Gestiona la solicitud HTTP POST para cambiar el estado de bloqueo de un usuario.
	 * @param userId ID del usuario cuyo estado de bloqueo se va a cambiar.
	 * @param bloqueado Estado de bloqueo del usuario.
	 * @param model Modelo utilizado para enviar mensajes a la vista.
	 * @return Redirige a la página de listado de usuarios después de realizar la operación.
	 */
	@PostMapping("/privada/cambiarEstadoBloqueo")
	public String cambiarEstadoBloqueo(@RequestParam Long userId, @RequestParam(required = false) boolean bloqueado, Model model) {
	    try {
	        // Registro del inicio del método en el log
	        logger.info("Iniciando cambio de estado de bloqueo de usuario.");
	        usuarioServicio.cambiarEstadoBloqueo(userId, bloqueado);
	        model.addAttribute("successMessage", "¡Estado de bloqueo actualizado correctamente!");
	    } catch (Exception e) {
	        // Registro de errores en el log en caso de excepción
	        logger.error("Error al cambiar el estado de bloqueo del usuario: {}", e.getMessage(), e);
	        model.addAttribute("errorMessage", "Error al cambiar el estado de bloqueo del usuario.");
	    }
	    return "redirect:/privada/listado";
	}
	/**
	 * Gestiona la solicitud HTTP POST para cambiar las fechas de bloqueo de un usuario.
	 * @param userId  ID del usuario cuyas fechas de bloqueo se van a cambiar.
	 * @param inicioBloqueo Fecha de inicio del bloqueo.
	 * @param finBloqueo Fecha de fin del bloqueo.
	 * @param model Modelo utilizado para enviar mensajes a la vista.
	 * @return Redirige a la página de listado de usuarios después de realizar la operación.
	 */
	@PostMapping("/privada/cambiarFechasBloqueo")
	public String cambiarFechasBloqueo(@RequestParam Long userId, @RequestParam String inicioBloqueo, @RequestParam String finBloqueo, Model model) {
	    try {
	        // Registro del inicio del método en el log
	        logger.info("Iniciando cambio de fechas de bloqueo de usuario.");
	        usuarioServicio.cambiarFechasBloqueo(userId, inicioBloqueo, finBloqueo);
	        model.addAttribute("successMessage", "¡Fechas de bloqueo actualizadas correctamente!");
	    } catch (Exception e) {
	        // Registro de errores en el log en caso de excepción
	        logger.error("Error al cambiar las fechas de bloqueo del usuario: {}", e.getMessage(), e);
	        model.addAttribute("errorMessage", "Error al cambiar las fechas de bloqueo del usuario.");
	    }
	    return "redirect:/privada/listado";
	}

}
