/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.ConvictedCriminalCharge;

/**
 *
 * @author S2026987
 */
public interface ConvictedCriminalChargeServiceLocal {

    ConvictedCriminalCharge save(ConvictedCriminalCharge convictedCriminalCharge);

    ConvictedCriminalCharge findById(Long id);

    ConvictedCriminalCharge update(ConvictedCriminalCharge convictedCriminalCharge);

    ConvictedCriminalCharge deleteById(Long id);

    List<ConvictedCriminalCharge> listAll();

    boolean isExist(ConvictedCriminalCharge convictedCriminalCharge);

}
