package Proyecto_Final.Periodico.repositorios;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Articulo;
import Proyecto_Final.Periodico.daos.Seccion;
import Proyecto_Final.Periodico.daos.Usuario;

/**
 * Interfaz de repositorio para la entidad Articulo. Proporciona métodos para
 * realizar operaciones CRUD en la base de datos.
 * 
 * @autor pabloRG
 */
@Service
public interface ArticuloRepositorio extends JpaRepository<Articulo, Long> {

    /**
     * Encuentra una lista de artículos por el autor.
     * 
     * @param autor El autor del artículo.
     * @return Una lista de artículos escritos por el autor.
     */
    List<Articulo> findByAutor(Usuario autor);

    /**
     * Encuentra una lista de artículos por el título.
     * 
     * @param titulo El título del artículo.
     * @return Una lista de artículos que coinciden con el título dado.
     */
    public List<Articulo> findByTitulo(String titulo);

    /**
     * Encuentra una lista de artículos por la sección.
     * 
     * @param seccion La sección del artículo.
     * @return Una lista de artículos que pertenecen a la sección dada.
     */
    public List<Articulo> findBySeccion(Seccion seccion);

    /**
     * Encuentra una lista de artículos por la fecha de creación.
     * 
     * @param fechaCreacion La fecha de creación del artículo.
     * @return Una lista de artículos creados en la fecha dada.
     */
    public List<Articulo> findByFchCreacion(Calendar fechaCreacion);

    /**
     * Encuentra una lista de artículos por la fecha de publicación.
     * 
     * @param fechaPublicacion La fecha de publicación del artículo.
     * @return Una lista de artículos publicados en la fecha dada.
     */
    List<Articulo> findByFchPublicacion(Calendar fechaPublicacion);

    /**
     * Busca artículos por un término en el título o contenido.
     * 
     * @param terminoBusqueda El término de búsqueda.
     * @return Una lista de artículos que contienen el término de búsqueda en el título o contenido.
     */
    @Query("SELECT a FROM Articulo a WHERE LOWER(a.titulo) LIKE %:termino% OR LOWER(a.contenido) LIKE %:termino%")
    List<Articulo> buscarPorTermino(@Param("termino") String terminoBusqueda);
}
