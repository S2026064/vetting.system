/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum SpouseType {
    PRESENT("Present"),
    PREVIOUS("Previous");
  

    private final String name;

    SpouseType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
