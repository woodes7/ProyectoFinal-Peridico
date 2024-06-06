package Proyecto_Final.Periodico.controladores;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Proyecto_Final.Periodico.dtos.ArticuloDto;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.dtos.SeccionDto;
import Proyecto_Final.Periodico.servicios.InterfazArticuloServicio;
import Proyecto_Final.Periodico.dtos.UsuarioDto;
import Proyecto_Final.Periodico.servicios.InterfazEstadoArticuloServicio;
import Proyecto_Final.Periodico.servicios.InterfazFotoServicio;
import Proyecto_Final.Periodico.servicios.InterfazSeccionServicio;
import Proyecto_Final.Periodico.servicios.InterfazUsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ArticuloControlador {

	Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);
	@Autowired
	private InterfazArticuloServicio articuloServicio;
	@Autowired
	private InterfazUsuarioServicio usuarioServicio;
	@Autowired
	private InterfazEstadoArticuloServicio estadoArticuloServicio;
	@Autowired
	private InterfazSeccionServicio seccionServicio;
	@Autowired
	private InterfazFotoServicio fotoServicioServicio;

	/**
	 * Método GET para cargar el formulario de creación de un nuevo artículo.
	 *
	 * @param model          Objeto de modelo para pasar datos a la vista.
	 * @param request        Objeto HttpServletRequest para acceder a la solicitud
	 *                       HTTP.
	 * @param authentication Objeto Authentication para obtener la información de
	 *                       autenticación del usuario.
	 * @return La vista para crear un nuevo artículo.
	 */
	@GetMapping("/privada/crearArticulo")
	public String crearArticulo(Model model, HttpServletRequest request, Authentication authentication) {
		try {
			logger.info("Ingresando al método GET crearArticulo");
			UsuarioDto usuarioDto = usuarioServicio.buscarPorEmail(authentication.getName());
			model.addAttribute("usuarioDto", usuarioDto);
			model.addAttribute("articuloDto", new ArticuloDto());
			model.addAttribute("secciones", seccionServicio.obtenerTodasLasSecciones());
			model.addAttribute("estados", estadoArticuloServicio.obtenerEstaodosArticulo());
			List<EstadoArticuloDto> estados = estadoArticuloServicio.obtenerEstaodosArticulo();
			return "crearArticulo";
		} catch (Exception e) {
			logger.error("Error al procesar la solicitud en el método GET crearArticulo: {}", e.getMessage());
			model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
			return "crearArticulo";
		}
	}

	/**
	 * Método POST para crear un nuevo artículo.
	 *
	 * @param articuloDto    Objeto ArticuloDto que contiene la información del
	 *                       artículo a crear.
	 * @param imagen         Objeto MultipartFile que representa la imagen del
	 *                       artículo.
	 * @param model          Objeto de modelo para pasar datos a la vista.
	 * @param authentication Objeto Authentication para obtener la información de
	 *                       autenticación del usuario.
	 * @return La vista de inicio del usuario después de crear el artículo.
	 */
	@PostMapping("/privada/crearArticulo")
	public String crearArticulo(@ModelAttribute("articuloDto") ArticuloDto articuloDto,
			@RequestParam("archivo") MultipartFile imagen, Model model, Authentication authentication) {
		try {
			logger.info("Ingresando al método POST crearArticulo");
			UsuarioDto usuarioDto = usuarioServicio.buscarPorEmail(authentication.getName());
			if (!imagen.isEmpty()) {
				byte[] imagenBytes = imagen.getBytes();
				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				articuloDto.setImagen(imagenBase64);
			}
			SeccionDto seccionDto = seccionServicio.obtenerSeccionPorId(articuloDto.getSeccion().getId());
			EstadoArticuloDto estadoDto = estadoArticuloServicio.obtenerEstadoPorId(articuloDto.getEstado().getId());
			articuloDto.setSeccion(seccionDto);
			articuloDto.setEstado(estadoDto);
			articuloDto.setUsuario(usuarioDto);
			ArticuloDto articuloCreado = articuloServicio.crearArticulo(articuloDto);
			return "home";
		} catch (Exception e) {
			logger.error("Error al procesar la solicitud en el método POST crearArticulo: {}", e.getMessage());
			model.addAttribute("errorMessage", "Error al crear el artículo");
			return "crearArticulo";
		}
	}

	/**
	 * Método GET para listar todos los artículos.
	 *
	 * @param model          Objeto de modelo para pasar datos a la vista.
	 * @param authentication Objeto Authentication para obtener la información de
	 *                       autenticación del usuario.
	 * @return La vista que muestra el listado de artículos.
	 */
	@GetMapping("/privada/listadoArticulo")
	public String listarArticulos(Model model, Authentication authentication) {
		try {
			logger.info("Ingresando al método GET listarArticulos");
			UsuarioDto usuarioLogeado = usuarioServicio.buscarPorEmail(authentication.getName());
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_AUTOR")
					|| auth.getAuthority().equals("ROLE_SUPERADMIN") || auth.getAuthority().equals("ROLE_ADMIN"))) {
				List<ArticuloDto> listaArticulosDto = articuloServicio.obtenerTodos();
				model.addAttribute("articulos", listaArticulosDto);
				model.addAttribute("usuarioLogeado", usuarioLogeado);

				return "listadoArticulo";

			} else {
				return "home";
			}
		} catch (Exception e) {
			logger.error("Error al procesar la solicitud en el método GET listarArticulos: {}", e.getMessage());
			return "error";
		}
	}

	/**
	 * Método GET para cargar el formulario de edición de un artículo.
	 *
	 * @param id    ID del artículo a editar.
	 * @param model Objeto de modelo para pasar datos a la vista.
	 * @return La vista para editar un artículo.
	 */
	@GetMapping("/privada/editarArticulo")
	public String editarArticulo(@RequestParam("id") Long id, Model model) {
		try {
			logger.info("Ingresando al método GET editarArticulo");
			ArticuloDto articuloDto = articuloServicio.buscarPorId(id);
			String imagenArticulo = articuloDto.getImagen();
			model.addAttribute("articuloDto", articuloDto);
			model.addAttribute("ImagenPerfilBase64", imagenArticulo);
			return "editarArticulo";
		} catch (Exception e) {
			logger.error("Error al procesar la solicitud en el método GET editarArticulo: {}", e.getMessage(), e);
			model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
			return "home";
		}
	}

	/**
	 * Método POST para editar un artículo.
	 *
	 * @param id          ID del artículo a editar.
	 * @param articuloDto Objeto ArticuloDto que contiene la información actualizada
	 *                    del artículo.
	 * @param archivoFoto Objeto MultipartFile que representa la nueva imagen del
	 *                    artículo.
	 * @param publicar    Parámetro opcional para indicar si se desea publicar el
	 *                    artículo.
	 * @param estadoId    ID del nuevo estado del artículo.
	 * @param seccionId   ID de la nueva sección del artículo.
	 * @return La vista para editar un artículo.
	 */
	@PostMapping("/editarArticulo/{id}")
	public String editarArticulo(@PathVariable("id") Long id, @ModelAttribute("articuloDto") ArticuloDto articuloDto,
	        @RequestParam("archivo") MultipartFile archivoFoto,
	        @RequestParam("estadoId") Long estadoId, @RequestParam("seccionId") Long seccionId) {
	    try {
	        logger.info("Ingresando al método POST editarArticulo");
	        ArticuloDto existenteArticulo = articuloServicio.buscarPorId(id);

	        if (existenteArticulo != null) {
	            existenteArticulo.setTitulo(articuloDto.getTitulo());
	            existenteArticulo.setContenido(articuloDto.getContenido());

	            EstadoArticuloDto estadoArticuloDto = estadoArticuloServicio.obtenerEstadoPorId(estadoId);
	            SeccionDto seccionDto = seccionServicio.obtenerSeccionPorId(seccionId);

	            if (!archivoFoto.isEmpty()) {
	                byte[] fotoBytes = archivoFoto.getBytes();
	                String fotoBase64 = Base64.getEncoder().encodeToString(fotoBytes);
	                existenteArticulo.setImagen(fotoBase64);
	            }

	            if (estadoArticuloDto != null) {
	                existenteArticulo.setEstado(estadoArticuloDto);
	                if ("PUBLICADO".equals(estadoArticuloDto.getEstado())) {
	                	 Calendar fechaActual = Calendar.getInstance();
	                     existenteArticulo.setFechaPublicacion(fechaActual);
	                }
	                existenteArticulo.setSeccion(seccionDto);
	                articuloServicio.editarArticulo(existenteArticulo);
	            } else {
	                logger.error("No se pudo obtener el estado del artículo con ID: {}", id);
	            }
	        } else {
	            logger.error("No se encontró ningún artículo con ID: {}", id);
	        }
	    } catch (IOException e) {
	        logger.error("Error de entrada y salida al procesar el artículo: {}", e.getMessage());
	    }

	    return "redirect:/privada/listadoArticulo";
	}




	/**
	 * Método GET para mostrar un artículo específico.
	 *
	 * @param id    ID del artículo a mostrar.
	 * @param model Objeto de modelo para pasar datos a la vista.
	 * @return La vista que muestra el artículo específico.
	 */
	@GetMapping("/articulo/{id}")
	public String articulo(@PathVariable Long id, Model model) {
		try {
			logger.info("Ingresando al método GET articulo");
			ArticuloDto articuloDto = articuloServicio.buscarPorId(id);
			String imagenArticulo = articuloDto.getImagen();

			if (articuloDto != null) {
				model.addAttribute("articuloDto", articuloDto);
				model.addAttribute("fotoPerfilBase64", imagenArticulo); // Corregir el nombre del atributo
				return "articulo";
			} else {
				model.addAttribute("errorMessage", "No se pudo encontrar el artículo con ID: " + id);
				return "ErrorPagina";
			}
		} catch (Exception e) {
			logger.error("Se produjo un error al procesar la solicitud en el método GET articulo: {}", e.getMessage());
			model.addAttribute("errorMessage", "Se produjo un error al procesar la solicitud");
			return "ErrorPagina";
		}
	}

}