/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum NotificationType {

    ADDITINAL_REQUEST("Additional Infomation Requested from Subject"),
    EMAIL_TO_SUBJECT("Send Email to Subject"),
    REMINDER_TO_SUBJECT("Send reminder to the subject"),
    REMINDER_TO_LINE_MANAGER("Send reminder to the Line Manager"),
    REMINDER_OF_EXPIRED_CLEARENCE("Send reminder of expired clearence"),
    ALLOCATE_VETTING("Vetting allocation"),
    SEND_TO_APPROVER("Send to first level approver"),
    DENIED_VETTING("Denied vetting "),
    VETTING_CONCLUDED("Vetting concluded"),
    REWORK("Rework"),
    CERTIFICATE("Feedback Certificate"),
    SUBMITTED("Vetting forms Submitted"),
    DOCUMENTS("Submission of outstanding documents"),
    ALLOCATED_OFFICER("Allocation of Vetting File"),
    RE_ALLOCATION("Re-allocation of Vetting File"),
    APPROVER_REWORK("Additional information requested for approval of vetting investigations"),
    ESCALATION_MAIL("Escalation email");

    private final String name;

    NotificationType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
