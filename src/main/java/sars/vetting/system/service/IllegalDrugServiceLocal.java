/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.IllegalDrug;

/**
 *
 * @author S2026987
 */
public interface IllegalDrugServiceLocal {

    IllegalDrug save(IllegalDrug illegalDrug);

    IllegalDrug findById(Long id);

    IllegalDrug update(IllegalDrug illegalDrug);

    IllegalDrug deleteById(Long id);

    List<IllegalDrug> listAll();

    boolean isExist(IllegalDrug illegalDrug);

}
