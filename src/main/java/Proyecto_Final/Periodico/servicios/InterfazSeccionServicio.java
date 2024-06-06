package Proyecto_Final.Periodico.servicios;

import java.util.List;

import Proyecto_Final.Periodico.daos.Seccion;
import Proyecto_Final.Periodico.dtos.SeccionDto;

/**
 * Interfaz para el servicio de secciones.
 */
public interface InterfazSeccionServicio {

    /**
     * Convierte una entidad Seccion a un DTO SeccionDto.
     *
     * @param seccion la entidad Seccion a convertir
     * @return el objeto SeccionDto resultante
     */
    public SeccionDto SeccionDaoASeccionDto(Seccion seccion);

    /**
     * Convierte un DTO SeccionDto a una entidad Seccion.
     *
     * @param seccionDto el DTO SeccionDto a convertir
     * @return la entidad Seccion resultante
     */
    public Seccion SeccionDtoASeccionDao(SeccionDto seccionDto);

    /**
     * Obtiene una lista de todas las secciones como DTOs.
     *
     * @return una lista de objetos SeccionDto
     */
    public List<SeccionDto> obtenerTodasLasSecciones();

    /**
     * Obtiene una seccion por su ID.
     *
     * @param id el ID de la seccion a buscar
     * @return el objeto SeccionDto correspondiente
     */
    public SeccionDto obtenerSeccionPorId(long id);

    /**
     * Guarda una nueva seccion.
     *
     * @param seccionDto el DTO de la seccion a guardar
     * @return el objeto SeccionDto guardado
     */
    public SeccionDto guardarSeccion(SeccionDto seccionDto);

    /**
     * Actualiza una seccion existente.
     *
     * @param id el ID de la seccion a actualizar
     * @param seccionDto el DTO de la seccion con los nuevos datos
     * @return el objeto SeccionDto actualizado
     */
    public SeccionDto actualizarSeccion(long id, SeccionDto seccionDto);

    /**
     * Convierte una lista de DTOs SeccionDto a una lista de entidades Seccion.
     *
     * @param seccionDtos la lista de DTOs SeccionDto a convertir
     * @return la lista de entidades Seccion resultante
     */
    public List<Seccion> convertirListaSeccionDtoASeccion(List<SeccionDto> seccionDtos);

    /**
     * Convierte una lista de entidades Seccion a una lista de DTOs SeccionDto.
     *
     * @param secciones la lista de entidades Seccion a convertir
     * @return la lista de DTOs SeccionDto resultante
     */
    public List<SeccionDto> convertirListaSeccionASeccionDto(List<Seccion> secciones);

    /**
     * Elimina una seccion por su ID.
     *
     * @param id el ID de la seccion a eliminar
     */
    void eliminarSeccion(long id);
}
