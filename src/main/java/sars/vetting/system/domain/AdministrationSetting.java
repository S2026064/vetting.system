/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "admin_sett")
@Getter
@Setter
public class AdministrationSetting extends BaseEntity {

    @Column(name = "administrator")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean administrator;
    @Column(name = "emp_role")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean employeeRole;
    @Column(name = "users")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean users;
    @Column(name = "escalation")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean escalation;
    @Column(name = "notification")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean notification;
    @Column(name = "job_role")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean jobRole;
    @Column(name = "sub_area")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean subArea;
    @Column(name = "adjust_category")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean adjustmentCategory;
    @Column(name = "reroute")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reroute;
    @Column(name = "vetting_files")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean vettingFiles;
    @Column(name = "super_admin")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean superAdministrator;
    @Column(name = "upload_ssa_sars")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean uploadSsaSars;

    public AdministrationSetting() {
        this.administrator = Boolean.FALSE;
        this.employeeRole = Boolean.FALSE;
        this.users = Boolean.FALSE;
        this.escalation = Boolean.FALSE;
        this.notification = Boolean.FALSE;
        this.jobRole = Boolean.FALSE;
        this.subArea = Boolean.FALSE;
        this.adjustmentCategory = Boolean.FALSE;
        this.reroute = Boolean.FALSE;
        this.vettingFiles = Boolean.FALSE;
        this.superAdministrator = Boolean.FALSE;
        this.uploadSsaSars = Boolean.FALSE;
    }

    public boolean isAdministrator() {
        return this.administrator || this.uploadSsaSars || this.employeeRole || this.users || this.escalation || this.notification || this.jobRole || this.subArea || this.adjustmentCategory || this.reroute || this.vettingFiles;
    }
}
