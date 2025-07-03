/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Area;

/**
 *
 * @author S2026987
 */
public interface AreaServiceLocal {

    Area save(Area area);

    Area findById(Long id);

    Area update(Area area);

    Area deleteById(Long id);

    List<Area> listAll();

    boolean isExist(Area area);

}
