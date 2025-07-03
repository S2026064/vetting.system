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
import sars.vetting.system.domain.Citizenship;
import sars.vetting.system.domain.Reference;
import sars.vetting.system.persistence.CitizenShipRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class CitizenshipService implements CitizenshipServiceLocal {

    @Autowired
    private CitizenShipRepository citizenShipRepository;

    @Override
    public Citizenship save(Citizenship citizenship) {
        return citizenShipRepository.save(citizenship);
    }

    @Override
    public Citizenship findById(Long id) {
        return citizenShipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Citizenship update(Citizenship citizenship) {
        return citizenShipRepository.save(citizenship);
    }

    @Override
    public Citizenship deleteById(Long id) {
        Citizenship citizenship = findById(id);

        if (citizenship != null) {
            citizenShipRepository.delete(citizenship);
        }
        return citizenship;
    }

    @Override
    public List<Citizenship> listAll() {
        return citizenShipRepository.findAll();
    }

    @Override
    public boolean isExist(Citizenship citizenship) {
        return citizenShipRepository.findById(citizenship.getId()) != null;
    }

}
