/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum CheckList {
    YES("Yes"),
    YES_BUT_INCOMPLETE("Yes but incomplete"),
    NO("No"),
    NOT_APPLICABLE("Not applicable");
    

    private final String name;

    CheckList(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
