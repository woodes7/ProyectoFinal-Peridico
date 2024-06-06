package Proyecto_Final.Periodico.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.EstadoArticulo;

/**
 * Interfaz de repositorio para la entidad EstadoArticulo. Proporciona métodos
 * para realizar operaciones CRUD en la base de datos.
 * 
 * @author pabloRG
 */
@Service
public interface EstadoArticuloRepositorio extends JpaRepository<EstadoArticulo, Long> {
	List<EstadoArticulo> findByEstadoContainingIgnoreCase(String nombre);
}
