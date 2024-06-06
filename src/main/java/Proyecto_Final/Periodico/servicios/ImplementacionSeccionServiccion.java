/**
 * 
 */
package Proyecto_Final.Periodico.servicios;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Seccion;
import Proyecto_Final.Periodico.dtos.SeccionDto;
import Proyecto_Final.Periodico.repositorios.SeccionRepositorio;

/**
 * 
 */
@Service
public class ImplementacionSeccionServiccion implements InterfazSeccionServicio {
	private static final Logger logger = LoggerFactory.getLogger(ImplementacionSeccionServiccion.class);

	@Autowired
    private SeccionRepositorio seccionRepositorio;

    @Override
    public SeccionDto SeccionDaoASeccionDto(Seccion seccion) {
        logger.info("Iniciando la conversión de Seccion DAO a Seccion DTO");
        
        try {
            SeccionDto seccionDto = new SeccionDto(seccion.getId(), seccion.getNombre());
            logger.info("Conversión de Seccion DAO a Seccion DTO realizada exitosamente");
            return seccionDto;
        } catch (Exception e) {
            logger.error("Error al convertir Seccion DAO a Seccion DTO: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Seccion SeccionDtoASeccionDao(SeccionDto seccionDto) {
        logger.info("Iniciando la conversión de Seccion DTO a Seccion DAO");
        
        try {
            Seccion seccion = new Seccion();
            seccion.setId(seccionDto.getId());
            seccion.setNombre(seccionDto.getNombre());
            logger.info("Conversión de Seccion DTO a Seccion DAO realizada exitosamente");
            return seccion;
        } catch (Exception e) {
            logger.error("Error al convertir Seccion DTO a Seccion DAO: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<SeccionDto> obtenerTodasLasSecciones() {
        logger.info("Iniciando la obtención de todas las secciones");
        
        try {
            List<Seccion> secciones = seccionRepositorio.findAll();
            List<SeccionDto> seccionesDto = secciones.stream()
                                                     .map(this::SeccionDaoASeccionDto)
                                                     .collect(Collectors.toList());
            logger.info("Obtención de todas las secciones realizada exitosamente");
            return seccionesDto;
        } catch (Exception e) {
            logger.error("Error al obtener todas las secciones: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public SeccionDto obtenerSeccionPorId(long id) {
        logger.info("Iniciando la obtención de la sección por ID: {}", id);
        
        try {
            Seccion seccion = seccionRepositorio.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Sección no encontrada con ID: " + id));
            SeccionDto seccionDto = SeccionDaoASeccionDto(seccion);
            logger.info("Obtención de la sección por ID realizada exitosamente");
            return seccionDto;
        } catch (Exception e) {
            logger.error("Error al obtener la sección por ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public SeccionDto guardarSeccion(SeccionDto seccionDto) {
        logger.info("Iniciando el guardado de una nueva sección");
        
        try {
            Seccion seccion = SeccionDtoASeccionDao(seccionDto);
            Seccion seccionGuardada = seccionRepositorio.save(seccion);
            SeccionDto seccionDtoGuardada = SeccionDaoASeccionDto(seccionGuardada);
            logger.info("Guardado de la sección realizado exitosamente");
            return seccionDtoGuardada;
        } catch (Exception e) {
            logger.error("Error al guardar la sección: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public SeccionDto actualizarSeccion(long id, SeccionDto seccionDto) {
        logger.info("Iniciando la actualización de la sección con ID: {}", id);
        
        try {
            Seccion seccionExistente = seccionRepositorio.findById(id)
                                                         .orElseThrow(() -> new RuntimeException("Sección no encontrada con ID: " + id));
            Seccion seccionActualizada = SeccionDtoASeccionDao(seccionDto);
            seccionActualizada.setId(id);
            Seccion seccionGuardada = seccionRepositorio.save(seccionActualizada);
            SeccionDto seccionDtoGuardada = SeccionDaoASeccionDto(seccionGuardada);
            logger.info("Actualización de la sección realizada exitosamente");
            return seccionDtoGuardada;
        } catch (Exception e) {
            logger.error("Error al actualizar la sección: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void eliminarSeccion(long id) {
        logger.info("Iniciando la eliminación de la sección con ID: {}", id);
        
        try {
            seccionRepositorio.deleteById(id);
            logger.info("Eliminación de la sección realizada exitosamente");
        } catch (Exception e) {
            logger.error("Error al eliminar la sección: {}", e.getMessage());
        }
    }

    @Override
    public List<SeccionDto> convertirListaSeccionASeccionDto(List<Seccion> secciones) {
        logger.info("Iniciando la conversión de lista de Seccion a lista de SeccionDto");
        
        try {
            List<SeccionDto> seccionesDto = secciones.stream()
                                                     .map(this::SeccionDaoASeccionDto)
                                                     .collect(Collectors.toList());
            logger.info("Conversión de lista de Seccion a lista de SeccionDto realizada exitosamente");
            return seccionesDto;
        } catch (Exception e) {
            logger.error("Error al convertir lista de Seccion a lista de SeccionDto: {}", e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Seccion> convertirListaSeccionDtoASeccion(List<SeccionDto> seccionDtos) {
        logger.info("Iniciando la conversión de lista de SeccionDto a lista de Seccion");
        
        try {
            List<Seccion> secciones = seccionDtos.stream()
                                                 .map(this::SeccionDtoASeccionDao)
                                                 .collect(Collectors.toList());
            logger.info("Conversión de lista de SeccionDto a lista de Seccion realizada exitosamente");
            return secciones;
        } catch (Exception e) {
            logger.error("Error al convertir lista de SeccionDto a lista de Seccion: {}", e.getMessage());
            return null;
        }
    }
}
