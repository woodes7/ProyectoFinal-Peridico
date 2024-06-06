package Proyecto_Final.Periodico.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImplementacionFotoServicio implements InterfazFotoServicio {
	private static final Logger logger = LoggerFactory.getLogger(ImplementacionFotoServicio.class);

	@Override
	public String convertirAbase64(byte[] datosImg) {
	    try {
	        logger.info("Iniciando el método convertirAbase64");
	        if (datosImg != null && datosImg.length > 0) {
	            return Base64.getEncoder().encodeToString(datosImg);
	        }
	        return null;
	    } catch (Exception e) {
	        logger.error("Error al convertir datos de imagen a base64: {}", e.getMessage());
	        return null;
	    }
	}

	@Override
	public byte[] convertirAarrayBytes(String imgBase64) {
	    try {
	        logger.info("Iniciando el método convertirAarrayBytes");
	        if (imgBase64 != null && !imgBase64.isEmpty()) {
	            return Base64.getDecoder().decode(imgBase64);
	        }
	        return null;
	    } catch (Exception e) {
	        logger.error("Error al convertir imagen base64 a array de bytes: {}", e.getMessage());
	        return null;
	    }
	}

	@Override
	public byte[] cargarFotoPredeterminada() {
	    try {
	        logger.info("Iniciando el método cargarFotoPredeterminada");
	        String Fotopredeterminada = "homerBeerDefault.jpg";
	        Path path = Paths.get("src/main/resources/static/css/assets/" + Fotopredeterminada);
	        return Files.readAllBytes(path);
	    } catch (IOException e) {
	        logger.error("Error al cargar la imagen predeterminada: {}", e.getMessage());
	        return null;
	    }
	}
}
