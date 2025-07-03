/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.MisconductAllegation;

/**
 *
 * @author S2026987
 */
public interface MisconductAllegationServiceLocal {

    MisconductAllegation save(MisconductAllegation misconductAllegation);

    MisconductAllegation findById(Long id);

    MisconductAllegation update(MisconductAllegation misconductAllegation);

    MisconductAllegation deleteById(Long id);

    List<MisconductAllegation> listAll();

    boolean isExist(MisconductAllegation misconductAllegation);

}
