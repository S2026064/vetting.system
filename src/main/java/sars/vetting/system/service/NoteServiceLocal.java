/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Note;

/**
 *
 * @author S2026064
 */
public interface NoteServiceLocal {

    Note save(Note note);

    Note findById(Long id);

    Note update(Note note);

    Note deleteById(Long id);

    List<Note> listAll();

    boolean isExist(Note note);
    
    Note findByRoleDescription(String roleDescription);
}
