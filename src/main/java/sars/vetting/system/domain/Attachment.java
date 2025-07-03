/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.AttachmentType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "attachment")
@Getter
@Setter
public class Attachment extends BaseEntity {

    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;
    @Column(name = "uploade_by")
    private String uploadedBy;
    @Column(name = "attachment_type")
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "vetting_id", nullable = true)
    private Vetting vetting;

}
