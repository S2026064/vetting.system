/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum JobRoleType {
    NATIONAL("Natural Security Requirements- Step 1 Job Role"),
    SUSTAINABILITY("Sustainability Requirements- Step 2 Job Role");

    private final String name;

    JobRoleType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
