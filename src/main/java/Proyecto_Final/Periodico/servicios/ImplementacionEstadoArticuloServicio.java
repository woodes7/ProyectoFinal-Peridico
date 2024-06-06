package Proyecto_Final.Periodico.servicios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.EstadoArticulo;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.repositorios.EstadoArticuloRepositorio;

@Service
public class ImplementacionEstadoArticuloServicio implements InterfazEstadoArticuloServicio {

	private static final Logger logger = LoggerFactory.getLogger(ImplementacionEstadoArticuloServicio.class);

	@Autowired
	private EstadoArticuloRepositorio repositorio;

	@Override
	public List<EstadoArticuloDto> obtenerEstaodosArticulo() {
		logger.info("Iniciando el proceso de obtención de todos los estados de artículo");

		try {
			List<EstadoArticulo> estados = repositorio.findAll();
			logger.info("Estados de artículo obtenidos exitosamente");
			return listaEstadosArticuloToDto(estados);
		} catch (Exception e) {
			logger.error("Error al buscar todos los estados de artículo: {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public EstadoArticuloDto obtenerEstadoPorId(long id) {
		logger.info("Iniciando el proceso de obtención del estado de artículo por ID: {}", id);

		try {
			EstadoArticulo estado = repositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Estado de artículo no encontrado con ID: " + id));
			logger.info("Estado de artículo obtenido exitosamente para ID: {}", id);
			return EstadoArticuloToDto(estado);
		} catch (Exception e) {
			logger.error("Error al buscar el estado de artículo por ID: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public EstadoArticuloDto actualizarEstadoArticulo(long id, EstadoArticuloDto estadoArticuloDto) {
		logger.info("Iniciando el proceso de actualización del estado de artículo para ID: {}", id);

		try {
			EstadoArticulo estadoExistente = repositorio.findById(id)
					.orElseThrow(() -> new RuntimeException("Estado de artículo no encontrado con ID: " + id));
			EstadoArticulo estadoActualizado = EstadoArticuloToDao(estadoArticuloDto);
			estadoActualizado.setId(id);
			EstadoArticulo estadoGuardado = repositorio.save(estadoActualizado);
			logger.info("Estado de artículo actualizado exitosamente para ID: {}", id);
			return EstadoArticuloToDto(estadoGuardado);
		} catch (Exception e) {
			logger.error("Error al actualizar el estado de artículo para ID: {}", id, e.getMessage());
			return null;
		}
	}

	@Override
	public EstadoArticuloDto guardarEstadoArticulo(EstadoArticuloDto estadoArticuloDto) {
		logger.info("Iniciando el proceso de guardado del estado de artículo");

		try {
			EstadoArticulo estadoArticulo = EstadoArticuloToDao(estadoArticuloDto);
			EstadoArticulo estadoGuardado = repositorio.save(estadoArticulo);
			logger.info("Estado de artículo guardado exitosamente");
			return EstadoArticuloToDto(estadoGuardado);
		} catch (Exception e) {
			logger.error("Error al guardar el estado de artículo: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public void eliminarEstadoArticulo(long id) {
		logger.info("Iniciando el proceso de eliminación del estado de artículo para ID: {}", id);

		try {
			repositorio.deleteById(id);
			logger.info("Estado de artículo eliminado exitosamente para ID: {}", id);
		} catch (Exception e) {
			logger.error("Error al eliminar el estado de artículo para ID: {}", id, e.getMessage());
		}
	}

	@Override
	public EstadoArticulo EstadoArticuloToDao(EstadoArticuloDto estadoArticuloDto) {
		logger.info("Iniciando la conversión de EstadoArticuloDto a EstadoArticulo");

		try {
			EstadoArticulo estadoArticulo = new EstadoArticulo();
			estadoArticulo.setId(estadoArticuloDto.getId());
			estadoArticulo.setEstado(estadoArticuloDto.getEstado());
			logger.info("Conversión de EstadoArticuloDto a EstadoArticulo realizada exitosamente");
			return estadoArticulo;
		} catch (Exception e) {
			logger.error("Error al convertir EstadoArticuloDto a EstadoArticulo: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public EstadoArticuloDto EstadoArticuloToDto(EstadoArticulo estadoArticulo) {
		logger.info("Iniciando la conversión de EstadoArticulo a EstadoArticuloDto");

		try {
			EstadoArticuloDto estadoArticuloDto = new EstadoArticuloDto();
			estadoArticuloDto.setId(estadoArticulo.getId());
			estadoArticuloDto.setEstado(estadoArticulo.getEstado());
			logger.info("Conversión de EstadoArticulo a EstadoArticuloDto realizada exitosamente");
			return estadoArticuloDto;
		} catch (Exception e) {
			logger.error("Error al convertir EstadoArticulo a EstadoArticuloDto: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public List<EstadoArticuloDto> listaEstadosArticuloToDto(List<EstadoArticulo> estadoArticulos) {
		logger.info("Iniciando la conversión de lista de EstadoArticulo a lista de EstadoArticuloDto");

		try {
			List<EstadoArticuloDto> listaDto = new ArrayList<>();
			for (EstadoArticulo estado : estadoArticulos) {
				listaDto.add(EstadoArticuloToDto(estado));
			}
			logger.info("Conversión de lista de EstadoArticulo a lista de EstadoArticuloDto realizada exitosamente");
			return listaDto;
		} catch (Exception e) {
			logger.error("Error al convertir lista de EstadoArticulo a lista de EstadoArticuloDto: {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public List<EstadoArticulo> listaEstadosArticuloToDao(List<EstadoArticuloDto> estadoArticulosDto) {
		logger.info("Iniciando la conversión de lista de EstadoArticuloDto a lista de EstadoArticulo");

		try {
			List<EstadoArticulo> listaDto = new ArrayList<>();
			for (EstadoArticuloDto estado : estadoArticulosDto) {
				listaDto.add(EstadoArticuloToDao(estado));
			}
			logger.info("Conversión de lista de EstadoArticuloDto a lista de EstadoArticulo realizada exitosamente");
			return listaDto;
		} catch (Exception e) {
			logger.error("Error al convertir lista de EstadoArticuloDto a lista de EstadoArticulo: {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public List<EstadoArticuloDto> buscarPorNombre(String nombre) {
		logger.info("Iniciando la búsqueda de estados de artículo por nombre: {}", nombre);

		try {
			List<EstadoArticulo> estados = repositorio.findByEstadoContainingIgnoreCase(nombre);
			logger.info("Búsqueda de estados de artículo por nombre realizada exitosamente");
			return listaEstadosArticuloToDto(estados);
		} catch (Exception e) {
			logger.error("Error al buscar estados de artículo por nombre: {}", e.getMessage());
			return new ArrayList<>();
		}
	}
}
