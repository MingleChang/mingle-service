package mingle.chang.service.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import mingle.chang.service.mybatis.dataobject.BaseDO;
import mingle.chang.service.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MetaObjectHandlerImpl implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        setFieldValByName(BaseDO.SF_CREATE_TIME, localDateTime, metaObject);
        setFieldValByName(BaseDO.SF_UPDATE_TIME, localDateTime, metaObject);
        setFieldValByName(BaseDO.SF_IS_DELETED, 0, metaObject);
        Long id = SecurityUtils.getUserId();
        if (Objects.nonNull(id)) {
            setFieldValByName(BaseDO.SF_CREATOR, id, metaObject);
            setFieldValByName(BaseDO.SF_UPDATER, id, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        setFieldValByName(BaseDO.SF_UPDATE_TIME, localDateTime, metaObject);

        Long id = SecurityUtils.getUserId();
        if (Objects.nonNull(id)) {
            setFieldValByName(BaseDO.SF_UPDATER, id, metaObject);
        }
    }
}
