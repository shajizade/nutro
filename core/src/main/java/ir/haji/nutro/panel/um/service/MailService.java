package ir.haji.nutro.panel.um.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Saeedon 1/9/2018.
 */
@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(MailService.class);

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.ad-venture.ir");
        mailSender.setPort(587);

        mailSender.setUsername("noreply@ad-venture.ir");
        mailSender.setPassword("@dVenture");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.debug", "false");

        return mailSender;
    }

    public Boolean sendCode(String email, String code) {
        try {
            JavaMailSender javaMailSender = getJavaMailSender();
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom("noreply@ad-venture.ir");
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("پنل ادونچر", "UTF-8");
            message.setText("رمز عبور شما برای استفاده از پنل ادونچر: " + code, "UTF-8");
            javaMailSender.send(message);
            logger.info("Code Sent To Email " + email);
            return true;
        } catch (Exception ex) {
            logger.info("Error Sending Code To Email " + email);
            ex.printStackTrace();
            return false;
        }
    }


}
