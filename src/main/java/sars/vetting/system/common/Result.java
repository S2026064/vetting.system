/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum Result {
    ISSUED_WITH_VETTING_FORMS("Issued With Vetting Forms"),
    VETTING_FORMS_SUBMITTED("Vetting Forms Submitted"),
    IN_PROCESS_AT_SARS("In Process at SARS"),
    IN_PROCESS_AT_SSA("In Process at SSA"),
    CLEARANCE_ISSUED("Clearance Issued"),
    CLEARANCE_DENIED("Clearance Denied");
  

    private final String name;

    Result(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
