package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "income_expenditure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeExpenditure extends BaseEntity {

    @Column(name = "spouse_total_income")
    private Double spouseTotalIncome;

    @Column(name = "employee_total_income")
    private Double employeeTotalIncome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "income_id")
    private Income income;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "income_expenditure_id")
    private List<Expenditure> expenditures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "additinal_income_id")
    private List<AdditionalIncome> additionalIncomes = new ArrayList<>();

    public void addExpenditure(Expenditure expenditure) {
        this.expenditures.add(expenditure);
    }

    public void removeExpenditure(Expenditure expenditure) {
        this.expenditures.remove(expenditure);
    }
    
     public void addAdditinalIncome(AdditionalIncome additionalIncome) {
        this.additionalIncomes.add(additionalIncome);
    }

    public void removeAdditionalIncome(AdditionalIncome additionalIncome) {
        this.additionalIncomes.remove(additionalIncome);
    }

}
