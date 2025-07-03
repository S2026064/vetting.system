/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2026080
 */
public enum PersonType {
    EMPLOYEE("Employee"),
    RELATIVE("Relative"),
    SPOUSE("Spouse"),
    REFERENCE("Reference");

    private final String name;

    PersonType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
