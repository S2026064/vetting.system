/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.PreviousDisciplinaryHearing;

/**
 *
 * @author S2026987
 */
public interface PreviousDisciplinaryHearingServiceLocal {

    PreviousDisciplinaryHearing save(PreviousDisciplinaryHearing previousDisciplinaryHearing);

    PreviousDisciplinaryHearing findById(Long id);

    PreviousDisciplinaryHearing update(PreviousDisciplinaryHearing previousDisciplinaryHearing);

    PreviousDisciplinaryHearing deleteById(Long id);

    List<PreviousDisciplinaryHearing> listAll();

    boolean isExist(PreviousDisciplinaryHearing previousDisciplinaryHearing);

}
