package mingle.chang.service.repository.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mingle.chang.service.dataobject.ExchangeRateDO;
import mingle.chang.service.mapper.ExchangeRateMapper;
import mingle.chang.service.mybatis.wrapper.QueryWrapperX;
import mingle.chang.service.repository.ExchangeRateRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExchangeRateRepositoryImpl extends ServiceImpl<ExchangeRateMapper, ExchangeRateDO> implements ExchangeRateRepository {
    @Override
    public boolean insertBatch(Map<String, Object> rates) {
        removeAll();
        List<ExchangeRateDO> list = new ArrayList<>();
        for (Map.Entry<String, Object> rate : rates.entrySet()) {
            String code = rate.getKey();
            Object value = rate.getValue();
            BigDecimal rateValue = NumberUtil.toBigDecimal((Number) value);
            ExchangeRateDO rateDO = new ExchangeRateDO();
            rateDO.setCode(code);
            rateDO.setRate(rateValue);
            list.add(rateDO);
        }
        return saveBatch(list);
    }

    @Override
    public boolean removeAll() {
        return remove(new QueryWrapperX<>());
    }
}
