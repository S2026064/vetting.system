/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.LegalAction;

/**
 *
 * @author S2026987
 */
public interface LegalActionServiceLocal {

    LegalAction save(LegalAction legalAction);

    LegalAction findById(Long id);

    LegalAction update(LegalAction legalAction);

    LegalAction deleteById(Long id);

    List<LegalAction> listAll();

   

}
