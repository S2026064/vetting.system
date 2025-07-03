/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.Attachment;

/**
 *
 * @author S2026987
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Modifying
    @Query("delete from Attachment a where a.id=:attachId")
    void deleteAttachment(@Param("attachId") Long id);

}
