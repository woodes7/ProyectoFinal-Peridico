package Proyecto_Final.Periodico.servicios;

import java.util.List;

import Proyecto_Final.Periodico.daos.EstadoArticulo;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;

/**
 * Interfaz que define los métodos para el servicio relacionado con el manejo de
 * estados de artículos.
 * 
 * @author pabloRG
 */
public interface InterfazEstadoArticuloServicio {

	/**
	 * Busca y retorna todos los estados de artículo.
	 * 
	 * @return Una lista de objetos EstadoArticuloDto que representan los estados de
	 *         artículo.
	 */
	public List<EstadoArticuloDto> obtenerEstaodosArticulo();

	/**
	 * Convierte un objeto EstadoArticuloDto a su equivalente en formato DAO.
	 * 
	 * @param estadoArticuloDto El objeto EstadoArticuloDto a convertir.
	 * @return El objeto EstadoArticulo equivalente en formato DAO.
	 */
	public EstadoArticulo EstadoArticuloToDao(EstadoArticuloDto estadoArticuloDto);

	/**
	 * Convierte un objeto EstadoArticulo a su equivalente en formato DTO.
	 * 
	 * @param estadoArticulo El objeto EstadoArticulo a convertir.
	 * @return El objeto EstadoArticuloDto equivalente en formato DTO.
	 */
	public EstadoArticuloDto EstadoArticuloToDto(EstadoArticulo estadoArticulo);

	/**
	 * Convierte una lista de objetos EstadoArticulo a su equivalente en formato
	 * DTO.
	 * 
	 * @param estadoArticulos La lista de objetos EstadoArticulo a convertir.
	 * @return Una lista de objetos EstadoArticuloDto equivalente en formato DTO.
	 */
	public List<EstadoArticuloDto> listaEstadosArticuloToDto(List<EstadoArticulo> estadoArticulos);

	/**
	 * Convierte una lista de objetos EstadoArticuloDto a su equivalente en formato
	 * DAO.
	 * 
	 * @param estadoArticulosDto La lista de objetos EstadoArticuloDto a convertir.
	 * @return Una lista de objetos EstadoArticulo equivalente en formato DAO.
	 */
	public List<EstadoArticulo> listaEstadosArticuloToDao(List<EstadoArticuloDto> estadoArticulosDto);
	
	/**
	 * Busca un estado de artículo por su ID.
	 * 
	 * @param id El ID del estado de artículo a buscar.
	 * @return El objeto EstadoArticuloDto encontrado o null si no se encuentra.
	 */
	public EstadoArticuloDto obtenerEstadoPorId(long id);
	
	/**
	 * Actualiza un estado de artículo existente.
	 * 
	 * @param id                El ID del estado de artículo a actualizar.
	 * @param estadoArticuloDto El DTO con los nuevos datos del estado de artículo.
	 * @return El objeto EstadoArticuloDto actualizado o null si ocurre un error.
	 */
	public EstadoArticuloDto actualizarEstadoArticulo(long id, EstadoArticuloDto estadoArticuloDto);
	
	/**
	 * Guarda un nuevo estado de artículo.
	 * 
	 * @param estadoArticuloDto El DTO del estado de artículo a guardar.
	 * @return El objeto EstadoArticuloDto guardado o null si ocurre un error.
	 */
	public EstadoArticuloDto guardarEstadoArticulo(EstadoArticuloDto estadoArticuloDto);
	
	/**
	 * Elimina un estado de artículo por su ID.
	 * 
	 * @param id El ID del estado de artículo a eliminar.
	 */
	public void eliminarEstadoArticulo(long id);
	
	/**
	 * Busca estados de artículo por su nombre.
	 * 
	 * @param nombre El nombre o parte del nombre de los estados de artículo a buscar.
	 * @return Una lista de objetos EstadoArticuloDto que coinciden con el nombre proporcionado.
	 */
	public List<EstadoArticuloDto> buscarPorNombre(String nombre);
}
