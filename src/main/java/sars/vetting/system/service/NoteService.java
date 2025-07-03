/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Note;
import sars.vetting.system.persistence.CommentRepository;
import sars.vetting.system.persistence.NoteRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class NoteService implements NoteServiceLocal {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Note update(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note deleteById(Long id) {
        Note note = findById(id);
        if (note != null) {
            noteRepository.delete(note);
        }
        return note;
    }

    @Override
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    @Override
    public boolean isExist(Note note) {
        return noteRepository.findById(note.getId()) != null;
    }

    @Override
    public Note findByRoleDescription(String roleDescription) {
        return noteRepository.findByRoleDescription(roleDescription);
    }

}
