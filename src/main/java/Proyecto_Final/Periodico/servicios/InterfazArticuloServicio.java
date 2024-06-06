package Proyecto_Final.Periodico.servicios;

import java.util.Calendar;
import java.util.List;

import Proyecto_Final.Periodico.daos.Articulo;
import Proyecto_Final.Periodico.daos.Seccion;
import Proyecto_Final.Periodico.daos.Usuario;
import Proyecto_Final.Periodico.dtos.ArticuloDto;
import Proyecto_Final.Periodico.dtos.EstadoArticuloDto;
import Proyecto_Final.Periodico.dtos.UsuarioDto;

/**
 * Interfaz que define los métodos necesarios para la gestión de artículos.
 * 
 * @author pabloRG
 */
public interface InterfazArticuloServicio {

    /**
     * Busca un artículo por su ID.
     * 
     * @param id El ID del artículo a buscar.
     * @return El DTO del artículo encontrado, o null si no se encuentra.
     */
    public ArticuloDto buscarPorId(long id);

    /**
     * Busca artículos por su título.
     * 
     * @param titulo El título del artículo a buscar.
     * @return Una lista de DTOs de artículos encontrados con el título especificado.
     */
    public List<ArticuloDto> buscarPorTitulo(String titulo);

    /**
     * Busca artículos publicados por un usuario específico.
     * 
     * @param usuarioDto El usuario autor de los artículos.
     * @return Una lista de DTOs de artículos publicados por el usuario especificado.
     */
    public List<ArticuloDto> buscarArticuloPorUsuario(UsuarioDto usuarioDto);

    /**
     * Busca artículos de una sección específica.
     * 
     * @param seccion La sección a la que pertenecen los artículos.
     * @return Una lista de DTOs de artículos pertenecientes a la sección especificada.
     */
    public List<ArticuloDto> buscarPorSeccion(Seccion seccion);

    /**
     * Busca artículos por su fecha de creación.
     * 
     * @param fecha La fecha de creación de los artículos.
     * @return Una lista de DTOs de artículos creados en la fecha especificada.
     */
    public List<ArticuloDto> buscarPorFechaCreacion(Calendar fecha);

    /**
     * Busca artículos por su fecha de publicación.
     * 
     * @param fecha La fecha de publicación de los artículos.
     * @return Una lista de DTOs de artículos publicados en la fecha especificada.
     */
    public List<ArticuloDto> buscarPorFechaPublicacion(Calendar fecha);

    /**
     * Obtiene todos los artículos.
     * 
     * @return Una lista de todos los DTOs de artículos.
     */
    public List<ArticuloDto> obtenerTodos();

    /**
     * Crea un nuevo artículo.
     * 
     * @param articuloDto El DTO del artículo a crear.
     * @param email       El correo electrónico del usuario que crea el artículo.
     * @return El DTO del artículo creado.
     */
    public ArticuloDto crearArticulo(ArticuloDto articuloDto);

    /**
     * Actualiza un artículo existente.
     * 
     * @param articuloDto El DTO del artículo actualizado.
     */
    public void editarArticulo(ArticuloDto articuloDto);

    /**
     * Elimina un artículo.
     * 
     * @param articuloDto El DTO del artículo a eliminar.
     */
    public void eliminarArticulo(ArticuloDto articuloDto);
    
    /**
     * Busca artículos que contienen un término de búsqueda en su título o contenido.
     * 
     * @param terminoBusqueda El término de búsqueda.
     * @return Una lista de artículos que contienen el término de búsqueda.
     */
    public List<Articulo> buscarPorTermino(String terminoBusqueda);
}
