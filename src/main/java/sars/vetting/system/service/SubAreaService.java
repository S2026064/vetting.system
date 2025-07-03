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
import sars.vetting.system.domain.SubArea;
import sars.vetting.system.persistence.SubAreaRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SubAreaService implements SubAreaServiceLocal {

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Override
    public SubArea save(SubArea subArea) {
        return subAreaRepository.save(subArea);
    }

    @Override
    public SubArea findById(Long id) {
        return subAreaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SubArea update(SubArea subArea) {
        return subAreaRepository.save(subArea);
    }

    @Override
    public SubArea deleteById(Long id) {
        SubArea subArea = findById(id);

        if (subArea != null) {
            subAreaRepository.delete(subArea);
        }
        return subArea;
    }

    @Override
    public List<SubArea> listAll() {
        return subAreaRepository.findAll();
    }

    @Override
    public boolean isExist(SubArea subArea) {
        return subAreaRepository.findById(subArea.getId()) != null;
    }
    
    @Override
    public Page<SubArea> findAll(Pageable pageable) {
        return subAreaRepository.findAll(pageable);
    }

}
