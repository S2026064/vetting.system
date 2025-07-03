/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.service.EmailNotificationServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class EmailNotificationBean extends BaseBean<EmailNotification> {

    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;

    private List<NotificationType> notificationTypes = new ArrayList<>();

    private Page<EmailNotification> slices;
    private boolean disabled;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        setPanelTitleName("Email Notifications");
//        Pageable pageable = PageRequest.of(0, 10);
        notificationTypes.addAll(Arrays.asList(NotificationType.values()));
//        slices = emailNotificationService.findAll(pageable);
//        addCollections(slices.toList());
        addCollections(emailNotificationService.listAll());

    }

    public void addOrUpdate(EmailNotification emailNotification) {
        reset().setAdd(true);
        if (emailNotification != null) {
            disabled = Boolean.TRUE;
            setPanelTitleName("Update Email Notification");
            emailNotification.setUpdatedBy(getActiveUser().getSid());
            emailNotification.setUpdatedDate(new Date());
        } else {
            disabled = Boolean.FALSE;
            setPanelTitleName("Add Email Notification");
            emailNotification = new EmailNotification();
            emailNotification.setCreatedBy(getActiveUser().getSid());
            emailNotification.setCreatedDate(new Date());
            addCollection(emailNotification);
        }
        addEntity(emailNotification);
    }

    public void save(EmailNotification emailNotification) {
        if (emailNotification.getId() != null) {
            emailNotificationService.update(emailNotification);
            addInformationMessage("Email Notification was successfully updated.");
        } else {
            emailNotificationService.save(emailNotification);
            addInformationMessage("Email Notification successfully created.");
        }
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }

    public void cancel(EmailNotification emailNotification) {
        if (emailNotification.getId() == null && getEmailNotifications().contains(emailNotification)) {
            remove(emailNotification);
        }
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }

    public void delete(EmailNotification emailNotification) {
        emailNotificationService.deleteById(emailNotification.getId());
        remove(emailNotification);
        addInformationMessage("Email Notification was successfully deleted");
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }

    public Integer getPageNumber() {
        return slices.getNumber() + 1;
    }

    public boolean isNextPage() {
        return slices.hasNext();
    }

    public boolean isPreviousPage() {
        return slices.hasPrevious();
    }

    public Integer getNumberOfPages() {
        return slices.getTotalPages();
    }

    public void firstPageDocuments() {
        slices = emailNotificationService.findAll(slices.previousOrFirstPageable());
        addCollections(slices.toList());
    }

    public void lastPageDocuments() {
        slices = emailNotificationService.findAll(slices.nextOrLastPageable());
        addCollections(slices.toList());
    }

    public void nextDocuments() {
        if (slices.hasNext()) {
            slices = emailNotificationService.findAll(slices.nextPageable());
            addCollections(slices.toList());
        }
    }

    public void previousDocuments() {
        if (slices.hasPrevious()) {
            slices = emailNotificationService.findAll(slices.previousPageable());
            addCollections(slices.toList());
        }
    }

    public List<EmailNotification> getEmailNotifications() {
        return this.getCollections();
    }

    public List<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(List<NotificationType> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
