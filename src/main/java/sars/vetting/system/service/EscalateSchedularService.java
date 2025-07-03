/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sars.vetting.system.common.DateUtil;
import sars.vetting.system.common.EmailReminderDTO;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.mail.LDAPService;
import sars.vetting.system.service.mail.MailService;

/**
 *
 * @author S2028398
 */
@Configuration
@EnableScheduling
public class EscalateSchedularService {

    private final String SOURCE_ADDRESS = "noreplyvetting@sars.gov.za";
    private static final Logger logger = LoggerFactory.getLogger(EscalateSchedularService.class);

    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;

    private EmailNotification emailNotification;
    private LDAPService lDAPService = new LDAPService();

    @Scheduled(cron = "0 0 0 * * ?")
    public void reminder() {
        System.out.println("Schedular is begining");
        Pageable pageable = PageRequest.of(0, 15);
        Slice<Vetting> escalatedVettings = vettingService.findReminderVettings(pageable);
        System.out.println(escalatedVettings.getNumberOfElements() + " has been collected");
        for (Vetting vetting : escalatedVettings.toList()) {
            if (vetting.getVettingStatus().equals(VettingStatus.FORMS_ISSUED)) {
                emailNotification = emailNotificationService.findByNotificationType(NotificationType.REMINDER_TO_SUBJECT);
                //reminder subject
                if ((DateUtils.addDays(vetting.getCreatedDate(), 5).compareTo(new Date()) < 0) || (DateUtils.addDays(vetting.getUpdatedDate(), 5).compareTo(new Date()) < 0)) {
                    try {
                        sendReminderMail(vetting, emailNotification);
                    } catch (FileNotFoundException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                emailNotification = emailNotificationService.findByNotificationType(NotificationType.REMINDER_TO_LINE_MANAGER);
                //reminder line manager
                if ((DateUtils.addDays(vetting.getCreatedDate(), 5).compareTo(new Date()) < 0) || (DateUtils.addDays(vetting.getUpdatedDate(), 5).compareTo(new Date()) < 0)) {
                    try {
                        sendReminderMail(vetting, emailNotification);
                    } catch (FileNotFoundException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("Reminder Schedular has finished");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void escalate() {
        System.out.println("Schedular is begining");
        List<Vetting> escalatedVettings = vettingService.findEscalatedVettings();
        System.out.println(escalatedVettings.size() + " has been collected");
        for (Vetting vetting : escalatedVettings) {
            if (vetting.getVettingStatus().equals(VettingStatus.SARS_PROCESSING) || vetting.getVettingStatus().equals(VettingStatus.FORMS_ISSUED) || vetting.getVettingStatus().equals(VettingStatus.SSA_PROCESSING) || vetting.getVettingStatus().equals(VettingStatus.SENT_TO_QA) || vetting.getVettingStatus().equals(VettingStatus.CLEARANCE_ISSUED) || vetting.getVettingStatus().equals(VettingStatus.CLEARANCE_DENIED)) {
                emailNotification = emailNotificationService.findByNotificationType(NotificationType.ESCALATION_MAIL);
                //if subject did not action within 30 day the escalate to analyst
                if (vetting.getVettingStatus().equals(VettingStatus.FORMS_ISSUED)) {
                    vetting.setVettingStatus(VettingStatus.ANALYST_POOL);
                }
                //emailtoadministrator OR analyst if is from subject
                if ((DateUtils.addDays(vetting.getCreatedDate(), 30).compareTo(new Date()) < 0) || (DateUtils.addDays(vetting.getUpdatedDate(), 30).compareTo(new Date()) < 0)) {
                    try {
                        sendReminderMail(vetting, emailNotification);
                    } catch (FileNotFoundException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("Escalte Schedular has finished");
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void expiredVetting() {
        System.out.println("Expired vetting Schedular is begining");
        Pageable pageable = PageRequest.of(0, 15);
        Slice<Vetting> escalatedVettings = vettingService.findExpiredVettings(pageable);
        System.out.println(escalatedVettings.getNumberOfElements() + " has been collected");
        for (Vetting vetting : escalatedVettings.toList()) {
            if (vetting.getVettingStatus().equals(VettingStatus.APPROVED)) {
                emailNotification = emailNotificationService.findByNotificationType(NotificationType.REMINDER_OF_EXPIRED_CLEARENCE);
                //reminder expiration tosubject
                if ((DateUtils.addDays(vetting.getCreatedDate(), 1642).compareTo(new Date()) < 0)) {
                    try {
                        sendReminderMail(vetting, emailNotification);
                    } catch (FileNotFoundException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(EscalateSchedularService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("Expired vetting Schedular has finished");
    }

    public void sendReminderMail(Vetting vetting, EmailNotification emailNotification) throws UnknownHostException, FileNotFoundException, IOException {
        String subject, body;

        MailService mailService = new MailService();
        List<String> emailNotificationRecipients = new ArrayList<>();
        if (emailNotification != null) {
            switch (emailNotification.getNotificationType()) {
                case REMINDER_TO_SUBJECT:
                    subject = emailNotification.getEmailSubject();
                    body = emailNotification.getDescription();
                    body = body.replace("vatting_date", DateUtil.getBefore(vetting.getCreatedDate(), -6));
                    break;
                case REMINDER_TO_LINE_MANAGER:
                    subject = emailNotification.getEmailSubject();
                    body = emailNotification.getDescription();
                    body = body.replace("vatted_emp", vetting.getEmployee().getFullNames());
                    break;
                case REMINDER_OF_EXPIRED_CLEARENCE:
                    subject = emailNotification.getEmailSubject();
                    body = emailNotification.getDescription();
                    break;
                case ESCALATION_MAIL:
                    subject = emailNotification.getEmailSubject();
                    body = emailNotification.getDescription();
                    break;

                default:
                    logger.warn("Unrecognized phase type: {}", emailNotification.getNotificationType());
                    return;
            }
            body = "<html><body style='font-family: Arial, sans-serif; 20px 20px 20px 20px;'>"
                    + body.replace("<p><br /></p>", "</p><p>")
                    + "</body></html>";
            if (emailNotification.getNotificationType().equals(NotificationType.REMINDER_TO_SUBJECT)) {
                emailNotificationRecipients.clear();
//                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
//                String destinationAddressCC = employeeInformationService.getEmployeeEmailAddress("s2026015");
                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
                String destinationAddressCC = employeeInformationService.getEmployeeEmailAddress("S2019152");
                emailNotificationRecipients.add(destinationAddress);
                emailNotificationRecipients.add(destinationAddressCC);

                //CC Line Manager
                EmailReminderDTO emailReminderDTO = new EmailReminderDTO();
                emailReminderDTO.setSourceEmailAddress(SOURCE_ADDRESS);
                emailReminderDTO.setDestinationEmailAddress(destinationAddress);
                emailReminderDTO.setCcManagerEmailAddress(destinationAddressCC);
                emailReminderDTO.setEmailSubject(emailNotification.getEmailSubject());
                emailReminderDTO.setMessage(body.replace("var_employee", vetting.getEmployee().getFullNames()));

                if (mailService.sendEmailCC(emailReminderDTO)) {
                    logger.info("Email sent successfully to: {}", vetting.getEmployee().getFullNames());
                }
            } else if (emailNotification.getNotificationType().equals(NotificationType.REMINDER_TO_LINE_MANAGER)) {
                //emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
//                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
                emailNotificationRecipients.add(destinationAddress);
                if (mailService.send(emailNotificationRecipients,
                        subject,
                        body.replace("emp_var", vetting.getEmployee().getManagerFullName()))) {
                    logger.info("Email sent successfully to: {}", vetting.getEmployee().getManagerFullName());
                }
            } else if (emailNotification.getNotificationType().equals(NotificationType.ESCALATION_MAIL)) {
                //emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
//                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
                emailNotificationRecipients.add(destinationAddress);
                if (mailService.send(emailNotificationRecipients,
                        subject,
                        body)) {
                    logger.info("Email sent successfully to: {}", "Administrator");
                }
            } else {
                //emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
//                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
                String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
                emailNotificationRecipients.add(destinationAddress);
                if (mailService.send(emailNotificationRecipients,
                        subject,
                        body.replace("emp_var", vetting.getEmployee().getManagerFullName()))) {
                    logger.info("Email sent successfully to: {}", vetting.getEmployee().getManagerFullName());
                }
            }
        }
    }

}
