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
import sars.vetting.system.persistence.BoardMembershipRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class BoardMembershipService implements BoardMembershipServiceLocal {

    @Autowired
    private BoardMembershipRepository boardMembershipRepository;

    @Override
    public BoardMembership save(BoardMembership boardMembership) {
        return boardMembershipRepository.save(boardMembership);
    }

    @Override
    public BoardMembership findById(Long id) {
        return boardMembershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public BoardMembership update(BoardMembership boardMembership) {
        return boardMembershipRepository.save(boardMembership);
    }

    @Override
    public BoardMembership deleteById(Long id) {
        BoardMembership boardMembership = findById(id);

        if (boardMembership != null) {
            boardMembershipRepository.delete(boardMembership);
        }
        return boardMembership;
    }

    @Override
    public List<BoardMembership> listAll() {
        return boardMembershipRepository.findAll();
    }

    @Override
    public boolean isExist(BoardMembership boardMembership) {
        return boardMembershipRepository.findById(boardMembership.getId()) != null;
    }

}
