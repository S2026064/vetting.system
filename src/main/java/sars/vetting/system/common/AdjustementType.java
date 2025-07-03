/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum AdjustementType {
    PROGRAM("Program Scope and Impact"),
    SUPERVISION("Supervision or other controls");

    private final String name;

    AdjustementType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
