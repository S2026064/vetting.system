package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "review_vetting_sett")
@Getter
@Setter
public class ReviewVettingSetting extends BaseEntity {

    @Column(name = "analist_review_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean analistReview;
    @Column(name = "officer_review_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean officerReview;
    @Column(name = "qa_review_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean qaReview;
    @Column(name = "manager_review_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean managerReview;

    public ReviewVettingSetting() {
        this.analistReview = Boolean.FALSE;
        this.officerReview = Boolean.FALSE;
        this.qaReview = Boolean.FALSE;
        this.managerReview = Boolean.FALSE;
    }

    public boolean isReviewVetting() {
        return this.analistReview || this.officerReview || this.qaReview || this.managerReview;
    }
}
