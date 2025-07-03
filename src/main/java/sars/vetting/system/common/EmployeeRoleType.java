/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum EmployeeRoleType {
    ADMINISTRATOR("Administrator"),
    SUBJECT("Subject"),
    ANALYST("Analyst"),
    OFFICER("Officer"),
    QUALITY_ASSURARER("Quality Assurarer"),
    MANAGER("Manager"),
    APPROVER("Approver"),
    SECOND_LEVEL_APPROVER("Second Level Approver"),
    INITIATOR("Initiator");
    private final String name;

    EmployeeRoleType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
