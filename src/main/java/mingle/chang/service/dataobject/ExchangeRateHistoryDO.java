package mingle.chang.service.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mingle.chang.service.mybatis.dataobject.BaseDO;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("exchange_rate_history")
@KeySequence("exchange_rate_history_id_seq")
@Data
@EqualsAndHashCode(callSuper = false)
public class ExchangeRateHistoryDO extends BaseDO {
    private String code;
    private BigDecimal rate;
    private LocalDate pt;
}
