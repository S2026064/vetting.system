/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;

/**
 *
 * @author S2026987
 */
public interface AdjustmentResponsibilityServiceLocal {

    AdjustmentResponsibility save(AdjustmentResponsibility adjustmenetResponsibility);

    AdjustmentResponsibility findById(Long id);

    AdjustmentResponsibility update(AdjustmentResponsibility adjustmenetResponsibility);

    AdjustmentResponsibility deleteById(Long id);

    List<AdjustmentResponsibility> listAll();

    boolean isExist(AdjustmentResponsibility adjustmenetResponsibility);

    List<AdjustmentResponsibility> findByAdjustmentCategory(AdjustmentCategory adjustementCategory);

    AdjustmentResponsibility findByDescription(String description);

}
