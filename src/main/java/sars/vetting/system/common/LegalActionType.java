/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum LegalActionType {
    CRIMINAL_OFFENCE("Criminal Offence"),
    SUMMONS("Summons"),
    INSOLVENCY("Insolvency");

    private final String name;

    LegalActionType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
