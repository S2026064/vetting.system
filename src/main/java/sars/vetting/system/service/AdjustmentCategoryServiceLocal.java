/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sars.vetting.system.domain.AdjustmentCategory;

/**
 *
 * @author S2026987
 */
public interface AdjustmentCategoryServiceLocal {

    AdjustmentCategory save(AdjustmentCategory adjustementCategory);

    AdjustmentCategory findById(Long id);

    AdjustmentCategory update(AdjustmentCategory adjustementCategory);

    AdjustmentCategory deleteById(Long id);

    List<AdjustmentCategory> listAll();

    Page<AdjustmentCategory> findAll(Pageable pageable);

    boolean isExist(AdjustmentCategory adjustementCategory);

}
