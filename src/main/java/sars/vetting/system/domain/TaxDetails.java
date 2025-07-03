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
import sars.vetting.system.common.YesNo;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "tax_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxDetails extends BaseEntity {

    @Column(name = "income_tax_number")
    private String incomeTaxNumber;

    @Column(name = "outstanding_return")
    private String outstandingReturn;
    
    @Column(name = "money_owed")
    private String moneyOwNedToSars;
    
    @Column(name = "last_assessment_date")
    private String lastAssesmentDate;

    @Column(name = "income_tax_registered")
    @Enumerated(EnumType.STRING)
    private YesNo taxRegistered;
    
    @Column(name = "status")
    private String status;

}
