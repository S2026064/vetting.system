/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum ExpenditureDescription {

    BOND_RENT("Bond or rent"),
    LEVY("Levy"),
    TELEPHONE_BILL("Telephone bill"),
    GROCERY("Grocery"),
    TRANSPORT("Transport"),
    SCHOOL_FEES("School Fees"),
    CREDIT_CARD("Credit card"),
    LOANS("Loans"),
    UTILITY("Utility");

    private final String name;

    ExpenditureDescription(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
