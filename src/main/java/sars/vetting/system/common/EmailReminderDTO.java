/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.common;

import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.domain.BaseEntity;

/**
 *
 * @author S2028398
 */
@Getter
@Setter
public class EmailReminderDTO extends BaseEntity {
    private String sourceEmailAddress;
    private String destinationEmailAddress;
    private String ccManagerEmailAddress;
    private String emailSubject;
    private String message;
}
