/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2026064
 */
public interface CommentServiceLocal {
    
     Comment save(Comment comment);

    Comment findById(Long id);

    Comment update(Comment comment);

    Comment deleteById(Long id);

    List<Comment> listAll();

    boolean isExist(Comment comment);
}
