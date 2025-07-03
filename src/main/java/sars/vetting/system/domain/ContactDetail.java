/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "contact_detail")
@Getter
@Setter
public class ContactDetail extends BaseEntity {

    @Column(name = "cell_phone_number")
    private String cellPhoneNumber;

    @Column(name = "tell_phone_number")
    private String telephoneNumber;
    
    @Column(name = "business_phone_number")
    private String businessNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "fax_number")
    private String faxNumber;

    public ContactDetail(String cellPhoneNumber, String telephoneNumber, String emailAddress, String faxNumber,String businessNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.faxNumber = faxNumber;
        this.businessNumber = businessNumber;
    }

    public ContactDetail() {
    }
    
    
}
