/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;
import sars.vetting.system.persistence.ResponsibilityRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ResponsibilityService implements ResponsibilityServiceLocal {

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Override
    public Responsibility save(Responsibility responsibility) {
        return responsibilityRepository.save(responsibility);
    }

    @Override
    public Responsibility findById(Long id) {
        return responsibilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Responsibility update(Responsibility responsibility) {
        return responsibilityRepository.save(responsibility);
    }

    @Override
    public Responsibility deleteById(Long id) {
        Responsibility responsibility = findById(id);
        if (responsibility != null) {
            responsibilityRepository.delete(responsibility);
        }
        return responsibility;
    }

    @Override
    public List<Responsibility> listAll() {
        return responsibilityRepository.findAll();
    }

    @Override
    public boolean isExist(Responsibility responsibility) {
        return responsibilityRepository.findById(responsibility.getId()) != null;
    }

    @Override
    public List<Responsibility> findByJobRole(JobRole jobRole) {
        return responsibilityRepository.findByJobRole(jobRole);
    }

    @Override
    public List<Responsibility> findByJobRoleDescriptionAndJobRoleType(String description,JobRoleType jobRoleType) {
        return responsibilityRepository.findByJobRoleDescriptionAndJobRoleType(description,jobRoleType);
    }

    @Override
    public Responsibility findByDescription(String description) {
        return responsibilityRepository.findByDescription(description);
    }

}
