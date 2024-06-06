package Proyecto_Final.Periodico.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import Proyecto_Final.Periodico.dtos.UsuarioDto;
import Proyecto_Final.Periodico.servicios.InterfazUsuarioServicio;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegistroControlador {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);
	@Autowired
	private InterfazUsuarioServicio usuarioServicio;

	/**
	 * Método para mostrar el formulario de creación de usuario.
	 * 
	 * @param model El modelo de la vista.
	 * @return La vista del formulario de creación de usuario.
	 */
	@GetMapping("/crearUsuarioForm")
	public String mostrarFormularioCrearUsuario(Model model) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Iniciando formulario de creación de usuario.");

		model.addAttribute("usuarioDto", new UsuarioDto());
		return "formulario-crear-usuario";
	}
	

	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/auth/registro")
	public String getRegistrar(Model model) {
		UsuarioDto usuarioDto = new UsuarioDto();
		model.addAttribute("usuarioDto", usuarioDto);
		return "registro";
	}

	/**
	 * Método para procesar el registro de usuario después de enviar el formulario.
	 * 
	 * @param usuarioDto     El objeto UsuarioDto con los datos del nuevo usuario.
	 * @param model          El modelo de la vista.
	 * @param authentication La autenticación del usuario.
	 * @return La vista correspondiente después del registro.
	 */
	@PostMapping("/auth/registro")
	public String registrarPost(@ModelAttribute UsuarioDto usuarioDto, Model model, Authentication authentication, HttpSession session) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Iniciando proceso de registro de usuario.");

		try {
			boolean esVerificado = false;
			UsuarioDto nuevoUsuario = usuarioServicio.registro(usuarioDto, null);
			UsuarioDto checkUser = usuarioServicio.buscarPorEmail(usuarioDto.getEmailUsuario());
			if (checkUser != null)
				esVerificado = checkUser.isConfirmada();

			String rolDelUsuario = "";
			if (authentication != null && authentication.isAuthenticated()) {
				rolDelUsuario = authentication.getAuthorities().iterator().next().getAuthority();
			}
			if (nuevoUsuario == null) {
				model.addAttribute("usuarioRegistradoPeroNoConfirmado",
						"Ya existe un usuario con ese email sin confirmar");
				logger.error("Ya existe un usuario con ese email sin confirmar.");
				return "registro";
			} else if (esVerificado) {
				model.addAttribute("emailYaRegistrado", "Ya existe un usuario con ese email");
				logger.error("Ya existe un usuario con ese email.");
				return "registro";
			} else if (nuevoUsuario != null && !rolDelUsuario.equals("ROLE_ADMIN")) {
				model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
				logger.info("Registro del nuevo usuario OK.");
				session.setAttribute("mensajeConfirmacion", "1");
				session.setAttribute("correo", usuarioDto.getEmailUsuario());
				return "auth/login";
			} else {
				model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
				model.addAttribute("usuarios", usuarioServicio.buscarTodos());
				logger.info("Registro del nuevo usuario OK.");
		
				return "administracionUsuarios";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
			logger.error("Error al procesar la solicitud al registrar el usuario.", e);
			return "registro";
		}
	}

}
