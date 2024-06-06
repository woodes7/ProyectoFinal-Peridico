package Proyecto_Final.Periodico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Rol;

/**
 * Interfaz de repositorio para la entidad Rol. Proporciona métodos para
 * realizar operaciones CRUD en la base de datos.
 * 
 * @author pabloRG
 */
@Service
public interface RolRepositorio extends JpaRepository<Rol, Long> {

	Rol findByNombre(String nombre);

}
