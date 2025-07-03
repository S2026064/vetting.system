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
@Table(name = "credit_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditRecord extends BaseEntity {

    @Column(name = "creditor")
    private String creditor;

    @Column(name = "amount")
    private String amount;

    @Column(name = "arrangement")
    private String arrangement;

    @Column(name = "comment")
    private String comment;

    @Column(name = "category")
    private String category;

    @Column(name = "credit_date")
    private String creditDate;

}
