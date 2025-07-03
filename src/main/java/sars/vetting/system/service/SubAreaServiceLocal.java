/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sars.vetting.system.domain.SubArea;

/**
 *
 * @author S2026987
 */
public interface SubAreaServiceLocal {

    SubArea save(SubArea subArea);

    SubArea findById(Long id);

    SubArea update(SubArea subArea);

    SubArea deleteById(Long id);

    List<SubArea> listAll();

    Page<SubArea> findAll(Pageable pageable);

    boolean isExist(SubArea subArea);

}
