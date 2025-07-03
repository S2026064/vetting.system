/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.domain.EmailNotification;

/**
 *
 * @author S2028398
 */
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    EmailNotification findByNotificationType(NotificationType notificationType);
}
