/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author S2028398
 */
@ManagedBean
@RequestScoped
public class MenuBean extends BaseBean {

    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    public String redirectPage(String page) {
        return redirectRouter(page);
    }

    public String route(String page) {
        System.out.println("selected page =" + page);
        return defaultRouter(page);
    }

    public String routing(String page) {
        if (page.equalsIgnoreCase("/users")) {
            getActiveUser().getRouter().reset().setAdministrator(true);
        }

        return defaultRouter(page);
    }

    public String routeToAdmin(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome To Administration");
        getActiveUser().getRouter().reset().setAdministrator(true);
        return defaultRouter(page);
    }

    public String routeToVetting(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome to Vetting Process");
        return defaultRouter(page);
    }

    public String routeToReports(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome To Reports");
        getActiveUser().getRouter().reset().setReports(true);
        return defaultRouter(page);
    }
}
