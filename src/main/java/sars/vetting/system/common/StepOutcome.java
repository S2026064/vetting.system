/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum StepOutcome {
    NOT_APPLICABLE("If not applicable, proceed to step 2"),
    MEDIUM_LOW("Medium to Low Risk. Please Proceed to Step 2"),
    HIGH_RISK("Automatic High Risk. Please proceed to Step 4");

    private final String name;

    StepOutcome(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
