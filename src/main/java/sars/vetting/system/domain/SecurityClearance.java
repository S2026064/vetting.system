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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.vetting.system.common.ClearanceLevel;

/**
 *
 * @author S2026064
 */
@Entity
@Table(name = "security_clearance_or_denied")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityClearance extends BaseEntity{
    
    @Column(name = "date_issued_or_denied")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIssued;
    
     @Column(name = "institution")
    private String institution;
     
     
    @Column(name = "clearance_level")
    @Enumerated(EnumType.STRING)
    private ClearanceLevel clearanceLevel;
    
}
