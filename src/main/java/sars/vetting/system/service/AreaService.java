/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Area;
import sars.vetting.system.persistence.AreaRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class AreaService implements AreaServiceLocal {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public Area save(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public Area findById(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Area update(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public Area deleteById(Long id) {
        Area area = findById(id);
        if (area != null) {
            areaRepository.delete(area);
        }
        return area;
    }

    @Override
    public List<Area> listAll() {
        return areaRepository.findAll();
    }

    @Override
    public boolean isExist(Area area) {
        return areaRepository.findById(area.getId()) != null;
    }

}
