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
import java.time.LocalDate;
import java.util.Date;


@Component
@AllArgsConstructor
public class SchedulerService {
    private final RemoveOldsSoldProductsService removeOldsSoldProductsService;

    private final IncomeCalcService incomeCalcService;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = "0 01 00 * * *")
    public void deleteOldProductsWithZeroQuantity() throws InterruptedException {
    removeOldsSoldProductsService.removeOldProducts();
    logger.info("Cleared old products with zero quantity at date " + LocalDate.now().minusDays(14));
    }

    @Scheduled(cron = "0 05 00 * * *")
    public void calcDailyIncome() throws InterruptedException {
        incomeCalcService.calcDailyIncome();
        logger.info("Calculated incomes by branches in date " + LocalDate.now().minusDays(1));
    }
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enable",matchIfMissing = true)
class SchedulingConfiguration {

}