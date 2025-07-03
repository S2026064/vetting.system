package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "income")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Income extends BaseEntity {

    @Column(name = "employee_income")
    private Double employeeIncome;

    @Column(name = "spouse_income")
    private Double spouseIncome;

    @Column(name = "additional_income")
    private Double additionalIncome;
    
 }
