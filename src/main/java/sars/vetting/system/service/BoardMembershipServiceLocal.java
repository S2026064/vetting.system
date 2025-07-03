/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.BoardMembership;

/**
 *
 * @author S2026987
 */
public interface BoardMembershipServiceLocal {

    BoardMembership save(BoardMembership boardMembership);

    BoardMembership findById(Long id);

    BoardMembership update(BoardMembership boardMembership);

    BoardMembership deleteById(Long id);

    List<BoardMembership> listAll();

    boolean isExist(BoardMembership boardMembership);

}
