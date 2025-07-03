/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;

/**
 *
 * @author S2026987
 */
@Repository
public interface AdjustmentResponsibilityRepository extends JpaRepository<AdjustmentResponsibility, Long> {

    List<AdjustmentResponsibility> findByAdjustmentCategory(AdjustmentCategory adjustementCategory);

    AdjustmentResponsibility findByDescription(String description);
}
