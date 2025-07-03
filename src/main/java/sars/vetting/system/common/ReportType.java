/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2026064
 */
public enum ReportType {

    MONITORING_RISK_REGISTER_REPORT("Monitoring Risk Register Report"),
    RECORD_CHECKS_REPORT("Record Checks Report "),
    VETTING_REGISTER_REPORT("Vetting Register Report");

    private final String name;

    ReportType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
