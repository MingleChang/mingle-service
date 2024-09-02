package mingle.chang.service.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import mingle.chang.service.repository.ExchangeRateHistoryRepository;
import mingle.chang.service.repository.ExchangeRateRepository;
import mingle.chang.service.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Value("${custom.exchange-rate.fixer-io.access-key}")
    private String accessKey;
    @Value("${custom.exchange-rate.fixer-io.host}")
    private String host;
    @Value("${custom.exchange-rate.fixer-io.symbols-endpoint}")
    private String symbolsEndpoint;
    @Value("${custom.exchange-rate.fixer-io.latest-endpoint}")
    private String latestEndpoint;
    @Value("${custom.exchange-rate.fixer-io.historical-endpoint}")
    private String historicalEndpoint;

    @Resource
    private ExchangeRateRepository exchangeRateRepository;
    @Resource
    private ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public void sync(LocalDate date) {
        LocalDate pt = date.plusDays(-1);
        Map<String, Object> lastRates = requestLastEndpoint();
        Map<String, Object> historyRates = requestHistoricalEndpoint(pt);
        exchangeRateRepository.insertBatch(lastRates);
        exchangeRateHistoryRepository.insertBatch(historyRates, pt);
    }

    private Map<String, Object> requestLastEndpoint() {
        String url = host + latestEndpoint + "?access_key="+accessKey;
        Map<String, String> params = new HashMap<>();
        params.put("access_key", accessKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class, params);
        Map<String, Object> rates = (Map<String, Object>) response.get("rates");
        return rates;
    }
    private Map<String, Object> requestHistoricalEndpoint(LocalDate date) {
        String pt = date.toString();
        String url = host + historicalEndpoint + pt + "?access_key="+accessKey;
        Map<String, String> params = new HashMap<>();
        params.put("access_key", accessKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class, params);
        Map<String, Object> rates = (Map<String, Object>) response.get("rates");
        return rates;
    }
}
