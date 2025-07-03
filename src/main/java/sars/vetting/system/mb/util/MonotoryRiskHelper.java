/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb.util;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Getter
@Setter
public class MonotoryRiskHelper {

    private String employeeSid;
    private String employeeFullNames;
    private String employeeIdNumber;
    private String employeePositionTitle;
    private String businessUnit;
    private String region;
    private String Office;
    private String clearanceRequired;
    private String clearanceIssued;
    private String yearsRecommendation;
    private String riskIdentified;
    private String riskCategory;
    private String dateNextMonitoring;
    private Integer comment;
    private String personResponsibleRisk;
    private String commentSSA;
    private String monitoring;
    private Date riskIdentifiedDate;

}
