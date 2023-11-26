package co.edu.ufps.ayd.convocatoria.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailUser;

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(String email, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailUser);
            message.setTo(email);
            message.setSubject("Contraseña generada");
            message.setText("Su nueva contraseña es: " + password);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electrónico:" + e.getMessage(), e);
        }
    }

    public void notificarEvaluador(String email, String nombre) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailUser);
            message.setTo(email);
            message.setSubject("Has sido asignado como evaluador");
            message.setText("Te toca evaluar la propuesta: " + nombre);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electrónico:" + e.getMessage(), e);
        }
    }

}
