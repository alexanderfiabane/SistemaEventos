/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.controller.util;

import com.javaleks.commons.mail.Email;
import com.javaleks.commons.util.ArrayUtils;
import com.javaleks.commons.util.DateUtils;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
public class EmailSender {
    private Properties javamailProperties;

    public void setJavamailProperties(final Properties javamailProperties) {
        this.javamailProperties = javamailProperties;
    }

    public void send(final Email email, final Map<String, Object> params) throws Exception {

        Session session;

        final String auth = (String) javamailProperties.get("mail.smtp.auth");
        if(auth != null && Boolean.valueOf(auth)) {
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    final String user = javamailProperties.getProperty("mail.auth.username");
                    final String pass = javamailProperties.getProperty("mail.auth.password");
                    return new PasswordAuthentication(user, pass);
                }
            };

            session = Session.getInstance(javamailProperties, authenticator);
        } else {
            // create some properties and get the default Session
            session = Session.getInstance(javamailProperties);
        }

        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email.getFrom()));
        msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(email.getTo()));

        if (!ArrayUtils.isEmptyOrNull(email.getCc())) {
            msg.setRecipients(Message.RecipientType.CC, getInternetAddresses(email.getCc()));
        }
        msg.setSubject(email.getSubject(), "UTF-8");

        // create and fill the message part
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(email.getContent(params), email.getContentType());

        // create the Multipart and its parts to it
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(bodyPart);

        // add the Multipart to the message
        msg.setContent(mp);
        msg.setSentDate(DateUtils.now());

        // send the message
        Transport.send(msg);
    }

    private InternetAddress[] getInternetAddresses(final String[] adresses) throws Exception {
        if (ArrayUtils.isEmptyOrNull(adresses)) {
            return new InternetAddress[]{};
        }
        InternetAddress[] iAddresses = new InternetAddress[adresses.length];
        for (int i = 0; i < adresses.length; i++) {
            iAddresses[i] = new InternetAddress(adresses[i]);
        }
        return iAddresses;
    }
}
