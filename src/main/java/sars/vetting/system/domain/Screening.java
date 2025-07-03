package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "screening")
@Getter
@Setter
public class Screening extends BaseEntity {

//    @Column(name = "latest_update")
//    private String latestUpdateDate;
    @Column(name = "latest_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date latestUpdateDate;

    @Column(name = "screen_status")
    private String screenStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_details_id")
    private TaxDetails taxDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<InternalInvestigationRecord> internalInvestigationRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<CriminalRecord> criminalRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<CreditRecord> creditRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<DisciplinaryRecord> disciplinaryRecords = new ArrayList<>();

    public void addInternalInvestigationRecords(List<InternalInvestigationRecord> internalInvestigationRecords) {
        this.internalInvestigationRecords.addAll(internalInvestigationRecords);
    }

    public void addCriminalRecords(List<CriminalRecord> criminalRecords) {
        this.criminalRecords.addAll(criminalRecords);
    }

    public void addcreditRecords(List<CreditRecord> creditRecords) {
        this.creditRecords.addAll(creditRecords);
    }

    public void addDisciplinaryRecords(List<DisciplinaryRecord> disciplinaryRecords) {
        this.disciplinaryRecords.addAll(disciplinaryRecords);
    }

    public void addInternalInvestigationRecord(InternalInvestigationRecord internalInvestigationRecord) {
        this.internalInvestigationRecords.add(internalInvestigationRecord);
    }

    public void removeInternalInvestigationRecord(InternalInvestigationRecord internalInvestigationRecord) {
        this.internalInvestigationRecords.remove(internalInvestigationRecord);
    }

    public void addCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecords.add(criminalRecord);
    }

    public void removeCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecords.remove(criminalRecord);
    }

    public void addCreditRecord(CreditRecord creditRecord) {
        this.creditRecords.add(creditRecord);
    }

    public void removeCreditRecord(CreditRecord creditRecord) {
        this.creditRecords.remove(creditRecord);
    }

    public void addDisciplinaryRecord(DisciplinaryRecord disciplinaryRecord) {
        this.disciplinaryRecords.add(disciplinaryRecord);
    }

    public void removeDisciplinaryRecord(DisciplinaryRecord disciplinaryRecord) {
        this.disciplinaryRecords.remove(disciplinaryRecord);
    }

}
