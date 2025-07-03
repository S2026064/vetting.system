/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Expenditure;

/**
 *
 * @author S2026987
 */
public interface ExpenditureServiceLocal {

    Expenditure save(Expenditure expenditure);

    Expenditure findById(Long id);

    Expenditure update(Expenditure expenditure);

    Expenditure deleteById(Long id);

    List<Expenditure> listAll();

    boolean isExist(Expenditure expenditure);

}
