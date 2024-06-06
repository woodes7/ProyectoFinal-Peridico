package Proyecto_Final.Periodico.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ImplementacioEmailservicio implements InterfazEmailServicio {

    private static final Logger logger = LoggerFactory.getLogger(ImplementacioEmailservicio.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarEmailRecuperacion(String emailDestino, String nombreUsuario, String token) {
        try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("pablorj991@gmail.com");
            helper.setTo(emailDestino);
            helper.setSubject("RESTABLECER CONTRASEÑA  PERIODICO PRIMAVERA");

            String urlDominio = "http://localhost:8080";
            String urlDeRecuperacion = String.format("%s/auth/recuperar?token=%s", urlDominio, token);

            String cuerpoMensaje = String.format(
                    "﻿<!DOCTYPE html> <html lang='es'> <body> <div style='width: 600px; padding: 20px; border: 2px solid #ff9900; border-radius: 12px;"
                            + " font-family: Sans-serif'> <h1 style='color:#192255'>Restablecer contraseña<b style='color:#ff9900'>  Periodico Primavera app</b></h1>"
                            + " <p style='margin-bottom:25px'>Estimado/a&nbsp;<b>%s</b>:</p> <p style='margin-bottom:25px'>"
                            + "Recibiste este correo porque se solicitó un restablecimiento de contraseña para tu cuenta. Haz clic en el botón que aparece a continuación para cambiar tu contraseña.</p>"
                            + " <a style='padding: 10px 15px; border-radius: 20px; background-color: #285845; color: white; text-decoration: none' href='%s' target='_blank'>Cambiar contraseña</a>"
                            + " <p style='margin-top:25px'>Si no solicitaste este restablecimiento de contraseña, puedes ignorar este correo de forma segura.</p>"
                            + " <p>Gracias por utilizar nuestros servicios.</p> </div> </body> </html>",
                    nombreUsuario, urlDeRecuperacion);

            helper.setText(cuerpoMensaje, true);

            javaMailSender.send(mensaje);
            // Registro del correo enviado en el log
            logger.info("Correo de recuperación enviado a: {}", emailDestino);

        } catch (MailException me) {
            // Registro de errores en el log en caso de excepción
            logger.error("[Error EmailServicioImpl - enviarEmailRecuperacion()] Ha ocurrido un error al enviar el email! {}",
                    me.getMessage());
        } catch (MessagingException el) {
            // Registro de errores en el log en caso de excepción
            logger.error("Error EmailServicioImpl - Ha ocurrido un error al enviar el email de recuperación: {}",
                    el.getMessage());
        }
    }

    @Override
    public void enviarEmailConfirmacion(String emailDestino, String nombreUsuario, String token) {
        logger.info("Iniciando el proceso de envío de correo de confirmación a: {}", emailDestino);

        try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("pablorj991@gmail.com");
            helper.setTo(emailDestino);
            helper.setSubject("Confirmación de cuenta PeriodicoPrimavera");

            String urlDominio = "http://localhost:8080";
            String urlDeConfirmacion = String.format("%s/auth/confirmarCuenta?token=%s", urlDominio, token);

            String cuerpoMensaje = String.format(
                    "﻿<!DOCTYPE html> <html lang='es'> <body> <div style='width: 600px; padding: 20px; border: 2px solid black; border-radius: 13px; background-color: #DEDEDE; font-family: Sans-serif'> <h1 style='color:#1f3c85'>Confirmar cuenta<b style='color:#5993d3'>  Periodico Primavera</b></h1>"
                            + " <p style='margin-bottom:25px'>Estimado/a&nbsp;<b>%s</b>:</p> <p style='margin-bottom:25px'>"
                            + "Bienvenido/a a Periodico Primavera. Para confirmar tu cuenta, haz clic en el botón que aparece a continuación:</p>"
                            + " <a style='padding: 10px 15px; border-radius: 10px; background-color: #5993d3; color: white; text-decoration: none' href='%s' target='_blank'>Confirmar cuenta</a>"
                            + " <p style='margin-top:25px'>Gracias por unirte a Periodico Primavera.</p> </div> </body> </html>",
                    nombreUsuario, urlDeConfirmacion);

            helper.setText(cuerpoMensaje, true);

            javaMailSender.send(mensaje);
            // Registro del correo enviado en el log
            logger.info("Correo de confirmación enviado a: {}", emailDestino);

        } catch (MailException me) {
            // Registro de errores en el log en caso de excepción
            logger.error("[Error EmailServicioImpl - enviarEmailConfirmacion()] Ha ocurrido un error al enviar el email! {}",
                    me.getMessage());
        } catch (MessagingException el) {
            // Registro de errores en el log en caso de excepción
            logger.error("Error EmailServicioImpl - Ha ocurrido un error al enviar el email de confirmación: {}",
                    el.getMessage());
        }
    }
}
