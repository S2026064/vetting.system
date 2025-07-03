/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum RiskLevel {
    LOW("Low Risk"),
    HIGH("High Risk"),
    MODARATE("Moderate");

    private final String name;

    RiskLevel(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
