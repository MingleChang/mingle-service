package mingle.chang.service.task;

import jakarta.annotation.Resource;
import mingle.chang.service.service.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ExchangeRateTask {
    @Resource
    private ExchangeRateService exchangeRateService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void sync() {
        LocalDate date = LocalDate.now();
        exchangeRateService.sync(date);
    }
}
