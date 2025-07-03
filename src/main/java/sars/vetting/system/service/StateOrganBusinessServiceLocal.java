/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.StateOrganBusiness;

/**
 *
 * @author S2026987
 */
public interface StateOrganBusinessServiceLocal {

    StateOrganBusiness save(StateOrganBusiness StateOrganBusiness);

    StateOrganBusiness findById(Long id);

    StateOrganBusiness update(StateOrganBusiness stateOrganBusiness);

    StateOrganBusiness deleteById(Long id);

    List<StateOrganBusiness> listAll();

    boolean isExist(StateOrganBusiness stateOrganBusiness);

}
