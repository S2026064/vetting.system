/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.persistence.JobRoleRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class JobRoleService implements JobRoleServiceLocal {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    @Override
    public JobRole save(JobRole jobRole) {
        return jobRoleRepository.save(jobRole);
    }

    @Override
    public JobRole findById(Long id) {
        return jobRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public JobRole update(JobRole jobRole) {
        return jobRoleRepository.save(jobRole);
    }

    @Override
    public JobRole deleteById(Long id) {
        JobRole jobRole = findById(id);
        if (jobRole != null) {
            jobRoleRepository.delete(jobRole);
        }
        return jobRole;
    }

    @Override
    public List<JobRole> listAll() {
        return jobRoleRepository.findAll();
    }

    @Override
    public boolean isExist(JobRole jobRole) {
        return jobRoleRepository.findById(jobRole.getId()) != null;
    }

    @Override
    public Page<JobRole> findAll(Pageable pageable) {
        return jobRoleRepository.findAll(pageable);
    }

    @Override
    public List<JobRole> findByJobRoleType(JobRoleType jobRoleType) {
    return jobRoleRepository.findByJobRoleType(jobRoleType);
    }

}
