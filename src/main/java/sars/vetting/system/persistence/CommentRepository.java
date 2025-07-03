/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2026064
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
