/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller.util;

import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.util.ConfigurableVelocityProcessor;
import br.esp.sysevent.util.VelocityProcessor;
import br.esp.sysevent.web.guest.command.InscricaoCommand;
import br.esp.sysevent.web.guest.controller.FormInscricaoController;
import com.javaleks.commons.mail.SimpleEmail;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.DateUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe utilitária que oferece vários métodos uteis para serem usados nos controllers.
 * <p/>
 * Visa a reutilização de código.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class ControllerUtils {

    public static DateFormat getDateFormat(final MessageSource messageSource, final Locale locale) {
        final String format = getMessage("i18n.dateFormat.java", messageSource, locale);
        if (CharSequenceUtils.isBlankOrNull(format)) {
            throw new NoSuchMessageException("i18n.dateFormat.java is empty!");
        }
        return new SimpleDateFormat(format, locale);
    }

    public static String getMessage(final String key, final MessageSource messageSource, final Locale locale) {
        return getMessage(key, messageSource, locale, true);
    }

    public static String getMessage(final String key, final MessageSource messageSource, final Locale locale, boolean mandatory) {
        String message = null;
        try {
            message = messageSource.getMessage(key, new Object[]{}, locale);
        } catch (NoSuchMessageException ex) {
            if (mandatory) {
                throw ex;
            }
        }
        return message;
    }

    public static Usuario getLoggedUser() {
        final Authentication auth = getAuth();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        final Object principal = auth.getPrincipal();
        return auth.isAuthenticated() && UserDetails.class.isAssignableFrom(principal.getClass()) ? ((Usuario) principal) : null;
    }

    public static boolean isLoggedIn() {
        return getLoggedUser() != null;
    }

    public static boolean isLoggedInAsAdmin() {
        return getLoggedUser() != null && getLoggedUser().isAdmin();
    }

    public static boolean isLoggedInAsUser() {
        return getLoggedUser() != null && getLoggedUser().isUser();
    }

    private static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void writeHttpAttached(final byte[] content, final String fullName, final String mimeType, final HttpServletResponse response) throws IOException {
		writeHttp(content, fullName, mimeType, "attachment", response);
	}

    public static void writeHttp(final byte[] content, final String fileName, final String mimeType, final String contentDisposition, final HttpServletResponse response) throws IOException {
		final StringBuffer contentDispositionBuffer = new StringBuffer();
		contentDispositionBuffer.append(contentDisposition);
		contentDispositionBuffer.append(";filename=");
		contentDispositionBuffer.append(fileName);
		response.setHeader("Content-Disposition", contentDispositionBuffer.toString());
		response.setIntHeader("max-age", 120);// vamos habilitar uma cache
		response.setContentType(mimeType);
		response.setContentLength(content.length);

		final ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(content);
	}

    public static VelocityProcessor<Object> getVelocityProcessor() {
        final Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", "utf-8");
        velocityProperties.setProperty("output.encoding", "utf-8");
        velocityProperties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        final ConfigurableVelocityProcessor<Object> velocityProcessor = new ConfigurableVelocityProcessor<Object>(velocityProperties);
        velocityProcessor.addTool("dateUtils", DateUtils.class);
        return velocityProcessor;
    }

    public static void sendMail(Inscricao inscricao, String subject, String modelName) {
        final Properties mailProperties = new Properties();
        try {
            ClassPathResource resource = new ClassPathResource("br/esp/sysevent/web/mail/mail.properties");
            mailProperties.load(resource.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read <mail.properties>.", ex);
        }

        final InputStream model = ControllerUtils.class.getClassLoader().getResourceAsStream("br/esp/sysevent/web/mail/" + modelName);
        final String content = getVelocityProcessor().process(model, Collections.singletonMap("inscricao", (Object) inscricao));

        sendMail(mailProperties, new String[]{inscricao.getConfraternista().getPessoa().getEndereco().getEmail()}, subject, content);
        
//        final SimpleEmail email = new SimpleEmail();
//        email.setFrom(mailProperties.getProperty("mail.smtp.from.name") 
////                + " - " + inscricao.getEdicaoEvento().getEvento().getSigla() 
//                + "<" + mailProperties.getProperty("mail.smtp.from") + ">");
//        email.setTo(new String[]{inscricao.getConfraternista().getPessoa().getEndereco().getEmail()});
//        email.setSubject(subject);
//        email.setRawContent(content);
//
//        final EmailSender emailSender = new EmailSender();
//        emailSender.setJavamailProperties(mailProperties);
//        try {
//            emailSender.send(email, null);
//        } catch (Exception ex) {
//            Logger.getLogger(FormInscricaoController.class.getName()).log(Level.SEVERE, "Erro enviando email", ex);
//        }
    }

    public static void sendMailInscricaoUsuario(InscricaoCommand inscricaoCmd, String subject, String modelName) {
        final Properties mailProperties = new Properties();
        try {
            ClassPathResource resource = new ClassPathResource("br/esp/sysevent/web/mail/mail.properties");
            mailProperties.load(resource.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read <mail.properties>.", ex);
        }

        final InputStream model = ControllerUtils.class.getClassLoader().getResourceAsStream("br/esp/sysevent/web/mail/" + modelName);
        final String content = getVelocityProcessor().process(model, Collections.singletonMap("inscricaoCmd", (Object) inscricaoCmd));

        sendMail(mailProperties, new String[]{inscricaoCmd.getInscricao().getConfraternista().getPessoa().getEndereco().getEmail()}, subject, content);
        
//        final SimpleEmail email = new SimpleEmail();
//         email.setFrom(mailProperties.getProperty("mail.smtp.from.name") 
////                + " - " + inscricaoCmd.getInscricao().getEdicaoEvento().getEvento().getSigla() 
//                + "<" + mailProperties.getProperty("mail.smtp.from") + ">");        
//        email.setTo(new String[]{inscricaoCmd.getInscricao().getConfraternista().getPessoa().getEndereco().getEmail()});
//        email.setSubject(subject);
//        email.setRawContent(content);
//
//        final EmailSender emailSender = new EmailSender();
//        emailSender.setJavamailProperties(mailProperties);
//        try {
//            emailSender.send(email, null);
//        } catch (Exception ex) {
//            Logger.getLogger(FormInscricaoController.class.getName()).log(Level.SEVERE, "Erro enviando email", ex);
//        }
    }

    public static void sendMailUsuario(Usuario usuario, String subject, String modelName) {
        final Properties mailProperties = new Properties();
        try {
            ClassPathResource resource = new ClassPathResource("br/esp/sysevent/web/mail/mail.properties");
            mailProperties.load(resource.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read <mail.properties>.", ex);
        }

        final InputStream model = ControllerUtils.class.getClassLoader().getResourceAsStream("br/esp/sysevent/web/mail/" + modelName);
        final String content = getVelocityProcessor().process(model, Collections.singletonMap("usuario", (Object) usuario));

        sendMail(mailProperties, new String[]{usuario.getPessoa().getEndereco().getEmail()}, subject, content);
        
//        final SimpleEmail email = new SimpleEmail();
//        email.setFrom(mailProperties.getProperty("mail.smtp.from.name") + "<" + mailProperties.getProperty("mail.smtp.from") +">");
//        email.setTo(new String[]{usuario.getPessoa().getEndereco().getEmail()});
//        email.setSubject(subject);
//        email.setRawContent(content);
//
//        final EmailSender emailSender = new EmailSender();
//        emailSender.setJavamailProperties(mailProperties);
//        try {
//            emailSender.send(email, null);
//        } catch (Exception ex) {
//            Logger.getLogger(FormInscricaoController.class.getName()).log(Level.SEVERE, "Erro enviando email", ex);
//        }
    }
    
    private static void sendMail(final Properties mailProperties, String[] sendTo, String subject, String content){
        String tsl = mailProperties.getProperty("mail.smtp.starttls.enable");
        Properties props = new Properties();        
        props.put("mail.smtp.starttls.enable", tsl);
        props.put("mail.smtp.host", mailProperties.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", mailProperties.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", mailProperties.getProperty("mail.smtp.auth"));
        if(!CharSequenceUtils.isBlankOrNull(tsl)){
            
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailProperties.getProperty("mail.auth.username"), mailProperties.getProperty("mail.auth.password"));
                }
            });    
            
            try {
                String sysName = mailProperties.getProperty("mail.smtp.from.name");
                Address adFrom = null;
                try {
                    adFrom = new InternetAddress(mailProperties.getProperty("mail.smtp.from"), sysName);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ControllerUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
                Message message = new MimeMessage(session);                
                message.setFrom(adFrom);
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(sendTo[0]));
                message.setSubject(subject);
                //message.setText(content);
                message.setContent(content, "text/html; charset=utf-8");
                Logger.getLogger(FormInscricaoController.class.getName()).log(Level.INFO, "Enviando email por TLS...");
                Transport.send(message);                
                Logger.getLogger(FormInscricaoController.class.getName()).log(Level.INFO, "Email enviado por TLS. =D");
            } catch (MessagingException e) {
                Logger.getLogger(FormInscricaoController.class.getName()).log(Level.SEVERE, "Erro enviando email", e);                
                Logger.getLogger(e.getMessage());                
            }
        }else{
            final SimpleEmail email = new SimpleEmail();
             email.setFrom(mailProperties.getProperty("mail.smtp.from.name") 
    //                + " - " + inscricaoCmd.getInscricao().getEdicaoEvento().getEvento().getSigla() 
                    + "<" + mailProperties.getProperty("mail.smtp.from") + ">");        
            email.setTo(sendTo);
            email.setSubject(subject);
            email.setRawContent(content);

            final EmailSender emailSender = new EmailSender();
            emailSender.setJavamailProperties(mailProperties);
            try {
                emailSender.send(email, null);
            } catch (Exception ex) {
                Logger.getLogger(FormInscricaoController.class.getName()).log(Level.SEVERE, "Erro enviando email", ex);
            }
        }
    }
}
