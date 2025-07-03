/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.CertificateType;
import sars.vetting.system.common.Properties;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.common.VettingType;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Certificate;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CertificateServiceLocal;
import sars.vetting.system.service.DocumentAttachmentServiceLocal;

/**
 *
 * @author S2028873
 */
@ManagedBean
@ViewScoped
public class CertificateBean extends BaseBean<Certificate> {

    @Autowired
    private DocumentAttachmentServiceLocal documentAttachmentService;
    @Autowired
    private CertificateServiceLocal certificateService;

    private List<Certificate> documentations = new ArrayList<>();
    private List<CertificateType> certificateTypes;

    private CertificateType selectedAttachmentType = null;
    private UploadedFile originalFile;
    private static final String DESTINATION = "./";
    private StreamedContent downloadFile;

    private VettingStatus vettingStatus;
    private VettingType vettingType;

    public void init(Vetting vettingRecord) {
        certificateTypes = new ArrayList<>();
        setVettingStatus(vettingRecord.getVettingStatus());
        setVettingType(vettingRecord.getVettingType());
        certificateTypes.addAll(Arrays.asList(CertificateType.values()));
        addCollections(vettingRecord.getCertificates());
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.originalFile = null;
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            this.originalFile = file;
            try {
                copyFile(event.getFile().getFileName(), file.getInputStream(), file.getSize());

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                file.getFileName() + " Successfully uploaded",
                                null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String fileName, InputStream inputStream, Long fileSize) {
        try {
            StringBuilder builder = new StringBuilder(DESTINATION);
            builder.append(fileName);
            try (OutputStream outputStream = new FileOutputStream(new File(builder.toString()))) {
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                inputStream.close();
                outputStream.flush();
            }
            addAttachmentItem(DESTINATION, fileName, fileSize);
        } catch (IOException e) {
            this.addInformationMessage(e.getMessage());
        }
    }

    public void addAttachmentItem(String destination, String fileName, Long fileSize) {
        Certificate localAttachment = new Certificate();
        localAttachment.setCreatedBy(getActiveUser().getSid());
        localAttachment.setCertificateType(selectedAttachmentType);
        localAttachment.setCreatedDate(new Date());
        localAttachment.setDescription(fileName);
        localAttachment.setUploadedBy(getActiveUser().getFullName());
        addCollection(localAttachment);
    }

    public JsonDocumentDto uploadDocumentsToServer(Attachment localAttachment) {
        String base64File = "";
        JsonDocumentDto jsonDocumentDto = new JsonDocumentDto();
        List<Properties> propertyList = new ArrayList<>();
        Properties properties = new Properties();
        properties.setPropertyName(localAttachment.getDescription());
        propertyList.add(properties);

        StringBuilder builder = new StringBuilder(DESTINATION);
        builder.append(localAttachment.getDescription());
        File newFile = new File(builder.toString());

        try (FileInputStream imageInFile = new FileInputStream(newFile)) {
            byte fileData[] = new byte[(int) newFile.length()];
            imageInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        } catch (FileNotFoundException e) {
            this.addInformationMessage("File not found" + e);
        } catch (IOException ioe) {
            this.addInformationMessage("Exception while reading the file " + ioe);
        }
        jsonDocumentDto.setObjectType("sars_pca_docs");
        jsonDocumentDto.setObjectName(localAttachment.getDescription());
        jsonDocumentDto.setContentType(FilenameUtils.getExtension(localAttachment.getDescription()));
        jsonDocumentDto.setAuthor(getActiveUser().getSid());
        jsonDocumentDto.setProperties(propertyList);
        jsonDocumentDto.setContent(base64File);
        return jsonDocumentDto;
    }

    public void removeAttach(Certificate attachment) {
        if (attachment.getId() != null) {
            remove(attachment);
            attachment.setVetting(null);
//            attachmentService.update(attachment);
            certificateService.deleteAttachment(attachment.getId());
        } else {
            remove(attachment);
        }

        addInformationMessage("Vetting Document successfuly deleted");
    }

    public void fileDownload(Certificate documentation) throws IOException {
        String objectId = documentation.getCode();
        documentAttachmentService.download(objectId);
        FileInputStream fis = new FileInputStream("./document.txt");
        String stringTooLong = IOUtils.toString(fis, "UTF-8");
        String b64 = stringTooLong;
        byte[] decoder = Base64.getDecoder().decode(b64);
        downloadFile = DefaultStreamedContent.builder().name(documentation.getDescription()).contentType("application/pdf").stream(() -> new ByteArrayInputStream(decoder)).build();
    }
    
    public List<Certificate> getCertificates() {
        return getCollections();
    }

    public DocumentAttachmentServiceLocal getDocumentAttachmentService() {
        return documentAttachmentService;
    }

    public void setDocumentAttachmentService(DocumentAttachmentServiceLocal documentAttachmentService) {
        this.documentAttachmentService = documentAttachmentService;
    }

    public List<Certificate> getDocumentations() {
        return documentations;
    }

    public void setDocumentations(List<Certificate> documentations) {
        this.documentations = documentations;
    }

    public List<CertificateType> getCertificateTypes() {
        return certificateTypes;
    }

    public void setCertificateTypes(List<CertificateType> certificateTypes) {
        this.certificateTypes = certificateTypes;
    }

    public CertificateType getSelectedAttachmentType() {
        return selectedAttachmentType;
    }

    public void setSelectedAttachmentType(CertificateType selectedAttachmentType) {
        this.selectedAttachmentType = selectedAttachmentType;
    }

    public UploadedFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(UploadedFile originalFile) {
        this.originalFile = originalFile;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public VettingStatus getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(VettingStatus vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

    public VettingType getVettingType() {
        return vettingType;
    }

    public void setVettingType(VettingType vettingType) {
        this.vettingType = vettingType;
    }

}
