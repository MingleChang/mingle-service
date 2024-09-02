package mingle.chang.service.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import mingle.chang.service.dataobject.ExchangeRateHistoryDO;

import java.time.LocalDate;
import java.util.Map;

public interface ExchangeRateHistoryRepository extends IService<ExchangeRateHistoryDO> {

    boolean insertBatch(Map<String, Object> rates, LocalDate pt);
    boolean removeByPt(LocalDate pt);
}
