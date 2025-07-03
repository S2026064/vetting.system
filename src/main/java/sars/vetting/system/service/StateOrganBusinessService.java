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
import sars.vetting.system.domain.StateOrganBusiness;
import sars.vetting.system.persistence.CitizenShipRepository;
import sars.vetting.system.persistence.StateOrganBusinessRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class StateOrganBusinessService implements StateOrganBusinessServiceLocal {

    @Autowired
    private StateOrganBusinessRepository organBusinessRepository;

    @Override
    public StateOrganBusiness save(StateOrganBusiness stateOrganBusiness) {
        return organBusinessRepository.save(stateOrganBusiness);
    }

    @Override
    public StateOrganBusiness findById(Long id) {
        return organBusinessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public StateOrganBusiness update(StateOrganBusiness stateOrganBusiness) {
        return organBusinessRepository.save(stateOrganBusiness);
    }

    @Override
    public StateOrganBusiness deleteById(Long id) {
        StateOrganBusiness stateOrganBusiness = findById(id);

        if (stateOrganBusiness != null) {
            organBusinessRepository.delete(stateOrganBusiness);
        }
        return stateOrganBusiness;
    }

    @Override
    public List<StateOrganBusiness> listAll() {
        return organBusinessRepository.findAll();
    }

    @Override
    public boolean isExist(StateOrganBusiness stateOrganBusiness) {
        return organBusinessRepository.findById(stateOrganBusiness.getId()) != null;
    }

}
