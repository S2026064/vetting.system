/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "attachment_checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentChecklist extends BaseEntity {

    @Column(name = "copy_of_id")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean copyOfIdentityDocument;

    @Column(name = "copy_of_passport")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean copyOfPassport;

    @Column(name = "copy_of_marriage_cert")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean marriageCertificate;

    @Column(name = "copy_of_divorce_cert")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean copyOfDivorceCertificate;

    @Column(name = "academic_cert")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean copyOfAcademicCertification;

    @Column(name = "id_photo")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean idPhoto;

    @Column(name = "dopi_ensured")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean dopiEnsured;

   

}
