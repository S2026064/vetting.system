/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.CreditRecord;
import sars.vetting.system.domain.CriminalRecord;
import sars.vetting.system.domain.DisciplinaryRecord;
import sars.vetting.system.domain.InternalInvestigationRecord;
import sars.vetting.system.domain.Qualification;
import sars.vetting.system.domain.Screening;

/**
 *
 * @author S2028398
 */
public interface DOPIServiceLocal {
    public Screening getScreeningByIdNumber(String IdNumber,String userSid);
    public List<Qualification>getQualificationByEmployeeIdnumber(String idNumber,String userSid);
    public List<CreditRecord>getCreditRecordByEmployeeIdnumber(String idNumber,String userSid);
    public List<CriminalRecord>getCriminalRecordByEmployeeIdnumber(String idNumber,String userSid);
    public List<InternalInvestigationRecord>getInternaInvestigationByEmployeeIdnumber(String idNumber,String userSid);
    public List<DisciplinaryRecord>getDisciplinaryByEmployeeIdnumber(String idNumber,String userSid);
}
