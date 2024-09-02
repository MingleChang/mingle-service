package mingle.chang.service.repository.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mingle.chang.service.dataobject.ExchangeRateDO;
import mingle.chang.service.dataobject.ExchangeRateHistoryDO;
import mingle.chang.service.mapper.ExchangeRateHistoryMapper;
import mingle.chang.service.mybatis.wrapper.LambdaQueryWrapperX;
import mingle.chang.service.repository.ExchangeRateHistoryRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExchangeRateHistoryRepositoryImpl extends ServiceImpl<ExchangeRateHistoryMapper, ExchangeRateHistoryDO> implements ExchangeRateHistoryRepository {
    @Override
    public boolean insertBatch(Map<String, Object> rates, LocalDate pt) {
        removeByPt(pt);
        List<ExchangeRateHistoryDO> list = new ArrayList<>();
        for (Map.Entry<String, Object> rate : rates.entrySet()) {
            String code = rate.getKey();
            Object value = rate.getValue();
            BigDecimal rateValue = NumberUtil.toBigDecimal((Number) value);
            ExchangeRateHistoryDO rateHistoryDO = new ExchangeRateHistoryDO();
            rateHistoryDO.setCode(code);
            rateHistoryDO.setRate(rateValue);
            rateHistoryDO.setPt(pt);
            list.add(rateHistoryDO);
        }
        return saveBatch(list);
    }

    @Override
    public boolean removeByPt(LocalDate pt) {
        LambdaQueryWrapperX<ExchangeRateHistoryDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ExchangeRateHistoryDO::getPt, pt);
        return remove(wrapper);
    }
}
