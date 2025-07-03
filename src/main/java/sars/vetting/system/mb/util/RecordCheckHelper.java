/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb.util;

import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.VettingStatus;

/**
 *
 * @author S2028398
 */
@Getter
@Setter
public class RecordCheckHelper {

    private String employeeSid;
    private String employeeFullNames;
    private String employeeIdNumber;
    private String employeePositionTitle;
    private String citizenship;
    private String personnelArea;
    private String businessUnit;
    private String region;
    private String Office;
    private String taxRefNumber;
    private VettingStatus vettingStatus;
    private String lastAssesment;
    private String returnOustanding;
    private String moneyOwedSars;

    //ibr
    private String director;
    private String outStandingReturn;
    private String outstandingDebt;
    private String enterpriseLink;
    private String enterpriseLinkPrevious;

    //screening
//    private List<Qualification> qualifications = new ArrayList<>();
//    private List<CreditRecord> creditRecords = new ArrayList<>();
//    private List<CriminalRecord> criminalRecords = new ArrayList<>();
//    private List<InternalInvestigationRecord> internalInvestigationRecords = new ArrayList<>();
    private Integer qualification;
    private Integer creditRecord;
    private Integer criminalRecord;
    private Integer internalInvestigationRecord; 

}
