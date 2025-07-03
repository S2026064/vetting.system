/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum DamageLevel {
    EXCEPTION("Exceptional-Grave Damage"),
    SIGNIFICANT("Significate-Serious Damage"),
    NO_MATERIAL("No Material Impact"),
    IMPACT("Severe Impact"),
    LIMITED("Limited Impact"),
    MODERATE("Moderate Impact"),
    NONE("None");

    private final String name;

    DamageLevel(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
