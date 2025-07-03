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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sars.vetting.system.common.FormAttachmentDTO;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.service.mail.LDAPService;
import sars.vetting.system.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sars.vetting.system.common.EmailApprovalDTO;
import sars.vetting.system.common.EmailReminderDTO;
import sars.vetting.system.common.RerouteDTO;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2028398
 */
@Service
public class EmailNotificationSenderService implements EmailNotificationSenderServiceLocal {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationSenderService.class);

    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;

    private final String SOURCE_ADDRESS = "noreplyvetting@sars.gov.za";

    private final MailService mailService = new MailService();
    private final LDAPService lDAPService = new LDAPService();

    private EmailNotification emailNotification;

    private final List<String> emailNotificationRecipients = new ArrayList<>();

    @Override
    public void sendNotification(NotificationType notificationType, Date date, Employee recipient, Employee provider) throws UnknownHostException, FileNotFoundException, IOException {
        String subject, body;
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        emailNotificationRecipients.clear();

        switch (emailNotification.getNotificationType()) {
            case EMAIL_TO_SUBJECT:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("emp_var", provider.getFullNames());
                break;
            case SUBMITTED:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_emp", provider.getFullNames());
                if (StringUtils.isNotEmpty(provider.getSid())) {
                    body = body.replace("emp_sid", provider.getSid());
                }
                if (StringUtils.isNotEmpty(provider.getIdentityNumber())) {
                    body = body.replace("empIdNum", provider.getIdentityNumber());
                } else {
                    body = body.replace("empIdNum", provider.getPassportNumber());
                }
                break;
            case DOCUMENTS:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_emp", provider.getFullNames());
                if (StringUtils.isNotEmpty(provider.getSid())) {
                    body = body.replace("emp_sid", provider.getSid());
                }
                if (StringUtils.isNotEmpty(provider.getIdentityNumber())) {
                    body = body.replace("empIdNum", provider.getIdentityNumber());
                } else {
                    body = body.replace("empIdNum", provider.getPassportNumber());
                }
                break;
            case ALLOCATED_OFFICER:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("emp_var", provider.getFullNames());
                break;
            case REWORK:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vat_emp_officer", provider.getFullNames());
                break;
            case ADDITINAL_REQUEST:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;
            case SEND_TO_APPROVER:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;
            case DENIED_VETTING:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;

            case VETTING_CONCLUDED:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;

            case CERTIFICATE:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;

            default:
                logger.warn("Unrecognized phase type: {}", emailNotification.getNotificationType());
                return;
        }
        body = "<html><body style='font-family: Arial, sans-serif; 20px 20px 20px 20px;'>"
                + body.replace("<p><br /></p>", "</p><p>")
                + "</body></html>";
        if (emailNotification.getNotificationType().equals(NotificationType.EMAIL_TO_SUBJECT)) {
            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("s2026015");
//            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
//            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("S2019152");
            emailNotificationRecipients.add(destinationAddress);
            emailNotificationRecipients.add(destinationCCManager);

            //attached Forms
            FormAttachmentDTO formAttachmentDTO = new FormAttachmentDTO();
            formAttachmentDTO.setSourceEmailAddress(SOURCE_ADDRESS);
            formAttachmentDTO.setDestinationEmailAddress(destinationAddress);
            formAttachmentDTO.setCcManagerEmailAddress(destinationCCManager);
            formAttachmentDTO.setEmailSubject(emailNotification.getEmailSubject());
            formAttachmentDTO.setMessage(body.replace("var_employee", recipient.getFullNames()));
            if (mailService.sendEmail(formAttachmentDTO)) {
                logger.info("Email sent successfully to: {}", recipient.getFullNames());
            }
        } else if (emailNotification.getNotificationType().equals(NotificationType.CERTIFICATE)) {
            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("s2026015");
//            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
//            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("S2019152");
            emailNotificationRecipients.add(destinationAddress);
            emailNotificationRecipients.add(destinationCCManager);

            //attached Forms
            EmailReminderDTO formAttachmentDTO = new EmailReminderDTO();
            formAttachmentDTO.setSourceEmailAddress(SOURCE_ADDRESS);
            formAttachmentDTO.setDestinationEmailAddress(destinationAddress);
            formAttachmentDTO.setCcManagerEmailAddress(destinationCCManager);
            formAttachmentDTO.setEmailSubject(emailNotification.getEmailSubject());
            formAttachmentDTO.setMessage(body.replace("emp_var", recipient.getFullNames()));
            if (mailService.sendEmailCC(formAttachmentDTO)) {
                logger.info("Email sent successfully to: {}", recipient.getFullNames());
            }
        } else if (!(emailNotification.getNotificationType().equals(NotificationType.EMAIL_TO_SUBJECT) || emailNotification.getNotificationType().equals(NotificationType.CERTIFICATE))) {
            //emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
//            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
            emailNotificationRecipients.add(destinationAddress);
            if (mailService.send(emailNotificationRecipients,
                    subject,
                    body.replace("emp_var", recipient.getFullNames()))) {
                logger.info("Email sent successfully to: {}", recipient.getFullNames());
            }
        }

    }

    @Override
    public void sendNotificationApproval(NotificationType notificationType, Date date, Employee recipients, Employee provider, Vetting vetting) throws UnknownHostException, FileNotFoundException, IOException {
        String subject, body;
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        emailNotificationRecipients.clear();

        switch (emailNotification.getNotificationType()) {
            case VETTING_CONCLUDED:
            case DENIED_VETTING:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;
            default:
                logger.warn("Unrecognized phase type: {}", emailNotification.getNotificationType());
                return;
        }
        body = "<html><body style='font-family: Arial, sans-serif; 20px 20px 20px 20px;'>"
                + body.replace("<p><br /></p>", "</p><p>")
                + "</body></html>";
        if (emailNotification.getNotificationType().equals(NotificationType.VETTING_CONCLUDED) || emailNotification.getNotificationType().equals(NotificationType.DENIED_VETTING)) {
            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("s2026015");

//            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
//            String destinationCCManager = employeeInformationService.getEmployeeEmailAddress("S2019152");
            String destinationCCQA = employeeInformationService.getEmployeeEmailAddress("s2026064");
            String destinationCCOfficer = employeeInformationService.getEmployeeEmailAddress("s2028398");

//            String destinationCCQA = employeeInformationService.getEmployeeEmailAddress(vetting.getQaSid());
//            String destinationCCOfficer = employeeInformationService.getEmployeeEmailAddress(vetting.getOfficerSid());
            emailNotificationRecipients.add(destinationAddress);
            emailNotificationRecipients.add(destinationCCManager);
            emailNotificationRecipients.add(destinationCCQA);
            emailNotificationRecipients.add(destinationCCOfficer);

            //cc
            EmailApprovalDTO approvalDTO = new EmailApprovalDTO();
            approvalDTO.setSourceEmailAddress(SOURCE_ADDRESS);
            approvalDTO.setDestinationEmailAddress(destinationAddress);
            approvalDTO.setCcManagerEmailAddress(destinationCCManager);
            approvalDTO.setOfficeEmailAddress(destinationCCOfficer);
            approvalDTO.setQaEmailAddress(destinationCCQA);
            approvalDTO.setEmailSubject(emailNotification.getEmailSubject());
            approvalDTO.setMessage(body.replace("emp_var", recipients.getFullNames()));
            if (mailService.sendEmailCCUsers(approvalDTO)) {
                logger.info("Email sent successfully to: {}", recipients.getFullNames());
            }
        }
    }

    //reallocationMail
    @Override
    public void sendNotificationReallocate(NotificationType notificationType, Date date, Employee recipient, Employee provider, Employee oldEmployee) throws UnknownHostException, FileNotFoundException, IOException {
        String subject, body;
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        emailNotificationRecipients.clear();

        switch (emailNotification.getNotificationType()) {

            case RE_ALLOCATION:
                subject = emailNotification.getEmailSubject();
                body = emailNotification.getDescription();
                body = body.replace("vet_officer", provider.getFullNames());
                break;

            default:
                logger.warn("Unrecognized phase type: {}", emailNotification.getNotificationType());
                return;
        }
        body = "<html><body style='font-family: Arial, sans-serif; 20px 20px 20px 20px;'>"
                + body.replace("<p><br /></p>", "</p><p>")
                + "</body></html>";
        if (emailNotification.getNotificationType().equals(NotificationType.RE_ALLOCATION)) {
            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("s2028398");
            String destinationOldOfficer = employeeInformationService.getEmployeeEmailAddress("s2026015");
//            String destinationAddress = employeeInformationService.getEmployeeEmailAddress("S2024258");
//            String destinationOldOfficer = employeeInformationService.getEmployeeEmailAddress("S2019152");
            emailNotificationRecipients.add(destinationAddress);
            emailNotificationRecipients.add(destinationOldOfficer);

            //reallocate
            RerouteDTO rerouteDTO = new RerouteDTO();
            rerouteDTO.setSourceEmailAddress(SOURCE_ADDRESS);
            rerouteDTO.setDestinationEmailAddress(destinationAddress);
            rerouteDTO.setOldOfficer(destinationOldOfficer);
            rerouteDTO.setEmailSubject(emailNotification.getEmailSubject());
            rerouteDTO.setMessage(body.replace("emp_var", recipient.getFullNames()));
            if (mailService.sendEmailReroute(rerouteDTO)) {
                logger.info("Email sent successfully to: {}", recipient.getFullNames());
            }
        }
    }

}
