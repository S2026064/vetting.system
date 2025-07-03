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
import sars.vetting.system.domain.IllegalDrug;
import sars.vetting.system.persistence.IllegalDrugRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class IllegalDrugService implements IllegalDrugServiceLocal {

    @Autowired
    private IllegalDrugRepository illegalDrugRepository;

    @Override
    public IllegalDrug save(IllegalDrug illegalDrug) {
        return illegalDrugRepository.save(illegalDrug);
    }

    @Override
    public IllegalDrug findById(Long id) {
        return illegalDrugRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public IllegalDrug update(IllegalDrug illegalDrug) {
        return illegalDrugRepository.save(illegalDrug);
    }

    @Override
    public IllegalDrug deleteById(Long id) {
        IllegalDrug illegalDrug = findById(id);

        if (illegalDrug != null) {
            illegalDrugRepository.delete(illegalDrug);
        }
        return illegalDrug;
    }

    @Override
    public List<IllegalDrug> listAll() {
        return illegalDrugRepository.findAll();
    }

    @Override
    public boolean isExist(IllegalDrug illegalDrug) {
        return illegalDrugRepository.findById(illegalDrug.getId()) != null;
    }

}
