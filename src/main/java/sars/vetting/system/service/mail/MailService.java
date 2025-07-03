/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service.mail;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import sars.vetting.system.common.EmailApprovalDTO;
import sars.vetting.system.common.EmailReminderDTO;
import sars.vetting.system.common.FormAttachmentDTO;
import sars.vetting.system.common.RerouteDTO;

/**
 *
 * @author S2028398
 */
public class MailService {

    private final int MAIL_SERVER_PORT = 25;
    private final String SMTP_SERVER = "smtp.sars.gov.za";
    private final String SOURCE_ADDRESS = "noreplyvetting@sars.gov.za";

    public MailService() {

    }

    public boolean send(List<String> destinationAddress, String subject, String message) {

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);

        try {
            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(SOURCE_ADDRESS);
            msg.setFrom(addressFrom);
            setReceipients(destinationAddress, msg);
            msg.setSubject(subject);
            msg.setContent(message, "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean send(List<String> destinationAddress, String subject, String message, String attachmentPath) {

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);

        try {

            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(SOURCE_ADDRESS);
            msg.setFrom(addressFrom);
            setReceipients(destinationAddress, msg);
            msg.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText(message);
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Set the attachment file
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentPath);
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);
            msg.setSentDate(new Date());
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    private void setReceipients(List<String> to, Message message)
            throws AddressException, MessagingException {
        InternetAddress[] addressTo = new InternetAddress[to.size()];
        int index = 0;
        for (String toAddress : to) {
            if (toAddress != null && toAddress.length() > 0) {
                addressTo[index] = new InternetAddress(toAddress);
                index++;
            }
        }
        message.setRecipients(Message.RecipientType.TO, addressTo);
    }

    ///cc email QA/MANAGER/OFFICER  On Aproval
    public boolean sendEmailCCUsers(EmailApprovalDTO emailApprovalDTO) {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_SERVER);
            props.put("mail.debug", "true");
            Session session = Session.getInstance(props);
            session.setDebug(false);

            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(emailApprovalDTO.getSourceEmailAddress());

            msg.setFrom(addressFrom);

            InternetAddress addressTo = new InternetAddress(emailApprovalDTO.getDestinationEmailAddress());
            InternetAddress addressManger = new InternetAddress(emailApprovalDTO.getCcManagerEmailAddress());
            InternetAddress addressOfficer = new InternetAddress(emailApprovalDTO.getOfficeEmailAddress());
            InternetAddress addressQA = new InternetAddress(emailApprovalDTO.getQaEmailAddress());

            msg.setRecipient(Message.RecipientType.CC, addressManger);
            msg.setRecipient(Message.RecipientType.CC, addressOfficer);
            msg.setRecipient(Message.RecipientType.CC, addressQA);
            msg.setRecipient(Message.RecipientType.TO, addressTo);

            msg.setSentDate(new Date());

            msg.setSubject(emailApprovalDTO.getEmailSubject());

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(emailApprovalDTO.getMessage());

            messageBodyPart.setContent(emailApprovalDTO.getMessage(), "text/html");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

            return true;

        } catch (AddressException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MessagingException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    ///cc email oldOfficer On reroute
    public boolean sendEmailReroute(RerouteDTO rerouteDTO) {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_SERVER);
            props.put("mail.debug", "true");
            Session session = Session.getInstance(props);
            session.setDebug(false);

            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(rerouteDTO.getSourceEmailAddress());

            msg.setFrom(addressFrom);

            InternetAddress addressTo = new InternetAddress(rerouteDTO.getDestinationEmailAddress());
            InternetAddress addressOfficer = new InternetAddress(rerouteDTO.getOldOfficer());

            msg.setRecipient(Message.RecipientType.CC, addressOfficer);
            msg.setRecipient(Message.RecipientType.TO, addressTo);

            msg.setSentDate(new Date());

            msg.setSubject(rerouteDTO.getEmailSubject());

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(rerouteDTO.getMessage());

            messageBodyPart.setContent(rerouteDTO.getMessage(), "text/html");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

            return true;

        } catch (AddressException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MessagingException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    ///cc email
    public boolean sendEmailCC(EmailReminderDTO emailReminderDTO) {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_SERVER);
            props.put("mail.debug", "true");
            Session session = Session.getInstance(props);
            session.setDebug(false);

            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(emailReminderDTO.getSourceEmailAddress());

            msg.setFrom(addressFrom);

            InternetAddress addressTo = new InternetAddress(emailReminderDTO.getDestinationEmailAddress());
            InternetAddress addressCc = new InternetAddress(emailReminderDTO.getCcManagerEmailAddress());

            msg.setRecipient(Message.RecipientType.CC, addressCc);
            msg.setRecipient(Message.RecipientType.TO, addressTo);

            msg.setSentDate(new Date());

            msg.setSubject(emailReminderDTO.getEmailSubject());

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(emailReminderDTO.getMessage());

            messageBodyPart.setContent(emailReminderDTO.getMessage(), "text/html");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

            return true;

        } catch (AddressException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MessagingException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    ///attachment
    public boolean sendEmail(FormAttachmentDTO formAttachmentDTO) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_SERVER);
            props.put("mail.debug", "true");
            Session session = Session.getInstance(props);
            session.setDebug(false);

            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(formAttachmentDTO.getSourceEmailAddress());

            msg.setFrom(addressFrom);

            InternetAddress addressTo = new InternetAddress(formAttachmentDTO.getDestinationEmailAddress());
            InternetAddress addressCc = new InternetAddress(formAttachmentDTO.getCcManagerEmailAddress());

            msg.setRecipient(Message.RecipientType.CC, addressCc);
            msg.setRecipient(Message.RecipientType.TO, addressTo);

            msg.setSentDate(new Date());

            msg.setSubject(formAttachmentDTO.getEmailSubject());

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(formAttachmentDTO.getMessage());

            messageBodyPart.setContent(formAttachmentDTO.getMessage(), "text/html");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/forms");
            String[] attachs = {
                path + "/z204Form.pdf",
                path + "/Dots360 Consent Form v4.4 (SARS) (003) Dots Africa.pdf",
                path + "/SAPS 91 A Finger prints form 2023.pdf",
                path + "/Vetting Consent Form - Amended.pdf"
            };
            for (String attachmet : attachs) {
                File file = new File(attachmet);
                if (file.exists()) {
                    MimeBodyPart bodyPart = new MimeBodyPart();
                    FileDataSource dataSource = new FileDataSource(file);
                    bodyPart.setDataHandler(new DataHandler(dataSource));
                    bodyPart.setFileName(file.getName());
                    multipart.addBodyPart(bodyPart);
                } else {
                    System.out.println("Attachment not found : " + attachmet);
                }
            }
            msg.setContent(multipart);

            Transport.send(msg);

            return true;

        } catch (AddressException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MessagingException ex) {

            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
}
