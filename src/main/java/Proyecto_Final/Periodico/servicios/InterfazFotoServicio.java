package Proyecto_Final.Periodico.servicios;

/**
 * Interfaz que define los métodos para el servicio relacionado con el manejo de
 * imágenes.
 * 
 * @author pabloRG
 */
public interface InterfazFotoServicio {

	/**
	 * Carga la foto predeterminada.
	 * 
	 * @return Un array de bytes que representa la imagen predeterminada.
	 */
	public byte[] cargarFotoPredeterminada();

	/**
	 * Convierte una imagen en formato base64 a un array de bytes.
	 * 
	 * @param imgBase64 La cadena que representa la imagen en formato base64.
	 * @return Un array de bytes que representa la imagen.
	 */
	public byte[] convertirAarrayBytes(String imgBase64);

	/**
	 * Convierte un array de bytes de una imagen a su representación en formato
	 * base64.
	 * 
	 * @param datosImg Los datos de la imagen en formato de array de bytes.
	 * @return Una cadena que representa la imagen en formato base64.
	 */
	public String convertirAbase64(byte[] datosImg);
}
