package mingle.chang.service.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public interface ExchangeRateService {
    void sync(LocalDate date);
}
