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
public class VettingHelper {

    private String employeeSid;
    private String employeeFullNames;
    private String employeeIdNumber;
    private String employeePositionTitle;
    private String employeeGrade;
    private String businessUnit;
    private String project;
    private String priorityProject;
    private String region;
    private String Office;
    private Date z204Request;
    private Date z204RequestSubmited;
    private String responsibleOfficer;
    private Date dateAllocatedOfficial;
    private Date allocatedQA;
    private String recommendation;
    private Date dateToSSA;
    private String clearenceRequest;
    private String clearenceIssue;
    private Date dateRecieveFromSSA;
    private String clearenceNumberIssue;
    private Date clearenceIssueDate;
    private String clearenceExpiryDate;
    private String commentFromSSA;
    private String commentFromSars;
    private Date riskIdentified; 

}
