package mingle.chang.service.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mingle.chang.service.mybatis.dataobject.BaseDO;

import java.math.BigDecimal;

@TableName("exchange_rate")
@KeySequence("exchange_rate_id_seq")
@Data
@EqualsAndHashCode(callSuper = false)
public class ExchangeRateDO extends BaseDO {
    private String code;
    private BigDecimal rate;
}
