package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.common.VettingType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "vetting")
@Getter
@Setter
public class Vetting extends BaseEntity {

    @Column(name = "archive_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date archiveDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "vett_processing_officer_id", nullable = true)
    private Employee vettingProcessingOfficer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id", nullable = true)
    private Screening screening;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "income_expenditure_id", nullable = true)
    private IncomeExpenditure incomeExpenditure;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private ScreeningDeclaration screeningDeclaration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_clearance_id")
    private GradeClearance gradeClearance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VettingStatus vettingStatus;

    @Column(name = "clearence_level")
    @Enumerated(EnumType.STRING)
    private ClearanceLevel clearanceLevel;

    @Column(name = "vetting_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VettingType vettingType;

    @Column(name = "subject_sid")
    private String subjectSid;

    @Column(name = "first_approver_sid")
    private String firstApproverSid;

    @Column(name = "analyst_sid")
    private String analystSid;

    @Column(name = "officer_sid")
    private String officerSid;

    @Column(name = "aq_sid")
    private String qaSid;

    @Column(name = "initiator_sid")
    private String initiatorSid;

    @Column(name = "officer")
    private String vettingOfficer;

    @Column(name = "z204_submitted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date z204SubmittedDate;

    @Column(name = "date_sent_qa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSentQa;

    @Column(name = "date_sent_officer")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSentOfficer;

    @Column(name = "tat")
    private Integer tat;

    @Lob
    @Column(name = "project")
    private String projectSummary;

    @Lob
    @Column(name = "project_priority")
    private String projectPrioritySummary;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "vetting")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "vetting")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "vetting")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<VettingFileAttachment> vettingFileAttachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vetting", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vetting", fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList();

    public void addNote(Note note) {
        note.setVetting(this);
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
        note.setVetting(null);
    }

    public void addComment(Comment comment) {
        comment.setVetting(this);
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setVetting(null);
    }

    public void addAttachment(Attachment attachment) {
        attachment.setVetting(this);
        this.attachments.add(attachment);
    }

    public void addAttachments(List<Attachment> attachments) {
        this.attachments.clear();
        for (Attachment attachment : attachments) {
            attachment.setVetting(this);
            this.attachments.add(attachment);
        }
    }

    public void removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setVetting(null);
    }

    public void addVettingFileAttachments(List<VettingFileAttachment> attachments) {
        this.attachments.clear();
        for (VettingFileAttachment attachment : attachments) {
            attachment.setVetting(this);
            this.vettingFileAttachments.add(attachment);
        }
    }

    public void removeVettingFileAttachment(VettingFileAttachment attachment) {
        this.vettingFileAttachments.remove(attachment);
        attachment.setVetting(null);
    }

    public void addCerificate(Certificate certificate) {
        certificate.setVetting(this);
        this.certificates.add(certificate);
    }

    public void addCerificats(List<Certificate> certificates) {
        this.certificates.clear();
        for (Certificate certificate : certificates) {
            certificate.setVetting(this);
            this.certificates.add(certificate);
        }
    }

    public void removeCertificate(Certificate attachment) {
        this.certificates.remove(attachment);
        attachment.setVetting(null);
    }
}
