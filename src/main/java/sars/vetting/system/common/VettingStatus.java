/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum VettingStatus {

    FIND_ALL("All Vetting Records"),
    FORMS_ISSUED("Submitted to Subject for initiation"),
    FORMS_SUBMITTED("Submitted to Initiator"),
    SARS_PROCESSING("Allocated to Analyst"),
    ANALYST_POOL("Submitted to Analyst Pool"),
    SSA_PROCESSING("Allocated to Vetting Officer"),
    OFFICER_POOL("Submitted to Vetting Officer Pool"),
    CLEARANCE_ISSUED("Allocated to 1st Level Vetting Approver"),
    SENT_TO_QA("Allocated to Quality Assurance"),
    QA_POOL("Submitted to Quality Assurance Pool"),
    CLEARANCE_DENIED("Allocated to 2nd Level Vetting Approver"),
    APPROVER_POOL("Submitted to 1st Level Vetting Approver Pool"),
    FINAL_APPROVER_POOL("Submitted to 2nd Level Vetting Approver Pool"),
    REJECTED("Rejected"),
    ESCALATED("Escalated"),
    APPROVED("Approved"),
    VETTING_DENIED("Denied"),
    REWORK("Submitted to Subject for additional Infomation"),
    CERTIFICATE("Certificate Issued"),
    ADDITIONAL_DOCUMENT("Request for additional Infomation");

    private final String name;

    VettingStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
