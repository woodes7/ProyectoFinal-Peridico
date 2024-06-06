package Proyecto_Final.Periodico.servicios;

import java.util.List;

import Proyecto_Final.Periodico.daos.EstadoArticulo;
import Proyecto_Final.Periodico.daos.Rol;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.dtos.RolDto;

/**
 * Interfaz que define los métodos que debe implementar un servicio para la gestión de roles en el sistema.
 * 
 * @param
 * @author pabloRG
 */
public interface InterfazRolServicio {
    /**
     * Recupera todos los roles disponibles en el sistema.
     * 
     * @param
     * @return Una lista de objetos RolDto que representan todos los roles disponibles.
     */
    public List<RolDto> buscarTodos();

    /**
     * Convierte un objeto RolDto en un objeto Rol.
     * 
     * @param rolDto El objeto RolDto a convertir.
     * @return Un objeto Rol resultante de la conversión.
     */
    public Rol RolToDao(RolDto rolDto);

    /**
     * Convierte un objeto Rol en un objeto RolDto.
     * 
     * @param rol El objeto Rol a convertir.
     * @return Un objeto RolDto resultante de la conversión.
     */
    public RolDto RolToDto(Rol rol);

    /**
     * Convierte una lista de objetos Rol en una lista de objetos RolDto.
     * 
     * @param roles La lista de objetos Rol a convertir.
     * @return Una lista de objetos RolDto resultante de la conversión.
     */
    public List<RolDto> listaRolToDto(List<Rol> roles);

    /**
     * Convierte una lista de objetos RolDto en una lista de objetos Rol.
     * 
     * @param rolesDto La lista de objetos RolDto a convertir.
     * @return Una lista de objetos Rol resultante de la conversión.
     */
    public List<Rol> listaRolToDao(List<RolDto> rolesDto);

    /**
     * Busca un rol por su nombre.
     * 
     * @param nombreRol El nombre del rol a buscar.
     * @return El objeto RolDto correspondiente al rol encontrado, o null si no se encuentra ningún rol con el nombre especificado.
     */
    public RolDto obtenerRolPorNombre(String nombreRol);

    /**
     * Busca un rol por su ID.
     * 
     * @param id El ID del rol a buscar.
     * @return El objeto Rol correspondiente al ID especificado, o null si no se encuentra ningún rol con ese ID.
     */
    public Rol obtenerRolPorId(Long id);
}
