package Proyecto_Final.Periodico.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.dtos.ArticuloDto;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.dtos.SeccionDto;
import Proyecto_Final.Periodico.dtos.UsuarioDto;
import Proyecto_Final.Periodico.daos.Articulo;
import Proyecto_Final.Periodico.daos.EstadoArticulo;
import Proyecto_Final.Periodico.daos.Seccion;
import Proyecto_Final.Periodico.daos.Usuario;
import Proyecto_Final.Periodico.repositorios.ArticuloRepositorio;
import Proyecto_Final.Periodico.repositorios.UsuarioRepositorio;

@Service
public class ImplementacionsArticuloServicio implements InterfazArticuloServicio {

	private static final Logger logger = LoggerFactory.getLogger(ImplementacionsArticuloServicio.class);

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private ArticuloRepositorio articuloRepositorio;

	@Autowired
	private InterfazUsuarioServicio usuarioServicio;

	@Autowired
	private InterfazEstadoArticuloServicio estadoArticuloServicio;

	@Autowired
	private InterfazFotoServicio fotoServicio;

	@Autowired
	private InterfazSeccionServicio seccionServicio;


    @Override
    public ArticuloDto buscarPorId(long id) {
        logger.info("Iniciando búsqueda de artículo por ID: {}", id);
        try {
            Articulo articulo = articuloRepositorio.findById(id).orElse(null);
            if (articulo != null) {
                logger.info("Artículo encontrado con ID: {}", id);
                return articuloToDto(articulo);
            }
        } catch (Exception e) {
            logger.error("Error al buscar artículo por ID: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<ArticuloDto> buscarPorTitulo(String titulo) {
        logger.info("Iniciando búsqueda de artículos por título: {}", titulo);
        try {
            List<Articulo> articulos = articuloRepositorio.findByTitulo(titulo);
            List<ArticuloDto> articulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                articulosDto.add(articuloDto);
            }

            logger.info("Búsqueda de artículos por título completada exitosamente");
            return articulosDto;
        } catch (Exception e) {
            logger.error("Error al buscar artículos por título: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ArticuloDto> buscarArticuloPorUsuario(UsuarioDto usuarioDto) {
        logger.info("Iniciando búsqueda de artículos por usuario: {}", usuarioDto.getId());
        try {
        	 Usuario usuario =usuarioServicio.usuarioToDao(usuarioDto);
            List<Articulo> articulos = articuloRepositorio.findByAutor(usuario);
            List<ArticuloDto> articulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                articulosDto.add(articuloDto);
            }

            logger.info("Búsqueda de artículos por usuario completada exitosamente");
            return articulosDto;
        } catch (Exception e) {
            logger.error("Error al buscar artículos por usuario: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ArticuloDto> buscarPorSeccion(Seccion seccion) {
        logger.info("Iniciando búsqueda de artículos por sección: {}", seccion.getId());
        try {
            List<Articulo> articulos = articuloRepositorio.findBySeccion(seccion);
            List<ArticuloDto> articulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                articulosDto.add(articuloDto);
            }

            logger.info("Búsqueda de artículos por sección completada exitosamente");
            return articulosDto;
        } catch (Exception e) {
            logger.error("Error al buscar artículos por sección: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ArticuloDto> buscarPorFechaCreacion(Calendar fecha) {
        logger.info("Iniciando búsqueda de artículos por fecha de creación: {}", fecha.getTime());
        try {
            List<Articulo> articulos = articuloRepositorio.findByFchCreacion(fecha);
            List<ArticuloDto> articulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                articulosDto.add(articuloDto);
            }

            logger.info("Búsqueda de artículos por fecha de creación completada exitosamente");
            return articulosDto;
        } catch (Exception e) {
            logger.error("Error al buscar artículos por fecha de creación: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ArticuloDto> buscarPorFechaPublicacion(Calendar fecha) {
        logger.info("Iniciando búsqueda de artículos por fecha de publicación: {}", fecha.getTime());
        try {
            List<Articulo> articulos = articuloRepositorio.findByFchPublicacion(fecha);
            List<ArticuloDto> articulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                articulosDto.add(articuloDto);
            }

            logger.info("Búsqueda de artículos por fecha de publicación completada exitosamente");
            return articulosDto;
        } catch (Exception e) {
            logger.error("Error al buscar artículos por fecha de publicación: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ArticuloDto> obtenerTodos() {
        logger.info("Iniciando obtención de todos los artículos");
        try {
            List<Articulo> articulos = articuloRepositorio.findAll();
            List<ArticuloDto> ListaArticulosDto = new ArrayList<>();

            for (Articulo articulo : articulos) {
                ArticuloDto articuloDto = articuloToDto(articulo);
                ListaArticulosDto.add(articuloDto);
            }

            logger.info("Obtención de todos los artículos completada exitosamente");
            return ListaArticulosDto;
        } catch (Exception e) {
            logger.error("Error al obtener todos los artículos: {}", e.getMessage());
            return null;
        }
    }


    @Override
    public ArticuloDto crearArticulo(ArticuloDto articuloDto) {
        logger.info("Iniciando creación de un nuevo artículo");
        try {
            EstadoArticulo estadoArticuloDao = estadoArticuloServicio.EstadoArticuloToDao(articuloDto.getEstado());
            Seccion seccionDao = seccionServicio.SeccionDtoASeccionDao(articuloDto.getSeccion());
            Articulo articuloDao = articuloToDao(articuloDto);
            articuloDao.setFchCreacion(Calendar.getInstance());
            articuloRepositorio.save(articuloDao);

            logger.info("Artículo creado exitosamente con ID: {}", articuloDao.getId());
            return articuloToDto(articuloDao);
        } catch (Exception e) {
            logger.error("Error al crear el artículo: {}", e.getMessage());
            return null;
        }
    }


    public List<Articulo> buscarPorTermino(String terminoBusqueda) {
        logger.info("Iniciando búsqueda de artículos por término de búsqueda: {}", terminoBusqueda);
        try {
            return articuloRepositorio.buscarPorTermino(terminoBusqueda);
        } catch (Exception e) {
            logger.error("Error al buscar artículos por término de búsqueda: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void editarArticulo(ArticuloDto articuloDto) {
        logger.info("Iniciando edición de artículo con ID: {}", articuloDto.getId());
        try {
            Optional<Articulo> optionalArticulo = articuloRepositorio.findById(articuloDto.getId());
            if (optionalArticulo.isPresent()) {
                Articulo articuloExistente = optionalArticulo.get();
                Articulo articuloActualizado = articuloToDao(articuloDto);

                articuloExistente.setTitulo(articuloActualizado.getTitulo());
                articuloExistente.setContenido(articuloActualizado.getContenido());
                articuloExistente.setImagen(articuloActualizado.getImagen());
                articuloExistente.setFchPublicacion(articuloActualizado.getFchPublicacion());

                EstadoArticuloDto estadoDto = articuloDto.getEstado();
                if (estadoDto != null) {
                    EstadoArticulo estado = estadoArticuloServicio.EstadoArticuloToDao(estadoDto);
                    articuloExistente.setEstado(estado);
                }

                SeccionDto seccionDto = articuloDto.getSeccion();
                if (seccionDto != null) {
                    Seccion seccion = seccionServicio.SeccionDtoASeccionDao(seccionDto);
                    articuloExistente.setSeccion(seccion);
                }

                articuloRepositorio.save(articuloExistente);
                logger.info("Artículo actualizado exitosamente con ID: {}", articuloDto.getId());
            } else {
                logger.error("No se encontró el artículo con el ID proporcionado.");
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el artículo: {}", e.getMessage());
        }
    }

    @Override
    public void eliminarArticulo(ArticuloDto articuloDto) {
        try {
            logger.info("Iniciando eliminación de artículo");
            Optional<Articulo> optionalArticulo = articuloRepositorio.findById(articuloDto.getId());
            if (optionalArticulo.isPresent()) {
                Articulo articulo = optionalArticulo.get();
                articuloRepositorio.delete(articulo);
                logger.info("Artículo eliminado correctamente.");
            } else {
                logger.error("No se encontró el artículo con el ID proporcionado.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el artículo: {}", e.getMessage());
        }
    }

	/**
	 * Convierte una cadena de texto en el formato "yyyy-MM-dd" a un objeto de tipo
	 * Calendar.
	 * 
	 * @param fechaString La cadena de texto a convertir.
	 * @return El objeto Calendar resultante de la conversión.
	 */
	private Calendar stringToCalendar(String fechaString) {
		logger.info("Iniciando conversión de String a Calendar");
		if (fechaString == null || fechaString.isEmpty()) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar fechaCalendar = Calendar.getInstance();
			fechaCalendar.setTime(sdf.parse(fechaString));
			logger.info("Conversión de String a Calendar completada exitosamente");
			return fechaCalendar;
		} catch (ParseException e) {
			logger.error("Error al convertir String a Calendar: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Convierte una lista de objetos de tipo ArticuloDto a una lista de objetos de
	 * tipo Articulo.
	 * 
	 * @param listaArticuloDto La lista de ArticuloDto a convertir.
	 * @return La lista de Articulo resultante de la conversión.
	 */
	private List<Articulo> listArticuloToDao(List<ArticuloDto> listaArticuloDto) {
		logger.info("Iniciando conversión de lista de ArticuloDto a lista de Articulo");
		try {
			List<Articulo> listaArticulo = new ArrayList<>();
			for (ArticuloDto dto : listaArticuloDto) {
				listaArticulo.add(articuloToDao(dto));
			}
			logger.info("Conversión de lista de ArticuloDto a lista de Articulo completada exitosamente");
			return listaArticulo;
		} catch (Exception e) {
			logger.error("Error al convertir lista de ArticuloDto a lista de Articulo: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Convierte un objeto de tipo Articulo a un objeto de tipo ArticuloDto.
	 * 
	 * @param articuloDao El objeto Articulo a convertir.
	 * @return El objeto ArticuloDto resultante de la conversión.
	 */
	private ArticuloDto articuloToDto(Articulo articuloDao) {
		logger.info("Iniciando conversión de Articulo a ArticuloDto");
		try {
			ArticuloDto dto = new ArticuloDto();
			dto.setId(articuloDao.getId());
			dto.setTitulo(articuloDao.getTitulo());
			dto.setContenido(articuloDao.getContenido());
			if (articuloDao.getImagen() != null) {
				String fotoBase64 = fotoServicio.convertirAbase64(articuloDao.getImagen());
				dto.setImagen(fotoBase64);
			}
			dto.setFechaCreacion(articuloDao.getFchCreacion());

			// Convertir la fecha de publicación si no es nula
			if (articuloDao.getFchPublicacion() != null) {
				dto.setFechaPublicacion(articuloDao.getFchPublicacion());
			}

			// Convertir el EstadoArticulo a EstadoArticuloDto
			EstadoArticuloDto estadoDto = estadoArticuloServicio.EstadoArticuloToDto(articuloDao.getEstado());
			dto.setEstado(estadoDto);

			// Convertir la Seccion a SeccionDto
			SeccionDto seccionDto = seccionServicio.SeccionDaoASeccionDto(articuloDao.getSeccion());
			dto.setSeccion(seccionDto);

			// Convertir el Usuario a UsuarioDto
			UsuarioDto usuarioDto = usuarioServicio.usuarioToDto(articuloDao.getAutor());
			dto.setUsuario(usuarioDto);

			logger.info("Conversión de Articulo a ArticuloDto completada exitosamente");
			return dto;
		} catch (Exception e) {
			logger.error("Error al convertir Articulo a ArticuloDto: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Convierte un objeto de tipo ArticuloDto a un objeto de tipo Articulo.
	 * 
	 * @param articuloDto El objeto ArticuloDto a convertir.
	 * @return El objeto Articulo resultante de la conversión.
	 */
	public Articulo articuloToDao(ArticuloDto articuloDto) {
	    try {
	        logger.info("Iniciando conversión de ArticuloDto a Articulo.");
	        Articulo articulo = new Articulo();
	        articulo.setId(articuloDto.getId());
	        articulo.setTitulo(articuloDto.getTitulo());
	        articulo.setContenido(articuloDto.getContenido());
	        
	        // Convertir la imagen si no es nula
	    	byte[] imgBytes = fotoServicio.convertirAarrayBytes(articuloDto.getImagen());
	    	articulo.setImagen(imgBytes);
			
	        articulo.setFchCreacion(articuloDto.getFechaCreacion());
	        // Convertir la fecha de publicación de String a Calendar
	        if (articuloDto.getFechaPublicacion() != null) {
	            articulo.setFchPublicacion(articuloDto.getFechaPublicacion());
	        }
	        // Convertir el EstadoArticuloDto en EstadoArticulo
	        EstadoArticulo estado = estadoArticuloServicio.EstadoArticuloToDao(articuloDto.getEstado());
	        articulo.setEstado(estado);
	      
	        // Convertir la SeccionDto en Seccion
	        Seccion seccion = seccionServicio.SeccionDtoASeccionDao(articuloDto.getSeccion());
	        articulo.setSeccion(seccion);
	        
	        // Convertir el UsuarioDto en Usuario
	        Usuario usuario = usuarioServicio.usuarioToDao(articuloDto.getUsuario());
	        articulo.setAutor(usuario);

	        // Se puede necesitar mapear la sección también, dependiendo de la lógica de tu aplicación

	        return articulo;
	    } catch (Exception e) {
	        logger.error("Error al convertir ArticuloDto a Articulo: {}", e.getMessage());
	        return null;
	    }
	}

	/**
	 * Convierte un objeto de tipo Calendar a una cadena de texto en el formato
	 * "yyyy-MM-dd".
	 * 
	 * @param fechaCalendar El objeto Calendar a convertir.
	 * @return La cadena de texto resultante de la conversión.
	 */
	private String calendarToString(Calendar fechaCalendar) {
	    try {
	        logger.info("Iniciando conversión de Calendar a String.");
	        if (fechaCalendar == null) {
	            return null;
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        return sdf.format(fechaCalendar.getTime());
	    } catch (Exception e) {
	        logger.error("Error al convertir Calendar a String: {}", e.getMessage());
	        return null;
	    }
	}

	/**
	 * Convierte una lista de objetos de tipo Articulo a una lista de objetos de
	 * tipo ArticuloDto.
	 * 
	 * @param listaArticuloDao La lista de Articulo a convertir.
	 * @return La lista de ArticuloDto resultante de la conversión.
	 */
	public List<ArticuloDto> listArticuloToDto(List<Articulo> listaArticuloDao) {
	    try {
	        logger.info("Iniciando conversión de lista de Articulo a lista de ArticuloDto.");
	        List<ArticuloDto> listaDto = new ArrayList<>();
	        for (Articulo articulo : listaArticuloDao) {
	            listaDto.add(articuloToDto(articulo));
	        }
	        return listaDto;
	    } catch (Exception e) {
	        logger.error("Error al convertir lista de Articulo a lista de ArticuloDto: {}", e.getMessage());
	        return null;
	    }
	}

}
