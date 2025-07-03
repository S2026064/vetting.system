package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.vetting.system.common.ExpenditureDescription;
import sars.vetting.system.common.ExpenditureType;
import sars.vetting.system.common.IncomeType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "expenditure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expenditure extends BaseEntity {

    @Column(name = "expenditure_name")
    @Enumerated(EnumType.STRING)
    private ExpenditureDescription expenditureDescription;

    @Column(name = "expenditure_type")
    @Enumerated(EnumType.STRING)
    private ExpenditureType expenditureType;

    @Column(name = "description")
    private String description;

    @Column(name = "income_type")
    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;

    @Column(name = "amount")
    private Double amount;

}
