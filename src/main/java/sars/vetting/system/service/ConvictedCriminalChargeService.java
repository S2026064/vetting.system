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
import sars.vetting.system.domain.ConvictedCriminalCharge;
import sars.vetting.system.persistence.ConvictedCriminalChargeRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ConvictedCriminalChargeService implements ConvictedCriminalChargeServiceLocal {

    @Autowired
    private ConvictedCriminalChargeRepository convictedCriminalChargeRepository;

    @Override
    public ConvictedCriminalCharge save(ConvictedCriminalCharge convictedCriminalCharge) {
        return convictedCriminalChargeRepository.save(convictedCriminalCharge);
    }

    @Override
    public ConvictedCriminalCharge findById(Long id) {
        return convictedCriminalChargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public ConvictedCriminalCharge update(ConvictedCriminalCharge convictedCriminalCharge) {
        return convictedCriminalChargeRepository.save(convictedCriminalCharge);
    }

    @Override
    public ConvictedCriminalCharge deleteById(Long id) {
        ConvictedCriminalCharge convictedCriminalCharge = findById(id);

        if (convictedCriminalCharge != null) {
            convictedCriminalChargeRepository.delete(convictedCriminalCharge);
        }
        return convictedCriminalCharge;
    }

    @Override
    public List<ConvictedCriminalCharge> listAll() {
        return convictedCriminalChargeRepository.findAll();
    }

    @Override
    public boolean isExist(ConvictedCriminalCharge convictedCriminalCharge) {
        return convictedCriminalChargeRepository.findById(convictedCriminalCharge.getId()) != null;
    }

}
