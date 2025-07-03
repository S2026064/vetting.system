/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2028398
 */
@Configuration
@EnableScheduling
public class TATSchedularService {

    @Autowired
    private VettingServiceLocal vettingService;

    @Scheduled(cron = "0 30 0 * * ?")
    public void reminder() {
        System.out.println("TAT Schedular is begining");
        Pageable pageable = PageRequest.of(0, 15);
        Slice<Vetting> escalatedVettings = vettingService.findAll(pageable);
        System.out.println(escalatedVettings.getNumberOfElements() + " has been collected");
        for (Vetting vetting : escalatedVettings.toList()) {
            if (vetting.getCreatedDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                String createdDate = dateFormat.format(vetting.getCreatedDate());
                String updateDate = dateFormat.format(new Date());
                int createdDay = Integer.parseInt(createdDate.substring(0, 2));
                int currentDay = Integer.parseInt(updateDate.substring(0, 2));
                Integer daysBetween = 0;
                if (currentDay > createdDay) {
                    daysBetween = currentDay - createdDay;
                } else {
                    daysBetween = createdDay - currentDay;
                }
                vetting.setTat(daysBetween);
                vettingService.update(vetting);
            }
        }
        System.out.println("TAT Schedular has finished");
    }
}
