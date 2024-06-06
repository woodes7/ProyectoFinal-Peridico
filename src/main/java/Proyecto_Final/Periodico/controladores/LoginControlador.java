package Proyecto_Final.Periodico.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import Proyecto_Final.Periodico.servicios.InterfazArticuloServicio;
import Proyecto_Final.Periodico.servicios.InterfazUsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import Proyecto_Final.Periodico.daos.Usuario;
import Proyecto_Final.Periodico.dtos.ArticuloDto;
import Proyecto_Final.Periodico.dtos.UsuarioDto;

@Controller
public class LoginControlador {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);
	@Autowired
	private InterfazUsuarioServicio usuarioServicio;
	@Autowired
	private InterfazArticuloServicio articuloServicio;

	/**
	 * Gestiona la solicitud HTTP GET para la url /auth/login y muestra la página de
	 * inicio de sesión
	 *
	 * @param model Modelo que se utiliza para enviar un usuarioDTO vacío a la
	 *              vista.
	 * @return La vista de inicio de sesión (login.html).
	 */
	@GetMapping("/auth/login")
	public String login(Model model, HttpSession session) {
		try {
			model.addAttribute("mensajeConfirmacion", session.getAttribute("mensajeConfirmacion"));
			model.addAttribute("correo", session.getAttribute("correo"));
			String correo = model.addAttribute("correo", session.getAttribute("correo")).toString();
			session.removeAttribute("mensajeConfirmacion");
			session.removeAttribute("correo");
			logger.info("Ingresando al método GET login");
			return "login";
		} catch (Exception e) {
			logger.error("Error al procesar la solicitud en el método GET login: {}", e.getMessage());
			// Manejar el error según sea necesario
			return "error"; // Por ejemplo, redirigir a una página de error
		}
	}

	/**
	 * Gestiona la solicitud HTTP GET para la url /privada/cuenta-usuario y muestra
	 * la página de cuenta de usuario si la cuenta fue confirmada, de lo contrario,
	 * la vista de inicio de sesión.
	 *
	 * @param model          modelos que se utiliza para enviar mensajes a la vista.
	 * @param authentication Objeto Authentication que contiene el nombre de usuario
	 * @return La vista de cuenta de usuario (cuentaDeUsuario.html) si la cuenta fue
	 *         confirmada o la vista de inicio de sesión (login.html).
	 */
	@GetMapping("/privada/home")
	public String loginCorrecto(Model model, Authentication authentication) {
	    try {
	        logger.info("Inicio del método get loginCorrecto");
	        boolean cuentaConfirmada = usuarioServicio.CuentaConfirmada(authentication.getName());
	        String nombreUsuario = authentication.getName();
	        
	        if (cuentaConfirmada) {
	            UsuarioDto usuarioDto = usuarioServicio.buscarPorEmail(authentication.getName());
	            String fotoPerfil = usuarioDto.getFotoPerfil(); 
	            model.addAttribute("usuarioDto", usuarioDto);
	            model.addAttribute("fotoPerfilBase64", fotoPerfil);

	            
	            if (authentication.getAuthorities().stream()
	                    .anyMatch(role -> role.getAuthority().equals("ROLE_AUTOR") || role.getAuthority().equals("ROLE_ADMIN")||  role.getAuthority().equals("ROLE_SUPERADMIN"))) {
	                // Obtener lista de artículos del usuario
	            	
	                List<ArticuloDto> articulos = articuloServicio.buscarArticuloPorUsuario(usuarioDto);
	               
	                model.addAttribute("articulos", articulos);
	            }

	            logger.info("Usuario '{}' ha iniciado sesión correctamente.", nombreUsuario);
	            return "home";
	        } else {
	            model.addAttribute("cuentaNoVerificada", "Error, confirme su email.");
	            logger.warn("El usuario '{}' intentó acceder a la página de inicio con una cuenta no verificada.",
	                    nombreUsuario);
	            return "login";
	        }
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	        logger.error("Error al procesar la solicitud en la página de inicio.", e);
	        return "login";
	    }
	}


	/**
	 * Gestiona la solicitud HTTP GET para la url /auth/confirmar-cuenta y realiza
	 * la confirmación de la cuenta.
	 *
	 * @param model Modelo que se utiliza para enviar mensajes a la vista.
	 * @param token Token de confirmación enviado al usuario.
	 * @return La vista de confirmación de cuenta.
	 */
	@GetMapping("/auth/confirmarCuenta")
	public String confirmarCuenta(Model model, @RequestParam("token") String token) {
		logger.info("Iniciando confirmación de cuenta con el token: '{}'", token);
		try {
			boolean confirmacionExitosa = usuarioServicio.confirmarCuenta(token);
			if (confirmacionExitosa) {
				model.addAttribute("cuentaVerificada", "Su dirección de correo ha sido confirmada correctamente");
				logger.info("La cuenta asociada al token '{}' ha sido confirmada con éxito.", token);
			} else {
				model.addAttribute("cuentaNoVerificada", "Error al confirmar su email");
				logger.error("Error al confirmar la cuenta asociada al token '{}'", token);
			}
			return "login";
		} catch (Exception e) {
			model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
			logger.error("Error al procesar la solicitud al confirmar la cuenta con el token '{}'", token, e);
			return "login";
		}
	}

	/**
	 * Método GET para mostrar la página principal.
	 *
	 * @param model          Modelo que se utiliza para enviar datos a la vista.
	 * @param authentication Objeto Authentication que contiene la información de
	 *                       autenticación del usuario.
	 * @return La vista de la página principal.
	 */
	@GetMapping("/")
	public String mostrarPaginaPrincipal(Model model, Authentication authentication) {        
	    logger.info("Iniciando obtención de todos los artículos... [@GetMapping mostrarPaginaPrincipal]");
	    try {
	        UsuarioDto usuarioDto = usuarioServicio.buscarPorEmail(authentication.getName());
	        List<ArticuloDto> articulosDto = articuloServicio.obtenerTodos().stream().filter(articuloDto -> {
	            boolean isPublicado = "PUBLICADO".equals(articuloDto.getEstado().getEstado());
	            if (isPublicado) {
	            	System.out.println(articuloDto.getFechaPublicacion());	               
	            }
	            return isPublicado;
	        }).collect(Collectors.toList());
	        logger.info("Se encontraron {} artículos publicados.", articulosDto.size());
	        model.addAttribute("articulosDto", articulosDto);
	        model.addAttribute("usuarioDto", usuarioDto);
	        return "index";
	    } catch (Exception e) {
	        logger.error("Se produjo un error al procesar la solicitud: {}", e.getMessage());
	        model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	        return "index"; // Nombre de la plantilla Thymeleaf
	    }
	}


		
				
		

}