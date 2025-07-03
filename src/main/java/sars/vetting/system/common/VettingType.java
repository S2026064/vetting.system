/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2026064
 */
public enum VettingType {

    EMPLOYEE("Initiate vetting for Employee"),
    PROVIDER("Initiate vetting for Service Provider");

    private final String name;

    VettingType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
