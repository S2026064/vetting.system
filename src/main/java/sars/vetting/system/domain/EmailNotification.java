/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.NotificationType;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "email_notification")
@Getter
@Setter
public class EmailNotification extends BaseEntity {

    @Column(name = "subject", length = 1000)
    private String emailSubject;

    @Column(name = "line_1", length = 1000)
    private String line1;

    @Column(name = "line_2", length = 1000)
    private String line2;

    @Column(name = "line_3", length = 1000)
    private String line3;

    @Column(name = "line_4", length = 1000)
    private String line4;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
