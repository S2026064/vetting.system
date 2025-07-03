/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.VisitedCountry;

/**
 *
 * @author S2026987
 */
public interface VisitedCountryServiceLocal {
    
    VisitedCountry save(VisitedCountry visitedCountry);

    VisitedCountry findById(Long id);

    VisitedCountry update(VisitedCountry visitedCountry);

    VisitedCountry deleteById(Long id);

    List<VisitedCountry> listAll();

    boolean isExist(VisitedCountry visitedCountry);
    
  
   
    
}
