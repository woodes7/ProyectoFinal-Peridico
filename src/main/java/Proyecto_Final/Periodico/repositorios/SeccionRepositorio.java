package Proyecto_Final.Periodico.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import Proyecto_Final.Periodico.daos.Seccion;
/**
 * Repositorio para la entidad Seccion, que proporciona métodos para realizar operaciones de
 * lectura, escritura y modificación en la base de datos relacionadas con las secciones.
 *  @author pabloRG
 */
public interface SeccionRepositorio extends JpaRepository<Seccion, Long> {
}
