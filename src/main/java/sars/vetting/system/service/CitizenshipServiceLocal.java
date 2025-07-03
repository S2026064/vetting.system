/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Citizenship;

/**
 *
 * @author S2026987
 */
public interface CitizenshipServiceLocal {

    Citizenship save(Citizenship citizenship);

    Citizenship findById(Long id);

    Citizenship update(Citizenship citizenship);

    Citizenship deleteById(Long id);

    List<Citizenship> listAll();

    boolean isExist(Citizenship citizenship);

}
