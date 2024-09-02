package mingle.chang.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import mingle.chang.service.response.Response;
import mingle.chang.service.service.ExchangeRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Exchange Rate")
@RestController
@RequestMapping("exchangeRate")
public class ExchangeRateController {
    @Resource
    private ExchangeRateService exchangeRateService;

    @Operation(summary = "同步汇率数据")
    @GetMapping("sync")
    @PermitAll
    public Response sync(@RequestParam("date") LocalDate date) {
        Response response = Response.success();
        exchangeRateService.sync(date);
        return response;
    }
}
