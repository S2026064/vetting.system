/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author S2028398
 */
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home.xhtml");
        registry.addViewController("/home").setViewName("home.xhtml");
        registry.addViewController("/users").setViewName("users.xhtml");
        registry.addViewController("/userroles").setViewName("userroles.xhtml");
    }
}
