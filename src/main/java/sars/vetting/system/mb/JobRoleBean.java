package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.DamageLevel;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;
import sars.vetting.system.service.ResponsibilityServiceLocal;
import sars.vetting.system.service.JobRoleServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class JobRoleBean extends BaseBean<JobRole> {

    @Autowired
    private JobRoleServiceLocal jobRoleService;
    @Autowired
    private ResponsibilityServiceLocal responsibilityService;

    private List<JobRoleType> jobRoleTypes;
    private List<DamageLevel> damageLevels;

    private Slice slices;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        jobRoleTypes = new ArrayList<>();
        jobRoleTypes.addAll(Arrays.asList(JobRoleType.values()));
        damageLevels = new ArrayList<>();
        damageLevels.addAll(Arrays.asList(DamageLevel.values()));
//        Pageable pageable = PageRequest.of(0, 10);
//        slices = jobRoleService.findAll(pageable);
//        addCollections(slices.toList());
        addCollections(jobRoleService.listAll());
    }

    public void addOrUpdate(JobRole jobRole) {
        reset().setAdd(true);

        if (jobRole != null) {
            jobRole.setUpdatedBy(getActiveUser().getSid());
            jobRole.setUpdatedDate(new Date());
        } else {
            jobRole = new JobRole();
            jobRole.setCreatedBy(getActiveUser().getSid());
            jobRole.setCreatedDate(new Date());

            addCollection(jobRole);
        }
        addEntity(jobRole);

    }

    public void addOrUpdateResponsibility() {
        Responsibility responsibility = new Responsibility();
        if (responsibility.getId() == null) {
            responsibility.setCreatedBy(getActiveUser().getSid());
            responsibility.setCreatedDate(new Date());
            getEntity().addResponsibilities(responsibility);
        } else {
            responsibility.setUpdatedBy(getActiveUser().getSid());
            responsibility.setUpdatedDate(new Date());
        }
    }

    public void removeResponsibility(Responsibility responsibility) {
        if (responsibility.getId() != null) {
            getEntity().removeResponsibilities(responsibility);
            addEntity(jobRoleService.update(getEntity()));
            responsibilityService.deleteById(responsibility.getId());
        } else {
            getEntity().removeResponsibilities(responsibility);
        }
        addInformationMessage("Responsibility successfully removed");
    }

    public void save(JobRole jobRole) {
        if (jobRole.getId() != null) {
            jobRoleService.update(jobRole);
            addInformationMessage("Job Role was successfully updated.");
        } else {
            jobRoleService.save(jobRole);
            addInformationMessage("Job Role was successfully created.");
        }
        reset().setList(true);
    }

    public void cancel(JobRole jobRole) {
        if (jobRole.getId() == null && getJobRoles().contains(jobRole)) {
            remove(jobRole);
        }
        reset().setList(true);
    }

    public void delete(JobRole potentialDamage) {
        jobRoleService.deleteById(potentialDamage.getId());
        remove(potentialDamage);
        addInformationMessage("Jobe Role was successfully deleted");
        reset().setList(true);
    }

    public Integer getPageNumber() {
        return slices.getNumber() + 1;
    }

    public boolean isNextPage() {
        return slices.hasNext();
    }

    public boolean isPreviousPage() {
        return slices.hasPrevious();
    }

    public void firstPageDocuments() {
        slices = jobRoleService.findAll(slices.previousOrFirstPageable());
        addCollections(slices.toList());
    }

    public void lastPageDocuments() {
        slices = jobRoleService.findAll(slices.nextOrLastPageable());
        addCollections(slices.toList());
    }

    public void nextDocuments() {
        if (slices.hasNext()) {
            slices = jobRoleService.findAll(slices.nextPageable());
            addCollections(slices.toList());
        }
    }

    public void previousDocuments() {
        if (slices.hasPrevious()) {
            slices = jobRoleService.findAll(slices.previousPageable());
            addCollections(slices.toList());
        }
    }

    public List<JobRole> getJobRoles() {
        return this.getCollections();
    }

    public List<JobRoleType> getJobRoleTypes() {
        return jobRoleTypes;
    }

    public void setJobRoleTypes(List<JobRoleType> jobRoleTypes) {
        this.jobRoleTypes = jobRoleTypes;
    }

    public List<DamageLevel> getDamageLevels() {
        return damageLevels;
    }

    public void setDamageLevels(List<DamageLevel> damageLevels) {
        this.damageLevels = damageLevels;
    }

}
