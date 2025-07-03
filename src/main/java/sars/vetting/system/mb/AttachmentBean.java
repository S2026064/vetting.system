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
import sars.vetting.system.common.AttachmentType;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.Properties;
import sars.vetting.system.common.ScreeningResponseOption;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.common.VettingType;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.AttachmentServiceLocal;
import sars.vetting.system.service.DocumentAttachmentServiceLocal;

/**
 *
 * @author S2028873
 */
@ManagedBean
@ViewScoped
public class AttachmentBean extends BaseBean<Attachment> {

    @Autowired
    private DocumentAttachmentServiceLocal documentAttachmentService;
    @Autowired
    private AttachmentServiceLocal attachmentService;

    private List<Attachment> documentations = new ArrayList<>();
    private List<AttachmentType> attachmentTypes;

    private AttachmentType selectedAttachmentType = null;
    private UploadedFile originalFile;
    private static final String DESTINATION = "./";
    private StreamedContent downloadFile;

    private VettingStatus vettingStatus;
    private VettingType vettingType;

    public void init(Vetting vettingRecord) {
        attachmentTypes = new ArrayList<>();
        setVettingStatus(vettingRecord.getVettingStatus());
        setVettingType(vettingRecord.getVettingType());
        if (!getActiveUser().getEmployeeRole().getDescription().equals(EmployeeRoleType.MANAGER.toString())) {
            addCollections(vettingRecord.getAttachments());
        }

        //for Vetted employee
        if (vettingRecord.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT) || vettingRecord.getVettingStatus().equals(VettingStatus.REWORK)) {

            attachmentTypes.add(AttachmentType.ID);
            attachmentTypes.add(AttachmentType.ID_PHOTO);
            attachmentTypes.add(AttachmentType.Z204_FORM);
            attachmentTypes.add(AttachmentType.STATEMENT);
            attachmentTypes.add(AttachmentType.SALARY_ADVICE);
            attachmentTypes.add(AttachmentType.SAVINGS_ACCOUNT);
            attachmentTypes.add(AttachmentType.PHOTO);
            attachmentTypes.add(AttachmentType.QUALIFICATION);
            attachmentTypes.add(AttachmentType.ACADEMIC_CERTIFICATE);
            attachmentTypes.add(AttachmentType.DOPI);
            attachmentTypes.add(AttachmentType.SCREENING_DECLARATION);
            attachmentTypes.add(AttachmentType.BOND);
            attachmentTypes.add(AttachmentType.CAR_LOAN);
            attachmentTypes.add(AttachmentType.CHEQUE_ACCOUNT);
            attachmentTypes.add(AttachmentType.CREDIT_CARD_ACCOUNT);
            attachmentTypes.add(AttachmentType.DIVORCE_CERTIFICATE);
            attachmentTypes.add(AttachmentType.INCOME_EXPENDITURE);
            attachmentTypes.add(AttachmentType.MARRIAGE_CERTIFICATE);
            attachmentTypes.add(AttachmentType.OTHER_ACCOUNTS);
            attachmentTypes.add(AttachmentType.OTHER_FINANCIAL_LOAN);
            attachmentTypes.add(AttachmentType.PASSPORT);
            attachmentTypes.add(AttachmentType.PERSONAL_LOAN);
            attachmentTypes.add(AttachmentType.STUDY_LOAN);
            attachmentTypes.add(AttachmentType.MARRIAGE_CERTIFICATE);
            attachmentTypes.add(AttachmentType.OTHER);
            attachmentTypes.add(AttachmentType.OTHER_DOC);
            //if vetted employee is a service provider the add this attachement type to the list
            if (vettingRecord.getVettingType().equals(VettingType.PROVIDER)) {
                attachmentTypes.add(AttachmentType.CONSENT);
                attachmentTypes.add(AttachmentType.DOTS_FORM);
            }
        }
        if (vettingRecord.getVettingStatus().equals(VettingStatus.FORMS_ISSUED)) {
            attachmentTypes.add(AttachmentType.ID);
            attachmentTypes.add(AttachmentType.Z204_FORM);
            attachmentTypes.add(AttachmentType.VETTING_FORM);
            attachmentTypes.add(AttachmentType.OTHER_DOC);
            if (vettingRecord.getScreeningDeclaration().getScreeningDeclarationResponse().getRsaCitizen() != null) {
                if (vettingRecord.getScreeningDeclaration().getScreeningDeclarationResponse().getRsaCitizen().equals(ScreeningResponseOption.NO)) {
                    attachmentTypes.add(AttachmentType.REFUGEE_STATUS);
                    attachmentTypes.add(AttachmentType.RESIDANCE_STATUS);
                    attachmentTypes.add(AttachmentType.WORK_PERMIT);
                }
            }
            //if vetted employee is a service provider the add this attachement type to the list
            if (vettingRecord.getVettingType().equals(VettingType.PROVIDER)) {
                attachmentTypes.add(AttachmentType.CONSENT);
                attachmentTypes.add(AttachmentType.DOTS_FORM);

            }
        }
        //for Vetting officer //for Vetting QA // for vetting analyst//manager//approvers
        if (vettingRecord.getVettingStatus().equals(VettingStatus.CLEARANCE_DENIED) || vettingRecord.getVettingStatus().equals(VettingStatus.CLEARANCE_ISSUED) || vettingRecord.getVettingStatus().equals(VettingStatus.SSA_PROCESSING) || vettingRecord.getVettingStatus().equals(VettingStatus.SARS_PROCESSING) || vettingRecord.getVettingStatus().equals(VettingStatus.SENT_TO_QA) || vettingRecord.getVettingStatus().equals(VettingStatus.FORMS_SUBMITTED) || getActiveUser().getEmployeeRole().getDescription().equals(EmployeeRoleType.MANAGER.toString())) {
            attachmentTypes.add(AttachmentType.OTHER_DOC);
        }
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
            try ( OutputStream outputStream = new FileOutputStream(new File(builder.toString()))) {
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
        Attachment localAttachment = new Attachment();
        localAttachment.setCreatedBy(getActiveUser().getSid());
        localAttachment.setAttachmentType(selectedAttachmentType);
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

        try ( FileInputStream imageInFile = new FileInputStream(newFile)) {
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

    public void removeAttach(Attachment attachment) {
        if (attachment.getId() != null) {
            remove(attachment);
            attachment.setVetting(null);
//            attachmentService.update(attachment);
            attachmentService.deleteAttachment(attachment.getId());
        } else {
            remove(attachment);
        }

        addInformationMessage("Vetting Document successfuly deleted");
    }

    public void fileDownload(Attachment documentation) throws IOException {
        String objectId = documentation.getCode();
        documentAttachmentService.download(objectId);
        FileInputStream fis = new FileInputStream("./document.txt");
        String stringTooLong = IOUtils.toString(fis, "UTF-8");
        String b64 = stringTooLong;
        byte[] decoder = Base64.getDecoder().decode(b64);
        downloadFile = DefaultStreamedContent.builder().name(documentation.getDescription()).contentType("application/pdf").stream(() -> new ByteArrayInputStream(decoder)).build();
    }

    public List<Attachment> getAttachments() {
        return getCollections();
    }

    public List<Attachment> getDocumentations() {
        return documentations;
    }

    public void setDocumentations(List<Attachment> documentations) {
        this.documentations = documentations;
    }

    public List<AttachmentType> getAttachmentTypes() {
        return attachmentTypes;
    }

    public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {
        this.attachmentTypes = attachmentTypes;
    }

    public AttachmentType getSelectedAttachmentType() {
        return selectedAttachmentType;
    }

    public void setSelectedAttachmentType(AttachmentType selectedAttachmentType) {
        this.selectedAttachmentType = selectedAttachmentType;
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

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

}
