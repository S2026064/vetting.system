package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Qualification;
import sars.vetting.system.domain.Screening;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.DOPIServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.QualificationServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class ScreeningBean extends BaseBean<Vetting> {

    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private DOPIServiceLocal dOPIService;
    @Autowired
    private QualificationServiceLocal qualificationService;
    @Autowired
    private VettingServiceLocal vettingService;

    private VettingStatus vettingStatus;

    public void init(Vetting vettingRecord) {
        addEntity(vettingRecord);
        setVettingStatus(vettingRecord.getVettingStatus());
    }

    public void refreshRecord(Vetting vettingRecord) {
//        if (!vettingRecord.getEmployee().getQualifications().isEmpty()) {
//            qualificationService.(vettingRecord.getEmployee().getId());
//            for (Qualification qualification : vettingRecord.getEmployee().getQualifications()) {
//                List<Long> ids = new ArrayList<>();
//                ids.add(qualification.getId());
//                qualificationService.deleteByQualificationId(ids);
//                qualificationService.deleteQualification(qualification.getId());
//            }
//            qualificationService.deleteQualification(vettingRecord.getEmployee().getId());
//            qualificationService.deleteById(Long.MIN_VALUE);
//            employeeService.update(vettingRecord.getEmployee());
//        }
        if (StringUtils.isNotEmpty(vettingRecord.getEmployee().getIdentityNumber())) {
            //pull information from DOPI and Public officer from IBR data using id number
            vettingRecord.setScreening(dOPIService.getScreeningByIdNumber(vettingRecord.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
//            vettingRecord.getEmployee().addQualifications(dOPIService.getQualificationByEmployeeIdnumber(vettingRecord.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
        }
        employeeService.update(vettingRecord.getEmployee());
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        addEntity(vettingService.save(vettingRecord));
    }

    public Screening getScreening() {
        return getEntity().getScreening();
    }

    public VettingStatus getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(VettingStatus vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

}
