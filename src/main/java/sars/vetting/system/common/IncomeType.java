/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum IncomeType {
    EMPLOYEE("Employee"),
    SPOUSE("Spouse");

    private final String name;

    IncomeType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
