package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "fin_docs_response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialDocsResponse extends BaseEntity {

    @Column(name = "cheque_account")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean chequeAccount;

    @Column(name = "savings_account")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean savingsAccount;

    @Column(name = "personal_loans")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean personalLoans;

    @Column(name = "study_loans")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean studyLoans;

    @Column(name = "other_accounts")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean otherAccounts;

    @Column(name = "income_exp_form")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean incomeExpenditureForm;

    @Column(name = "credit_card_account")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean creditCardAccount;

    @Column(name = "bond")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean bond;

    @Column(name = "car_loan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean carLoan;

    @Column(name = "other_loans")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean otherFinancialLoans;

    @Column(name = "latest_salary_advice")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean latestSalaryAdvice;

}
