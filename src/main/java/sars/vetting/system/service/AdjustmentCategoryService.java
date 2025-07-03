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
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.persistence.AdjustmentCategoryRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class AdjustmentCategoryService implements AdjustmentCategoryServiceLocal {

    @Autowired
    private AdjustmentCategoryRepository adjustementCategoryRepository;

    @Override
    public AdjustmentCategory save(AdjustmentCategory adjustementCategory) {
        return adjustementCategoryRepository.save(adjustementCategory);
    }

    @Override
    public AdjustmentCategory findById(Long id) {
        return adjustementCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public AdjustmentCategory update(AdjustmentCategory adjustementCategory) {
        return adjustementCategoryRepository.save(adjustementCategory);
    }

    @Override
    public AdjustmentCategory deleteById(Long id) {
        AdjustmentCategory adjustementCategory = findById(id);
        if (adjustementCategory != null) {
            adjustementCategoryRepository.delete(adjustementCategory);
        }
        return adjustementCategory;
    }

    @Override
    public List<AdjustmentCategory> listAll() {
        return adjustementCategoryRepository.findAll();
    }

    @Override
    public boolean isExist(AdjustmentCategory adjustementCategory) {
        return adjustementCategoryRepository.findById(adjustementCategory.getId()) != null;
    }

    @Override
    public Page<AdjustmentCategory> findAll(Pageable pageable) {
        return adjustementCategoryRepository.findAll(pageable);
    }


}
