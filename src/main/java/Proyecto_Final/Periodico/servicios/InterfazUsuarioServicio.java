package Proyecto_Final.Periodico.servicios;

import java.util.List;

import Proyecto_Final.Periodico.daos.Usuario;
import Proyecto_Final.Periodico.dtos.UsuarioDto;

/**
 * Interfaz que define los métodos que debe implementar un servicio para la
 * gestión de usuarios en el sistema.
 * 
 * @author pabloRG
 */
public interface InterfazUsuarioServicio {

    /**
     * Registra a un nuevo usuario en el sistema, verificando si ya existe
     * previamente.
     * 
     * @param userDto El DTO del usuario a registrar.
     * @return El DTO del usuario registrado.
     */
	public UsuarioDto registro(UsuarioDto userDto, Long idAdmin);

    /**
     * Busca un usuario por su identificador en la base de datos.
     * 
     * @param id El identificador del usuario a buscar.
     * @return El DTO del usuario encontrado, o null si no se encuentra.
     */
    public UsuarioDto buscarPorId(long id);

    /**
     * Busca un usuario por su dirección de correo electrónico registrada.
     * 
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return El DTO del usuario encontrado, o null si no se encuentra.
     */
    public UsuarioDto buscarPorEmail(String email);

    /**
     * Busca un usuario por su número de teléfono registrado.
     * 
     * @param apodo El número de teléfono del usuario a buscar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean buscarPorTelefono(String apodo);

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return La lista de DTOs de usuarios.
     */
    public List<UsuarioDto> buscarTodos();

    /**
     * Método para obtener una lista de todos los usuarios para su visualización en
     * la vista, excluyendo a los usuarios con el rol "SUPERADMIN".
     * 
     * @return Lista de objetos UsuarioDto que representan a los usuarios.
     */
    public List<UsuarioDto> buscarTodosListaVista();

    /**
     * Convierte los datos de un usuario DTO a los datos de un usuario DAO.
     * 
     * @param usuarioDto El DTO del usuario a convertir.
     * @return El usuario DAO resultante de la conversión.
     */
    public Usuario usuarioToDao(UsuarioDto usuarioDto);

    /**
     * Convierte los datos de un usuario DAO a los datos de un usuario DTO.
     * 
     * @param usuarioDao El DAO del usuario a convertir.
     * @return El DTO de usuario resultante de la conversión.
     */
    public UsuarioDto usuarioToDto(Usuario usuarioDao);

    /**
     * Busca un usuario por su token de recuperación de contraseña.
     * 
     * @param token El token asociado al usuario que solicitó la recuperación.
     * @return El DTO del usuario encontrado, o null si no se encuentra.
     */
    public UsuarioDto obtenerUsuarioPorToken(String token);

    /**
     * Inicia el proceso de recuperación de contraseña para un usuario mediante su
     * correo electrónico.
     * 
     * @param emailUsuario El correo electrónico del usuario para recuperar la
     *                     contraseña.
     * @return true si el proceso se inicia correctamente, false en caso contrario.
     */
    public boolean iniciarResetPassConEmail(String emailUsuario);

    /**
     * Establece una nueva contraseña para un usuario utilizando el token de
     * recuperación.
     * 
     * @param usuario El DTO del usuario con la nueva contraseña y el token de
     *                recuperación.
     * @return true si la contraseña se actualiza correctamente, false en caso
     *         contrario.
     */
    public boolean modificarContraseñaConToken(UsuarioDto usuario);

    /**
     * Verifica si la cuenta de usuario asociada al correo electrónico está
     * confirmada.
     * 
     * @param email La dirección de correo electrónico del usuario.
     * @return true si la cuenta está confirmada, false si no lo está.
     */
    public boolean CuentaConfirmada(String email);

    /**
     * Confirma la cuenta de usuario cambiando la propiedad 'verificado' a true.
     * 
     * @param token El token de confirmación asociado a la cuenta.
     * @return true si la cuenta se confirma correctamente, false en caso contrario.
     */
    boolean confirmarCuenta(String token);

    /**
     * Elimina un usuario de la base de datos mediante su identificador.
     * 
     * @param id El identificador del usuario a eliminar.
     */
    public void eliminar(long id);

    /**
     * Modifica los datos de un usuario existente en la base de datos.
     * 
     * @param usuarioDto El usuario con los datos modificados.
     */
    public void modificarUsuario(UsuarioDto usuarioDto);

    /**
     * Cuenta la cantidad de usuarios que tienen un rol específico.
     * 
     * @param rol El nombre del rol.
     * @return El número de usuarios que tienen el rol especificado.
     */
    public int contarUsuariosPorRol(String rol);

    /**
     * Cambia el rol de un usuario específico.
     * 
     * @param userId El identificador del usuario.
     * @param roleId El identificador del nuevo rol.
     */
    public void cambiarRol(Long userId, Long roleId);

    /**
     * Cambia el estado de bloqueo de un usuario.
     * 
     * @param userId    El identificador del usuario.
     * @param bloqueado El nuevo estado de bloqueo.
     */
    public void cambiarEstadoBloqueo(Long userId, boolean bloqueado);

    /**
     * Cambia las fechas de bloqueo de un usuario.
     * 
     * @param userId       El identificador del usuario.
     * @param inicioBloqueo La fecha de inicio del bloqueo.
     * @param finBloqueo    La fecha de fin del bloqueo.
     */
    public void cambiarFechasBloqueo(Long userId, String inicioBloqueo, String finBloqueo);
}
