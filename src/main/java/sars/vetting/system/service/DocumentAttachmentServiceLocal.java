/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import sars.vetting.system.domain.JsonDocumentDto;

/**
 *
 * @author S2026987
 */
public interface DocumentAttachmentServiceLocal {

    public JsonDocumentDto getDocumentJsonValue(String code);

    public String uploadDocument(JsonDocumentDto document);

    public void download(String objectId);

    

}
