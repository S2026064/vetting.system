/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.persistence.EmailNotificationRepository;

/**
 *
 * @author S2028398
 */
@Service
public class EmailNotificationService implements EmailNotificationServiceLocal {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Override
    public EmailNotification save(EmailNotification emailNotification) {
        return emailNotificationRepository.save(emailNotification);
    }

    @Override
    public EmailNotification findById(Long id) {
        return emailNotificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public EmailNotification update(EmailNotification emailNotification) {
        return emailNotificationRepository.save(emailNotification);
    }

    @Override
    public EmailNotification deleteById(Long id) {
        EmailNotification emailNotification = findById(id);

        if (emailNotification != null) {
            emailNotificationRepository.delete(emailNotification);
        }
        return emailNotification;
    }

    @Override
    public List<EmailNotification> listAll() {
        return emailNotificationRepository.findAll();
    }

    @Override
    public boolean isExist(EmailNotification emailNotification) {
        return emailNotificationRepository.findById(emailNotification.getId()) != null;
    }

    @Override
    public EmailNotification findByNotificationType(NotificationType notificationType) {
        return emailNotificationRepository.findByNotificationType(notificationType);
    }

    @Override
    public Page<EmailNotification> findAll(Pageable pageable) {
        return emailNotificationRepository.findAll(pageable);
    }

}
