/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;

/**
 *
 * @author S2026987
 */
@Repository
public interface ResponsibilityRepository extends JpaRepository<Responsibility, Long> {

    List<Responsibility> findByJobRole(JobRole jobRole);

    Responsibility findByDescription(String description);

    @Query("SELECT e FROM Responsibility e WHERE e.jobRole.description=:description AND e.jobRole.jobRoleType=:jobRoleType")
    List<Responsibility> findByJobRoleDescriptionAndJobRoleType(@Param("description") String description,@Param("jobRoleType")JobRoleType jobRoleType);
}
