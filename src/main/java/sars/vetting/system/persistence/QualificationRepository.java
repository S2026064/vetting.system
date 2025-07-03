/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Qualification;

/**
 *
 * @author S2026987
 */
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {

    @Query("SELECT e FROM Qualification e WHERE e.employee.id =:id")
    List<Qualification> deleteAllByEmployeeId(@Param("id") Long empId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Qualification e WHERE e.employee.id=:id")
    void deleteByEmployeeId(@Param("id") Long empId);

    @Modifying
    @Query("DELETE FROM Qualification e WHERE e.id=:id")
    void deleteQualification(@Param("id") Long id);
}
