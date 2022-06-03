package com.example.store_automation.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@AllArgsConstructor
public class SchedulerService {
    private final RemoveOldsSoldProductsService removeOldsSoldProductsService;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = "0 01 00 * * *")
    public void deleteOldProductsWithZeroQuantity() throws InterruptedException {
    removeOldsSoldProductsService.removeOldProducts();
    logger.info("cleared products with a date more than 14 days old  ",dateFormat.format(new Date()));



    }
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enable",matchIfMissing = true)
class SchedulingConfiguration {

}