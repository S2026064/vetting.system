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
import sars.vetting.system.domain.HealthInfo;
import sars.vetting.system.persistence.HealthInfoRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class HealthInfoService implements HealthInfoServiceLocal{
    
    @Autowired
    private HealthInfoRepository healthInfoRepository;

    @Override
    public HealthInfo save(HealthInfo healthInfo) {
        return healthInfoRepository.save(healthInfo);
    }

    @Override
    public HealthInfo findById(Long id) {
        return healthInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public HealthInfo update(HealthInfo healthInfo) {
        return healthInfoRepository.save(healthInfo);
    }

    @Override
    public HealthInfo deleteById(Long id) {
        HealthInfo healthInfo = findById(id);

        if (healthInfo != null) {
            healthInfoRepository.delete(healthInfo);
        }
        return healthInfo;
    }

    @Override
    public List<HealthInfo> listAll() {
        return healthInfoRepository.findAll();
    }

    @Override
    public boolean isExist(HealthInfo healthInfo) {
        return healthInfoRepository.findById(healthInfo.getId()) != null;
    }

  

  
    
}
