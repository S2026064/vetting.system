/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public enum AttachmentType {

    ID("ID Copy"),
    Z204_FORM("Z204 Form"),
    INCOME_EXPENDITURE("Income Expenditure"),
    PASSPORT("Passport"),
    MARRIAGE_CERTIFICATE("Marriage  Certificate"),
    DIVORCE_CERTIFICATE("Divorce  Certificate"),
    ACADEMIC_CERTIFICATE("Academic Certificate "),
    ID_PHOTO("Id  Photo"),
    SALARY_ADVICE("Salary Advice"),
    OTHER_FINANCIAL_LOAN("Other Financial Loan"),
    CAR_LOAN("Car Loan"),
    BOND("Bond"),
    CREDIT_CARD_ACCOUNT("Credit Card Account"),
    OTHER_ACCOUNTS("Other Accounts"),
    STUDY_LOAN("Study LOAN"),
    PERSONAL_LOAN("Personal Loan"),
    SAVINGS_ACCOUNT("Savings Account"),
    CHEQUE_ACCOUNT("Cheque Account"),
    SCREENING_DECLARATION("Screening Declaration"),
    PHOTO("Photo attached to cover page of Z-204"),
    STATEMENT("Bank Statements"),
    CONSENT("Service provider screening consent form"),
    OTHER("Email Communication etc."),
    OTHER_DOC("Additional supporting Documents"),
    QUALIFICATION("Copies of Qualifications"),
    VETTING_FORM("Vetting Consent Form"),
    DOTS_FORM("Dots Consent Form"),
    DOPI("DOPI"),
    WORK_PERMIT("Working Permit"),
    REFUGEE_STATUS("Refugee Status"),
    RESIDANCE_STATUS("Permanent Residance Status");
//    DESCIPLINARY("Desciplinary Report");

    private final String name;

    AttachmentType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
