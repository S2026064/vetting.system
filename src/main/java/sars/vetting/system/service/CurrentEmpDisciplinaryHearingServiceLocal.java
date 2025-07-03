/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.CurrentEmpDisciplinaryHearing;

/**
 *
 * @author S2026987
 */
public interface CurrentEmpDisciplinaryHearingServiceLocal {

    CurrentEmpDisciplinaryHearing save(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing);

    CurrentEmpDisciplinaryHearing findById(Long id);

    CurrentEmpDisciplinaryHearing update(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing);

    CurrentEmpDisciplinaryHearing deleteById(Long id);

    List<CurrentEmpDisciplinaryHearing> listAll();

    boolean isExist(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing);

}
