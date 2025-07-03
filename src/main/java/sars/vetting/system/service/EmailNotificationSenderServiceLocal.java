/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2028398
 */
public interface EmailNotificationSenderServiceLocal {

//    public boolean sendEmailNotification(NotificationType notificationType, Date date, Employee recipients, Employee provider);
    
    public void sendNotification(NotificationType notificationType, Date date, Employee recipients, Employee provider)throws UnknownHostException, FileNotFoundException, IOException;
    public void sendNotificationApproval(NotificationType notificationType, Date date, Employee recipients, Employee provider,Vetting vetting)throws UnknownHostException, FileNotFoundException, IOException;
    public void sendNotificationReallocate(NotificationType notificationType, Date date, Employee recipient, Employee provider,Employee oldEmployee)throws UnknownHostException, FileNotFoundException, IOException;

}
