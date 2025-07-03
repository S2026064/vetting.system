package sars.vetting.system.service;
import java.util.List;
import sars.vetting.system.domain.PublicOfficer;
import sars.vetting.system.domain.Employee;

/**
 *
 * @author S2028398
 */
public interface EmployeeInformationServiceLocal {
     public Employee getEmployeeUserBySid(String sid, String userSid);
     public String getEmployeeEmailAddress(String sid);
     
    public List<PublicOfficer> getPublicOfficer(String IdNumber, String userSid); 
}
