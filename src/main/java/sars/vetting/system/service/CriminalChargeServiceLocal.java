/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.CriminalCharge;

/**
 *
 * @author S2026987
 */
public interface CriminalChargeServiceLocal {

    CriminalCharge save(CriminalCharge criminalCharge);

    CriminalCharge findById(Long id);

    CriminalCharge update(CriminalCharge criminalCharge);

    CriminalCharge deleteById(Long id);

    List<CriminalCharge> listAll();

    boolean isExist(CriminalCharge criminalCharge);

}
