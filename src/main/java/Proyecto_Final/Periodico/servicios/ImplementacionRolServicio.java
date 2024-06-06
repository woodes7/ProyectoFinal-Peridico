package Proyecto_Final.Periodico.servicios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Proyecto_Final.Periodico.daos.Rol;
import Proyecto_Final.Periodico.dtos.RolDto;
import Proyecto_Final.Periodico.repositorios.RolRepositorio;

@Service
public class ImplementacionRolServicio implements InterfazRolServicio {

	private static final Logger logger = LoggerFactory.getLogger(ImplementacionRolServicio.class);

	@Autowired
	private RolRepositorio repositorio;

	@Override
	public List<RolDto> buscarTodos() {
		logger.info("Iniciando el proceso de búsqueda de todos los roles");

		try {
			List<Rol> roles = repositorio.findAll();
			logger.info("Roles obtenidos exitosamente");
			return listaRolToDto(roles);
		} catch (Exception e) {
			logger.error("Error al buscar todos los roles: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public Rol RolToDao(RolDto rolDto1) {
		logger.info("Iniciando la conversión de RolDto a Rol");

		try {
			if (rolDto1 == null) {
				throw new IllegalArgumentException("El objeto RolDto pasado al método RolToDao es nulo");
			}
			Rol rol = new Rol();
			rol.setId(rolDto1.getId());
			rol.setNombre(rolDto1.getNombre());
			logger.info("Conversión de RolDto a Rol realizada exitosamente");
			return rol;
		} catch (Exception e) {
			logger.error("Error al convertir RolDto a Rol: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public RolDto obtenerRolPorNombre(String nombre) {
		logger.info("Iniciando la búsqueda de Rol por nombre: {}", nombre);

		try {
			Rol rol = repositorio.findByNombre(nombre);
			logger.info("Rol obtenido exitosamente por nombre: {}", nombre);
			return rol != null ? RolToDto(rol) : null;
		} catch (Exception e) {
			logger.error("Error al obtener Rol por nombre: {}", nombre, e.getMessage());
			return null;
		}
	}

	@Override
	public Rol obtenerRolPorId(Long id) {
		logger.info("Iniciando la búsqueda de Rol por ID: {}", id);

		try {
			Rol rol = repositorio.findById(id).orElse(null);
			if (rol != null) {
				logger.info("Rol obtenido exitosamente por ID: {}", id);
			} else {
				logger.warn("No se encontró Rol con ID: {}", id);
			}
			return rol;
		} catch (Exception e) {
			logger.error("Error al obtener Rol por ID: {}", id, e.getMessage());
			return null;
		}
	}

	@Override
	public RolDto RolToDto(Rol rol) {
		logger.info("Iniciando la conversión de Rol a RolDto");

		try {
			RolDto rolDto = new RolDto();
			rolDto.setId(rol.getId());
			rolDto.setNombre(rol.getNombre());
			logger.info("Conversión de Rol a RolDto realizada exitosamente");
			return rolDto;
		} catch (Exception e) {
			logger.error("Error al convertir Rol a RolDto: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public List<RolDto> listaRolToDto(List<Rol> roles) {
		logger.info("Iniciando la conversión de lista de Rol a lista de RolDto");

		try {
			List<RolDto> rolesDto = new ArrayList<>();
			for (Rol rol : roles) {
				rolesDto.add(RolToDto(rol));
			}
			logger.info("Conversión de lista de Rol a lista de RolDto realizada exitosamente");
			return rolesDto;
		} catch (Exception e) {
			logger.error("Error al convertir lista de Rol a lista de RolDto: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public List<Rol> listaRolToDao(List<RolDto> rolesDto) {
		logger.info("Iniciando la conversión de lista de RolDto a lista de Rol");

		try {
			List<Rol> roles = new ArrayList<>();
			for (RolDto rolDto : rolesDto) {
				roles.add(RolToDao(rolDto));
			}
			logger.info("Conversión de lista de RolDto a lista de Rol realizada exitosamente");
			return roles;
		} catch (Exception e) {
			logger.error("Error al convertir lista de RolDto a lista de Rol: {}", e.getMessage());
			return null;
		}
	}
}
