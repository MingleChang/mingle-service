package mingle.chang.service.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import mingle.chang.service.dataobject.ExchangeRateDO;

import java.util.Map;

public interface ExchangeRateRepository extends IService<ExchangeRateDO> {
    boolean insertBatch(Map<String, Object> rates);
    boolean removeAll();
}
