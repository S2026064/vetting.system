/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author S2028398
 */
@ManagedBean
@RequestScoped
public class HeaderBean extends BaseBean {
    
    private Boolean hideDialogFlag;
    private Integer timeoutParam;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }
    
    public void onIdle() {
        setHideDialogFlag(true);
        setTimeoutParam(10);
    }

    public void resetSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setMaxInactiveInterval(120);
        setHideDialogFlag(false);
    }

    public void onTimeout() {
        addInformationMessage("Redirecting to Login Page");
        this.redirectToHtmlLoginPage("login");
    }

    public void onActive() {
        setHideDialogFlag(false);
    }

    public void logout() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(HeaderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean getHideDialogFlag() {
        return hideDialogFlag;
    }

    public void setHideDialogFlag(Boolean hideDialogFlag) {
        this.hideDialogFlag = hideDialogFlag;
    }

    public Integer getTimeoutParam() {
        return timeoutParam;
    }

    public void setTimeoutParam(Integer timeoutParam) {
        this.timeoutParam = timeoutParam;
    }
    
    
}
