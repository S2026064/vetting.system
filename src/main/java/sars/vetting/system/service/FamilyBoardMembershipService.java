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
import sars.vetting.system.domain.BoardMembership;
import sars.vetting.system.domain.FamilyBoardMembership;
import sars.vetting.system.persistence.BoardMembershipRepository;
import sars.vetting.system.persistence.FamilyBoardMembershipRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class FamilyBoardMembershipService implements FamilyBoardMembershipServiceLocal {

    @Autowired
    private FamilyBoardMembershipRepository familyBoardMembershipRepository;

    @Override
    public FamilyBoardMembership save(FamilyBoardMembership familyBoardMembership) {
        return familyBoardMembershipRepository.save(familyBoardMembership);
    }

    @Override
    public FamilyBoardMembership findById(Long id) {
        return familyBoardMembershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public FamilyBoardMembership update(FamilyBoardMembership familyBoardMembership) {
        return familyBoardMembershipRepository.save(familyBoardMembership);
    }

    @Override
    public FamilyBoardMembership deleteById(Long id) {
        FamilyBoardMembership familyBoardMembership = findById(id);

        if (familyBoardMembership != null) {
            familyBoardMembershipRepository.delete(familyBoardMembership);
        }
        return familyBoardMembership;
    }

    @Override
    public List<FamilyBoardMembership> listAll() {
        return familyBoardMembershipRepository.findAll();
    }

    @Override
    public boolean isExist(FamilyBoardMembership familyBoardMembership) {
        return familyBoardMembershipRepository.findById(familyBoardMembership.getId()) != null;
    }

}
