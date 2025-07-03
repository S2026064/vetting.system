/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;

/**
 *
 * @author S2026987
 */
public interface ResponsibilityServiceLocal {

    Responsibility save(Responsibility responsibility);

    Responsibility findById(Long id);

    Responsibility update(Responsibility responsibility);

    Responsibility deleteById(Long id);

    List<Responsibility> listAll();

    boolean isExist(Responsibility responsibility);
    
    List<Responsibility> findByJobRole(JobRole jobRole);
    
    List<Responsibility> findByJobRoleDescriptionAndJobRoleType(String description,JobRoleType jobRoleType);
    
    Responsibility findByDescription(String description);

}
