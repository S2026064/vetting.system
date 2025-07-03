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
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;
import sars.vetting.system.persistence.AdjustmentResponsibilityRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class AdjustmentResponsibilityService implements AdjustmentResponsibilityServiceLocal {

    @Autowired
    private AdjustmentResponsibilityRepository adjustementAdjustmenetResponsibilityRepository;

    @Override
    public AdjustmentResponsibility save(AdjustmentResponsibility adjustmenetResponsibility) {
        return adjustementAdjustmenetResponsibilityRepository.save(adjustmenetResponsibility);
    }

    @Override
    public AdjustmentResponsibility findById(Long id) {
        return adjustementAdjustmenetResponsibilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public AdjustmentResponsibility update(AdjustmentResponsibility adjustmenetResponsibility) {
        return adjustementAdjustmenetResponsibilityRepository.save(adjustmenetResponsibility);
    }

    @Override
    public AdjustmentResponsibility deleteById(Long id) {
        AdjustmentResponsibility adjustmenetResponsibility = findById(id);
        if (adjustmenetResponsibility != null) {
            adjustementAdjustmenetResponsibilityRepository.delete(adjustmenetResponsibility);
        }
        return adjustmenetResponsibility;
    }

    @Override
    public List<AdjustmentResponsibility> listAll() {
        return adjustementAdjustmenetResponsibilityRepository.findAll();
    }

    @Override
    public boolean isExist(AdjustmentResponsibility adjustmenetResponsibility) {
        return adjustementAdjustmenetResponsibilityRepository.findById(adjustmenetResponsibility.getId()) != null;
    }

    @Override
    public List<AdjustmentResponsibility> findByAdjustmentCategory(AdjustmentCategory adjustementCategory) {
        return adjustementAdjustmenetResponsibilityRepository.findByAdjustmentCategory(adjustementCategory);
    }

    @Override
    public AdjustmentResponsibility findByDescription(String description) {
        return adjustementAdjustmenetResponsibilityRepository.findByDescription(description);
    }

}
