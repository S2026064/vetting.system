/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;

/**
 *
 * @author S2026987
 */
public interface JobRoleServiceLocal {

    JobRole save(JobRole jobRole);

    JobRole findById(Long id);

    JobRole update(JobRole jobRole);

    JobRole deleteById(Long id);

    List<JobRole> listAll();

    Page<JobRole> findAll(Pageable pageable);

    boolean isExist(JobRole jobRole);

    List<JobRole> findByJobRoleType(JobRoleType jobRoleType);

}
