/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.common;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Getter
@Setter
public class Router implements Serializable {

    private boolean administrator;
    private boolean vettingInitiation;
    private boolean vettingCompletion;
    private boolean vettingReview;
    private boolean managerReview;
    private boolean vettingApproval;
    private boolean vettingFiles;
    private boolean superAdmin;
    private boolean reports;

    public Router reset() {
        administrator = Boolean.FALSE;
        vettingInitiation = Boolean.FALSE;
        vettingCompletion = Boolean.FALSE;
        vettingReview = Boolean.FALSE;
        vettingApproval = Boolean.FALSE;
        managerReview = Boolean.FALSE;
        vettingFiles = Boolean.FALSE;
        reports = Boolean.FALSE;
        superAdmin = Boolean.FALSE;
        return this;
    }
}
