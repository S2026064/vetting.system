/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "public_officer")
@Getter
@Setter
public class PublicOfficer extends BaseEntity {

    @Column(name = "company_status")
    private String companyStatus;
    @Column(name = "public_officer_at")
    private String publicOfficerAt;
    @Column(name = "linked_ref_nbr")
    private String linkedRefNBR;
    @Column(name = "linked_account_type")
    private String linkedAccountType;
    @Column(name = "linked_it_turnover")
    private String linkedItTurnover;
    @Column(name = "linked_it_ass_yr")
    private String linkedItAssYr;
    @Column(name = "linked_tot_outst_debt")
    private String linkedTotOutstDebt;
    @Column(name = "linked_tot_outst_returns")
    private String linkedTotOutstReturns;
    @Column(name = "po_name")
    private String poName;
    @Column(name = "po_id_number")
    private String poIdNumber;
    @Column(name = "po_tax_nbr")
    private String poTaxNbr;
    @Column(name = "po_phone_nbr")
    private String poPhoneNbr;
    @Column(name = "po_cell_nbr")
    private String poCellNbr;
    @Column(name = "import_date")
    private String importDate;
    
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public PublicOfficer(String companyStatus, String publicOfficerAt, String linkedRefNBR, String linkedAccountType, String linkedItTurnover, String linkedItAssYr, String linkedTotOutstDebt, String linkedTotOutstReturns, String poName, String poIdNumber, String poTaxNbr, String poPhoneNbr, String poCellNbr, String importDate) {
        this.companyStatus = companyStatus;
        this.publicOfficerAt = publicOfficerAt;
        this.linkedRefNBR = linkedRefNBR;
        this.linkedAccountType = linkedAccountType;
        this.linkedItTurnover = linkedItTurnover;
        this.linkedItAssYr = linkedItAssYr;
        this.linkedTotOutstDebt = linkedTotOutstDebt;
        this.linkedTotOutstReturns = linkedTotOutstReturns;
        this.poName = poName;
        this.poIdNumber = poIdNumber;
        this.poTaxNbr = poTaxNbr;
        this.poPhoneNbr = poPhoneNbr;
        this.poCellNbr = poCellNbr;
        this.importDate = importDate;
    }

    public PublicOfficer() {
    }

}
