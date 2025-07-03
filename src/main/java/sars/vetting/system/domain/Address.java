/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.AddressType;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "address")
@Getter
@Setter
public class Address extends BaseEntity {

    @Column(name = "add_line1")
    private String line1;

    @Column(name = "add_line2")
    private String line2;

    @Column(name = "add_line3")
    private String line3;

    @Column(name = "area")
    private String area;

    @Column(name = "code")
    private String code;
    

    @Column(name = "add_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "countryName")
    private String countryName;
    
     @Column(name = "date_occupied")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOccupied;

}
